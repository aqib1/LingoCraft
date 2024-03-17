package com.lingo.craft.domain.user.exception;

import com.lingo.craft.domain.base.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidUserIdException extends BaseException {
    public InvalidUserIdException(HttpStatus httpStatus, String message, Throwable throwable) {
        super(httpStatus, message, throwable);
    }
}
