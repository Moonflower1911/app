import { Component, OnDestroy } from '@angular/core';
import {
  FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators
} from '@angular/forms';
import {
  ButtonDirective, ColComponent, FormControlDirective, FormDirective,
  FormFeedbackComponent, FormLabelDirective, RowComponent,
} from '@coreui/angular';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import {ActivatedRoute, Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import { combineLatest, Subscription } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { GuestService } from '../../services/guest.service';
import { GuestItemGetModel } from '../../models/guest-item-get.model';
import { GuestItemPatchModel } from '../../models/guest-patch.model';
import { CountrySelectComponent } from '../../../../shared/components/country-select/country-select.component';
import { NgxIntlTelInputModule, CountryISO, SearchCountryField } from 'ngx-intl-tel-input';
import { noNumbersValidator } from '../../../../shared/validators/no-number.validator';
import {TooltipDirective} from "ngx-bootstrap/tooltip";
import { NgxDaterangepickerBootstrapDirective} from 'ngx-daterangepicker-bootstrap';
import moment from 'moment';
import {NgIf} from "@angular/common";
import dayjs from "dayjs";

@Component({
  selector: 'app-guest-edit',
  standalone: true,
  imports: [
    RowComponent, ColComponent, FormControlDirective, FormDirective,
    FormFeedbackComponent, FormLabelDirective, FormsModule,
    ReactiveFormsModule, TranslatePipe, CountrySelectComponent,
    NgxIntlTelInputModule,
    ButtonDirective, RouterOutlet, TooltipDirective, RouterLink, RouterLinkActive,
    NgxDaterangepickerBootstrapDirective, NgIf
  ],
  templateUrl: './guest-edit.component.html',
  styleUrl: './guest-edit.component.scss'
})
export class GuestEditComponent implements OnDestroy {

  guestForm: FormGroup;
  guestId!: string;
  guest!: GuestItemGetModel;
  isLoading = true;
  private subscriptions: Subscription[] = [];

  protected readonly SearchCountryField = SearchCountryField;
  protected readonly CountryISO = CountryISO;

  datePickerLocale = {
    format: 'DD-MM-YYYY',
    applyLabel: this.translateService.instant('commons.apply'),
    cancelLabel: this.translateService.instant('commons.cancel'),
  };
  
  birthDate: { startDate: moment.Moment | null, endDate: moment.Moment | null } = { startDate: null, endDate: null };
  isNavigating = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly guestService: GuestService,
    private readonly activatedRoute: ActivatedRoute,
    private readonly toastrService: ToastrService,
    private readonly translateService: TranslateService,
    private readonly router: Router
  ) {
    this.guestForm = this.fb.group({
      firstName: [null, [Validators.required, noNumbersValidator()]],
      lastName: [null, [Validators.required, noNumbersValidator()]],
      birthDate: [null],
      address: this.fb.group({
        street1: [null],
        street2: [null],
        postCode: [null],
        city: [null, noNumbersValidator()],
        country: [null]
      }),
      contact: this.fb.group({
        email: [null, [Validators.required, Validators.email]],
        mobile: [null]
      }),
    });

    this.subscriptions.push(
      combineLatest(
        this.activatedRoute.pathFromRoot.map(route => route.paramMap)
      ).subscribe(paramMaps => {
        const guestId = paramMaps
          .map(paramMap => paramMap.get('id'))
          .find(id => id !== null);
        if (guestId) {
          this.guestId = guestId;
          this.retrieveGuest();
        }
      })
    );
  }

  private retrieveGuest() {
    this.subscriptions.push(
      this.guestService.getGuestById(this.guestId).subscribe({
        next: (data) => {

          console.log('Guest loaded successfully:', data);
          this.guest = data;
          if (this.guest.birthDate) {
            const parsedDate = moment(this.guest.birthDate, 'YYYY-MM-DD');
            this.birthDate = { startDate: parsedDate, endDate: parsedDate };
            this.guestForm.get('birthDate')?.setValue(parsedDate.toDate());
          }

          this.guestForm.patchValue({
            firstName: this.guest.firstName,
            lastName: this.guest.lastName,
            address: this.guest.address,
            contact: this.guest.contact
          });

          this.isLoading = false;
        },
        error: (err) => {
          console.error('Failed to load guest:', err);
          this.isLoading = false;
        }
      })
    );
  }

  onDateChange(event: any) {
    console.log('Date change event:', event);
    if (event.startDate) {
      this.birthDate = event;
      this.guestForm.get('birthDate')?.setValue(event.startDate?.toDate());
    } else {
      this.birthDate = { startDate: null, endDate: null };
      this.guestForm.get('birthDate')?.setValue(null);
    }
  }

  submit() {
    if (this.guestForm.invalid) {
      this.toastrService.warning(this.translateService.instant('commons.form.validation-errors'));
      return;
    }

    const formValue = this.guestForm.value;
    const birthDate = this.birthDate.startDate ? this.birthDate.startDate.format('YYYY-MM-DD') : null;

    const payload: GuestItemPatchModel = {
      ...formValue,
      birthDate: birthDate,
      contact: {
        email: formValue.contact.email,
        mobile: formValue.contact.mobile?.e164Number
      }
    };

    console.log('PATCH Payload being sent:', payload);

    this.subscriptions.push(
      this.guestService.patchGuestById(payload, this.guestId).subscribe({
        next: () => {
          console.log('Guest updated successfully.');
          this.toastrService.info(
            this.translateService.instant('guests.edit-guest.notifications.info.message'),
            this.translateService.instant('guests.edit-guest.notifications.info.title')
          );
        },
        error: (err) => {
          console.error('Failed to update guest:', err);
          this.toastrService.error(
            this.translateService.instant('guests.edit-guest.notifications.error.message'),
            this.translateService.instant('guests.edit-guest.notifications.error.title')
          );
        }
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
    this.isNavigating = true;
  }
}
