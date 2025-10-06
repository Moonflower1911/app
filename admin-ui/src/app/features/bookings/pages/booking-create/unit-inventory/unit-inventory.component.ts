import {Component, EventEmitter, Input, Output} from '@angular/core';
import {InventoryItemGetModel} from '../../../models/inventory/inventory-item-get.model';
import {
  ButtonDirective,
  CardBodyComponent,
  CardComponent,
  CardHeaderComponent,
  ColComponent,
  RowComponent
} from '@coreui/angular';
import {TranslatePipe} from '@ngx-translate/core';
import {cilBaby, cilBed, cilUser} from '@coreui/icons';
import {IconDirective} from '@coreui/icons-angular';
import {DatePipe, DecimalPipe, NgForOf, SlicePipe, TitleCasePipe} from '@angular/common';
import {BadgeComponent} from '../../../../../shared/components/badge/badge.component';
import {BookingGetModel} from '../../../models/booking/booking-get.model';

@Component({
  selector: 'app-unit-inventory',
  imports: [
    CardComponent,
    ColComponent,
    RowComponent,
    CardHeaderComponent,
    TranslatePipe,
    IconDirective,
    CardBodyComponent,
    TitleCasePipe,
    ButtonDirective,
    SlicePipe,
    BadgeComponent,
    NgForOf,
    DatePipe,
    DecimalPipe
  ],
  templateUrl: './unit-inventory.component.html',
  standalone: true,
  styleUrl: './unit-inventory.component.scss'
})
export class UnitInventoryComponent {

  icons = {cilBaby, cilUser, cilBed}
  @Input()
  unitInventory!: InventoryItemGetModel;
  @Output()
  unitInventorySelected = new EventEmitter<InventoryItemGetModel>();
  limit: number = 100;
  isExpanded: boolean = false;
  showAllAmenities = false;
  @Input() booking!: BookingGetModel | null;

  get available() {
    if (this.booking) {
      return Math.max(this.unitInventory.availability.available - this.booking.items.filter(item => item.stay.unitRef.id === this.unitInventory.id).length, 0)
    } else {
      return this.unitInventory.availability.available;
    }
  }

  // get requiredFeesCount(): number {
  //   let fees = this.unitInventory.fees;
  //   return fees.filter(fee => fee.required).length;
  // }
  //
  // get optionalFeesCount(): number {
  //   let fees = this.unitInventory.fees;
  //   return fees.filter(fee => !fee.required).length;
  // }

  toggleExpand(): void {
    this.isExpanded = !this.isExpanded;
  }

  toggleAmenities(): void {
    this.showAllAmenities = !this.showAllAmenities;
  }

  addToSelection() {
    this.unitInventorySelected.emit(this.unitInventory);
  }

  get hasPricing(): boolean {
    return !!this.unitInventory.avgRateNight && !!this.unitInventory.totalBookingRate;
  }

  get averageRate(): number | undefined {
    return this.unitInventory.avgRateNight;
  }

  get totalRate(): number | undefined {
    return this.unitInventory.totalBookingRate;
  }

  get dailyRates(): { date: string; rate: number }[] {
    return this.unitInventory.dailyRates ?? [];
  }

}
