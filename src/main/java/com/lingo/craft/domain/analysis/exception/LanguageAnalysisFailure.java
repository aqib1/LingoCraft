package com.lingo.craft.domain.analysis.exception;

import com.lingo.craft.domain.base.BaseException;
import org.springframework.http.HttpStatus;

public class LanguageAnalysisFailure extends BaseException {
    public LanguageAnalysisFailure(HttpStatus httpStatus, String message, Throwable throwable) {
        super(httpStatus, message, throwable);
    }
}
