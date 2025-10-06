import {Component} from '@angular/core';
import {ListContentComponent} from '../../../../../shared/components/list-content/list-content.component';
import {ActivatedRoute, Router} from '@angular/router';
import {BsModalService} from 'ngx-bootstrap/modal';
import {PageFilterModel} from '../../../../../shared/models/page-filter.model';
import {AccountClassApiService} from '../../services/account-class-api.service';
import {
    ButtonDirective,
    CardBodyComponent,
    CardComponent,
    CardFooterComponent,
    CardTextDirective,
    CardTitleDirective,
    ColComponent,
    FormControlDirective,
    GutterDirective,
    InputGroupComponent,
    InputGroupTextDirective,
    RowComponent,
    SpinnerComponent
} from '@coreui/angular';
import {IconDirective} from '@coreui/icons-angular';
import {TableControlComponent} from '../../../../../shared/components/table-control/table-control.component';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {cilArrowBottom, cilBath, cilBed, cilChevronRight, cilMediaPlay, cilPen, cilSearch} from '@coreui/icons';
import {EmptyDataComponent} from '../../../../../shared/components/empty-data/empty-data.component';
import {NgClass, SlicePipe, TitleCasePipe} from '@angular/common';
import {BadgeComponent} from '../../../../../shared/components/badge/badge.component';
import {AccountClassCuModalComponent} from "../account-class-cu-modal/account-class-cu-modal.component";
import {ConfirmModalComponent} from "../../../../../shared/components/confirm-modal/confirm-modal.component";

@Component({
    selector: 'app-account-class-list',
    imports: [
        ColComponent,
        FormControlDirective,
        IconDirective,
        InputGroupComponent,
        InputGroupTextDirective,
        RowComponent,
        TableControlComponent,
        TranslatePipe,
        ButtonDirective,
        SpinnerComponent,
        EmptyDataComponent,
        GutterDirective,
        CardComponent,
        CardBodyComponent,
        CardTitleDirective,
        CardTextDirective,
        TitleCasePipe,
        SlicePipe,
        CardFooterComponent,
        BadgeComponent,
        NgClass
    ],
    templateUrl: './account-class-list.component.html',
    styleUrl: './account-class-list.component.scss',
    providers: [BsModalService]
})
export class AccountClassListComponent extends ListContentComponent {
    private component = '[AccountClassListComponent]: ';
    icons = {cilSearch, cilBed, cilBath, cilPen, cilMediaPlay, cilArrowBottom, cilChevronRight}
    override listContent: any[] = [];

    override listParamValidator = {
        page: /^[1-9]\d*$/,
        size: ['10', '20', '50', '100'],
        sort: /^(name|type|website|status|creationDate|modifiedAt),(asc|desc)$/,
        search: /.{3,}/,
    };

    constructor(public override router: Router,
                public override route: ActivatedRoute,
                public readonly accountClassApiService: AccountClassApiService,
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
        console.log(this.component.concat('Retrieving account class list ...'))
        let pageFilter: PageFilterModel = {
            page: this.page,
            size: this.size,
            sort: this.sort,
            sortDirection: this.sortDirection,
            search: this.search
        }
        this.subscriptions.push(
            this.accountClassApiService
                .getAllByPage(pageFilter)
                .subscribe({
                    next: (data) => {
                        super.handleSuccessData(data);
                    },
                    error: (err: any) => {
                        console.warn(this.component.concat('An error occurred when retrieving account list from API. API error response:'), err)
                        if (!this.firstCallDone) {
                            this.firstCallDone = true;
                        }
                    }
                })
        );
    }

    openCuModal(accountClass?: any) {
        let initialState = {
            // class: 'modal-lg',
            initialState: {
                accountClassToEdit: accountClass
            }
        }
        let accountClassCuModalRef = this.modalService.show(AccountClassCuModalComponent, initialState);
        this.subscriptions.push((accountClassCuModalRef.content as AccountClassCuModalComponent).actionConfirmed.subscribe(
            (value) => {
                console.info(this.component.concat('Action confirmed in modal'));
                this.refreshListContent();
            }
        ))
    }

    confirmDeletion(accountClass: any) {
        const initialState = {
            title: this.translateService.instant('administration.accounting.account-class.pages.delete-modal.title'),
            message: this.translateService.instant(
                'administration.accounting.account-class.pages.delete-modal.message').replace(':accountClassName', accountClass.name)
        };

        const confirmModalRef = this.modalService.show(ConfirmModalComponent, {initialState});

        this.subscriptions.push(
            (confirmModalRef.content as ConfirmModalComponent).actionConfirmed.subscribe(() => {
                this.subscriptions.push(this.accountClassApiService.deleteById(accountClass.id).subscribe({
                    next: () => {
                        console.info(this.component.concat('Account class:'), accountClass.name, 'removed successfully.')
                        this.refreshListContent();
                    },
                    error: (err) => {
                        console.error(this.component.concat('An error occurred when removing account class:'),
                            accountClass.name, 'API error response is:', err);
                    }
                }))
            })
        );
    }
}
