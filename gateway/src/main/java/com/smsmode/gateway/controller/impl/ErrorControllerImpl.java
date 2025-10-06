/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.gateway.controller.impl;

import com.smsmode.gateway.controller.ErrorController;
import com.smsmode.gateway.exception.AbstractBaseException;
import com.smsmode.gateway.exception.AuthorizationForbiddenException;
import com.smsmode.gateway.resource.error.ErrorDetailsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import java.time.Instant;

/**
 * Controller advice class for handling exceptions thrown within the application. This class is
 * annotated with {@link RestControllerAdvice} and implements the {@link ErrorController} interface,
 * providing centralized exception handling.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 *     <p>Created 17 Mar 2025
 */
@RestControllerAdvice
public class ErrorControllerImpl implements ErrorController {
  @Override
  @ExceptionHandler({AuthorizationForbiddenException.class})
  public ResponseEntity<ErrorDetailsResource> handleGenericExceptions(
      AbstractBaseException e, ServerWebExchange exchange) {
    ErrorDetailsResource errorDetailResource = new ErrorDetailsResource();
    errorDetailResource.setTimestamp(Instant.now().toEpochMilli());
    errorDetailResource.setTitle(e.getTitle().toString());
    errorDetailResource.setCode(e.getTitle().getCode());
    errorDetailResource.setDeveloperMessage(e.getClass().getName());
    errorDetailResource.setStatus(e.getStatus().value());
    errorDetailResource.setDetail(e.getMessage());
    return new ResponseEntity<>(errorDetailResource, e.getStatus());
  }
}
