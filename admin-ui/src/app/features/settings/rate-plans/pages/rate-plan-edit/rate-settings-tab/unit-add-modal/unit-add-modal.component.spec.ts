import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitAddModalComponent } from './unit-add-modal.component';

describe('UnitAddModalComponent', () => {
  let component: UnitAddModalComponent;
  let fixture: ComponentFixture<UnitAddModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UnitAddModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitAddModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
