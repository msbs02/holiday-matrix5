
/*
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private userSubject = new BehaviorSubject<User | null>(null);

  public user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient) {
    // Récupérer l'utilisateur du localStorage au démarrage
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.userSubject.next(JSON.parse(storedUser));
    }
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password }).pipe(
      tap(response => {
        if (response && response.token) {
          // Stocker l'utilisateur et le token
          const user: User = {
            username: response.username,
            role: response.role
          };
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('token', response.token);
          this.userSubject.next(user);
        }
      })
    );
  }

  register(username: string, password: string, role: string = 'USER'): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, { username, password, role });
  }

  logout(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.userSubject.next(null);
  }

  get currentUser(): User | null {
    return this.userSubject.value;
  }

  // Ajout de cette propriété qui manquait
  get currentUserValue(): User | null {
    return this.currentUserSubject.value;
  }

  get isLoggedIn(): boolean {
    return !!this.currentUser;
  }

  get token(): string | null {
    return localStorage.getItem('token');
  }

  hasRole(role: string): boolean {
    return this.currentUser?.role?.toLowerCase() === role.toLowerCase();
  }
}*/

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private currentUserSubject: BehaviorSubject<User | null>;
  public currentUser$: Observable<User | null>;

  constructor(private http: HttpClient) {
    // Récupérer l'utilisateur du localStorage au démarrage
    const storedUser = localStorage.getItem('user');
    this.currentUserSubject = new BehaviorSubject<User | null>(
      storedUser ? JSON.parse(storedUser) : null
    );
    this.currentUser$ = this.currentUserSubject.asObservable();
  }

  /*login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password }).pipe(
      map(response => {
        if (response && response.token) {
          // Stocker l'utilisateur et le token
          const user: User = {
            username: response.username,
            role: response.role
          };
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('token', response.token);
          this.currentUserSubject.next(user);
        }
        return response;
      })
    );
  }*/
  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password }).pipe(
      map(response => {
        console.log("Login response:", response); // Pour déboguer
        if (response && response.token) {
          // Stocker l'utilisateur et le token
          const user: User = {
            id: response.id, // Ajoutez l'ID si disponible dans la réponse
            username: response.username,
            role: response.role
          };
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('token', response.token);
          this.currentUserSubject.next(user);
        }
        return response;
      })
    );
  }


  /*register(username: string, password: string, role: string = 'USER', email: any, firstName: any, lastName: any, department: any, position: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, { username, password, role });
  }*/

  register(
    username: string,
    password: string,
    role: string = 'EMPLOYEE',
    email?: string,
    firstName?: string,
    lastName?: string,
    department?: string,
    position?: string
  ): Observable<any> {
    const body = {
      username,
      password,
      role,
      email,
      firstName,
      lastName,
      department,
      position
    };
    return this.http.post<any>(`${this.apiUrl}/register`, body);
  }


  logout(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.currentUserSubject.next(null);
  }

  get currentUser(): User | null {
    return this.currentUserSubject.value;
  }

  // Ajout de cette propriété qui manquait
  get currentUserValue(): User | null {
    return this.currentUserSubject.value;
  }

  // Ajout de la propriété isLoggedIn
  get isLoggedIn(): boolean {
    return !!this.currentUserValue; // Convertit l'objet en boolean (true si non-null)
  }

  get token(): string | null {
    return localStorage.getItem('token');
  }

  hasRole(role: string): boolean {
    return this.currentUser?.role?.toLowerCase() === role.toLowerCase();
  }

  // Ajoutez ces méthodes après la méthode hasRole

  isManager(): boolean {
    return this.hasRole('MANAGER');
  }

  isHOS(): boolean {
    return this.hasRole('HEAD_OF_SERVICE');
  }

  isDG(): boolean {
    return this.hasRole('DIRECTION_GENERAL');
  }

  isEmployee(): boolean {
    return this.hasRole('EMPLOYEE');
  }

// Cette méthode est utile pour vérifier si l'utilisateur a un rôle de management (MANAGER, HOS ou DG)
  hasManagementRole(): boolean {
    return this.isManager() || this.isHOS() || this.isDG();
  }

}
