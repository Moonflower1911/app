package com.smsmode.gateway.exception.enumeration;

/**
 * Interface representing the base contract for exception enums. Implementing enums should provide a
 * unique code for identifying the exception type.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 09 Oct 2024
 */
public interface BaseExceptionEnum {
  /**
   * Gets the unique code representing the exception type.
   *
   * @return A string code identifying the exception type.
   */
  String getCode();
}
