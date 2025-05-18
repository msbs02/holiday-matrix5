/*
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      role: ['USER', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      const { username, password, role } = this.registerForm.value;
      this.authService.register(username, password, role).subscribe({
        next: () => this.router.navigate(['/login']),
        error: err => this.errorMessage = 'Erreur lors de l’enregistrement'
      });
    }
  }
}
*/

import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';
  loading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    // Initialiser le formulaire d'inscription
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required], // Ajout de ce champ
      email: ['', [Validators.email]],
      firstName: [''],
      lastName: [''],
      department: [''],
      position: [''],
      role: ['EMPLOYEE', Validators.required]
    }, {
      validators: this.passwordMatchValidator
    });

    // Rediriger si déjà connecté
    if (this.authService.currentUserValue) {
      this.router.navigate(['/dashboard/employee']);
    }
  }

  // Validateur personnalisé pour vérifier que les mots de passe correspondent
  passwordMatchValidator(formGroup: FormGroup) {
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;

    if (password !== confirmPassword) {
      formGroup.get('confirmPassword')?.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    } else {
      formGroup.get('confirmPassword')?.setErrors(null);
      return null;
    }
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    //const { username, password, role } = this.registerForm.value;
    const formValues = this.registerForm.value;
    this.authService.register(
      formValues.username,
      formValues.password,
      formValues.role,
      formValues.email,
      formValues.firstName,
      formValues.lastName,
      formValues.department,
      formValues.position
    ).subscribe({
      next: () => {
        this.successMessage = 'Inscription réussie ! Vous pouvez maintenant vous connecter.';
        this.loading = false;
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Erreur lors de l\'inscription. Veuillez réessayer.';
        if (err.error?.error === 'Username already exists') {
          this.errorMessage = 'Ce nom d\'utilisateur existe déjà.';
        }
        this.loading = false;
        console.error('Erreur d\'inscription', err);
      }
    });
  }
}
