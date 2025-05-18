/*import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, HttpClientModule],
  template: `<router-outlet></router-outlet>`,
  styles: []
})
export class AppComponent {
  title = 'holiday-matrix-frontend';
}
*/

import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { AuthService } from './services/auth.service';
import {User} from './models/user.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, HttpClientModule, CommonModule],
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4" *ngIf="isLoggedIn">
      <div class="container">
        <a class="navbar-brand" routerLink="/">Holiday Matrix</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link" routerLink="/dashboard/employee" routerLinkActive="active">Tableau de bord</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/matrix" routerLinkActive="active">Matrices</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/holiday-periods" routerLinkActive="active">Périodes de congés</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/holiday-planning" routerLinkActive="active">Planifications</a>
            </li>
            <li class="nav-item" *ngIf="isManager">
              <a class="nav-link" routerLink="/dashboard/manager" routerLinkActive="active">Dashboard Manager</a>
            </li>
            <li class="nav-item" *ngIf="isAdmin">
              <a class="nav-link" routerLink="/dashboard/admin" routerLinkActive="active">Dashboard HOS</a>
            </li>
            <li class="nav-item" *ngIf="isDirector">
              <a class="nav-link" routerLink="/dashboard/direction" routerLinkActive="active">Dashboard DG</a>
            </li>
          </ul>
          <div class="d-flex">
            <span class="navbar-text me-3">
              Connecté en tant que {{ currentUserName }}
            </span>
            <button class="btn btn-outline-light" (click)="logout()">Déconnexion</button>
          </div>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <router-outlet></router-outlet>
    </div>
  `,
  styles: [`
    .navbar-nav .nav-link.active {
      font-weight: bold;
    }
  `]
})
export class AppComponent implements OnInit {
  title = 'holiday-matrix-frontend';
  isLoggedIn = false;
  isManager = false;
  isAdmin = false;
  isDirector = false;
  currentUserName = '';

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.authService.currentUser$.subscribe(user => {
    //this.authService.currentUser$.subscribe((user: User) => {
      this.isLoggedIn = !!user;

      if (user) {
        this.currentUserName = user.username;

        const role = user.role.toLowerCase();
        this.isManager = role === 'manager' || role === 'head of service' || role === 'direction general';
        this.isAdmin = role === 'head of service' || role === 'direction general';
        this.isDirector = role === 'direction general';
      }
    });
  }

  logout() {
    this.authService.logout();
  }
}
