import { Component } from '@angular/core';
import {
    ColComponent,
    ListGroupDirective,
    ListGroupItemDirective,
    NavLinkDirective,
    RowComponent
} from "@coreui/angular";
import {PageTitleComponent} from "../../../../../shared/components/page-title/page-title.component";
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {TranslatePipe} from "@ngx-translate/core";

@Component({
  selector: 'app-accounting-wrapper',
    imports: [
        ColComponent,
        ListGroupDirective,
        ListGroupItemDirective,
        NavLinkDirective,
        PageTitleComponent,
        RouterLink,
        RouterLinkActive,
        RouterOutlet,
        RowComponent,
        TranslatePipe
    ],
  templateUrl: './accounting-wrapper.component.html',
  styleUrl: './accounting-wrapper.component.scss'
})
export class AccountingWrapperComponent {

}
