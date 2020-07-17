package com.quid.currencyconverter.dbService;

import static org.junit.jupiter.api.Assertions.*;

import com.quid.currencyconverter.config.ApplicationConfig;
import com.quid.currencyconverter.jpa.CurrencyJPA;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=ApplicationConfig.class)
class DBServiceImplTest {
    @Autowired
    private DBService dbService;
//    public DBServiceImplTest(DBService dbService) {
//        this.dbService = dbService;
//    }

//    @org.junit.jupiter.api.BeforeEach
//    void setUp() {
//        this d
//    }

//    @org.junit.jupiter.api.AfterEach
//    void tearDown() {
//    }

//    @org.junit.jupiter.api.Test
//    void save() {
//    }

    @org.junit.jupiter.api.Test
    void shouldUpdate() {
        CurrencyJPA oldCurrencyJPA = dbService.read(1L);
        CurrencyJPA newCurrencyJPA = new CurrencyJPA(
                oldCurrencyJPA.getId(), oldCurrencyJPA.getFromCurrency(),
                oldCurrencyJPA.getToCurrency(), oldCurrencyJPA.getRate().add(new BigDecimal("0.1"))
        );

        dbService.update(newCurrencyJPA);

        CurrencyJPA currencyJPA = dbService.read(1L);
        assertEquals(currencyJPA, newCurrencyJPA);
        assertNotEquals(currencyJPA, oldCurrencyJPA);

        dbService.update(oldCurrencyJPA);
        assertEquals(dbService.read(1L), oldCurrencyJPA);
    }

//    @org.junit.jupiter.api.Test
//    void read() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void readByCurrencies() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void readAll() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void shutdown() {
//    }
}