import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { WeeklyStatistics } from '../statistics.service';
import {BaseChartDirective} from 'ng2-charts';

@Component({
  selector: 'app-statistics-chart',
  templateUrl: './statistics-chart.component.html',
  standalone: true,
  imports: [
    BaseChartDirective
  ],
  styleUrls: ['./statistics-chart.component.css']
})
export class StatisticsChartComponent implements OnChanges {
  @Input() data: WeeklyStatistics[] = [];
  @Input() managerName: string = '';

  chartData: any;
  chartOptions: any;

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['data']) {
      this.updateChart();
    }
  }

  updateChart(): void {
    const labels = this.data.map(item => `Semaine ${item.weekNumber}`);
    const values = this.data.map(item => item.plannedHolidays);

    this.chartData = {
      labels: labels,
      datasets: [{
        label: 'Congés planifiés',
        data: values,
        borderColor: '#1e5eb8',
        tension: 0.1
      }]
    };

    this.chartOptions = {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            stepSize: 1,
            precision: 0
          }
        }
      }
    };
  }
}
