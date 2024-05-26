package com.lingo.craft.domain.processing.exception;

import com.lingo.craft.domain.base.BaseException;
import org.springframework.http.HttpStatus;

public class ContentSentimentAnalysisFailure extends BaseException {

    public ContentSentimentAnalysisFailure(HttpStatus httpStatus, String message, Throwable throwable) {
        super(httpStatus, message, throwable);
    }
}
