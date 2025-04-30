/*import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule]
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    // Création du formulaire avec deux champs : username et password
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      this.authService.login(username, password).subscribe({
        next: (response) => {
          console.log('Login successful', response);
          // Récupérer le rôle depuis localStorage (ou response.role si disponible)
          const role = localStorage.getItem('userRole');

          if (role ) {
            switch (role.toLowerCase()) {
              case 'employee':
                this.router.navigate(['/dashboard/employee']);
                break;
              case 'head of service':
                this.router.navigate(['/dashboard/admin']);
                break;
              case 'direction general':
                this.router.navigate(['/dashboard/direction']);
                break;
              case 'manager':
                this.router.navigate(['/dashboard/manager']);
                break;
              default:
                this.errorMessage = 'Rôle non reconnu.';
            }
          } else {
            this.errorMessage = 'Rôle non trouvé.';
          }
        },
        error: (err) => {
          console.error('Login error', err);
          this.errorMessage = 'Nom d’utilisateur ou mot de passe invalide.';
        }
      });
    }
  }
}*/

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule]
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      this.authService.login(username, password).subscribe({
        next: (response) => {
          console.log('Login successful', response);
          const role = localStorage.getItem('userRole');
          console.log('Rôle récupéré :', role);  // Log pour déboguer

          if (role) {
            switch (role.toLowerCase()) {
              case 'employee':
                this.router.navigate(['/dashboard/employee']);
                break;
              case 'head of service':
                this.router.navigate(['/dashboard/admin']);
                break;
              case 'direction general':
                this.router.navigate(['/dashboard/direction']);
                break;
              case 'manager':
                this.router.navigate(['/dashboard/manager']);
                break;
              default:
                this.errorMessage = 'Rôle non reconnu.';
            }
          } else {
            this.errorMessage = 'Rôle non trouvé.';
          }
        },
        error: (err) => {
          console.error('Login error', err);
          this.errorMessage = 'Nom d’utilisateur ou mot de passe invalide.';
        }
      });
    }
  }
}
