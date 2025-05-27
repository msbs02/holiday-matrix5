import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EmployeeDashboardData {
  companyName: string;
  calendar: {
    month: string;
    year: number;
    highlightedDate: number;
    holidayDates: number[];
  };
  notifications: { message: string; read: boolean }[];
  news: { title: string; content: string }[];
}

export interface ManagerDashboardData {
  managerName: string;
  welcomeMessage: string;
  leaveRequests: {
    teamOrPosition: string;
    leaveCategory: string;
    employeeName: string;
    status: string;
  }[];
}

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private baseUrl = 'http://localhost:8080/api/dashboard';


  constructor(private http: HttpClient) {}

  getEmployeeDashboardData(): Observable<EmployeeDashboardData> {
    return this.http.get<EmployeeDashboardData>(`${this.baseUrl}/employee`);
  }

  getManagerDashboardData(): Observable<ManagerDashboardData> {
    return this.http.get<ManagerDashboardData>(`${this.baseUrl}/manager`);
  }
}
