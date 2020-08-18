package com.quid.currencyconverter.service;

import static com.quid.currencyconverter.myutils.CurrencyCode.*;
import com.quid.currencyconverter.dbservice.CurrencyDBService;
import com.quid.currencyconverter.dto.ExchangeResultDTO;
import com.quid.currencyconverter.myutils.CurrencyCode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;


@Service("converter")
public class ConverterService {
    private final CurrencyDBService dbService;

    public ConverterService(CurrencyDBService dbService) {
        this.dbService = dbService;
    }

    public BigDecimal convert(ConvertQuery query) {
        if (query.getToCurrency().equals(query.getFromCurrency())) {return query.getInputValue();}
        if (
                query.getFromCurrency().equals(EUR) ||
                (query.getFromCurrency().equals(USD) && Arrays.asList(RUB, BYN)
                        .contains(query.getToCurrency())) ||
                (query.getFromCurrency().equals(RUB) && query.getToCurrency().equals(BYN))
        ) {
            BigDecimal rate = dbService
                    .readByCurrencies(query.getFromCurrency().toString(), query.getToCurrency().toString())
                    .getRate();
            return query.getInputValue().multiply(rate).setScale(4, RoundingMode.HALF_UP);
        }
        BigDecimal rate = dbService
                .readByCurrencies(query.getToCurrency().toString(), query.getFromCurrency().toString())
                .getRate();
        return query.getInputValue().divide(rate,4, RoundingMode.HALF_UP);
    }

    public ExchangeResultDTO convert(ExchangeResultDTO query) {
        if (query.getToCurrency().equals(query.getFromCurrency())) {
            return query;
        }
        BigDecimal rate = getRate(query.getFromCurrency(), query.getToCurrency());
        query.setExchangeResultValue(query.getFromCurrencyValue().multiply(rate).setScale(4, RoundingMode.HALF_UP));
        return query;
    }

    private BigDecimal getRate(CurrencyCode fromCurrency, CurrencyCode toCurrency) {
        if (
                fromCurrency.equals(EUR) ||
                        (fromCurrency.equals(USD) && Arrays.asList(RUB, BYN)
                                .contains(toCurrency)) ||
                        (fromCurrency.equals(RUB) && toCurrency.equals(BYN))
        ) {
            return dbService
                    .readByCurrencies(fromCurrency.toString(), toCurrency.toString())
                    .getRate();
        }
        return dbService
                .readByCurrencies(toCurrency.toString(), fromCurrency.toString())
                .getRate().pow(-1, MathContext.DECIMAL64);
    }
}
