import { Component, ViewChild } from "@angular/core";
import {ApexLegend, ApexPlotOptions, ChartComponent} from "ng-apexcharts";

import {
  ApexNonAxisChartSeries,
  ApexResponsive,
  ApexChart
} from "ng-apexcharts";

export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  responsive: ApexResponsive[];
  plotOptions: ApexPlotOptions;
  labels: any;
  colors: string[];
  legend: ApexLegend
};
@Component({
  selector: 'app-pie-chart',
  standalone: true,
  imports: [
    ChartComponent
  ],
  templateUrl: './pie-chart.component.html',
  styleUrl: './pie-chart.component.scss'
})
export class PieChartComponent {
  @ViewChild("chart") chart!: ChartComponent;
  public chartOptions: ChartOptions;
  constructor() {
    this.chartOptions = {
      series: [25, 35, 20, 15, 5],
      chart: {
  type: "donut",
  height: 220
      },
      labels: ["USA", "UK", "France", "Germany", "Others"],
      plotOptions: {
          pie:{
            startAngle: -90,
            endAngle: 90
          }
      },
      colors: ["#589bff", "#af6ded", "#28d785", "#f171b1", "#f85050"],
      legend: {
        show: true,
        floating: false,
        fontSize: "9px",
        position: "bottom",
        offsetX: 0,
        offsetY: 0,
        labels: {
          useSeriesColors: true
        },
        formatter: function(seriesName, opts) {
          return seriesName + ":  " + opts.w.globals.series[opts.seriesIndex] + "%";
        },
        itemMargin: {
          horizontal: 2,
          vertical: 1
        }
      },
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              width: 220
            },
            legend: {
              position: "bottom"
            }
          }
        }
      ]
    };
  }
}
