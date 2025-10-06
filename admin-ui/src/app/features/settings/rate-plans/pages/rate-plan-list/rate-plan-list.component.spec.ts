import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RatePlanListComponent } from './rate-plan-list.component';

describe('RateManagementComponent', () => {
  let component: RatePlanListComponent;
  let fixture: ComponentFixture<RatePlanListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RatePlanListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RatePlanListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
