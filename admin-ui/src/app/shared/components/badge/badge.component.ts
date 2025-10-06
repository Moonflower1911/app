import {Component, Input} from '@angular/core';
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-badge',
  imports: [
    NgClass
  ],
  templateUrl: './badge.component.html',
  standalone: true,
  styleUrl: './badge.component.scss'
})
export class BadgeComponent {

  @Input()
  smaller: boolean = true;
  @Input()
  rounded: boolean = false;
  @Input()
  light: boolean = true;

  @Input()
  color: string = 'primary';
  @Input() mb = false;

  getBgClass() {
    let value = (this.light ? '-light' : '');
    switch (this.color) {
      case 'primary':
        return 'bg-primary' + value;
      case 'secondary':
        return 'bg-secondary' + value;
      case 'info':
        return 'bg-info' + value;
      case 'success':
        return 'bg-success' + value;
      case 'warning':
        return 'bg-warning' + value;
      case 'danger':
        return 'bg-danger' + value;
      case 'dark':
        return 'bg-dark' + value;
      case 'purple':
        return 'bg-purple'+value;
      case 'deep-purple':
        return 'bg-deep-purple'+value;
      case 'brown':
        return 'bg-brown'+value;
      case 'teal':
        return 'bg-teal'+value;
      default:
        return 'bg-dark' + value;
    }
  }
}
