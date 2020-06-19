package dbService;

import base.DBService;
import base.dataSets.CurrencyDataSet;
import dbService.dao.CurrencyDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DBServiceImpl implements DBService {
    private SessionFactory sessionFactory; // static?

    public DBServiceImpl() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(CurrencyDataSet.class);
        try {
            sessionFactory = configuration.configure().buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("Failed to create sessionFactory object." + e);
            throw new ExceptionInInitializerError(e);
        }
//        configuration.setProperty()
    }

    private CurrencyDAO startSession() {
        Session session = sessionFactory.openSession();
        return new CurrencyDAO(session);
    }

    public void save(CurrencyDataSet dataSet) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CurrencyDAO dao = new CurrencyDAO(session);
        dao.save(dataSet);
        transaction.commit();
        session.close();
    }

    public void update(CurrencyDataSet dataSet) {
        Session session = sessionFactory.openSession();
        CurrencyDAO dao = new CurrencyDAO(session);
        Transaction t = session.beginTransaction();
        dao.update(dataSet);
        t.commit();
        session.close();
    }

    public CurrencyDataSet read(long id){
        Session session = sessionFactory.openSession();
        CurrencyDAO dao = new CurrencyDAO(session);
        return dao.read(id);
    }

    public CurrencyDataSet readByCurrencies(String fromCurr, String toCurr) {
        Session session = sessionFactory.openSession();
        CurrencyDAO dao = new CurrencyDAO(session);
        return dao.readByCurrencies(fromCurr, toCurr);
    }

    public List<CurrencyDataSet> readAll() {
        Session session = sessionFactory.openSession();
        CurrencyDAO dao = new CurrencyDAO(session);
        return dao.readAll();
    }

//    @Override
    public void shutdown() {
        sessionFactory.close(); //???
    }
}
