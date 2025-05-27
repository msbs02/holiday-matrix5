/*import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap, map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080';
  private currentUserSubject = new BehaviorSubject<any>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    // Vérifier si un utilisateur est déjà connecté au chargement du service
    const storedUser = localStorage.getItem('currentUser');
    if (storedUser) {
      this.currentUserSubject.next(JSON.parse(storedUser));
    }
  }

  public get currentUserValue() {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string): Observable<any> {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    return this.http.post<any>(`${this.apiUrl}/login`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      withCredentials: true
    }).pipe(
      tap(response => {
        // Récupérer le user et son rôle
        this.getUserDetails(username).subscribe(userDetails => {
          const user = {
            id: userDetails.id,
            username: userDetails.username,
            role: userDetails.role
          };

          // Stocker les informations utilisateur dans localStorage
          localStorage.setItem('currentUser', JSON.stringify(user));
          localStorage.setItem('userRole', user.role.toLowerCase());

          // Mettre à jour l'Observable currentUser
          this.currentUserSubject.next(user);
        });
      })
    );
  }

  register(username: string, password: string, role: string = 'EMPLOYEE'): Observable<any> {
    const body = { username, password, role };
    return this.http.post<any>(`${this.apiUrl}/api/auth/register`, body);
  }

  logout(): void {
    // Supprimer les données de l'utilisateur du localStorage
    localStorage.removeItem('currentUser');
    localStorage.removeItem('userRole');

    // Mettre à jour l'Observable currentUser
    this.currentUserSubject.next(null);

    // Rediriger vers la page de connexion
    this.router.navigate(['/login']);
  }

  getUserDetails(username: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/api/users/${username}`);
  }

  hasRole(role: string): boolean {
    const userRole = localStorage.getItem('userRole');
    if (!userRole) return false;
    return userRole.toLowerCase() === role.toLowerCase();
  }

  isManager(): boolean {
    return this.hasRole('manager') || this.hasRole('head of service') || this.hasRole('direction general');
  }

  isHOS(): boolean {
    return this.hasRole('head of service') || this.hasRole('direction general');
  }

  isDG(): boolean {
    return this.hasRole('direction general');
  }
}*/

// make en commentaire le 11/05/2025 a 5:18
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
//make en commentaire le 11/05/2025 a 06:02
/*
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

  login(username: string, password: string): Observable<any> {
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
  }

  register(username: string, password: string, role: string = 'USER'): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, { username, password, role });
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
}*/
//src/app/auth/auth.service.ts :
//make en comment le 26/05/2025
/*
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
  }*
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
  }*

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

}*/

//commenté le 26/05/2025 a 6:46
/*
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

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password }).pipe(
      map(response => {
        console.log("Login response:", response); // Pour déboguer
        if (response && response.token) {
          // Stocker l'utilisateur et le token avec toutes les informations nécessaires
          const user: User = {
            id: response.id || response.userId || null, // Essayer différents noms de champs
            username: response.username || username,
            email: response.email || null,
            firstName: response.firstName || null,
            lastName: response.lastName || null,
            role: response.role,
            department: response.department || null,
            position: response.position || null
          };

          // Vérification que l'ID est bien présent
          if (!user.id) {
            console.warn("Attention: ID utilisateur non trouvé dans la réponse:", response);
          }

          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('token', response.token);
          this.currentUserSubject.next(user);

          console.log("Utilisateur connecté:", user); // Debug
        }
        return response;
      })
    );
  }

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

  get currentUserValue(): User | null {
    return this.currentUserSubject.value;
  }

  get isLoggedIn(): boolean {
    return !!this.currentUserValue && !!this.token;
  }

  get token(): string | null {
    return localStorage.getItem('token');
  }

  // Nouvelle méthode pour récupérer l'ID utilisateur de manière sûre
  get currentUserId(): number | null {
    const user = this.currentUserValue;
    return user?.id || null;
  }

  hasRole(role: string): boolean {
    return this.currentUser?.role?.toLowerCase() === role.toLowerCase();
  }

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

  hasManagementRole(): boolean {
    return this.isManager() || this.isHOS() || this.isDG();
  }

  // Méthode pour vérifier si l'utilisateur est bien authentifié avec toutes les infos
  isFullyAuthenticated(): boolean {
    const user = this.currentUserValue;
    return !!(user && user.id && user.username && user.role && this.token);
  }

  // Méthode pour recharger les informations utilisateur depuis le serveur
  refreshUserInfo(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/me`).pipe(
      map(userInfo => {
        const updatedUser = { ...this.currentUserValue, ...userInfo };
        localStorage.setItem('user', JSON.stringify(updatedUser));
        this.currentUserSubject.next(updatedUser);
        return updatedUser;
      })
    );
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

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password }).pipe(
      map(response => {
        console.log("Login response:", response); // Pour déboguer
        if (response && response.token) {
          // Stocker l'utilisateur et le token avec toutes les informations maintenant disponibles
          const user: User = {
            id: response.id,
            username: response.username || username,
            email: response.email,
            firstName: response.firstName,
            lastName: response.lastName,
            role: response.role,
            department: response.department,
            position: response.position,
            managerId: response.managerId,
            managerName: response.managerName
          };

          // Vérification que l'ID est bien présent
          if (!user.id) {
            console.error("ERREUR CRITIQUE: ID utilisateur manquant dans la réponse:", response);
            throw new Error("Réponse de connexion invalide: ID utilisateur manquant");
          }

          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('token', response.token);
          this.currentUserSubject.next(user);

          console.log("Utilisateur connecté avec succès:", user); // Debug
        }
        return response;
      })
    );
  }

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

  get currentUserValue(): User | null {
    return this.currentUserSubject.value;
  }

  get isLoggedIn(): boolean {
    return !!this.currentUserValue && !!this.token;
  }

  get token(): string | null {
    return localStorage.getItem('token');
  }

  // Nouvelle méthode pour récupérer l'ID utilisateur de manière sûre
  get currentUserId(): number | null {
    const user = this.currentUserValue;
    return user?.id || null;
  }

  hasRole(role: string): boolean {
    return this.currentUser?.role?.toLowerCase() === role.toLowerCase();
  }

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

  hasManagementRole(): boolean {
    return this.isManager() || this.isHOS() || this.isDG();
  }

  // Méthode pour vérifier si l'utilisateur est bien authentifié avec toutes les infos
  isFullyAuthenticated(): boolean {
    const user = this.currentUserValue;
    return !!(user && user.id && user.username && user.role && this.token);
  }

  // Méthode pour recharger les informations utilisateur depuis le serveur
  refreshUserInfo(): Observable<User> {
    return this.http.get<any>(`${this.apiUrl}/me`).pipe(
      map(userInfo => {
        const updatedUser: User = {
          id: userInfo.id,
          username: userInfo.username,
          email: userInfo.email,
          firstName: userInfo.firstName,
          lastName: userInfo.lastName,
          role: userInfo.role,
          department: userInfo.department,
          position: userInfo.position,
          managerId: userInfo.managerId,
          managerName: userInfo.managerName
        };
        localStorage.setItem('user', JSON.stringify(updatedUser));
        this.currentUserSubject.next(updatedUser);
        console.log("Informations utilisateur mises à jour:", updatedUser);
        return updatedUser;
      })
    );
  }
}
