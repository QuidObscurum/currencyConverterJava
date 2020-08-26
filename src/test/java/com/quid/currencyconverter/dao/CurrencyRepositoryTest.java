package com.quid.currencyconverter.dao;

import com.quid.currencyconverter.config.ApplicationConfig;
import com.quid.currencyconverter.config.HibernateConfig;
import com.quid.currencyconverter.jpa.CurrencyJPA;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.quid.currencyconverter.myutils.CurrencyCode.*;
import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, HibernateConfig.class})
public class CurrencyRepositoryTest {
    @Autowired
    private CurrencyRepository currencyRepository;

    CurrencyJPA setUpCurrencyJPA;

    @Before
//    @Rollback(value = false)
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setUp() {
//        for (int i = 0; i < 3; i++) {
            setUpCurrencyJPA = currencyRepository.save(
                    new CurrencyJPA(USD.toString(), "AUD", new BigDecimal("1.4300")
//                            .add(new BigDecimal(i))
                    )
            );
//        }
    }

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
        currencyRepository.delete(setUpCurrencyJPA);
        Optional<CurrencyJPA> deletedCurrencyJPA = currencyRepository.findById(setUpCurrencyJPA.getId());
        assertThat(deletedCurrencyJPA).isEmpty();
    }

    @Test
//    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public void shouldFindAll() {
        List<CurrencyJPA> all = currencyRepository.findAll();
        all.forEach(System.out::println);
        assertThat(all).hasSizeGreaterThan(0);
        assertThat(all).doesNotContainNull();
    }

    @Test
//    @Transactional(readOnly = true)
    public void shouldFindById() {
        Optional<CurrencyJPA> currency = currencyRepository.findById(1L); // setUpCurrencyJPA.getId()
        assertThat(currency).isNotEmpty();
        assertThat(currency).get().hasNoNullFieldsOrProperties();
    }

    @Test
//    @Transactional(readOnly = true)
    public void shouldFindByFromCurrencyAndToCurrency(){
//        Interestingly, seems to ignore case by default, so ("usd", "byn") would also work
        Optional<CurrencyJPA> optionalCurrencyJPA = currencyRepository
                .findFirstByFromCurrencyAndToCurrency(USD.toString(), BYN.toString());  // USD, AUD

        assertThat(optionalCurrencyJPA).containsInstanceOf(CurrencyJPA.class);
        assertThat(optionalCurrencyJPA).get().hasNoNullFieldsOrProperties();

        Optional<CurrencyJPA> emptyCurrencyJPA = currencyRepository.findFirstByFromCurrencyAndToCurrency("ZXC", "VBN");
        assertThat(emptyCurrencyJPA).isEmpty();
    }

    @Test
//    @Transactional(readOnly = true)
    public void shouldFindByFromCurrencyAndToCurrencyIgnoreCase(){
        Optional<CurrencyJPA> currency = currencyRepository.findByFromCurrencyIgnoreCaseAndToCurrencyIgnoreCase("usd", "BYn");
        assertThat(currency).containsInstanceOf(CurrencyJPA.class);
        assertThat(currency).get().hasNoNullFieldsOrProperties();
        System.out.println(currency.orElseGet(CurrencyJPA::new));
    }

//    @Ignore
//    public CurrencyJPA testUpdate() throws RuntimeException {
////        CurrencyJPA oldCurrencyJPA = currencyRepository.findById(saveCurrencyJPA.getId())
////                .orElse(currencyRepository
////                        // WHY SAVE IS CALLED??
////                        .save(new CurrencyJPA(USD.toString(), "AUD", new BigDecimal("1.4300"))));
////        System.out.println(currencyRepository.count());
//        Optional<CurrencyJPA> optionalCurrencyJPA = currencyRepository.findById(setUpCurrencyJPA.getId());
//        if (!optionalCurrencyJPA.isPresent()) {
//            throw new RuntimeException("Did not find any record with id " + setUpCurrencyJPA.getId());
//        }
//        CurrencyJPA currencyJPA = optionalCurrencyJPA.get();
//
////        System.out.println("Retrieved currencyJPA");
////        System.out.println(currencyJPA);
////        System.out.println(System.identityHashCode(currencyJPA));
//
//        currencyJPA.setRate(currencyJPA.getRate().add(new BigDecimal("5")));
//        // calling save method is unnecessary
//        return currencyJPA;
//    }

    @Test
    public void shouldFindAndUpdate() {

        // given
        CurrencyJPA copyOldCurrencyJPA = new CurrencyJPA(
                setUpCurrencyJPA.getId(),
                setUpCurrencyJPA.getFromCurrency(),
                setUpCurrencyJPA.getToCurrency(),
                setUpCurrencyJPA.getRate()
        );

        Optional<CurrencyJPA> optionalNewCurrencyJPA = currencyRepository.findById(setUpCurrencyJPA.getId());
        if (!optionalNewCurrencyJPA.isPresent()) {
            throw new RuntimeException("Did not find any record with id " + setUpCurrencyJPA.getId());
        }
        CurrencyJPA newCurrencyJPA = optionalNewCurrencyJPA.get();
        assertThat(newCurrencyJPA).isEqualTo(copyOldCurrencyJPA);

        long initCount = currencyRepository.count();

        //when
        newCurrencyJPA.setRate(newCurrencyJPA.getRate().add(new BigDecimal("5")));

        // then
        assertThat(newCurrencyJPA).isNotEqualTo(copyOldCurrencyJPA);
        assertThat(currencyRepository.count()).isEqualTo(initCount);
    }

    @Test
    public void shouldGetOneAndUpdate() {
        // NO DIFFERENCE FROM USING FIND
        System.out.println("setUpCurrencyJPA");
        System.out.println(setUpCurrencyJPA);
        System.out.println(System.identityHashCode(setUpCurrencyJPA));

        CurrencyJPA currencyJPA = currencyRepository.getOne(setUpCurrencyJPA.getId());
        System.out.println("Retrieved currencyJPA");
        System.out.println(currencyJPA);
        System.out.println(System.identityHashCode(currencyJPA));

        currencyJPA.setRate(setUpCurrencyJPA.getRate().add(new BigDecimal("5")));

        List<CurrencyJPA> all = currencyRepository.findAll();
        all.forEach(System.out::println);
    }

    @Test
    public void shouldDeleteByToCurrency() {
//        Filters out relevant entities and deletes them one by one by "id"
        List<CurrencyJPA> all1 = currencyRepository.findAll();
        assertThat(all1).contains(setUpCurrencyJPA);
        currencyRepository.deleteByToCurrency(setUpCurrencyJPA.getToCurrency());
        List<CurrencyJPA> all2 = currencyRepository.findAll();
        assertThat(all2).hasSizeLessThan(all1.size());
        assertThat(all2).doesNotContain(setUpCurrencyJPA);
    }

    @Test
    public void shouldCustomDeleteByToCurrency() {
//        Deletes all records that conform with the filter at once
        long initialCount = currencyRepository.count();
        currencyRepository.customDeleteByToCurrency(setUpCurrencyJPA.getToCurrency());
        assertThat(currencyRepository.count()).isLessThan(initialCount);
        assertThat(currencyRepository.findAll()).doesNotContain(setUpCurrencyJPA);
    }
}
