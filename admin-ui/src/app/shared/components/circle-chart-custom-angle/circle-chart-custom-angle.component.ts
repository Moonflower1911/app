import { Component, ViewChild } from "@angular/core";

import {
  ApexNonAxisChartSeries,
  ApexPlotOptions,
  ApexChart,
  ApexLegend,
  ApexResponsive,
  ChartComponent
} from "ng-apexcharts";
export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  labels: string[];
  colors: string[];
  legend: ApexLegend;
  plotOptions: ApexPlotOptions;
  responsive: ApexResponsive[];
};

@Component({
  selector: 'app-circle-chart-custom-angle',
  imports: [
    ChartComponent
  ],
  templateUrl: './circle-chart-custom-angle.component.html',
  styleUrl: './circle-chart-custom-angle.component.scss'
})

export class CircleChartCustomAngle {
  @ViewChild("chart") chart!: ChartComponent;
  public chartOptions: ChartOptions;
  constructor() {
    this.chartOptions = {
      series: [76, 67, 61, 90],
      chart: {
        height: 200,
        type: "radialBar"
      },
      plotOptions: {
        radialBar: {
          offsetY: 0,
          startAngle: 0,
          endAngle: 270,
          hollow: {
            margin: 5,
            size: "30%",
            background: "transparent",
            image: undefined
          },
          dataLabels: {
            name: {
              show: false
            },
            value: {
              show: false
            }
          }
        }
      },
      colors: ["#1ab7ea", "#0084ff", "#39539E", "#0077B5"],
      labels: ["Single Room", "Double Room", "Deluxe Room", "Premium Room"],
      legend: {
        show: true,
        floating: false,
        fontSize: "10px",
        position: "bottom",
        offsetX: 0,
        offsetY: 0,
        labels: {
          useSeriesColors: true
        },
        formatter: function(seriesName, opts) {
          return seriesName + ":  " + opts.w.globals.series[opts.seriesIndex];
        },
        itemMargin: {
          horizontal: 3
        }
      },
      responsive: [
        {
          breakpoint: 480,
          options: {
            legend: {
              show: false
            }
          }
        }
      ]
    };
  }
}
