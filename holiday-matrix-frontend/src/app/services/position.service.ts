import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Position } from '../models/position';

@Injectable({
  providedIn: 'root'
})
export class PositionService {
  private apiUrl = 'http://localhost:8080/api/positions';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Position[]> {
    return this.http.get<Position[]>(this.apiUrl);
  }

  getById(id: number): Observable<Position> {
    return this.http.get<Position>(`${this.apiUrl}/${id}`);
  }

  getByOrganization(organizationId: number): Observable<Position[]> {
    return this.http.get<Position[]>(`${this.apiUrl}/organization/${organizationId}`);
  }

  getCritical(): Observable<Position[]> {
    return this.http.get<Position[]>(`${this.apiUrl}/critical`);
  }

  create(position: Position): Observable<Position> {
    return this.http.post<Position>(this.apiUrl, position);
  }

  update(id: number, position: Position): Observable<Position> {
    return this.http.put<Position>(`${this.apiUrl}/${id}`, position);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
