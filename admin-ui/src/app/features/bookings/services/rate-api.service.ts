import {Injectable} from '@angular/core';
import {UnitFeePostModel} from '../models/fee/unit-fee-post.model';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {Observable} from 'rxjs';
import {UnitFeeRateGetModel} from '../models/fee/unit-fee-rate-get.model';

@Injectable({
  providedIn: 'root'
})
export class RateApiService {

  constructor(private httpClient: HttpClient) {
  }

  postFeeRate(payload: UnitFeePostModel): Observable<UnitFeeRateGetModel> {
    return this.httpClient.post<UnitFeeRateGetModel>(environment.apiBaseUrl.concat(environment.feeCalculate), payload);
  }
}
