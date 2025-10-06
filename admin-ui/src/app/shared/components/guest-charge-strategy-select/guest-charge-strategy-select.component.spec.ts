import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestChargeStrategySelectComponent } from './guest-charge-strategy-select.component';

describe('GuestChargeStrategySelectComponent', () => {
  let component: GuestChargeStrategySelectComponent;
  let fixture: ComponentFixture<GuestChargeStrategySelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GuestChargeStrategySelectComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GuestChargeStrategySelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
