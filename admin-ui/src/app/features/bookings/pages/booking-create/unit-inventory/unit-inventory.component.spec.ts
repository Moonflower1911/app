import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitInventoryComponent } from './unit-inventory.component';

describe('UnitInventoryComponent', () => {
  let component: UnitInventoryComponent;
  let fixture: ComponentFixture<UnitInventoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UnitInventoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnitInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
