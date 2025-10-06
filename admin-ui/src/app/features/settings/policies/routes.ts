import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Policies'
    },
    children: [
      {
        path: '',
        redirectTo: 'cancellations',
        pathMatch: 'full',
      },
      {
        path: 'cancellations',
        loadChildren: () => import('./cancellations/routes').then(m=>m.routes)
      }
    ]
  }
]
