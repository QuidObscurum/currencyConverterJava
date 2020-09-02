package com.quid.currencyconverter.dao;

import com.quid.currencyconverter.config.ApplicationConfig;
import com.quid.currencyconverter.domain.enums.CurrencyCode;
import com.quid.currencyconverter.domain.jpa.CurrencyJPA;
import com.quid.currencyconverter.testConfig.HibernateConfig;
import org.dbunit.PrepAndExpectedTestCase;
import org.dbunit.VerifyTableDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {DbUnitConfiguration.class, HibernateConfig.class, ApplicationConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CurrencyRepositoryTest_pureDbunit {

    @Autowired
    private PrepAndExpectedTestCase prepAndExpectedTestCase;

    @Autowired
    private CurrencyRepository currencyRepository;

    private final VerifyTableDefinition[] verifyTableDefinitions = new VerifyTableDefinition[]
            {new VerifyTableDefinition("banksells_ii", null)};

    @SuppressWarnings("unchecked")
    @Test
    public void testFindAll() throws Exception
    {
        List<CurrencyJPA> all = (List<CurrencyJPA>) prepAndExpectedTestCase.runTest(
                verifyTableDefinitions,
                new String[]{"/setUp/sampleData.xml"}, // prepDataFiles
                new String[]{"/setUp/sampleData.xml"}, // expectedDataFiles
                () -> {
                    // execute test steps that exercise production code
                    // e.g. call repository/DAO, call REST service

                    // assert responses or other values

                    // after this method exits, dbUnit will:
                    //  * verify configured tables
                    //  * cleanup tables as configured
                    return currencyRepository.findAll();
                }
        );
        assertThat(all).isNotEmpty();
        all.forEach(System.out::println);
    }

    @Test
    public void testDelete() throws Exception
    {
        long initialCount = (long) prepAndExpectedTestCase.runTest(
                verifyTableDefinitions,
                new String[]{"/setUp/sampleData.xml"}, // prepDataFiles
                new String[]{"/expected/sampleDataWhenDeleteByToCurrency.xml"}, // expectedDataFiles
                () -> {
                    long count = currencyRepository.count();
                    currencyRepository.customDeleteByToCurrency(CurrencyCode.RUB.toString());
                    return count;
                }
        );
        assertThat(currencyRepository.count()).isLessThan(initialCount);
        currencyRepository.findAll().forEach(System.out::println);
    }
}