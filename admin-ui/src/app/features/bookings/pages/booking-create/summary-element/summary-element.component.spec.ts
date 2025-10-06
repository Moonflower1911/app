import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SummaryElementComponent } from './summary-element.component';

describe('SummaryElementComponent', () => {
  let component: SummaryElementComponent;
  let fixture: ComponentFixture<SummaryElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SummaryElementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SummaryElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
