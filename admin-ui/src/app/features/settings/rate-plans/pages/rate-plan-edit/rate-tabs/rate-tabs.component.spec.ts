import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateTabsComponent } from './rate-tabs.component';

describe('RateEditPageComponent', () => {
  let component: RateTabsComponent;
  let fixture: ComponentFixture<RateTabsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RateTabsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RateTabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
