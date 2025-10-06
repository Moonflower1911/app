import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaxTabsComponent } from './tax-tabs.component';

describe('TaxTabsComponent', () => {
  let component: TaxTabsComponent;
  let fixture: ComponentFixture<TaxTabsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaxTabsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaxTabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
