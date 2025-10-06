import { TestBed } from '@angular/core/testing';

import { LedgerGroupApiService } from './ledger-group-api.service';

describe('LedgerGroupApiService', () => {
  let service: LedgerGroupApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LedgerGroupApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
