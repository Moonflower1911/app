import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeeItemFormComponent } from './fee-item-form.component';

describe('FeeItemFormComponent', () => {
  let component: FeeItemFormComponent;
  let fixture: ComponentFixture<FeeItemFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FeeItemFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FeeItemFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
