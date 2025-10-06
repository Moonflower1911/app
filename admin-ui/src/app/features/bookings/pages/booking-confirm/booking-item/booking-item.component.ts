import {Component, Input, OnDestroy} from '@angular/core';
import {BookingGetModel} from '../../../models/booking/booking-get.model';
import {
  CardBodyComponent,
  CardComponent,
  CardHeaderComponent,
  ColComponent,
  FormControlDirective,
  RowComponent,
  TableDirective
} from '@coreui/angular';
import {DatePipe, DecimalPipe, TitleCasePipe} from '@angular/common';
import {TranslatePipe} from '@ngx-translate/core';
import {RouterLink} from '@angular/router';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import {Subscription} from 'rxjs';
import {FeeGetModel} from '../../../models/fee/fee-get.model';
import {NgxIntlTelInputModule} from 'ngx-intl-tel-input';
import {ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-booking-item',
  imports: [
    CardBodyComponent,
    CardComponent,
    CardHeaderComponent,
    DatePipe,
    TitleCasePipe,
    TranslatePipe,
    RouterLink,
    ColComponent,
    RowComponent,
    TableDirective,
    FormControlDirective,
    DecimalPipe,
    NgxIntlTelInputModule,
    ReactiveFormsModule
  ],
  templateUrl: './booking-item.component.html',
  styleUrl: './booking-item.component.scss'
})
export class BookingItemComponent implements OnDestroy {
  private component = '[BookingItemComponent]: ';
  @Input() bookingItem!: BookingGetModel;
  @Input() index!: number;

  private subscriptions: Subscription[] = [];

  constructor() {
    dayjs.extend(utc);
  }

  getChildrenCount() {
    return (this.bookingItem.stay.occupancy.children ?? []).reduce((total, child) => total + child.quantity, 0);
  }

  getFeeChildrenCount(fee: FeeGetModel) {
    // return (fee.occupancy.children ?? []).reduce((total, child) => total + child.quantity, 0);
    return 0;
  }

  getNightCount() {
    if (this.bookingItem) {
      return dayjs.utc(this.bookingItem.stay.checkoutDate).local().diff(dayjs.utc(this.bookingItem.stay.checkinDate).local(), 'day');
    } else {
      return 0
    }
  }

  getTotal() {
    return this.bookingItem.totalPrice?.total;
  }

  ngOnDestroy(): void {
    console.log(this.component.concat('Cleaning subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }


}
