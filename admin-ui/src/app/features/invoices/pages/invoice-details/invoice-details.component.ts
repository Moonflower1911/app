import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { InvoiceApiService } from '../../services/invoice-api.service';
import { Subscription } from 'rxjs';
import { UtilsService } from '../../../../shared/services/utils.service';
import { TranslatePipe } from '@ngx-translate/core';
import {
  RowComponent,
  ColComponent,
  TableDirective,
  ButtonDirective,
  SpinnerComponent, ButtonGroupComponent, CardComponent, CardBodyComponent, CardHeaderComponent
} from '@coreui/angular';
import { PageTitleComponent } from '../../../../shared/components/page-title/page-title.component';
import {cilCloudDownload, cilSave} from '@coreui/icons';
import {InvoiceItemGetModel} from '../../models/get/invoice-get.model';
import {CurrencyPipe, DatePipe, NgClass} from '@angular/common';
import {IconDirective} from '@coreui/icons-angular';
import {PartyItemGetModel} from '../../models/get/party-item-get.model';
import {GuestApiService} from '../../services/guest-api.service';
import {PropertyGetModel} from "../../models/get/property-get.model";
import {PropertyApiService} from "../../services/property-api.service";
import {BadgeComponent} from "../../../../shared/components/badge/badge.component";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-invoice-details',
  standalone: true,
  imports: [
    TranslatePipe,
    RowComponent,
    SpinnerComponent,
    ColComponent,
    NgClass,
    TableDirective,
    IconDirective,
    ButtonDirective,
    ButtonGroupComponent,
    PageTitleComponent,
    DatePipe,
    CardComponent,
    BadgeComponent,
    CardBodyComponent,
    CardHeaderComponent,
    FormsModule,
    CurrencyPipe
  ],
  templateUrl: './invoice-details.component.html',
  styleUrls: ['./invoice-details.component.scss']
})
export class InvoiceDetailsComponent implements OnInit, OnDestroy {
  protected readonly UtilsService = UtilsService;
  icons = { cilCloudDownload, cilSave};

  invoiceId!: string;
  invoice!: InvoiceItemGetModel;
  guestInfo?: PartyItemGetModel;
  property?: PropertyGetModel;

  isLoading = true;
  firstCallDone = false;
  error: string | null = null;

  subtotalExclTax = 0;
  taxAmount = 0;
  afterDiscountExclTax = 0;
  totalInclTax = 0;
  discountPercent = 0;
  invoiceNotes = '';


  private subscriptions: Subscription[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private invoiceApi: InvoiceApiService,
    private guestApi: GuestApiService,
    private propertyApi: PropertyApiService
  ) {}

  ngOnInit(): void {
    this.subscriptions.push(
      this.route.paramMap.subscribe((params) => {
        const id = params.get('id');
        if (!id) {
          this.error = 'Invalid invoice id';
          this.isLoading = false;
          this.firstCallDone = true;
          return;
        }
        this.invoiceId = id;
        this.fetchInvoice();
      })
    );
  }

  discountValue = 0;

  private calculateSummary(): void {
    if (!this.invoice) {
      this.subtotalExclTax = 0;
      this.taxAmount = 0;
      this.discountValue = 0;
      this.afterDiscountExclTax = 0;
      this.totalInclTax = 0;
      return;
    }

    this.subtotalExclTax = (this.invoice.totalAmount ?? 0) - (this.invoice.taxAmount ?? 0);
    this.taxAmount = this.invoice.taxAmount ?? 0;

    const discount = this.discountPercent ?? 0;
    this.discountValue = this.subtotalExclTax * discount / 100;

    this.afterDiscountExclTax = this.subtotalExclTax - this.discountValue;
    this.totalInclTax = this.afterDiscountExclTax + this.taxAmount;
  }

  recalculateTotals(): void {
    this.calculateSummary();
  }

  fetchInvoice(): void {
    this.isLoading = true;
    this.error = null;

    this.subscriptions.push(
      this.invoiceApi.getInvoiceById(this.invoiceId).subscribe({
        next: (inv) => {
          this.invoice = inv;
          this.firstCallDone = true;
          this.isLoading = false;

          this.calculateSummary();
          this.fetchProperty();

          if (inv.clientId) {
            console.log(inv.clientId)
            this.fetchGuestInfo(inv.clientId);
          }
        },
        error: (err) => {
          console.warn('Failed to load invoice', err);
          this.error = 'Failed to load invoice';
          this.firstCallDone = true;
          this.isLoading = false;
        }
      })
    );
  }

  downloadInvoice(): void {
  }

  fetchGuestInfo(clientId: string): void {
    this.subscriptions.push(
      this.guestApi.getPartyById(clientId).subscribe({
        next: (guest) => {
          this.guestInfo = guest;
        },
        error: (err) => {
          console.warn('Failed to load guest info', err);
        }
      })
    );
  }

  fetchProperty(): void {
    this.subscriptions.push(
        this.propertyApi.getCurrentProperty().subscribe({
          next: (prop) => {
            this.property = prop;
          },
          error: (err) => {
            console.warn('Failed to load property', err);
          }
        })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }
}
