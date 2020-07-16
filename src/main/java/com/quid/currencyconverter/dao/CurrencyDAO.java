package com.quid.currencyconverter.dao;

import com.quid.currencyconverter.jpa.CurrencyJPA;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CurrencyDAO {

    public CurrencyJPA read(Session session, long id){
        return session.get(CurrencyJPA.class, id);
    }

    public CurrencyJPA readByCurrencies(Session session, String fromCurr, String toCurr) {
        Criteria criteria = session.createCriteria(CurrencyJPA.class);
        return (CurrencyJPA) criteria
                .add(Restrictions.eq("fromCurrency", fromCurr))
                .add(Restrictions.eq("toCurrency", toCurr))
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<CurrencyJPA> readAll(Session session) {
        Criteria criteria = session.createCriteria(CurrencyJPA.class);
        return (List<CurrencyJPA>) criteria.list();
    }

    public void save(Session session, CurrencyJPA dataSet){
        session.save(dataSet);
    }

    public void update(Session session, CurrencyJPA dataSet) {
        session.update(dataSet);
    }

    public void delete(Session session, CurrencyJPA dataSet) {
        session.delete(dataSet);
    }

}
