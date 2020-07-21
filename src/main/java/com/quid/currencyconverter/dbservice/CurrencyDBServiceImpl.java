package com.quid.currencyconverter.dbservice;

import com.quid.currencyconverter.dao.CurrencyDAO;
import com.quid.currencyconverter.jpa.CurrencyJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dbService")
public class CurrencyDBServiceImpl implements CurrencyDBService {

    @Autowired
    private CurrencyDAO currencyDAO;

    @Override
    public void save(CurrencyJPA currencyJPA) {
        currencyDAO.save(currencyJPA);
    }

    @Override
    public void delete(CurrencyJPA currencyJPA) {
        currencyDAO.delete(currencyJPA);
    }

    @Override
    public void update(CurrencyJPA currencyJPA) {
        currencyDAO.update(currencyJPA);
    }

    @Override
    public CurrencyJPA read(long id){
        return currencyDAO.read(id);
    }

    @Override
    public CurrencyJPA readByCurrencies(String fromCurr, String toCurr) {
        return currencyDAO.readByCurrencies(fromCurr, toCurr);
    }

    @Override
    public List<CurrencyJPA> readAll() {
        return currencyDAO.readAll();
    }

    @Override
    public void shutdown() {
        currencyDAO.shutdown();
    }
}
