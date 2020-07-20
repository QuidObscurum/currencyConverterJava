package com.quid.currencyconverter.dbservice;

import com.quid.currencyconverter.jpa.CurrencyJPA;

import java.util.List;

public interface DBService {
    void save(CurrencyJPA currencyJPA);

    void update(CurrencyJPA currencyJPA);

    CurrencyJPA read(long id);

    CurrencyJPA readByCurrencies(String from, String to);

    List<CurrencyJPA> readAll();

    void delete(CurrencyJPA currencyJPA);

    void shutdown();
}
