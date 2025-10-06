import {Component} from '@angular/core';
import {
  ButtonDirective,
  ColComponent,
  FormControlDirective,
  InputGroupComponent,
  InputGroupTextDirective,
  RowComponent,
  SpinnerComponent,
  TableDirective
} from '@coreui/angular';
import {IconDirective} from '@coreui/icons-angular';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {EmptyDataComponent} from '../../../../../../shared/components/empty-data/empty-data.component';
import {TableControlComponent} from '../../../../../../shared/components/table-control/table-control.component';
import {DecimalPipe, NgClass, TitleCasePipe} from '@angular/common';
import {ListContentComponent} from '../../../../../../shared/components/list-content/list-content.component';
import {cilSearch} from '@coreui/icons';
import {ActivatedRoute, Router} from '@angular/router';
import {BsModalService} from 'ngx-bootstrap/modal';
import {VatRuleApiService} from '../../../services/vat-rule-api.service';
import {PageFilterModel} from '../../../../../../shared/models/page-filter.model';
import {BadgeComponent} from '../../../../../../shared/components/badge/badge.component';
import {PostingAccountCuModalComponent} from '../../posting-account-cu-modal/posting-account-cu-modal.component';
import {TaxRuleCuModalComponent} from '../tax-rule-cu-modal/tax-rule-cu-modal.component';
import {ConfirmModalComponent} from '../../../../../../shared/components/confirm-modal/confirm-modal.component';

@Component({
  selector: 'app-tax-rule-list',
  imports: [
    ButtonDirective,
    ColComponent,
    FormControlDirective,
    IconDirective,
    InputGroupComponent,
    InputGroupTextDirective,
    RowComponent,
    TranslatePipe,
    EmptyDataComponent,
    SpinnerComponent,
    TableControlComponent,
    TableDirective,
    TitleCasePipe,
    BadgeComponent,
    DecimalPipe,
    NgClass
  ],
  templateUrl: './tax-rule-list.component.html',
  styleUrl: './tax-rule-list.component.scss',
  providers: [BsModalService],
})
export class TaxRuleListComponent extends ListContentComponent {
  private component = ['TaxRuleListComponent'];
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
              public readonly vatRuleApiService: VatRuleApiService,
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
    console.log(this.component.concat('Retrieving vat rule list ...'))
    let pageFilter: PageFilterModel = {
      page: this.page,
      size: this.size,
      sort: this.sort,
      sortDirection: this.sortDirection,
      search: this.search
    }
    this.subscriptions.push(
      this.vatRuleApiService
        .getAllByPage(pageFilter)
        .subscribe({
          next: (data: any) => {
            super.handleSuccessData(data);
          },
          error: (err: any) => {
            console.warn(this.component.concat('An error occurred when retrieving vat rules. API error response:'), err)
            if (!this.firstCallDone) {
              this.firstCallDone = true;
            }
          }
        })
    );
  }

  openCuModal(vatRule?:any) {
    let initialState = {
      initialState: {
        taxRuleToEdit: vatRule
      }
    }
    let taxRuleCuModalRef = this.modalService.show(TaxRuleCuModalComponent, initialState);
    this.subscriptions.push((taxRuleCuModalRef.content as TaxRuleCuModalComponent).actionConfirmed.subscribe(
      (value) => {
        console.info(this.component.concat('Action confirmed in modal'));
        this.refreshListContent();
      }
    ))
  }

  confirmDeletion(vatRule: any) {
    const initialState = {
      title: this.translateService.instant('administration.accounting.taxes.rules.pages.delete-modal.title'),
      message: this.translateService.instant(
        'administration.accounting.taxes.rules.pages.delete-modal.message').replace(':taxRuleName', vatRule.name)
    };

    const confirmModalRef = this.modalService.show(ConfirmModalComponent, {initialState});

    this.subscriptions.push(
      (confirmModalRef.content as ConfirmModalComponent).actionConfirmed.subscribe(() => {
        this.subscriptions.push(this.vatRuleApiService.deleteById(vatRule.id).subscribe({
          next: () => {
            console.info(this.component.concat('VAT rule:'), vatRule.name, 'removed successfully.')
            this.refreshListContent();
          },
          error: (err) => {
            console.error(this.component.concat('An error occurred when removing vat rule:'),
              vatRule.name, 'API error response is:', err);
          }
        }))
      })
    );
  }
}
