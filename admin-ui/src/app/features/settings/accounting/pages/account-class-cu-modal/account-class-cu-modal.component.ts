import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {Subscription} from 'rxjs';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {ToastrService} from 'ngx-toastr';
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
import {AccountClassApiService} from '../../services/account-class-api.service';

@Component({
  selector: 'app-account-class-cu-modal',
  imports: [
    TranslatePipe,
    FormDirective,
    FormsModule,
    ReactiveFormsModule,
    ButtonDirective,
    ColComponent,
    RowComponent,
    FormControlDirective,
    FormFeedbackComponent,
    FormLabelDirective,
    FormCheckComponent,
    FormCheckInputDirective,
    FormCheckLabelDirective
  ],
  templateUrl: './account-class-cu-modal.component.html',
  styleUrl: './account-class-cu-modal.component.scss'
})
export class AccountClassCuModalComponent implements OnInit, OnDestroy {

  private component = '[AccountClassCuModalComponent]: ';
  accountClassToEdit!: any;
  accountClassForm: FormGroup;
  @Output() actionConfirmed = new EventEmitter<string>();
  private readonly subscriptions: Subscription[] = [];

  public constructor(private readonly fb: FormBuilder,
                     private readonly modalRef: BsModalRef,
                     private readonly accountClassApiService: AccountClassApiService,
                     private readonly translateService: TranslateService,
                     private readonly toastrService: ToastrService) {
    this.accountClassForm = this.fb.group({
      name: [null, [Validators.required]],
      description: [null, [Validators.maxLength(255)]],
      enabled: [true, [Validators.required]]
    })
  }

  ngOnInit(): void {
    if (this.accountClassToEdit) {
      this.accountClassForm.patchValue({
        name: this.accountClassToEdit.name,
        description: this.accountClassToEdit.description,
        enabled: this.accountClassToEdit.enabled
      })
    }
  }

  submit() {
    let payload = this.accountClassForm.value;
    if (!this.accountClassToEdit) {
      this.subscriptions.push(this.accountClassApiService.post(payload).subscribe({
        next: (data) => {
          console.log(this.component.concat('Account class created successfully. API response is:'), data);
          this.actionConfirmed.emit();
          this.closeModal();
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred when creating account class. API error is:'), err);
          //TODO: add toastr
        }
      }))
    } else {
      this.subscriptions.push(this.accountClassApiService.patchById(this.accountClassToEdit.id, payload).subscribe({
        next: (data) => {
          console.log(this.component.concat('Account class updated successfully. API response is:'), data);
          this.actionConfirmed.emit();
          this.closeModal();
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred when updating account class:'), this.accountClassToEdit.name, 'API error is:', err);
          //TODO: add toastr
        }
      }))
    }
  }

  closeModal() {
    this.modalRef.hide();
    this.accountClassForm.reset();
  }

  ngOnDestroy(): void {
    console.debug(this.component.concat('Unsubscribing all subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
