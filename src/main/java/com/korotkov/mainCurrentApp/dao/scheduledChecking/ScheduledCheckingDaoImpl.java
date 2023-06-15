package com.korotkov.mainCurrentApp.dao.scheduledChecking;


import com.korotkov.mainCurrentApp.enums.ScheduledCheckingStatusEnum;
import com.korotkov.mainCurrentApp.model.ScheduledChecking;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ScheduledCheckingDaoImpl implements ScheduledCheckingDao {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(ScheduledChecking scheduledChecking) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(scheduledChecking);
    }

    @Override
    public void update(ScheduledChecking scheduledChecking) {
        Session session = sessionFactory.getCurrentSession();
        session.update(scheduledChecking);
    }

    @Override
    public ScheduledChecking getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(ScheduledChecking.class, id);
    }

    @Override
    public Long getCountAllScheduledChecking() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(sc.id) from ScheduledChecking sc")
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScheduledChecking> getAllScheduledChecking(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sc from ScheduledChecking sc order by sc.id desc")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScheduledChecking> getScheduledCheckingForDates(LocalDate dateFrom, LocalDate dateTo, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sc from ScheduledChecking sc where date(sc.startedAt) >= :dateFrom and date(sc.startedAt) <= :dateTo order by sc.id desc")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountScheduledCheckingForDates(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(sc.id) from ScheduledChecking sc where date(sc.startedAt) >= :dateFrom and date(sc.startedAt) <= :dateTo")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScheduledChecking> getScheduledCheckingForDateFrom(LocalDate dateFrom, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sc from ScheduledChecking sc where date(sc.startedAt) >= :dateFrom order by sc.id desc")
                .setParameter("dateFrom", dateFrom)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountScheduledCheckingForDateFrom(LocalDate dateFrom) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(sc.id) from ScheduledChecking sc where date(sc.startedAt) >= :dateFrom")
                .setParameter("dateFrom", dateFrom)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScheduledChecking> getScheduledCheckingForDateTo(LocalDate dateTo, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sc from ScheduledChecking sc where date(sc.startedAt) <= :dateTo order by sc.id desc")
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountScheduledCheckingForDateTo(LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(sc.id) from ScheduledChecking sc where date(sc.startedAt) <= :dateTo")
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

    @Override
    public ScheduledChecking getUniqueNotFinishedScheduledChecking(String titleScheduledChecking) {
        Session session = sessionFactory.getCurrentSession();
        return (ScheduledChecking) session.createQuery("select sc from ScheduledChecking sc where sc.status = :status and sc.title = :titleScheduledChecking order by sc.id desc")
                .setParameter("status", String.valueOf(ScheduledCheckingStatusEnum.STARTED))
                .setParameter("titleScheduledChecking", titleScheduledChecking)
                .setMaxResults(1).uniqueResult();
    }
}
