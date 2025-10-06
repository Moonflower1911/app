/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.exception.enumeration;

/**
 * Enum representing titles for resource not found exceptions. Each enum constant should provide a
 * unique code for identifying the exception type.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Oct 2024
 */
public enum ResourceNotFoundExceptionTitleEnum implements BaseExceptionEnum {

    /**
     * Resource not found exception title: User not found.
     */
    USER_NOT_FOUND("UAA_RNF_ERR_1"),

    /**
     * Resource not found exception title: Account not found.
     */
    ACCOUNT_NOT_FOUND("UAA_RNF_ERR_6"),

    /**
     * Resource not found exception title: Role not found.
     */
    ROLE_NOT_FOUND("UAA_RNF_ERR_2"),

    /**
     * Resource not found exception title: Reset key not found.
     */
    RESET_KEY_NOT_FOUND("UAA_RNF_ERR_3"),

    /*
     * Resource not found exception title: Activation key not found.
     * */
    ACTIVATION_KEY_NOT_FOUND("UUA_RNF_ERR_4"),

    RECENT_DEVICE_NOT_FOUND("UAA_RNF_ERR_5");


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
