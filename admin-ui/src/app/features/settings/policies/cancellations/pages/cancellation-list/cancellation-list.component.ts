import {Component} from '@angular/core';
import {PageTitleComponent} from '../../../../../../shared/components/page-title/page-title.component';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
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
import {ListContentComponent} from '../../../../../../shared/components/list-content/list-content.component';
import {cilSearch} from '@coreui/icons';
import {ActivatedRoute, Router} from '@angular/router';
import {BsModalService} from 'ngx-bootstrap/modal';
import {CancellationPolicyApiService} from '../../services/cancellation-policy-api.service';
import {PageFilterModel} from '../../../../../../shared/models/page-filter.model';
import {EmptyDataComponent} from '../../../../../../shared/components/empty-data/empty-data.component';
import {BadgeComponent} from '../../../../../../shared/components/badge/badge.component';
import {DecimalPipe, TitleCasePipe} from '@angular/common';
import {ChargeCuModalComponent} from '../../../../charges/pages/charge-cu-modal/charge-cu-modal.component';
import {CancellationCuModalComponent} from '../cancellation-cu-modal/cancellation-cu-modal.component';

@Component({
  selector: 'app-cancellation-list',
  imports: [
    PageTitleComponent,
    TranslatePipe,
    ButtonDirective,
    ColComponent,
    FormControlDirective,
    IconDirective,
    InputGroupComponent,
    InputGroupTextDirective,
    RowComponent,
    SpinnerComponent,
    EmptyDataComponent,
    TableDirective,
    BadgeComponent,
    TitleCasePipe,
    DecimalPipe
  ],
  templateUrl: './cancellation-list.component.html',
  standalone: true,
  styleUrl: './cancellation-list.component.scss',
  providers: [BsModalService]
})
export class CancellationListComponent extends ListContentComponent {
  private component = '[CancellationListComponent]';
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
              public readonly cancellationPolicyApiService: CancellationPolicyApiService,
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
    console.log(this.component.concat('Retrieving cancellation policies ...'))
    let pageFilter: PageFilterModel = {
      page: this.page,
      size: this.size,
      sort: this.sort,
      sortDirection: this.sortDirection,
      search: this.search
    }
    this.subscriptions.push(
      this.cancellationPolicyApiService
        .getAllByPage(pageFilter)
        .subscribe({
          next: (data: any) => {
            super.handleSuccessData(data);
          },
          error: (err: any) => {
            console.warn(this.component.concat('An error occurred when retrieving cancellation policies. API error response:'), err)
            if (!this.firstCallDone) {
              this.firstCallDone = true;
            }
          }
        })
    );
  }

  openCuModal(policy?: any) {
    let initialState = {
      class: 'modal-lg',
      initialState: {
        cancellationToEdit: policy
      }
    }
    let cancellationCuModalRef = this.modalService.show(CancellationCuModalComponent, initialState);
    this.subscriptions.push((cancellationCuModalRef.content as CancellationCuModalComponent).actionConfirmed.subscribe(
      (value) => {
        console.info(this.component.concat('Action confirmed in modal'));
        this.refreshListContent();
      }
    ))
  }
}
