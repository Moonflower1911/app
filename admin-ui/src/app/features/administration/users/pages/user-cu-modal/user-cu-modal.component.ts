import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
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
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { NgxIntlTelInputModule, CountryISO, SearchCountryField } from 'ngx-intl-tel-input';
import { NgSelectComponent, NgLabelTemplateDirective } from '@ng-select/ng-select';


import { emailValidator } from '../../../../../shared/validators/email.validator';
import {RoleEnum} from '../../models/enum/role.enum';
import {UserItemGetModel} from '../../models/get/user-item-get.model';
import {UserPostModel} from '../../models/post/user-post.model';
import {UserPatchModel} from '../../models/patch/user-patch.model';
import {UserApiService} from '../../services/user-api.service';


@Component({
  selector: 'app-user-cu-modal',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ButtonDirective,
    ColComponent,
    FormDirective,
    FormControlDirective,
    FormLabelDirective,
    FormFeedbackComponent,
    RowComponent,
    TranslatePipe,
    NgxIntlTelInputModule,
    NgSelectComponent,
    NgLabelTemplateDirective,
    FormCheckComponent,
    FormCheckInputDirective,
    FormCheckLabelDirective
  ],
  templateUrl: './user-cu-modal.component.html',
  styleUrls: ['./user-cu-modal.component.scss']
})
export class UserCuModalComponent implements OnInit, OnDestroy {

  @Input() userToEdit?: UserItemGetModel;
  @Output() actionConfirmed = new EventEmitter<UserItemGetModel>();

  userForm: FormGroup;
  uniqueEmailError = false;
  isSubmitting = false;
  roles = Object.values(RoleEnum);

  protected readonly SearchCountryField = SearchCountryField;
  protected readonly CountryISO = CountryISO;

  private subscriptions: Subscription[] = [];

  constructor(
    private readonly fb: FormBuilder,
    private readonly userApiService: UserApiService,
    private readonly modalRef: BsModalRef,
    private readonly translateService: TranslateService,
    private readonly toastrService: ToastrService
  ) {
    this.userForm = this.createForm();
  }

  ngOnInit(): void {
    if (this.userToEdit) {
      this.populateForm();
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  private createForm(): FormGroup {
    return this.fb.group({
      fullName: ['', [Validators.required]],
      email: ['', [Validators.required, emailValidator()]],
      mobile: [''],
      roles: [null, [Validators.required]],
      enabled: [true]
    });
  }

  private populateForm(): void {
    if (this.userToEdit) {
      this.userForm.patchValue({
        fullName: this.userToEdit.fullName,
        email: this.userToEdit.email,
        mobile: this.userToEdit.mobile,
        roles: this.userToEdit.roles[0], // Assuming single role for now
        enabled: this.userToEdit.enabled
      });
    }
  }

  submit(): void {
    if (!this.userForm.valid || this.isSubmitting) {
      return;
    }

    this.isSubmitting = true;
    this.uniqueEmailError = false;

    const formValue = this.userForm.value;

    if (!this.userToEdit) {
      // Create mode
      const payload: UserPostModel = {
        fullName: formValue.fullName.trim(),
        email: formValue.email.trim(),
        mobile: formValue.mobile?.e164Number || formValue.mobile,
        roles: [formValue.roles]
      };

      this.subscriptions.push(
        this.userApiService.createUser(payload).subscribe({
          next: (createdUser) => {
            this.isSubmitting = false;
            this.actionConfirmed.emit(createdUser);
            this.closeModal();

            const message = this.translateService.instant('administration.users.pages.create-user.form.notifications.success.message')
              .replace(':name', createdUser.fullName);
            const title = this.translateService.instant('administration.users.pages.create-user.form.notifications.success.title');
            this.toastrService.success(message, title);
          },
          error: (error) => {
            this.handleError(error);
          }
        })
      );
    } else {
      // Update mode
      const payload: UserPatchModel = {
        fullName: formValue.fullName.trim(),
        email: formValue.email.trim(),
        mobile: formValue.mobile?.e164Number || formValue.mobile,
        roles: [formValue.roles],
        enabled: formValue.enabled
      };

      this.subscriptions.push(
        this.userApiService.updateUser(this.userToEdit.id, payload).subscribe({
          next: (updatedUser) => {
            this.isSubmitting = false;
            this.actionConfirmed.emit(updatedUser);
            this.closeModal();

            const message = this.translateService.instant('administration.users.pages.edit-user.form.notifications.success.message')
              .replace(':name', updatedUser.fullName);
            const title = this.translateService.instant('administration.users.pages.edit-user.form.notifications.success.title');
            this.toastrService.info(message, title);
          },
          error: (error) => {
            this.handleError(error);
          }
        })
      );
    }
  }

  private handleError(error: any): void {
    this.isSubmitting = false;

    if (error?.error?.errors?.email && Array.isArray(error.error.errors.email)) {
      const uniqueEmailError = error.error.errors.email.find(
        (err: { code: string }) => err.code === 'UniqueEmail'
      );
      if (uniqueEmailError) {
        this.uniqueEmailError = true;
        return;
      }
    }

    const errorKey = this.userToEdit
      ? 'administration.users.pages.edit-user.form.notifications.error'
      : 'administration.users.pages.create-user.form.notifications.error';

    const message = this.translateService.instant(`${errorKey}.message`);
    const title = this.translateService.instant(`${errorKey}.title`);
    this.toastrService.error(message, title);
  }

  closeModal(): void {
    if (this.isSubmitting) return;

    this.modalRef.hide();
    this.userForm.reset({ enabled: true });
    this.isSubmitting = false;
    this.uniqueEmailError = false;
  }

  getRoleLabel(role: RoleEnum): string {
    const roleLabels: { [key in RoleEnum]: string } = {
      [RoleEnum.ADMINISTRATOR]: 'Administrator',
      [RoleEnum.ACCOUNTING]: 'Accounting',
      [RoleEnum.HOUSE_STAFF]: 'House Staff',
      [RoleEnum.PROPERTY_MANAGER]: 'Property Manager',
      [RoleEnum.PROPERTY_OWNER]: 'Property Owner'
    };
    return roleLabels[role] || role;
  }
}
