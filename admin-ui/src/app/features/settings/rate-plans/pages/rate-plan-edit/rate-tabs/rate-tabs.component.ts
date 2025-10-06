import {Component, OnDestroy} from '@angular/core';
import {ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {PageTitleComponent} from '../../../../../../shared/components/page-title/page-title.component';
import {TranslatePipe} from '@ngx-translate/core';
import {CardBodyComponent, CardComponent, NavComponent, NavItemComponent, NavLinkDirective} from '@coreui/angular';
import {Subscription} from 'rxjs';
import {RatePlanApiService} from '../../../services/rate-plan-api.service';
import {TitleCasePipe} from '@angular/common';

@Component({
  selector: 'app-rate-edit-page',
  standalone: true,
  imports: [
    PageTitleComponent,
    TranslatePipe,
    NavComponent,
    NavItemComponent,
    NavLinkDirective,
    RouterLink,
    RouterLinkActive,
    RouterOutlet,
    TitleCasePipe,
    CardComponent,
    CardBodyComponent
  ],
  templateUrl: './rate-tabs.component.html',
  styleUrl: './rate-tabs.component.scss'
})
export class RateTabsComponent implements OnDestroy {
  private component = '[RateTabsComponent]: ';
  ratePlan!: any;
  private ratePlanId!: string;

  private readonly subscriptions: Subscription[] = [];

  constructor(private readonly activatedRoute: ActivatedRoute,
              private readonly ratePlanApiService: RatePlanApiService) {
    this.subscriptions.push(this.activatedRoute.paramMap.subscribe(value => {
      this.ratePlanId = value.get('ratePlanId') as string;
      this.retrieveRatePlan();
    }));
  }

  private retrieveRatePlan() {
    this.subscriptions.push(this.ratePlanApiService.getById(this.ratePlanId).subscribe({
      next: (data) => {
        console.info(this.component.concat('Rate plan retrieved successfully. API response is:'), data);
        this.ratePlan = data;
      },
      error: (err) => {
        console.error(this.component.concat('An error occurred when retrieving rate plan by Id:'), this.ratePlanId, '. Error API is:', err);
        //TODO: redirect to listing page with an alert message
      }
    }))
  }

  ngOnDestroy(): void {
    console.debug(this.component.concat('Unsubscribing all subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }


}
