package com.quid.currencyconverter.dbservice;

import com.quid.currencyconverter.domain.jpa.CurrencyJPA;

import java.util.List;

public interface CurrencyDBService {
    CurrencyJPA save(CurrencyJPA currencyJPA);

    CurrencyJPA update(CurrencyJPA currencyJPA);

    CurrencyJPA read(long id);

    CurrencyJPA readByCurrencies(String from, String to);

    List<CurrencyJPA> readAll();

    void delete(CurrencyJPA currencyJPA);

    void deleteByToCurrency(String toCurrency);

}
