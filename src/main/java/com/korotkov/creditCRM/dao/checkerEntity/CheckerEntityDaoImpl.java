package com.korotkov.creditCRM.dao.checkerEntity;

import com.korotkov.creditCRM.model.CheckerEntity;
import com.korotkov.mainCurrentApp.enums.ApplicationStatusesCRMEnum;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LocalDateTimeType;
import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Repository
public class CheckerEntityDaoImpl implements CheckerEntityDao {
    private static final Logger logger = LoggerFactory.getLogger(CheckerEntityDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryCRM")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CheckerEntity> getApplicationsOnLongDMS(int minutesAgoForCheck) {
        Session session = sessionFactory.openSession();
        DateTimeFormatter dfDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        List<CheckerEntity> appsOnDMSList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select la.id as id,\n" +
                            "la.status_changed_at as date_time\n" +
                            "from loan_application la \n" +
                            "where la.status = :status")
                            .addScalar("id", new LongType())
                            .addScalar("date_time", new LocalDateTimeType())
                            .setParameter("status", String.valueOf(ApplicationStatusesCRMEnum.MAKE_DECISION_IN_PROGRESS))
                            .list();
            for (Object[] row : rows) {
                if (row[0] != null && row[1] != null) {
                    CheckerEntity checkerEntity = new CheckerEntity();
                    checkerEntity.setId(Long.parseLong(row[0].toString()));
                    checkerEntity.setDateTime(LocalDateTime.parse(row[1].toString(), dfDateTime));
                    if (checkerEntity.getDateTime().isBefore(LocalDateTime.now(ZoneId.of(TIME_ZONE)).minusMinutes(minutesAgoForCheck))) {
                        appsOnDMSList.add(checkerEntity);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return appsOnDMSList;
    }

    @Override
    public Long getCountLoansWithExpiredAndWithoutActiveDebtCollection() {
        Session session = sessionFactory.openSession();
        Long count = null;
        try {
            count =
                    (Long) session.createSQLQuery("select count(l.id) from loan l\n" +
                            "left join debt_collection dc \n" +
                            "on l.id = dc.loan_id \n" +
                            "and dc.finished is false \n" +
                            "where l.status = 'EXPIRED'\n" +
                            "and dc.loan_id is null")
                            .addScalar("count", new LongType())
                            .uniqueResult();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return count;
    }

    @Override
    public Long getCountLoansWhichNotCalculated() {
        Session session = sessionFactory.openSession();
        Long count = null;
        try {
            count =
                    (Long) session.createSQLQuery("select count(l.id) from loan l\n" +
                                    "left join\n" +
                                    "(select ls.loan_id as loan_id, max(ls.\"date\") as date from loan_snapshot ls \n" +
                                    "group by ls.loan_id ) as snap\n" +
                                    "on snap.loan_id = l.id\n" +
                                    "where l.status <> 'FINISHED'\n" +
                                    "and snap.date <> :dateToday")
                            .addScalar("count", new LongType())
                            .setParameter("dateToday", LocalDate.now(ZoneId.of(TIME_ZONE)))
                            .uniqueResult();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return count;
    }


    @Override
    @SuppressWarnings("unchecked")
    public CheckerEntity getLastCreatedApplication() {
        Session session = sessionFactory.openSession();
        DateTimeFormatter dfDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        CheckerEntity checkerEntity = new CheckerEntity();
        try {
            List<Object[]> rows = session.createSQLQuery("select la.id, la.requested_at from loan_application la\n" +
                    "order by la.id desc\n")
                    .addScalar("id", new LongType())
                    .addScalar("requested_at", new LocalDateTimeType())
                    .setMaxResults(1).list();
            if (!rows.isEmpty()) {
                if (rows.get(0)[0] != null && rows.get(0)[1] != null) {
                    checkerEntity.setId(Long.parseLong(rows.get(0)[0].toString()));
                    checkerEntity.setDateTime(LocalDateTime.parse(rows.get(0)[1].toString(),dfDateTime));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return checkerEntity;
    }


    @Override
    @SuppressWarnings("unchecked")
    public CheckerEntity getLastIssuedLoan() {
        Session session = sessionFactory.openSession();
        DateTimeFormatter dfDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        CheckerEntity checkerEntity = new CheckerEntity();
        try {
            List<Object[]> rows = session.createSQLQuery("select l.id, l.issued_at from loan l \n" +
                    "order by l.id desc")
                    .addScalar("id", new LongType())
                    .addScalar("issued_at", new LocalDateTimeType())
                    .setMaxResults(1).list();
            if (!rows.isEmpty()) {
                if (rows.get(0)[0] != null && rows.get(0)[1] != null) {
                    checkerEntity.setId(Long.parseLong(rows.get(0)[0].toString()));
                    checkerEntity.setDateTime(LocalDateTime.parse(rows.get(0)[1].toString(),dfDateTime));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return checkerEntity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CheckerEntity getLastRegisteredClient() {
        Session session = sessionFactory.openSession();
        DateTimeFormatter dfDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        CheckerEntity checkerEntity = new CheckerEntity();
        try {
            List<Object[]> rows = session.createSQLQuery("select cua.id, cua.created_at from client_user_account cua \n" +
                            "order by cua.id desc")
                    .addScalar("id", new LongType())
                    .addScalar("created_at", new LocalDateTimeType())
                    .setMaxResults(1).list();
            if (!rows.isEmpty()) {
                if (rows.get(0)[0] != null && rows.get(0)[1] != null) {
                    checkerEntity.setId(Long.parseLong(rows.get(0)[0].toString()));
                    checkerEntity.setDateTime(LocalDateTime.parse(rows.get(0)[1].toString(),dfDateTime));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return checkerEntity;
    }


    @Override
    @SuppressWarnings("unchecked")
    public CheckerEntity getLastPayment() {
        Session session = sessionFactory.openSession();
        DateTimeFormatter dfDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        CheckerEntity checkerEntity = new CheckerEntity();
        try {
            List<Object[]> rows = session.createSQLQuery("select lo.id, lo.created_at from loan_operation lo \n" +
                            "where lo.\"type\" = 'PAYMENT'\n" +
                            "and lo.status = 'ACTIVE'\n" +
                            "order by lo.id desc")
                    .addScalar("id", new LongType())
                    .addScalar("created_at", new LocalDateTimeType())
                    .setMaxResults(1).list();
            if (!rows.isEmpty()) {
                if (rows.get(0)[0] != null && rows.get(0)[1] != null) {
                    checkerEntity.setId(Long.parseLong(rows.get(0)[0].toString()));
                    checkerEntity.setDateTime(LocalDateTime.parse(rows.get(0)[1].toString(),dfDateTime));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return checkerEntity;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<CheckerEntity> getApplicationsWithPayoutError() {
        Session session = sessionFactory.openSession();
        DateTimeFormatter dfDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        List<CheckerEntity> appList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select la.id as id,\n" +
                                    "la.status_changed_at as date_time\n" +
                                    "from loan_application la \n" +
                                    "where la.status = :status")
                            .addScalar("id", new LongType())
                            .addScalar("date_time", new LocalDateTimeType())
                            .setParameter("status", String.valueOf(ApplicationStatusesCRMEnum.OUTGOING_PAYMENT_ERROR))
                            .list();
            for (Object[] row : rows) {
                if (row[0] != null && row[1] != null) {
                    CheckerEntity checkerEntity = new CheckerEntity();
                    checkerEntity.setId(Long.parseLong(row[0].toString()));
                    checkerEntity.setDateTime(LocalDateTime.parse(row[1].toString(), dfDateTime));
                    appList.add(checkerEntity);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return appList;
    }

    @Override
    public Long getCountAppsOnStatusSentToUnderwriting() {
        Session session = sessionFactory.openSession();
        Long count = null;
        try {
            count =
                    (Long) session.createSQLQuery("select count(la.id) from loan_application la \n" +
                                    "where la.status = :status")
                            .addScalar("count", new LongType())
                            .setParameter("status", String.valueOf(ApplicationStatusesCRMEnum.SENT_TO_UNDERWRITING))
                            .uniqueResult();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return count;
    }

}
