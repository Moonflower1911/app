import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {Subscription} from 'rxjs';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {ToastrService} from 'ngx-toastr';
import {ExtraGuestChargeApiService} from '../../services/extra-guest-charge-api.service';
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
  InputGroupTextDirective,
  RowComponent
} from '@coreui/angular';
import {IconDirective} from '@coreui/icons-angular';
import {noChildAgeOverlapValidator} from '../../../../properties/validators/no-age-overlap.validator';
import {ageRangeValidator} from '../../../../properties/validators/ageBucket.validator';
import {cilPlus, cilTrash} from '@coreui/icons';
import {JsonPipe} from '@angular/common';

@Component({
  selector: 'app-extra-guest-charge-cu-modal',
  imports: [
    TranslatePipe,
    ReactiveFormsModule,
    ButtonDirective,
    ColComponent,
    RowComponent,
    FormDirective,
    FormControlDirective,
    FormFeedbackComponent,
    FormLabelDirective,
    DropdownComponent,
    DropdownItemDirective,
    DropdownMenuDirective,
    DropdownToggleDirective,
    FormSelectDirective,
    IconDirective,
    InputGroupComponent,
    InputGroupTextDirective,
    FormCheckComponent,
    FormCheckInputDirective,
    FormCheckLabelDirective,
    JsonPipe
  ],
  templateUrl: './extra-guest-charge-cu-modal.component.html',
  standalone: true,
  styleUrl: './extra-guest-charge-cu-modal.component.scss'
})
export class ExtraGuestChargeCuModalComponent implements OnInit, OnDestroy {

  private component = '[ExtraGuestChargeCuModalComponent]: ';
  icons = {cilTrash, cilPlus};
  extraGuestChargeToEdit!: any;
  extraGuestForm: FormGroup;
  @Output() actionConfirmed = new EventEmitter<any>();
  private readonly subscriptions: Subscription[] = [];

  constructor(private fb: FormBuilder,
              private readonly modalRef: BsModalRef,
              private readonly extraGuestChargeApiService: ExtraGuestChargeApiService,
              private readonly translateService: TranslateService,
              private readonly toastrService: ToastrService) {
    this.extraGuestForm = this.fb.group({
      name: [null, Validators.required],
      description: [null, [Validators.maxLength(255)]],
      enabled: [true, [Validators.required]],
      rules: this.fb.array([], [noChildAgeOverlapValidator])
    })
  }

  ngOnInit(): void {
    if (this.extraGuestChargeToEdit) {
      this.subscriptions.push(this.extraGuestChargeApiService.getById(this.extraGuestChargeToEdit.id).subscribe({
        next: (data:any)=>{
          console.info(this.component.concat('Extra guest charge retrieved by Id successfully. API response is:'), data)
          this.extraGuestForm.patchValue({
            name: data.name,
            description: data.description,
            enabled: data.enabled
          });
          this.populateForm(data);
        }
      }))
    } else {
      this.addRule();
    }
  }

  submit() {
    let payload = this.extraGuestForm.value;
    payload.rules = payload.rules.map((rule:any)=>{
      if (rule.guestType === 'ADULT') {
        delete rule.ageBucket;
      }
      return rule;
    })
    console.log('Payload is:', payload);
    if (this.extraGuestChargeToEdit) {
      this.subscriptions.push(this.extraGuestChargeApiService.patchById(this.extraGuestChargeToEdit.id, payload).subscribe({
        next: (data)=>{
          console.info(this.component.concat('Extra guest charge strategy updated successfully. API response is:'), data);
          this.closeModal();
          this.actionConfirmed.emit();
        },
        error:(err)=>{
          console.error(this.component.concat('An error occurred when updating extra guest charge strategy. API error response is:'), err);
        }
      }))
    } else {
      this.subscriptions.push(this.extraGuestChargeApiService.post(payload).subscribe({
        next: (data) => {
          console.info(this.component.concat('Extra guest charge strategy created successfully. API response is:'), data);
          this.closeModal();
          this.actionConfirmed.emit();
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred when creating extra guest charge strategy. API error response is:'), err);
        }
      }))
    }

  }

  private populateForm(data: any): void {

    const rules = this.extraGuestForm.get('rules') as FormArray;
    data.rules?.forEach((fee: any) => {
      rules.push(this.fb.group({
        id: [fee.id],
        guestThreshold: [fee.guestThreshold, [Validators.required, Validators.min(1)]],
        guestType: [fee.guestType, [Validators.required]],
        amountType: [fee.amountType, [Validators.required]],
        value: [fee.value, [Validators.required, Validators.min(0)]],
        ageBucket: this.fb.group({
          fromAge: [fee.ageBucket?.fromAge ?? null],
          toAge: [fee.ageBucket?.toAge ?? null]
        })
      }));
    });
  }

  hasAdult(currentIndex: number): boolean {
    return this.rules.controls
      .some((group, index) => index !== currentIndex && group.get('guestType')?.value === 'ADULT');
  }

  get rules(): FormArray {
    return this.extraGuestForm.get('rules') as FormArray;
  }

  setAmountType(index: number, amountType: string): void {
    const feeGroup = this.rules.at(index);
    feeGroup.get('amountType')?.setValue(amountType);
  }

  addRule(): void {
    const rules = this.rules;

    const hasAdult = rules.controls.some(
      group => group.get('guestType')?.value === 'ADULT'
    );

    const defaultGuestType = hasAdult ? 'CHILD' : 'ADULT';

    const ruleGroup = this.fb.group({
      id: [null],
      guestThreshold: [1, [Validators.required, Validators.min(1)]],
      guestType: [defaultGuestType, [Validators.required]],
      amountType: ['FLAT', [Validators.required]],
      value: [0, [Validators.required, Validators.min(0)]]
    });

    if (defaultGuestType === 'CHILD') {
      (ruleGroup as FormGroup).addControl(
        'ageBucket',
        this.fb.group(
          {
            fromAge: [0, [Validators.required, Validators.min(0)]],
            toAge: [0, [Validators.required, Validators.min(0)]]
          },
          {validators: ageRangeValidator()}
        )
      );
    }

    const guestTypeControl = ruleGroup.get('guestType');
    guestTypeControl?.valueChanges.subscribe(type => {
      if (type === 'CHILD' && !ruleGroup.get('ageBucket')) {
        (ruleGroup as FormGroup).addControl(
          'ageBucket',
          this.fb.group({
            fromAge: [0, [Validators.required, Validators.min(0)]],
            toAge: [0, [Validators.required, Validators.min(0)]]
          }, {validators: ageRangeValidator()})
        );
      } else if (type === 'ADULT' && ruleGroup.get('ageBucket')) {
        (ruleGroup as FormGroup).removeControl('ageBucket');
      }
    });

    rules.push(ruleGroup);
  }

  removeRule(index: number): void {
    const rules = this.extraGuestForm.get('rules') as FormArray;
    if (rules.length > 0) {
      rules.removeAt(index);
    }
  }

  closeModal() {
    this.modalRef.hide();
    this.extraGuestForm.reset();
  }

  ngOnDestroy(): void {
    console.log(this.component.concat('Unsubscribing all subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
