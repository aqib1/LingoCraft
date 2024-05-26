package com.lingo.craft.adapters.advice.temporal;

import io.temporal.failure.ApplicationFailure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.diabolocom.release.openapi.model.ErrorResponse;

@RestControllerAdvice
public class TemporalExceptionHandler {

    @ExceptionHandler(ApplicationFailure.class)
    public ResponseEntity<ErrorResponse> handleApplicationFailure(
            ApplicationFailure applicationFailure
    ) {
        return new ResponseEntity<>(
                new ErrorResponse().errorMessage(
                        applicationFailure.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
