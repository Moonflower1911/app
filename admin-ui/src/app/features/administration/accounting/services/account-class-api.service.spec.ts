import { TestBed } from '@angular/core/testing';

import { AccountClassApiService } from './account-class-api.service';

describe('AccountClassApiService', () => {
  let service: AccountClassApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountClassApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
