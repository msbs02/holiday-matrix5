import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService, EmployeeDashboardData } from '../dashboard.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee-dashboard.component.html',
  styleUrls: ['./employee-dashboard.component.css']
})
export class EmployeeDashboardComponent implements OnInit {
  dashboardData?: EmployeeDashboardData;
  errorMessage = '';

  constructor(
    private dashboardService: DashboardService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.dashboardService.getEmployeeDashboardData().subscribe({
      next: data => this.dashboardData = data,
      error: err => this.errorMessage = 'Erreur lors du chargement des données'
    });
  }

  logout(): void {
    // Code de déconnexion (ex. via AuthService)
    this.router.navigate(['/login']);
  }
}
