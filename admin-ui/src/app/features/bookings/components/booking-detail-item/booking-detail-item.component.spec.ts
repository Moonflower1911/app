import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingDetailItemComponent } from './booking-detail-item.component';

describe('BookingDetailItemComponent', () => {
  let component: BookingDetailItemComponent;
  let fixture: ComponentFixture<BookingDetailItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookingDetailItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookingDetailItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
