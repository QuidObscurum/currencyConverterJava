package com.quid.currencyconverter.service;

import com.quid.currencyconverter.dbService.DBService;
import com.quid.currencyconverter.myUtils.InvalidInputException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ConverterService {
    private DBService dbService;

//    public ConverterService() {
//    }

    public ConverterService(DBService dbService) {
        this.dbService = dbService;
        System.out.println("this.dbService hash " + this.dbService.hashCode());
    }

    public final static List<String> allowedCurrenciesList = new ArrayList<>(
            Arrays.asList("BYN", "USD", "EUR", "RUB")
    );
    public static final String inputFormat = "10 BYN : USD";

    private BigDecimal inputValue;
    private String fromCurrency;
    private String toCurrency;

    public BigDecimal getInputValue() {
        return inputValue;
    }

    private void setInputValue(BigDecimal inputValue) {
        this.inputValue = inputValue.setScale(4, RoundingMode.HALF_UP);
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    private void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    private void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal convert() {
        if (toCurrency.equals(fromCurrency)) {return inputValue;}
        if (
                fromCurrency.equals("EUR") ||
                (fromCurrency.equals("USD") && Arrays.asList("RUB", "BYN").contains(toCurrency)) ||
                (fromCurrency.equals("RUB") && toCurrency.equals("BYN"))

        ) {
            BigDecimal rate = dbService
                    .readByCurrencies(fromCurrency, toCurrency)
                    .getRate();
            return inputValue.multiply(rate).setScale(4, RoundingMode.HALF_UP);
        }
            BigDecimal rate = dbService
                    .readByCurrencies(toCurrency, fromCurrency)
                    .getRate();
            return inputValue.divide(rate,4, RoundingMode.HALF_UP);
    }

    public void processQuery(List<String> args) throws InvalidInputException {
        if (args.size() < 4) {
            throw new InvalidInputException(
                    "Got invalid query. Input format:\n" + inputFormat
            );
        }
        String inputValue = args.get(0);
        String fromCurrency = args.get(1).toUpperCase();
        String toCurrency = args.get(3).toUpperCase();
        if (!ConverterService.validateCurrencies(fromCurrency, toCurrency)){
            throw new InvalidInputException(
                    "Got invalid query.\nAllowed currencies: " + allowedCurrenciesList
            );
        }

        try {
            this.setFromCurrency(fromCurrency);
            this.setToCurrency(toCurrency);
            this.setInputValue(new BigDecimal(inputValue));
        } catch (NumberFormatException e) {
            throw new InvalidInputException(
                    "Got invalid sum: " + inputValue + ". Input format:\n" + inputFormat
            );
        }
    }

    public static boolean validateCurrencies(String currency1, String currency2) {
        return allowedCurrenciesList
                .stream()
                .anyMatch(currency1::equals)
            && allowedCurrenciesList
                .stream()
                .anyMatch(currency2::equals);
    }
}
