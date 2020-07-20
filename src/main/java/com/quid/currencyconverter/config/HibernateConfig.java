package com.quid.currencyconverter.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.quid.currencyconverter.dbservice.DBServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.quid.currencyconverter.jpa");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(hibernateProperties().getProperty("hibernate.connection.username"));
        dataSource.setPassword(hibernateProperties().getProperty("hibernate.connection.password"));
        dataSource.setUrl(hibernateProperties().getProperty("hibernate.connection.url"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        try (InputStream in = DBServiceImpl.class
                .getClassLoader()
                .getResourceAsStream("hibernate.properties")) {
            hibernateProperties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hibernateProperties;
    }
}