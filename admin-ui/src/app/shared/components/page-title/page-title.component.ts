import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-page-title',
  imports: [],
  templateUrl: './page-title.component.html',
  standalone: true,
  styleUrl: './page-title.component.scss'
})
export class PageTitleComponent {

  @Input()
  title!: string;

  @Input()
  subTitle!: string;

}
