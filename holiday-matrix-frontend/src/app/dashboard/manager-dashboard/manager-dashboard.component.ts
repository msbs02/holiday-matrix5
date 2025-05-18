import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DashboardService, ManagerDashboardData } from '../dashboard.service';
import { AuthService } from '../../services/auth.service';
import { HolidayPlanningService } from '../../services/holiday-planning.service';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-manager-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './manager-dashboard.component.html',
  styleUrls: ['./manager-dashboard.component.css']
})
export class ManagerDashboardComponent implements OnInit {
  dashboardData?: ManagerDashboardData;
  pendingRequests: any[] = [];
  teamMembers: any[] = [];
  errorMessage = '';
  loading = true;

  constructor(
    private dashboardService: DashboardService,
    private authService: AuthService,
    private holidayPlanningService: HolidayPlanningService,
    private employeeService: EmployeeService
  ) {}

  ngOnInit(): void {
    this.loadDashboardData();
    this.loadPendingRequests();
    this.loadTeamMembers();
  }

  loadDashboardData(): void {
    this.dashboardService.getManagerDashboardData().subscribe({
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

  loadPendingRequests(): void {
    // Charger les demandes de congés en attente
    this.holidayPlanningService.getAll().subscribe({
      next: plannings => {
        this.pendingRequests = plannings.filter(p => !p.managerValidated);
      },
      error: err => {
        console.error('Erreur lors du chargement des demandes', err);
      }
    });
  }

  loadTeamMembers(): void {
    const currentUser = this.authService.currentUserValue;
    if (currentUser && currentUser.id) {
      this.employeeService.getByUser(currentUser.id).subscribe({
        next: employee => {
          if (employee && employee.id) {
            this.employeeService.getByManager(employee.id).subscribe({
              next: employees => {
                this.teamMembers = employees;
              },
              error: err => {
                console.error('Erreur lors du chargement des membres de l\'équipe', err);
              }
            });
          }
        },
        error: err => {
          console.error('Erreur lors du chargement des informations de l\'employé', err);
        }
      });
    }
  }

  validateRequest(id: number): void {
    this.holidayPlanningService.validateByManager(id).subscribe({
      next: () => {
        this.pendingRequests = this.pendingRequests.filter(req => req.id !== id);
      },
      error: err => {
        console.error('Erreur lors de la validation', err);
      }
    });
  }
}
