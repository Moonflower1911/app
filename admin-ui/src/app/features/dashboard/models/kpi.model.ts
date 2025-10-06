export interface BackendKPIResponse {
  booking: number;
  totalCountCheckOut: number;
  countCheckOut: number;
  inHouse: number;
  totalCountCheckIn: number;
  countCheckIn: number;
}

export interface DashboardKPI {
  checkins: CheckInKPI;
  checkouts: CheckOutKPI;
  inHouse: InHouseKPI;
  booking: BookingKPI;
}

export interface CheckInKPI {
  total: number;
  current: number;
  percentage: number;
}

export interface CheckOutKPI {
  total: number;
  current: number;
  percentage: number;
}

export interface InHouseKPI {
  total: number;
  percentage: number;
}

export interface BookingKPI {
  total: number;
  percentage: number;
}

export interface BookingData {
  checkinDate: string;
  checkoutDate: string;
  checkedInDate?: string;
  checkedOutDate?: string;
  parentId?: number;
} 