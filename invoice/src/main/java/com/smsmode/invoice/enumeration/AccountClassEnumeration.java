package com.smsmode.invoice.enumeration;

public enum AccountClassEnumeration {
    REVENUE,
    PAYMENT,
    TAX,
    PACKAGE;

    /*PAID_OUT,
    INTERNAL_TRANSFER*/
    public String toLowerCaseName() {
        return this.name().toLowerCase();
    }
}
