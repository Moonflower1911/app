import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {PageFilterModel} from '../../../../shared/models/page-filter.model';
import {PageModel} from '../../../../shared/models/pageable/page.model';
import {environment} from '../../../../../environments/environment';

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
      let searchValue = pageFilter.search ? pageFilter.search : pageFilter.advancedSearchFormValue.advancedSearch;
      params = params.set('search', searchValue);
    }
    if(pageFilter.advancedSearchFormValue){
      if(pageFilter.advancedSearchFormValue.withParent!=null){
        params = params.set('withParent', pageFilter.advancedSearchFormValue.withParent);
      }
      if(pageFilter.advancedSearchFormValue.parentId!=null){
        params = params.set('parentId', pageFilter.advancedSearchFormValue.parentId);
      }
      if(pageFilter.advancedSearchFormValue.expanded!=null){
        params = params.set('expanded', pageFilter.advancedSearchFormValue.expanded);
      }
    }

    return this.httpClient.get<PageModel<any>>(environment.apiBaseUrl.concat(environment.ledgerGroupList), {params});
  }

  post(payload: any) {
    return this.httpClient.post<any>(environment.apiBaseUrl.concat(environment.ledgerGroupList), payload);
  }

  patchById(ledgerGroupId: string, payload: any) {
    return this.httpClient.patch<any>(environment.apiBaseUrl.concat(environment.ledgerGroupById.replace(':ledgerGroupId', ledgerGroupId)), payload);
  }

  deleteById(ledgerGroupId: any) {
    return this.httpClient.delete<any>(environment.apiBaseUrl.concat(environment.ledgerGroupById.replace(':ledgerGroupId', ledgerGroupId)));
  }

}
