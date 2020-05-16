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

    public static final class NotFoundException extends RuntimeException {

        public NotFoundException() {
        }

        public NotFoundException(String message) {
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

    public static final class WrongQuizType extends RuntimeException {

        public WrongQuizType() {
        }

        public WrongQuizType(String message) {
            super(message);
        }
    }
}
