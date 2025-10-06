import {Routes} from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./rate-tabs/rate-tabs.component').then(m => m.RateTabsComponent),
    children: [
      {
        path: '',
        redirectTo: 'rates',
        pathMatch: 'full'
      },
      {
        path: 'rates',
        loadComponent: () =>
          import('./rate-grid-tab/rate-grid-tab.component').then(m => m.RateGridTabComponent),
        data: {
          title: 'General Information'
        }
      },
      {
        path: 'settings',
        loadComponent: () =>
          import('./rate-settings-tab/rate-settings-tab.component').then(m => m.RateSettingsTabComponent),
        data: {
          title: 'General Information'
        }
      }
    ]
  }
];
