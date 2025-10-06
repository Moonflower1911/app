import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Accounting'
    },
    loadComponent: () => import('./pages/accounting-wrapper/accounting-wrapper.component').then(m => m.AccountingWrapperComponent),
    children: [
      {
        path: '',
        redirectTo: 'posting-accounts',
        pathMatch: 'full',
      },
      {
        path: 'posting-accounts',
        loadComponent: () => import('./pages/posting-account-list/posting-account-list.component').then((m) => m.PostingAccountListComponent)
      },
/*      {
        path: 'account-classes',
        loadComponent: () => import('./pages/account-class-list/account-class-list.component').then((m) => m.AccountClassListComponent)
      },*/
      {
        path: 'ledger-groups',
        loadComponent: () => import('./pages/ledger-group-list/ledger-group-list.component').then((m) => m.LedgerGroupListComponent)
      },
      {
        path: 'taxes',
        loadChildren: () =>
          import('./pages/tax-edit/routes').then((m) => m.routes),
        data: {
          title: 'Taxes'
        }
      }
    ]
  }
]
