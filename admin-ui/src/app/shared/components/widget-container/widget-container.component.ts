import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RowComponent, ColComponent } from '@coreui/angular';
import { WidgetComponent } from '../widget/widget.component';
import { RevenueMetricsComponent, RevenueMetricData } from '../revenue-metrics/revenue-metrics.component';

export interface WidgetData {
  iconClass: string;
  title: string;
  subtitle: string;
  description?: string;
  iconColor?: string;
  backgroundColor?: string;
  iconBackGroundColor? : string
}

@Component({
  selector: 'app-widget-container',
  standalone: true,
  imports: [CommonModule, WidgetComponent, RevenueMetricsComponent, RowComponent, ColComponent],
  templateUrl: 'widget-container.component.html',
  styles: []
})
export class WidgetContainerComponent {
  @Input({ required: true }) widgets!: WidgetData[];
  @Input() containerClass: string = ''; // Additional classes for the row
  @Input() showRevenueMetrics: boolean = true; // Control whether to show revenue metrics
  @Input() revenueMetrics?: RevenueMetricData[]; // Optional custom revenue metrics

  get metricsData(): RevenueMetricData[] {
    return this.revenueMetrics || [
      {
        title: 'Total Revenue',
        value: '$1,100',
        changePercentage: '+1.2%',
        isPositive: true,
        iconClass: 'bi bi-currency-dollar',
        backgroundColor: '#10b981'
      },
      {
        title: 'Average Daily Rate',
        value: '$85',
        changePercentage: '+3.4%',
        isPositive: true,
        iconClass: 'bi bi-graph-up',
        backgroundColor: '#3b82f6'
      },
      {
        title: 'Booking Lead Time',
        value: '3 days',
        changePercentage: '-0.8%',
        isPositive: false,
        iconClass: 'bi bi-calendar-alt',
        backgroundColor: '#f59e0b'
      },
      {
        title: 'Avg Length of Stay',
        value: '2.5 days',
        changePercentage: '+2.1%',
        isPositive: true,
        iconClass: 'bi bi-house-door',
        backgroundColor: '#8b5cf6'
      }
    ];
  }

}
