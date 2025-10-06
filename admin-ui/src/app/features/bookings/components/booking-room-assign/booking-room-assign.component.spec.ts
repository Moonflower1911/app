import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingRoomAssignComponent } from './booking-room-assign.component';

describe('BookingRoomAssignComponent', () => {
  let component: BookingRoomAssignComponent;
  let fixture: ComponentFixture<BookingRoomAssignComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookingRoomAssignComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookingRoomAssignComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
