import {Component, EventEmitter, OnDestroy, Output} from '@angular/core';
import {TranslatePipe, TranslateService} from "@ngx-translate/core";
import {
  ButtonDirective,
  ColComponent,
  FormDirective,
  FormFeedbackComponent,
  FormLabelDirective,
  RowComponent
} from '@coreui/angular';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {InclusionApiService} from '../../../../services/inclusion-api.service';
import {ToastrService} from 'ngx-toastr';
import {ChargeSelectComponent} from '../../../../../../../shared/components/charge-select/charge-select.component';

@Component({
  selector: 'app-inclusion-create-modal',
  imports: [
    TranslatePipe,
    FormDirective,
    FormsModule,
    ReactiveFormsModule,
    RowComponent,
    ColComponent,
    FormLabelDirective,
    ButtonDirective,
    FormFeedbackComponent,
    ChargeSelectComponent
  ],
  templateUrl: './inclusion-create-modal.component.html',
  standalone: true,
  styleUrl: './inclusion-create-modal.component.scss'
})
export class InclusionCreateModalComponent implements OnDestroy {
  private component = '[InclusionCreateModalComponent]: ';

  inclusionForm: FormGroup;
  ratePlanId!: string;
  @Output() actionConfirmed = new EventEmitter<any>();
  private readonly subscriptions: Subscription[] = [];

  constructor(private fb: FormBuilder,
              private readonly modalRef: BsModalRef,
              private readonly inclusionApiService: InclusionApiService,
              private readonly translateService: TranslateService,
              private readonly toastrService: ToastrService) {
    this.inclusionForm = this.fb.group({
      charge: [null, Validators.required],
    })
  }


  submit() {
    let payload: any = {
      chargeId: this.inclusionForm.value.charge.id,
      ratePlanId: this.ratePlanId,
    }
    console.log(this.component.concat('Payload to post is:'), payload);
    this.subscriptions.push(this.inclusionApiService.post(payload).subscribe({
      next: (data) => {
        console.info(this.component.concat('Inclusion added successfully. API response is:'), data);
        this.actionConfirmed.emit();
        this.closeModal();
      },
      error: (err) => {
        console.error(this.component.concat('An error occurred during inclusion addition'), err);
      }
    }))
  }

  closeModal() {
    this.modalRef.hide();
    this.inclusionForm.reset();
  }

  ngOnDestroy(): void {
    console.log(this.component.concat('Unsubscribing all subscriptions'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
