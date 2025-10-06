import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InvoiceApiService {
  private baseUrl = '/api/invoices';

  constructor(private httpClient: HttpClient) {
  }

  generateInvoicePdfByBookingId(bookingId: string): Observable<Blob> {
    const url = environment.apiBaseUrl.concat(
      environment.invoiceGeneratePdf.replace(':bookingId', bookingId)
    );

    return this.httpClient.post(url, {}, { responseType: 'blob' });
  }
}
