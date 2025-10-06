import {Component, Input} from '@angular/core';
import {BadgeComponent} from "../../../../shared/components/badge/badge.component";
import {TranslatePipe} from "@ngx-translate/core";
import {BookingStatusEnum} from '../../models/booking-status.enum';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-booking-status',
  imports: [
    BadgeComponent,
    TranslatePipe,
    NgClass
  ],
  templateUrl: './booking-status.component.html',
  styleUrl: './booking-status.component.scss'
})
export class BookingStatusComponent {

  @Input()
  status!: BookingStatusEnum;


  getBadgeIcon() {
    switch (this.status) {
      case 'CONFIRMED':
        return 'bi-check-circle';
      case 'CANCELED':
        return 'bi-dash-circle';
      case 'DRAFT':
        return 'bi-exclamation-circle';
      case 'CHECKED_IN':
        return 'bi-arrow-down-right-circle';
      case 'CHECKED_OUT':
        return 'bi-arrow-up-right-circle';
      default:
        return 'bi-exclamation-circle';
    }
  }

  getBadgeColor() {
    switch (this.status) {
      case 'CONFIRMED':
        return 'info';
      case 'CANCELED':
        return 'light';
      case 'DRAFT':
        return 'warning';
      case 'CHECKED_IN':
        return 'success';
      case 'CHECKED_OUT':
        return 'secondary';
      default:
        return 'warning';
    }
  }
}
