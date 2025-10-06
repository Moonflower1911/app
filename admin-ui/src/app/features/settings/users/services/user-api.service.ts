import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {PageModel} from '../../../../shared/models/pageable/page.model';
import {UserItemGetModel} from '../models/get/user-item-get.model';
import {environment} from '../../../../../environments/environment';
import {UserPostModel} from '../models/post/user-post.model';
import {UserPatchModel} from '../models/patch/user-patch.model';



@Injectable({
  providedIn: 'root'
})
export class UserApiService {

  constructor(private readonly httpClient: HttpClient) {}

  getUsersByPage(page: number, size: number, sort: string, sortDirection: string, search?: string): Observable<PageModel<UserItemGetModel>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (search) {
      params = params.set('search', search);
    }

    if (sort) {
      params = params.set('sort', `${sort},${sortDirection}`);
    }

    return this.httpClient.get<PageModel<UserItemGetModel>>(
      environment.apiBaseUrl.concat(environment.userList),
      { params }
    );
  }

  createUser(payload: UserPostModel): Observable<UserItemGetModel> {
    return this.httpClient.post<UserItemGetModel>(
      environment.apiBaseUrl.concat(environment.userList),
      payload
    );
  }

  updateUser(userId: string, payload: UserPatchModel): Observable<UserItemGetModel> {
    return this.httpClient.patch<UserItemGetModel>(
      environment.apiBaseUrl.concat(environment.userById).replace(':userId', userId),
      payload
    );
  }

  deleteUser(userId: string): Observable<void> {
    return this.httpClient.delete<void>(
      environment.apiBaseUrl.concat(environment.userById).replace(':userId', userId)
    );
  }
}
