import {Component, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {BookingApiService} from '../../services/booking-api.service';
import {BookingGetModel} from '../../models/booking/booking-get.model';
import {CommonModule} from '@angular/common';
import {cilEnvelopeClosed, cilPhone, cilUser} from '@coreui/icons';
import {PageTitleComponent} from "../../../../shared/components/page-title/page-title.component";
import {BookingHeaderComponent} from "../../components/booking-header/booking-header.component";
import {ColComponent, RowComponent} from '@coreui/angular';
import {BookingSideComponent} from '../../components/booking-side/booking-side.component';
import {Subscription} from 'rxjs';
import {BookingDetailItemComponent} from '../../components/booking-detail-item/booking-detail-item.component';

@Component({
  selector: 'app-booking-details',
  standalone: true,
  imports: [
    CommonModule,
    PageTitleComponent,
    BookingHeaderComponent,
    ColComponent,
    RowComponent,
    BookingSideComponent,
    BookingDetailItemComponent
  ],
  templateUrl: './booking-details.component.html',
  styleUrls: ['./booking-details.component.scss']
})
export class BookingDetailsComponent implements OnDestroy {
  private component = '[BookingDetailsComponent]: ';
  bookingId!: string;
  booking?: BookingGetModel;
  icons = {cilUser, cilEnvelopeClosed, cilPhone}
  private subscriptions: Subscription[] = [];

  constructor(private route: ActivatedRoute,
              private bookingApiService: BookingApiService) {
    this.fetchBooking();
  }

  getNotes() {
    if (this.booking) {
      if (this.booking.items.length > 1) {
        return this.booking.notes;
      } else {
        return this.booking.items[0].notes;
      }
    } else {
      return null;
    }
  }

  getBookingContact() {
    if (this.booking) {
      if (this.booking.items.length > 1) {
        return this.booking.contact;
      } else {
        return this.booking.items[0].contact;
      }
    } else {
      return null;
    }
  }

  getBookingId() {
    if (this.booking) {
      if (this.booking.items.length > 1) {
        return this.booking.id;
      } else {
        return this.booking.items[0].id;
      }
    } else {
      return null;
    }
  }

  private fetchBooking(): void {
    this.bookingId = this.route.snapshot.paramMap.get('bookingId')!;
    this.subscriptions.push(
      this.bookingApiService.getById(this.bookingId).subscribe({
        next: (res) => {
          console.info(this.component.concat('Booking with id:'), this.bookingId, 'retrieved successfully. API response:', res);
          this.booking = res;
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred when retrieving booking with id:'),
            this.bookingId, 'API error response:', err);
        }
      })
    );
  }

  ngOnDestroy(): void {
    console.log(this.component.concat('Cleaning subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
