import { Component, OnInit } from '@angular/core';
import { ManagerStatistics, StatisticsService } from '../statistics.service';
import {StatisticsChartComponent} from '../statistics-chart/statistics-chart.component';

@Component({
  selector: 'app-manager-statistics',
  templateUrl: './manager-statistics.component.html',
  standalone: true,
  imports: [
    StatisticsChartComponent
  ],
  styleUrls: ['./manager-statistics.component.css']
})
export class ManagerStatisticsComponent implements OnInit {
  managers: string[] = [];
  selectedManager: string | null = null;
  statistics: ManagerStatistics | null = null;
  loading = false;

  constructor(private statisticsService: StatisticsService) { }

  ngOnInit(): void {
    this.loadManagers();
  }

  loadManagers(): void {
    this.loading = true;
    this.statisticsService.getAllManagers().subscribe({
      next: (data) => {
        this.managers = data;
        this.loading = false;
        if (this.managers.length > 0) {
          this.selectManager(this.managers[0]);
        }
      },
      error: (err) => {
        console.error('Erreur lors du chargement des managers', err);
        this.loading = false;
      }
    });
  }

  selectManager(managerId: string): void {
    this.selectedManager = managerId;
    this.loadStatistics(managerId);
  }

  loadStatistics(managerId: string): void {
    this.loading = true;
    this.statisticsService.getManagerStatistics(managerId).subscribe({
      next: (data) => {
        this.statistics = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des statistiques', err);
        this.loading = false;
      }
    });
  }
}
