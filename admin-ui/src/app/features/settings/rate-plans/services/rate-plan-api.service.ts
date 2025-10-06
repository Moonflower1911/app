import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../../../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import {PageFilterModel} from '../../../../shared/models/page-filter.model';
import {PageModel} from '../../../../shared/models/pageable/page.model';

@Injectable({
  providedIn: 'root'
})
export class RatePlanApiService {

  constructor(private httpClient: HttpClient) {
  }

  getAllByPage(pageFilter: PageFilterModel): Observable<PageModel<any>> {
    let params = new HttpParams();
    params = params.set('page', pageFilter.page);
    params = params.set('size', pageFilter.size);

    if (pageFilter.search || pageFilter.advancedSearchFormValue?.search) {
      let searchValue = pageFilter.search ? pageFilter.search : pageFilter.advancedSearchFormValue.search;
      params = params.set('search', searchValue);
    }
    return this.httpClient.get<PageModel<any>>(
      environment.apiBaseUrl.concat(environment.ratePlanList),
      {params}
    );
  }

  getById(ratePlanId: string) {
    return this.httpClient.get<any>(environment.apiBaseUrl.concat(environment.ratePlanById.replace(':ratePlanId', ratePlanId)));
  }

  post(payload: any): Observable<any> {
    return this.httpClient.post<any>(environment.apiBaseUrl.concat(environment.ratePlanList), payload);
  }

  patchById(ratePlanId: any, payload: any) {
    return this.httpClient.patch(environment.apiBaseUrl.concat(environment.ratePlanById.replace(':ratePlanId', ratePlanId)), payload);
  }

}
