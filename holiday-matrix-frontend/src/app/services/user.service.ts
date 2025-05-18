import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User, UserDetails } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  getUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${username}`);
  }

  getCurrentUserDetails(): Observable<UserDetails> {
    return this.http.get<UserDetails>(`${this.apiUrl}/me`);
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getUsersByRole(role: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/role/${role}`);
  }

  updateUser(id: number, user: {
    firstName: any;
    lastName: any;
    position: any;
    department: any;
    email: any
  }): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, user);
  }

  assignManager(userId: number, managerId: number): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${userId}/manager/${managerId}`, null);
  }
}
