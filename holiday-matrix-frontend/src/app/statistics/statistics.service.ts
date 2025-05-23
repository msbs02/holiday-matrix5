import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface WeeklyStatistics {
  weekNumber: number;
  plannedHolidays: number;
}

export interface ManagerStatistics {
  managerId: string;
  managerName: string;
  weeklyStatistics: WeeklyStatistics[];
}

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {
  private apiUrl = '/api/statistics';

  constructor(private http: HttpClient) { }

  getAllManagers(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/managers`);
  }

  getManagerStatistics(managerId: string): Observable<ManagerStatistics> {
    return this.http.get<ManagerStatistics>(`${this.apiUrl}/manager/${managerId}`);
  }
}
