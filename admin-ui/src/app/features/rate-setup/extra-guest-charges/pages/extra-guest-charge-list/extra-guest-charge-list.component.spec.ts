import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExtraGuestChargeListComponent } from './extra-guest-charge-list.component';

describe('ExtraGuestChargeListComponent', () => {
  let component: ExtraGuestChargeListComponent;
  let fixture: ComponentFixture<ExtraGuestChargeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExtraGuestChargeListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExtraGuestChargeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
