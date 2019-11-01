package com.videoservice.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class MicroserviceException extends RuntimeException {

    private HttpStatus errorCode;

    private String errorDescription;

    private String response;

    public MicroserviceException(HttpStatus errorCode, String errorDescription) {
        super(errorDescription);

        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public MicroserviceException(HttpStatus errorCode, String errorDescription, String response) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.response = response;
    }

    public MicroserviceException(String errorDescription) {
        super(errorDescription);

        this.errorCode = HttpStatus.BAD_REQUEST;
        this.errorDescription = errorDescription;
    }
}
