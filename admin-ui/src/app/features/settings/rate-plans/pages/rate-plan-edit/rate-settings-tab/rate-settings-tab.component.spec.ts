import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateSettingsTabComponent } from './rate-settings-tab.component';

describe('RateSettingsTabComponent', () => {
  let component: RateSettingsTabComponent;
  let fixture: ComponentFixture<RateSettingsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RateSettingsTabComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RateSettingsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
