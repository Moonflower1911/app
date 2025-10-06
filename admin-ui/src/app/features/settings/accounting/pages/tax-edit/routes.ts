import {Routes} from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./tax-tabs/tax-tabs.component').then(m => m.TaxTabsComponent),
    children: [
      {
        path: '',
        redirectTo: 'rules',
        pathMatch: 'full'
      },
      {
        path: 'rules',
        loadComponent: () =>
          import('./tax-rule-list/tax-rule-list.component').then(m => m.TaxRuleListComponent),
        data: {
          title: 'General Information'
        }
      },
      {
        path: 'exemptions',
        loadComponent: () =>
          import('./tax-exemption-list/tax-exemption-list.component').then(m => m.TaxExemptionListComponent),
        data: {
          title: 'General Information'
        }
      }
    ]
  }
];
