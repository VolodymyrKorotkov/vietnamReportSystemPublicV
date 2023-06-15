package com.korotkov.mainCurrentApp.dao.currencyRate;

import com.korotkov.mainCurrentApp.dao.currencyRate.CurrencyRateDao;
import com.korotkov.mainCurrentApp.model.CurrencyRate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CurrencyRateDaoImpl implements CurrencyRateDao {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(CurrencyRate currencyRate) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(currencyRate);
    }

    @Override
    public void update(CurrencyRate currencyRate) {
        Session session = sessionFactory.getCurrentSession();
        session.update(currencyRate);
    }

    @Override
    public CurrencyRate getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(CurrencyRate.class,id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CurrencyRate> getAllCurrencyRates(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select cr from CurrencyRate cr order by cr.date desc")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountAllCurrencyRates() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(cr.id) from CurrencyRate cr")
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CurrencyRate> getCurrencyRateForDates(LocalDate dateFrom, LocalDate dateTo, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select cr from CurrencyRate cr where cr.date >= :dateFrom and cr.date <= :dateTo order by cr.date desc")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long getCountCurrencyRateForDates(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(cr.id) from CurrencyRate cr where cr.date >= :dateFrom and cr.date <= :dateTo")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long getCountCurrencyRateForDateFrom(LocalDate dateFrom) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(cr.id) from CurrencyRate cr where cr.date >= :dateFrom")
                .setParameter("dateFrom", dateFrom)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long getCountCurrencyRateForDateTo(LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(cr.id) from CurrencyRate cr where cr.date <= :dateTo")
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CurrencyRate> getCurrencyRateForDateFrom(LocalDate dateFrom, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select cr from CurrencyRate cr where cr.date >= :dateFrom order by cr.date desc")
                .setParameter("dateFrom", dateFrom)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CurrencyRate> getCurrencyRateForDateTo(LocalDate dateTo, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select cr from CurrencyRate cr where cr.date <= :dateTo order by cr.date desc")
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CurrencyRate> getCurrencyRatesWithDates(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select cr from CurrencyRate cr where cr.date >= :dateFrom and cr.date <= :dateTo order by cr.date desc")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .list();
    }

    @Override
    public CurrencyRate getCurrencyRateByDate(LocalDate date) {
        Session session = sessionFactory.getCurrentSession();
        return (CurrencyRate) session.createQuery("select cr from CurrencyRate cr where cr.date = :date")
                .setParameter("date", date)
                .uniqueResult();
    }

    @Override
    public CurrencyRate getLastCurrencyRate() {
        Session session = sessionFactory.getCurrentSession();
        return (CurrencyRate) session.createQuery("select cr from CurrencyRate cr order by cr.date desc")
                .setMaxResults(1).uniqueResult();
    }

}
