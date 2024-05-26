package com.lingo.craft.adapters.advice.sentiment;

import com.lingo.craft.domain.base.BaseException;
import com.lingo.craft.domain.sentiment.exception.ContentSentimentNotFoundException;
import com.lingo.craft.domain.sentiment.exception.ContentSentimentPersistenceFailure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.diabolocom.release.openapi.model.ErrorResponse;

@RestControllerAdvice
public class ContentSentimentControllerAdvice {
    @ExceptionHandler({
            ContentSentimentPersistenceFailure.class,
            ContentSentimentNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handle(
            BaseException failure
    ) {
        return new ResponseEntity<>(
                new ErrorResponse().errorMessage(
                        failure.getMessage()),
                failure.getStatus()
        );
    }
}
