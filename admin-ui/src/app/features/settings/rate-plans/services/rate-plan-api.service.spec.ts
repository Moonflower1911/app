import { TestBed } from '@angular/core/testing';

import { RatePlanApiService } from './rate-plan-api.service';

describe('RatePlanApiService', () => {
  let service: RatePlanApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RatePlanApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
