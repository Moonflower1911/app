import {AuditGetModel} from '../../../../shared/models/audit-get.model';
import {PaymentMethodEnum} from '../enums/payment-method.enum';


export interface PaymentGetModel {
  id: string;
  amount: number;
  method: PaymentMethodEnum;
  audit: AuditGetModel;
}
