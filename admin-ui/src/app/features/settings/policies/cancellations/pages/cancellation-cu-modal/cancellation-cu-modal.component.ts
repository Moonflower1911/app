import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
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
  InputGroupTextDirective,
  RowComponent
} from '@coreui/angular';
import {
  PostingAccountSelectComponent
} from '../../../../../../shared/components/posting-account-select/posting-account-select.component';
import {CancellationPolicyApiService} from '../../services/cancellation-policy-api.service';
import {NgxIntlTelInputModule} from 'ngx-intl-tel-input';

@Component({
  selector: 'app-cancellation-cu-modal',
  imports: [
    TranslatePipe,
    FormDirective,
    ReactiveFormsModule,
    ButtonDirective,
    ColComponent,
    FormCheckComponent,
    FormCheckInputDirective,
    FormCheckLabelDirective,
    RowComponent,
    FormControlDirective,
    FormFeedbackComponent,
    FormLabelDirective,
    PostingAccountSelectComponent,
    DropdownComponent,
    DropdownItemDirective,
    DropdownMenuDirective,
    DropdownToggleDirective,
    InputGroupComponent,
    InputGroupTextDirective,
    NgxIntlTelInputModule
  ],
  templateUrl: './cancellation-cu-modal.component.html',
  standalone: true,
  styleUrl: './cancellation-cu-modal.component.scss'
})
export class CancellationCuModalComponent implements OnInit, OnDestroy {
  private component = '[CancellationCuModalComponent]: ';
  formErrors: any = {};
  type!: any;
  cancellationToEdit!: any;
  cancellationForm: FormGroup;
  @Output() actionConfirmed = new EventEmitter<any>();
  private readonly subscriptions: Subscription[] = [];

  constructor(private fb: FormBuilder,
              private readonly modalRef: BsModalRef,
              private readonly cancellationPolicyApiService: CancellationPolicyApiService,
              private readonly translateService: TranslateService,
              private readonly toastrService: ToastrService) {
    this.cancellationForm = this.fb.group({
      name: [null, Validators.required],
      code: [null, Validators.required],
      description: [null],
      type: ['FLAT', Validators.required],
      hours: [null, Validators.required],
      timingType: ['AFTER_BOOKING', [Validators.required]],
      amount: [null, Validators.required],
      postingAccount: [null, Validators.required],
      enabled: [true, Validators.required]
    })
  }

  ngOnInit(): void {
    if (this.cancellationToEdit) {
      this.cancellationForm.patchValue(this.cancellationToEdit);
      this.cancellationForm.get('code')?.disable();
    }
  }


  submit() {
    let payload: any = {
      name: this.cancellationForm.value.name,
      description: this.cancellationForm.value.description,
      type: this.cancellationForm.value.type,
      hours: this.cancellationForm.value.hours,
      timingType: this.cancellationForm.value.timingType,
      amount: this.cancellationForm.value.amount,
      postingAccount: {
        id: this.cancellationForm.value.postingAccount.id,
        name: this.cancellationForm.value.postingAccount.name
      },
      enabled: this.cancellationForm.value.enabled
    }
    if (this.cancellationToEdit) {
      this.subscriptions.push(this.cancellationPolicyApiService.patchById(this.cancellationToEdit.id, payload).subscribe({
        next: (data: any) => {
          console.info(this.component.concat('Cancellation policy created updated successfully. API response is:'), data);
          this.actionConfirmed.emit();
          this.closeModal();
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred during cancellation policy creation'), err);

        }
      }))
    } else {
      payload.code = this.cancellationForm.value.code;
      this.subscriptions.push(this.cancellationPolicyApiService.post(payload).subscribe({
        next: (data: any) => {
          console.info(this.component.concat('Cancellation policy created successfully. API response is:'), data);
          this.actionConfirmed.emit();
          this.closeModal();
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred during cancellation policy creation'), err);
          this.handleErrors(err);
        }
      }))
    }
  }

  closeModal() {
    this.modalRef.hide();
    this.cancellationForm.reset();
  }

  setTimingType(value: string) {
    this.cancellationForm.patchValue({
      timingType: value
    });
  }

  setType(value: string) {
    this.cancellationForm.patchValue({
      type: value
    });
  }

  private handleErrors(err: any) {
    if (err.status === 400) {
      const errors = err?.error?.errors;
      if (errors?.code && Array.isArray(errors.code)) {
        const hasUniqueCode = errors.code.some(
          (e: any) => e?.code === 'UniqueCancellationPolicyCode'
        );
        if (hasUniqueCode) {
          this.formErrors.code = {...this.formErrors.code, unique: true}
        }
      }
    }
  }

  ngOnDestroy(): void {
    console.log(this.component.concat('Unsubscribing from all subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }


}
