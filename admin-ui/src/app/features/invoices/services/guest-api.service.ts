import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {Observable} from 'rxjs';
import {PartyItemGetModel} from '../models/get/party-item-get.model';

@Injectable({
  providedIn: 'root'
})
export class GuestApiService {
  constructor(private httpClient: HttpClient) {
  }
  getPartyById(partyId: string): Observable<PartyItemGetModel> {
    return this.httpClient.get<PartyItemGetModel>(
      environment.apiBaseUrl.concat(environment.partyById).replace(':partyId', partyId)
    );
  }
}
