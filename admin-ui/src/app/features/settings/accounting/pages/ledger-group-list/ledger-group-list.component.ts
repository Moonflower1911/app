import {Component} from '@angular/core';
import {ListContentComponent} from '../../../../../shared/components/list-content/list-content.component';
import {cilArrowBottom, cilBath, cilBed, cilChevronRight, cilMediaPlay, cilPen, cilSearch} from '@coreui/icons';
import {ActivatedRoute, Router} from '@angular/router';
import {BsModalService} from 'ngx-bootstrap/modal';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {PageFilterModel} from '../../../../../shared/models/page-filter.model';
import {
    AccordionButtonDirective,
    AccordionComponent,
    AccordionItemComponent,
    ButtonDirective,
    CardBodyComponent,
    CardComponent,
    ColComponent,
    FormControlDirective,
    InputGroupComponent,
    InputGroupTextDirective,
    RowComponent,
    SpinnerComponent,
    TemplateIdDirective
} from '@coreui/angular';
import {IconDirective} from '@coreui/icons-angular';
import {TableControlComponent} from '../../../../../shared/components/table-control/table-control.component';
import {EmptyDataComponent} from '../../../../../shared/components/empty-data/empty-data.component';
import {LedgerGroupApiService} from "../../services/ledger-group-api.service";
import {LedgerGroupCuModalComponent} from "../ledger-group-cu-modal/ledger-group-cu-modal.component";
import {BadgeComponent} from "../../../../../shared/components/badge/badge.component";
import {ConfirmModalComponent} from '../../../../../shared/components/confirm-modal/confirm-modal.component';

@Component({
    selector: 'app-ledger-group-list',
    imports: [
        ButtonDirective,
        ColComponent,
        FormControlDirective,
        IconDirective,
        InputGroupComponent,
        InputGroupTextDirective,
        RowComponent,
        TranslatePipe,
        TableControlComponent,
        SpinnerComponent,
        EmptyDataComponent,
        AccordionComponent,
        AccordionItemComponent,
        AccordionButtonDirective,
        TemplateIdDirective,
        BadgeComponent,
        CardComponent,
        CardBodyComponent,
    ],
    templateUrl: './ledger-group-list.component.html',
    styleUrl: './ledger-group-list.component.scss',
    providers: [BsModalService]
})
export class LedgerGroupListComponent extends ListContentComponent {
    private component = ['LedgerGroupListComponent'];
    openedLedgerGroup: number | null = null;
    icons = {cilSearch, cilBed, cilBath, cilPen, cilMediaPlay, cilArrowBottom, cilChevronRight}
    override listContent: any[] = [];

    override listParamValidator = {
        page: /^[1-9]\d*$/,
        size: ['10', '20', '50', '100'],
        sort: /^(name|enabled|creationDate|modifiedAt),(asc|desc)$/,
        search: /.{3,}/,
    };


    constructor(public override router: Router,
                public override route: ActivatedRoute,
                public readonly ledgerGroupApiService: LedgerGroupApiService,
                public readonly modalService: BsModalService,
                public readonly translateService: TranslateService) {
        super(router, route);
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.sort = 'modifiedAt';
        this.sortDirection = 'desc';
        this.size = 10;
        this.subscribeToQueryParam();
        this.isAdvancedSearchDisplayed = false;
    }

    override retrieveListContent(params: any) {
        super.retrieveListContent(params);
        console.log(this.component.concat('Retrieving ledger group list ...'))
        let pageFilter: PageFilterModel = {
            page: this.page,
            size: this.size,
            sort: this.sort,
            sortDirection: this.sortDirection,
            search: this.search,
            advancedSearchFormValue: {
                withParent: false,
                expanded: true
            }
        }
        this.subscriptions.push(
            this.ledgerGroupApiService
                .getAllByPage(pageFilter)
                .subscribe({
                    next: (data) => {
                        super.handleSuccessData(data);
                    },
                    error: (err: any) => {
                        console.warn(this.component.concat('An error occurred when retrieving ledger group list from API. API error response:'), err)
                        if (!this.firstCallDone) {
                            this.firstCallDone = true;
                        }
                    }
                })
        );
    }

    openCuModal(ledgerGroup?: any, event?: MouseEvent, parentLedger?: any) {
        event?.stopPropagation();

        let payload = null;
        if (ledgerGroup) {
            payload = {...ledgerGroup}
            if (parentLedger != null) {
                payload.parent = {id: parentLedger.id, name: parentLedger.name}
            }
        }

        let initialState = {
            // class: 'modal-lg',
            initialState: {
                ledgerGroupToEdit: payload
            }
        }
        let ledgerGroupCuModalRef = this.modalService.show(LedgerGroupCuModalComponent, initialState);
        this.subscriptions.push((ledgerGroupCuModalRef.content as LedgerGroupCuModalComponent).actionConfirmed.subscribe(
            (value) => {
                console.info(this.component.concat('Action confirmed in modal'));
                this.refreshListContent();
                console.log('your opened ledger is', this.openedLedgerGroup);
                if (this.openedLedgerGroup !== null) {
                    let value = this.openedLedgerGroup;
                    this.openedLedgerGroup = null;
                    this.setOpenedLedgerGroup(value as number);
                }
            }
        ))
    }

    confirmDeletion(ledgerGroup: any, event?: MouseEvent) {
        event?.stopPropagation();

        const initialState = {
            title: this.translateService.instant('administration.accounting.ledger-group.pages.delete-modal.title'),
            message: this.translateService.instant(
                'administration.accounting.ledger-group.pages.delete-modal.message').replace(':ledgerGroupName', ledgerGroup.name)
        };

        const confirmModalRef = this.modalService.show(ConfirmModalComponent, {initialState});

        this.subscriptions.push(
            (confirmModalRef.content as ConfirmModalComponent).actionConfirmed.subscribe(() => {
                this.subscriptions.push(this.ledgerGroupApiService.deleteById(ledgerGroup.id).subscribe({
                    next: () => {
                        console.info(this.component.concat('Ledger Group:'), ledgerGroup.name, 'removed successfully.')
                        this.refreshListContent();
                    },
                    error: (err) => {
                        console.error(this.component.concat('An error occurred when removing ledger group:'),
                            ledgerGroup.name, 'API error response is:', err);
                    }
                }))
            })
        );
    }

    setOpenedLedgerGroup(i: number) {
        if (this.openedLedgerGroup == i) {
            this.openedLedgerGroup = null;
        } else {
            this.openedLedgerGroup = i;
        }
    }
}
