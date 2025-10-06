import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Bookings'
    },
    children: [
      {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full',
      },
      {
        path: 'list',
        loadComponent: () => import('./pages/booking-list/booking-list.component').then(m => m.BookingListComponent),
        data: {
          title: 'Booking list'
        }
      },
      {
        path: 'check-ins',
        loadComponent: () => import('./pages/booking-list/booking-list.component').then(m => m.BookingListComponent),
        data: {
          title: 'Booking list'
        }
      },
      {
        path: 'check-outs',
        loadComponent: () => import('./pages/booking-list/booking-list.component').then(m => m.BookingListComponent),
        data: {
          title: 'Booking list'
        }
      },
      {
        path: 'calendar',
        loadComponent: () => import('./pages/calendar/calendar.component').then(m => m.CalendarComponent),
        data: {
          title: 'Calendar'
        }
      },
      {
        path: 'create',
        loadComponent: () => import('./pages/booking-create/booking-create.component').then(m => m.BookingCreateComponent),
        data: {
          title: 'New booking'
        }
      },
      {
        path: ':bookingId/create',
        loadComponent: () => import('./pages/booking-create/booking-create.component').then(m => m.BookingCreateComponent),
        data: {
          title: 'New booking'
        }
      },
      {
        path: ':bookingId/confirm',
        loadComponent: () => import('./pages/booking-confirm/booking-confirm.component').then(m => m.BookingConfirmComponent),
        data: {
          title: 'Confirm booking'
        }
      },
      {
        path: ':bookingId',
        loadComponent: () => import('./pages/booking-details/booking-details.component').then(m => m.BookingDetailsComponent),
        data: {
          title: 'Booking details'
        }
      },
    ]
  }
]
