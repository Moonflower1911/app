import {AuditGetModel} from '../../../shared/models/audit-get.model';
import {BookingStatusEnum} from './booking-status.enum';
import {BookingTypeEnum} from './booking-type.enum';

export interface BookingItemGetModel {
  id: string;
  reference: string;
  type: BookingTypeEnum;
  checkinDate: Date;
  checkoutDate: Date;
  unitRef: any;
  roomRef: any;
  status: BookingStatusEnum;
  contact: {
    name: string;
    email: string;
    mobile: string;
  },
  parent: { id: string, name: string, reference: string };
  audit: AuditGetModel;
}
