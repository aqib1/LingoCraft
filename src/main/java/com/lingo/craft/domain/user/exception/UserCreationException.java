package com.lingo.craft.domain.user.exception;

import com.lingo.craft.domain.base.BaseException;
import org.springframework.http.HttpStatus;

public class UserCreationException extends BaseException {
    public UserCreationException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
