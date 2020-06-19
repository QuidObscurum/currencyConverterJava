package converter.main;

import base.DBService;
import base.dataSets.CurrencyDataSet;
import dbService.DBServiceImpl;
import myUtils.InvalidInputException;
import myUtils.Propagate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CurrencyConverter {
    public static String[] allowedCurrencies = {"BYN", "USD", "EUR", "RUB"};
    public double inputValue;
    public String fromCurrency;
    public String toCurrency;

    public static void main (String[] args) {
        DBService dbService = new DBServiceImpl();
        Propagate.run(dbService);
        // Some test reads
//        CurrencyDataSet currencyDS1 = dbService.readByCurrencies("USD", "BYN");
//        System.out.println(currencyDS1);
//        CurrencyDataSet currencyDS2 = dbService.read(1);
//        System.out.println(currencyDS2);
//        List<CurrencyDataSet> dataSets = dbService.readAll();
//        for (CurrencyDataSet ds : dataSets) {
//            System.out.println(ds);
//        }

        CurrencyConverter converter = new CurrencyConverter();
        CurrencyConverter.printRules();

        if (args.length > 0){
            try {
                converter.processArgs(args);
            } catch (InvalidInputException e) {
                System.err.println(e);
            }
            double rate = dbService.readByCurrencies(converter.fromCurrency, converter.toCurrency).getRate();
            System.out.println(converter.convert(rate) + ' ' + converter.toCurrency);
        }

        boolean run = true;
        while (run){
//        Prompt user input
            String[] queryArgs = CurrencyConverter.getUserInput();
            if (queryArgs.length == 1
                    && ((String)Array.get(queryArgs, 0)).equalsIgnoreCase("exit")) {
                run = false;
            } else {
                try {
                    converter.processArgs(queryArgs);
                } catch (InvalidInputException e) {
                    System.err.println(e);
                    continue;
                }
                double rate = dbService.readByCurrencies(converter.fromCurrency, converter.toCurrency).getRate();
                System.out.println(converter.convert(rate) + " " + converter.toCurrency);
            }
        }
        // close connections
        dbService.shutdown();
    }

    protected double convert(double rate) {
        return this.inputValue * rate;
    }

    protected void processArgs(String[] args) throws InvalidInputException{
        try {
            this.inputValue = Double.parseDouble(
                    (String) Array.get(args, 0)
            );
        } catch (NumberFormatException e) {
            throw new InvalidInputException(
                    "Got invalid sum: " + Array.get(args, 0) + "\nInput format: 10 BYN to USD"
            );
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidInputException(
                    "Got invalid query. \nInput format: 10 BYN to USD"
            );
        }
        try {
            String fromCurrency = (String) Array.get(args, 1);
            String toCurrency = (String) Array.get(args, 3);
            if (!CurrencyConverter.validateCurrencies(fromCurrency, toCurrency)){
                throw new InvalidInputException(
                    "Got invalid currency. \nAllowed currencies: " + Arrays.toString(CurrencyConverter.allowedCurrencies)
                );
            }
            this.fromCurrency = fromCurrency.toUpperCase();
            this.toCurrency = toCurrency.toUpperCase();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidInputException(
                    "Got invalid query. \nInput format: 10 BYN to USD"
            );
        }
    }

    public static boolean validateCurrencies(String currency1, String currency2) {

        return Arrays.stream(CurrencyConverter.allowedCurrencies).anyMatch(currency1.toUpperCase()::equals)
                && Arrays.stream(CurrencyConverter.allowedCurrencies).anyMatch(currency2.toUpperCase()::equals);
    }

    public static void printRules() {
        System.out.println("If you want to buy 10 BYN with US dollars, \nthen input format: 10 BYN to USD");
        System.out.println("Allowed currencies: " + Arrays.toString(CurrencyConverter.allowedCurrencies));
        System.out.println("Type 'exit' to finish.");
    }

    public static String[] getUserInput() {
        Scanner sc = new Scanner(System.in);
        String query = sc.nextLine();
        return query.split(" ");
    }
}
