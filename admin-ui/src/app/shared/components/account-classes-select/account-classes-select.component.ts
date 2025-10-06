import {Component, EventEmitter, forwardRef, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR} from '@angular/forms';
import {NgLabelTemplateDirective, NgOptionTemplateDirective, NgSelectComponent} from '@ng-select/ng-select';
import {TranslatePipe} from '@ngx-translate/core';
import {BehaviorSubject, debounceTime, distinctUntilChanged, Subscription} from 'rxjs';
import {PageFilterModel} from '../../models/page-filter.model';
import {AccountClassApiService} from '../../../features/administration/accounting/services/account-class-api.service';

@Component({
  selector: 'app-account-classes-select',
  standalone: true,
  templateUrl: './account-classes-select.component.html',
  styleUrls: ['./account-classes-select.component.scss'],
  imports: [
    NgSelectComponent,
    FormsModule,
    TranslatePipe,
    NgOptionTemplateDirective,
    NgLabelTemplateDirective
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => AccountClassesSelectComponent),
      multi: true
    }
  ]
})
export class AccountClassesSelectComponent implements OnInit, OnDestroy, ControlValueAccessor {

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

  constructor(private readonly accountClassApiService: AccountClassApiService) {
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
        withParent: false
      }
    };

    this.subscriptions.push(
      this.accountClassApiService.getAllByPage(pageFilter).subscribe({
        next: (res) => {
          console.log('Account Class retrieved successfully. API response is:', res);
          if (this.resourceSearchPage === 0) {
            this.resourceSearchList = res.content;
          } else {
            this.resourceSearchList = this.resourceSearchList.concat(res.content);
          }
          this.isLastPage = res.last;
        },
        error: (err) => {
          console.error('An error occurred when retrieving account class list. API error response:', err);
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
    console.log('value changed in account select: ', $event)
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

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }
}
