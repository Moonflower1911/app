import {commonProperties} from './environment.common';

export const environment = {
  bookingBaseRate: undefined,
    ...commonProperties,
    production: false,
  apiBaseUrl: (window as any).env?.BASE_URL || 'https://api.qa.xstay.com/',
};
