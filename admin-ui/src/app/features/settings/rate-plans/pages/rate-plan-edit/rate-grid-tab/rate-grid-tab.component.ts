import {Component, OnDestroy} from '@angular/core';
import {
  ButtonCloseDirective,
  ColComponent,
  FormLabelDirective,
  OffcanvasBodyComponent,
  OffcanvasComponent,
  OffcanvasHeaderComponent,
  OffcanvasTitleDirective,
  RowComponent,
  TableDirective
} from '@coreui/angular';
import {TranslatePipe} from '@ngx-translate/core';
import {DatePipe, DecimalPipe, NgClass, TitleCasePipe, UpperCasePipe} from '@angular/common';
import {ActivatedRoute} from '@angular/router';
import {combineLatest, Subscription} from 'rxjs';
import {RatePlanApiService} from '../../../services/rate-plan-api.service';
import {NgxDaterangepickerBootstrapDirective} from 'ngx-daterangepicker-bootstrap';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import dayjs, {Dayjs} from 'dayjs';
import utc from 'dayjs/plugin/utc';
import {RateApiService} from '../../../services/rate-api.service';
import {RateModalEditComponent} from '../rate-modal-edit/rate-modal-edit.component';
import {BadgeComponent} from '../../../../../../shared/components/badge/badge.component';

@Component({
  selector: 'app-rate-grid-tab',
  imports: [
    TableDirective,
    TranslatePipe,
    UpperCasePipe,
    ColComponent,
    NgxDaterangepickerBootstrapDirective,
    ReactiveFormsModule,
    RowComponent,
    DatePipe,
    FormLabelDirective,
    TitleCasePipe,
    DecimalPipe,
    OffcanvasComponent,
    OffcanvasHeaderComponent,
    OffcanvasTitleDirective,
    ButtonCloseDirective,
    OffcanvasBodyComponent,
    RateModalEditComponent,
    BadgeComponent,
    NgClass
  ],
  templateUrl: './rate-grid-tab.component.html',
  standalone: true,
  styleUrl: './rate-grid-tab.component.scss'
})
export class RateGridTabComponent implements OnDestroy {
  private component = '[RateGridTabComponent]: ';
  dates: Date[] = [];
  ratePlan!: any;
  rates!: any;
  rateToEdit!: any | null;
  visible = false;
  ratePlanId!: string;
  searchForm!: FormGroup;
  private readonly subscriptions: Subscription[] = [];

  constructor(private fb: FormBuilder,
              private readonly activatedRoute: ActivatedRoute,
              private readonly ratePlanApiService: RatePlanApiService,
              private readonly rateApiService: RateApiService) {
    dayjs.extend(utc);
    let period = this.generateTwoWeekPeriod();
    this.searchForm = this.fb.group({
      period: [period, [Validators.required]]
    })
    this.generateDateArray(period);
    this.subscriptions.push(
      combineLatest(
        this.activatedRoute.pathFromRoot.map(route => route.paramMap)
      ).subscribe(paramMaps => {
        const ratePlanId = paramMaps
          .map(paramMap => paramMap.get('ratePlanId'))
          .find(id => id !== null);
        if (ratePlanId) {
          this.ratePlanId = ratePlanId;
          // this.retrieveRatePlan();
          this.retrieveRates();
        }
      })
    );
  }

  private generateTwoWeekPeriod(): { startDate: Dayjs; endDate: Dayjs } {
    const startDate = dayjs(); // current date/time
    const endDate = startDate.add(2, "week"); // two weeks later
    return {startDate, endDate};
  }

  dateRangeUpdated() {
    this.dates = [];
    this.generateDateArray(this.searchForm.value.period);
    this.retrieveRates();
  }

  generateDateArray(period: any) {
    let current = period.startDate.startOf("day");
    while (current.isBefore(period.endDate) || current.isSame(period.endDate, "day")) {
      this.dates.push(current.toDate()); // convert Dayjs → Date
      current = current.add(1, "day");
    }
  }

  editRate(unitRate: any, dailyRate: any) {
    console.log('Edit rate for date:', dailyRate.date);
    this.rateToEdit = {
      unitId: unitRate.unitId,
      unitName: unitRate.unitName,
      dailyRate: dailyRate,
    }
    this.toggleOffCanvas();
  }

  handleUpdatedRates(event: any) {
    this.retrieveRates();

    this.closeEditRate();

  }

  toggleOffCanvas() {
    if (!this.visible) {
      this.visible = !this.visible;
    }
  }

  closeEditRate() {
    this.visible = false;
    this.rateToEdit = null;
  }

  private retrieveRatePlan() {
    this.subscriptions.push(this.ratePlanApiService.getById(this.ratePlanId).subscribe({
      next: (data) => {
        console.info(this.component.concat('Rate plan retrieved successfully. API response is:'), data);
        this.ratePlan = data;
      },
      error: (err) => {
        console.error(this.component.concat('An error occurred when retrieving rate plan by Id:'), this.ratePlanId, '. Error API is:', err);
        //TODO: redirect to listing page with an alert message
      }
    }))
  }

  public retrieveRates() {
    this.subscriptions.push(this.rateApiService.getAll(this.ratePlanId, dayjs.utc(this.searchForm.value.period.startDate).local().format('YYYY-MM-DD'), dayjs.utc(this.searchForm.value.period.endDate).local().format('YYYY-MM-DD')).subscribe({
      next: (data) => {
        console.info(this.component.concat('Rates retrieved successfully. API response is:'), data);
        this.rates = data;
      },
      error: (err) => {
        console.error(this.component.concat('An error occurred when retrieving rates. Error API is:'), err);
        //TODO: redirect to listing page with an alert message
      }
    }))
  }

  isWeekend(dailyRate: any) {
    const date = new Date(dailyRate.date);
    if (isNaN(date.getTime())) {
      throw new Error("Invalid date provided");
    }
    const day = date.getDay(); // 0 = Sunday, 6 = Saturday
    return day === 0 || day === 6;
  }

  ngOnDestroy(): void {
    this.subscriptions.map(subscription => subscription.unsubscribe());
  }


}
