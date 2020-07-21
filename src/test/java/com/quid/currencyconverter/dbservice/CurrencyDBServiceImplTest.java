package com.quid.currencyconverter.dbservice;

import static org.junit.jupiter.api.Assertions.*;

import com.quid.currencyconverter.config.ApplicationConfig;
import com.quid.currencyconverter.jpa.CurrencyJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=ApplicationConfig.class)
class CurrencyDBServiceImplTest {
    @Autowired
    private CurrencyDBService dbService;

//    @BeforeEach
//    void setUp() {
//        this d
//    }

//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void shouldSave() {
        CurrencyJPA currency = new CurrencyJPA("USD", "AUD", new BigDecimal("1.4300"));

        dbService.save(currency);

        CurrencyJPA currencyJPA = dbService.readByCurrencies("USD", "AUD");
        assertEquals(currency, currencyJPA);
        System.out.println(currencyJPA);
        System.out.println(currency);

        dbService.delete(currencyJPA);
    }

    @Test
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

    @Test
    void shouldRead() {
        CurrencyJPA currencyJPA = dbService.read(4L);
        assertNotNull(currencyJPA);
        assertNotNull(currencyJPA.getFromCurrency());
        assertNotNull(currencyJPA.getToCurrency());
        assertNotNull(currencyJPA.getRate());
        System.out.println(currencyJPA);
    }

    @Test
    void shouldReadByCurrencies() {
        CurrencyJPA currencyJPA = dbService.readByCurrencies("EUR", "BYN");
        assertNotNull(currencyJPA);
        assertNotNull(currencyJPA.getFromCurrency());
        assertNotNull(currencyJPA.getToCurrency());
        assertNotNull(currencyJPA.getRate());
        System.out.println(currencyJPA);
    }

    @Test
    void shouldReadAll() {
        List<CurrencyJPA> currencyJPAList = dbService.readAll();
        assertTrue(currencyJPAList.size() > 0);
        for (CurrencyJPA currency : currencyJPAList) {
            System.out.println(currency);
        }
    }

//    @org.junit.jupiter.api.Test
//    void shutdown() {
//    }
}