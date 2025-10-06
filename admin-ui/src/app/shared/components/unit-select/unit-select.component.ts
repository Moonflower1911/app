import {Component, EventEmitter, forwardRef, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR} from "@angular/forms";
import {BehaviorSubject, debounceTime, distinctUntilChanged, Subscription} from "rxjs";
import {PageFilterModel} from "../../models/page-filter.model";
import {NgLabelTemplateDirective, NgOptionTemplateDirective, NgSelectComponent} from "@ng-select/ng-select";
import {UnitApiService} from '../../../features/properties/services/unit-api.service';
import {UnitItemGetModel} from '../../../features/properties/models/unit/get/unit-item-get.model';
import {BadgeComponent} from '../badge/badge.component';

@Component({
  selector: 'app-unit-select',
  imports: [
    NgSelectComponent,
    FormsModule,
    NgLabelTemplateDirective,
    NgOptionTemplateDirective,
    BadgeComponent
  ],
  templateUrl: './unit-select.component.html',
  styleUrl: './unit-select.component.scss',
  standalone: true,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => UnitSelectComponent),
    }
  ]
})
export class UnitSelectComponent implements OnInit, OnDestroy, ControlValueAccessor {

  @Input() multi = true;
  @Input() allowMultiUnit = false;
  @Input() withParent: boolean | null = null;
  @Input() nature: string | null = null;

  @Input() placeholder!: string;
  @Input() disable = false;
  @Input() initialResource: any | null = null;

  @Output() updatedResource = new EventEmitter<any | null>();

  resourceSearchList: UnitItemGetModel[] = [];
  selectedResource: UnitItemGetModel[] | null = null;

  $resourceSearch = new BehaviorSubject<string>('');
  private unitSearchPage = 0;
  private isLastPage = false;

  touched = false;
  disabled = false;
  private readonly subscriptions: Subscription[] = [];

  constructor(private readonly unitApiService: UnitApiService) {
  }

  ngOnInit(): void {
    this.subscribeToUnitSearch();
    if (this.initialResource) {
      this.selectedResource = this.initialResource;
      this.writeValue(this.initialResource);
    }
  }

  private subscribeToUnitSearch() {
    this.subscriptions.push(this.$resourceSearch.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(() => {
      this.unitSearchPage = 0;
      this.isLastPage = false;
      this.retrieveResourceSearchList();
    }))
  }

  private retrieveResourceSearchList() {
    const searchValue = this.$resourceSearch.getValue()?.trim();

    const pageFilter: PageFilterModel = {
      page: this.unitSearchPage,
      size: 20,
      sort: 'name',
      sortDirection: 'asc',
      search: searchValue,
      advancedSearchFormValue: {
        nature: (this.nature != null) ? this.nature : undefined,
        withParent: (this.withParent !== null) ? this.withParent : undefined,
      }
    }

    this.subscriptions.push(
      this.unitApiService.getUnitsByPage(pageFilter).subscribe({
        next: (res) => {
          console.log('Units retrieved successfully. API response is:', res);
          if (this.unitSearchPage === 0) {
            this.resourceSearchList = res.content;
          } else {
            this.resourceSearchList = this.resourceSearchList.concat(res.content);
          }
          this.isLastPage = res.last;
        },
        error: (err) => {
          console.error('An error occurred when retrieving unit list. API error response:', err);
        }
      })
    )
  }

  // Called when user types in search box
  searchResources($event: { term: string; items: any[] }): void {
    this.unitSearchPage = 0;    // Reset to first page for new search term
    this.isLastPage = false;     // Reset last page flag
    this.$resourceSearch.next($event.term);
  }

  // Called when user scrolls to the end of dropdown list
  onScrollToEnd(): void {
    if (!this.isLastPage) {
      this.unitSearchPage++;
      this.retrieveResourceSearchList();
    }
  }

  valueChanged($event: any): void {
    console.log('value changed in unit select: ', $event)
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
