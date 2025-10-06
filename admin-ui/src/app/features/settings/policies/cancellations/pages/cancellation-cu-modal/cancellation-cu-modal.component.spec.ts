import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancellationCuModalComponent } from './cancellation-cu-modal.component';

describe('CancellationCuModalComponent', () => {
  let component: CancellationCuModalComponent;
  let fixture: ComponentFixture<CancellationCuModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CancellationCuModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CancellationCuModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
