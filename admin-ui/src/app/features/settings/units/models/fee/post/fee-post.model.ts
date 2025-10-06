import {FeeModalityEnum} from '../enum/fee-modality.enum';
import {UnitRefModel} from '../commons/unit-ref.model';

export interface FeePostModel {
  name: string;
  amount: number;
  modality: FeeModalityEnum;
  description?: string;
  active?: boolean;
  required: boolean;
  unit: UnitRefModel;
  additionalGuestPrices?: any[];
}
