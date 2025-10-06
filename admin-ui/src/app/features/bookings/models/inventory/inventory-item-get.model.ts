export interface InventoryItemGetModel {
  id: string;
  name: string;
  description: string;
  nature: string;
  availability: { quantity: number; available: number };
  floorSize: number;
  floorSizeUnit: string;
  maxOccupancy: { adults: number; children: number };
  amenities: string[];
  childrenAllowed: boolean;
  eventsAllowed: boolean;
  smokingAllowed: boolean;
  petsAllowed: boolean;
  beds: { type: string; quantity: number }[];

  // 🧠 Updated pricing fields
  avgRateNight?: number; // new field
  totalBookingRate?: number; // replaces totalPrice
  dailyRates?: { date: string; rate: number }[]; // replaces pricingPerDay

  // 🗑️ Removed (backend no longer sends fees)
  // fees?: FeeGetModel[];
}

