/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.pricing.exception.enumeration;

/**
 * Enum representing titles for resource not found exceptions. Each enum constant should provide a
 * unique code for identifying the exception type.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Oct 2024
 */
public enum ResourceNotFoundExceptionTitleEnum implements BaseExceptionEnum {


    DEFAULT_RATE_NOT_FOUND("PRC_RNF_ERR_1"),
    RATE_PLAN_NOT_FOUND("PRC_RNF_ERR_2"),
    RATE_TABLE_NOT_FOUND("PRC_RNF_ERR_3"),
    FEE_NOT_FOUND("FEE_RNF_ERR_4"),
    GUEST_RATE_STRATEGY_NOT_FOUND("RTG_RNF_ERR_5"),
    VAT_RULE_NOT_FOUND("VAT_RULE_RNF_ERR_6"),
    CHARGE_NOT_FOUND("CHARGE_RNF_ERR_7"),
    INCLUSION_NOT_FOUND("RTE_RNF_ERR_8"),
    UNIT_REF_NOT_FOUND("RTE_RNF_ERR_9"),
    RATE_PLAN_UNIT_NOT_FOUND("RTE_RNF_ERR_10"),
    CANCELLATION_POLICY_NOT_FOUND("RTE_RNF_ERR_11"),
    PROPERTY_SETTINGS_NOT_FOUND("RTE_RNF_ERR_12");


    private final String code;

    /**
     * Constructs a ResourceNotFoundExceptionTitleEnum with the specified code.
     *
     * @param code A string code identifying the exception type.
     */
    ResourceNotFoundExceptionTitleEnum(String code) {
        this.code = code;
    }

    /**
     * {@inheritDoc}
     *
     * @return A string code identifying the exception type.
     */
    @Override
    public String getCode() {
        return code;
    }
}
