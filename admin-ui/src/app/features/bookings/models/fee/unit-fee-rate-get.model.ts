import {FeeModalityEnum} from '../../../properties/models/fee/enum/fee-modality.enum';
import {RateGetModel} from '../inventory/rate-get.model';
import {BreakdownItemGetModel} from './breakdown-item-get.model';

export interface UnitFeeRateGetModel {
  id: string;
  name: string;
  modality: FeeModalityEnum;
  amount: number;
  required: boolean;
  rate: RateGetModel;
  breakdown: BreakdownItemGetModel[];
}
