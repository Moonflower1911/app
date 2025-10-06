import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';

import {
  ButtonDirective,
  ColComponent,
  FormCheckComponent,
  FormCheckInputDirective,
  FormCheckLabelDirective,
  FormControlDirective,
  FormDirective,
  FormFeedbackComponent,
  FormLabelDirective,
  RowComponent
} from '@coreui/angular';
import {CommonModule} from '@angular/common';
import {Subscription} from 'rxjs';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {ToastrService} from 'ngx-toastr';
import {PostingAccountApiService} from '../../services/posting-account-api.service';
import {
  LedgerGroupSelectComponent
} from '../../../../../shared/components/ledger-group-select/ledger-group-select.component';
import {VatRuleSelectComponent} from '../../../../../shared/components/vat-rule-select/vat-rule-select.component';

@Component({
  selector: 'app-posting-account-cu-modal',
  templateUrl: './posting-account-cu-modal.component.html',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormDirective,
    ButtonDirective,
    ColComponent,
    RowComponent,
    TranslatePipe,
    FormsModule,
    FormControlDirective,
    FormFeedbackComponent,
    FormLabelDirective,
    FormCheckComponent,
    FormCheckInputDirective,
    FormCheckLabelDirective,
    LedgerGroupSelectComponent,
    VatRuleSelectComponent
  ],
  styleUrls: ['./posting-account-cu-modal.component.scss']
})
export class PostingAccountCuModalComponent implements OnInit, OnDestroy {

  private component = '[PostingAccountCuModalComponent]: ';
  disableSubLedger = true;
  formErrors: any = {};
  postingAccountToEdit!: any;
  postingAccountForm: FormGroup;
  @Output() actionConfirmed = new EventEmitter<any>();
  private readonly subscriptions: Subscription[] = [];

  constructor(private fb: FormBuilder,
              private readonly modalRef: BsModalRef,
              private readonly postingAccountApiService: PostingAccountApiService,
              private readonly translateService: TranslateService,
              private readonly toastrService: ToastrService) {
    this.postingAccountForm = this.fb.group({
      name: [null, Validators.required],
      code: [null, Validators.required],
      description: [null, [Validators.maxLength(255)]],
      enabled: [true, [Validators.required]],
      ledgerGroup: [null],
      subLedgerGroup: [null],
      vatRule: [null],
    });
    // subscribe to ledgerGroup changes
    this.subscriptions.push(this.postingAccountForm.get('ledgerGroup')?.valueChanges.subscribe(value => {
      this.postingAccountForm.patchValue({
        subLedgerGroup: null
      })
      this.disableSubLedger = value == null;
    }) as Subscription);
  }

  ngOnInit(): void {
    if (this.postingAccountToEdit) {
      this.postingAccountForm.get('code')?.disable();
      this.postingAccountForm.patchValue(this.postingAccountToEdit);
    } else {
      this.disableSubLedger = true;
    }
  }

  submit() {
    let payload: any = {
      name: this.postingAccountForm.value.name,
      code: this.postingAccountForm.value.code,
      description: this.postingAccountForm.value.description,
      enabled: this.postingAccountForm.value.enabled,
    }
    if (this.postingAccountForm.value.ledgerGroup != null) {
      payload.ledgerGroupId = this.postingAccountForm.value.ledgerGroup.id
    } else {
      payload.ledgerGroupId = '';
    }
    if (this.postingAccountForm.value.subLedgerGroup != null) {
      payload.subLedgerGroupId = this.postingAccountForm.value.subLedgerGroup.id;
    } else {
      payload.subLedgerGroupId = '';
    }
    if (this.postingAccountForm.value.vatRule != null) {
      payload.vatRuleId = this.postingAccountForm.value.vatRule.id;
    } else {
      payload.vatRuleId = '';
    }

    if (!this.postingAccountToEdit) {
      console.log(this.component.concat('Creation mode ...'))
      this.subscriptions.push(this.postingAccountApiService.post(payload).subscribe({
        next: (data) => {
          console.info(this.component.concat('Posting Account created successfully. API response is:'), data);
          this.actionConfirmed.emit();
          this.closeModal();
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred when creating posting account. API error is:'), err);
          this.handleErrors(err);
        }
      }))
    } else {
      console.log(this.component.concat('Edit mode ...'))
      this.subscriptions.push(this.postingAccountApiService.patchById(this.postingAccountToEdit.id, payload).subscribe({
        next: (data) => {
          console.info(this.component.concat('Posting Account:'), this.postingAccountToEdit.name,
            'updated successfully. API response is:', data);
          this.actionConfirmed.emit();
          this.closeModal();
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred when updating posting account:',
            this.postingAccountToEdit.name, 'API error is:'), err);
          this.handleErrors(err);
        }
      }))
    }
  }

  closeModal() {
    this.modalRef.hide();
    this.postingAccountForm.reset();
  }

  private handleErrors(err: any) {
    if (err.status === 400) {
      const errors = err?.error?.errors;
      if (errors?.code && Array.isArray(errors.code)) {
        const hasUniqueCode = errors.code.some(
          (e: any) => e?.code === 'UniqueCode'
        );
        if (hasUniqueCode) {
          this.formErrors.code = {...this.formErrors.code, unique: true}
        }
      }
    }
  }

  ngOnDestroy(): void {
    console.debug(this.component.concat('Unsubscribing all subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
