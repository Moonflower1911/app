import { Component } from '@angular/core';
import {CardBodyComponent, CardComponent, CardHeaderComponent, ColComponent, RowComponent} from '@coreui/angular';

@Component({
  selector: 'app-fee-item-form',
  imports: [
    CardComponent,
    CardHeaderComponent,
    RowComponent,
    ColComponent,
    CardBodyComponent
  ],
  templateUrl: './fee-item-form.component.html',
  standalone: true,
  styleUrl: './fee-item-form.component.scss'
})
export class FeeItemFormComponent {

}
