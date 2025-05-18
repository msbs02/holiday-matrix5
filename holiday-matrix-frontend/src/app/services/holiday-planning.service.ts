import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HolidayPlanning } from '../models/holiday-planning';

@Injectable({
  providedIn: 'root'
})
export class HolidayPlanningService {
  private apiUrl = 'http://localhost:8080/api/holiday-planning';

  constructor(private http: HttpClient) { }

  getAll(): Observable<HolidayPlanning[]> {
    return this.http.get<HolidayPlanning[]>(this.apiUrl);
  }

  getById(id: number): Observable<HolidayPlanning> {
    return this.http.get<HolidayPlanning>(`${this.apiUrl}/${id}`);
  }

  getByEmployee(employeeId: number): Observable<HolidayPlanning[]> {
    return this.http.get<HolidayPlanning[]>(`${this.apiUrl}/employee/${employeeId}`);
  }

  getByPeriod(periodId: number): Observable<HolidayPlanning[]> {
    return this.http.get<HolidayPlanning[]>(`${this.apiUrl}/period/${periodId}`);
  }

  create(planning: HolidayPlanning): Observable<HolidayPlanning> {
    return this.http.post<HolidayPlanning>(this.apiUrl, planning);
  }

  validateByManager(planningId: number): Observable<HolidayPlanning> {
    return this.http.post<HolidayPlanning>(`${this.apiUrl}/${planningId}/validate/manager`, {});
  }

  validateByHOS(planningId: number): Observable<HolidayPlanning> {
    return this.http.post<HolidayPlanning>(`${this.apiUrl}/${planningId}/validate/hos`, {});
  }

  validateByDG(planningId: number): Observable<HolidayPlanning> {
    return this.http.post<HolidayPlanning>(`${this.apiUrl}/${planningId}/validate/dg`, {});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
