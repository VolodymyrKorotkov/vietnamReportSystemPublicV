package com.korotkov.mainCurrentApp.dao.uploadingClientPhones;

import com.korotkov.mainCurrentApp.model.UploadingClientPhones;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class UploadingClientPhonesDAOImpl implements UploadingClientPhonesDAO {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(UploadingClientPhones uploadingClientPhones) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(uploadingClientPhones);
    }

    @Override
    public void update(UploadingClientPhones uploadingClientPhones) {
        Session session = sessionFactory.getCurrentSession();
        session.update(uploadingClientPhones);
    }

    @Override
    public UploadingClientPhones getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(UploadingClientPhones.class, id);
    }

    @Override
    public Long getCountAllUploadingClientPhones() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(a.id) from UploadingClientPhones a")
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UploadingClientPhones> getAllUploadingClientPhones(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select a from UploadingClientPhones a order by a.id desc")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountUploadingClientPhonesForDates(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count (a.id) from UploadingClientPhones a where date(a.startedAt) >= :dateFrom and date(a.startedAt) <= :dateTo")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UploadingClientPhones> getUploadingClientPhonesForDates(LocalDate dateFrom, LocalDate dateTo, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select a from UploadingClientPhones a where date(a.startedAt) >= :dateFrom and date(a.startedAt) <= :dateTo order by a.id desc")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountUploadingClientPhonesForDateFrom(LocalDate dateFrom) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count (a.id) from UploadingClientPhones a where date(a.startedAt) >= :dateFrom")
                .setParameter("dateFrom", dateFrom)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UploadingClientPhones> getUploadingClientPhonesForDateFrom(LocalDate dateFrom, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select a from UploadingClientPhones a where date(a.startedAt) >= :dateFrom order by a.id desc")
                .setParameter("dateFrom", dateFrom)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountUploadingClientPhonesForDateTo(LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count (a.id) from UploadingClientPhones a where date(a.startedAt) <= :dateTo")
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UploadingClientPhones> getUploadingClientPhonesForDateTo(LocalDate dateTo, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select a from UploadingClientPhones a where date(a.startedAt) <= :dateTo order by a.id desc")
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }
}
