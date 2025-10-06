// booking.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import {BaseRateResponseModel} from "../models/base-rate-response.model";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private readonly httpClient: HttpClient) {}

  /*getBaseRate(payload: {
    unitId: string;
    arrive: string;
    depart: string;
    adults: number;
    children: number;
  }) {
    return this.httpClient.post<BaseRateResponseModel>(
      environment.apiBaseUrl.concat(environment.existingRate),
      payload
    );
  }*/

  // MOCK RESPONSE
  getBaseRate(payload: {
    unitId: string;
    arrive: string;
    depart: string;
    adults: number;
    children: number;
  }) {
    const mockResponse: BaseRateResponseModel = {
      baseRate: 100,
      fees: [
        {
          feeName: 'Cleaning Fee',
          feeRate: 20,
          modality: 'PN',
          quantity: 2
        },
        {
          feeName: 'Tourism Tax',
          feeRate: 10,
          modality: 'PP',
          quantity: payload.adults
        }
      ]
    };

    return of(mockResponse);
  }

}
