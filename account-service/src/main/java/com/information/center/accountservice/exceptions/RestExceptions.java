package com.information.center.accountservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class RestExceptions extends Throwable {

  @ResponseStatus(value = HttpStatus.CONFLICT)
  public static final class EntityExistsException extends RuntimeException {

    public EntityExistsException() {
      super();
    }

    public EntityExistsException(String message) {
      super(message);
    }
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public static final class BadRequest extends RuntimeException {

    public BadRequest() {
      super();
    }

    public BadRequest(String message) {
      super(message);
    }
  }
}
