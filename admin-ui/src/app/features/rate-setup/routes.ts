import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Ratings'
    },
    children: [
      {
        path: '',
        redirectTo: 'rate-plans',
        pathMatch: 'full',
      },
      {
        path: 'extra-guest-charges',
        loadChildren: () => import('./extra-guest-charges/routes').then((m) => m.routes)
      }
    ]
  }
]
