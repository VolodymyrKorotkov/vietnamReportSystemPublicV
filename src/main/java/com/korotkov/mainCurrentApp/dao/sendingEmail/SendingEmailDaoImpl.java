package com.korotkov.mainCurrentApp.dao.sendingEmail;

import com.korotkov.mainCurrentApp.enums.EmailSendingStatusEnum;
import com.korotkov.mainCurrentApp.model.SendingEmail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Repository
public class SendingEmailDaoImpl implements SendingEmailDao {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(SendingEmail sendingEmail) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(sendingEmail);
    }

    @Override
    public void update(SendingEmail sendingEmail) {
        Session session = sessionFactory.getCurrentSession();
        session.update(sendingEmail);
    }

    @Override
    public SendingEmail getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(SendingEmail.class, id);
    }

    @Override
    public Long getCountAllSendingEmails() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(se.id) from SendingEmail se")
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SendingEmail> getAllSendingEmails(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select se from SendingEmail se order by se.id desc")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getIdLastCreatedSendingEmail(LocalDateTime createdAt, String title, boolean auto) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select se.id from SendingEmail se where se.createdAt = :createdAt and se.title = :title and se.auto = :auto")
                .setParameter("createdAt", createdAt)
                .setParameter("title", title)
                .setParameter("auto", auto)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SendingEmail> getSendingEmailsForDates(LocalDate dateFrom, LocalDate dateTo, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select se from SendingEmail se where date(se.startedAt) >= :dateFrom and date(se.startedAt) <= :dateTo order by se.startedAt desc")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountSendingEmailsForDates(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(se.id) from SendingEmail se where date(se.startedAt) >= :dateFrom and date(se.startedAt) <= :dateTo")
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SendingEmail> getSendingEmailsForDateFrom(LocalDate dateFrom, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select se from SendingEmail se where date(se.startedAt) >= :dateFrom order by se.startedAt desc")
                .setParameter("dateFrom", dateFrom)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountSendingEmailsForDateFrom(LocalDate dateFrom) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(se.id) from SendingEmail se where date(se.startedAt) >= :dateFrom")
                .setParameter("dateFrom", dateFrom)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SendingEmail> getSendingEmailsForDateTo(LocalDate dateTo, int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select se from SendingEmail se where date(se.startedAt) <= :dateTo order by se.startedAt desc")
                .setParameter("dateTo", dateTo)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountSendingEmailsForDateTo(LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(se.id) from SendingEmail se where date(se.startedAt) <= :dateTo")
                .setParameter("dateTo", dateTo)
                .uniqueResult();
    }

    @Override
    public SendingEmail getOnePlannedSendingEmailForStart() {
        Session session = sessionFactory.getCurrentSession();
        return (SendingEmail) session.createQuery("select se from SendingEmail se where se.status = :status and se.plannedAt <= :dateTimeNow order by se.plannedAt asc")
                .setParameter("status", String.valueOf(EmailSendingStatusEnum.PLANNED))
                .setParameter("dateTimeNow", LocalDateTime.now(ZoneId.of(TIME_ZONE)))
                .setMaxResults(1).uniqueResult();
    }

    @Override
    public void updateStatusAsCanceledForNotCompletedSendingEmail(LocalDate dateBefore) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        try {
            session.createSQLQuery("update sending_email\n" +
                            "set status = :canceled\n" +
                            "where date(started_at) < :dateBefore")
                    .setParameter("dateBefore", dateBefore)
                    .setParameter("canceled", String.valueOf(EmailSendingStatusEnum.CANCELED))
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ignored) {

        } finally {
            session.close();
        }
    }


}
