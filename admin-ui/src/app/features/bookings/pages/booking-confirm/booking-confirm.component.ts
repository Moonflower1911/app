import {Component, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject, Subscription} from 'rxjs';
import {BookingApiService} from '../../services/booking-api.service';
import {BookingGetModel} from '../../models/booking/booking-get.model';
import {PageTitleComponent} from "../../../../shared/components/page-title/page-title.component";
import {TranslatePipe, TranslateService} from "@ngx-translate/core";
import {ButtonDirective, ColComponent, RowComponent} from "@coreui/angular";
import {AlertService} from "../../../../core/services/alert.service";
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";
import {NgxIntlTelInputModule} from "ngx-intl-tel-input";
import {BookingItemComponent} from "./booking-item/booking-item.component";
import dayjs from "dayjs";
import utc from "dayjs/plugin/utc";
import {BookingHeaderComponent} from '../../components/booking-header/booking-header.component';
import {BookingSideComponent} from '../../components/booking-side/booking-side.component';
import {JsonPipe} from '@angular/common';
import {BookingDetailItemComponent} from '../../components/booking-detail-item/booking-detail-item.component';

@Component({
  selector: 'app-booking-confirm',
  imports: [
    PageTitleComponent,
    TranslatePipe,
    ColComponent,
    RowComponent,
    ButtonDirective,
    ReactiveFormsModule,
    NgxIntlTelInputModule,
    BookingItemComponent,
    BookingHeaderComponent,
    BookingSideComponent,
    JsonPipe,
    BookingDetailItemComponent
  ],
  templateUrl: './booking-confirm.component.html',
  styleUrl: './booking-confirm.component.scss'
})
export class BookingConfirmComponent implements OnDestroy {
  private component = '[BookingConfirmComponent]: ';
  booking!: BookingGetModel;
  bookingId!: string;

  private subscriptions: Subscription [] = [];
  private destroy$ = new Subject<void>();

  constructor(private fb: FormBuilder,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private alertService: AlertService,
              private translateService: TranslateService,
              private bookingApiService: BookingApiService) {
    dayjs.extend(utc);
    this.retrieveBooking();
  }

  confirmBooking() {
    let payload = {
      status: 'CONFIRMED',
      applyStatusToAll: true,
    }
    this.subscriptions.push(this.bookingApiService.patchById(this.bookingId, payload).subscribe({
      next: (data) => {
        console.info(this.component.concat('Booking with id:'), this.bookingId, 'updated successfully. API response is:', data);
        this.router.navigate(['/bookings/list']).then(() => console.log('Routed to bookings list page'));
      },
      error: (err) => {
        console.error(this.component.concat('An error occurred when updating the booking with id:'),
          this.bookingId, 'API error response is:', err);
      }
    }))
  }

  generateProforma() {

  }

  private retrieveBooking() {
    this.subscriptions.push(this.activatedRoute.paramMap.subscribe(value => {
      this.bookingId = value.get('bookingId') as string;
      if (this.bookingId) {
        this.subscriptions.push(this.bookingApiService.getById(this.bookingId).subscribe({
          next: (data) => {
            console.log(this.component.concat('booking with id:'), this.bookingId, 'retrieved successfully. API response is:', data);
            if (data.type == 'SINGLE') {
              console.warn(this.component.concat('Booking is of type SINGLE and can\'t be confirmed from this page. Will route to booking list page ...'))
              this.alertService.addAlert(this.translateService.instant('bookings.pages.confirm.errors.single-type').replace(':bookingRef', data.reference), 'warning', true);
              this.router.navigate(['/bookings/list']).then(() => console.info(this.component.concat('Routed to bookings list page')));
            }
            if (data.status != 'DRAFT') {
              console.warn(this.component.concat('Booking is not in status DRAFT and can\'t be confirmed from this page. Will route to booking list page ...'))
              this.alertService.addAlert(this.translateService.instant('bookings.pages.confirm.errors.not-draft-status').replace(':bookingRef', data.reference), 'warning', true);
              this.router.navigate(['/bookings/list']).then(() => console.info(this.component.concat('Routed to bookings list page')));
            }
            this.booking = data;

          },
          error: (err) => {
            console.error(this.component.concat('An error occurred when retrieving booking with id:'), this.bookingId, 'API error response is:', err, 'will route to bookings list');
            this.alertService.addAlert(this.translateService.instant('bookings.pages.confirm.errors.not-found'), 'warning', true);
            this.router.navigate(['/bookings/list']).then(() => console.info(this.component.concat('Routed to bookings list page')));
          }
        }))
      }
    }));
  }

  updateContact(event: BookingGetModel) {
    console.log('your event is:', event);
    this.booking.contact = event.contact;
  }

  bookingNotValid() {
    if (!this.booking.contact) {
      return true;
    } else {
      if(!this.booking.contact.name){
        return true;
      }
      if(!this.booking.contact.email){
        return true;
      }
      return false;
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());

  }


}
