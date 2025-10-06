import {Injectable} from '@angular/core';
import {PageFilterModel} from '../../../../shared/models/page-filter.model';
import {HttpClient, HttpParams} from '@angular/common/http';
import {PageModel} from '../../../../shared/models/pageable/page.model';
import {environment} from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AccountClassApiService {

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

    return this.httpClient.get<PageModel<any>>(environment.apiBaseUrl.concat(environment.accountClassList), {params});
  }

  post(payload: any) {
    return this.httpClient.post<any>(environment.apiBaseUrl.concat(environment.accountClassList), payload);
  }

  patchById(accountClassId: string, payload: any) {
    return this.httpClient.patch<any>(environment.apiBaseUrl.concat(environment.accountClassById.replace(':accountClassId', accountClassId)), payload);
  }

  deleteById(accountClassId: any) {
    return this.httpClient.delete<any>(environment.apiBaseUrl.concat(environment.accountClassById.replace(':accountClassId', accountClassId)));
  }
}
