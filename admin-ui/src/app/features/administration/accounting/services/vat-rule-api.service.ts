import {Injectable} from '@angular/core';
import {PageFilterModel} from '../../../../shared/models/page-filter.model';
import {HttpClient, HttpParams} from '@angular/common/http';
import {PageModel} from '../../../../shared/models/pageable/page.model';
import {environment} from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VatRuleApiService {

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
    return this.httpClient.get<PageModel<any>>(
      environment.apiBaseUrl.concat(environment.vatRuleList),
      {params}
    );
  }

  post(payload: any) {
    return this.httpClient.post(environment.apiBaseUrl.concat(environment.vatRuleList), payload);
  }

  patch(vatRuleId: any, payload: any) {
    return this.httpClient.patch(environment.apiBaseUrl.concat(environment.vatRuleById.replace(':vatRuleId', vatRuleId)), payload);
  }

  deleteById(vatRuleId: any) {
    return this.httpClient.delete(environment.apiBaseUrl.concat(environment.vatRuleById.replace(':vatRuleId', vatRuleId)));
  }
}
