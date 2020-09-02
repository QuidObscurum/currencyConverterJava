package com.quid.currencyconverter.domain.dto;

import com.quid.currencyconverter.domain.enums.CurrencyCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeResultDTO {

    @NotNull(message = "Value to be converted cannot be null")
    @Positive
    private BigDecimal fromCurrencyValue;

    private BigDecimal exchangeResultValue;

    @NotNull(message = "Currency code cannot be null or empty")
    private CurrencyCode fromCurrency;

    @NotNull(message = "Currency code cannot be null or empty")
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
//    `com.quid.currencyconverter.domain.dto.ExchangeResultDTO` (no Creators, like default constructor, exist):
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
        this.exchangeResultValue = exchangeResultValue.setScale(4, RoundingMode.HALF_UP);
    }

    public CurrencyCode getFromCurrency() {
        return fromCurrency;
    }

    public CurrencyCode getToCurrency() {
        return toCurrency;
    }

    public void setFromCurrencyValue(BigDecimal fromCurrencyValue) {
        this.fromCurrencyValue = fromCurrencyValue.setScale(4, RoundingMode.HALF_UP);
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
