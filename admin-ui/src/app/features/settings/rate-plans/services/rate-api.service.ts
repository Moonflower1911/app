import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RateApiService {

  constructor(private httpClient: HttpClient) {
  }

  getAll(ratePlanId: any, startDate: string, endDate: string) {
    let params = new HttpParams();
    params = params.set('ratePlanId', ratePlanId);
    params = params.set('startDate', startDate);
    params = params.set('endDate', endDate);
    return this.httpClient.get(environment.apiBaseUrl.concat(environment.rateList), {params})
  }

  post(payload: any) {
    return this.httpClient.post(environment.apiBaseUrl.concat(environment.rateList), payload);
  }

}
