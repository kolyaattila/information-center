package exception;

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

  public static final class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException() {
    }

    public ServiceUnavailableException(String message) {
      super(message);
    }
  }
}
