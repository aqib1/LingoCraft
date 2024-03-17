package com.lingo.craft.domain.base;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BaseException(HttpStatus httpStatus, String message,
                         Throwable throwable) {
        super(message, throwable);

        this.httpStatus = httpStatus;
    }

    public BaseException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

}
