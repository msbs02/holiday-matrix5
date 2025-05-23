/*import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { EmployeeDashboardComponent } from './dashboard/employee-dashboard/employee-dashboard.component';
import { ManagerDashboardComponent } from './dashboard/manager-dashboard/manager-dashboard.component';


export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent } ,
  { path: 'register', component: RegisterComponent } ,
  { path: 'dashboard/employee', component: EmployeeDashboardComponent },
  { path: 'dashboard/manager', component: ManagerDashboardComponent },
];*/

//make en commentaire le 10/05/2025 a 22:05
/*
import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { EmployeeDashboardComponent } from './dashboard/employee-dashboard/employee-dashboard.component';
import { ManagerDashboardComponent } from './dashboard/manager-dashboard/manager-dashboard.component';
import { DirectionDashboardComponent } from './dashboard/direction-dashboard/direction-dashboard.component';
import { AdminDashboardComponent } from './dashboard/admin-dashboard/admin-dashboard.component';
import { MatrixListComponent } from './matrix/matrix-list/matrix-list.component';
import { MatrixFormComponent } from './matrix/matrix-form/matrix-form.component';
import { MatrixDetailComponent } from './matrix/matrix-detail/matrix-detail.component';
import { HolidayPeriodListComponent } from './holiday-period/holiday-period-list/holiday-period-list.component';
import { HolidayPlanningListComponent } from './holiday-planning/holiday-planning-list/holiday-planning-list.component';
import { HolidayPlanningFormComponent } from './holiday-planning/holiday-planning-form/holiday-planning-form.component';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // Dashboards
  {
    path: 'dashboard/employee',
    component: EmployeeDashboardComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'dashboard/manager',
    component: ManagerDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL'] }
  },
  {
    path: 'dashboard/direction',
    component: DirectionDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['DIRECTION_GENERAL'] }
  },
  {
    path: 'dashboard/admin',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['HEAD_OF_SERVICE', 'DIRECTION_GENERAL'] }
  },

  // Matrix
  {
    path: 'matrix',
    component: MatrixListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'matrix/create',
    component: MatrixFormComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL'] }
  },
  {
    path: 'matrix/edit/:id',
    component: MatrixFormComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL'] }
  },
  {
    path: 'matrix/:id',
    component: MatrixDetailComponent,
    canActivate: [AuthGuard]
  },

  // Holiday Periods
  {
    path: 'holiday-periods',
    component: HolidayPeriodListComponent,
    canActivate: [AuthGuard]
  },

  // Holiday Planning
  {
    path: 'holiday-planning',
    component: HolidayPlanningListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'holiday-planning/create',
    component: HolidayPlanningFormComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'holiday-planning/edit/:id',
    component: HolidayPlanningFormComponent,
    canActivate: [AuthGuard]
  },

  // Redirection par défaut
  { path: '**', redirectTo: 'login' }
];*/

/*import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { EmployeeDashboardComponent } from './dashboard/employee-dashboard/employee-dashboard.component';
import { ManagerDashboardComponent } from './dashboard/manager-dashboard/manager-dashboard.component';
import { DirectorDashboardComponent } from './dashboard/direction-dashboard/direction-dashboard.component';
import { AdminDashboardComponent } from './dashboard/admin-dashboard/admin-dashboard.component';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'dashboard/employee',
    component: EmployeeDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['EMPLOYEE'] }
  },
  {
    path: 'dashboard/manager',
    component: ManagerDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['MANAGER'] }
  },
  {
    path: 'dashboard/direction',
    component: DirectorDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['DIRECTION_GENERAL'] }
  },
  {
    path: 'dashboard/admin',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['HEAD_OF_SERVICE'] }
  },
];*/


//make en comment le 23/05/2025 a 1:26 am
/*
import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { EmployeeDashboardComponent } from './dashboard/employee-dashboard/employee-dashboard.component';
import { ManagerDashboardComponent } from './dashboard/manager-dashboard/manager-dashboard.component';
import { DirectorDashboardComponent } from './dashboard/direction-dashboard/direction-dashboard.component';
import { AdminDashboardComponent } from './dashboard/admin-dashboard/admin-dashboard.component';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';

// Imports pour Matrix
import { MatrixListComponent } from './matrix/matrix-list/matrix-list.component';
import { MatrixFormComponent } from './matrix/matrix-form/matrix-form.component';
import { MatrixDetailComponent } from './matrix/matrix-detail/matrix-detail.component';

// Imports pour HolidayPeriod
import { HolidayPeriodListComponent } from './holiday-period/holiday-period-list/holiday-period-list.component';

// Imports pour HolidayPlanning
import { HolidayPlanningListComponent } from './holiday-planning/holiday-planning-list/holiday-planning-list.component';
import { HolidayPlanningFormComponent } from './holiday-planning/holiday-planning-form/holiday-planning-form.component';
import {ManagerStatisticsComponent} from './statistics/manager-statistics/manager-statistics.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // Dashboard routes
  {
    path: 'dashboard/employee',
    component: EmployeeDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['EMPLOYEE'] }
  },
  {
    path: 'dashboard/manager',
    component: ManagerDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['MANAGER'] }
  },
  {
    path: 'dashboard/direction',
    component: DirectorDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['DIRECTION_GENERAL'] }
  },
  {
    path: 'dashboard/admin',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['HEAD_OF_SERVICE'] }
  },

  // Matrix routes
  {
    path: 'matrix',
    component: MatrixListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'matrix/create',
    component: MatrixFormComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'matrix/edit/:id',
    component: MatrixFormComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'matrix/:id',
    component: MatrixDetailComponent,
    canActivate: [AuthGuard]
  },

  // Holiday Period routes
  {
    path: 'holiday-periods',
    component: HolidayPeriodListComponent,
    canActivate: [AuthGuard]
  },

  // Holiday Planning routes
  {
    path: 'holiday-planning',
    component: HolidayPlanningListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'holiday-planning/create',
    component: HolidayPlanningFormComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'holiday-planning/:id',
    component: HolidayPlanningFormComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'statistics',
    component: ManagerStatisticsComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['MANAGER', 'HOS', 'DG'] }
  } ,
  {
    path: 'statistics',
    component: ManagerStatisticsComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['MANAGER', 'HOS', 'DG'] }
  } ,




  // Route par défaut - redirige vers login
  { path: '**', redirectTo: 'login' }
];*/

import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { EmployeeDashboardComponent } from './dashboard/employee-dashboard/employee-dashboard.component';
import { ManagerDashboardComponent } from './dashboard/manager-dashboard/manager-dashboard.component';
import { DirectorDashboardComponent } from './dashboard/direction-dashboard/direction-dashboard.component';
import { AdminDashboardComponent } from './dashboard/admin-dashboard/admin-dashboard.component';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';

// Imports pour Matrix
import { MatrixListComponent } from './matrix/matrix-list/matrix-list.component';
import { MatrixFormComponent } from './matrix/matrix-form/matrix-form.component';
import { MatrixDetailComponent } from './matrix/matrix-detail/matrix-detail.component';

// Imports pour HolidayPeriod
import { HolidayPeriodListComponent } from './holiday-period/holiday-period-list/holiday-period-list.component';

// Imports pour HolidayPlanning
import { HolidayPlanningListComponent } from './holiday-planning/holiday-planning-list/holiday-planning-list.component';
import { HolidayPlanningFormComponent } from './holiday-planning/holiday-planning-form/holiday-planning-form.component';

// Imports pour Statistics
import { ManagerStatisticsComponent } from './statistics/manager-statistics/manager-statistics.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // Dashboard routes
  {
    path: 'dashboard/employee',
    component: EmployeeDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['EMPLOYEE'] }
  },
  {
    path: 'dashboard/manager',
    component: ManagerDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['MANAGER'] }
  },
  {
    path: 'dashboard/direction',
    component: DirectorDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['DIRECTION_GENERAL'] }
  },
  {
    path: 'dashboard/admin',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['HEAD_OF_SERVICE'] }
  },

  // Matrix routes
  {
    path: 'matrix',
    component: MatrixListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'matrix/create',
    component: MatrixFormComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'matrix/edit/:id',
    component: MatrixFormComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'matrix/:id',
    component: MatrixDetailComponent,
    canActivate: [AuthGuard]
  },

  // Holiday Period routes
  {
    path: 'holiday-periods',
    component: HolidayPeriodListComponent,
    canActivate: [AuthGuard]
  },

  // Holiday Planning routes
  {
    path: 'holiday-planning',
    component: HolidayPlanningListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'holiday-planning/create',
    component: HolidayPlanningFormComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'holiday-planning/:id',
    component: HolidayPlanningFormComponent,
    canActivate: [AuthGuard]
  },

  // Statistics routes - NOUVELLES ROUTES À AJOUTER
  {
    path: 'statistics',
    component: ManagerStatisticsComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL'] }
  },
  {
    path: 'statistics/manager/:managerName',
    component: ManagerStatisticsComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['MANAGER', 'HEAD_OF_SERVICE', 'DIRECTION_GENERAL'] }
  },
  {
    path: 'statistics/global',
    component: ManagerStatisticsComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['HEAD_OF_SERVICE', 'DIRECTION_GENERAL'] }
  },

  // Route par défaut - redirige vers login
  { path: '**', redirectTo: 'login' }
];
