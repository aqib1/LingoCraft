package com.lingo.craft.domain.sentiment.exception;

import com.lingo.craft.domain.base.BaseException;
import org.springframework.http.HttpStatus;

public class ContentSentimentNotFoundException extends BaseException {
    public ContentSentimentNotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
