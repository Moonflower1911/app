import {Component, EventEmitter, forwardRef, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {NgLabelTemplateDirective, NgOptionTemplateDirective, NgSelectComponent} from '@ng-select/ng-select';
import {TranslatePipe} from '@ngx-translate/core';
import {ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR} from '@angular/forms';
import {BehaviorSubject, debounceTime, distinctUntilChanged, Subscription} from 'rxjs';
import {PageFilterModel} from '../../models/page-filter.model';
import {PartyItemGetModel} from '../../../features/crm/models/party/party-item-get.model';
import {
  ExtraGuestChargeApiService
} from '../../../features/rate-setup/extra-guest-charges/services/extra-guest-charge-api.service';

@Component({
  selector: 'app-guest-charge-strategy-select',
  imports: [
    NgLabelTemplateDirective,
    NgOptionTemplateDirective,
    NgSelectComponent,
    TranslatePipe,
    FormsModule
  ],
  templateUrl: './guest-charge-strategy-select.component.html',
  styleUrl: './guest-charge-strategy-select.component.scss',
  standalone: true,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => GuestChargeStrategySelectComponent),
    }
  ]
})
export class GuestChargeStrategySelectComponent implements OnInit, OnDestroy, ControlValueAccessor {
  private component = ['GuestChargeStrategySelectComponent'];

  @Input() disable = false;
  @Input() initialResource: any | null = null;
  @Output() updatedResource = new EventEmitter<any | null>();

  resourceSearchList: any[] = [];
  selectedResource: any | null = null;

  $resourceSearch = new BehaviorSubject<string>('');

  private resourceSearchPage = 0;
  private isLastPage = false;

  touched = false;
  disabled = false;
  private readonly subscriptions: Subscription[] = [];

  constructor(private readonly guestChargeStrategyApiService: ExtraGuestChargeApiService) {
  }

  ngOnInit(): void {
    this.subscribeToResourceSearch();
    // Auto-select party if provided
    if (this.initialResource) {
      this.selectedResource = this.initialResource;
      this.writeValue(this.initialResource);
    }
  }

  private subscribeToResourceSearch() {
    this.subscriptions.push(this.$resourceSearch.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(() => {
      this.resourceSearchPage = 0;
      this.isLastPage = false;
      this.retrieveResourceSearchList();
    }))
  }

  private retrieveResourceSearchList() {
    const searchValue = this.$resourceSearch.getValue()?.trim();

    let pageFilter: PageFilterModel = {
      page: this.resourceSearchPage,
      size: 20,
      sort: 'name',
      sortDirection: 'asc',
      search: searchValue,
      advancedSearchFormValue: {
        enabled: true
      }
    };

    this.subscriptions.push(
      this.guestChargeStrategyApiService.getAllByPage(pageFilter).subscribe({
        next: (res) => {
          console.log(this.component.concat('Resources retrieved successfully. API response is:'), res);
          if (this.resourceSearchPage === 0) {
            this.resourceSearchList = res.content;
          } else {
            this.resourceSearchList = this.resourceSearchList.concat(res.content);
          }
          this.isLastPage = res.last;
        },
        error: (err) => {
          console.error(this.component.concat('An error occurred when retrieving resources list. API error response:'), err);
        }
      })
    )
  }

  searchResources($event: { term: string; items: any[] }): void {
    this.resourceSearchPage = 0;
    this.isLastPage = false;
    this.$resourceSearch.next($event.term);
  }

  onScrollToEnd(): void {
    if (!this.isLastPage) {
      this.resourceSearchPage++;
      this.retrieveResourceSearchList();
    }
  }

  valueChanged($event: any): void {
    console.log(this.component.concat('value changed: '), $event)
    this.markAsTouched();
    if (!this.disabled) {
      if ($event) {
        this.selectedResource = $event;
      } else {
        this.selectedResource = null;
      }
      this.onChange(this.selectedResource);
      this.updatedResource.emit(this.selectedResource);
    }
  }


  writeValue(obj: any): void {
    this.selectedResource = obj;
  }

  onChange = (_: any) => {
  };

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  onTouched = () => {
  };

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  markAsTouched(): void {
    if (!this.touched) {
      this.onTouched();
      this.touched = true;
    }
  }

  compareParties(item1: PartyItemGetModel, item2: any): boolean {
    if (!item1 || !item2) {
      return false;
    }
    const item1Identifier = item1.id;
    if (item2.id !== undefined) {
      return item1Identifier === item2.id;
    }
    if (item2.uuid !== undefined) {
      return item1Identifier === item2.uuid;
    }
    return false;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }
}
