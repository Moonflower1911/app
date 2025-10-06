import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {
  ButtonDirective,
  CardBodyComponent,
  CardComponent,
  CardFooterComponent,
  CardHeaderComponent,
  ColComponent,
  RowComponent,
  TableDirective
} from '@coreui/angular';
import {BookingStatusComponent} from '../booking-status/booking-status.component';
import {BookingGetModel} from '../../models/booking/booking-get.model';
import {UtilsService} from '../../../../shared/services/utils.service';
import {BookingService} from '../../services/booking.service';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {DatePipe, DecimalPipe, TitleCasePipe} from '@angular/common';
import {TranslatePipe} from '@ngx-translate/core';
import {BsModalService} from 'ngx-bootstrap/modal';
import {BookingRoomAssignComponent} from '../booking-room-assign/booking-room-assign.component';
import {Subscription} from 'rxjs';
import {ToastrService} from 'ngx-toastr';
import {InvoiceApiService} from '../../services/invoice-api.service';
import {saveAs} from 'file-saver';
import {BookingStatusEnum} from '../../models/booking-status.enum';

@Component({
  selector: 'app-booking-detail-item',
  imports: [
    CardComponent,
    CardHeaderComponent,
    CardBodyComponent,
    BookingStatusComponent,
    RouterLink,
    DecimalPipe,
    ColComponent,
    RowComponent,
    TableDirective,
    TitleCasePipe,
    TranslatePipe,
    DatePipe,
    CardFooterComponent,
    ButtonDirective
  ],
  templateUrl: './booking-detail-item.component.html',
  styleUrl: './booking-detail-item.component.scss',
  providers: [BsModalService]
})
export class BookingDetailItemComponent implements OnDestroy, OnInit {

  @Input()
  booking!: BookingGetModel;
  protected readonly UtilsService = UtilsService;
  protected readonly BookingService = BookingService;

  loading = false;
  bookingId!: string;

  private readonly subscriptions: Subscription[] = [];
  clicked = false;

  constructor(private readonly utilsService: UtilsService,
              private readonly bookingService: BookingService,
              private route: ActivatedRoute,
              private toastr: ToastrService,
              private invoiceApiService: InvoiceApiService,
              private readonly modalService: BsModalService) {
  }

  ngOnInit(): void {
    this.bookingId = this.route.snapshot.paramMap.get('bookingId') || '';
  }

  getBookingTotal() {
    if (this.booking) {
      let totalAmount = 0;
      totalAmount += this.booking.stay.totalPrice.amountInclTax;
      if (this.booking.fees && this.booking.fees.length > 0) {
        const totalFeesInclTax = this.booking.fees
          .map(f => f.rate?.amountInclTax || 0)
          .reduce((sum, val) => sum + val, 0);
        totalAmount += totalFeesInclTax;
      }
      return totalAmount;
    } else {
      return 0;
    }
  }


  openModal() {
    let initialState = {
      class: 'modal-lg',
      initialState: {
        bookingId: this.booking.id
      }
    }
    let assignModalRef = this.modalService.show(BookingRoomAssignComponent, initialState);
    this.subscriptions.push((assignModalRef.content as BookingRoomAssignComponent).actionConfirmed.subscribe(
      (value) => {
        console.log('Room assigned');
        this.booking = value;
      }
    ))
  }

  onGenerateQuote(): void {
    if (!this.bookingId) {
      this.toastr.error('No booking selected');
      return;
    }

    this.loading = true;
    this.invoiceApiService.generateInvoicePdfByBookingId(this.bookingId).subscribe({
      next: (pdfBlob) => {
        saveAs(pdfBlob, `invoice-${this.bookingId}.pdf`);
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  checkin() {
    this.clicked = true;
    this.booking.status = BookingStatusEnum.CHECKED_IN;
  }

  getCurrentDate() {
    return new Date();
  }
}
