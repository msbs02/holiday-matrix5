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
/*
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
}*/

//make en commentaire le 11/05/2025 a 06:05
/*
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string = '';
  loading = false;
  returnUrl: string = '/dashboard/employee';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    // Initialiser le formulaire de connexion
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    // Récupérer l'URL de retour depuis les paramètres de route
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/dashboard/employee';

    // Rediriger si déjà connecté
    if (this.authService.currentUserValue) {
      this.router.navigate([this.returnUrl]);
    }
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const { username, password } = this.loginForm.value;

    this.authService.login(username, password).subscribe({
      next: () => {
        // Récupérer le rôle depuis localStorage
        const role = localStorage.getItem('userRole');

        if (role) {
          // Rediriger vers le dashboard approprié selon le rôle
          switch (role.toLowerCase()) {
            case 'employee':
              this.router.navigate(['/dashboard/employee']);
              break;
            case 'manager':
              this.router.navigate(['/dashboard/manager']);
              break;
            case 'head of service':
              this.router.navigate(['/dashboard/admin']);
              break;
            case 'direction general':
              this.router.navigate(['/dashboard/direction']);
              break;
            default:
              this.router.navigate(['/dashboard/employee']);
          }
        } else {
          this.router.navigate(['/dashboard/employee']);
        }
      },
      error: (err) => {
        this.errorMessage = 'Nom d\'utilisateur ou mot de passe incorrect';
        this.loading = false;
        console.error('Erreur de connexion', err);
      }
    });
  }
}*/

/*import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string = '';
  loading = false;
  returnUrl: string = '/';
  registrationSuccess = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    // Vérifier si l'utilisateur est déjà connecté
    if (this.authService.currentUser) {
      this.redirectBasedOnRole(this.authService.currentUser.role);
      return;
    }

    // Récupérer l'URL de retour ou définir la page d'accueil par défaut
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';

    // Vérifier si l'utilisateur vient de s'inscrire
    this.registrationSuccess = this.route.snapshot.queryParams['registered'] === 'true';


  }*/



/*
  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const { username, password } = this.loginForm.value;

    this.authService.login(username, password).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        this.loading = false;

        // Si l'utilisateur a un rôle, redirigez en fonction de ce rôle
        if (this.authService.currentUser && this.authService.currentUser.role) {
          this.redirectBasedOnRole(this.authService.currentUser.role);
        } else {
          this.router.navigate([this.returnUrl]);
        }
      },
      error: (err) => {
        console.error('Login error', err);
        this.loading = false;
        this.errorMessage = err.error?.message || 'Nom d\'utilisateur ou mot de passe invalide';
      }
    });
  }*/

  /*onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const { username, password } = this.loginForm.value;

    this.authService.login(username, password).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        this.loading = false;

        // Utiliser directement les informations de la réponse pour la redirection
        if (response && response.role) {
          console.log('Rôle détecté dans la réponse:', response.role);
          this.redirectBasedOnRole(response.role);
        } else if (this.authService.currentUser && this.authService.currentUser.role) {
          console.log('Rôle détecté dans currentUser:', this.authService.currentUser.role);
          this.redirectBasedOnRole(this.authService.currentUser.role);
        } else {
          console.log('Aucun rôle détecté, redirection vers:', this.returnUrl);
          this.router.navigate([this.returnUrl]);
        }
      },
      error: (err) => {
        console.error('Login error', err);
        this.loading = false;
        this.errorMessage = err.error?.message || 'Nom d\'utilisateur ou mot de passe invalide';
      }
    });
  }*/

  /*private redirectBasedOnRole(role: string): void {
    console.log('Redirecting based on role:', role);

    switch (role.toLowerCase()) {
      case 'employee':
        this.router.navigate(['/dashboard/employee']);
        break;
      case 'head_of_service':
        this.router.navigate(['/dashboard/admin']);
        break;
      case 'direction_general':
        this.router.navigate(['/dashboard/direction']);
        break;
      case 'manager':
        this.router.navigate(['/dashboard/manager']);
        break;
      default:
        this.router.navigate(['/dashboard/employee']);
        break;
    }
  }*/

  /*private redirectBasedOnRole(role: string): void {
    console.log('Redirecting based on role:', role);

    // Normalisation du rôle pour la comparaison
    const normalizedRole = role.toLowerCase().replace(/_/g, ' ');

    switch (normalizedRole) {
      case 'employee':
        this.router.navigate(['/dashboard/employee']);
        break;
      case 'head of service':
      case 'head_of_service':
        this.router.navigate(['/dashboard/admin']);
        break;
      case 'direction general':
      case 'direction_general':
        this.router.navigate(['/dashboard/direction']);
        break;
      case 'manager':
        this.router.navigate(['/dashboard/manager']);
        break;
      default:
        console.warn('Rôle inconnu:', role);
        this.router.navigate(['/dashboard/employee']);
        break;
    }
  }*/



  // Ajoutez cette méthode qui manque
  /*private redirectBasedOnRole(role: string): void {
    console.log('Redirecting based on role:', role);

    // Convertir en minuscules pour faciliter la comparaison
    const roleToLower = role.toLowerCase();


  private redirectBasedOnRole(role: string): void {
    console.log('Redirecting based on role:', role);

    // Convertir en minuscules et supprimer les espaces pour plus de robustesse
    const normalizedRole = role.toLowerCase().trim();

    if (normalizedRole.includes('employee')) {
      this.router.navigate(['/dashboard/employee']);
    } else if (normalizedRole.includes('head') || normalizedRole.includes('hos')) {
      this.router.navigate(['/dashboard/admin']);
    } else if (normalizedRole.includes('direct') || normalizedRole.includes('dg')) {
      this.router.navigate(['/dashboard/direction']);
    } else if (normalizedRole.includes('manager')) {
      this.router.navigate(['/dashboard/manager']);
    } else {
      // Par défaut, rediriger vers le dashboard de l'employé
      this.router.navigate(['/dashboard/employee']);
    }
  }



}*/


import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string = '';
  loading = false;
  returnUrl: string = '/';
  registrationSuccess = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }



  ngOnInit(): void {
    // Debugging
    console.log('localStorage token:', localStorage.getItem('token'));
    console.log('localStorage user:', localStorage.getItem('user'));
    console.log('authService.currentUser:', this.authService.currentUser);
    console.log('authService.isLoggedIn:', this.authService.isLoggedIn);

    // Vérifier si l'utilisateur est déjà connecté
    if (this.authService.currentUser) {
      this.redirectBasedOnRole(this.authService.currentUser.role);
      return;
    }

    // Récupérer l'URL de retour ou définir la page d'accueil par défaut
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';

    // Vérifier si l'utilisateur vient de s'inscrire
    this.registrationSuccess = this.route.snapshot.queryParams['registered'] === 'true';

    /*this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });*/
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const { username, password } = this.loginForm.value;

    this.authService.login(username, password).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        this.loading = false;

        // Si l'utilisateur a un rôle, redirigez en fonction de ce rôle
        if (this.authService.currentUser && this.authService.currentUser.role) {
          this.redirectBasedOnRole(this.authService.currentUser.role);
        } else {
          this.router.navigate([this.returnUrl]);
        }
      },
      error: (err) => {
        console.error('Login error', err);
        this.loading = false;
        this.errorMessage = err.error?.message || 'Nom d\'utilisateur ou mot de passe invalide';
      }
    });
  }

  // Ajoutez cette méthode qui manque
  /*private redirectBasedOnRole(role: string): void {
    console.log('Redirecting based on role:', role);

    // Convertir en minuscules pour faciliter la comparaison
    const roleToLower = role.toLowerCase();

    switch (roleToLower) {
      case 'employee':
        this.router.navigate(['/dashboard/employee']);
        break;
      case 'head_of_service':
        this.router.navigate(['/dashboard/admin']);
        break;
      case 'direction_general':
        this.router.navigate(['/dashboard/direction']);
        break;
      case 'manager':
        this.router.navigate(['/dashboard/manager']);
        break;
      default:
        console.log('Role non reconnu :', role);
        this.router.navigate(['/dashboard/employee']);
        break;
    }
  }*/

  private redirectBasedOnRole(role: string): void {
    console.log('Redirecting based on role:', role);

    // Force navigation with skipLocationChange to bypass guards temporarily
    // for testing purposes
    setTimeout(() => {
      try {
        const roleUpper = role.toUpperCase();
        console.log('Role uppercase:', roleUpper);

        switch (roleUpper) {
          case 'EMPLOYEE':
            this.router.navigateByUrl('/dashboard/employee');
            break;
          case 'HEAD_OF_SERVICE':
            this.router.navigateByUrl('/dashboard/admin');
            break;
          case 'DIRECTION_GENERAL':
            console.log('Redirecting to direction dashboard');
            this.router.navigateByUrl('/dashboard/direction');
            break;
          case 'MANAGER':
            this.router.navigateByUrl('/dashboard/manager');
            break;
          default:
            console.log('Role non reconnu :', role);
            this.router.navigateByUrl('/dashboard/employee');
            break;
        }
      } catch (error) {
        console.error('Navigation error:', error);
      }
    }, 100);
  }

}
