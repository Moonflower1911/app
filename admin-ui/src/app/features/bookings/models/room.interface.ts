export interface InventoryResponse {
  content: Room[];
}

export interface RoomAvailability {
  quantity: number;
  available: number;
}

export interface MaxOccupancy {
  adults: number;
  children: number;
}

export interface Bed {
  type: string;
  quantity: number;
}

export interface RoomRate {
  pricingPerDay: { [date: string]: number };
  averagePrice: number;
  fees: RoomFee[];
}

export interface RoomFee {
  id: number;
  title: string;
  price: number;
  required: boolean;
}

export interface Room {
  id: string;
  name: string;
  description: string;
  nature: string;
  availability: RoomAvailability;
  floorSize: number;
  floorSizeUnit: string;
  maxOccupancy: MaxOccupancy;
  amenities: string[];
  childrenAllowed: boolean;
  eventsAllowed: boolean;
  smokingAllowed: boolean;
  petsAllowed: boolean;
  beds: Bed[];
  rate: RoomRate;
}
