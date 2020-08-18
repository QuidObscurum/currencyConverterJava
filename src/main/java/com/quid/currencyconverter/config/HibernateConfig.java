package com.quid.currencyconverter.config;

import com.mysql.cj.jdbc.Driver;
import com.quid.currencyconverter.dbservice.CurrencyDBServiceImpl;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
//@EnableTransactionManagement

// adding basePackages almost solved the problem of spring not creating repository bean, had to also change Bean names
@EnableJpaRepositories(basePackages = "com.quid.currencyconverter.dao")
public class HibernateConfig {

    @Bean(name = "dataSource")
    public DriverManagerDataSource springDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUsername(hibernateProperties().getProperty("hibernate.connection.username"));
        dataSource.setPassword(hibernateProperties().getProperty("hibernate.connection.password"));
        dataSource.setUrl(hibernateProperties().getProperty("hibernate.connection.url"));
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean springEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
        emFactory.setDataSource(springDataSource());
        emFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emFactory.setJpaProperties(hibernateProperties());
        emFactory.setPackagesToScan("com.quid.currencyconverter.jpa");
        return emFactory;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager springTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(springEntityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        try (InputStream in = CurrencyDBServiceImpl.class
                .getClassLoader()
                .getResourceAsStream("hibernate.properties")) {
            hibernateProperties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hibernateProperties;
    }
}