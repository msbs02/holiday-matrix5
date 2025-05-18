/*import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const currentUser = this.authService.currentUserValue;

    if (!currentUser) {
      this.router.navigate(['/login']);
      return false;
    }

    // Vérification des rôles requis pour la route
    const requiredRoles = route.data['roles'] as string[];
    if (!requiredRoles || requiredRoles.length === 0) {
      return true;
    }

    // Vérifier si l'utilisateur a l'un des rôles requis
    const userRole = currentUser.role.toUpperCase();
    const hasRole = requiredRoles.some(role => role === userRole);

    if (hasRole) {
      return true;
    }

    // L'utilisateur n'a pas le rôle requis, rediriger vers une page appropriée
    this.router.navigate(['/dashboard/employee']);
    return false;
  }
}*/
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  /*canActivate(route: ActivatedRouteSnapshot): boolean {
    const requiredRoles = route.data['roles'] as Array<string>;
    console.log('Required roles:', requiredRoles);
    if (!requiredRoles || requiredRoles.length === 0) {
      return true;
    }

    const userRole = this.authService.currentUser?.role;
    console.log('User role:', userRole);
    if (!userRole) {
      console.warn('No user role found, redirecting to login');
      this.router.navigate(['/login']);
      return false;
    }

    const hasRequiredRole = requiredRoles.some(role =>
      userRole.toUpperCase() === role.toUpperCase()
    );

    if (hasRequiredRole) {
      return true;
    }
    */
  canActivate(route: ActivatedRouteSnapshot): boolean {
    const requiredRoles = route.data['roles'] as Array<string>;
    console.log('Required roles:', requiredRoles);
    console.log('URL actuelle:', route.url.join('/'));

    if (!requiredRoles || requiredRoles.length === 0) {
      console.log('Aucun rôle requis, accès autorisé');
      return true;
    }

    const userRole = this.authService.currentUser?.role;
    console.log('User role from service:', userRole);

    if (!userRole) {
      console.warn('No user role found, redirecting to login');
      this.router.navigate(['/login']);
      return false;
    }

    // Convertir en majuscules pour comparer
    const userRoleUpper = userRole.toUpperCase();
    console.log('Comparing user role:', userRoleUpper, 'with required roles', requiredRoles);

    const hasRequiredRole = requiredRoles.some(role => {
      const roleUpper = role.toUpperCase();
      console.log('Comparing with:', roleUpper, roleUpper === userRoleUpper);
      return roleUpper === userRoleUpper;
    });

    console.log('Has required role?', hasRequiredRole);

    if (hasRequiredRole) {
      return true;
    }

    // Ici on pourrait éviter de rediriger si on est déjà sur une page correspondant au rôle
    console.log('Role mismatch, redirecting based on role:', userRoleUpper);

    // Rediriger en fonction du rôle...
    // Rediriger en fonction du rôle
    switch (userRole.toUpperCase()) {
      case 'EMPLOYEE':
        this.router.navigate(['/dashboard/employee']);
        break;
      case 'MANAGER':
        this.router.navigate(['/dashboard/manager']);
        break;
      case 'HEAD_OF_SERVICE':
        this.router.navigate(['/dashboard/admin']);
        break;
      case 'DIRECTION_GENERAL':
        this.router.navigate(['/dashboard/direction']);
        break;
      default:
        this.router.navigate(['/login']);
    }

    return false;
  }
}


