import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    data: { title: 'Invoices' },
    children: [
      {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
      },
      {
        path: 'list',
        loadComponent: () => import('./pages/invoice-list/invoice-list.component').then(m => m.InvoiceListComponent),
        data: { title: 'List' }
      },
      {
        path: ':id',
        loadComponent: () => import('./pages/invoice-details/invoice-details.component').then(m => m.InvoiceDetailsComponent),
        data: { title: 'Details' }
      }
    ]
  }
];
