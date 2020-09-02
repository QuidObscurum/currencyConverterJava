package com.quid.currencyconverter.dbservice;

import com.quid.currencyconverter.config.ApplicationConfig;
import com.quid.currencyconverter.dao.CurrencyRepository;
import com.quid.currencyconverter.domain.jpa.CurrencyJPA;
import com.quid.currencyconverter.testConfig.HibernateConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.quid.currencyconverter.domain.enums.CurrencyCode.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, HibernateConfig.class})
public class CurrencyDBServiceImplTest {
    @Autowired
    private CurrencyDBService dbService;

    @Autowired
    private CurrencyRepository currencyRepository;

    CurrencyJPA setUpCurrencyJPA;

    @Before
    public void setUp() {
        setUpCurrencyJPA = dbService.save(new CurrencyJPA(USD.toString(), "AUD", new BigDecimal("1.4300")));
    }

    @After
    public void tearDown() {
        currencyRepository.customDeleteByToCurrency("AUD");
    }

    @Test
    public void shouldSave() {

        // given
        long initialCount = currencyRepository.count();
        System.out.println(initialCount);
        CurrencyJPA currency = new CurrencyJPA(USD.toString(), "AUD", new BigDecimal("1.4300"));

        // when
        CurrencyJPA savedCurrency = dbService.save(currency);

        // then
        assertThat(currency).isEqualToIgnoringGivenFields(savedCurrency, "id");
        long finalCount = currencyRepository.count();
        System.out.println(finalCount);
        assertThat(finalCount).isGreaterThan(initialCount);
        System.out.println(savedCurrency);
    }

    @Test
    public void shouldUpdate() {

        // given
        BigDecimal oldRate = setUpCurrencyJPA.getRate();
        Long oldId = setUpCurrencyJPA.getId();
        List<CurrencyJPA> all1 = dbService.readAll();
        all1.forEach(System.out::println);
        long initialCount = currencyRepository.count();

        // when
        setUpCurrencyJPA.setRate(oldRate.add(new BigDecimal("5")));
        CurrencyJPA updatedCurrencyJPA = dbService.update(setUpCurrencyJPA);
        System.out.println(updatedCurrencyJPA);

        // then
        dbService.readAll().forEach(System.out::println);
        assertThat(updatedCurrencyJPA.getId()).isEqualTo(oldId);
        assertThat(updatedCurrencyJPA.getRate()).isGreaterThan(oldRate);
        assertThat(currencyRepository.count()).isEqualTo(initialCount);
        assertThat(dbService.read(updatedCurrencyJPA.getId()))
                .isEqualToIgnoringGivenFields(setUpCurrencyJPA,"rate");
//        assertThat(updatedCurrencyJPA).isEqualToIgnoringGivenFields(setUpCurrencyJPA,"rate");
//        raised error
//        org.assertj.core.util.introspection.IntrospectionError:
//        Can't find any field or property with name '$$_hibernate_interceptor'.
//        Error when introspecting properties was :
//        - No getter for property '$$_hibernate_interceptor' in com.quid.currencyconverter.domain.jpa.CurrencyJPA
//        Error when introspecting fields was :
//        - Unable to obtain the value of the field <'$$_hibernate_interceptor'> from <CurrencyJPA{id=120, fromCurrency='USD', toCurrency='AUD', rate=6.4300}>
    }

    @Test
    public void shouldRead() {
        CurrencyJPA currencyJPA = dbService.read(setUpCurrencyJPA.getId());
        assertThat(currencyJPA).isNotNull();
        assertThat(currencyJPA).hasNoNullFieldsOrProperties();
        System.out.println(currencyJPA);
    }

    @Test
    public void shouldReadByCurrencies() {
        CurrencyJPA currencyJPA = dbService.readByCurrencies(EUR.toString(), BYN.toString());
        assertThat(currencyJPA).isNotNull();
        assertThat(currencyJPA).hasNoNullFieldsOrProperties();
        System.out.println(currencyJPA);
    }

    @Test
    public void shouldReadAll() {
        List<CurrencyJPA> currencyJPAList = dbService.readAll();
        assertThat(currencyJPAList).isNotEmpty();
        currencyJPAList.forEach(System.out::println);
    }

    @Test
    public void shouldDelete() {
        //given
        long initialCount = currencyRepository.count();
        System.out.println(initialCount);
        CurrencyJPA currencyJPA = dbService.read(setUpCurrencyJPA.getId());

        // when
        dbService.delete(currencyJPA);

        // then
        assertThat(currencyRepository.count()).isLessThan(initialCount);
        CurrencyJPA deletedCurrencyJPA = dbService.read(setUpCurrencyJPA.getId());
        System.out.println(deletedCurrencyJPA);
    }

    @Test
    public void shouldDeleteByToCurrency() {
        //given
        long initialCount = currencyRepository.count();
        System.out.println(initialCount);

        // when
        dbService.deleteByToCurrency(setUpCurrencyJPA.getToCurrency());

        // then
        assertThat(currencyRepository.count()).isLessThan(initialCount);
        CurrencyJPA deletedCurrencyJPA = dbService.read(setUpCurrencyJPA.getId());
        System.out.println(deletedCurrencyJPA);
    }
}