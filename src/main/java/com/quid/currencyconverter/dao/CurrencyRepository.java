package com.quid.currencyconverter.dao;

import com.quid.currencyconverter.jpa.CurrencyJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface CurrencyRepository extends JpaRepository<CurrencyJPA, Long> {

    Optional<CurrencyJPA> findFirstByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);

    Optional<CurrencyJPA> findByFromCurrencyIgnoreCaseAndToCurrencyIgnoreCase(String fromCurrency, String toCurrency);

    void deleteByToCurrency(String toCurrency);

    @Transactional
    @Modifying
    @Query("delete from CurrencyJPA c where c.toCurrency = :toCurrency")
    void customDeleteByToCurrency(@Param("toCurrency") String toCurrency);

}
