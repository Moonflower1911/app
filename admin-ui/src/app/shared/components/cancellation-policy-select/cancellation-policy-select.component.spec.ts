import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancellationPolicySelectComponent } from './cancellation-policy-select.component';

describe('CancellationPolicySelectComponent', () => {
  let component: CancellationPolicySelectComponent;
  let fixture: ComponentFixture<CancellationPolicySelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CancellationPolicySelectComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CancellationPolicySelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
