import {Component, EventEmitter, OnDestroy, Output} from '@angular/core';
import {TranslatePipe} from "@ngx-translate/core";
import {
  AvatarComponent,
  ButtonDirective,
  CardBodyComponent,
  CardComponent,
  CardHeaderComponent,
  ColComponent,
  ColDirective,
  FormDirective,
  FormLabelDirective,
  RowComponent
} from '@coreui/angular';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {Subscription} from 'rxjs';
import {UnitSelectComponent} from '../../../../shared/components/unit-select/unit-select.component';
import {UnitApiService} from '../../../properties/services/unit-api.service';
import {BadgeComponent} from '../../../../shared/components/badge/badge.component';
import {NgClass} from '@angular/common';
import {BookingApiService} from '../../services/booking-api.service';
import {BookingGetModel} from '../../models/booking/booking-get.model';

@Component({
  selector: 'app-booking-room-assign',
  imports: [
    TranslatePipe,
    FormDirective,
    FormsModule,
    ReactiveFormsModule,
    ButtonDirective,
    ColComponent,
    RowComponent,
    ColDirective,
    FormLabelDirective,
    UnitSelectComponent,
    CardComponent,
    CardHeaderComponent,
    CardBodyComponent,
    BadgeComponent,
    AvatarComponent,
    NgClass
  ],
  templateUrl: './booking-room-assign.component.html',
  styleUrl: './booking-room-assign.component.scss'
})
export class BookingRoomAssignComponent implements OnDestroy {
  bookingId!: string;
  unitForm: FormGroup;
  unitRef!: any;
  @Output() actionConfirmed = new EventEmitter<BookingGetModel>();
  subUnits!: any[];
  roomNumber!: number;
  private subscriptions: Subscription[] = [];

  constructor(private readonly fb: FormBuilder,
              private readonly modalRef: BsModalRef,
              private bookingApiService: BookingApiService,
              private unitApiService: UnitApiService) {
    this.unitForm = this.fb.group({
      unit: [null]
    })

    this.unitForm.patchValue({
      unit: [{
        id: "e85affbb-9d16-4a80-a2b9-4f02e9bc7820",
        name: "Double Vue Piscine",
        subtitle: ""
      }]
    })


  }

  submit() {
    let payload: any;
    if (this.roomNumber == 1) {
      payload = {
        roomRef: {
          id: 'd350f557-f93f-4def-b45b-6a3142ab2193',
          name: 'DVP-005'
        }
      }
    } else if (this.roomNumber == 2) {
      payload = {
        roomRef: {
          id: '944d2cb9-07a1-40ce-8872-4825b8ed72aa',
          name: 'DVP-004'
        }
      }
    } else {
      payload = {
        roomRef: {
          id: '8281c65b-1778-4bf6-a2bc-4002b6c08bed',
          name: 'DVP-003'
        }
      }
    }

    this.subscriptions.push(this.bookingApiService.patchById(this.bookingId, payload).subscribe({
      next: (value) => {
        console.log('room assigned', value);
        this.actionConfirmed.emit(value);
        this.modalRef.hide();
      },
      error: (err) => {
        console.log('An error occurred when assigning room', err);
      }
    }))

  }

  closeModal() {
    this.modalRef.hide();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  selectRoom(number: number) {
    console.log('select:', number);
    this.roomNumber = number;
  }
}
