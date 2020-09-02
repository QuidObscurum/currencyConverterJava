package com.quid.currencyconverter.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = {
        "com.quid.currencyconverter.controller",
        "com.quid.currencyconverter.dao",
        "com.quid.currencyconverter.dbservice",
        "com.quid.currencyconverter.service"
})
@Configuration
@EnableJpaRepositories(basePackages = "com.quid.currencyconverter.dao")
public class ApplicationConfig {
}
