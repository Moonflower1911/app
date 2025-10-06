import {Component, EventEmitter, forwardRef, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {NgLabelTemplateDirective, NgOptionTemplateDirective, NgSelectComponent} from "@ng-select/ng-select";
import {TranslatePipe} from "@ngx-translate/core";
import {ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR} from '@angular/forms';
import {BehaviorSubject, debounceTime, distinctUntilChanged, Subscription} from 'rxjs';
import {PageFilterModel} from '../../models/page-filter.model';
import {PartyItemGetModel} from '../../../features/crm/models/party/party-item-get.model';
import {ChargeApiService} from '../../../features/settings/charges/services/charge-api.service';
import {BadgeComponent} from '../badge/badge.component';
import {DecimalPipe} from '@angular/common';

@Component({
  selector: 'app-charge-select',
  imports: [
    NgLabelTemplateDirective,
    NgOptionTemplateDirective,
    NgSelectComponent,
    TranslatePipe,
    FormsModule,
    BadgeComponent,
    DecimalPipe
  ],
  templateUrl: './charge-select.component.html',
  styleUrl: './charge-select.component.scss',
  standalone: true,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => ChargeSelectComponent),
    }
  ]
})
export class ChargeSelectComponent implements OnInit, OnDestroy, ControlValueAccessor {
  private component = '[ChargeSelectComponent]: ';

  @Input() disable = false;
  @Input() initialResource: any | null = null;
  @Output() updatedResource = new EventEmitter<any | null>();
  private _isPackage!: boolean;
  private _isExtra!: boolean;

  resourceSearchList: any[] = [];
  selectedResource: any | null = null;

  $resourceSearch = new BehaviorSubject<string>('');

  private resourceSearchPage = 0;
  private isLastPage = false;

  touched = false;
  disabled = false;
  private readonly subscriptions: Subscription[] = [];

  constructor(private readonly chargeApiService: ChargeApiService) {
  }

  ngOnInit(): void {
    this.subscribeToResourceSearch();
    // Auto-select party if provided
    if (this.initialResource) {
      this.selectedResource = this.initialResource;
      this.writeValue(this.initialResource);
    }
  }

  @Input()
  set isPackage(value: any) {
    console.log(this.component.concat('value is:', value));
    if (this._isPackage !== value) {
      let makeCall = false;
      if (this.isPackage != null) {
        makeCall = true;
      }
      this._isPackage = value;
      this.resourceSearchPage = 0;
      this.isLastPage = false;
      if (makeCall) {
        this.retrieveResourceSearchList();
      }
    }
  }

  @Input()
  set isExtra(value: any) {
    if (this._isExtra !== value) {
      let makeCall = false;
      if (this.isExtra != null) {
        makeCall = true;
      }
      this._isExtra = value;
      this.resourceSearchPage = 0;
      this.isLastPage = false;
      if (makeCall) {
        this.retrieveResourceSearchList();
      }
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
        isPackage: (this._isPackage != null) ? this._isPackage : undefined,
        isExtra: (this._isExtra != null) ? this._isExtra : undefined,
        enabled: true
      }
    };

    this.subscriptions.push(
      this.chargeApiService.getAllByPage(pageFilter).subscribe({
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
