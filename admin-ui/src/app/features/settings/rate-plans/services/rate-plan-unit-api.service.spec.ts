import { TestBed } from '@angular/core/testing';

import { RatePlanUnitApiService } from './rate-plan-unit-api.service';

describe('RatePlanUnitApiService', () => {
  let service: RatePlanUnitApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RatePlanUnitApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
