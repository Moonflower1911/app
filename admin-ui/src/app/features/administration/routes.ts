import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Administration'
    },
    children: [
      {
        path: '',
        redirectTo: 'accounting',
        pathMatch: 'full',
      },
      {
        path: 'accounting',
        loadChildren: () => import('./accounting/routes').then((m) => m.routes)
      },
      {
        path: 'users',
        loadComponent: () => import('./users/pages/user-settings-tab.component').then(m => m.UserSettingsTabComponent),
        data: {
          title: 'User Settings'
        }
      },
    ]
  }
]
