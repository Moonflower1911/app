import {OccupancyModel} from '../commons/occupancy.model';
import {FeePostModel} from '../fee/fee-post.model';
import {ResourceRefModel} from '../../../../shared/models/resource-ref.model';

export interface BookingItemPostModel {
  checkinDate: String;
  checkoutDate: String;
  nightlyRate: number;
  vatPercentage: number;
  occupancy: OccupancyModel;
  unitRef: ResourceRefModel;
  fees?: FeePostModel[];
}
