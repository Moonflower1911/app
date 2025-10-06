import {OccupancyModel} from '../commons/occupancy.model';

export interface UnitFeePostModel{
  nights: number;
  guests: OccupancyModel;
  feeId: string;
}
