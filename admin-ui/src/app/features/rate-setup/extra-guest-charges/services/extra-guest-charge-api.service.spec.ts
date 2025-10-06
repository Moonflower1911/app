import { TestBed } from '@angular/core/testing';

import { ExtraGuestChargeApiService } from './extra-guest-charge-api.service';

describe('ExtraGuestChargeApiService', () => {
  let service: ExtraGuestChargeApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExtraGuestChargeApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
