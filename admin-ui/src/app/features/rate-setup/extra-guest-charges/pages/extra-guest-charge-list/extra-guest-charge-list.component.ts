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
import {TableControlComponent} from '../../../../../shared/components/table-control/table-control.component';
import {EmptyDataComponent} from '../../../../../shared/components/empty-data/empty-data.component';
import {ListContentComponent} from '../../../../../shared/components/list-content/list-content.component';
import {cilSearch} from '@coreui/icons';
import {ActivatedRoute, Router} from '@angular/router';
import {BsModalService} from 'ngx-bootstrap/modal';
import {ExtraGuestChargeApiService} from '../../services/extra-guest-charge-api.service';
import {PageFilterModel} from '../../../../../shared/models/page-filter.model';
import {ExtraGuestChargeCuModalComponent} from '../extra-guest-charge-cu-modal/extra-guest-charge-cu-modal.component';
import {SlicePipe} from '@angular/common';
import {BadgeComponent} from '../../../../../shared/components/badge/badge.component';

@Component({
  selector: 'app-extra-guest-charge-list',
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
    TableControlComponent,
    SpinnerComponent,
    EmptyDataComponent,
    TableDirective,
    SlicePipe,
    BadgeComponent
  ],
  templateUrl: './extra-guest-charge-list.component.html',
  standalone: true,
  styleUrl: './extra-guest-charge-list.component.scss',
  providers: [BsModalService]
})
export class ExtraGuestChargeListComponent extends ListContentComponent {
  private component = ['ExtraGuestChargeListComponent'];
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
              public readonly extraGuestChargeApiService: ExtraGuestChargeApiService,
              public readonly modalService: BsModalService,
              public readonly translateService: TranslateService) {
    super(router, route);
  }

  //
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
    console.log(this.component.concat('Retrieving extra guest charges list ...'))
    let pageFilter: PageFilterModel = {
      page: this.page,
      size: this.size,
      sort: this.sort,
      sortDirection: this.sortDirection,
      search: this.search
    }
    this.subscriptions.push(
      this.extraGuestChargeApiService
        .getAllByPage(pageFilter)
        .subscribe({
          next: (data: any) => {
            super.handleSuccessData(data);
          },
          error: (err: any) => {
            console.warn(this.component.concat('An error occurred when retrieving the extra guest charges. API error response:'), err)
            if (!this.firstCallDone) {
              this.firstCallDone = true;
            }
          }
        })
    );
  }

  openCuModal(extraGuestCharge?: any) {
    console.log('value is:', extraGuestCharge);
    let initialState = {
      class: 'modal-lg',
      initialState:{
        extraGuestChargeToEdit: extraGuestCharge
      }
    }
    let extraGuestChargeCuModalRef = this.modalService.show(ExtraGuestChargeCuModalComponent, initialState);
    this.subscriptions.push((extraGuestChargeCuModalRef.content as ExtraGuestChargeCuModalComponent).actionConfirmed.subscribe(
      (value) => {
        console.info(this.component.concat('Action confirmed in modal'));
        this.refreshListContent();
      }
    ))
  }

}
