
package com.smsmode.gateway.exception.enumeration;

/**
 * Enum representing titles for authorization forbidden exceptions. Each enum constant should
 * provide a unique code for identifying the exception type.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 09 Oct 2024
 */
public enum AuthorizationForbiddenExceptionTitleEnum implements BaseExceptionEnum {

  /** Token not valid exception title. */
  TOKEN_NOT_VALID("GTW_AUTH_FORB_ERR_1");

  private final String code;

  /**
   * Constructs an AuthorizationForbiddenExceptionTitleEnum with the specified code.
   *
   * @param code A string code identifying the exception type.
   */
  AuthorizationForbiddenExceptionTitleEnum(String code) {
    this.code = code;
  }

  /** {@inheritDoc} */
  @Override
  public String getCode() {
    return code;
  }
}
