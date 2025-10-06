export interface FeeModel {
  feeName: string;
  feeRate: number;
  modality: 'PPPN' | 'PN' | 'PP';
  quantity: number;
}
