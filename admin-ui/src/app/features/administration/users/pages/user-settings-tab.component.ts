import { Component } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import {UserListComponent} from './user-list/user-list.component';

@Component({
  selector: 'app-user-settings-tab',
  standalone: true,
  imports: [
    TranslatePipe,
    UserListComponent
  ],
  templateUrl: './user-settings-tab.component.html',
  styleUrls: ['./user-settings-tab.component.scss']
})
export class UserSettingsTabComponent {}
