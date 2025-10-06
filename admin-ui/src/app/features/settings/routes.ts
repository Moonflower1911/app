import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Settings'
    },
    children: [
      {
        path: '',
        redirectTo: 'units',
        pathMatch: 'full',
      },
      {
        path: 'units',
        loadChildren: () => import('./units/routes').then(m => m.routes)
      },
      {
        path: 'charges',
        loadChildren: () => import('./charges/routes').then(m => m.routes)
      },
      {
        path: 'accounting',
        loadChildren: () => import('./accounting/routes').then(m => m.routes)
      },
      {
        path: 'users',
        loadChildren: () => import('./users/routes').then(m => m.routes)
      },
      {
        path: 'rate-plans',
        loadChildren: () => import('./rate-plans/routes').then(m => m.routes)
      },
      {
        path: 'policies',
        loadChildren: ()=> import('./policies/routes').then(m=>m.routes)
      }
    ]
  }
]
