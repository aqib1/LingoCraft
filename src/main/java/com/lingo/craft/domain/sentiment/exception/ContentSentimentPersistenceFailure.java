package com.lingo.craft.domain.sentiment.exception;

import com.lingo.craft.domain.base.BaseException;
import org.springframework.http.HttpStatus;

public class ContentSentimentPersistenceFailure extends BaseException {

    public ContentSentimentPersistenceFailure(HttpStatus httpStatus, String message, Throwable throwable) {
        super(httpStatus, message, throwable);
    }

    public ContentSentimentPersistenceFailure(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
