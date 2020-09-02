package com.quid.currencyconverter.controller;

import com.quid.currencyconverter.dbservice.CurrencyDBServiceImpl;
import com.quid.currencyconverter.domain.dto.ExchangeResultDTO;
import com.quid.currencyconverter.domain.enums.CurrencyCode;
import com.quid.currencyconverter.service.ConverterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class CurrencyController {

    private final ConverterService converter;

    private static final Logger logger = LoggerFactory.getLogger(CurrencyDBServiceImpl.class);

    public CurrencyController(ConverterService converter) {
        this.converter = converter;
    }

    @PostMapping(value = "/convert", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> convert(
            @RequestBody @Valid ExchangeResultDTO resultDTO,
            BindingResult result,
            HttpServletRequest request
    ) {
        if (result.hasFieldErrors()) {
            String message = getFieldErrorMessage(result.getFieldErrors());
            logger.info(message, resultDTO);
            return ResponseEntity.badRequest().body(
                    new MyErrorResponse(
                            request.getServletPath(),
                            HttpStatus.BAD_REQUEST.value(),
                            "Bad Request",
                            message
            ));
        }
        return ResponseEntity.ok(converter.convert(resultDTO));
    }

    @GetMapping(value = "/")
    public String home() {
        return "WORKS!";
    }

    private String getFieldErrorMessage(List<FieldError> fieldErrors) {
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

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public MyErrorResponse conflict(HttpMessageNotReadableException e, HttpServletRequest request) {
        logger.info(e.getMessage());
        return new MyErrorResponse(
                request.getServletPath(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Revise request parameters. Allowed currency codes: "
                        + Arrays.toString(CurrencyCode.values())
        );
    }

    static class MyErrorResponse {
        public final String path;
        public final Integer status;
        public final String error;
        public final String message;

        public MyErrorResponse(String path, Integer status, String error, String message) {
            this.path = path;
            this.status = status;
            this.error = error;
            this.message = message;
        }

        @Override
        public String toString() {
            return "MyErrorResponse{" +
                    "path='" + path + '\'' +
                    ", status=" + status +
                    ", error='" + error + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

}
