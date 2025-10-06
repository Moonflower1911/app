import {BookingTypeEnum} from '../booking-type.enum';
import {BookingStatusEnum} from '../booking-status.enum';
import {ResourceRefModel} from '../../../../shared/models/resource-ref.model';
import {OccupancyModel} from '../commons/occupancy.model';
import {FeeGetModel} from '../fee/fee-get.model';

export interface BookingGetModel {
  id: string;
  reference: string;
  type: BookingTypeEnum;
  status: BookingStatusEnum;
  partyRef: ResourceRefModel;
  parent: { id: string, name: string, reference: string }
  items: BookingGetModel[];
  contact: { name: string, email: string, mobile: string }
  notes: string;
  fees: FeeGetModel[];
  stay: {
    checkinDate: Date,
    checkoutDate: Date,
    unitRef: ResourceRefModel,
    roomRef: ResourceRefModel,
    occupancy: OccupancyModel,
    totalPrice: {
      unitPriceInclTax: number,
      unitPriceExclTax:number,
      quantity: number,
      vatPercentage: number,
      vatAmount: number,
      amountExclTax: number,
      amountInclTax: number
    }
  }
  totalPrice: {
    subTotal: number,
    taxTotal: number,
    discount: number,
    deposit: number,
    total: number
  }
}
