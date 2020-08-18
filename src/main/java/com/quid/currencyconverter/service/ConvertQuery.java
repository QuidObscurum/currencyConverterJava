package com.quid.currencyconverter.service;

import com.quid.currencyconverter.myutils.CurrencyCode;
import com.quid.currencyconverter.myutils.InvalidInputException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ConvertQuery {
    private BigDecimal inputValue;
    private CurrencyCode fromCurrency;
    private CurrencyCode toCurrency;

    public String inputFormat = "10 " + CurrencyCode.BYN + " : " + CurrencyCode.USD;
    public List<CurrencyCode> allowedCurrenciesList = Collections.unmodifiableList(Arrays.asList(CurrencyCode.values()));

    public static ConvertQuery getQuery(List<String> args) throws InvalidInputException {
        ConvertQuery query = new ConvertQuery();

        if (args.size() < 4) {
            throw new InvalidInputException(
                    "Got invalid query. Input format:\n" + query.inputFormat
            );
        }
        String inputValue = args.get(0).trim();
        String fromCurrency = args.get(1).trim().toUpperCase();
        String toCurrency = args.get(3).trim().toUpperCase();

        try {
            query.setFromCurrency(CurrencyCode.valueOf(fromCurrency));
            query.setToCurrency(CurrencyCode.valueOf(toCurrency));
            query.setInputValue(new BigDecimal(inputValue));
        } catch (NumberFormatException e) {
            throw new InvalidInputException(
                    "Got invalid sum: " + inputValue + ". Input format:\n" + query.inputFormat
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(
                    "Got invalid query.\nAllowed currencies: " + query.allowedCurrenciesList
            );
        }
        return query;
    }

    private void setInputValue(BigDecimal inputValue) {
        this.inputValue = inputValue.setScale(4, RoundingMode.HALF_UP);
    }

    private void setFromCurrency(CurrencyCode fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    private void setToCurrency(CurrencyCode toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getInputValue() {
        return inputValue;
    }

    public CurrencyCode getFromCurrency() {
        return fromCurrency;
    }

    public CurrencyCode getToCurrency() {
        return toCurrency;
    }

}
