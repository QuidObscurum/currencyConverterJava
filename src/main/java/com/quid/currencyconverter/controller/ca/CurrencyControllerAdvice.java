package com.quid.currencyconverter.controller.ca;

import com.quid.currencyconverter.controller.exception.FieldErrorsException;
import com.quid.currencyconverter.domain.dto.MyErrorResponse;
import com.quid.currencyconverter.domain.enums.CurrencyCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@ControllerAdvice
public class CurrencyControllerAdvice {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MyErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e,
            HttpServletRequest request
    ) {
        return ResponseEntity.badRequest().body(
                new MyErrorResponse(
                        request.getServletPath(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "Revise request parameters. Allowed currency codes: "
                                + Arrays.toString(CurrencyCode.values())
                )
        );
    }

    @ExceptionHandler(FieldErrorsException.class)
    public ResponseEntity<MyErrorResponse> handleFieldErrorsException (
            FieldErrorsException e,
            HttpServletRequest request
    ) {
        return ResponseEntity.badRequest().body(
                new MyErrorResponse(
                        request.getServletPath(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        e.getMessage()
                )
        );
    }
}
