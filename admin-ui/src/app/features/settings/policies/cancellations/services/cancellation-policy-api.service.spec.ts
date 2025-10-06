import { TestBed } from '@angular/core/testing';

import { CancellationPolicyApiService } from './cancellation-policy-api.service';

describe('CancellationPolicyApiService', () => {
  let service: CancellationPolicyApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CancellationPolicyApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
