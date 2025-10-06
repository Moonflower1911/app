import { Component } from '@angular/core';
import {CardBodyComponent, CardComponent, NavComponent, NavItemComponent, NavLinkDirective} from '@coreui/angular';
import {PageTitleComponent} from '../../../../../../shared/components/page-title/page-title.component';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {TitleCasePipe} from '@angular/common';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-tax-tabs',
  imports: [
    CardBodyComponent,
    CardComponent,
    NavComponent,
    NavItemComponent,
    NavLinkDirective,
    PageTitleComponent,
    RouterLink,
    RouterLinkActive,
    RouterOutlet,
    TitleCasePipe,
    TranslatePipe
  ],
  templateUrl: './tax-tabs.component.html',
  styleUrl: './tax-tabs.component.scss'
})
export class TaxTabsComponent {

}
