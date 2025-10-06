import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {ToastrService} from 'ngx-toastr';
import {LedgerGroupApiService} from '../../services/ledger-group-api.service';
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
import {
    LedgerGroupSelectComponent
} from '../../../../../shared/components/ledger-group-select/ledger-group-select.component';

@Component({
    selector: 'app-ledger-group-cu-modal',
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
        FormCheckLabelDirective,
        LedgerGroupSelectComponent
    ],
    templateUrl: './ledger-group-cu-modal.component.html',
    styleUrl: './ledger-group-cu-modal.component.scss'
})
export class LedgerGroupCuModalComponent implements OnInit, OnDestroy {

    private component = '[LedgerGroupCuModalComponent]: ';
    ledgerGroupToEdit!: any;
    ledgerGroupForm: FormGroup;
    @Output() actionConfirmed = new EventEmitter<any>();
    private readonly subscriptions: Subscription[] = [];

    public constructor(private readonly fb: FormBuilder,
                       private readonly modalRef: BsModalRef,
                       private readonly ledgerGroupApiService: LedgerGroupApiService,
                       private readonly translateService: TranslateService,
                       private readonly toastrService: ToastrService) {
        this.ledgerGroupForm = this.fb.group({
            name: [null, [Validators.required]],
            description: [null, [Validators.maxLength(255)]],
            parent: [null],
            enabled: [true, [Validators.required]]
        })
    }

    ngOnInit(): void {
        if (this.ledgerGroupToEdit) {
            this.ledgerGroupForm.patchValue({
                name: this.ledgerGroupToEdit.name,
                description: this.ledgerGroupToEdit.description,
                enabled: this.ledgerGroupToEdit.enabled,
                parent: this.ledgerGroupToEdit.parent
            })
        }
    }

    submit() {
        let payload: any = {
            name: this.ledgerGroupForm.value.name,
            description: this.ledgerGroupForm.value.description,
            enabled: this.ledgerGroupForm.value.enabled
        }
        if (this.ledgerGroupForm.value.parent != null) {
            payload.parentId = this.ledgerGroupForm.value.parent.id;
        } else {
            payload.parentId = '';
        }
        if (!this.ledgerGroupToEdit) {
            this.subscriptions.push(this.ledgerGroupApiService.post(payload).subscribe({
                next: (data) => {
                    console.log(this.component.concat('Ledger Group created successfully. API response is:'), data);
                    this.actionConfirmed.emit();
                    this.closeModal();
                },
                error: (err) => {
                    console.error(this.component.concat('An error occurred when creating ledger group. API error is:'), err);
                    //TODO: add toastr
                }
            }))
        } else {
            this.subscriptions.push(this.ledgerGroupApiService.patchById(this.ledgerGroupToEdit.id, payload).subscribe({
                next: (data) => {
                    console.log(this.component.concat('Ledger Group:'), this.ledgerGroupToEdit.name,
                        'updated successfully. API response is:', data);
                    this.actionConfirmed.emit();
                    this.closeModal();
                },
                error: (err) => {
                    console.error(this.component.concat('An error occurred when updating ledger group:',
                        this.ledgerGroupToEdit.name, 'API error is:'), err);
                    //TODO: add toastr
                }
            }))
        }
    }

    closeModal() {
        this.modalRef.hide();
        this.ledgerGroupForm.reset();
    }

    ngOnDestroy(): void {
        console.debug(this.component.concat('Unsubscribing all subscriptions ...'))
        this.subscriptions.forEach(subscription => subscription.unsubscribe());
    }

}
