import {Component, OnDestroy} from '@angular/core';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {cilSearch} from '@coreui/icons';
import {ActivatedRoute} from '@angular/router';
import {BsModalService} from 'ngx-bootstrap/modal';
import {BehaviorSubject, combineLatest, debounceTime, distinctUntilChanged, pipe, skip, Subscription} from 'rxjs';
import {PageFilterModel} from '../../../../../../../shared/models/page-filter.model';
import {
  ButtonDirective,
  ColComponent,
  FormControlDirective,
  InputGroupComponent,
  InputGroupTextDirective,
  ListGroupDirective,
  ListGroupItemDirective,
  RowComponent,
  SpinnerComponent
} from '@coreui/angular';
import {IconDirective} from '@coreui/icons-angular';
import {filter, tap} from 'rxjs/operators';
import {RatePlanUnitApiService} from '../../../../services/rate-plan-unit-api.service';
import {TableControlComponent} from '../../../../../../../shared/components/table-control/table-control.component';
import {EmptyDataComponent} from '../../../../../../../shared/components/empty-data/empty-data.component';
import {BadgeComponent} from '../../../../../../../shared/components/badge/badge.component';
import {ConfirmModalComponent} from '../../../../../../../shared/components/confirm-modal/confirm-modal.component';
import {UnitAddModalComponent} from '../unit-add-modal/unit-add-modal.component';

@Component({
  selector: 'app-unit-list',
  imports: [
    TranslatePipe,
    RowComponent,
    ColComponent,
    InputGroupComponent,
    IconDirective,
    InputGroupTextDirective,
    FormControlDirective,
    ButtonDirective,
    TableControlComponent,
    SpinnerComponent,
    EmptyDataComponent,
    ListGroupDirective,
    ListGroupItemDirective,
    BadgeComponent
  ],
  templateUrl: './unit-list.component.html',
  styleUrl: './unit-list.component.scss',
  providers: [BsModalService],
  standalone: true
})
export class UnitListComponent implements OnDestroy {
  private component = '[RatePlanUnitListComponent]: ';
  ratePlanId!: string;
  icons = {cilSearch}
  listContent: any[] = [];
  isListEmpty: boolean = true;
  firstCallDone: boolean = false;
  hasNext: boolean = false;
  hasPrevious: boolean = false;
  totalElements!: number;
  numberOfElements!: number;
  currentNumberOfElements!: number;
  pageDisplayed = 1;
  page: number = 0;
  size = 10;
  sort = 'modifiedAt';
  sortDirection = 'desc';
  search!: string;
  basicSearch: string = '';
  isSearchActive: boolean = false;
  private readonly $basicSearchSubject: BehaviorSubject<string> = new BehaviorSubject<string>('');
  private readonly subscriptions: Subscription[] = [];

  constructor(private activatedRoute: ActivatedRoute,
              private readonly ratePlanUnitApiService: RatePlanUnitApiService,
              private modalService: BsModalService,
              private translateService: TranslateService) {
    this.subscribeToBasicSearch();
    this.subscriptions.push(
      combineLatest(
        this.activatedRoute.pathFromRoot.map(route => route.paramMap)
      ).subscribe(paramMaps => {
        const ratePlanId = paramMaps
          .map(paramMap => paramMap.get('ratePlanId'))
          .find(id => id !== null);
        if (ratePlanId) {
          this.ratePlanId = ratePlanId;
          this.retrieveListContent();
        }
      })
    );
  }

  retrieveListContent() {
    console.log(this.component.concat('Retrieving units list ...'))
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
      this.ratePlanUnitApiService
        .getAllByPage(this.ratePlanId, pageFilter)
        .subscribe({
          next: (data) => {
            console.info(this.component.concat('Units retrieved successfully. API response is:'), data);
            this.listContent = data.content;
            this.totalElements = data.totalElements;
            this.numberOfElements = data.numberOfElements;
            this.currentNumberOfElements = data.pageable.offset + data.numberOfElements;
            this.isListEmpty = data.empty;
            this.hasNext = !data.last;
            this.hasPrevious = !data.first;
            this.firstCallDone = true;
          },
          error: (err: any) => {
            console.warn(this.component.concat('An error occurred when retrieving units list from API. API error response:'), err)
            if (!this.firstCallDone) {
              this.firstCallDone = true;
            }
          }
        })
    );
  }

  triggerBasicSearch(event: any): void {
    let value = (event.target as HTMLInputElement)?.value;
    if (value) {
      value = value.trim();
    }
    console.log('Triggering basic search with value:', value);  // Add this log
    this.$basicSearchSubject.next(value);
  }

  private subscribeToBasicSearch(): void {
    this.subscriptions.push(
      this.$basicSearchSubject.pipe(
        pipe(
          skip(1),
          debounceTime(300),
        ),
        distinctUntilChanged(),
        filter(value => value !== null),
        tap(value => {
          if (value) {
            this.basicSearch = value;
          }
        })
      ).subscribe(value => {
        this.basicSearchCompany(value);
      })
    );
  }

  basicSearchCompany(value: string): void {
    console.log('Basic search value:', value);  // Log the value here too

    if (value && value.length >= 1) {
      this.isSearchActive = true;
      this.page = 0;
      this.pageDisplayed = 1;
      this.search = value;
      this.retrieveListContent();
    } else if (this.isSearchActive) {
      // No need to check `(!value || value.length === 0)` again since we're in the `else` block.
      this.isSearchActive = false;
      this.page = 0;
      this.pageDisplayed = 1;
      this.search = '';
      this.retrieveListContent();
    }
  }

  previous() {
    this.pageDisplayed = this.pageDisplayed - 1;
    this.page = this.pageDisplayed - 1;
  }

  next() {
    this.pageDisplayed = this.pageDisplayed + 1;
    this.page = this.pageDisplayed - 1;
  }

  changeSize($event: any): void {
    console.log('your event is:', $event);
    this.page = 0;
    this.pageDisplayed = 1;
    this.size = $event;
  }

  ngOnDestroy(): void {
    console.log(this.component.concat('Unsubscribing all subscription ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  openAddModal() {
    let initialState = {
      initialState: {
        ratePlanId: this.ratePlanId
      }
    }
    let unitAddModalRef = this.modalService.show(UnitAddModalComponent, initialState);
    this.subscriptions.push((unitAddModalRef.content as UnitAddModalComponent).actionConfirmed.subscribe(
      (value) => {
        console.info(this.component.concat('Action confirmed in modal'));
        this.pageDisplayed = 1;
        this.page = 0;
        this.retrieveListContent();
      }
    ))
  }

  updateUnit(unit: any) {
    const initialState = {
      title: this.translateService.instant(('settings.rate-plan.pages.settings.units-section.pages.edit-modal.title.' + (unit.enabled ? 'disable' : 'enable'))),
      message: this.translateService.instant(
        ('settings.rate-plan.pages.settings.units-section.pages.edit-modal.message.' + (unit.enabled ? 'disable' : 'enable')))
    };
    const confirmModalRef = this.modalService.show(ConfirmModalComponent, {initialState});
    this.subscriptions.push(
      (confirmModalRef.content as ConfirmModalComponent).actionConfirmed.subscribe(() => {
        let payload = {
          enabled: !unit.enabled
        }
        this.subscriptions.push(this.ratePlanUnitApiService.patchById(unit.id, payload).subscribe({
          next: (data: any) => {
            console.info(this.component.concat('Rate plan unit updated successfully. API response is:'), data);
            this.pageDisplayed = 1;
            this.page = 0;
            this.retrieveListContent();
          },
          error: (err) => {
            console.error(this.component.concat('An error occurred when updating the unit rate plan. API error response is:', err));
          }
        }))
      })
    );
  }
}
