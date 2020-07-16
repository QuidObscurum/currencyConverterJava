package com.quid.currencyconverter.config;

import com.quid.currencyconverter.dao.CurrencyDAO;
import com.quid.currencyconverter.dbService.DBService;
import com.quid.currencyconverter.dbService.DBServiceImpl;
import com.quid.currencyconverter.service.ConverterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ConverterService converter() {
        return new ConverterService(dbService()); // @Autowired
    }

    @Bean
    public DBService dbService() {
        return new DBServiceImpl(currencyDAO());
    }

    @Bean
    public CurrencyDAO currencyDAO() {
        return new CurrencyDAO();
    }
}
