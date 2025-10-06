import { Component, OnInit, OnDestroy } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { BookingSource } from '../../models/source.enum';
import { TranslatePipe } from '@ngx-translate/core';
import {
  ButtonDirective,
  ColComponent, DropdownComponent, DropdownItemDirective, DropdownMenuDirective, DropdownToggleDirective,
  FormControlDirective,
  FormDirective,
  FormFeedbackComponent,
  FormLabelDirective,
  RowComponent,
} from '@coreui/angular';
import {NgForOf, NgIf} from '@angular/common';
import { GuestSelectComponent } from '../../../../shared/components/guest-select/guest-select.component';
import { GuestModel } from '../../models/guest-model';
import { UnitSelectComponent } from '../../../../shared/components/unit-select/unit-select.component';
import { BookingService } from '../../services/booking.service';
import { IconDirective } from '@coreui/icons-angular';
import { FeeModel } from '../../models/fee.model';
import {cilPlus, cilTrash} from '@coreui/icons';
import {
  debounceTime,
  distinctUntilChanged,
  map,
  filter,
} from 'rxjs/operators';
import { Subscription } from 'rxjs';
import {Modality} from "../../models/modality.enum";
import { NgxDaterangepickerBootstrapDirective, NgxDaterangepickerBootstrapComponent} from "ngx-daterangepicker-bootstrap";
import {ActivatedRoute} from "@angular/router";
import {UnitApiService} from "../../../units/services/unit-api.service";
import {CountryISO, NgxIntlTelInputModule, SearchCountryField} from "ngx-intl-tel-input";
import moment from "moment";


@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    TranslatePipe,
    ColComponent,
    RowComponent,
    FormFeedbackComponent,
    FormLabelDirective,
    FormControlDirective,
    FormDirective,
    ButtonDirective,
    NgForOf,
    GuestSelectComponent,
    UnitSelectComponent,
    IconDirective,
    FormsModule,
    NgIf,
    NgxDaterangepickerBootstrapDirective, NgxDaterangepickerBootstrapComponent, NgIf,
    DropdownComponent, ButtonDirective, DropdownToggleDirective, DropdownMenuDirective, DropdownItemDirective, NgxIntlTelInputModule
  ],
})
export class BookingComponent implements OnInit, OnDestroy {
  icons = {
    cilTrash,
    cilPlus
  };
  bookingForm!: FormGroup;

  total = 0;

  bookingSources = Object.values(BookingSource);
  modalities = Object.values(Modality);

  private formSub!: Subscription;

  isNavigating = false;

  constructor(
    private fb: FormBuilder,
    private bookingApiService: BookingService,
    private unitApiService: UnitApiService,
    private route: ActivatedRoute
  ) {}

  get fees(): FormArray {
    return this.bookingForm.get('fees') as FormArray;
  }

  ngOnInit(): void {
    this.bookingForm = this.fb.group({
      guest: [null],
      guestName: ['', Validators.required],
      guestEmail: ['', [Validators.required, Validators.email]],
      guestPhone: [''],
      source: ['', Validators.required],
      adults: ['', [Validators.required, Validators.min(1)]],
      children: [''],
      unit: [null, Validators.required],
      dateRange: [null, Validators.required],
      baseCharge: ['', Validators.required],
      feeAmount: [''],
      fees: this.fb.array([]),
    });

    this.route.queryParams.subscribe(params => {
      const startDate = params['startDate'] ? moment(params['startDate']) : null;
      const endDate = params['endDate'] ? moment(params['endDate']) : null;
      const unitId = params['unitId'];

      if (startDate && endDate) {
        this.bookingForm.get('dateRange')?.setValue({
          startDate,
          endDate
        });
      }


      if (unitId) {
        this.unitApiService.getUnitById(unitId).subscribe({
          next: (unit) => {
            this.bookingForm.get('unit')?.setValue(unit);
          },
          error: (err) => {
            console.error('Failed to fetch unit by ID:', err);
          }
        });
      }
    });

    this.listenToFormChanges();
  }

  private listenToFormChanges(): void {
    this.formSub = this.bookingForm.valueChanges
      .pipe(
        debounceTime(300),
        map((form) => ({
          unit: form.unit,
          dateRange: form.dateRange,
          adults: form.adults,
          children: form.children,
        })),
        distinctUntilChanged((a, b) => JSON.stringify(a) === JSON.stringify(b)),
        filter(
          (val) =>
            val.unit &&
            val.dateRange &&
            val.dateRange.startDate &&
            val.dateRange.endDate &&
            val.adults !== null &&
            val.adults !== ''
        )
      )
      .subscribe(() => {
        this.fetchBaseRateIfReady();
      });
  }

  fetchBaseRateIfReady(): void {
    const { unit, dateRange, adults, children } = this.bookingForm.value;
    if (!dateRange?.startDate || !dateRange?.endDate) {
      return;
    }
    const payload = {
      unitId: unit,
      arrive: dateRange.startDate.toDate(),
      depart: dateRange.endDate.toDate(),
      adults: Number(adults),
      children: Number(children || 0),
    };

    this.bookingApiService.getBaseRate(payload).subscribe({
      next: ({ baseRate, fees }) => {
        if (this.bookingForm.get('baseCharge')?.value !== baseRate) {
          this.bookingForm.get('baseCharge')?.patchValue(baseRate);
        }

        const feeFormGroups = (fees ?? []).map((fee) =>
          this.createFeeFormGroup(fee)
        );
        const feeArray = this.fb.array(feeFormGroups);

        this.bookingForm.setControl('fees', feeArray);

        this.calculateTotal();
      },
      error: (err) => {
        console.error('Failed to fetch base rate:', err);
      },
    });
  }


  calculateTotal(): void {
    const baseCharge = parseFloat(
      this.bookingForm.get('baseCharge')?.value || 0
    );
    this.total = baseCharge;
  }

  onSave(type: 'booking' | 'quote'): void {
    if (!this.validateDates()) {
      return;
    }

    if (this.bookingForm.invalid) {
      this.bookingForm.markAllAsTouched();
      return;
    }

    const raw = this.bookingForm.getRawValue();
    const payload = {
      ...raw,
      dateRange: {
        startDate: raw.dateRange?.startDate?.format('YYYY-MM-DD'),
        endDate: raw.dateRange?.endDate?.format('YYYY-MM-DD')
      },
      guestPhone: raw.guestPhone?.e164Number || '',
      total: this.total,
      status: type === 'quote' ? 'QUOTE' : 'CONFIRMED'
    };

    console.log(`Saving as ${type}:`, payload);
    // TODO: Call API service accordingly
  }

  private validateDates(): boolean {
    const dateRange = this.bookingForm.get('dateRange')?.value;

    if (!dateRange?.startDate || !dateRange?.endDate) {
      alert('Please select check-in and check-out dates');
      return false;
    }

    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const startDate = dateRange.startDate.toDate();
    const endDate = dateRange.endDate.toDate();

    if (startDate < today) {
      alert('Check-in date cannot be in the past');
      return false;
    }

    if (endDate <= startDate) {
      alert('Check-out date must be after check-in date');
      return false;
    }

    return true;
  }


  fillGuestFields(guest: GuestModel | null): void {
    if (guest) {
      this.bookingForm.patchValue({
        guestName: guest.name,
        guestEmail: guest.email,
        guestPhone: guest.phone,
      });

      this.bookingForm.get('guestName')?.disable();
      this.bookingForm.get('guestEmail')?.disable();
      this.bookingForm.get('guestPhone')?.disable();
    } else {
      this.bookingForm.get('guestName')?.enable();
      this.bookingForm.get('guestEmail')?.enable();
      this.bookingForm.get('guestPhone')?.enable();

      this.bookingForm.patchValue({
        guestName: '',
        guestEmail: '',
        guestPhone: '',
      });
    }
  }

  private createFeeFormGroup(fee: FeeModel): FormGroup {
    return this.fb.group({
      feeName: [fee.feeName, Validators.required],
      feeRate: [fee.feeRate, [Validators.required, Validators.min(1)]],
      modality: [fee.modality as Modality, Validators.required],
      quantity: [fee.quantity, [Validators.required, Validators.min(1)]],
    });
  }

  removeFee(index: number): void {
    this.fees.removeAt(index);
    this.calculateTotal();
  }

  ngOnDestroy(): void {
    if (this.formSub) this.formSub.unsubscribe();
  }

  addFee(): void {
    const newFee = this.fb.group({
      feeName: ['', Validators.required],
      feeRate: ['', [Validators.required, Validators.min(1)]],
      modality: ['', Validators.required],
      quantity: ['', [Validators.required, Validators.min(1)]],
    });

    this.fees.push(newFee);
  }

  protected readonly SearchCountryField = SearchCountryField;
  protected readonly CountryISO = CountryISO;
}
