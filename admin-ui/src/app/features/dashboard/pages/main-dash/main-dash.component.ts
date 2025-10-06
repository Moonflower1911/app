import { Component, ViewChild, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import {PageTitleComponent} from '../../../../shared/components/page-title/page-title.component';
import {TranslatePipe} from '@ngx-translate/core';
import {
  WidgetContainerComponent,
  WidgetData
} from '../../../../shared/components/widget-container/widget-container.component';
import {
  RevenueMetricsComponent,
  RevenueMetricData
} from '../../../../shared/components/revenue-metrics/revenue-metrics.component';
import {ColComponent, RowComponent, CardComponent, CardHeaderComponent, CardBodyComponent} from '@coreui/angular';
import { PieChartComponent } from '../../../../shared/components/pie-chart/pie-chart.component';
import {CircleChartCustomAngle} from '../../../../shared/components/circle-chart-custom-angle/circle-chart-custom-angle.component';
import {
  ChartComponent,
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexDataLabels,
  ApexTitleSubtitle,
  ApexStroke,
  ApexGrid,
  NgApexchartsModule,
  ApexNonAxisChartSeries,
  ApexPlotOptions
} from "ng-apexcharts";
import { KpiServiceService } from '../../service/kpi-service.service';
import { DashboardKPI } from '../../models/kpi.model';
import { Subject, takeUntil } from 'rxjs';
import { AnimatedNumberDirective } from '../../../../shared/directives/animated-number.directive';
import {CardWidgetComponent} from '../../components/card-widget/card-widget.component';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  dataLabels: ApexDataLabels;
  grid: ApexGrid;
  stroke: ApexStroke;
  title: ApexTitleSubtitle;
};

export type RadialChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  labels: string[];
  plotOptions: ApexPlotOptions;
  colors: string[]
};


@Component({
  selector: 'app-main-dash',
  imports: [
    CommonModule,
    PageTitleComponent,
    TranslatePipe,
    WidgetContainerComponent,
    RevenueMetricsComponent,
    RowComponent,
    ColComponent,
    NgApexchartsModule,
    CircleChartCustomAngle,
    CardComponent,
    CardHeaderComponent,
    CardBodyComponent,
    PieChartComponent,
    AnimatedNumberDirective,
    CardWidgetComponent,
  ],
  templateUrl: './main-dash.component.html',
  styleUrl: './main-dash.component.scss'
})
export class MainDashComponent implements OnInit, OnDestroy {
  @ViewChild("chart") chart!: ChartComponent;
  public chartOptions!: Partial<ChartOptions>;
  public radialChartOptions!: RadialChartOptions;

  private destroy$ = new Subject<void>();
  dashboardKPIs: DashboardKPI | null = null;
  isLoading = true;

  constructor(private kpiService: KpiServiceService) {
    this.initializeChartOptions();
  }

  ngOnInit(): void {
    this.loadDashboardData();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  public loadDashboardData(): void {
    this.isLoading = true;

    this.kpiService.getDashboardKPIs()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data) => {
          // Transform the individual endpoint responses to dashboard format
          this.dashboardKPIs = this.transformEndpointResponses(data);
          this.updateDashboardWidgets();
        },
        error: (error) => {
          console.error('Error loading dashboard data:', error);
        },
        complete: () => {
          this.isLoading = false;
        }
      });
  }

  private transformEndpointResponses(responses: any): DashboardKPI {
    return {
      checkins: {
        total: responses.checkins?.totalCountCheckIn || 0,
        current: responses.checkins?.countCheckIn || 0,
        percentage: responses.checkins?.totalCountCheckIn > 0 ?
          (responses.checkins.countCheckIn / responses.checkins.totalCountCheckIn) * 100 : 0
      },
      checkouts: {
        total: responses.checkouts?.totalCountCheckOut || 0,
        current: responses.checkouts?.countCheckOut || 0,
        percentage: responses.checkouts?.totalCountCheckOut > 0 ?
          (responses.checkouts.countCheckOut / responses.checkouts.totalCountCheckOut) * 100 : 0
      },
      inHouse: {
        total: responses.inHouse?.inHouse || 0,
        percentage: 100
      },
      booking: {
        total: responses.booking?.booking || 0,
        percentage: 100
      }
    };
  }

  private updateDashboardWidgets(): void {
    if (!this.dashboardKPIs) return;

    this.dashboardWidgets = [
      {
        iconClass: 'bi bi-box-arrow-in-right',
        title: 'Check-ins',
        subtitle: `${this.dashboardKPIs.checkins.total || 0} / ${this.dashboardKPIs.checkins.current || 0}`,
        iconColor: '#fff',
        iconBackGroundColor: '#20c997'
      },
      {
        iconClass: 'bi bi-box-arrow-right',
        title: 'Check-outs',
        subtitle: `${this.dashboardKPIs.checkouts.current || 0} / ${this.dashboardKPIs.checkouts.total}`,
        iconColor: '#fff',
        iconBackGroundColor: '#AF6DED'
      },
      {
        iconClass: 'bi bi-calendar-check-fill',
        title: 'Bookings',
        subtitle: this.dashboardKPIs.booking.total.toString(),
        iconColor: '#fff',
        iconBackGroundColor: '#28D785'
      },
      {
        iconClass: 'bi bi-house-fill',
        title: 'In-House',
        subtitle: this.dashboardKPIs.inHouse.total.toString(),
        iconColor: '#fff',
        iconBackGroundColor: '#17a2b8'
      },
    ];
  }

  private initializeChartOptions(): void {
    this.chartOptions = {
      series: [
        {
          name: "Income",
          data: [1200, 1800, 1450, 2100, 1950, 2400, 2150, 2800, 3200]
        }
      ],
      chart: {
        height: 350,
        type: "line",
        zoom: {
          enabled: false
        }
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: "straight"
      },
      title: {
        text: "Monthly Income Trends",
        align: "left"
      },
      grid: {
        row: {
          colors: ["#f3f3f3", "transparent"],
          opacity: 0.5
        }
      },
      xaxis: {
        categories: [
          "Jan",
          "Feb",
          "Mar",
          "Apr",
          "May",
          "Jun",
          "Jul",
          "Aug",
          "Sep"
        ]
      }
    };

    this.radialChartOptions = {
      series: [52],
      chart: {
        height: 180,
        type: "radialBar",
      },
      plotOptions: {
        radialBar: {
          hollow: {
            size: "60%"
          },
          dataLabels: {
            name: {
              show: true,
              fontSize: "12px",
              fontWeight: 600,
              color: "#333"
            },
            value: {
              show: true,
              fontSize: "14px",
              fontWeight: 700,
              color: "#333"
            }
          }
        }
      },
      colors: ["#589bff"],
      labels: ["Total Occupancy"]
    };
  }

  dashboardWidgets: WidgetData[] = [
    {
      iconClass: 'bi bi-box-arrow-in-right',
      title: 'Check-ins',
      subtitle: '11 / 10',
      iconColor: '#fff',
      iconBackGroundColor: '#20c997'
    },
    {
      iconClass: 'bi bi-box-arrow-right',
      title: 'Check-outs',
      subtitle: '4 / 7',
      iconColor: '#fff',
      iconBackGroundColor: '#AF6DED'
    },
    {
      iconClass: 'bi bi-calendar-check-fill',
      title: 'Bookings',
      subtitle: '14',
      iconColor: '#fff',
      iconBackGroundColor: '#28D785'
    },
    {
      iconClass: 'bi bi-house-fill',
      title: 'In-House',
      subtitle: '14',
      iconColor: '#fff',
      iconBackGroundColor: '#17a2b8'
    },
  ];

  revenueMetrics: RevenueMetricData[] = [
    {
      title: 'Total Revenue',
      value: '$1,100',
      changePercentage: '+1.2%',
      isPositive: true,
      iconClass: 'bi bi-currency-dollar',
      backgroundColor: '#10b981'
    },
    {
      title: 'Monthly Growth',
      value: '$850',
      changePercentage: '+8.5%',
      isPositive: true,
      iconClass: 'bi bi-graph-up',
      backgroundColor: '#3b82f6'
    },
    {
      title: 'Daily Average',
      value: '$45',
      changePercentage: '-2.1%',
      isPositive: false,
      iconClass: 'bi bi-calendar-day',
      backgroundColor: '#8b5cf6'
    },
    {
      title: 'Bookings',
      value: '23',
      changePercentage: '+12.3%',
      isPositive: true,
      iconClass: 'bi bi-house-door',
      backgroundColor: '#f59e0b'
    }
  ];

}
