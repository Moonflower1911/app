import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LedgerGroupsComponent } from './ledger-groups.component';

describe('LedgerGroupsComponent', () => {
  let component: LedgerGroupsComponent;
  let fixture: ComponentFixture<LedgerGroupsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LedgerGroupsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LedgerGroupsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
