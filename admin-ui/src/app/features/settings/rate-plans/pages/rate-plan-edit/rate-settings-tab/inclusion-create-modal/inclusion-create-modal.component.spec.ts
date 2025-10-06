import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InclusionCreateModalComponent } from './inclusion-create-modal.component';

describe('InclusionCreateModalComponent', () => {
  let component: InclusionCreateModalComponent;
  let fixture: ComponentFixture<InclusionCreateModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InclusionCreateModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InclusionCreateModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
