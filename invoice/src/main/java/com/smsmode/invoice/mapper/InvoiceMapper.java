package com.smsmode.invoice.mapper;

import com.smsmode.invoice.model.InvoiceModel;
import com.smsmode.invoice.model.PaymentModel;
import com.smsmode.invoice.model.base.AbstractBaseModel;
import com.smsmode.invoice.resource.booking.get.BookingGetResource;
import com.smsmode.invoice.resource.common.AuditGetResource;
import com.smsmode.invoice.resource.invoice.get.InvoiceItemGetResource;
import com.smsmode.invoice.resource.payment.PaymentGetResource;
import com.smsmode.invoice.resource.invoice.post.InvoicePdfPostResource;
import com.smsmode.invoice.resource.invoice.post.InvoicePdfPostResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

/**
 * Mapper for Invoice entity and resources.
 *
 * Created 11 Aug 2025
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class InvoiceMapper {

    @Mappings({
            @Mapping(target = "clientId", source = "clientRef.id"),
            @Mapping(target = "clientName", source = "clientRef.name"),
            @Mapping(target = "items", source = "items"),
            @Mapping(target = "payments", source = "payments")
    })
    public abstract InvoiceItemGetResource modelToGetResource(InvoiceModel invoiceModel);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "reference", source = "reference")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "stay", source = "stay")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "parent", source = "parent")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "fees", source = "fees")
    @Mapping(target = "notes", source = "notes")
    @Mapping(target = "contact", source = "contact")
    @Mapping(target = "audit", source = "audit")
    public abstract InvoicePdfPostResource bookingToInvoicePdfPostResource(BookingGetResource booking);


    /*@Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    public abstract InvoiceModel postResourceToModel(InvoicePostResource invoicePostResource);

    public abstract InvoiceModel patchResourceToModel(InvoicePatchResource invoicePatchResource, @MappingTarget InvoiceModel invoiceModel);
    */

    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    @AfterMapping
    public void afterModelToItemGetResource(InvoiceModel invoiceModel,
                                            @MappingTarget InvoiceItemGetResource invoiceItemGetResource) {
        invoiceItemGetResource.setAudit(modelToAuditResource(invoiceModel));

        if (invoiceItemGetResource.getPayments() != null) {
            for (PaymentGetResource paymentRes : invoiceItemGetResource.getPayments()) {
                PaymentModel paymentModel = invoiceModel.getPayments()
                        .stream()
                        .filter(pm -> pm.getId().equals(paymentRes.getId()))
                        .findFirst()
                        .orElse(null);

                if (paymentModel != null) {
                    paymentRes.setAudit(modelToAuditResource(paymentModel));
                }
            }
        }
    }
}
