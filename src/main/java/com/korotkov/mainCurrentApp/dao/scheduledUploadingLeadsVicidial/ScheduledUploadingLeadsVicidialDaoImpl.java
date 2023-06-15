package com.korotkov.mainCurrentApp.dao.scheduledUploadingLeadsVicidial;

import com.korotkov.mainCurrentApp.model.ScheduledUploadingLeadsVicidial;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ScheduledUploadingLeadsVicidialDaoImpl implements ScheduledUploadingLeadsVicidialDao {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(ScheduledUploadingLeadsVicidial leadsVicidial) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(leadsVicidial);
    }

    @Override
    public void update(ScheduledUploadingLeadsVicidial leadsVicidial) {
        Session session = sessionFactory.getCurrentSession();
        session.update(leadsVicidial);
    }

    @Override
    public ScheduledUploadingLeadsVicidial getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(ScheduledUploadingLeadsVicidial.class, id);
    }

    @Override
    public ScheduledUploadingLeadsVicidial getLastCreatedAndNotFinished(String name,
                                                                        LocalDateTime startedExportAt,
                                                                        String status) {
        Session session = sessionFactory.getCurrentSession();
        return (ScheduledUploadingLeadsVicidial) session.createQuery("select sulv from ScheduledUploadingLeadsVicidial sulv where sulv.uploadingName = :name and sulv.startedExportAt = :startedExportAt and :status order by sulv.id desc")
                .setMaxResults(1).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScheduledUploadingLeadsVicidial> getAllScheduledUploadingLeadsVicidialList(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select a from ScheduledUploadingLeadsVicidial a order by a.id desc")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountAllScheduledUploadingLeadsVicidial() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(a.id) from ScheduledUploadingLeadsVicidial a")
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScheduledUploadingLeadsVicidial> getScheduledUploadingLeadsVicidialListForDates(LocalDate dateFrom, LocalDate dateTo, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select a from ScheduledUploadingLeadsVicidial a where date(a.startedExportAt) between :dateFrom and :dateTo order by a.id desc")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountScheduledUploadingLeadsVicidialForDates(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(a.id) from ScheduledUploadingLeadsVicidial a where date(a.startedExportAt) between :dateFrom and :dateTo")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScheduledUploadingLeadsVicidial> getScheduledUploadingLeadsVicidialListForDateFrom(LocalDate dateFrom, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select a from ScheduledUploadingLeadsVicidial a where date(a.startedExportAt) >= :dateFrom order by a.id desc")
                .setParameter("dateFrom", dateFrom)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountScheduledUploadingLeadsVicidialForDateFrom(LocalDate dateFrom) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(a.id) from ScheduledUploadingLeadsVicidial a where date(a.startedExportAt) >= :dateFrom")
                .setParameter("dateFrom", dateFrom)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScheduledUploadingLeadsVicidial> getScheduledUploadingLeadsVicidialListForDateTo(LocalDate dateTo, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select a from ScheduledUploadingLeadsVicidial a where date(a.startedExportAt) <= :dateTo order by a.id desc")
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountScheduledUploadingLeadsVicidialForDateTo(LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(a.id) from ScheduledUploadingLeadsVicidial a where date(a.startedExportAt) <= :dateTo")
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

}
