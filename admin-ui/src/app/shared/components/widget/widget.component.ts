import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-widget',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './widget.component.html',
  styleUrl:'widget.component.scss'
})
export class WidgetComponent {
  @Input({ required: true }) iconClass!: string;
  @Input({ required: true }) title!: string;
  @Input({ required: true }) subtitle!: string;
  @Input() description?: string;
  @Input() iconColor?: string = "#fff";
  @Input() iconBackGroundColor?: string = "#589BFF";

  /**
   * Extract the first number from subtitle (before the slash)
   */
  getFirstNumber(subtitle: string): string {
    if (!subtitle) return '0';
    const parts = subtitle.split(' / ');
    return parts[0];
  }

  /**
   * Extract the second number from subtitle (after the slash)
   */
  getSecondNumber(subtitle: string): string {
    if (!subtitle) return '0';
    const parts = subtitle.split(' / ');
    return parts[1] || '0';
  }

  /**
   * Check if subtitle contains two numbers separated by slash
   */
  hasTwoNumbers(subtitle: string): boolean {
    if (!subtitle) return false;
    return subtitle.includes(' / ');
  }

  /**
   * Determine if the single number should be colored based on widget title
   */
  shouldColorNumber(title: string): boolean {
    // No single number widgets get colored - all stay black
    return false;
  }
}
