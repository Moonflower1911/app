import {FeeModalityEnum} from '../../../properties/models/fee/enum/fee-modality.enum';

export interface FeeGetModel {
  id: string;
  name: string;
  modality: FeeModalityEnum;
  amount: number;
  required: boolean;
}
