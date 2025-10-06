import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSettingsTabComponent } from './user-settings-tab.component';

describe('UserSettingsTabComponent', () => {
  let component: UserSettingsTabComponent;
  let fixture: ComponentFixture<UserSettingsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserSettingsTabComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserSettingsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
