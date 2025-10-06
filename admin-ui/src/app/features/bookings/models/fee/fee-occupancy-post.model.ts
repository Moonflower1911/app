import {AgeBucketModel} from '../../../../shared/models/age-bucket.model';

export interface FeeOccupancyPostModel {
  adults: number;
  children: {
    ageBucket: AgeBucketModel,
    quantity: number
  } | null;
}
