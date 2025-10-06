import {Component} from '@angular/core';
import {
  AvatarComponent,
  ButtonDirective,
  ButtonGroupComponent,
  ColComponent,
  FormControlDirective,
  InputGroupComponent,
  InputGroupTextDirective,
  RowComponent,
  SpinnerComponent,
  TableDirective
} from "@coreui/angular";
import {TranslatePipe} from "@ngx-translate/core";
import {IconDirective} from "@coreui/icons-angular";
import {cilArrowBottom, cilChevronRight, cilCloudDownload, cilMediaPlay, cilSearch} from "@coreui/icons";
import {BsModalService} from "ngx-bootstrap/modal";
import {ListContentComponent} from "../../../../shared/components/list-content/list-content.component";
import {ActivatedRoute, Router} from "@angular/router";
import {AuditNamePipe} from "../../../../shared/pipes/audit-name.pipe";
import {TooltipDirective} from "ngx-bootstrap/tooltip";
import {TableControlComponent} from "../../../../shared/components/table-control/table-control.component";
import {EmptyDataComponent} from "../../../../shared/components/empty-data/empty-data.component";
import {PageTitleComponent} from "../../../../shared/components/page-title/page-title.component";
import {PageFilterModel} from "../../../../shared/models/page-filter.model";
import {UtilsService} from '../../../../shared/services/utils.service';
import {InvoiceItemGetModel} from '../../models/get/invoice-get.model';
import {InvoiceApiService} from '../../services/invoice-api.service';
import {InvoiceStatusEnum} from '../../models/enums/status-enum';
import {NgClass} from '@angular/common';
import {BadgeComponent} from '../../../../shared/components/badge/badge.component';

@Component({
  selector: 'app-invoice-list',
  imports: [
    ButtonDirective,
    ColComponent,
    RowComponent,
    TranslatePipe,
    FormControlDirective,
    IconDirective,
    InputGroupComponent,
    InputGroupTextDirective,
    TableDirective,
    AuditNamePipe,
    AvatarComponent,
    TooltipDirective,
    ButtonGroupComponent,
    TableControlComponent,
    EmptyDataComponent,
    SpinnerComponent,
    PageTitleComponent,
    NgClass,
    BadgeComponent
  ],
  templateUrl: './invoice-list.component.html',
  styleUrl: './invoice-list.component.scss',
  providers: [BsModalService]
})
export class InvoiceListComponent extends ListContentComponent {
  protected readonly UtilsService = UtilsService;
  icons = { cilSearch, cilMediaPlay, cilArrowBottom, cilChevronRight, cilCloudDownload }
  override listContent: InvoiceItemGetModel[] = [];

  override listParamValidator = {
    page: /^[1-9]\d*$/,
    size: ['10', '20', '50', '100'],
    sort: /^(reference|date|status|modifiedAt),(asc|desc)$/,
    search: /.{3,}/,
  };

  constructor(public override router: Router,
              public override route: ActivatedRoute,
              public readonly invoiceApiService: InvoiceApiService) {
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
    console.log('Retrieving invoice list ...')
    let pageFilter: PageFilterModel = {
      page: this.page,
      size: this.size,
      sort: this.sort,
      sortDirection: this.sortDirection,
      search: this.search
    }
    this.subscriptions.push(
      this.invoiceApiService
        .getInvoicesByPage(pageFilter)
        .subscribe({
          next: (data) => {
            super.handleSuccessData(data);
          },
          error: (err: any) => {
            console.warn('An error occurred when retrieving invoice list from API:', err)
            if (!this.firstCallDone) {
              this.firstCallDone = true;
            }
          }
        })
    );
  }

  viewInvoice(invoice: InvoiceItemGetModel) {
    this.router.navigate(['/invoices/', invoice.id]).then(() => console.log('Routing to invoice details page', invoice.reference));
  }

  downloadInvoice(invoice: InvoiceItemGetModel) {
  }

  protected readonly InvoiceStatusEnum = InvoiceStatusEnum;
}
