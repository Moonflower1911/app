import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaxRuleListComponent } from './tax-rule-list.component';

describe('TaxRuleListComponent', () => {
  let component: TaxRuleListComponent;
  let fixture: ComponentFixture<TaxRuleListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaxRuleListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaxRuleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
