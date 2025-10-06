import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VatRuleSelectComponent } from './vat-rule-select.component';

describe('VatRuleSelectComponent', () => {
  let component: VatRuleSelectComponent;
  let fixture: ComponentFixture<VatRuleSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VatRuleSelectComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VatRuleSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
