import { TestBed } from '@angular/core/testing';

import { RateApiService } from './rate-api.service';

describe('RateApiService', () => {
  let service: RateApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RateApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
