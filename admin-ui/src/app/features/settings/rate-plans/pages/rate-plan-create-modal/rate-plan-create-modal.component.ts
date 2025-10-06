import {Component, EventEmitter, OnDestroy, Output} from '@angular/core';
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
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {ToastrService} from 'ngx-toastr';
import {RatePlanApiService} from '../../services/rate-plan-api.service';
import {UnitSelectComponent} from '../../../../../shared/components/unit-select/unit-select.component';

@Component({
  selector: 'app-rate-plan-create-modal',
  imports: [
    TranslatePipe,
    FormDirective,
    ReactiveFormsModule,
    ButtonDirective,
    ColComponent,
    RowComponent,
    FormControlDirective,
    FormFeedbackComponent,
    FormLabelDirective,
    FormCheckComponent,
    FormCheckInputDirective,
    FormCheckLabelDirective,
    UnitSelectComponent
  ],
  templateUrl: './rate-plan-create-modal.component.html',
  standalone: true,
  styleUrl: './rate-plan-create-modal.component.scss'
})
export class RatePlanCreateModalComponent implements OnDestroy {

  private component = '[RatePlanCreateModalComponent]: ';
  formErrors: any = {};
  ratePlanForm: FormGroup;
  @Output() actionConfirmed = new EventEmitter<any>();
  private readonly subscriptions: Subscription[] = [];

  constructor(private fb: FormBuilder,
              private readonly modalRef: BsModalRef,
              private readonly ratePlanApiService: RatePlanApiService,
              private readonly translateService: TranslateService,
              private readonly toastrService: ToastrService) {
    this.ratePlanForm = this.fb.group({
      name: [null, Validators.required],
      code: [null, Validators.required],
      type: ['BASE', Validators.required],
      description: [null, [Validators.maxLength(255)]],
      units: [null, Validators.required],
      enabled: [true, [Validators.required]],
    })
  }

  submit() {
    let payload: any = {
      name: this.ratePlanForm.value.name,
      code: this.ratePlanForm.value.code,
      description: this.ratePlanForm.value.description,
      enabled: this.ratePlanForm.value.enabled,
      type: this.ratePlanForm.value.type,
      units: this.ratePlanForm.value.units.map((obj: any) => ({id: obj.id, name: obj.name, code: obj.code}))
    }
    console.debug(this.component.concat('Payload to be sent is:'), payload);
    this.subscriptions.push(this.ratePlanApiService.post(payload).subscribe({
      next: (data: any) => {
        console.info(this.component.concat('Rate Plan created successfully. API response is:'), data);
        this.actionConfirmed.emit();
        this.closeModal();
      },
      error: (err: any) => {
        console.error(this.component.concat('An error occurred when creating rate plan. API error is:'), err);
        this.handleErrors(err);
      }
    }))
  }

  closeModal() {
    this.modalRef.hide();
    this.ratePlanForm.reset();
  }

  private handleErrors(err: any) {
    if (err.status === 400) {
      const errors = err?.error?.errors;
      if (errors?.code && Array.isArray(errors.code)) {
        const hasUniqueCode = errors.code.some(
          (e: any) => e?.code === 'UniqueRatePlanCode'
        );
        if (hasUniqueCode) {
          this.formErrors.code = {...this.formErrors.code, uniqueRatePlanCode: true}
        }
      }
    }
  }

  ngOnDestroy(): void {
    console.debug(this.component.concat('Unsubscribing all subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}
