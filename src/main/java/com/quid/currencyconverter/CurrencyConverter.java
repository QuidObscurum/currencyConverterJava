package com.quid.currencyconverter;

import com.quid.currencyconverter.dbService.DBService;
import com.quid.currencyconverter.dbService.DBServiceImpl;
import com.quid.currencyconverter.myUtils.InvalidInputException;
import com.quid.currencyconverter.myUtils.Propagate;
import com.quid.currencyconverter.service.ConverterService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main (String[] args) {
        DBService dbService = new DBServiceImpl();
//        for running for the first time uncomment the line below
//        Propagate.run(dbService);
        // Some test reads
//        CurrencyJPA currencyDS1 = dbService.readByCurrencies("USD", "BYN");
//        System.out.println(currencyDS1);
//        CurrencyJPA currencyDS2 = dbService.read(1);
//        System.out.println(currencyDS2);
//        List<CurrencyJPA> dataSets = dbService.readAll();
//        for (CurrencyJPA ds : dataSets) {
//            System.out.println(ds);
//        }

        ConverterService converter = new ConverterService();
        CurrencyConverter.printRules(ConverterService.inputFormat);

        if (args.length > 0){
            try {
                converter.processQuery(Arrays.asList(args));
            } catch (InvalidInputException e) {
                System.err.println(e);
            }
            System.out.println(converter.convert() + " " + converter.getToCurrency());
        }

        boolean run = true;
        while (run){
            List<String> queryArgs = CurrencyConverter.getUserInput();
            if (queryArgs.size() == 1
                    && (queryArgs.get(0)).equalsIgnoreCase("exit")) {
                run = false;
            } else {
                try {
                    converter.processQuery(queryArgs);
                } catch (InvalidInputException e) {
                    System.err.println(e);
                    continue;
                }
                BigDecimal decimal = converter.convert();
                System.out.println(converter.convert() + " " + converter.getToCurrency());
            }
        }
        // close connections
        dbService.shutdown();
    }

    public static void printRules(String format) {
        System.out.println("If you want to buy 10 BYN with US dollars, " +
                "\nthe input format is: " + format);
        System.out.println("Allowed currencies: " + ConverterService.allowedCurrenciesList);
        System.out.println("Type 'exit' to finish.");
    }

    public static List<String> getUserInput() {
        Scanner sc = new Scanner(System.in);
        String query = sc.nextLine();
        return Arrays.asList(query.split(" "));
    }
}
