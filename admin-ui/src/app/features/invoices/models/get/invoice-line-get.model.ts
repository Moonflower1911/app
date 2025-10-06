export interface InvoiceLineGetModel {
  id: string;
  description: string;
  unitPrice: number;
  taxPercentage: number;
  quantity: number;
  totalAmount: number;
  taxAmount: number;
}
