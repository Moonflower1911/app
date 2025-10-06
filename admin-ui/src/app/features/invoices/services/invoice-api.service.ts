import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {PageModel} from '../../../shared/models/pageable/page.model';
import {InvoiceItemGetModel} from '../models/get/invoice-get.model';
import {PageFilterModel} from '../../../shared/models/page-filter.model';
import {environment} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InvoiceApiService {
  constructor(private httpClient: HttpClient) {
  }

  getInvoicesByPage(pageFilter: PageFilterModel) {
    let params = new HttpParams();
    params = params.set('page', pageFilter.page);
    params = params.set('size', pageFilter.size);

    if (pageFilter.search || pageFilter.advancedSearchFormValue?.search) {
      const searchValue = pageFilter.search
        ? pageFilter.search
        : pageFilter.advancedSearchFormValue.advancedSearch;
      params = params.set('search', searchValue);
    }
    return this.httpClient.get<PageModel<InvoiceItemGetModel>>(
      environment.apiBaseUrl.concat(environment.invoiceList),
      { params }
    );
  }

  getInvoiceById(invoiceId: string) {
    return this.httpClient.get<InvoiceItemGetModel>(
      environment.apiBaseUrl.concat(environment.invoiceById).replace(':invoiceId', invoiceId)
    );
  }

}
