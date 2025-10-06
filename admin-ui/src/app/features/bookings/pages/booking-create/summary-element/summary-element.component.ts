import {Component, EventEmitter, Input, OnDestroy, Output} from '@angular/core';
import {
  ButtonDirective,
  CardBodyComponent,
  CardComponent,
  CardHeaderComponent,
  ColComponent,
  RowComponent
} from '@coreui/angular';
import {Subscription} from 'rxjs';
import {BookingApiService} from '../../../services/booking-api.service';
import {BookingGetModel} from '../../../models/booking/booking-get.model';
import {TranslatePipe} from '@ngx-translate/core';
import {DecimalPipe} from '@angular/common';

@Component({
  selector: 'app-summary-element',
  imports: [
    CardComponent,
    CardBodyComponent,
    ButtonDirective,
    CardHeaderComponent,
    RowComponent,
    ColComponent,
    TranslatePipe,
    DecimalPipe
  ],
  templateUrl: './summary-element.component.html',
  standalone: true,
  styleUrl: './summary-element.component.scss'
})
export class SummaryElementComponent implements OnDestroy {
  private component = '[SummaryElementComponent]: ';
  @Input()
  bookingItem!: BookingGetModel;
  @Output()
  bookingItemDeleted = new EventEmitter<BookingGetModel>();
  private subscriptions: Subscription[] = [];

  constructor(private bookingApiService: BookingApiService) {
  }

  getChildrenCount() {
    return (this.bookingItem.stay.occupancy.children ?? []).reduce((total, child) => total + child.quantity, 0);
  }

  removeBookingItem() {
    this.subscriptions.push(this.bookingApiService.deleteById(this.bookingItem.id).subscribe({
      next: (data) => {
        console.info(this.component.concat('Booking with Id:'), this.bookingItem.id, 'successfully removed');
        this.bookingItemDeleted.emit(this.bookingItem);
      },
      error: (err) => {
        console.info(this.component.concat('An error occurred when deleting booking with Id:'), this.bookingItem.id, '. Api Error response is:', err);
      }
    }))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }


}
