import {FeeModalityEnum} from '../../../properties/models/fee/enum/fee-modality.enum';
import {OccupancyModel} from '../commons/occupancy.model';
import {FeeOccupancyPostModel} from './fee-occupancy-post.model';
import {RateGetModel} from '../inventory/rate-get.model';

export interface FeeGetModel {
  id: string;
  price: number;
  quantity: number;
  feeRef: { id: string, name: string, price: number };
  modality: FeeModalityEnum;
  occupancy: FeeOccupancyPostModel;
  rate: RateGetModel;
}
