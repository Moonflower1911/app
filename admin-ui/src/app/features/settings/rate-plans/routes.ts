import { Routes } from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    data: {
      title: 'ratings'
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
          import('./pages/rate-plan-list/rate-plan-list.component').then(m => m.RatePlanListComponent),
        data: {
          title: 'Rate Management'
        }
      },
      {
        path: ':ratePlanId',
        loadChildren: () =>
          import('./pages/rate-plan-edit/routes').then((m) => m.routes),
        data: {
          title: 'Edit Rate'
        }
      }
    ]
  }
];
