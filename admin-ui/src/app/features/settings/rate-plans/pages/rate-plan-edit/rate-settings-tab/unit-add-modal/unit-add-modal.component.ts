import {Component, EventEmitter, OnDestroy, Output} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {RatePlanUnitApiService} from '../../../../services/rate-plan-unit-api.service';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {
  ButtonDirective,
  ColComponent,
  FormCheckComponent,
  FormCheckInputDirective,
  FormCheckLabelDirective,
  FormDirective,
  FormFeedbackComponent,
  FormLabelDirective,
  RowComponent
} from '@coreui/angular';
import {UnitSelectComponent} from '../../../../../../../shared/components/unit-select/unit-select.component';
import {ToastrService} from 'ngx-toastr';
import {JsonPipe} from '@angular/common';

@Component({
  selector: 'app-unit-add-modal',
  imports: [
    TranslatePipe,
    FormDirective,
    FormsModule,
    ButtonDirective,
    ColComponent,
    RowComponent,
    FormCheckComponent,
    FormCheckInputDirective,
    FormCheckLabelDirective,
    ReactiveFormsModule,
    FormFeedbackComponent,
    FormLabelDirective,
    UnitSelectComponent,
    JsonPipe
  ],
  templateUrl: './unit-add-modal.component.html',
  styleUrl: './unit-add-modal.component.scss'
})
export class UnitAddModalComponent implements OnDestroy {
  private component = '[UnitAddModalComponent]: ';
  ratePlanUnitForm: FormGroup;
  ratePlanId!: any;
  @Output() actionConfirmed = new EventEmitter<any>();
  private readonly subscriptions: Subscription[] = [];

  constructor(private fb: FormBuilder,
              private readonly modalRef: BsModalRef,
              private readonly ratePlanUnitApiService: RatePlanUnitApiService,
              private readonly translateService: TranslateService,
              private readonly toastrService: ToastrService) {
    this.ratePlanUnitForm = this.fb.group({
      unit: [null, Validators.required],
      enabled: [true, Validators.required]
    })
  }

  submit() {
    let payload = {
      ratePlanId: this.ratePlanId,
      unit: {
        id: this.ratePlanUnitForm.value.unit.id,
        name: this.ratePlanUnitForm.value.unit.name,
        code: this.ratePlanUnitForm.value.unit.code
      },
      enabled: this.ratePlanUnitForm.value.enabled
    };
    this.subscriptions.push(this.ratePlanUnitApiService.post(payload).subscribe({
      next:(data:any)=>{
        console.info(this.component.concat('Unit added successfully. API response is:'), data);
        this.modalRef.hide();
        this.ratePlanUnitForm.reset();
        this.actionConfirmed.emit();
      },
      error: (err:any)=>{
        console.error(this.component.concat('An error occurred when adding unit. API error response is:'), err);
      }
    }))
  }

  closeModal() {
    this.modalRef.hide();
    this.ratePlanUnitForm.reset();
  }

  ngOnDestroy(): void {
    console.log(this.component.concat('Unsubscribing from all subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}
