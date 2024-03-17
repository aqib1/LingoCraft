package com.lingo.craft.adapters.advice.user;

import com.diabolocom.release.openapi.model.ErrorResponse;
import com.lingo.craft.domain.base.BaseException;
import com.lingo.craft.domain.user.exception.InvalidUserIdException;
import com.lingo.craft.domain.user.exception.UserCreationException;
import com.lingo.craft.domain.user.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler( {
        InvalidUserIdException.class,
        UserCreationException.class,
        UserNotFoundException.class
    } )
    public ResponseEntity<ErrorResponse> handle(BaseException baseException) {
        return new ResponseEntity<>(
            new ErrorResponse().errorMessage(
                baseException.getMessage()),
            baseException.getStatus()
        );
    }
}
