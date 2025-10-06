import {Component} from '@angular/core';
import {PageTitleComponent} from '../../../../../shared/components/page-title/page-title.component';
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
import {ListContentComponent} from '../../../../../shared/components/list-content/list-content.component';
import {cilSearch} from '@coreui/icons';
import {ActivatedRoute, Router} from '@angular/router';
import {BsModalService} from 'ngx-bootstrap/modal';
import {ChargeApiService} from '../../services/charge-api.service';
import {PageFilterModel} from '../../../../../shared/models/page-filter.model';
import {EmptyDataComponent} from '../../../../../shared/components/empty-data/empty-data.component';
import {BadgeComponent} from '../../../../../shared/components/badge/badge.component';
import {DecimalPipe, TitleCasePipe} from '@angular/common';
import {ChargeCuModalComponent} from '../charge-cu-modal/charge-cu-modal.component';
import {TableControlComponent} from '../../../../../shared/components/table-control/table-control.component';
import {PaginationComponent} from 'ngx-bootstrap/pagination';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-charge-list',
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
    DecimalPipe,
    TitleCasePipe,
    TableControlComponent,
    PaginationComponent,
    FormsModule,

  ],
  templateUrl: './charge-list.component.html',
  styleUrl: './charge-list.component.scss',
  standalone: true,
  providers: [BsModalService]
})
export class ChargeListComponent extends ListContentComponent {
  private component = '[ChargeListComponent]';
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
              public readonly chargeApiService: ChargeApiService,
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
    console.log(this.component.concat('Retrieving charges list ...'))
    let pageFilter: PageFilterModel = {
      page: this.page,
      size: this.size,
      sort: this.sort,
      sortDirection: this.sortDirection,
      search: this.search
    }
    this.subscriptions.push(
      this.chargeApiService
        .getAllByPage(pageFilter)
        .subscribe({
          next: (data: any) => {
            super.handleSuccessData(data);
          },
          error: (err: any) => {
            console.warn(this.component.concat('An error occurred when retrieving charges. API error response:'), err)
            if (!this.firstCallDone) {
              this.firstCallDone = true;
            }
          }
        })
    );
  }

  openCuModal(charge?: any) {
    let initialState = {
      class: 'modal-lg',
      initialState: {
        chargeToEdit: charge
      }
    }
    let chargeCuModalRef = this.modalService.show(ChargeCuModalComponent, initialState);
    this.subscriptions.push((chargeCuModalRef.content as ChargeCuModalComponent).actionConfirmed.subscribe(
      (value) => {
        console.info(this.component.concat('Action confirmed in modal'));
        this.refreshListContent();
      }
    ))
  }
}
