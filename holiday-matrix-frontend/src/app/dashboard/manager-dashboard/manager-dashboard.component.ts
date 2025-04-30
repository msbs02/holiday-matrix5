import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService, ManagerDashboardData } from '../dashboard.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manager-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './manager-dashboard.component.html',
  styleUrls: ['./manager-dashboard.component.css']
})
export class ManagerDashboardComponent implements OnInit {
  dashboardData?: ManagerDashboardData;
  errorMessage = '';

  constructor(
    private dashboardService: DashboardService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.dashboardService.getManagerDashboardData().subscribe({
      next: data => this.dashboardData = data,
      error: err => this.errorMessage = 'Erreur lors du chargement des données'
    });
  }

  logout(): void {
    // Code de déconnexion
    this.router.navigate(['/login']);
  }
}
