package com.quid.currencyconverter.testConfig;

import com.mysql.cj.jdbc.Driver;
import com.quid.currencyconverter.dbservice.CurrencyDBServiceImpl;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Test context requires configuration of entityManagerFactory,
// otherwise tests throw java.lang.IllegalStateException: Failed to load ApplicationContext
// Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException:
// ... nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'entityManagerFactory' available

@TestConfiguration
public class HibernateConfig {

    @Bean(name = "dataSource")
    public DriverManagerDataSource springDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUsername(hibernateProperties().getProperty("username"));
        dataSource.setPassword(hibernateProperties().getProperty("password"));
        dataSource.setUrl(hibernateProperties().getProperty("url"));
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean springEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
        emFactory.setDataSource(springDataSource());
        emFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emFactory.setJpaProperties(hibernateProperties());
        emFactory.setPackagesToScan("com.quid.currencyconverter.domain.jpa");
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
                .getResourceAsStream("testDataSource.properties")) {
            hibernateProperties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hibernateProperties;
    }
}