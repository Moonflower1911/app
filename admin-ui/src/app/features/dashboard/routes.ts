import {Routes} from '@angular/router';
import {DashboardComponent} from './pages/new/dashboard/dashboard.component';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/new/dashboard/dashboard.component').then(m => m.DashboardComponent),
    data: {
      title: 'Dashboard'
    }
  }
];

