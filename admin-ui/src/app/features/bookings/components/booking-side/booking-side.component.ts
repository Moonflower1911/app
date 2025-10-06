import {Component, effect, EventEmitter, Input, OnDestroy, OnInit, Output, signal} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {debounceTime, distinctUntilChanged, skip, Subject, Subscription, takeUntil} from 'rxjs';
import {BookingApiService} from '../../services/booking-api.service';
import {
  ButtonDirective,
  CardBodyComponent,
  CardComponent,
  CardHeaderComponent,
  ColComponent,
  FormControlDirective,
  FormFeedbackComponent,
  FormLabelDirective,
  RowComponent
} from '@coreui/angular';
import {CountryISO, NgxIntlTelInputModule, SearchCountryField} from 'ngx-intl-tel-input';
import {TranslatePipe} from '@ngx-translate/core';
import {BookingGetModel} from '../../models/booking/booking-get.model';

@Component({
  selector: 'app-booking-side',
  imports: [
    CardBodyComponent,
    CardComponent,
    CardHeaderComponent,
    ColComponent,
    FormControlDirective,
    FormFeedbackComponent,
    FormLabelDirective,
    FormsModule,
    NgxIntlTelInputModule,
    ReactiveFormsModule,
    RowComponent,
    TranslatePipe,
    ButtonDirective
  ],
  templateUrl: './booking-side.component.html',
  styleUrl: './booking-side.component.scss'
})
export class BookingSideComponent implements OnInit, OnDestroy {
  private component = '[BookingSideComponent]: ';
  @Input()
  editMode = false;
  @Input()
  displayEditButton = true;
  protected readonly SearchCountryField = SearchCountryField;
  protected readonly CountryISO = CountryISO;
  @Input()
  bookingId!: string | null;
  @Input()
  contact!: any;
  @Input()
  notes!: string | null;
  private firstLoad = true;
  formDataSignal;
  bookingForm: FormGroup;
  @Output()
  contactUpdated = new EventEmitter<BookingGetModel>();
  private lastSubmittedValue: any = null;
  private subscriptions: Subscription [] = [];
  private destroy$ = new Subject<void>();

  constructor(private readonly fb: FormBuilder,
              private bookingApiService: BookingApiService) {
    this.bookingForm = this.fb.group({
      notes: [null],
      contact: this.fb.group({
        name: [null, [Validators.required]],
        email: [null, [Validators.required]],
        mobile: [null]
      })
    });
    this.formDataSignal = signal(this.bookingForm.value)
    this.listenToFormChanges();
  }

  ngOnInit(): void {
    this.bookingForm.patchValue({
      contact: this.contact,
      notes: this.notes
    })
  }

  switchEditMode() {
    this.editMode = !this.editMode;
  }

  private onSubmit(formValue: any) {
    if (this.firstLoad) {
      this.firstLoad = !this.firstLoad;
    } else {
      let payload = {
        contact: {
          name: formValue.contact.name,
          email: formValue.contact.email,
          mobile: formValue.contact.mobile?.e164Number
        },
        notes: formValue.notes
      }
      this.subscriptions.push(this.bookingApiService.patchById(this.bookingId as string, payload).subscribe({
        next: (data) => {
          console.info(this.component.concat('Booking with id:'), this.bookingId, 'updated successfully. API response:', data);
          this.contactUpdated.emit(data);
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred when updating the booking with id:'),
            this.bookingId, 'API error response:', err);
        }
      }))
    }
  }

  private listenToFormChanges() {
    this.bookingForm.valueChanges
      .pipe(
        skip(2),
        debounceTime(400), // wait 0.4s after user stops typing
        distinctUntilChanged((prev, curr) => JSON.stringify(prev) === JSON.stringify(curr)), // avoid duplicate consecutive calls
        takeUntil(this.destroy$)
      )
      .subscribe(value => {
        this.formDataSignal.set(value);
      });
    // Watch for signal changes and send updates to backend
    effect(() => {
      const latestValue = this.formDataSignal();
      // If form value exists and it's different from last submitted, send it
      if (latestValue && JSON.stringify(latestValue) !== JSON.stringify(this.lastSubmittedValue)) {
        this.lastSubmittedValue = latestValue;
        this.onSubmit(latestValue);
      }
    });
  }


  ngOnDestroy(): void {
    console.log(this.component.concat('Cleaning subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
    this.destroy$.next();
    this.destroy$.complete();
  }


}
