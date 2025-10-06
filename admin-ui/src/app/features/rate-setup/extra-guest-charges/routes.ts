import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Extra Guest Charges'
    },
    children: [
      {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full',
      },
      {
        path: 'list',
        loadComponent: () =>
          import('./pages/extra-guest-charge-list/extra-guest-charge-list.component').then(m => m.ExtraGuestChargeListComponent),
        data: {
          title: 'Rate Management'
        }
      }
    ]
  }
];
