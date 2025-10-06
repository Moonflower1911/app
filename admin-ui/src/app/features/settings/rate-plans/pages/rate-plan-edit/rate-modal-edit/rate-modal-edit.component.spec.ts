import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateModalEditComponent } from './rate-modal-edit.component';

describe('RateModalEditComponent', () => {
  let component: RateModalEditComponent;
  let fixture: ComponentFixture<RateModalEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RateModalEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RateModalEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
