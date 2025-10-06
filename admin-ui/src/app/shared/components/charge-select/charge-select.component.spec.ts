import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChargeSelectComponent } from './charge-select.component';

describe('ChargeSelectComponent', () => {
  let component: ChargeSelectComponent;
  let fixture: ComponentFixture<ChargeSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChargeSelectComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChargeSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
