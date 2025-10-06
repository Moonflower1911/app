import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnimatedNumberDirective } from '../../directives/animated-number.directive';

export interface RevenueMetricData {
  title: string;
  value: string;
  changePercentage: string;
  isPositive: boolean;
  iconClass?: string;
  backgroundColor?: string;
}

@Component({
  selector: 'app-revenue-metrics',
  standalone: true,
  imports: [CommonModule, AnimatedNumberDirective],
  templateUrl: './revenue-metrics.component.html',
  styleUrl: './revenue-metrics.component.scss'
})
export class RevenueMetricsComponent {
  @Input() metrics: RevenueMetricData[] = [
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

  /**
   * Extract numeric value from metric value string
   */
  getNumericValue(value: string): number {
    if (!value) return 0;
    
    // Remove currency symbols and commas, then parse
    const numericString = value.replace(/[$,€£]/g, '').replace(/,/g, '');
    const number = parseFloat(numericString);
    
    return isNaN(number) ? 0 : number;
  }

  /**
   * Get delay for staggered animation
   */
  getDelay(index: number): number {
    return index * 200; // 200ms delay between each metric
  }
}
