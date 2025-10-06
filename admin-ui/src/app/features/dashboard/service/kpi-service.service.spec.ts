import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '../../../../environments/environment';

import { KpiServiceService } from './kpi-service.service';
import { DashboardKPI } from '../models/kpi.model';

describe('KpiServiceService', () => {
  let service: KpiServiceService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [KpiServiceService]
    });
    service = TestBed.inject(KpiServiceService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch dashboard KPIs from separate endpoints', () => {
    const mockCheckins = { totalCountCheckIn: 10, countCheckIn: 8 };
    const mockCheckouts = { totalCountCheckOut: 5, countCheckOut: 2 };
    const mockInHouse = { inHouse: 3 };
    const mockBooking = { booking: 7 };

    service.getDashboardKPIs().subscribe(kpis => {
      expect(kpis).toBeDefined();
      expect(kpis.checkins).toBeDefined();
      expect(kpis.checkouts).toBeDefined();
      expect(kpis.inHouse).toBeDefined();
      expect(kpis.booking).toBeDefined();
    });

    const checkinsReq = httpMock.expectOne(`${environment.apiBaseUrl}/bookingMgtApi/kpis/checkins`);
    const checkoutsReq = httpMock.expectOne(`${environment.apiBaseUrl}/bookingMgtApi/kpis/checkouts`);
    const inHouseReq = httpMock.expectOne(`${environment.apiBaseUrl}/bookingMgtApi/kpis/in-house`);
    const bookingReq = httpMock.expectOne(`${environment.apiBaseUrl}/bookingMgtApi/kpis/booking`);

    checkinsReq.flush(mockCheckins);
    checkoutsReq.flush(mockCheckouts);
    inHouseReq.flush(mockInHouse);
    bookingReq.flush(mockBooking);
  });

  it('should fetch checkins data', () => {
    const mockCheckins = { totalCountCheckIn: 10, countCheckIn: 8 };

    service.getCheckins().subscribe(data => {
      expect(data).toEqual(mockCheckins);
    });

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/bookingMgtApi/kpis/checkins`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCheckins);
  });

  it('should fetch checkouts data', () => {
    const mockCheckouts = { totalCountCheckOut: 5, countCheckOut: 2 };

    service.getCheckouts().subscribe(data => {
      expect(data).toEqual(mockCheckouts);
    });

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/bookingMgtApi/kpis/checkouts`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCheckouts);
  });

  it('should fetch in-house data', () => {
    const mockInHouse = { inHouse: 3 };

    service.getInHouse().subscribe(data => {
      expect(data).toEqual(mockInHouse);
    });

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/bookingMgtApi/kpis/in-house`);
    expect(req.request.method).toBe('GET');
    req.flush(mockInHouse);
  });

  it('should fetch booking data', () => {
    const mockBooking = { booking: 7 };

    service.getBooking().subscribe(data => {
      expect(data).toEqual(mockBooking);
    });

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/bookingMgtApi/kpis/booking`);
    expect(req.request.method).toBe('GET');
    req.flush(mockBooking);
  });
});
