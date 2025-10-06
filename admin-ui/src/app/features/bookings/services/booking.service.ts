import {Injectable} from '@angular/core';
import {OccupancyModel} from '../models/commons/occupancy.model';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor() {
  }

  static getOccupancy(occupancy: OccupancyModel, format: string) {
    if (format == 'short') {
      const totalChildren = (children?: { age: number, quantity: number }[]) => {
        return children?.reduce((sum, child) => sum + child.quantity, 0) ?? 0;
      };
      return occupancy.adults + 'A ' + totalChildren(occupancy.children) + 'C'
    } else {
      return 0 + ' A ' + 0 + ' C';
    }
  }



}
