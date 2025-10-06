import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountClassCuModalComponent } from './account-class-cu-modal.component';

describe('AccountClassCuModalComponent', () => {
  let component: AccountClassCuModalComponent;
  let fixture: ComponentFixture<AccountClassCuModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountClassCuModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountClassCuModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
