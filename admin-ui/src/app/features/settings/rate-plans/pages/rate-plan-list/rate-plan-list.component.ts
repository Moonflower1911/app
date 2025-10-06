import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {
  ButtonDirective,
  ColComponent,
  FormControlDirective,
  InputGroupComponent,
  InputGroupTextDirective,
  ModalModule,
  RowComponent,
  SpinnerComponent,
  TableDirective
} from '@coreui/angular';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {IconDirective} from '@coreui/icons-angular';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {TableControlComponent} from '../../../../../shared/components/table-control/table-control.component';
import {EmptyDataComponent} from '../../../../../shared/components/empty-data/empty-data.component';
import {BadgeComponent} from '../../../../../shared/components/badge/badge.component';
import {BsModalService} from 'ngx-bootstrap/modal';
import {ListContentComponent} from '../../../../../shared/components/list-content/list-content.component';
import {cilSearch} from '@coreui/icons';
import {ActivatedRoute, Router} from '@angular/router';
import {PageFilterModel} from '../../../../../shared/models/page-filter.model';
import {PageTitleComponent} from '../../../../../shared/components/page-title/page-title.component';
import {RatePlanCreateModalComponent} from '../rate-plan-create-modal/rate-plan-create-modal.component';
import {TooltipDirective} from 'ngx-bootstrap/tooltip';
import {RatePlanApiService} from '../../services/rate-plan-api.service';

@Component({
  selector: 'app-rate-plan-list',
  templateUrl: './rate-plan-list.component.html',
  styleUrl: './rate-plan-list.component.scss',
  standalone: true,
  providers: [BsModalService],
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
    BadgeComponent,
    PageTitleComponent,
    TooltipDirective
  ],
})
export class RatePlanListComponent extends ListContentComponent {
  private component = ['RateListComponent'];
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
              public readonly ratePlanApiService: RatePlanApiService,
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
    console.log(this.component.concat('Retrieving rate plan list ...'))
    let pageFilter: PageFilterModel = {
      page: this.page,
      size: this.size,
      sort: this.sort,
      sortDirection: this.sortDirection,
      search: this.search
    }
    this.subscriptions.push(
      this.ratePlanApiService
        .getAllByPage(pageFilter)
        .subscribe({
          next: (data: any) => {
            super.handleSuccessData(data);
          },
          error: (err: any) => {
            console.warn(this.component.concat('An error occurred when retrieving the rate plans. API error response:'), err)
            if (!this.firstCallDone) {
              this.firstCallDone = true;
            }
          }
        })
    );
  }


  goToEdit(ratePlan: any): void {
    this.router.navigate(['/settings/rate-plans', ratePlan.id], {state: {ratePlan}})
      .then(() => console.log('Navigated to edit page for', ratePlan.name));
  }


  openCreateModal() {
    let ratePlanCreateModalRef = this.modalService.show(RatePlanCreateModalComponent);
    this.subscriptions.push((ratePlanCreateModalRef.content as RatePlanCreateModalComponent).actionConfirmed.subscribe(
      (value) => {
        console.info(this.component.concat('Action confirmed in modal'));
        this.refreshListContent();
      }
    ))
  }

  getTooltipText(units: any[]): string {
    if (!units || units.length <= 1) return '';
    return units.slice(1).map(i => i.name).join(', ');
  }

}
