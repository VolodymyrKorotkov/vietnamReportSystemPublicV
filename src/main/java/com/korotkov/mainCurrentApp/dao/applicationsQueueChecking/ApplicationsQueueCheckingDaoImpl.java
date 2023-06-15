package com.korotkov.mainCurrentApp.dao.applicationsQueueChecking;

import com.korotkov.mainCurrentApp.model.ApplicationsQueueChecking;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ApplicationsQueueCheckingDaoImpl implements ApplicationsQueueCheckingDao {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(ApplicationsQueueChecking applicationsQueueChecking) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(applicationsQueueChecking);
    }

    @Override
    public void update(ApplicationsQueueChecking applicationsQueueChecking) {
        Session session = sessionFactory.getCurrentSession();
        session.update(applicationsQueueChecking);
    }

    @Override
    public ApplicationsQueueChecking getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(ApplicationsQueueChecking.class, id);
    }

    @Override
    public Long getCountAllAppsQueueChecking() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(aqc.id) from ApplicationsQueueChecking aqc")
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ApplicationsQueueChecking> getAllAppsQueueChecking(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select aqc from ApplicationsQueueChecking aqc order by aqc.id desc")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ApplicationsQueueChecking> getAppsQueueCheckingForDates(LocalDate dateFrom, LocalDate dateTo,
                                                                        int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select aqc from ApplicationsQueueChecking aqc where date(aqc.dateTime) >= :dateFrom and date(aqc.dateTime) <= :dateTo order by aqc.id asc")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountAppsQueueCheckingForDates(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(aqc.id) from ApplicationsQueueChecking aqc where date(aqc.dateTime) >= :dateFrom and date(aqc.dateTime) <= :dateTo")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ApplicationsQueueChecking> getAppsQueueCheckingForDateFrom(LocalDate dateFrom, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select aqc from ApplicationsQueueChecking aqc where date(aqc.dateTime) >= :dateFrom order by aqc.id asc")
                .setParameter("dateFrom", dateFrom)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountAppsQueueCheckingForDateFrom(LocalDate dateFrom) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(aqc.id) from ApplicationsQueueChecking aqc where date(aqc.dateTime) >= :dateFrom")
                .setParameter("dateFrom", dateFrom)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ApplicationsQueueChecking> getAppsQueueCheckingForDateTo(LocalDate dateTo, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select aqc from ApplicationsQueueChecking aqc where date(aqc.dateTime) <= :dateTo order by aqc.id asc")
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountAppsQueueCheckingForDateTo(LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(aqc.id) from ApplicationsQueueChecking aqc where date(aqc.dateTime) <= :dateTo")
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }
}
