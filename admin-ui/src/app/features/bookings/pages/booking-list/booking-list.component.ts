import {Component} from '@angular/core';
import {ListContentComponent} from '../../../../shared/components/list-content/list-content.component';
import {cilPlus, cilSearch} from '@coreui/icons';
import {BookingItemGetModel} from '../../models/booking-item-get.model';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {BookingApiService} from '../../services/booking-api.service';
import {PageFilterModel} from '../../../../shared/models/page-filter.model';
import {PageTitleComponent} from '../../../../shared/components/page-title/page-title.component';
import {TranslatePipe} from '@ngx-translate/core';
import {
  AvatarComponent,
  ButtonDirective,
  CardBodyComponent,
  CardComponent,
  CardHeaderComponent,
  ColComponent,
  FormControlDirective,
  InputGroupComponent,
  InputGroupTextDirective,
  RowComponent,
  SpinnerComponent,
  TableDirective
} from '@coreui/angular';
import {IconDirective} from '@coreui/icons-angular';
import {TableControlComponent} from '../../../../shared/components/table-control/table-control.component';
import {EmptyDataComponent} from '../../../../shared/components/empty-data/empty-data.component';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-booking-list',
  imports: [
    PageTitleComponent,
    TranslatePipe,
    ColComponent,
    FormControlDirective,
    IconDirective,
    InputGroupComponent,
    InputGroupTextDirective,
    RowComponent,
    CardComponent,
    CardBodyComponent,
    CardHeaderComponent,
    TableControlComponent,
    SpinnerComponent,
    EmptyDataComponent,
    TableDirective,
    AvatarComponent,
    ButtonDirective,
    DatePipe,
    RouterLink
  ],
  templateUrl: './booking-list.component.html',
  styleUrl: './booking-list.component.scss'
})
export class BookingListComponent extends ListContentComponent {

  icons = {cilSearch, cilPlus}

  override listContent: BookingItemGetModel[] = [];

  override listParamValidator = {
    page: /^[1-9]\d*$/,
    size: ['10', '20', '50', '100'],
    sort: /^(reference|status|creationDate|modifiedAt),(asc|desc)$/,
    search: /.{3,}/,
  };

  constructor(public override router: Router,
              public override route: ActivatedRoute,
              public readonly bookingApiService: BookingApiService) {
    super(router, route);
  }

  override ngOnInit() {
    super.ngOnInit();
    this.sort = 'modifiedAt';
    this.sortDirection = 'desc';
    this.size = 10;
    this.subscribeToQueryParam();
    this.isAdvancedSearchDisplayed = false;
  }

  override retrieveListContent(params: any) {
    super.retrieveListContent(params);
    console.log('Retrieving booking list ...')
    let pageFilter: PageFilterModel = {
      page: this.page,
      size: this.size,
      sort: this.sort,
      sortDirection: this.sortDirection,
      search: this.search,
      advancedSearchFormValue:{
        types: 'SINGLE'
      }
    }
    this.subscriptions.push(
      this.bookingApiService.getAllByPage(pageFilter).subscribe({
        next: (data) => {
          console.log('bookings retrieved successfully', data);
          super.handleSuccessData(data);
        },
        error: (err) => {
          console.error('An error occurred when retrieving bookings', err);
        }
      })
    )
  }


  routeToDetails(bookingId: string) {
    this.router.navigate(['/bookings/', bookingId]).then(() => console.log('Routed to details page'));

  }
}
