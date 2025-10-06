import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountClassesSelectComponent } from './account-classes-select.component';

describe('AccountClassesSelectComponent', () => {
  let component: AccountClassesSelectComponent;
  let fixture: ComponentFixture<AccountClassesSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountClassesSelectComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountClassesSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
