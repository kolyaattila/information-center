package exception;

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

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public static final class EmailSendException extends RuntimeException {

        public EmailSendException() {
            super();
        }

        public EmailSendException(String message) {
            super(message);
        }
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public static final class ServiceUnavailable extends RuntimeException {

        public ServiceUnavailable() {
            super();
        }

        public ServiceUnavailable(String message) {
            super(message);
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static final class NotFoundException extends RuntimeException {

        public NotFoundException() {
        }

        public NotFoundException(String message) {
            super(message);
        }
    }


}
