package com.quid.currencyconverter.dbService;

import com.quid.currencyconverter.jpa.CurrencyJPA;
import com.quid.currencyconverter.dao.CurrencyDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dbService")
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

    private CurrencyDAO currencyDAO;

    public DBServiceImpl(CurrencyDAO currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    @Override
    public void save(CurrencyJPA currencyJPA) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
//            Oracle documentation:
//            In a try-with-resources statement, any catch or finally block
//            is run after the resources declared have been closed.
//            => in case of Exception, rollback before session is closed
            try {
                currencyDAO.save(session, currencyJPA);
                transaction.commit();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                transaction.rollback();
            }
        }
    }

    @Override
    public void update(CurrencyJPA currencyJPA) {
        try (Session session = sessionFactory.openSession()){
            Transaction t = session.beginTransaction();
            try {
                currencyDAO.update(session, currencyJPA);
                t.commit();
            } catch (Exception e){
                System.err.println(e.getMessage());
                t.rollback();
            }
        }
    }

    @Override
    public CurrencyJPA read(long id){
        try (Session session = sessionFactory.openSession()) {
            return currencyDAO.read(session, id);
        }
    }

    @Override
    public CurrencyJPA readByCurrencies(String fromCurr, String toCurr) {
        try (Session session = sessionFactory.openSession()) {
            return currencyDAO.readByCurrencies(session, fromCurr, toCurr);
        }
    }

    @Override
    public List<CurrencyJPA> readAll() {
        try (Session session = sessionFactory.openSession())
        {
            return currencyDAO.readAll(session);
        }
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }
}
