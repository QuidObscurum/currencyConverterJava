package com.quid.currencyconverter.dao;

import com.quid.currencyconverter.jpa.CurrencyJPA;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CurrencyDAO {
    private final Session session;

    public CurrencyDAO(Session session){
        this.session = session;
    }

    public CurrencyJPA read(long id){
        CurrencyJPA currency = session.get(CurrencyJPA.class, id);
        session.close();
        return currency;
    }

    public CurrencyJPA readByCurrencies(String fromCurr, String toCurr) {
        Criteria criteria = session.createCriteria(CurrencyJPA.class);
        CurrencyJPA currency = (CurrencyJPA) criteria
                .add(Restrictions.eq("fromCurrency", fromCurr))
                .add(Restrictions.eq("toCurrency", toCurr))
                .uniqueResult();
        session.close();
        return currency;
    }

    @SuppressWarnings("unchecked")
    public List<CurrencyJPA> readAll() {
        Criteria criteria = session.createCriteria(CurrencyJPA.class);
        List<CurrencyJPA> currencyList = criteria.list();
        session.close();
        return currencyList;
    }

    public void save(CurrencyJPA dataSet){
        session.save(dataSet);
    }

    public void update(CurrencyJPA dataSet) {
        session.update(dataSet);
    }

    public void delete(CurrencyJPA dataSet) {
        session.delete(dataSet);
    }

    public void closeSession() {
        session.close();
    }
}
