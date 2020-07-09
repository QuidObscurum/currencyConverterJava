package com.quid.currencyconverter.dbService;

import com.quid.currencyconverter.jpa.CurrencyJPA;
import com.quid.currencyconverter.dao.CurrencyDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DBServiceImpl implements DBService {
    static SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(CurrencyJPA.class);
        try {
            sessionFactory = configuration
//                    .configure() // expects cfg.xml
                    .buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("Failed to create sessionFactory object." + e);
            throw new ExceptionInInitializerError(e);
        }
//        configuration.setProperty()
    }

    @Override
    public void save(CurrencyJPA dataSet) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            CurrencyDAO dao = new CurrencyDAO(session);
//            Oracle documentation:
//            In a try-with-resources statement, any catch or finally block
//            is run after the resources declared have been closed.
//            => in case of Exception, rollback before session is closed
            try {
                dao.save(dataSet);
                transaction.commit();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                transaction.rollback();
            }
        }
    }

    @Override
    public void update(CurrencyJPA dataSet) {
        try (Session session = sessionFactory.openSession()){
            CurrencyDAO dao = new CurrencyDAO(session);
            Transaction t = session.beginTransaction();
            try {
                dao.update(dataSet);
                t.commit();
            } catch (Exception e){
                System.err.println(e.getMessage());
                t.rollback();
            }
        }
    }

    @Override
    public CurrencyJPA read(long id){
        Session session = sessionFactory.openSession();
        CurrencyDAO dao = new CurrencyDAO(session);
        return dao.read(id);
    }

    @Override
    public CurrencyJPA readByCurrencies(String fromCurr, String toCurr) {
        Session session = sessionFactory.openSession();
        CurrencyDAO dao = new CurrencyDAO(session);
        return dao.readByCurrencies(fromCurr, toCurr);
    }

    @Override
    public List<CurrencyJPA> readAll() {
        Session session = sessionFactory.openSession();
        CurrencyDAO dao = new CurrencyDAO(session);
        return dao.readAll();
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }
}
