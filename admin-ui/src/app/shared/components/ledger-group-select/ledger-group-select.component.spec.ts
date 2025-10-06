import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LedgerGroupSelectComponent } from './ledger-group-select.component';

describe('LedgerGroupSelectComponent', () => {
  let component: LedgerGroupSelectComponent;
  let fixture: ComponentFixture<LedgerGroupSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LedgerGroupSelectComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LedgerGroupSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
