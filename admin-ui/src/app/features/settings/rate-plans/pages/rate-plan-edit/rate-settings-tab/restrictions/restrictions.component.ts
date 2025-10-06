import {Component, Input, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {
  ButtonDirective,
  CardBodyComponent,
  CardComponent,
  CardSubtitleDirective,
  CardTitleDirective,
  ColComponent,
  FormControlDirective,
  FormFeedbackComponent,
  FormLabelDirective,
  InputGroupComponent,
  InputGroupTextDirective,
  RowComponent
} from '@coreui/angular';
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {RatePlanApiService} from '../../../../services/rate-plan-api.service';
import {maxLosValidator} from '../../../../../../../shared/validators/max-los.validator';
import {maxLeadValidator} from '../../../../../../../shared/validators/max-lead.validator';

@Component({
  selector: 'app-restrictions',
  imports: [
    TranslatePipe,
    ColComponent,
    FormsModule,
    RowComponent,
    ReactiveFormsModule,
    CardComponent,
    CardBodyComponent,
    CardTitleDirective,
    CardSubtitleDirective,
    FormControlDirective,
    FormLabelDirective,
    InputGroupComponent,
    InputGroupTextDirective,
    FormFeedbackComponent,
    ButtonDirective
  ],
  templateUrl: './restrictions.component.html',
  styleUrl: './restrictions.component.scss'
})
export class RestrictionsComponent implements OnDestroy {

  private component = '[RestrictionsComponent]: ';
  _ratePlan: any;
  restrictionsForm: any;

  private subscriptions: Subscription[] = [];


  constructor(private readonly fb: FormBuilder,
              private readonly translateService: TranslateService,
              private readonly ratePlanApiService: RatePlanApiService) {
    this.restrictionsForm = this.fb.group({
      minLos: [1, [Validators.required, Validators.min(1)]],
      maxLos: [null, [Validators.min(1), maxLosValidator]],
      minLead: [0, [Validators.required, Validators.min(0)]],
      maxLead: [null, [Validators.min(0), maxLeadValidator]]
    })
  }

  @Input()
  set ratePlan(value: any) {
    if (value) {
      this._ratePlan = value;
      this.restrictionsForm.patchValue(value.restrictions);
    }
  }

  submit() {
    this.subscriptions.push(this.ratePlanApiService.patchById(this._ratePlan.id, {
      restrictions: this.restrictionsForm.value
    }).subscribe({
      next: (data: any) => {
        console.info(this.component.concat('Restrictions updated successfully. API response is:'), data);
        this.restrictionsForm.patchValue(data.restrictions);
      },
      error: (err: any) => {
        console.error(this.component.concat('An error occurred when updating restrictions. API error response is:'), err);
      }
    }))
  }


  ngOnDestroy(): void {
    console.log(this.component.concat('Unsubscribing from all subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
