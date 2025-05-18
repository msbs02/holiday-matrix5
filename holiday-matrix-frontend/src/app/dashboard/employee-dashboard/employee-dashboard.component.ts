import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService, EmployeeDashboardData } from '../dashboard.service';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { HolidayPlanningService } from '../../services/holiday-planning.service';

@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee-dashboard.component.html',
  styleUrls: ['./employee-dashboard.component.css']
})
export class EmployeeDashboardComponent implements OnInit {
  dashboardData?: EmployeeDashboardData;
  userHolidays: any[] = [];
  errorMessage = '';
  loading = true;

  constructor(
    private dashboardService: DashboardService,
    private holidayPlanningService: HolidayPlanningService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadDashboardData();
    this.loadUserHolidays();
  }

  loadDashboardData(): void {
    this.dashboardService.getEmployeeDashboardData().subscribe({
      next: data => {
        this.dashboardData = data;
        this.loading = false;
      },
      error: err => {
        this.errorMessage = 'Erreur lors du chargement des données';
        this.loading = false;
        console.error(err);
      }
    });
  }

  loadUserHolidays(): void {
    const currentUser = this.authService.currentUserValue;
    if (currentUser && currentUser.id) {
      this.holidayPlanningService.getByEmployee(currentUser.id).subscribe({
        next: plannings => {
          this.userHolidays = plannings;
        },
        error: err => {
          console.error('Erreur lors du chargement des congés', err);
        }
      });
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
