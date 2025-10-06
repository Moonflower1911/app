import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CircleChartCustomAngelComponent } from './circle-chart-custom-angle.component';

describe('CircleChartCustomAngelComponent', () => {
  let component: CircleChartCustomAngelComponent;
  let fixture: ComponentFixture<CircleChartCustomAngelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CircleChartCustomAngelComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CircleChartCustomAngelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
