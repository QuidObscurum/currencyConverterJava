package com.quid.currencyconverter.controller;

import com.quid.currencyconverter.controller.exception.FieldErrorsException;
import com.quid.currencyconverter.domain.dto.ExchangeResultDTO;
import com.quid.currencyconverter.service.ConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    private final ConverterService converter;

    public CurrencyController(ConverterService converter) {
        this.converter = converter;
    }

    @PostMapping(value = "/convert", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ExchangeResultDTO> convert(
            @RequestBody @Validated ExchangeResultDTO resultDTO,
            BindingResult result
    ) {
        if (result.hasFieldErrors()) {
            throw new FieldErrorsException(result.getFieldErrors());
        }
        return ResponseEntity.ok(converter.convert(resultDTO));
    }

    @GetMapping(value = "/")
    public String home() {
        return "WORKS!";
    }
}
