import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaxRuleCuModalComponent } from './tax-rule-cu-modal.component';

describe('TaxRuleCuModalComponent', () => {
  let component: TaxRuleCuModalComponent;
  let fixture: ComponentFixture<TaxRuleCuModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaxRuleCuModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaxRuleCuModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
