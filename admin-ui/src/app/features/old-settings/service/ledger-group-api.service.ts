import { Injectable } from '@angular/core';
import { PageFilterModel } from '../../../shared/models/page-filter.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { Observable } from 'rxjs';
import {LedgerGroupGetModel} from '../models/invoice/ledger-groups/ledger-get-model';
import {LedgerGroupPostModel} from '../models/invoice/ledger-groups/ledger-post-model';
import {LedgerGroupPatchModel} from '../models/invoice/ledger-groups/ledger-patch-model';

@Injectable({
  providedIn: 'root'
})
export class LedgerGroupApiService {

  constructor(private httpClient: HttpClient) {
  }

  getAllByPage(pageFilter: PageFilterModel) {
    let params = new HttpParams();
    params = params.set('page', pageFilter.page);
    params = params.set('size', pageFilter.size);

    if (pageFilter.search || pageFilter.advancedSearchFormValue?.search) {
      let searchValue = pageFilter.search ? pageFilter.search : pageFilter.advancedSearchFormValue.search;
      params = params.set('search', searchValue);
    }

    if (pageFilter.advancedSearchFormValue) {
      const advancedSearch = pageFilter.advancedSearchFormValue;

      if (advancedSearch.withParent !== undefined && advancedSearch.withParent !== null) {
        params = params.set('withParent', advancedSearch.withParent.toString());
      }

      if (advancedSearch.enabled !== undefined && advancedSearch.enabled !== null) {
        params = params.set('enabled', advancedSearch.enabled.toString());
      }
    }

    return this.httpClient.get(environment.apiBaseUrl.concat(environment.ledgerGroupList), { params });
  }

  getById(ledgerGroupId: string): Observable<LedgerGroupGetModel> {
    return this.httpClient.get<LedgerGroupGetModel>(
      environment.apiBaseUrl.concat(environment.ledgerGroupById.replace(':ledgerGroupId', ledgerGroupId))
    );
  }

  post(payload: LedgerGroupPostModel): Observable<LedgerGroupGetModel> {
    return this.httpClient.post<LedgerGroupGetModel>(
      environment.apiBaseUrl.concat(environment.ledgerGroupList),
      payload
    );
  }

  patchById(ledgerGroupId: string, payload: LedgerGroupPatchModel): Observable<LedgerGroupGetModel> {
    return this.httpClient.patch<LedgerGroupGetModel>(
      environment.apiBaseUrl.concat(environment.ledgerGroupById).replace(':ledgerGroupId', ledgerGroupId),
      payload
    );
  }

}
