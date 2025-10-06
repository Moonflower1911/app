import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountClassListComponent } from './account-class-list.component';

describe('AccountClassListComponent', () => {
  let component: AccountClassListComponent;
  let fixture: ComponentFixture<AccountClassListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountClassListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountClassListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
