import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environments/environment';
import {InventorySearchPayload} from '../models/inventory-search.interface';
import {InventoryItemGetModel} from '../models/inventory/inventory-item-get.model';
import {PageModel} from '../../../shared/models/pageable/page.model';

@Injectable({
  providedIn: 'root'
})
export class UnitApiService {

  constructor(private readonly httpClient: HttpClient) {
  }

  getInventory(payload: InventorySearchPayload): Observable<PageModel<InventoryItemGetModel>> {
    return this.httpClient.post<PageModel<InventoryItemGetModel>>(environment.apiBaseUrl.concat(environment.inventory), payload);
  }

}
