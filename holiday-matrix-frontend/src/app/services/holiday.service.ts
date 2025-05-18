import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Holiday, HolidayRequest, HolidayStatus } from '../models/holiday.model';

@Injectable({
  providedIn: 'root'
})
export class HolidayService {
  private apiUrl = 'http://localhost:8080/api/holidays';

  constructor(private http: HttpClient) { }

  createHoliday(holiday: HolidayRequest): Observable<Holiday> {
    return this.http.post<Holiday>(this.apiUrl, holiday);
  }

  getMyHolidays(): Observable<Holiday[]> {
    return this.http.get<Holiday[]>(`${this.apiUrl}/my`);
  }

  getMyUpcomingHolidays(): Observable<Holiday[]> {
    return this.http.get<Holiday[]>(`${this.apiUrl}/my/upcoming`);
  }

  getTeamHolidays(): Observable<Holiday[]> {
    return this.http.get<Holiday[]>(`${this.apiUrl}/team`);
  }

  updateHolidayStatus(id: number, status: HolidayStatus): Observable<Holiday> {
    return this.http.put<Holiday>(`${this.apiUrl}/${id}/status`, null, {
      params: { status: status.toString() }
    });
  }
}
