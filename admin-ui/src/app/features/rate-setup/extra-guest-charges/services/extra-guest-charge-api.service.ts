import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {PageFilterModel} from '../../../../shared/models/page-filter.model';
import {PageModel} from '../../../../shared/models/pageable/page.model';
import {environment} from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ExtraGuestChargeApiService {

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
    if (pageFilter.advancedSearchFormValue) {
      if (pageFilter.advancedSearchFormValue.enabled != null) {
        params = params.set('enabled', pageFilter.advancedSearchFormValue.enabled)
      }
    }
    return this.httpClient.get<PageModel<any>>(environment.apiBaseUrl.concat(environment.extraGuestChargeList), {params});
  }

  getById(extraGuestChargeStrategyId: any) {
    return this.httpClient.get(environment.apiBaseUrl.concat(environment.extraGuestChargeById.replace(':additionalGuestStrategyId', extraGuestChargeStrategyId)));
  }

  post(payload: any) {
    return this.httpClient.post(environment.apiBaseUrl.concat(environment.extraGuestChargeList), payload);
  }

  patchById(extraGuestChargeStrategyId: any, payload: any) {
    return this.httpClient.patch(environment.apiBaseUrl.concat(environment.extraGuestChargeById.replace(':additionalGuestStrategyId', extraGuestChargeStrategyId)), payload)
  }
}
