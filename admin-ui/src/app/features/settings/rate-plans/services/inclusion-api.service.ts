import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {PageModel} from '../../../../shared/models/pageable/page.model';
import {environment} from '../../../../../environments/environment';
import {PageFilterModel} from '../../../../shared/models/page-filter.model';

@Injectable({
  providedIn: 'root'
})
export class InclusionApiService {

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
      if (pageFilter.advancedSearchFormValue?.enabled != null) {
        params = params.set('enabled', pageFilter.advancedSearchFormValue.enabled);
      }
      if (pageFilter.advancedSearchFormValue?.ratePlanId != null) {
        params = params.set('ratePlanId', pageFilter.advancedSearchFormValue.ratePlanId);
      }
    }
    return this.httpClient.get<PageModel<any>>(
      environment.apiBaseUrl.concat(environment.inclusionList),
      {params}
    );
  }

  post(payload: any) {
    return this.httpClient.post(environment.apiBaseUrl.concat(environment.inclusionList), payload);
  }

  patchById(id: any, payload: any) {
    return this.httpClient.patch(environment.apiBaseUrl.concat(environment.inclusionById.replace(':inclusionId', id)), payload);
  }

  deleteById(id:any) {
    return this.httpClient.delete(environment.apiBaseUrl.concat(environment.inclusionById.replace(':inclusionId', id)));

  }
}
