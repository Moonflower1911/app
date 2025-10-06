import {GuestTypeEnum} from '../../../../shared/models/guest-type.enum';
import {AgeBucketModel} from '../../../../shared/models/age-bucket.model';
import {RateGetModel} from '../inventory/rate-get.model';

export interface BreakdownItemGetModel{
  guestType: GuestTypeEnum;
  ageBucket: AgeBucketModel;
  quantity: number;
  rate: RateGetModel;
}
