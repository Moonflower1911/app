import {SegmentRefGetModel} from '../commons/segment-ref-get.model';

export interface RatePlanPostModel {
  name: string;
  enabled: boolean;
  standard: boolean;
  segments: SegmentRefGetModel[];
  unitId: string;
}
