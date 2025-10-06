import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, forkJoin } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { DashboardKPI, BackendKPIResponse } from '../models/kpi.model';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class KpiServiceService {

  private apiUrl = environment.apiBaseUrl || 'http://localhost:8080/';
  private kpiBaseUrl = `${this.apiUrl}bookingMgtApi/kpis`;

  constructor(private http: HttpClient) { }

  /**
   * Fetch all dashboard KPIs from the backend using separate endpoints
   */
  getDashboardKPIs(): Observable<DashboardKPI> {
    return forkJoin({
      checkins: this.getCheckins(),
      checkouts: this.getCheckouts(),
      inHouse: this.getInHouse(),
      booking: this.getBooking()
    }).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Fetch checkins data from the backend
   */
  getCheckins(): Observable<any> {
    return this.http.get<any>(`${this.kpiBaseUrl}/checkins`)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Fetch checkouts data from the backend
   */
  getCheckouts(): Observable<any> {
    return this.http.get<any>(`${this.kpiBaseUrl}/checkouts`)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Fetch in-house data from the backend
   */
  getInHouse(): Observable<any> {
    return this.http.get<any>(`${this.kpiBaseUrl}/in-house`)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Fetch booking data from the backend
   */
  getBooking(): Observable<any> {
    return this.http.get<any>(`${this.kpiBaseUrl}/booking`)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Handle HTTP errors gracefully
   */
  private handleError(error: any): Observable<any> {
    console.error('An error occurred:', error);
    throw error; // Re-throw the error to be handled by the component
  }
}
