package com.smsmode.gateway.exception;

import com.smsmode.gateway.exception.enumeration.AuthorizationForbiddenExceptionTitleEnum;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown to indicate that an authorization attempt is forbidden. Extends {@link
 * AbstractBaseException} and provides a standardized structure for forbidden authorization
 * exceptions.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 30 Oct 2024
 */
public class AuthorizationForbiddenException extends AbstractBaseException {
  /**
   * Constructs an instance of {@code AuthorizationForbiddenException} with the specified title and
   * message.
   *
   * @param title The title or type of the exception, represented by an {@link
   *     AuthorizationForbiddenExceptionTitleEnum}.
   * @param message The detail message (which is saved for later retrieval by the {@link
   *     #getMessage()} method).
   */
  public AuthorizationForbiddenException(
      AuthorizationForbiddenExceptionTitleEnum title, String message) {
    super(title, HttpStatus.FORBIDDEN, message);
  }
}
