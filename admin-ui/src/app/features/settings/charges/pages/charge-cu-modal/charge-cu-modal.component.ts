import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {TranslatePipe, TranslateService} from "@ngx-translate/core";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {ToastrService} from 'ngx-toastr';
import {
  ButtonDirective,
  ColComponent,
  DropdownComponent,
  DropdownItemDirective,
  DropdownMenuDirective,
  DropdownToggleDirective,
  FormCheckComponent,
  FormCheckInputDirective,
  FormCheckLabelDirective,
  FormControlDirective,
  FormDirective,
  FormFeedbackComponent,
  FormLabelDirective,
  InputGroupComponent,
  RowComponent
} from '@coreui/angular';
import {
  PostingAccountSelectComponent
} from '../../../../../shared/components/posting-account-select/posting-account-select.component';
import {ChargeApiService} from '../../services/charge-api.service';
import {NgxIntlTelInputModule} from 'ngx-intl-tel-input';
import {JsonPipe} from '@angular/common';

@Component({
  selector: 'app-charge-cu-modal',
  imports: [
    TranslatePipe,
    FormDirective,
    FormsModule,
    ReactiveFormsModule,
    ColComponent,
    FormControlDirective,
    FormFeedbackComponent,
    FormLabelDirective,
    RowComponent,
    ButtonDirective,
    DropdownComponent,
    DropdownItemDirective,
    DropdownMenuDirective,
    DropdownToggleDirective,
    InputGroupComponent,
    PostingAccountSelectComponent,
    FormCheckComponent,
    FormCheckInputDirective,
    FormCheckLabelDirective,
    NgxIntlTelInputModule,
    JsonPipe
  ],
  templateUrl: './charge-cu-modal.component.html',
  standalone: true,
  styleUrl: './charge-cu-modal.component.scss'
})
export class ChargeCuModalComponent implements OnInit, OnDestroy {
  private component = '[ChargeCuModalComponent]: ';
  formErrors: any = {};
  type!: any;
  chargeToEdit!: any;
  chargeForm: FormGroup;
  @Output() actionConfirmed = new EventEmitter<any>();
  private readonly subscriptions: Subscription[] = [];

  constructor(private fb: FormBuilder,
              private readonly modalRef: BsModalRef,
              private readonly chargeApiService: ChargeApiService,
              private readonly translateService: TranslateService,
              private readonly toastrService: ToastrService) {
    this.chargeForm = this.fb.group({
      name: [null, Validators.required],
      code: [null, Validators.required],
      description: [null, [Validators.maxLength(255)]],
      enabled: [true, [Validators.required]],
      amount: [null, [Validators.required]],
      chargingBasis: ['PER_PERSON', Validators.required],
      postingTiming: ['NIGHTLY', Validators.required],
      postingAccount: [null, Validators.required],
      packageAllowed: [true],
      excludePrice: [false],
      extraAllowed: [false]
    })

    this.subscriptions.push(this.chargeForm.get('packageAllowed')?.valueChanges.subscribe(value => {
      if (!value) {
        this.chargeForm.patchValue({
          excludePrice: false
        })
      }
    }) as Subscription);

  }

  ngOnInit(): void {
    if (this.chargeToEdit) {
      this.chargeForm.patchValue(this.chargeToEdit);
      this.chargeForm.get('code')?.disable();
    }
    if (this.type) {
      this.chargeForm.patchValue({type: this.type})
    }
  }

  submit() {
    let payload: any = {
      name: this.chargeForm.value.name,
      type: this.chargeForm.value.type,
      description: this.chargeForm.value.description,
      enabled: this.chargeForm.value.enabled,
      amount: this.chargeForm.value.amount,
      chargingBasis: this.chargeForm.value.chargingBasis,
      postingTiming: this.chargeForm.value.postingTiming,
      postingAccount: {
        id: this.chargeForm.value.postingAccount.id,
        name: this.chargeForm.value.postingAccount.name
      },
      packageAllowed: this.chargeForm.value.packageAllowed,
      extraAllowed: this.chargeForm.value.extraAllowed,
      excludePrice: this.chargeForm.value.packageAllowed ? this.chargeForm.value.excludePrice : false
    }


    if (this.chargeToEdit) {
      this.subscriptions.push(this.chargeApiService.patchById(this.chargeToEdit.id, payload).subscribe({
        next: (data: any) => {
          console.info(this.component.concat('Charge created updated successfully. API response is:'), data);
          this.actionConfirmed.emit();
          this.closeModal();
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred during charge creation'), err);
          this.handleErrors(err);
        }
      }))
    } else {
      payload.code = this.chargeForm.value.code;
      this.subscriptions.push(this.chargeApiService.post(payload).subscribe({
        next: (data: any) => {
          console.info(this.component.concat('Charge created successfully. API response is:'), data);
          this.actionConfirmed.emit();
          this.closeModal();
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred during charge creation'), err);
          this.handleErrors(err);
        }
      }))
    }

  }

  closeModal() {
    this.modalRef.hide();
    this.chargeForm.reset();
  }

  private handleErrors(err: any) {
    if (err.status === 400) {
      const errors = err?.error?.errors;
      if (errors?.code && Array.isArray(errors.code)) {
        const hasUniqueCode = errors.code.some(
          (e: any) => e?.code === 'UniqueChargeCode'
        );
        if (hasUniqueCode) {
          this.formErrors.code = {...this.formErrors.code, unique: true}
        }
      }
    }
  }

  setChargingBasis(value: string) {
    this.chargeForm.patchValue({
      chargingBasis: value
    })
  }

  setPostingTiming(value: string) {
    this.chargeForm.patchValue({
      postingTiming: value
    })
  }

  ngOnDestroy(): void {
    console.debug(this.component.concat('Unsubscribing all subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }


}
