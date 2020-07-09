package com.quid.currencyconverter.myUtils;

import com.quid.currencyconverter.dbService.DBService;
import com.quid.currencyconverter.jpa.CurrencyJPA;

import java.math.BigDecimal;

public class Propagate {

    public static void run(DBService dbService) {
        dbService.save(new CurrencyJPA("RUB", "BYN", new BigDecimal("0.033882")));
        dbService.save(new CurrencyJPA("USD", "BYN", new BigDecimal("2.4440")));
        dbService.save(new CurrencyJPA("USD", "RUB", new BigDecimal("71.5500")));
        dbService.save(new CurrencyJPA("EUR", "BYN", new BigDecimal("2.7560")));
        dbService.save(new CurrencyJPA("EUR", "USD", new BigDecimal("1.1290")));
        dbService.save(new CurrencyJPA("EUR", "RUB", new BigDecimal("80.6000")));
    }
}
