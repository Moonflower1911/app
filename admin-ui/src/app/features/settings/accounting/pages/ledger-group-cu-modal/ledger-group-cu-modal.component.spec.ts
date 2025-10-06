import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LedgerGroupCuModalComponent } from './ledger-group-cu-modal.component';

describe('LedgerGroupCuModalComponent', () => {
  let component: LedgerGroupCuModalComponent;
  let fixture: ComponentFixture<LedgerGroupCuModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LedgerGroupCuModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LedgerGroupCuModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
