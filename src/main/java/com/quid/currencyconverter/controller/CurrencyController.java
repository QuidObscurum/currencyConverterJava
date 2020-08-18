package com.quid.currencyconverter.controller;

import com.quid.currencyconverter.dto.ExchangeResultDTO;
import com.quid.currencyconverter.service.ConverterService;
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
    public ExchangeResultDTO convert(@RequestBody ExchangeResultDTO resultDTO) {
        return converter.convert(resultDTO);
    }

    @GetMapping(value = "/")
    public String home() {
        return "WORKS!";
    }
}
