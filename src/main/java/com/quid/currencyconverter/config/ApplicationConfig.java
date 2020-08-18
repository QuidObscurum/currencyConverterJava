package com.quid.currencyconverter.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {
        "com.quid.currencyconverter.controller",
        "com.quid.currencyconverter.dao",
        "com.quid.currencyconverter.dbservice",
        "com.quid.currencyconverter.service"
})
@Configuration
public class ApplicationConfig {
}
