import { Component, OnDestroy } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { UnitApiService } from '../../services/unit-api.service';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { ActivatedRoute, Router } from '@angular/router';
import dayjs from 'dayjs';
import { PageTitleComponent } from '../../../../shared/components/page-title/page-title.component';
import {
  ButtonDirective,
  CardBodyComponent,
  CardComponent,
  CardHeaderComponent,
  ColComponent,
  FormLabelDirective,
  RowComponent,
  SpinnerComponent
} from '@coreui/angular';
import { BookingApiService } from '../../services/booking-api.service';
import { NgxDaterangepickerBootstrapModule } from 'ngx-daterangepicker-bootstrap';
import { IconDirective } from '@coreui/icons-angular';
import { cilBaby } from '@coreui/icons';
import { InventoryPostModel } from '../../models/inventory/inventory-post.model';
import { InventoryItemGetModel } from '../../models/inventory/inventory-item-get.model';
import { UnitInventoryComponent } from './unit-inventory/unit-inventory.component';
import { BookingPostModel } from '../../models/booking/booking-post.model';
import { SummaryElementComponent } from './summary-element/summary-element.component';
import { BookingGetModel } from '../../models/booking/booking-get.model';
import utc from 'dayjs/plugin/utc';
import { BookingItemPostModel } from '../../models/booking/booking-item-post.model';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-booking-create',
  imports: [
    PageTitleComponent,
    TranslatePipe,
    RowComponent,
    ColComponent,
    CardComponent,
    CardHeaderComponent,
    CardBodyComponent,
    ReactiveFormsModule,
    FormLabelDirective,
    NgxDaterangepickerBootstrapModule,
    ButtonDirective,
    IconDirective,
    UnitInventoryComponent,
    SummaryElementComponent,
    SpinnerComponent,
    DecimalPipe,
  ],
  templateUrl: './booking-create.component.html',
  standalone: true,
  styleUrl: './booking-create.component.scss'
})
export class BookingCreateComponent implements OnDestroy {

  private component = '[BookingCreateComponent]: ';
  icons = { cilBaby };
  ageOptions: number[] = Array.from({ length: 13 }, (_, i) => i);
  bookingId!: string;
  booking!: BookingGetModel | null;
  minDate = dayjs();
  searchForm!: FormGroup;
  inventoryList: InventoryItemGetModel[] = [];
  isSearching = false;
  private subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private bookingApiService: BookingApiService,
    private readonly toastrService: ToastrService,
    private readonly unitApiService: UnitApiService,
    private readonly translateService: TranslateService,
  ) {
    dayjs.extend(utc);
    this.searchForm = this.fb.group({
      period: [null, [Validators.required]],
      adults: [1, [Validators.required]],
      children: this.fb.array([])
    });

    this.subscriptions.push(
      this.activatedRoute.paramMap.subscribe(value => {
        this.bookingId = value.get('bookingId') as string;
        if (this.bookingId) {
          this.retrieveBooking();
        } else {
          console.log('New empty booking');
        }
      })
    );
  }

  incrementAdults() {
    this.searchForm.patchValue({
      adults: this.searchForm.value.adults + 1
    });
  }

  decrementAdults() {
    if (this.searchForm.value.adults > 1) {
      this.searchForm.patchValue({
        adults: this.searchForm.value.adults - 1
      });
    }
  }

  get children(): FormArray {
    return this.searchForm.get('children') as FormArray;
  }

  addChild(): void {
    this.children.push(this.createChild());
  }

  removeChild(index?: number): void {
    if (this.children.length === 0) return;
    if (index === undefined || index < 0 || index >= this.children.length) {
      this.children.removeAt(this.children.length - 1);
    } else {
      this.children.removeAt(index);
    }
  }

  getChildrenCount(): number {
    return this.children.length;
  }

  getNights(): number {
    if (this.searchForm.value.period) {
      return dayjs
        .utc(this.searchForm.value.period.endDate)
        .local()
        .diff(dayjs.utc(this.searchForm.value.period.startDate).local(), 'day');
    }
    return 0;
  }

  /**
   * Add selected unit to booking (new or existing)
   */
  addToBooking(unitInventory: InventoryItemGetModel) {
    const checkinDate = dayjs.utc(this.searchForm.value.period.startDate).local().format('YYYY-MM-DD');
    const checkoutDate = dayjs.utc(this.searchForm.value.period.endDate).local().format('YYYY-MM-DD');

    // Updated fields
    const nightlyRate = unitInventory.avgRateNight || 0;
    const totalRate = unitInventory.totalBookingRate || 0;

    const unitRef = {
      id: unitInventory.id,
      name: unitInventory.name
    };

    const occupancy = {
      adults: this.searchForm.value.adults,
      children: this.groupChildrenByAge()
    };

    if (this.booking) {
      console.log(`${this.component} Add selected room "${unitInventory.name}" to existing booking ...`);

      const payload: BookingItemPostModel = {
        checkinDate,
        checkoutDate,
        nightlyRate,
        vatPercentage: 0, // no VAT info provided now
        unitRef,
        occupancy
      };

      this.createBookingItem(payload);
    } else {
      const payload: BookingPostModel = {
        items: [
          {
            checkinDate,
            checkoutDate,
            nightlyRate,
            vatPercentage: 0,
            unitRef,
            occupancy
          }
        ]
      };

      this.createBooking(payload);
    }
  }

  /**
   * Fetch unit availability and pricing
   */
  searchAvailability() {
    this.isSearching = true;

    const payload: InventoryPostModel = {
      checkinDate: dayjs.utc(this.searchForm.value.period.startDate).local().format('YYYY-MM-DD'),
      checkoutDate: dayjs.utc(this.searchForm.value.period.endDate).local().format('YYYY-MM-DD'),
      guests: {
        adults: this.searchForm.value.adults,
        children: this.groupChildrenByAge()
      }
    };

    this.subscriptions.push(
      this.unitApiService.getInventory(payload).subscribe({
        next: (data) => {
          console.info(this.component.concat('Inventory retrieved successfully. API response:'), data);
          this.inventoryList = data.content;
          this.isSearching = false;
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred when fetching unit inventory. API error response:'), err);
          this.isSearching = false;
        }
      })
    );
  }

  removeBooking() {
    this.subscriptions.push(
      this.bookingApiService.deleteById((this.booking as BookingGetModel).id).subscribe({
        next: () => {
          this.router.navigate(['/bookings/new']).then(() => console.log('Routing to new booking page'));
          this.booking = null;
        },
        error: (err) => {
          console.error('An error occurred when deleting booking with Id:', (this.booking as BookingGetModel).id, err);
        }
      })
    );
  }

  removeBookingItem(bookingItem: BookingGetModel) {
    if (!bookingItem?.id) return;
    if (this.booking) {
      this.booking.items = (this.booking?.items ?? []).filter(item => item.id !== bookingItem.id);
    }
  }

  /**
   * Compute total booking amount (simplified)
   */
  getBookingTotal(): number {
    if (!this.booking) return 0;

    let totalAmount = 0;

    this.booking.items.forEach(item => {
      // Handle the case where pricing is under `stay.totalPrice`
      if (item.stay?.totalPrice?.amountInclTax) {
        totalAmount += item.stay.totalPrice.amountInclTax;
      }
      // fallback in case structure differs
      else if ((item as any).totalBookingRate) {
        totalAmount += (item as any).totalBookingRate;
      }
    });

    return totalAmount;
  }


  goToConfirmPage() {
    const url = '/bookings/' + this.booking?.id + '/confirm';
    this.router.navigate([url]).then(() => console.log(this.component.concat('Routed to confirmation page')));
  }

  private createBooking(payload: BookingPostModel) {
    this.subscriptions.push(
      this.bookingApiService.post(payload).subscribe({
        next: (data) => {
          console.info(this.component.concat('Booking created successfully. API response is:'), data);
          this.booking = data;
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred when creating the booking. Api error response is:'), err);
        }
      })
    );
  }

  private createBookingItem(payload: BookingItemPostModel) {
    this.subscriptions.push(
      this.bookingApiService.postById(payload, this.booking?.id as string).subscribe({
        next: (data) => {
          console.info(this.component.concat('Booking item created successfully. API response is:'), data);
          this.booking?.items.push(data);
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred when creating the booking item. Api error response is:'), err);
        }
      })
    );
  }

  private groupChildrenByAge(): { age: number; quantity: number }[] {
    const children = this.searchForm.value.children || [];
    const groupedMap = new Map<number, number>();

    children.forEach((child: { age: number; quantity: number }) => {
      if (child.age != null) {
        const currentQuantity = groupedMap.get(child.age) || 0;
        groupedMap.set(child.age, currentQuantity + (child.quantity || 0));
      }
    });

    return Array.from(groupedMap.entries()).map(([age, quantity]) => ({
      age,
      quantity
    }));
  }

  private createChild(): FormGroup {
    return this.fb.group({
      quantity: [1, [Validators.required]],
      age: [0, [Validators.required]]
    });
  }

  private retrieveBooking() {
    this.subscriptions.push(
      this.bookingApiService.getById(this.bookingId).subscribe({
        next: (data) => {
          this.booking = data;
          console.log('Your booking data is:', this.booking);
        },
        error: (err) => {
          console.error('An error occurred when retrieving the booking by Id', err);
        }
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
