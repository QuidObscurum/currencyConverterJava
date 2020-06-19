package myUtils;

import base.DBService;
import base.dataSets.CurrencyDataSet;

public class Propagate {

    public static void run(DBService dbService) {

        dbService.save(new CurrencyDataSet("RUB", "BYN",29.24));

        dbService.save(new CurrencyDataSet("BYN", "RUB", 0.0342));

        dbService.save(new CurrencyDataSet("USD", "BYN", 0.42));

        dbService.save(new CurrencyDataSet("EUR", "BYN", 0.3748));

        dbService.save(new CurrencyDataSet("BYN", "USD", 2.377));

        dbService.save(new CurrencyDataSet("BYN", "EUR", 2.665));

        dbService.save(new CurrencyDataSet("RUB", "USD", 69.65));

        dbService.save(new CurrencyDataSet("RUB", "EUR", 78.0));

        dbService.save(new CurrencyDataSet("USD", "EUR", 1.117));

        dbService.save(new CurrencyDataSet("EUR", "USD", 0.8913));

        dbService.save(new CurrencyDataSet("USD", "RUB", 0.0143));

        dbService.save(new CurrencyDataSet("EUR", "RUB", 0.0128));
    }
}
