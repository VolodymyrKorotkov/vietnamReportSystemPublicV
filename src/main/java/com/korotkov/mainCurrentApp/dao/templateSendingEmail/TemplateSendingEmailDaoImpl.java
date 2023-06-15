package com.korotkov.mainCurrentApp.dao.templateSendingEmail;

import com.korotkov.mainCurrentApp.model.TemplateSendingEmail;
import com.korotkov.mainCurrentApp.model.UserAccount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TemplateSendingEmailDaoImpl implements TemplateSendingEmailDao {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(TemplateSendingEmail templateSendingEmail) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(templateSendingEmail);
    }

    @Override
    public void update(TemplateSendingEmail templateSendingEmail) {
        Session session = sessionFactory.getCurrentSession();
        session.update(templateSendingEmail);
    }

    @Override
    public void delete(TemplateSendingEmail templateSendingEmail) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(templateSendingEmail);
    }

    @Override
    public TemplateSendingEmail getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(TemplateSendingEmail.class, id);
    }

    @Override
    public Long getCountAllTemplatesSendingEmails() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(tse.id) from TemplateSendingEmail tse")
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TemplateSendingEmail> getAllTemplatesSendingEmails(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tse from TemplateSendingEmail tse order by tse.id desc")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountTemplatesSendingEmailsForDates(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(tse.id) from TemplateSendingEmail tse where date(tse.createdAt) >= :dateFrom and date(tse.createdAt) <= :dateTo")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TemplateSendingEmail> getTemplatesSendingEmailsForDates(LocalDate dateFrom,
                                                                        LocalDate dateTo,
                                                                        int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tse from TemplateSendingEmail tse where date(tse.createdAt) >= :dateFrom and date(tse.createdAt) <= :dateTo order by tse.id desc")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountTemplatesSendingEmailsForDateFrom(LocalDate dateFrom) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(tse.id) from TemplateSendingEmail tse where date(tse.createdAt) >= :dateFrom")
                .setParameter("dateFrom", dateFrom)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TemplateSendingEmail> getTemplatesSendingEmailsForDateFrom(LocalDate dateFrom,
                                                                           int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tse from TemplateSendingEmail tse where date(tse.createdAt) >= :dateFrom order by tse.id desc")
                .setParameter("dateFrom", dateFrom)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountTemplatesSendingEmailsForDateTo(LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(tse.id) from TemplateSendingEmail tse where date(tse.createdAt) <= :dateTo")
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TemplateSendingEmail> getTemplatesSendingEmailsForDateTo(LocalDate dateTo,
                                                                         int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tse from TemplateSendingEmail tse where date(tse.createdAt) <= :dateTo order by tse.id desc")
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getIdLastCreatedTemplateSendingEmail(LocalDateTime createdAt, UserAccount createdBy, String title,
                                                     String subjectEmail) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select tse.id from TemplateSendingEmail tse where tse.createdAt = :createdAt and tse.title = :title and tse.createdBy = :createdBy and tse.subjectEmail = :subjectEmail")
                .setParameter("createdAt", createdAt)
                .setParameter("createdBy", createdBy)
                .setParameter("title", title)
                .setParameter("subjectEmail", subjectEmail)
                .uniqueResult();
    }

    @Override
    public Long getCountSendingEmailsForTemplate(TemplateSendingEmail templateSendingEmail) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(se.id) from SendingEmail se where se.templateSendingEmail = :templateSendingEmail")
                .setParameter("templateSendingEmail", templateSendingEmail)
                .uniqueResult();
    }
}
