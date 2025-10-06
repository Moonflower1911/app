import {Component, Input, OnDestroy} from '@angular/core';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {ColComponent, FormCheckComponent, FormCheckInputDirective, RowComponent} from '@coreui/angular';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {BsModalService} from 'ngx-bootstrap/modal';
import {RatePlanApiService} from '../../../../services/rate-plan-api.service';
import {
  CancellationPolicySelectComponent
} from '../../../../../../../shared/components/cancellation-policy-select/cancellation-policy-select.component';
import {ConfirmModalComponent} from '../../../../../../../shared/components/confirm-modal/confirm-modal.component';

@Component({
  selector: 'app-cancellation-policy',
  imports: [
    TranslatePipe,
    FormCheckComponent,
    FormCheckInputDirective,
    ReactiveFormsModule,
    FormsModule,
    CancellationPolicySelectComponent,
    ColComponent,
    RowComponent
  ],
  templateUrl: './cancellation-policy.component.html',
  standalone: true,
  styleUrl: './cancellation-policy.component.scss',
  providers: [BsModalService]
})
export class CancellationPolicyComponent implements OnDestroy {
  private component = '[CancellationPolicyComponent]: ';
  _ratePlan!: any;
  cancellationPolicyForm: FormGroup;
  private readonly subscriptions: Subscription[] = [];

  constructor(private readonly fb: FormBuilder,
              private readonly translateService: TranslateService,
              private readonly ratePlanApiService: RatePlanApiService,
              private readonly modalService: BsModalService) {
    this.cancellationPolicyForm = this.fb.group({
      cancellationEnabled: [false],
      cancellationPolicy: [null, [Validators.required]]
    })

    this.subscriptions.push(this.cancellationPolicyForm?.valueChanges.subscribe(value => {
      if (value.cancellationEnabled === false) {
        if (value.cancellationPolicy) {
          const initialState = {
            title: this.translateService.instant('settings.rate-plan.pages.settings.cancellation-policy-section.disable-modal.title'),
            message: this.translateService.instant(
              'settings.rate-plan.pages.settings.cancellation-policy-section.disable-modal.message')
          };

          const confirmModalRef = this.modalService.show(ConfirmModalComponent, {initialState});

          this.subscriptions.push(
            (confirmModalRef.content as ConfirmModalComponent).actionConfirmed.subscribe(() => {
              this.subscriptions.push(this.ratePlanApiService.patchById(this._ratePlan.id, {cancellationPolicyId: ''}).subscribe({
                next: (data: any) => {
                  console.info(this.component.concat('Rate plan updated successfully. Cancellation policy disabled. API Response is:'), data)
                  this.cancellationPolicyForm.patchValue({
                    cancellationEnabled: false,
                    cancellationPolicy: null
                  })
                },
                error: (err) => {
                  console.error(this.component.concat('An error occurred when updating rate plan to disable cancellation policy. API error response is:'), err);
                }
              }))
            })
          );
        }

      } else {
        // enabling: you could also confirm here if you want
        if (this.cancellationPolicyForm.value.cancellationPolicy) {
          this.subscriptions.push(this.ratePlanApiService.patchById(this._ratePlan.id, {cancellationPolicyId: this.cancellationPolicyForm.value.cancellationPolicy.id}).subscribe({
            next: (data: any) => {
              console.info(this.component.concat('Rate plan updated successfully. Cancellation policy enabled. API Response is:'), data)
            },
            error: (err) => {
              console.error(this.component.concat('An error occurred when updating rate plan to enable cancellation policy. API error response is:'), err);
            }
          }))
        }
      }
    }) as Subscription);

  }

  @Input()
  set ratePlan(value: any) {
    if(value){
      this._ratePlan = value;
      if (this._ratePlan.cancellationPolicy) {
        this.cancellationPolicyForm.patchValue({
          cancellationEnabled: true,
          cancellationPolicy: this._ratePlan.cancellationPolicy
        })
      }
    }



  }

  submit() {

  }

  ngOnDestroy(): void {
    console.log(this.component.concat('Unsubscribing from all subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
