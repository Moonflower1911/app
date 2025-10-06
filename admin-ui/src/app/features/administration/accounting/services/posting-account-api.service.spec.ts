import { TestBed } from '@angular/core/testing';

import { PostingAccountApiService } from './posting-account-api.service';

describe('PostingAccountApiService', () => {
  let service: PostingAccountApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PostingAccountApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
