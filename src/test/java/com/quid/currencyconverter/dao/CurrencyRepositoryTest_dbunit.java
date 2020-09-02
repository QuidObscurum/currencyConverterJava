package com.quid.currencyconverter.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.quid.currencyconverter.config.ApplicationConfig;
import com.quid.currencyconverter.domain.jpa.CurrencyJPA;
import com.quid.currencyconverter.testConfig.HibernateConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.quid.currencyconverter.domain.enums.CurrencyCode.BYN;
import static com.quid.currencyconverter.domain.enums.CurrencyCode.USD;
import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, HibernateConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup(value = "classpath:setUp/sampleData.xml")
public class CurrencyRepositoryTest_dbunit{
    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void shouldSave() {

        //given
        List<CurrencyJPA> all1 = currencyRepository.findAll();
        CurrencyJPA currencyJPA = new CurrencyJPA(USD.toString(), "AUD", new BigDecimal("1.4300"));

        //when
        CurrencyJPA savedCurrencyJPA = currencyRepository.save(currencyJPA);

        //then
        List<CurrencyJPA> all2 = currencyRepository.findAll();
        assertThat(all2).contains(savedCurrencyJPA);
        assertThat(all2).hasSizeGreaterThan(all1.size());
        assertThat(savedCurrencyJPA).isEqualToIgnoringGivenFields(currencyJPA, "id");
    }

    @Test
    public void shouldDeleteById() {
        Long id = 1L;
        long initialCount = currencyRepository.count();
        CurrencyJPA currencyJPA = currencyRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Did not find any record with id " + id)
        );
        currencyRepository.delete(currencyJPA);
        assertThat(currencyRepository.findById(id)).isEmpty();
        assertThat(currencyRepository.count()).isLessThan(initialCount);
    }

    @Test
    public void shouldFindAll() {
        List<CurrencyJPA> all = currencyRepository.findAll();
        all.forEach(System.out::println);
        assertThat(all).hasSizeGreaterThan(0);
        assertThat(all).doesNotContainNull();
    }

    @Test
    public void shouldFindById() {
        Optional<CurrencyJPA> currency = currencyRepository.findById(1L);
        assertThat(currency).isNotEmpty();
        assertThat(currency).get().hasNoNullFieldsOrProperties();
    }

    @Test
    public void shouldFindByFromCurrencyAndToCurrency(){
//        Interestingly, seems to ignore case by default, so ("usd", "byn") would also work
        Optional<CurrencyJPA> optionalCurrencyJPA = currencyRepository
                .findFirstByFromCurrencyAndToCurrency(USD.toString(), BYN.toString());

        assertThat(optionalCurrencyJPA).containsInstanceOf(CurrencyJPA.class);
        assertThat(optionalCurrencyJPA).get().hasNoNullFieldsOrProperties();

        Optional<CurrencyJPA> emptyCurrencyJPA = currencyRepository.findFirstByFromCurrencyAndToCurrency("ZXC", "VBN");
        assertThat(emptyCurrencyJPA).isEmpty();
    }

    @Test
    public void shouldFindByFromCurrencyAndToCurrencyIgnoreCase(){
        Optional<CurrencyJPA> currency = currencyRepository.findByFromCurrencyIgnoreCaseAndToCurrencyIgnoreCase("usd", "BYn");
        assertThat(currency).containsInstanceOf(CurrencyJPA.class);
        assertThat(currency).get().hasNoNullFieldsOrProperties();
        System.out.println(currency.orElseGet(CurrencyJPA::new));
    }

    @Test
    public void shouldFindAndUpdate() {

        // given
        Long id = 1L;
        CurrencyJPA currencyJPA = currencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Did not find any record with id " + id));
        CurrencyJPA copyOldCurrencyJPA = new CurrencyJPA(
                currencyJPA.getId(),
                currencyJPA.getFromCurrency(),
                currencyJPA.getToCurrency(),
                currencyJPA.getRate()
        );
        long initCount = currencyRepository.count();

        //when
        currencyJPA.setRate(currencyJPA.getRate().add(new BigDecimal("5")));

        // then
        assertThat(currencyJPA).isNotEqualTo(copyOldCurrencyJPA);
        assertThat(currencyRepository.findById(currencyJPA.getId()))
                .contains(currencyJPA);
        assertThat(currencyRepository.count()).isEqualTo(initCount);
    }

    @Test
    public void shouldDeleteByToCurrency() {
//        Filters out relevant entities and deletes them one by one by "id"
        long initialCount = currencyRepository.count();
        currencyRepository.deleteByToCurrency(BYN.toString());
        long count = currencyRepository.count();
        assertThat(count).isLessThan(initialCount);
        assertThat(count).isEqualTo(initialCount - 3);
    }

    @Test
    public void shouldCustomDeleteByToCurrency() {
//        Deletes all records that conform with the filter at once
        long initialCount = currencyRepository.count();
        currencyRepository.customDeleteByToCurrency(BYN.toString());
        assertThat(currencyRepository.count()).isLessThan(initialCount);
    }
}
