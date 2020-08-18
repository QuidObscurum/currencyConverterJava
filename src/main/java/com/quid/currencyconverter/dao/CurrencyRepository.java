package com.quid.currencyconverter.dao;

import com.quid.currencyconverter.jpa.CurrencyJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface CurrencyRepository extends JpaRepository<CurrencyJPA, Long> {

    Optional<CurrencyJPA> findFirstByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);

    Optional<CurrencyJPA> findByFromCurrencyIgnoreCaseAndToCurrencyIgnoreCase(String fromCurrency, String toCurrency);

    @Modifying
    @Transactional
    void deleteByToCurrency(String toCurrency);

}
