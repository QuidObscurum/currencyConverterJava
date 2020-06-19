package base;

import base.dataSets.CurrencyDataSet;

import java.util.List;

public interface DBService {
    void save(CurrencyDataSet dataSet);

    CurrencyDataSet read(long id);

    CurrencyDataSet readByCurrencies(String from, String to);

    List<CurrencyDataSet> readAll();

    void shutdown();
}
