import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LedgerGroupListComponent } from './ledger-group-list.component';

describe('LedgerGroupListComponent', () => {
  let component: LedgerGroupListComponent;
  let fixture: ComponentFixture<LedgerGroupListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LedgerGroupListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LedgerGroupListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
