import {Injectable} from '@angular/core';
import {PageFilterModel} from '../../../shared/models/page-filter.model';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {BookingPostModel} from '../models/booking/booking-post.model';
import {Observable} from 'rxjs';
import {BookingGetModel} from '../models/booking/booking-get.model';
import {BookingItemPostModel} from '../models/booking/booking-item-post.model';

@Injectable({
    providedIn: 'root'
})
export class BookingApiService {

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
        const advancedSearch = pageFilter.advancedSearchFormValue;

        if (advancedSearch.checkinDate) {
          params = params.set('checkinDate', advancedSearch.checkinDate);
        }
        if (advancedSearch.checkoutDate) {
          params = params.set('checkoutDate', advancedSearch.checkoutDate);
        }

        if (advancedSearch.statuses) {
          params = params.set('statuses', advancedSearch.statuses);
        }

        if (advancedSearch.types) {
          params = params.set('types', advancedSearch.types);
        }

        if (advancedSearch.unitId) {
          params = params.set('unitId', advancedSearch.unitId);
        }


      }
        return this.httpClient.get(environment.apiBaseUrl.concat(environment.bookingList), {params})
    }

    getById(bookingId: string): Observable<BookingGetModel> {
        return this.httpClient.get<BookingGetModel>(environment.apiBaseUrl.concat(environment.bookingById.replace(':bookingId', bookingId)));
    }

    post(payload: BookingPostModel): Observable<any> {
        return this.httpClient.post(environment.apiBaseUrl.concat(environment.bookingList), payload);
    }

    postById(payload: BookingItemPostModel, bookingId: string): Observable<BookingGetModel> {
        return this.httpClient.post<BookingGetModel>(environment.apiBaseUrl.concat(environment.bookingById.replace(':bookingId', bookingId)), payload);
    }

    patchById(bookingId: string, payload: any): Observable<BookingGetModel> {
        return this.httpClient.patch<BookingGetModel>(environment.apiBaseUrl.concat(environment.bookingById).replace(':bookingId', bookingId), payload);
    }

    deleteById(bookingId: string) {
        return this.httpClient.delete(environment.apiBaseUrl.concat(environment.bookingById.replace(':bookingId', bookingId)));
    }

}
