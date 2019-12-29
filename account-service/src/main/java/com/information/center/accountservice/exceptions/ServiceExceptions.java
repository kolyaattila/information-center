package com.information.center.accountservice.exceptions;

public final class ServiceExceptions extends Throwable {

  public static final class InsertFailedException extends RuntimeException {

    public InsertFailedException() {
    }

    public InsertFailedException(String message) {
      super(message);
    }
  }

  public static final class InconsistentDataException extends RuntimeException {

    public InconsistentDataException() {
    }

    public InconsistentDataException(String message) {
      super(message);
    }
  }
}