package com.lingo.craft.domain.translation.exception;

import com.lingo.craft.domain.base.BaseException;
import org.springframework.http.HttpStatus;

public class ContentTranslationException extends BaseException {
    public ContentTranslationException(HttpStatus httpStatus, String message, Throwable throwable) {
        super(httpStatus, message, throwable);
    }
}
