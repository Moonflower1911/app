import {Injectable} from '@angular/core';
import {PageFilterModel} from '../../../../../shared/models/page-filter.model';
import {HttpClient, HttpParams} from '@angular/common/http';
import {PageModel} from '../../../../../shared/models/pageable/page.model';
import {environment} from '../../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CancellationPolicyApiService {

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
      if (pageFilter.advancedSearchFormValue?.enabled) {
        params = params.set('enabled', pageFilter.advancedSearchFormValue.enabled);
      }
    }
    return this.httpClient.get<PageModel<any>>(
      environment.apiBaseUrl.concat(environment.cancellationPolicyList),
      {params}
    );
  }

  post(payload: any) {
    return this.httpClient.post<any>(environment.apiBaseUrl.concat(environment.cancellationPolicyList), payload);
  }

  patchById(cancellationPolicyId: any, payload: any) {
    return this.httpClient.patch(environment.apiBaseUrl.concat(environment.cancellationPolicyById.replace(':cancellationPolicyId', cancellationPolicyId)), payload);
  }
}
