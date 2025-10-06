import {Component, Input} from '@angular/core';
import {CardComponent, CardHeaderComponent, ColComponent, RowComponent} from '@coreui/angular';
import {TranslatePipe} from '@ngx-translate/core';
import {DecimalPipe} from '@angular/common';
import {BookingGetModel} from '../../models/booking/booking-get.model';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import {BookingStatusComponent} from '../booking-status/booking-status.component';

@Component({
  selector: 'app-booking-header',
  imports: [
    ColComponent,
    RowComponent,
    CardComponent,
    CardHeaderComponent,
    TranslatePipe,
    DecimalPipe,
    BookingStatusComponent
  ],
  templateUrl: './booking-header.component.html',
  styleUrl: './booking-header.component.scss'
})
export class BookingHeaderComponent {

  @Input()
  booking!: BookingGetModel;

  constructor() {
    dayjs.extend(utc);
  }

  getBookingTotal() {
    if (this.booking) {
      let totalAmount = 0;
      this.booking.items.forEach(item => {
        totalAmount += item.stay.totalPrice.amountInclTax;
        if (item.fees && item.fees.length > 0) {
          const totalFeesInclTax = item.fees
            .map(f => f.rate?.amountInclTax || 0)
            .reduce((sum, val) => sum + val, 0);
          totalAmount += totalFeesInclTax;
        }
      });

      return totalAmount;
    } else {
      return 0;
    }
  }

}
