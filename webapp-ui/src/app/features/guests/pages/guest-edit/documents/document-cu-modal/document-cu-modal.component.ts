import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {Subscription} from "rxjs";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {IdentityDocumentService} from "../../../../services/identity-document.service";
import {BsModalRef} from "ngx-bootstrap/modal";
import {TranslatePipe, TranslateService} from "@ngx-translate/core";
import {ToastrService} from "ngx-toastr";
import {IdentityDocumentItemGetModel} from "../../../../models/identity-document-item-get.model";
import {
  ButtonDirective,
  ColComponent,
  DropdownComponent,
  DropdownItemDirective,
  DropdownMenuDirective,
  DropdownToggleDirective,
  FormControlDirective,
  FormFeedbackComponent,
  FormLabelDirective,
  InputGroupComponent,
  RowComponent
} from "@coreui/angular";
import {JsonPipe, NgForOf, NgIf} from "@angular/common";
import {DocumentTypeEnum} from "../../../../models/document-type.enum";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import { NgxDaterangepickerBootstrapDirective } from 'ngx-daterangepicker-bootstrap';
import moment from 'moment';

@Component({
  selector: 'app-document-cu-modal',
  imports: [
    TranslatePipe,
    ReactiveFormsModule,
    ButtonDirective,
    ColComponent,
    RowComponent,
    FormLabelDirective,
    InputGroupComponent,
    DropdownComponent,
    DropdownToggleDirective,
    DropdownMenuDirective,
    DropdownItemDirective,
    FormControlDirective,
    FormFeedbackComponent,
    NgForOf,
    NgxDaterangepickerBootstrapDirective,
    NgIf,
    FormsModule
  ],
  templateUrl: './document-cu-modal.component.html',
  styleUrl: './document-cu-modal.component.scss'
})
export class DocumentCuModalComponent implements OnInit, OnDestroy {
  imageUrl: SafeUrl | null = null;
  isNewFile: boolean = false;
  file: any;
  documentForm: FormGroup;
  documentTypes: DocumentTypeEnum[] = Object.values(DocumentTypeEnum);
  documentToEdit!: IdentityDocumentItemGetModel | undefined;
  guestId!: string;
  @Output() actionConfirmed = new EventEmitter<void>();
  private readonly subscriptions: Subscription[] = [];

  isNavigating = false;
  expirationDateModel: { startDate: moment.Moment | null, endDate: moment.Moment | null } = { startDate: null, endDate: null };

  datePickerLocale = {
    format: 'DD-MM-YYYY',
    applyLabel: this.translateService.instant('commons.apply'),
    cancelLabel: this.translateService.instant('commons.cancel'),
  };

  constructor(private readonly fb: FormBuilder,
              private readonly documentService: IdentityDocumentService,
              private readonly sanitizer: DomSanitizer,
              private readonly modalRef: BsModalRef,
              private readonly translateService: TranslateService,
              private readonly toastrService: ToastrService) {
    this.documentForm = this.fb.group({
      type: [DocumentTypeEnum.IDENTITY_CARD, [Validators.required]],
      value: [null, [Validators.required]],
      expirationDate: [null, [Validators.required]],
      file: [null, [Validators.required]]
    })
  }

  ngOnInit(): void {
    console.log('your document to edit', this.documentToEdit, this.guestId);

    this.documentForm.patchValue(this.documentToEdit as IdentityDocumentItemGetModel);

    if (this.documentToEdit?.expirationDate) {
      const parsedDate = moment(this.documentToEdit.expirationDate, 'YYYY-MM-DD');
      this.expirationDateModel = { startDate: parsedDate, endDate: parsedDate };
      this.documentForm.get('expirationDate')?.setValue(parsedDate.toDate());
    }

    if (this.documentToEdit?.fileProvided) {
      this.retrieveIdentityDocumentFile();
    }
  }

  private retrieveIdentityDocumentFile() {
    this.subscriptions.push(this.documentService.getIdentityDocumentImageById(this.documentToEdit?.id as string).subscribe({
      next: (res) => {
        console.info('Identity Document Image retrieved by Id:', this.documentToEdit?.id, 'API response is:', res);
        const objectUrl = URL.createObjectURL(res);
        this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectUrl);
        this.documentForm.patchValue({
          file: res
        })
      },
      error: (err) => {
        console.error('An error occurred when retrieving identity document file. API error response is:', err);
      }
    }))
  }

  onExpirationDateChange(event: any) {
    console.log('Expiration Date change event:', event);
    if (event.startDate) {
      this.expirationDateModel = event;
      this.documentForm.get('expirationDate')?.setValue(event.startDate.toDate());
    } else {
      this.expirationDateModel = { startDate: null, endDate: null };
      this.documentForm.get('expirationDate')?.setValue(null);
    }
  }


  submit() {
    const formValue = this.documentForm.value;
    const expirationDateFormatted = this.expirationDateModel.startDate
      ? this.expirationDateModel.startDate.format('YYYY-MM-DD')
      : null;
    let payload: any = {
      type: formValue.type,
      value: formValue.value,
      expirationDate: expirationDateFormatted,
    }
    const formData = new FormData();
    if (this.file && this.isNewFile) {
      formData.append('file', this.file);
    }
    if (this.documentToEdit) {
      //updating existing document
      formData.append('payload', new Blob([JSON.stringify(payload)], {type: 'application/json'}));
      console.log('your form data is:', formData);
      this.subscriptions.push(this.documentService.updateDocument(this.documentToEdit.id, formData).subscribe({
        next: (res) => {
          console.info('Document updated successfully. API response is:', res);
          this.toastrService.info(this.translateService.instant('documents.cu-modal.notifications.success.message.edit'), this.translateService.instant('documents.cu-modal.notifications.success.title.edit'));
          this.closeModal();
          this.actionConfirmed.emit();
        },
        error: (err) => {
          console.error('An error occurred during identity document update. API response error:', err);
          this.toastrService.warning(this.translateService.instant('documents.cu-modal.notifications.error.message.edit'), this.translateService.instant('documents.cu-modal.notifications.error.title.edit'));
          this.closeModal();
        }
      }))
    } else {
      //create a new document
      payload.guestId = this.guestId;
      formData.append('payload', new Blob([JSON.stringify(payload)], {type: 'application/json'}));
      console.log('your form data is:', formData);
      this.subscriptions.push(this.documentService.createDocument(formData).subscribe({
        next: (res) => {
          console.info('Document created successfully. API response is:', res);
          this.toastrService.success(this.translateService.instant('documents.cu-modal.notifications.success.message.create'), this.translateService.instant('documents.cu-modal.notifications.success.title.create'));
          this.closeModal();
          this.actionConfirmed.emit();
        },
        error: (err) => {
          console.error('An error occurred during identity document creation. API response error:', err);
          this.toastrService.warning(this.translateService.instant('documents.cu-modal.notifications.error.message.create'), this.translateService.instant('documents.cu-modal.notifications.error.title.create'));
          this.closeModal();
        }
      }))
    }
  }

  closeModal() {
    this.modalRef.hide();
  }

  setDocumentType(documentType: DocumentTypeEnum) {
    this.documentForm.patchValue({
      type: documentType
    })
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      const objectUrl = URL.createObjectURL(file);
      this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectUrl);
      this.file = file;
      this.isNewFile = true;
      this.documentForm.patchValue({
        file: file
      })
    }
  }

  removeImage() {

  }

  ngOnDestroy(): void {
    console.debug("[Document CU Modal] Unsubscribing all subscriptions ...")
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
    this.isNavigating = true;
  }
}
