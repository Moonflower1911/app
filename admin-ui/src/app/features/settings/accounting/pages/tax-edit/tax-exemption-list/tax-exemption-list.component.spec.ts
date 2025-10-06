import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaxExemptionListComponent } from './tax-exemption-list.component';

describe('TaxExemptionListComponent', () => {
  let component: TaxExemptionListComponent;
  let fixture: ComponentFixture<TaxExemptionListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaxExemptionListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaxExemptionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
