import { FeeModel } from './fee.model';

export interface BaseRateResponseModel {
  baseRate: number;
  fees: FeeModel[];
}
