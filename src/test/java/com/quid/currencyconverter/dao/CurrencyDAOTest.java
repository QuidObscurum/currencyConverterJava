package com.quid.currencyconverter.dao;

import com.quid.currencyconverter.config.ApplicationConfig;
import com.quid.currencyconverter.config.HibernateConfig;
import com.quid.currencyconverter.jpa.CurrencyJPA;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {ApplicationConfig.class, HibernateConfig.class})
class CurrencyDAOTest {
    @Autowired
    private CurrencyDAO currencyDAO;

//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void shouldRead() {
        CurrencyJPA currencyJPA = currencyDAO.read(4L);
        assertNotNull(currencyJPA);
        assertNotNull(currencyJPA.getFromCurrency());
        assertNotNull(currencyJPA.getToCurrency());
        assertNotNull(currencyJPA.getRate());
        System.out.println(currencyJPA);
    }

    @Test
    void shouldReadByCurrencies() {
        CurrencyJPA currencyJPA = currencyDAO.readByCurrencies("EUR", "BYN");
        assertNotNull(currencyJPA);
        assertNotNull(currencyJPA.getFromCurrency());
        assertNotNull(currencyJPA.getToCurrency());
        assertNotNull(currencyJPA.getRate());
        System.out.println(currencyJPA);
    }

    @Test
    void shouldReadAll() {
        List<CurrencyJPA> currencyJPAList = currencyDAO.readAll();
        assertTrue(currencyJPAList.size() > 0);
        for (CurrencyJPA currency : currencyJPAList) {
            System.out.println(currency);
        }
    }

    @Test
    void shouldSave() {
        CurrencyJPA currency = new CurrencyJPA("USD", "AUD", new BigDecimal("1.4300"));
        currencyDAO.save(currency);
        CurrencyJPA currencyJPA = currencyDAO.readByCurrencies("USD", "AUD");
        assertEquals(currency, currencyJPA);
        System.out.println(currencyJPA);
        System.out.println(currency);
        currencyDAO.delete(currencyJPA);
    }

    @Test
    void shouldUpdate() {
        CurrencyJPA oldCurrencyJPA = currencyDAO.read(1L);
        CurrencyJPA newCurrencyJPA = new CurrencyJPA(
                oldCurrencyJPA.getId(), oldCurrencyJPA.getFromCurrency(),
                oldCurrencyJPA.getToCurrency(), oldCurrencyJPA.getRate().add(new BigDecimal("0.1"))
        );

        currencyDAO.update(newCurrencyJPA);

        CurrencyJPA currencyJPA = currencyDAO.read(1L);
        assertEquals(currencyJPA, newCurrencyJPA);
        assertNotEquals(currencyJPA, oldCurrencyJPA);
        System.out.println(oldCurrencyJPA);
        System.out.println(currencyJPA);

        currencyDAO.update(oldCurrencyJPA);
        assertEquals(currencyDAO.read(1L), oldCurrencyJPA);
    }

    @Test
    void shouldDelete() {
        CurrencyJPA currency = new CurrencyJPA(14L, "USD", "AUD", new BigDecimal("1.43"));
        currencyDAO.delete(currency);
    }
}