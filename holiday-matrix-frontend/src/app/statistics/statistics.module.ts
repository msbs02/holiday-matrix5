import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// @ts-ignore
import { NgChartsModule } from 'ng2-charts';
import { HttpClientModule } from '@angular/common/http';

import { ManagerStatisticsComponent } from './manager-statistics/manager-statistics.component';
import { StatisticsChartComponent } from './statistics-chart/statistics-chart.component';

@NgModule({
  declarations: [

  ],
  imports: [
    CommonModule,
    NgChartsModule,
    HttpClientModule,
    StatisticsChartComponent,
    ManagerStatisticsComponent
  ],
  exports: [
    ManagerStatisticsComponent
  ]
})
export class StatisticsModule { }
