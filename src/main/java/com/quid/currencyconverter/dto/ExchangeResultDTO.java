package com.quid.currencyconverter.dto;

import com.quid.currencyconverter.myutils.CurrencyCode;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeResultDTO {
    private BigDecimal fromCurrencyValue;
    private BigDecimal exchangeResultValue;
    private CurrencyCode fromCurrency;
    private CurrencyCode toCurrency;

    public ExchangeResultDTO(BigDecimal fromCurrencyValue, CurrencyCode fromCurrency, CurrencyCode toCurrency, BigDecimal exchangeResultValue) {
        this.fromCurrencyValue = fromCurrencyValue.setScale(4, RoundingMode.HALF_UP);
        this.exchangeResultValue = exchangeResultValue.setScale(4, RoundingMode.HALF_UP);
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    public ExchangeResultDTO(BigDecimal fromCurrencyValue, CurrencyCode fromCurrency, CurrencyCode toCurrency) {
        this(fromCurrencyValue, fromCurrency, toCurrency, fromCurrencyValue);
    }

    //default constructor to solve
//    com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of
//    `com.quid.currencyconverter.dto.ExchangeResultDTO` (no Creators, like default constructor, exist):
//    cannot deserialize from Object value (no delegate- or property-based Creator)
    public ExchangeResultDTO() {
        super();
    }

    public BigDecimal getFromCurrencyValue() {
        return fromCurrencyValue;
    }

    public BigDecimal getExchangeResultValue() {
        return exchangeResultValue;
    }

    public void setExchangeResultValue(BigDecimal exchangeResultValue) {
        this.exchangeResultValue = exchangeResultValue;
    }

    public CurrencyCode getFromCurrency() {
        return fromCurrency;
    }

    public CurrencyCode getToCurrency() {
        return toCurrency;
    }

    public void setFromCurrencyValue(BigDecimal fromCurrencyValue) {
        this.fromCurrencyValue = fromCurrencyValue;
    }

    public void setFromCurrency(CurrencyCode fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public void setToCurrency(CurrencyCode toCurrency) {
        this.toCurrency = toCurrency;
    }

    @Override
    public String toString() {
        return "ExchangeResultDTO{" +
                "fromCurrencyValue=" + fromCurrencyValue +
                ", exchangeResultValue=" + exchangeResultValue +
                ", fromCurrency=" + fromCurrency +
                ", toCurrency=" + toCurrency +
                '}';
    }
}
