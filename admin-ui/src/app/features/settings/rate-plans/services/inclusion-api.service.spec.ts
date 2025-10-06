import { TestBed } from '@angular/core/testing';

import { InclusionApiService } from './inclusion-api.service';

describe('InclusionApiService', () => {
  let service: InclusionApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InclusionApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
