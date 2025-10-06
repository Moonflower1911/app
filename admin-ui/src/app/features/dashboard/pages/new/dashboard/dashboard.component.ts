import { Component } from '@angular/core';
import { IconDirective } from '@coreui/icons-angular';
import { ReactiveFormsModule } from '@angular/forms';
import {
  cilBookmark,
  cilChevronBottom,
  cilChevronTop,
  cilHome,
  cilTransfer,
  cilCheckCircle,
  cilUser,
  cilCalendar,
  cilCreditCard,
  cilDollar,
  cilArrowTop,
  cilClock,
  cilStar,
  cilSpeedometer,
  cilWarning,
  cilTask,
  cilBuilding
} from '@coreui/icons';
import { NgApexchartsModule } from 'ng-apexcharts';
import { CommonModule } from '@angular/common';
import {
  RowComponent,
  ColComponent,
  WidgetStatFComponent,
  TemplateIdDirective,
  CardComponent,
  CardBodyComponent,
  CardHeaderComponent,
  ProgressBarComponent,
  ProgressComponent
} from '@coreui/angular';

import {
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexDataLabels,
  ApexTooltip,
  ApexStroke,
  ApexPlotOptions,
  ApexYAxis,
  ApexFill,
  ApexLegend,
  ApexMarkers
} from 'ng-apexcharts';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  yaxis: ApexYAxis | ApexYAxis[];
  dataLabels: ApexDataLabels;
  tooltip: ApexTooltip;
  stroke: ApexStroke;
  plotOptions: ApexPlotOptions;
  fill: ApexFill;
  legend: ApexLegend;
  colors: string[];
  markers: ApexMarkers;
};

interface RecentActivity {
  description: string;
  time: string;
  icon: any;
  iconClass: string;
}

interface BookingSource {
  name: string;
  percentage: number;
  color: string;
}

interface PropertyType {
  name: string;
  count: number;
  occupancy: number;
  color: string;
  icon: any;
}

interface MaintenanceItem {
  label: string;
  description: string;
  count: number;
  icon: any;
  statusClass: string;
  countClass: string;
}

@Component({
  templateUrl: 'dashboard.component.html',
  styleUrls: ['dashboard.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    RowComponent,
    ColComponent,
    IconDirective,
    ReactiveFormsModule,
    WidgetStatFComponent,
    TemplateIdDirective,
    NgApexchartsModule,
    CardComponent,
    CardBodyComponent,
    CardHeaderComponent,
    ProgressBarComponent,
    ProgressComponent
  ]
})
export class DashboardComponent {
  icons = {
    cilTransfer,
    cilChevronBottom,
    cilChevronTop,
    cilHome,
    cilBookmark,
    cilCheckCircle,
    cilUser,
    cilCalendar,
    cilCreditCard,
    cilDollar,
    cilArrowTop,
    cilClock,
    cilStar,
    cilSpeedometer,
    cilWarning,
    cilTask,
    cilBuilding
  };

  public chartOptions: Partial<ChartOptions>;

  // Property revenue data in MAD (Moroccan Dirham)
  public monthlyIncome = [52000, 58000, 53500, 40000, 46000, 42500];
  public totalIncome = this.monthlyIncome.reduce((sum, income) => sum + income, 0);
  public averageIncome = Math.round(this.totalIncome / this.monthlyIncome.length);
  public highestMonth = Math.max(...this.monthlyIncome);

  // Additional KPIs
  public totalRevenue = 125000;
  public revenueGrowth = 12.5;
  public completedBookings = 48;
  public avgStayDuration = 4.5;
  public customerRating = 4.7;

  public donutChart1Options: any;
  public donutChart2Options: any;
  public bookingSourcesChart: any;

  // Recent Activities Data
  public recentActivities: RecentActivity[] = [
    {
      description: 'New booking - Apartment 301, Casablanca',
      time: '15 min ago',
      icon: cilCalendar,
      iconClass: 'icon-booking'
    },
    {
      description: 'Check-in - Villa Marrakech',
      time: '1 hour ago',
      icon: cilCheckCircle,
      iconClass: 'icon-checkin'
    },
    {
      description: 'New tenant registered - Ahmed El Mansouri',
      time: '2 hours ago',
      icon: cilUser,
      iconClass: 'icon-user'
    },
    {
      description: 'Payment received - 15,000 DH',
      time: '3 hours ago',
      icon: cilCreditCard,
      iconClass: 'icon-payment'
    },
    {
      description: 'Maintenance completed - Rabat Office',
      time: '4 hours ago',
      icon: cilTask,
      iconClass: 'icon-maintenance'
    }
  ];

  // Booking Sources Data
  public bookingSources: BookingSource[] = [
    { name: 'Direct', percentage: 35, color: '#2E93fA' },
    { name: 'Booking.com', percentage: 28, color: '#66BB6A' },
    { name: 'Airbnb', percentage: 22, color: '#FF6384' },
    { name: 'Expedia', percentage: 10, color: '#FFA726' },
    { name: 'Others', percentage: 5, color: '#9E9E9E' }
  ];

  // Property Types Data
  public propertyTypes: PropertyType[] = [
    { name: 'Apartments', count: 12, occupancy: 85, color: '#2E93fA', icon: cilBuilding },
    { name: 'Villas', count: 6, occupancy: 92, color: '#66BB6A', icon: cilHome },
    { name: 'Studios', count: 4, occupancy: 75, color: '#FFA726', icon: cilSpeedometer },
    { name: 'Offices', count: 3, occupancy: 67, color: '#9C27B0', icon: cilBuilding }
  ];

  // Maintenance Status Data
  public maintenanceStatus: MaintenanceItem[] = [
    {
      label: 'Urgent',
      description: 'Requires immediate attention',
      count: 3,
      icon: cilWarning,
      statusClass: 'status-urgent',
      countClass: 'text-danger'
    },
    {
      label: 'In Progress',
      description: 'Currently being handled',
      count: 5,
      icon: cilTask,
      statusClass: 'status-progress',
      countClass: 'text-warning'
    },
    {
      label: 'Scheduled',
      description: 'Planned for this week',
      count: 8,
      icon: cilCalendar,
      statusClass: 'status-scheduled',
      countClass: 'text-info'
    },
    {
      label: 'Completed',
      description: 'Done this month',
      count: 24,
      icon: cilCheckCircle,
      statusClass: 'status-completed',
      countClass: 'text-success'
    }
  ];

  constructor() {
    this.chartOptions = {
      series: [
        {
          name: "Monthly Income (Bars)",
          type: "column",
          data: this.monthlyIncome
        },
        {
          name: "Income Trend (Line)",
          type: "line",
          data: this.monthlyIncome
        }
      ],
      chart: {
        height: 300,
        type: "line",
        stacked: false,
        toolbar: {
          show: true
        }
      },
      dataLabels: {
        enabled: false
      },
      markers: {
        size: [0, 4],
        strokeWidth: [0, 0],
        colors: ['#2E93fA', '#013557'],
        strokeColors: ['#ffffff'],
        hover: {
          size: 8
        }
      },
      stroke: {
        width: [0, 3],
        curve: 'straight'
      },
      xaxis: {
        categories: [
          "July",
          "August",
          "September",
          "October",
          "November",
          "December"
        ],
        title: {
          text: "Month (2025)"
        }
      },
      yaxis: {
        axisTicks: {
          show: true
        },
        axisBorder: {
          show: true,
          color: "#2E93fA"
        },
        labels: {
          style: {
            colors: "#2E93fA"
          },
          formatter: function (val) {
            return (val / 1000).toFixed(0) + "k DH";
          }
        },
        title: {
          text: "Monthly Income (DH)",
          style: {
            color: "#2E93fA"
          }
        }
      },
      tooltip: {
        shared: true,
        intersect: false,
        y: {
          formatter: function (val) {
            return val.toLocaleString() + " DH";
          }
        }
      },
      legend: {
        horizontalAlign: "center",
        offsetX: 0,
        position: "bottom"
      },
      plotOptions: {
        bar: {
          columnWidth: "60%",
          borderRadius: 4
        }
      },
      fill: {
        opacity: [0.8, 1]
      },
      colors: ["#2E93fA", "#013557"]
    };

    this.donutChart1Options = {
      series: [17, 2, 6],
      chart: {
        type: 'donut',
        height: 150,
        width: 150
      },
      labels: ['Occupied', 'Vacant', 'Unlisted'],
      colors: ['#2E93fA', '#dc3545', '#ffc107'],
      legend: {
        show: false
      },
      dataLabels: {
        enabled: true,
        style: {
          fontSize: '12px',
          fontWeight: 'bold',
          colors: ['#013557']
        },
        background: {
          enabled: true,
          foreColor: '#fff',
          borderWidth: 0
        },
        dropShadow: {
          enabled: false
        },
        formatter: function(val: any, opts: any) {
          return opts.w.config.series[opts.seriesIndex];
        }
      },
      plotOptions: {
        pie: {
          expandOnClick: false,
          dataLabels: {
            offset: 15,
            minAngleToShowLabel: 20
          },
          donut: {
            size: '65%',
            labels: {
              show: true,
              total: {
                show: true,
                label: 'Total',
                fontSize: '12px',
                color: '#666',
                formatter: () => '25'
              },
              value: {
                fontSize: '20px',
                fontWeight: 'bold',
                color: '#242424'
              }
            }
          }
        }
      }
    };

    this.donutChart2Options = {
      series: [70, 30],
      chart: {
        type: 'donut',
        height: 150,
        width: 150
      },
      labels: ['Occupied', 'Available'],
      colors: ['#2E93fA', '#dee2e6'],
      legend: {
        show: false
      },
      dataLabels: {
        enabled: true,
        style: {
          fontSize: '12px',
          fontWeight: 'bold',
          colors: ['#013557']
        },
        background: {
          enabled: true,
          foreColor: '#fff',
          borderWidth: 0
        },
        dropShadow: {
          enabled: false
        },
        formatter: function(val: any, opts: any) {
          return opts.w.config.series[opts.seriesIndex];
        }
      },
      plotOptions: {
        pie: {
          expandOnClick: false,
          dataLabels: {
            offset: 15,
            minAngleToShowLabel: 10
          },
          donut: {
            size: '65%',
            labels: {
              show: true,
              total: {
                show: true,
                label: 'Occupancy Rate',
                fontSize: '10px',
                color: '#666',
                formatter: () => '70%'
              },
              value: {
                fontSize: '20px',
                fontWeight: 'bold',
                color: '#242424'
              }
            }
          }
        }
      }
    };

    // Booking Sources Chart
    this.bookingSourcesChart = {
      series: this.bookingSources.map(s => s.percentage),
      chart: {
        type: 'pie',
        height: 200
      },
      labels: this.bookingSources.map(s => s.name),
      colors: this.bookingSources.map(s => s.color),
      legend: {
        show: false
      },
      dataLabels: {
        enabled: true,
        formatter: function(val: number) {
          return Math.round(val) + '%';
        },
        style: {
          fontSize: '11px',
          fontWeight: 'bold'
        }
      },
      plotOptions: {
        pie: {
          expandOnClick: false
        }
      }
    };
  }
}
