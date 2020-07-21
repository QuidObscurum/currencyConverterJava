package com.quid.currencyconverter.myutils;

import com.quid.currencyconverter.service.ConvertQuery;

public class Helper {
    public void printRules() {
        ConvertQuery query = new ConvertQuery();
        System.out.println("If you want to buy 10 BYN with US dollars, " +
                "\nthe input format is: " + query.inputFormat);
        System.out.println("Allowed currencies: " + query.allowedCurrenciesList);
        System.out.println("Type 'exit' to finish.");
    }
}
