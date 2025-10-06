import {Component, EventEmitter, forwardRef, Input, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR} from '@angular/forms';
import {BehaviorSubject, debounceTime, distinctUntilChanged, Subscription} from 'rxjs';
import {PageFilterModel} from '../../models/page-filter.model';
import {NgLabelTemplateDirective, NgOptionTemplateDirective, NgSelectComponent} from '@ng-select/ng-select';
import {SegmentItemGetModel} from '../../../features/old-settings/models/segment/segment-item-get.model';
import {CrmApiService} from '../../../features/crm/services/crm-api.service';

@Component({
  selector: 'app-segment-select',
  imports: [
    NgLabelTemplateDirective,
    NgOptionTemplateDirective,
    NgSelectComponent,
    FormsModule
  ],
  templateUrl: './segment-select.component.html',
  styleUrl: './segment-select.component.scss',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => SegmentSelectComponent),
    }
  ]
})
export class SegmentSelectComponent implements OnInit, OnDestroy, ControlValueAccessor {

  @Input() withParent: undefined | boolean = undefined;
  @Input() multiple = false;
  @Input() disable = false;
  @Input() allowMultiUnit = false;  // Nouveau paramètre pour permettre les MULTI_UNIT si nécessaire
  @Output() updatedUnits = new EventEmitter<SegmentItemGetModel[] | null>();
  @Input() showEnabledOnly: boolean = false;
  @Input() parentId?: string;


  segmentSearchList: SegmentItemGetModel[] = [];
  selectedSegments: SegmentItemGetModel[] | null = null;

  $segmentSearch = new BehaviorSubject<string>('');
  private segmentSearchPage = 0;
  private isLastPage = false;

  touched = false;
  disabled = false;
  private readonly subscriptions: Subscription[] = [];

  constructor(private readonly crmApiService: CrmApiService) {
  }

  ngOnInit(): void {
    this.subscribeToSegmentSearch();
  }

  private subscribeToSegmentSearch() {
    this.subscriptions.push(this.$segmentSearch.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(() => {
      this.segmentSearchPage = 0;
      this.isLastPage = false;
      this.retrieveUnitSearchList();
    }))
  }

  private retrieveUnitSearchList() {
    const searchValue = this.$segmentSearch.getValue()?.trim();

    let pageFilter: PageFilterModel = {
      page: this.segmentSearchPage,
      size: 20,
      sort: 'name',
      sortDirection: 'asc',
      search: searchValue,
    };

    let advancedSearchFiler: any = {};

    if (this.withParent != undefined) {
      advancedSearchFiler = {
        ...advancedSearchFiler,
        withParent: this.withParent
      };
    }

    if (this.parentId) {
      advancedSearchFiler = {
        ...advancedSearchFiler,
        parentId: this.parentId
      };
    }


    if (this.showEnabledOnly) {
      advancedSearchFiler = {
        ...advancedSearchFiler,
        enabled: true
      };
    }

    if (Object.keys(advancedSearchFiler).length > 0) {
      pageFilter = {...pageFilter, advancedSearchFormValue: advancedSearchFiler};
    }

    this.subscriptions.push(
      this.crmApiService.getSegmentsByPage(pageFilter).subscribe({
        next: (res) => {
          console.log('Segments retrieved successfully. API response is:', res);
          if (this.segmentSearchPage === 0) {
            this.segmentSearchList = res.content;
          } else {
            this.segmentSearchList = this.segmentSearchList.concat(res.content);
          }
          this.isLastPage = res.last;
        },
        error: (err) => {
          console.error('An error occurred when retrieving segment list. API error response:', err);
        }
      })
    )
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['parentId'] && !changes['parentId'].firstChange) {
      console.log('parentId changed to:', this.parentId);
      this.segmentSearchPage = 0;
      this.isLastPage = false;
      this.retrieveUnitSearchList();
    }
  }

  // Called when user types in search box
  searchUnits($event: { term: string; items: any[] }): void {
    this.segmentSearchPage = 0;    // Reset to first page for new search term
    this.isLastPage = false;     // Reset last page flag
    this.$segmentSearch.next($event.term);
  }

  // Called when user scrolls to the end of dropdown list
  onScrollToEnd(): void {
    if (!this.isLastPage) {
      this.segmentSearchPage++;
      this.retrieveUnitSearchList();
    }
  }

  valueChanged($event: any): void {
    console.log('value changed in segment select: ', $event)
    this.markAsTouched();
    if (!this.disabled) {
      if ($event) {
        this.selectedSegments = $event;
      } else {
        this.selectedSegments = null;
      }
      this.onChange(this.selectedSegments);
      this.updatedUnits.emit(this.selectedSegments);
    }
  }

  writeValue(obj: any): void {
    this.selectedSegments = obj;
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

  compareSegments(item1: SegmentItemGetModel, item2: any): boolean {
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
