import {ApplicationConfig, importProvidersFrom} from '@angular/core';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {
  provideRouter,
  withEnabledBlockingInitialNavigation,
  withHashLocation,
  withInMemoryScrolling,
  withRouterConfig,
  withViewTransitions
} from '@angular/router';
import { provideDaterangepickerLocale } from "ngx-daterangepicker-bootstrap";

import {DropdownModule, SidebarModule} from '@coreui/angular';
import {IconSetService} from '@coreui/icons-angular';
import {routes} from './app.routes';
import {HttpClient, provideHttpClient, withInterceptors, withInterceptorsFromDi} from "@angular/common/http";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {jwtInterceptor} from "./core/interceptors/jwt.interceptor";
import {provideToastr} from "ngx-toastr";
import {JwtModule} from "@auth0/angular-jwt";
import {AuthService} from "./core/services/auth.service";
import {tokenExpiredInterceptor} from "./core/interceptors/token-expired.interceptor";

export function tokenGetter() {
  return localStorage.getItem(AuthService.TOKEN);
}

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes,
      withRouterConfig({
        onSameUrlNavigation: 'reload'
      }),
      withInMemoryScrolling({
        scrollPositionRestoration: 'top',
        anchorScrolling: 'enabled'
      }),
      withEnabledBlockingInitialNavigation(),
      withViewTransitions(),
      withHashLocation()
    ),
    provideHttpClient(
      withInterceptorsFromDi(), withInterceptors([jwtInterceptor, tokenExpiredInterceptor])
    ),
    importProvidersFrom(SidebarModule, DropdownModule),
    IconSetService,
    provideAnimationsAsync(),
    provideToastr({
      positionClass: 'toast-bottom-right',
      preventDuplicates: true,
      timeOut: 2500
    }),
    importProvidersFrom(JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter
      }
    })),
    importProvidersFrom(
      TranslateModule.forRoot({
        "loader": {
          "provide": TranslateLoader,
          "useFactory": createTranslateLoader,
          "deps": [HttpClient],
        },
      })
    ),
    provideRouter(routes),
    provideDaterangepickerLocale({
      separator: ' - ',
      applyLabel: 'Okay',
    })
  ]
};
