package com.quid.currencyconverter.dbService;

import com.quid.currencyconverter.jpa.CurrencyJPA;

import java.util.List;

public interface DBService {
    void save(CurrencyJPA currencyJPA);

    void update(CurrencyJPA currencyJPA);

    CurrencyJPA read(long id);

    CurrencyJPA readByCurrencies(String from, String to);

    List<CurrencyJPA> readAll();

    void shutdown();
}
