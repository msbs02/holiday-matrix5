/*import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Remplacez cette URL par celle de votre API d'authentification
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    const body = { username, password };
    return this.http.post<any>(`${this.apiUrl}/login`, body);
  }

  register(username: string, password: string, role: string = 'USER'): Observable<any> {
    const body = { username, password, role };
    return this.http.post<any>(`${this.apiUrl}/register`, body);
  }

}*/

/*
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Remplacez cette URL par celle de votre API d'authentification
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    // Créer les données encodées pour le formulaire
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    // Envoyer une requête POST avec les en-têtes appropriés
    return this.http.post<any>(`${this.apiUrl}/login`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      withCredentials: true // Inclure les cookies pour la gestion de session
    }).pipe(
      tap(response => {
        // Vérifier si la réponse contient un champ 'role' et le stocker dans localStorage
        if (response && response.role) {
          localStorage.setItem('userRole', response.role.toLowerCase());
        }
      })
    );
  }

  register(username: string, password: string, role: string = 'USER'): Observable<any> {
    const body = { username, password, role };
    return this.http.post<any>(`${this.apiUrl}/register`, body);
  }
}*/


import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080';  // Remplace par l'URL de ton API

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    return this.http.post<any>(`${this.apiUrl}/login`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      withCredentials: true
    }).pipe(
      tap(response => {
        console.log('Réponse API :', response);  // Log pour déboguer la réponse
        if (response && response.role) {
          localStorage.setItem('userRole', response.role.toLowerCase());
          console.log('Rôle stocké dans localStorage :', response.role.toLowerCase());
        } else {
          console.log('Aucun rôle dans la réponse');
        }
      })
    );
  }

  register(username: string, password: string, role: string = 'USER'): Observable<any> {
    const body = { username, password, role };
    return this.http.post<any>(`${this.apiUrl}/register`, body);
  }
}
