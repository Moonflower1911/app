import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChargeCuModalComponent } from './charge-cu-modal.component';

describe('ChargeCuModalComponent', () => {
  let component: ChargeCuModalComponent;
  let fixture: ComponentFixture<ChargeCuModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChargeCuModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChargeCuModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
