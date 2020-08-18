package com.quid.currencyconverter.dbservice;

import com.quid.currencyconverter.dao.CurrencyRepository;
import com.quid.currencyconverter.jpa.CurrencyJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("dbService")
public class CurrencyDBServiceImpl implements CurrencyDBService {

    private final CurrencyRepository currencyRepository;

    private static final Logger logger = LoggerFactory.getLogger(CurrencyDBServiceImpl.class);

    public CurrencyDBServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    @Transactional
    public CurrencyJPA save(CurrencyJPA currencyJPA) {
        return currencyRepository.save(currencyJPA);
    }

    @Override
    @Transactional
    public void delete(CurrencyJPA currencyJPA) {
        currencyRepository.delete(currencyJPA);
    }

    @Override
    @Transactional
    public CurrencyJPA update(CurrencyJPA newCurrencyJPA) {
        CurrencyJPA currencyJPA = currencyRepository.getOne(newCurrencyJPA.getId());
        try {
            currencyJPA.setFromCurrency(newCurrencyJPA.getFromCurrency());
            currencyJPA.setToCurrency(newCurrencyJPA.getToCurrency());
            currencyJPA.setRate(newCurrencyJPA.getRate());
        } catch (EntityNotFoundException e) {
            logger.warn("Could not update record with id {}.", newCurrencyJPA.getId(), e);
        }
        return currencyJPA;
    }

    @Override
    public CurrencyJPA read(long id){
        return currencyRepository.findById(id).orElseGet(CurrencyJPA::new);
    }

    @Override
    public CurrencyJPA readByCurrencies(String fromCurr, String toCurr) {
        return currencyRepository.findFirstByFromCurrencyAndToCurrency(fromCurr, toCurr).orElseGet(CurrencyJPA::new);
    }

    @Override
    public List<CurrencyJPA> readAll() {
        return currencyRepository.findAll();
    }
}
