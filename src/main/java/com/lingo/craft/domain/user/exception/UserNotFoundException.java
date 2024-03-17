package com.lingo.craft.domain.user.exception;

import com.lingo.craft.domain.base.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
