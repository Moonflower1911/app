import {OccupancyModel} from '../commons/occupancy.model';

export interface InventoryPostModel {
  checkinDate: string;
  checkoutDate: string;
  guests: OccupancyModel;
}
