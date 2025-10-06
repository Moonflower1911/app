import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RatePlanCreateModalComponent } from './rate-plan-create-modal.component';

describe('RateCreateModalComponent', () => {
  let component: RatePlanCreateModalComponent;
  let fixture: ComponentFixture<RatePlanCreateModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RatePlanCreateModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RatePlanCreateModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
