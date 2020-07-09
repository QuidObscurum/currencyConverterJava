package com.quid.currencyconverter.dbService;

import com.quid.currencyconverter.jpa.CurrencyJPA;

import java.util.List;

public interface DBService {
    void save(CurrencyJPA dataSet);

    void update(CurrencyJPA dataSet);

    CurrencyJPA read(long id);

    CurrencyJPA readByCurrencies(String from, String to);

    List<CurrencyJPA> readAll();

    void shutdown();
}
