import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Charges'
    },
    children: [
      {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full',
      },
      {
        path: 'list',
        loadComponent: () => import('./pages/charge-list/charge-list.component').then(m => m.ChargeListComponent),
        data: {
          title: 'List'
        }
      }
    ]
  }
]
