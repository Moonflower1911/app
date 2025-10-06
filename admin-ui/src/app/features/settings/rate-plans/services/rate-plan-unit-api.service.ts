import {Injectable} from '@angular/core';
import {PageFilterModel} from '../../../../shared/models/page-filter.model';
import {HttpClient, HttpParams} from '@angular/common/http';
import {PageModel} from '../../../../shared/models/pageable/page.model';
import {environment} from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RatePlanUnitApiService {

  constructor(private httpClient: HttpClient) {
  }

  getAllByPage(ratePlanId: any, pageFilter: PageFilterModel) {
    let params = new HttpParams();
    params = params.set('page', pageFilter.page);
    params = params.set('size', pageFilter.size);
    params = params.set('ratePlanId', ratePlanId)
    if (pageFilter.search || pageFilter.advancedSearchFormValue?.search) {
      let searchValue = pageFilter.search ? pageFilter.search : pageFilter.advancedSearchFormValue.search;
      params = params.set('search', searchValue);
    }
    if (pageFilter.advancedSearchFormValue) {
      if (pageFilter.advancedSearchFormValue?.enabled != null) {
        params = params.set('enabled', pageFilter.advancedSearchFormValue.enabled);
      }
      if (pageFilter.advancedSearchFormValue?.ratePlanId != null) {
        params = params.set('ratePlanId', pageFilter.advancedSearchFormValue.ratePlanId);
      }
    }
    return this.httpClient.get<PageModel<any>>(
      environment.apiBaseUrl.concat(environment.ratePlanUnitList),
      {params}
    );
  }

  patchById(id: string, payload: any) {
    return this.httpClient.patch(
      environment.apiBaseUrl.concat(environment.ratePlanUnitById.replace(':ratePlanUnitId', id)),
      payload
    );

  }

  post(payload: any) {
    return this.httpClient.post(environment.apiBaseUrl.concat(environment.ratePlanUnitList), payload);
  }
}
