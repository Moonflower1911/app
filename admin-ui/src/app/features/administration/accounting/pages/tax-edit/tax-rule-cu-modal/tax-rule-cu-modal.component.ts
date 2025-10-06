import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {Subscription} from 'rxjs';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {VatRuleApiService} from '../../../services/vat-rule-api.service';
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
  FormSelectDirective,
  InputGroupComponent,
  RowComponent
} from '@coreui/angular';
import {JsonPipe} from '@angular/common';

@Component({
  selector: 'app-tax-rule-cu-modal',
  imports: [
    TranslatePipe,
    FormDirective,
    ReactiveFormsModule,
    ColComponent,
    FormControlDirective,
    FormFeedbackComponent,
    FormLabelDirective,
    RowComponent,
    FormSelectDirective,
    ButtonDirective,
    DropdownComponent,
    DropdownItemDirective,
    DropdownMenuDirective,
    DropdownToggleDirective,
    InputGroupComponent,
    JsonPipe,
    FormCheckComponent,
    FormCheckInputDirective,
    FormCheckLabelDirective
  ],
  templateUrl: './tax-rule-cu-modal.component.html',
  styleUrl: './tax-rule-cu-modal.component.scss'
})
export class TaxRuleCuModalComponent implements OnInit, OnDestroy {
  private component = '[TaxRuleCuModalComponent]: ';
  taxRuleToEdit!: any;
  taxRuleForm: FormGroup;
  @Output() actionConfirmed = new EventEmitter<any>();
  private readonly subscriptions: Subscription[] = [];

  constructor(private fb: FormBuilder,
              private readonly modalRef: BsModalRef,
              private readonly vatRuleApiService: VatRuleApiService,
              private readonly translateService: TranslateService,
              private readonly toastrService: ToastrService) {
    this.taxRuleForm = this.fb.group({
      name: [null, Validators.required],
      amount: [null, Validators.required],
      amountType: ['PERCENT', Validators.required],
      application: [null, Validators.required],
      enabled: [true, Validators.required]
    })
  }

  ngOnInit(): void {
    if (this.taxRuleToEdit) {
      this.taxRuleForm.patchValue(this.taxRuleToEdit);
    }
  }

  submit() {
    let payload = this.taxRuleForm.value;
    if (this.taxRuleToEdit) {
      this.subscriptions.push(this.vatRuleApiService.patch(this.taxRuleToEdit.id, payload).subscribe({
        next: (data: any) => {
          console.info(this.component.concat('Tax rule updated successfully. API response is:'), data);
          this.actionConfirmed.emit();
          this.closeModal();
        },
        error: (err: any) => {
          console.error(this.component.concat('An error occurred when updating the tax rule. API error response is:'), err);
        }
      }))
    } else {
      this.subscriptions.push(this.vatRuleApiService.post(payload).subscribe({
        next: (data: any) => {
          console.info(this.component.concat('Tax rule created successfully. API response is:'), data);
          this.actionConfirmed.emit();
          this.closeModal();
        },
        error: (err: any) => {
          console.error(this.component.concat('An error occurred when creating the tax rule. API error response is:'), err);
        }
      }))
    }
  }

  setAmountType(amountType: string) {
    this.taxRuleForm.patchValue({
      amountType: amountType
    })
  }

  closeModal() {
    this.modalRef.hide();
    this.taxRuleForm.reset();
  }

  ngOnDestroy(): void {
    console.debug(this.component.concat('Unsubscribing all subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}
