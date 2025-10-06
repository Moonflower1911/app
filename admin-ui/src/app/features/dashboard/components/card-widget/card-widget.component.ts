import { Component } from '@angular/core';
import {CardBodyComponent, CardComponent, ColComponent, RowComponent} from '@coreui/angular';

@Component({
  selector: 'app-card-widget',
  imports: [
    RowComponent,
    ColComponent,
    CardComponent,
    CardBodyComponent
  ],
  templateUrl: './card-widget.component.html',
  styleUrl: './card-widget.component.scss'
})
export class CardWidgetComponent {

}
