import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingSideComponent } from './booking-side.component';

describe('BookingSideComponent', () => {
  let component: BookingSideComponent;
  let fixture: ComponentFixture<BookingSideComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookingSideComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookingSideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
