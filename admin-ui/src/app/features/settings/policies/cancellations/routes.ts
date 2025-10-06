import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Cancellations'
    },
    children: [
      {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full',
      },
      {
        path: 'list',
        loadComponent: () => import('./pages/cancellation-list/cancellation-list.component').then(m => m.CancellationListComponent),
        data: {
          title: 'List'
        }
      }
    ]
  }
]
