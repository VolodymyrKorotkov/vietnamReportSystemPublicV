package com.korotkov.creditCRM.dao.collection.debtCollection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Repository
public class DebtCollectionDAOImpl implements DebtCollectionDAO {
    private static final Logger logger = LoggerFactory.getLogger(DebtCollectionDAOImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryCRM")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Modifying
    public void updatePromisedPaymentsAsExpired() {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        try {
            session.createSQLQuery("update debt_collection \n" +
                    "set status = 'Expired promised payment'\n" +
                    "where status = 'Promised payment'\n" +
                    "and last_promised_payment_date < :today \n" +
                    "and finished is false")
                    .setParameter("today", LocalDateTime.now(ZoneId.of(TIME_ZONE)))
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
    }



}
