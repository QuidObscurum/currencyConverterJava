package com.quid.currencyconverter.service;

import static com.quid.currencyconverter.service.ConvertQuery.CurrencyCode.*;
import com.quid.currencyconverter.dbservice.CurrencyDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;


@Service("converter")
public class ConverterService {
    @Autowired
    private CurrencyDBService dbService;

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
}
