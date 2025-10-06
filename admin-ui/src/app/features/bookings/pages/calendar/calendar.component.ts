import {Component, OnDestroy} from '@angular/core';
import {NgxDaterangepickerBootstrapModule} from "ngx-daterangepicker-bootstrap";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import moment from 'moment';
import {PageTitleComponent} from '../../../../shared/components/page-title/page-title.component';
import {TranslatePipe} from '@ngx-translate/core';

import {CardBodyComponent, CardComponent, ColComponent, RowComponent, TableDirective,} from '@coreui/angular';
import {IconComponent} from '@coreui/icons-angular';
import {UnitApiService} from '../../../properties/services/unit-api.service';
import {PageFilterModel} from '../../../../shared/models/page-filter.model';
import {PageModel} from '../../../../shared/models/pageable/page.model';
import {UnitItemGetModel} from '../../../properties/models/unit/get/unit-item-get.model';
import {Observable, Subscription} from 'rxjs';
import {PopoverDirective} from 'ngx-bootstrap/popover';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import isoWeek from 'dayjs/plugin/isoWeek';
import weekday from 'dayjs/plugin/weekday';
import {BookingApiService} from '../../services/booking-api.service';
import {BookingItemGetModel} from '../../models/booking-item-get.model';
import {DatePipe, NgIf} from '@angular/common';
import {BookingStatusEnum} from '../../models/booking-status.enum';
import {cilCalendar, cilUser} from '@coreui/icons';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-calendar',
  imports: [
    NgxDaterangepickerBootstrapModule,
    ReactiveFormsModule,
    FormsModule,
    PageTitleComponent,
    TranslatePipe,
    CardComponent,
    CardBodyComponent,
    RowComponent,
    ColComponent,
    TableDirective,
    IconComponent,
    PopoverDirective,
    DatePipe,
    NgIf
  ],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.scss'
})
export class CalendarComponent implements OnDestroy {
  isLoading = true
  unitsLoaded = false;
  bookingsLoaded = false;
  isNavigating: boolean = false
  startDate = '2025-08-17';
  endDate = '2025-08-26';
  dateRange: Date[] = [];
  units: UnitItemGetModel[] = [];
  bookings: BookingItemGetModel[] = [] // Using the actual fetched bookings
  filteredUnits: UnitItemGetModel[] = [];
  subscriptions: Subscription[] = []
  ranges: any = {
    'Last 14 Days': [moment().subtract(13, 'days'), moment()],
    'Last 30 Days': [moment().subtract(29, 'days'), moment()],
    'This Month': [moment().startOf('month'), moment().endOf('month')],
    'Last Month': [
      moment().subtract(1, 'month').startOf('month'),
      moment().subtract(1, 'month').endOf('month')
    ]
  };

  selected: any = {
    startDate: moment().subtract(14, 'days'),
    endDate: moment().add(15, 'days')
  };
  // selected: any = {
  //   startDate: moment('2025-08-17'),
  //   endDate: moment('2025-08-26'),
  // };

  searchForm: FormGroup;

  icons = {cilUser, cilCalendar}
  // Category search functionality
  categorySearchTerm: string = '';

  constructor(private router: Router, private route: ActivatedRoute, private readonly bookingService: BookingApiService, private readonly unitApiService: UnitApiService,
              private fb: FormBuilder) {
    dayjs.extend(utc);
    dayjs.extend(isoWeek);
    dayjs.extend(weekday);
    this.searchForm = this.fb.group({
      period: [null, [Validators.required]]
    });
  }

  fetchUnits() {
    const filter: PageFilterModel = {
      page: 0,
      size: 10,
      sort: 'modifiedAt',
      sortDirection: 'desc',
      search: '' // optional
    };

    this.unitApiService.getUnitsByPage(filter).subscribe({
      next: (pageData: PageModel<UnitItemGetModel>) => {
        this.units = pageData.content;
        this.filteredUnits = [...this.units];
      },
      error: (err) => {
        console.error('Error fetching units:', err);
      },
      complete: ()=>{
        this.unitsLoaded = true;
        this.checkIfAllLoaded();
      }
    });
  }

  fetchBookings() {
    const filter: PageFilterModel = {
      page: 0,
      size: 30,
      sort: 'modifiedAt',
      sortDirection: 'desc',
      search: '',
      advancedSearchFormValue: {
        checkinDate: this.startDate,
        checkoutDate: this.endDate,
        statuses: "CONFIRMED, CHECKED_IN, CHECKED_OUT",
      }
    };

    (this.bookingService.getAllByPage(filter) as Observable<PageModel<BookingItemGetModel>>).subscribe({
      next: (pageData: PageModel<BookingItemGetModel>) => {
        this.bookings = pageData.content;
        console.log('booking===>', this.bookings)
      },
      error: (err) => {
        console.error('Error fetching bookings:', err);
      },
      complete: ()=>{
        this.bookingsLoaded = true;
        this.checkIfAllLoaded();
      }
    });
  }
  checkIfAllLoaded() {
    if (this.unitsLoaded && this.bookingsLoaded) {
      this.isLoading = false;
    }
  }
  ngOnInit() {
    this.isLoading = true
    this.generateDateRange();
    this.fetchUnits();
    this.fetchBookings()
  }

  private dateToString(date: Date): string {
    return date.toISOString().split('T')[0];
  }

  getDaysDifference(checkinDate: Date | string, checkoutDate: Date | string): number {
    const checkin = typeof checkinDate === 'string' ? new Date(checkinDate) : checkinDate;
    const checkout = typeof checkoutDate === 'string' ? new Date(checkoutDate) : checkoutDate;
    const timeDiff = checkout.getTime() - checkin.getTime();
    return Math.ceil(timeDiff / (1000 * 3600 * 24));
  }

  onCategorySearch(event: any) {
    const searchTerm = event.target.value.toLowerCase().trim();
    this.categorySearchTerm = searchTerm;

    if (searchTerm.length <= 2) {
      this.filteredUnits = [...this.units];
    } else {
      this.filteredUnits = this.units.filter(unit =>
        unit.name.toLowerCase().includes(searchTerm)
      );
    }
  }

  // Get the units to display (filtered or all)
  get displayedUnits(): UnitItemGetModel[] {
    return this.filteredUnits;
  }

  // Toggle unit collapse state
  toggleUnit(unit: UnitItemGetModel) {
    // Add collapsed property to unit if it doesn't exist
    (unit as any).collapsed = !(unit as any).collapsed;
  }

  // Check if unit is collapsed
  isUnitCollapsed(unit: UnitItemGetModel): boolean {
    return (unit as any).collapsed || false;
  }

  // Get room count for unit
  getRoomCount(unit: UnitItemGetModel): number {
    return unit.subUnits?.length || 0;
  }

  getUnitColor(unit: UnitItemGetModel): string {
    const colors = [ '#E6F0FB', '#E8F1FA', '#E5EAEE'];
    let index = this.displayedUnits.findIndex(unitItem=> unitItem.id == unit.id);
    return index >= 0 ? colors[index % colors.length] : colors[0];
  }
  getUnitTextColor(unit: UnitItemGetModel): string {
    const colors = ['#2E7D8C', '#1976D2', '#013557'];
    let index = this.displayedUnits.findIndex(unitItem=> unitItem.id == unit.id);
    return index >= 0 ? colors[index % colors.length] : colors[0];
  }

  // Updated method to handle date range picker changes
  onDateRangeSelected(value: any) {
    if (value.startDate && value.endDate) {
      this.startDate = value.startDate.format('YYYY-MM-DD');
      this.endDate = value.endDate.format('YYYY-MM-DD');
      this.isLoading = true
      this.bookingsLoaded=false
      this.generateDateRange();
      // Re-fetch bookings when date range changes
      this.fetchBookings();
    }
  }

  private generateDateRange() {
    const start = new Date(this.startDate);
    const end = new Date(this.endDate);
    this.dateRange = [];

    for (let d = new Date(start); d <= end; d.setDate(d.getDate() + 1)) {
      this.dateRange.push(new Date(d));
    }
  }

  formatDate(date: Date): string {
    return date.toLocaleDateString('en-US', {
      weekday: 'short',
      month: 'short',
      day: 'numeric'
    });
  }

  isWeekEnd(date: Date) {
    const formatedDate = this.formatDate(date)
    const split = formatedDate.split(',')
    if (split.length)
      return split[0] == "Sat" || split[0] == "Sun"
    return false
  }

  // Get bookings for a specific room using BookingItemGetModel
  getBookingsForRoom(roomId: string): BookingItemGetModel[] {
    const startDate = new Date(this.startDate);
    const endDate = new Date(this.endDate);

    return this.bookings.filter(booking => {
      if (booking.roomRef?.id !== roomId) {
        return false;
      }

      const checkInDate = new Date(booking.checkinDate);
      const checkOutDate = new Date(booking.checkoutDate);
      return checkInDate <= endDate && checkOutDate >= startDate;
    });
  }

  // Suite Familiale
  // bebb0d6f-c172-4f3e-913c-54ae1620773d
  // SF-003
  // c3d2c71b-7f2e-4f6b-8ed0-e815252aa283
  getTimelineLeftOffset(): number {
    return 250;
  }

  getBookingLeftPosition(booking: BookingItemGetModel): number {
    const checkInDate = new Date(booking.checkinDate);
    const startDate = new Date(this.startDate);
    const endDate = new Date(this.endDate);

    // Clamp check-in date to calendar range
    const effectiveCheckIn = checkInDate < startDate ? startDate : checkInDate;

    // Calculate the number of days from start date to effective check-in
    const daysDiff = Math.floor((effectiveCheckIn.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));

    // Calculate percentage position based on total date range
    const totalDays = this.dateRange.length;
    const cellWidth = 100 / totalDays; // Width of one day cell in percentage

    // If booking starts before calendar range, start from beginning (0%)
    if (checkInDate < startDate) {
      return 0;
    }

    // Otherwise start from middle of check-in date
    return (daysDiff / totalDays) * 100 + (cellWidth / 2);
  }

  getBookingWidth(booking: BookingItemGetModel): number {
    const checkInDate = new Date(booking.checkinDate);
    const checkOutDate = new Date(booking.checkoutDate);
    const startDate = new Date(this.startDate);
    const endDate = new Date(this.endDate);

    // Clamp dates to calendar range
    const effectiveCheckIn = checkInDate < startDate ? startDate : checkInDate;
    const effectiveCheckOut = checkOutDate > endDate ? endDate : checkOutDate;

    // Calculate the number of days from start date to effective check-in and check-out
    const checkInDaysDiff = Math.floor((effectiveCheckIn.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
    const checkOutDaysDiff = Math.floor((effectiveCheckOut.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));

    // Calculate percentage width based on total date range
    const totalDays = this.dateRange.length;
    const cellWidth = 100 / totalDays; // Width of one day cell in percentage

    let startPosition: number;
    let endPosition: number;

    // Handle start position
    if (checkInDate < startDate) {
      startPosition = 0; // Start from beginning if booking starts before calendar
    } else {
      startPosition = (checkInDaysDiff / totalDays) * 100 + (cellWidth / 2);
    }

    // Handle end position
    if (checkOutDate > endDate) {
      endPosition = 100; // End at 100% if booking extends beyond calendar
    } else {
      endPosition = (checkOutDaysDiff / totalDays) * 100 + (cellWidth / 2);
    }

    return endPosition - startPosition;
  }

  getUnconfirmedBookings(unit: UnitItemGetModel, date: Date): BookingItemGetModel[] {
    const dateStr = this.dateToString(date);

    return this.bookings.filter(booking => {
      if (booking.unitRef?.id !== unit.id || booking.roomRef) {
        return false;
      }

      const checkIn = new Date(booking.checkinDate);
      const checkOut = new Date(booking.checkoutDate);
      const currentDate = new Date(dateStr);

      // Check if the date falls within the booking period
      return currentDate >= checkIn && currentDate < checkOut;
    });
  }

  getBookingColor(booking: BookingItemGetModel): string {
    switch (booking.status) {
      case BookingStatusEnum.CHECKED_IN:
        return '#A8E6A3';
      case BookingStatusEnum.CONFIRMED:
        return '#FDC766';
      case BookingStatusEnum.CHECKED_OUT:
        return '#f67070';
      default:
        return '#94a3b8';
    }
  }

  getBookingBorderColor(booking: BookingItemGetModel): string {
    const bg = this.getBookingColor(booking);
    return `2px solid color-mix(in srgb, ${bg} 80%, black)`;
  }


  private getTwoWeekRangeFromPreviousMonday() {
    const today = dayjs(); // e.g., 2025-08-13

    // Step 1: Get Monday of the previous week
    const startDate = today
      .weekday(0) // Monday of this week (0 = Monday when using weekday plugin)
      .subtract(1, 'week'); // Go back one week

    // Step 2: Get Sunday two weeks later
    const endDate = startDate
      .add(2, 'week')
      .weekday(6); // Sunday (6 = Sunday with weekday plugin)

    return {
      startDate: startDate.format('YYYY-MM-DD'),
      endDate: endDate.format('YYYY-MM-DD'),
    };
  }

  routeToDetails(booking: BookingItemGetModel) {
    let bookingId: string;
    if (booking.parent) {
      bookingId = booking.parent.id;
    } else {
      bookingId = booking.id;
    }
    this.router.navigate(['/bookings/', bookingId]).then(() => console.log('Routed to details page'));

  }

  ngOnDestroy(): void {
    this.isNavigating = true;
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
