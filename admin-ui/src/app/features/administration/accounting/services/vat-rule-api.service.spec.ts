import { TestBed } from '@angular/core/testing';

import { VatRuleApiService } from './vat-rule-api.service';

describe('VatRuleApiService', () => {
  let service: VatRuleApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VatRuleApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
