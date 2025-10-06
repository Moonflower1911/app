import { Directive, ElementRef, Input, OnInit, OnDestroy } from '@angular/core';

@Directive({
  selector: '[appAnimatedNumber]',
  standalone: true
})
export class AnimatedNumberDirective implements OnInit, OnDestroy {
  @Input() appAnimatedNumber: number = 0;
  @Input() duration: number = 2000; // Animation duration in milliseconds
  @Input() delay: number = 0; // Delay before animation starts
  
  private animationFrame: number | null = null;
  private startTime: number = 0;
  private startValue: number = 0;
  private endValue: number = 0;

  constructor(private el: ElementRef) {}

  ngOnInit() {
    if (this.appAnimatedNumber !== undefined) {
      this.startAnimation();
    }
  }

  ngOnDestroy() {
    if (this.animationFrame) {
      cancelAnimationFrame(this.animationFrame);
    }
  }

  private startAnimation() {
    setTimeout(() => {
      this.startTime = Date.now();
      this.startValue = 0;
      this.endValue = this.appAnimatedNumber;
      this.animate();
    }, this.delay);
  }

  private animate() {
    const currentTime = Date.now();
    const elapsed = currentTime - this.startTime;
    const progress = Math.min(elapsed / this.duration, 1);

    // Easing function for smooth animation
    const easeOutQuart = 1 - Math.pow(1 - progress, 4);
    
    const currentValue = this.startValue + (this.endValue - this.startValue) * easeOutQuart;
    
    // Format the number based on its type
    let displayValue: string;
    if (this.isCurrency()) {
      displayValue = this.formatCurrency(currentValue);
    } else {
      displayValue = Math.round(currentValue).toString();
    }
    
    this.el.nativeElement.textContent = displayValue;

    if (progress < 1) {
      this.animationFrame = requestAnimationFrame(() => this.animate());
    }
  }

  private isCurrency(): boolean {
    const text = this.el.nativeElement.textContent || '';
    return text.includes('$') || text.includes('€') || text.includes('£');
  }

  private formatCurrency(value: number): string {
    // Check if the original text has a currency symbol
    const originalText = this.el.nativeElement.textContent || '';
    if (originalText.includes('$')) {
      return `$${value.toLocaleString('en-US', { minimumFractionDigits: 0, maximumFractionDigits: 0 })}`;
    } else if (originalText.includes('€')) {
      return `€${value.toLocaleString('en-US', { minimumFractionDigits: 0, maximumFractionDigits: 0 })}`;
    } else if (originalText.includes('£')) {
      return `£${value.toLocaleString('en-US', { minimumFractionDigits: 0, maximumFractionDigits: 0 })}`;
    }
    return value.toLocaleString('en-US', { minimumFractionDigits: 0, maximumFractionDigits: 0 });
  }
}
