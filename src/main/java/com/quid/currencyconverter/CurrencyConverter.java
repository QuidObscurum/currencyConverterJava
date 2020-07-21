package com.quid.currencyconverter;

import com.quid.currencyconverter.config.ApplicationConfig;
import com.quid.currencyconverter.config.HibernateConfig;
import com.quid.currencyconverter.myutils.Helper;
import com.quid.currencyconverter.myutils.InvalidInputException;
import com.quid.currencyconverter.service.ConvertQuery;
import com.quid.currencyconverter.service.ConverterService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main (String[] args) {
        Helper helper = new Helper();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        context.register(HibernateConfig.class);
        ConverterService converter = context.getBean("converter", ConverterService.class);
//        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
//        System.out.println(context.getBeanDefinitionCount());

        helper.printRules();

        if (args.length > 0){
            try {
                ConvertQuery query = ConvertQuery.getQuery(Arrays.asList(args));
                System.out.println(converter.convert(query) + " " + query.getToCurrency());
            } catch (InvalidInputException e) {
                System.err.println(e.getMessage());
            }
        }

        boolean run = true;
        while (run){
            List<String> queryArgs = CurrencyConverter.getUserInput();
            if (queryArgs.size() == 1
                    && (queryArgs.get(0)).equalsIgnoreCase("exit")) {
                run = false;
            } else {
                try {
                    ConvertQuery query = ConvertQuery.getQuery(queryArgs);
                    System.out.println(converter.convert(query) + " " + query.getToCurrency());
                } catch (InvalidInputException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public static List<String> getUserInput() {
        Scanner sc = new Scanner(System.in);
        String query = sc.nextLine();
        return Arrays.asList(query.split(" "));
    }
}
