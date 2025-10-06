import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancellationListComponent } from './cancellation-list.component';

describe('CancellationListComponent', () => {
  let component: CancellationListComponent;
  let fixture: ComponentFixture<CancellationListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CancellationListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CancellationListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
