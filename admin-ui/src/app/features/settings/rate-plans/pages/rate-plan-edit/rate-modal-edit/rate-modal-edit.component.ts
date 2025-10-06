import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {
  ButtonDirective,
  ColComponent,
  FormControlDirective,
  FormFeedbackComponent,
  FormLabelDirective,
  RowComponent
} from '@coreui/angular';
import {NgxDaterangepickerBootstrapDirective} from 'ngx-daterangepicker-bootstrap';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {TranslatePipe} from '@ngx-translate/core';
import {Subscription} from 'rxjs';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import {maxTwoDecimalsValidator} from '../../../../../../shared/validators/max-two-decimals.validator';
import {RateApiService} from '../../../services/rate-api.service';

@Component({
  selector: 'app-rate-modal-edit',
  imports: [
    ColComponent,
    FormLabelDirective,
    NgxDaterangepickerBootstrapDirective,
    ReactiveFormsModule,
    RowComponent,
    TranslatePipe,
    FormControlDirective,
    ButtonDirective,
    FormFeedbackComponent
  ],
  templateUrl: './rate-modal-edit.component.html',
  standalone: true,
  styleUrl: './rate-modal-edit.component.scss'
})
export class RateModalEditComponent implements OnInit, OnDestroy {
  private component = '[RateModalEditComponent]: ';
  @Input()
  rateToEdit: any;
  @Input()
  ratePlanId: any;
  @Output() updatedRates: EventEmitter<any> = new EventEmitter<any>();
  rateForm!: FormGroup;
  private readonly subscriptions: Subscription[] = [];

  public constructor(private readonly fb: FormBuilder,
                     private readonly rateApiService: RateApiService) {
    dayjs.extend(utc);
    this.rateForm = this.fb.group({
      period: [null, [Validators.required]],
      singleAdult: [null, [Validators.required, maxTwoDecimalsValidator()]],
      doubleAdult: [null, [maxTwoDecimalsValidator()]],
      extraAdult: [null, [maxTwoDecimalsValidator()]],
      extraChild: [null, [maxTwoDecimalsValidator()]],
    })

  }

  ngOnInit(): void {
    if (this.rateToEdit) {
      let period = {
        startDate: dayjs.utc(dayjs(this.rateToEdit.dailyRate.date, "YYYY-MM-DD", true)).local(),
        endDate: dayjs.utc(dayjs(this.rateToEdit.dailyRate.date, "YYYY-MM-DD", true)).local()
      }
      this.rateForm.patchValue({
        period: period,
        amount: this.rateToEdit.dailyRate.amount
      })
    }
  }

  submit() {
    console.log(this.component.concat('Constructing payload ...'))
    let payload = {
      startDate: dayjs.utc(this.rateForm.value.period.startDate).local().format('YYYY-MM-DD'),
      endDate: dayjs.utc(this.rateForm.value.period.endDate).local().format('YYYY-MM-DD'),
      singleAdult: this.rateForm.value.singleAdult,
      doubleAdult: this.rateForm.value.doubleAdult,
      extraAdult: this.rateForm.value.extraAdult,
      extraChild: this.rateForm.value.extraChild,
      ratePlanId: this.ratePlanId,
      unitIds: [this.rateToEdit.unitId]
    }
    console.log(this.component.concat('Payload is:'), payload);
    this.subscriptions.push(this.rateApiService.post(payload).subscribe({
      next: (data: any) => {
        console.info(this.component.concat('Rates created successfully. API response is:'), data);
        this.updatedRates.emit(data);
      },
      error: (err) => {
        console.error(this.component.concat('An error occurred during rates creation/update. API error is:'), err);
        //TODO: add toastr
      }
    }))
  }

  ngOnDestroy(): void {
    console.log(this.component.concat('Unsubscribing all subscriptions ...'))
    this.subscriptions.map(subscription => subscription.unsubscribe());
  }


}
