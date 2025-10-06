export interface InventorySearchPayload {
  checkinDate: string;
  checkoutDate: string;
  guests: {
    adults: number;
    children?: Array<{ age: number; quantity: number }>;
  };
  party?: any;
  segmentId?: string | null;
  subSegmentId?: string | null;
}
