import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateGridTabComponent } from './rate-grid-tab.component';

describe('RateGridTabComponent', () => {
  let component: RateGridTabComponent;
  let fixture: ComponentFixture<RateGridTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RateGridTabComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RateGridTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
