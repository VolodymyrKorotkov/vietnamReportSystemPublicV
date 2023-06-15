package com.korotkov.mainCurrentApp.dao.report;

import com.korotkov.mainCurrentApp.model.Report;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportDaoImpl implements ReportDao{
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Report report) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(report);
    }

    @Override
    public void update(Report report) {
        Session session = sessionFactory.getCurrentSession();
        session.update(report);
    }

    @Override
    public Report getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Report.class, id);
    }

    @Override
    public Long getCountAllReports() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(r.id) from Report r")
                .uniqueResult();
    }

    @Override
    public Long getCountReportsWithFilterName(String reportName) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(r.id) from Report r where r.reportName like :reportName")
                .setParameter("reportName", "%" + reportName + "%")
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Report> getAllReports(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select r from Report r")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Report> getReportsWithFilterName(int page, String reportName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select r from Report r where r.reportName = :reportName")
                .setParameter("reportName", "%" + reportName + "%")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }
}
