import {FeeModalityEnum} from '../../../properties/models/fee/enum/fee-modality.enum';
import {FeeOccupancyPostModel} from './fee-occupancy-post.model';

export interface FeePostModel {
  feeRef: { id: string, name: string, price: number };
  price: number;
  vatPercentage: number;
  quantity: number;
  bookingId?: string;
  modality: FeeModalityEnum;
  occupancy: FeeOccupancyPostModel;
}
