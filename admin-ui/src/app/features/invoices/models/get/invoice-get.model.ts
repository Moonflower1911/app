import { AuditGetModel } from '../../../../shared/models/audit-get.model';
import {InvoiceLineGetModel} from './invoice-line-get.model';
import {PaymentGetModel} from './payment-get.model';
import {InvoiceStatusEnum} from '../enums/status-enum';


export interface InvoiceItemGetModel {
  id: string;
  reference: string;
  date: string;
  proforma: boolean;
  status: InvoiceStatusEnum;
  clientId: string;
  clientName: string;
  totalAmount: number;
  taxAmount: number;
  items: InvoiceLineGetModel[];
  payments: PaymentGetModel[];
  audit: AuditGetModel;
}
