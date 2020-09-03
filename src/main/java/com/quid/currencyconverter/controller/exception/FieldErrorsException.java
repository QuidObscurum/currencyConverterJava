package com.quid.currencyconverter.controller.exception;

import org.springframework.validation.FieldError;

import java.util.List;

public class FieldErrorsException extends RuntimeException {

    public FieldErrorsException(List<FieldError> fieldErrors) {
        this(getFieldErrorMessage(fieldErrors));
    }

    public FieldErrorsException(String message) {
        super(message);
    }

    static private String getFieldErrorMessage(List<FieldError> fieldErrors) {
        StringBuilder message = new StringBuilder();
        int lastErrorIndex = fieldErrors.size() - 1;
        for (int i = 0; i <= lastErrorIndex; i++) {
            FieldError fieldError = fieldErrors.get(i);
            message.append(String.format(
                    "%s (%s): %s%s",
                    fieldError.getCode(), fieldError.getField(),
                    fieldError.getDefaultMessage(), i == lastErrorIndex ? "." : "; "
            ));
        }
        return message.toString();
    }
}
