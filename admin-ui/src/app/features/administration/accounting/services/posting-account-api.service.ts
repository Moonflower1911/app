import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PageFilterModel} from '../../../../shared/models/page-filter.model';
import {PageModel} from '../../../../shared/models/pageable/page.model';
import {environment} from '../../../../../environments/environment';

export interface PostingAccountsGetResource {
  id: string;
  code: string;
  name: string;
  description: string;
  enabled: boolean;
  accountClassId: string;
  ledgerGroup?: {
    id: string;
    name: string;
  };
  ledgerSubgroup?: {
    id: string;
    name: string;
  };
}

export interface PostingAccountsPostResource {
  code: string;
  name: string;
  description: string;
  accountClassId: string;
  ledgerGroupId?: string;
  ledgerSubgroupId?: string;
  enabled: boolean;
}

export interface PostingAccountsPatchResource {
  code?: string;
  name?: string;
  description?: string;
  accountClassId?: string;
  ledgerGroupId?: string;
  ledgerSubgroupId?: string;
  enabled?: boolean;
}

export interface AccountClassResource {
  id: string;
  name: string;
}

export interface LedgerGroupResource {
  id: string;
  name: string;
}

export interface LedgerSubgroupResource {
  id: string;
  name: string;
}


@Injectable({
  providedIn: 'root'
})
export class PostingAccountApiService {

  constructor(private httpClient: HttpClient) {
  }

  getAllByPage(pageFilter: PageFilterModel): Observable<PageModel<PostingAccountsGetResource>> {
    let params = new HttpParams();
    params = params.set('page', pageFilter.page);
    params = params.set('size', pageFilter.size);
    if (pageFilter.search || pageFilter.advancedSearchFormValue?.search) {
      let searchValue = pageFilter.search ? pageFilter.search : pageFilter.advancedSearchFormValue.advancedSearch;
      params = params.set('search', searchValue);
    }
    return this.httpClient.get<PageModel<PostingAccountsGetResource>>(environment.apiBaseUrl.concat(environment.postingAccountList), {params});
  }

  post(payload: PostingAccountsPostResource): Observable<PostingAccountsGetResource> {
    return this.httpClient.post<PostingAccountsGetResource>(environment.apiBaseUrl.concat(environment.postingAccountList), payload);
  }

  patchById(postingAccountId: string, payload: PostingAccountsPatchResource): Observable<PostingAccountsGetResource> {
    return this.httpClient.patch<PostingAccountsGetResource>(environment.apiBaseUrl.concat(environment.postingAccountById.replace(':postingAccountId', postingAccountId)), payload);
  }

  getAccountClasses(): Observable<AccountClassResource[]> {
    return this.httpClient.get<AccountClassResource[]>(
      environment.apiBaseUrl.concat(environment.accountClassList),
    );
  }

  deleteById(postingAccountId: string): Observable<void> {
    const url = environment.apiBaseUrl.concat(
      environment.postingAccountById.replace(':postingAccountId', postingAccountId)
    );
    return this.httpClient.delete<void>(url);
  }


}
