import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HolidayPeriod } from '../models/holiday-period';

@Injectable({
  providedIn: 'root'
})
export class HolidayPeriodService {
  private apiUrl = 'http://localhost:8080/api/holiday-periods';

  constructor(private http: HttpClient) { }

  getAll(): Observable<HolidayPeriod[]> {
    return this.http.get<HolidayPeriod[]>(this.apiUrl);
  }

  getById(id: number): Observable<HolidayPeriod> {
    return this.http.get<HolidayPeriod>(`${this.apiUrl}/${id}`);
  }

  getUpcoming(): Observable<HolidayPeriod[]> {
    return this.http.get<HolidayPeriod[]>(`${this.apiUrl}/upcoming`);
  }

  create(period: HolidayPeriod): Observable<HolidayPeriod> {
    return this.http.post<HolidayPeriod>(this.apiUrl, period);
  }

  update(id: number, period: HolidayPeriod): Observable<HolidayPeriod> {
    return this.http.put<HolidayPeriod>(`${this.apiUrl}/${id}`, period);
  }

  markNotified(id: number): Observable<HolidayPeriod> {
    return this.http.post<HolidayPeriod>(`${this.apiUrl}/${id}/notify`, {});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
