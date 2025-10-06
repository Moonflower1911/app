import { Component, OnInit, OnDestroy } from '@angular/core';
import { UnitItemGetModel } from "../../../units/models/unit-item-get.model";
import { UnitApiService } from "../../../units/services/unit-api.service";
import { TableDirective, ButtonDirective, RowComponent, ColComponent, AvatarComponent} from "@coreui/angular";
import { FormsModule } from "@angular/forms";

import { UtilsService } from "../../../../shared/services/utils.service";
import {PageFilterModel} from "../../../../shared/models/page-filter.model";
import {TranslatePipe} from "@ngx-translate/core";

import {ActivatedRoute, Router} from '@angular/router';

import { NgxDaterangepickerBootstrapDirective, NgxDaterangepickerBootstrapComponent} from "ngx-daterangepicker-bootstrap";
import { Dayjs } from "dayjs";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-availability-list',
  templateUrl: './availability-list.component.html',
  styleUrl: './availability-list.component.scss',
  standalone: true,
  imports: [
    TableDirective,
    ButtonDirective,
    FormsModule,
    RowComponent,
    ColComponent,
    AvatarComponent,
    TranslatePipe,
    NgxDaterangepickerBootstrapDirective, NgIf
  ]
})


export class AvailabilityListComponent implements OnInit, OnDestroy {

  units: UnitItemGetModel[] = [];

  dateRange: {startDate: Dayjs, endDate: Dayjs} | null = null;
  guests!: number;
  isSearchPerformed: boolean = false;

  private priceCache = new Map<string, number>();
  private minimumStayCache = new Map<string, number>();


  constructor(
    private readonly unitApiService: UnitApiService,
    private readonly router: Router,
    private route: ActivatedRoute
  ) {}
  ngOnInit(): void {
  }

  onSearchAvailability(): void {
    if (!this.dateRange || !this.dateRange.startDate || !this.dateRange.endDate) {
      alert('Please select check-in and check-out dates');
      return;
    }

    const today = new Date();
    today.setHours(0, 0, 0, 0);

// Vérifier que startDate et endDate existent avant de les utiliser
    const startDate = this.dateRange.startDate?.toDate();
    const endDate = this.dateRange.endDate?.toDate();

    if (!startDate || !endDate) {
      alert('Please select valid dates');
      return;
    }

    if (startDate < today) {
      alert('Check-in date cannot be in the past');
      return;
    }

    if (endDate <= startDate) {
      alert('Check-out date must be after check-in date');
      return;
    }

    if (!this.guests || this.guests < 1 || isNaN(this.guests)) {
      alert('Please enter a valid number of guests');
      return;
    }

    this.performSearch();
  }

  private performSearch(): void {
    this.isSearchPerformed = true;

    const pageFilter: PageFilterModel = {
      page: 0,
      size: 50,
      sort: 'name',
      sortDirection: 'asc',
      search: '',
      advancedSearchFormValue: {
        nature: undefined,
        withParent: undefined
      }
    };

    this.unitApiService.getUnitsByPage(pageFilter)
      .subscribe({
        next: (res) => {
          this.units = this.flattenUnitsWithReadiness(res.content || []);
        },
        error: (err) => {
          alert('Error loading units. Please try again.');
        }
      });
  }

  private flattenUnitsWithReadiness(units: UnitItemGetModel[]): UnitItemGetModel[] {
    const result: UnitItemGetModel[] = [];

    for (const unit of units) {
      if (unit.nature === 'SINGLE' && unit.readiness) {
        result.push(unit);
      } else if (unit.nature === 'MULTI_UNIT') {
        const readySubUnits = unit.subUnits?.filter(sub => sub.readiness) ?? [];
        if (readySubUnits.length > 0) {
          result.push(unit);
          for (const subUnit of readySubUnits) {
            result.push(subUnit);
          }
        }
      }
    }

    console.log('Final filtered units:', result);
    return result;
  }

  getNameInitials(name: string): string {
    return UtilsService.getNameInitials(name);
  }

  getPricePerNight(unit: UnitItemGetModel): number {
    if (this.priceCache.has(unit.id)) {
      return this.priceCache.get(unit.id)!;
    }

    let price: number;
    if (unit.nature === 'MULTI_UNIT') {
      price = Math.floor(Math.random() * 300) + 200; // 200-500 DH
    } else {
      price = Math.floor(Math.random() * 200) + 150; // 150-350 DH
    }

    this.priceCache.set(unit.id, price);
    return price;
  }

  getMinimumStay(unit: UnitItemGetModel): number {
    if (this.minimumStayCache.has(unit.id)) {
      return this.minimumStayCache.get(unit.id)!;
    }
    const stay = Math.floor(Math.random() * 6) + 1;
    this.minimumStayCache.set(unit.id, stay);
    return stay;
  }

  isNavigating = false;

  onBookUnit(unit: any): void {
    if (!this.dateRange?.startDate || !this.dateRange?.endDate) return;

    this.isNavigating = true;
    setTimeout(() => {
      this.router.navigate(['../booking'], {
        queryParams: {
          unitId: unit.id,
          startDate: this.dateRange!.startDate.toISOString(),
          endDate: this.dateRange!.endDate.toISOString(),
        },
        relativeTo: this.route,
      });
    }, 0);
  }

  ngOnDestroy(): void {
    // Nettoyer les caches pour éviter les fuites mémoire
    this.priceCache.clear();
    this.minimumStayCache.clear();
  }
}
