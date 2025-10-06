import {Component, OnDestroy} from '@angular/core';
import {ColComponent, RowComponent} from '@coreui/angular';
import {InclusionListComponent} from './inclusion-list/inclusion-list.component';
import {CancellationPolicyComponent} from './cancellation-policy/cancellation-policy.component';
import {combineLatest, Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {RatePlanApiService} from '../../../services/rate-plan-api.service';
import {RestrictionsComponent} from './restrictions/restrictions.component';

@Component({
  selector: 'app-rate-settings-tab',
  standalone: true,
  imports: [
    ColComponent,
    RowComponent,
    InclusionListComponent,
    CancellationPolicyComponent,
    RestrictionsComponent
  ],
  templateUrl: './rate-settings-tab.component.html',
  styleUrl: './rate-settings-tab.component.scss'
})
export class RateSettingsTabComponent implements OnDestroy {

  private component = '[RateSettingsTabComponent]: ';
  ratePlanId!: string;
  ratePlan: any;
  private subscriptions: Subscription [] = [];

  constructor(private activatedRoute: ActivatedRoute, private ratePlanApiService: RatePlanApiService) {
    this.subscriptions.push(
      combineLatest(
        this.activatedRoute.pathFromRoot.map(route => route.paramMap)
      ).subscribe(paramMaps => {
        const ratePlanId = paramMaps
          .map(paramMap => paramMap.get('ratePlanId'))
          .find(id => id !== null);
        if (ratePlanId) {
          this.ratePlanId = ratePlanId;
          this.retrieveRatePlan();
        }
      })
    );
  }

  private retrieveRatePlan() {
    this.subscriptions.push(this.ratePlanApiService.getById(this.ratePlanId).subscribe({
      next: (data)=>{
        console.info(this.component.concat('Rate plan retrieved successfully. API response is:'), data);
        this.ratePlan = data;
      },
      error: (err)=>{
        console.error(this.component.concat('An error occurred when retrieving rate plan by Id. API error is:'), err);
      }
    }))
  }

  ngOnDestroy(): void {
    console.log(this.component.concat('Unsubscribing from all subscriptions ...'))
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }



}
