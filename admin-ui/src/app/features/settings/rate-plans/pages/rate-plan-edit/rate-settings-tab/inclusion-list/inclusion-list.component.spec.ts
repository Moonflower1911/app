import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InclusionListComponent } from './inclusion-list.component';

describe('InclusionListComponent', () => {
  let component: InclusionListComponent;
  let fixture: ComponentFixture<InclusionListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InclusionListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InclusionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
