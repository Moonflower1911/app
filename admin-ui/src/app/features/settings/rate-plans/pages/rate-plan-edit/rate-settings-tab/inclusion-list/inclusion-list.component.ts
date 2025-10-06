import {Component, Input} from '@angular/core';
import {
  ButtonDirective,
  ColComponent,
  FormControlDirective,
  InputGroupComponent,
  InputGroupTextDirective,
  RowComponent,
  SpinnerComponent,
  TableDirective
} from "@coreui/angular";
import {TranslatePipe, TranslateService} from "@ngx-translate/core";
import {BsModalService} from 'ngx-bootstrap/modal';
import {PageFilterModel} from '../../../../../../../shared/models/page-filter.model';
import {ActivatedRoute, Router} from '@angular/router';
import {IconDirective} from '@coreui/icons-angular';
import {TableControlComponent} from '../../../../../../../shared/components/table-control/table-control.component';
import {EmptyDataComponent} from '../../../../../../../shared/components/empty-data/empty-data.component';
import {ListContentComponent} from '../../../../../../../shared/components/list-content/list-content.component';
import {cilSearch} from '@coreui/icons';
import {InclusionApiService} from '../../../../services/inclusion-api.service';
import {BadgeComponent} from '../../../../../../../shared/components/badge/badge.component';
import {DecimalPipe} from '@angular/common';
import {InclusionCreateModalComponent} from '../inclusion-create-modal/inclusion-create-modal.component';
import {ConfirmModalComponent} from '../../../../../../../shared/components/confirm-modal/confirm-modal.component';

@Component({
  selector: 'app-inclusion-list',
  imports: [
    TableDirective,
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
    BadgeComponent,
    DecimalPipe
  ],
  templateUrl: './inclusion-list.component.html',
  styleUrl: './inclusion-list.component.scss',
  providers: [BsModalService],
  standalone: true
})
export class InclusionListComponent extends ListContentComponent {
  private component = '[InclusionListComponent]: ';
  @Input()
  ratePlanId!: string;
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
              private readonly activatedRoute: ActivatedRoute,
              public readonly inclusionApiService: InclusionApiService,
              public readonly modalService: BsModalService,
              public readonly translateService: TranslateService) {
    super(router, route);
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.sort = 'modifiedAt';
    this.sortDirection = 'desc';
    this.size = 10;
    this.isAdvancedSearchDisplayed = false;
    this.subscribeToQueryParam();

  }


  override retrieveListContent(params: any) {
    super.retrieveListContent(params);
    console.log(this.component.concat('Retrieving inclusion list ...'))
    let pageFilter: PageFilterModel = {
      page: this.page,
      size: this.size,
      sort: this.sort,
      sortDirection: this.sortDirection,
      search: this.search,
      advancedSearchFormValue: {
        ratePlanId: this.ratePlanId
      }
    }
    this.subscriptions.push(
      this.inclusionApiService
        .getAllByPage(pageFilter)
        .subscribe({
          next: (data) => {
            super.handleSuccessData(data);
          },
          error: (err: any) => {
            console.warn(this.component.concat('An error occurred when retrieving inclusion list from API. API error response:'), err)
            if (!this.firstCallDone) {
              this.firstCallDone = true;
            }
          }
        })
    );
  }

  openAddModal() {
    let initialState = {
      initialState: {
        ratePlanId: this.ratePlanId
      }
    }
    let inclusionAddModalRef = this.modalService.show(InclusionCreateModalComponent, initialState);
    this.subscriptions.push((inclusionAddModalRef.content as InclusionCreateModalComponent).actionConfirmed.subscribe(
      (value) => {
        console.info(this.component.concat('Action confirmed in modal'));
        this.refreshListContent();
      }
    ))
  }

  confirmDeletion(inclusion: any) {
    const initialState = {
      title: this.translateService.instant('rate-plan.pages.settings.inclusions-section.pages.delete-modal.title'),
      message: this.translateService.instant(
        'rate-plan.pages.settings.inclusions-section.pages.delete-modal.message')
    };

    const confirmModalRef = this.modalService.show(ConfirmModalComponent, {initialState});

    this.subscriptions.push(
      (confirmModalRef.content as ConfirmModalComponent).actionConfirmed.subscribe(() => {
        this.subscriptions.push(this.inclusionApiService.deleteById(inclusion.id).subscribe({
          next: () => {
            console.info(this.component.concat('Inclusion removed successfully from this rate plan'));
            this.refreshListContent();
          },
          error: (err) => {
            console.error(this.component.concat('An error occurred when removing inclusion:'),
              inclusion.name, 'API error response is:', err);
          }
        }))
      })
    );
  }
}
