package dbService.dao;

import base.dataSets.CurrencyDataSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CurrencyDAO {
    private Session session;

    public CurrencyDAO(Session session){
        this.session = session;
    }

    public CurrencyDataSet read(long id){
        return session.load(CurrencyDataSet.class, id); // should i cast type?
    }

    public CurrencyDataSet readByCurrencies(String fromCurr, String toCurr) {
        Criteria criteria = session.createCriteria(CurrencyDataSet.class);
        return (CurrencyDataSet) criteria
                .add(Restrictions.eq("fromCurrency", fromCurr))
                .add(Restrictions.eq("toCurrency", toCurr))
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<CurrencyDataSet> readAll() {
        Criteria criteria = session.createCriteria(CurrencyDataSet.class);
        return (List<CurrencyDataSet>) criteria.list();
    }

    public void save(CurrencyDataSet dataSet){
        session.save(dataSet);
    }

    public void update(CurrencyDataSet dataSet) {
        session.update(dataSet);
    }

    public void delete(CurrencyDataSet dataSet) {
        session.delete(dataSet);
    }

    public void closeSession() {
        session.close();
    }
}
