import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {cilSearch} from '@coreui/icons';
import {
  ButtonDirective,
  ColComponent,
  FormControlDirective,
  InputGroupComponent,
  InputGroupTextDirective,
  ModalModule,
  RowComponent,
  SpinnerComponent,
  TableDirective,
} from '@coreui/angular';
import {IconDirective} from '@coreui/icons-angular';
import {CommonModule} from '@angular/common';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {PostingAccountApiService} from '../../services/posting-account-api.service';
import {BsModalService} from 'ngx-bootstrap/modal';
import {ListContentComponent} from '../../../../../shared/components/list-content/list-content.component';
import {PostingAccountCuModalComponent} from '../posting-account-cu-modal/posting-account-cu-modal.component';
import {TableControlComponent} from '../../../../../shared/components/table-control/table-control.component';
import {EmptyDataComponent} from '../../../../../shared/components/empty-data/empty-data.component';
import {PageFilterModel} from '../../../../../shared/models/page-filter.model';
import {BadgeComponent} from '../../../../../shared/components/badge/badge.component';
import {ConfirmModalComponent} from '../../../../../shared/components/confirm-modal/confirm-modal.component';

@Component({
  selector: 'app-posting-account-list',
  templateUrl: './posting-account-list.component.html',
  standalone: true,
  imports: [
    CommonModule,
    RowComponent,
    ColComponent,
    InputGroupComponent,
    FormsModule,
    IconDirective,
    TranslatePipe,
    ButtonDirective,
    FormControlDirective,
    InputGroupTextDirective,
    ModalModule,
    ReactiveFormsModule,
    TableControlComponent,
    SpinnerComponent,
    EmptyDataComponent,
    TableDirective,
    BadgeComponent
  ],
  styleUrls: ['./posting-account-list.component.scss'],
  providers: [BsModalService]
})
export class PostingAccountListComponent extends ListContentComponent {
  private component = ['PostingAccountListComponent'];
  icons = {cilSearch}
  override listContent: any[] = [];

  override listParamValidator = {
    page: /^[1-9]\d*$/,
    size: ['10', '20', '50', '100'],
    sort: /^(name|enabled|creationDate|modifiedAt),(asc|desc)$/,
    search: /.{3,}/,
  };

  constructor(public override router: Router,
              public override route: ActivatedRoute,
              public readonly postingAccountApiService: PostingAccountApiService,
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
    console.log(this.component.concat('Retrieving posting account list ...'))
    let pageFilter: PageFilterModel = {
      page: this.page,
      size: this.size,
      sort: this.sort,
      sortDirection: this.sortDirection,
      search: this.search
    }
    this.subscriptions.push(
      this.postingAccountApiService
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

  openCuModal(postingAccount?: any) {

    let initialState = {
      // class: 'modal-lg',
      initialState: {
        postingAccountToEdit: postingAccount
      }
    }
    let postingAccountCuModalRef = this.modalService.show(PostingAccountCuModalComponent, initialState);
    this.subscriptions.push((postingAccountCuModalRef.content as PostingAccountCuModalComponent).actionConfirmed.subscribe(
      (value) => {
        console.info(this.component.concat('Action confirmed in modal'));
        this.refreshListContent();
      }
    ))
  }

  confirmDeletion(postingAccount: any) {
    const initialState = {
      title: this.translateService.instant('administration.accounting.posting-account.pages.delete-modal.title'),
      message: this.translateService.instant(
        'administration.accounting.posting-account.pages.delete-modal.message').replace(':postingAccountName', postingAccount.name)
    };

    const confirmModalRef = this.modalService.show(ConfirmModalComponent, {initialState});

    this.subscriptions.push(
      (confirmModalRef.content as ConfirmModalComponent).actionConfirmed.subscribe(() => {
        this.subscriptions.push(this.postingAccountApiService.deleteById(postingAccount.id).subscribe({
          next: () => {
            console.info(this.component.concat('Posting Account:'), postingAccount.name, 'removed successfully.')
            this.refreshListContent();
          },
          error: (err) => {
            console.error(this.component.concat('An error occurred when removing posting account:'),
              postingAccount.name, 'API error response is:', err);
          }
        }))
      })
    );
  }

}
