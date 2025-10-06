import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {PageFilterModel} from '../../../../shared/models/page-filter.model';
import {PageModel} from '../../../../shared/models/pageable/page.model';
import {environment} from '../../../../../environments/environment';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChargeApiService {

  constructor(private httpClient: HttpClient) {
  }

  getAllByPage(pageFilter: PageFilterModel) {
    console.log('your page filter is:', pageFilter);
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
      if (pageFilter.advancedSearchFormValue?.isPackage != null) {
        params = params.set('isPackage', pageFilter.advancedSearchFormValue.isPackage)
      }
      if (pageFilter.advancedSearchFormValue?.isExtra != null) {
        params = params.set('isExtra', pageFilter.advancedSearchFormValue.isExtra)
      }
    }
    return this.httpClient.get<PageModel<any>>(
      environment.apiBaseUrl.concat(environment.chargeList),
      {params}
    );
  }

  getById(chargeId: string) {
    return this.httpClient.get<any>(environment.apiBaseUrl.concat(environment.chargeById.replace(':chargeId', chargeId)));
  }

  post(payload: any): Observable<any> {
    return this.httpClient.post<any>(environment.apiBaseUrl.concat(environment.chargeList), payload);
  }

  patchById(chargeId: string, payload: any) {
    return this.httpClient.patch(environment.apiBaseUrl.concat(environment.chargeById.replace(':chargeId', chargeId)), payload);
  }
}
