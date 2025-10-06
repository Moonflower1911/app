import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExtraGuestChargeCuModalComponent } from './extra-guest-charge-cu-modal.component';

describe('ExtraGuestChargeCuModalComponent', () => {
  let component: ExtraGuestChargeCuModalComponent;
  let fixture: ComponentFixture<ExtraGuestChargeCuModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExtraGuestChargeCuModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExtraGuestChargeCuModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
