package com.quid.currencyconverter;

import com.quid.currencyconverter.dbService.DBService;
import com.quid.currencyconverter.dbService.DBServiceImpl;
import com.quid.currencyconverter.myUtils.InvalidInputException;
import com.quid.currencyconverter.service.ConverterService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main (String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "context.xml"
        );
        // getting via class is bad, should get via interface - ?
        DBService dbService = context.getBean("dbService", DBServiceImpl.class);

        ConverterService converter = context.getBean("converter", ConverterService.class);
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        System.out.println(context.getBeanDefinitionCount());
        System.out.println("dbService hash " + dbService.hashCode());

        CurrencyConverter.printRules(ConverterService.inputFormat);

        if (args.length > 0){
            try {
                converter.processQuery(Arrays.asList(args));
            } catch (InvalidInputException e) {
                System.err.println(e.getMessage());
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
                    System.err.println(e.getMessage());
                    continue;
                }
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
