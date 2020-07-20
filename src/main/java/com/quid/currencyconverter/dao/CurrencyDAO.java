package com.quid.currencyconverter.dao;

import com.quid.currencyconverter.jpa.CurrencyJPA;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class CurrencyDAO {
    private SessionFactory sessionFactory;
    private HibernateTemplate hibernateTemplate;

    @Autowired
    public CurrencyDAO(LocalSessionFactoryBean sessionFactoryBean) {
        this.sessionFactory = sessionFactoryBean.getObject();
        this.hibernateTemplate = new HibernateTemplate(this.sessionFactory);
    }

    public CurrencyJPA read(long id){
        return hibernateTemplate.get(CurrencyJPA.class, id);
    }

    @SuppressWarnings("unchecked")
    public CurrencyJPA readByCurrencies(String fromCurr, String toCurr) {
        List<CurrencyJPA> jpaList = (List<CurrencyJPA>) hibernateTemplate
                .findByCriteria(
                        DetachedCriteria.forClass(CurrencyJPA.class)
                                .add(Restrictions.eq("fromCurrency", fromCurr))
                                .add(Restrictions.eq("toCurrency", toCurr))
                );
        if (jpaList.size() > 0) {
            return jpaList.get(0);
        }
        // Raise Exception?
        return new CurrencyJPA();
    }

    @SuppressWarnings("unchecked")
    public List<CurrencyJPA> readAll() {
        return (List<CurrencyJPA>) hibernateTemplate
                .findByCriteria(
                        DetachedCriteria.forClass(CurrencyJPA.class)
                );
    }

    @Transactional
    public void save(CurrencyJPA currencyJPA) {
        hibernateTemplate.save(currencyJPA);
    }

    @Transactional
    public void update(CurrencyJPA currencyJPA) {
        hibernateTemplate.update(currencyJPA);
    }

    @Transactional // works even without the annotation
    public void delete(CurrencyJPA currencyJPA) {
        try {
            hibernateTemplate.delete(currencyJPA);
//        } catch (DataAccessException e) {
        } catch (Exception e) { // Why doesn't it catch? org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException
            System.err.printf("No record of object %s found%n", currencyJPA.toString());
            e.printStackTrace();
        }
    }

    public void shutdown() {
        sessionFactory.close();
    }
}
