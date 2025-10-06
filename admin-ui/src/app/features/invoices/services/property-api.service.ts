import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {Observable} from 'rxjs';
import {PropertyGetModel} from '../models/get/property-get.model';

@Injectable({
  providedIn: 'root'
})
export class PropertyApiService {
  constructor(private httpClient: HttpClient) {
  }

  getCurrentProperty(): Observable<PropertyGetModel> {
    return this.httpClient.get<PropertyGetModel>(
      environment.apiBaseUrl.concat(environment.properties)
    );
  }
}
