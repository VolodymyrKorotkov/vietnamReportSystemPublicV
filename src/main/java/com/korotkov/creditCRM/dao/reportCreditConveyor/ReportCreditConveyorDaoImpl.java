package com.korotkov.creditCRM.dao.reportCreditConveyor;

import com.korotkov.creditCRM.model.loansInfo.ExportLoansAndPaymentByDate;
import com.korotkov.creditCRM.model.reportsCreditConveyor.*;
import com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Repository
public class ReportCreditConveyorDaoImpl implements ReportCreditConveyorDao {
    private static final Logger logger = LoggerFactory.getLogger(ReportCreditConveyorDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryCRM")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportIssuedLoanForCrossCheck> getExportIssuedLoanForCrossCheckList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportIssuedLoanForCrossCheck> list = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (l.id)\n" +
                            "       date(l.issued_at) as date,\n" +
                            "       cua.document_id_number,\n" +
                            "       l.contract_number,\n" +
                            "       l.amount,\n" +
                            "       pt.provider_tx_id,\n" +
                            "                          l.id\n" +
                            "from loan l\n" +
                            "left join client_user_account cua on l.client_id = cua.id\n" +
                            "left join loan_application la on l.application_id = la.id\n" +
                            "left join payment_transaction pt on la.payout_transaction_id = pt.id\n" +
                            "where\n" +
                            "date(l.issued_at) >= :dateFrom\n" +
                            "and date(l.issued_at) <= :dateTo ;")
                            .addScalar("date", new LocalDateType())
                            .addScalar("document_id_number", new StringType())
                            .addScalar("contract_number", new StringType())
                            .addScalar("amount", new DoubleType())
                            .addScalar("provider_tx_id", new StringType())
                            .addScalar("id", new LongType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                ExportIssuedLoanForCrossCheck i = new ExportIssuedLoanForCrossCheck();
                i.setDate(LocalDate.parse(row[0].toString()));
                i.setDocId(row[1].toString());
                i.setContract(row[2].toString());
                i.setAmount(Double.parseDouble(row[3].toString()));
                i.setTxId(row[4].toString());
                i.setId(Long.parseLong(row[5].toString()));
                list.add(i);
            }

        } catch (Exception e){
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportPaymentForCrossCheck> getExportPaymentForCrossCheckList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportPaymentForCrossCheck> list = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "       snap.date,\n" +
                            "       snap.loan_id,\n" +
                            "       snap.paid_for_date,\n" +
                            "       snap.contract_number,\n" +
                            "       snap.document_id_number,\n" +
                            "       op.auto,\n" +
                            "       op.manual,\n" +
                                    "       snap.account\n" +
                                    "       from\n" +
                                    "(select distinct on (ls.date, ls.loan_id)\n" +
                                    "                            ls.date,\n" +
                                    "                            ls.loan_id,\n" +
                                    "                            ls.paid_for_date,\n" +
                                    "                            l2.contract_number,\n" +
                                    "                            c.document_id_number,\n" +
                                    "                                          bva.account\n" +
                                    "                            from loan_snapshot ls\n" +
                                    "                            left join loan l2 on ls.loan_id = l2.id\n" +
                                    "                            left join client_user_account c on l2.client_id = c.id\n" +
                                    "left join baokim_virtual_account bva on l2.application_id = bva.application_id\n" +
                                    "                            where ls.paid_for_date > 0\n" +
                            "                            and\n" +
                            "                            date(ls.date) >= :dateFrom\n" +
                            "                            and\n" +
                            "                            date(ls.date) <= :dateTo) as snap\n" +
                            "left join\n" +
                            "(select\n" +
                            "       date(lo.activated_at) as date,\n" +
                            "       lo.loan_id,\n" +
                            "       sum(case when lo.created_by_id is null then lo.payment_amount else 0 end) as auto,\n" +
                            "       sum(case when lo.created_by_id is not null then lo.payment_amount else 0 end) as manual\n" +
                            "from loan_operation lo\n" +
                            "where lo.type = 'PAYMENT'\n" +
                            "  and lo.payment_amount > 0\n" +
                            "  and lo.status = 'ACTIVE'\n" +
                            "  and date(lo.activated_at) >= :dateFrom\n" +
                            "  and date(lo.activated_at) <= :dateTo\n" +
                            "group by date(lo.activated_at),\n" +
                            "       lo.loan_id) as op\n" +
                            "on op.date = snap.date and op.loan_id = snap.loan_id;")
                            .addScalar("date", new LocalDateType())
                            .addScalar("loan_id", new LongType())
                            .addScalar("paid_for_date", new DoubleType())
                            .addScalar("contract_number", new StringType())
                            .addScalar("document_id_number", new StringType())
                            .addScalar("auto", new DoubleType())
                            .addScalar("manual", new DoubleType())
                            .addScalar("account", new StringType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                ExportPaymentForCrossCheck i = new ExportPaymentForCrossCheck();
                i.setDate(LocalDate.parse(row[0].toString()));
                i.setLoanId(Long.parseLong(row[1].toString()));
                i.setPayment(Double.parseDouble(row[2].toString()));
                i.setContract(row[3].toString());
                i.setDocId(row[4].toString());
                i.setAuto(Double.parseDouble(row[5].toString()));
                i.setManual(Double.parseDouble(row[6].toString()));
                i.setAccount(row[7].toString());

                list.add(i);
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportDeactivatedPayments> getExportDeactivatedPaymentsList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportDeactivatedPayments> list = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "    lo.last_modified_at,\n" +
                            "    lo.loan_id,\n" +
                            "    lo.last_modified_by_info,\n" +
                            "    l.contract_number,\n" +
                            "    lo.payment_amount,\n" +
                            "    lo.payment_info as comment,\n" +
                            "    lo.activated_at,\n" +
                            "    lo.created_at\n" +
                            "from loan_operation lo\n" +
                            "         left join loan l on lo.loan_id = l.id\n" +
                            "where lo.type = 'PAYMENT'\n" +
                            "  and lo.payment_amount > 0\n" +
                            "  and lo.status = 'DEACTIVATED'\n" +
                            "  and date(lo.activated_at) >= :dateFrom\n" +
                            "  and date(lo.activated_at) <= :dateTo ;")
                            .addScalar("last_modified_at", new LocalDateTimeType())
                            .addScalar("loan_id", new LongType())
                            .addScalar("last_modified_by_info", new StringType())
                            .addScalar("contract_number", new StringType())
                            .addScalar("payment_amount", new DoubleType())
                            .addScalar("comment", new StringType())
                            .addScalar("activated_at", new LocalDateTimeType())
                            .addScalar("created_at", new LocalDateTimeType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                ExportDeactivatedPayments i = new ExportDeactivatedPayments();
                if (row[0] != null) {
                    i.setDeactivatedAt(LocalDateTime.parse(row[0].toString()));
                }
                if (row[1] != null) {
                    i.setLoanId(Long.parseLong(row[1].toString()));
                }
                if (row[2] != null) {
                    i.setDeactivatedBy(row[2].toString());
                }
                if (row[3] != null) {
                    i.setContract(row[3].toString());
                }
                if (row[4] != null) {
                    i.setAmount(Double.parseDouble(row[4].toString()));
                }
                if (row[5] != null) {
                    i.setComment(row[5].toString());
                }
                if (row[6] != null) {
                    i.setActivatedAt(LocalDateTime.parse(row[6].toString()));
                }
                if (row[7] != null) {
                    i.setCreatedAt(LocalDateTime.parse(row[7].toString()));
                }
                list.add(i);
            }


        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportRefundOverpayment> getExportRefundOverpaymentList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportRefundOverpayment> list = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "       lo.activated_at,\n" +
                            "       lo.loan_id,\n" +
                            "       l.contract_number,\n" +
                            "       (lo.payment_amount * -1) as amount,\n" +
                            "       lo.payment_info as comment,\n" +
                            "       lo.created_at,\n" +
                            "       lo.created_by_info\n" +
                            "from loan_operation lo\n" +
                            "left join loan l on lo.loan_id = l.id\n" +
                            "where lo.type = 'PAYMENT'\n" +
                            "and lo.payment_amount < 0\n" +
                            "and lo.status = 'ACTIVE'\n" +
                            "and date(lo.activated_at) >= :dateFrom\n" +
                            "and date(lo.activated_at) <= :dateTo ;")
                            .addScalar("activated_at", new LocalDateTimeType())
                            .addScalar("loan_id", new LongType())
                            .addScalar("contract_number", new StringType())
                            .addScalar("amount", new DoubleType())
                            .addScalar("comment", new StringType())
                            .addScalar("created_at", new LocalDateTimeType())
                            .addScalar("created_by_info", new StringType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                ExportRefundOverpayment i = new ExportRefundOverpayment();
                if (row[0] != null) {
                    i.setActivatedAt(LocalDateTime.parse(row[0].toString()));
                }
                if (row[1] != null) {
                    i.setLoanId(Long.parseLong(row[1].toString()));
                }
                if (row[2] != null) {
                    i.setContractNumber(row[2].toString());
                }
                if (row[3] != null) {
                    i.setAmount(Double.parseDouble(row[3].toString()));
                }
                if (row[4] != null) {
                    i.setComment(row[4].toString());
                }
                if (row[5] != null) {
                    i.setCreatedAt(LocalDateTime.parse(row[5].toString()));
                }
                if (row[6] != null) {
                    i.setCreatedBy(row[6].toString());
                }
                list.add(i);
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportAppByUnderwriterPerHour> getExportAppByUnderwriterPerHourList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportAppByUnderwriterPerHour> list = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "       la.underwriter_account_info as underwriter,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 0 then 1 else 0 end) as app_0,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 1 then 1 else 0 end) as app_1,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 2 then 1 else 0 end) as app_2,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 3 then 1 else 0 end) as app_3,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 4 then 1 else 0 end) as app_4,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 5 then 1 else 0 end) as app_5,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 6 then 1 else 0 end) as app_6,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 7 then 1 else 0 end) as app_7,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 8 then 1 else 0 end) as app_8,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 9 then 1 else 0 end) as app_9,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 10 then 1 else 0 end) as app_10,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 11 then 1 else 0 end) as app_11,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 12 then 1 else 0 end) as app_12,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 13 then 1 else 0 end) as app_13,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 14 then 1 else 0 end) as app_14,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 15 then 1 else 0 end) as app_15,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 16 then 1 else 0 end) as app_16,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 17 then 1 else 0 end) as app_17,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 18 then 1 else 0 end) as app_18,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 19 then 1 else 0 end) as app_19,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 20 then 1 else 0 end) as app_20,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 21 then 1 else 0 end) as app_21,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 22 then 1 else 0 end) as app_22,\n" +
                            "       sum(case when cast(date_part('hour',la.underwriter_decision_made_at) as INTEGER) = 23 then 1 else 0 end) as app_23,\n" +
                            "       count(la.id) as count_app\n" +
                            "from loan_application la\n" +
                            "where\n" +
                            "      la.underwriter_account_id is not null\n" +
                            "  and date(la.underwriter_decision_made_at) >= :dateFrom\n" +
                            "  and date(la.underwriter_decision_made_at) <= :dateTo\n" +
                            "group by la.underwriter_account_info;")
                            .addScalar("underwriter", new StringType())
                            .addScalar("app_0", new LongType())
                            .addScalar("app_1", new LongType())
                            .addScalar("app_2", new LongType())
                            .addScalar("app_3", new LongType())
                            .addScalar("app_4", new LongType())
                            .addScalar("app_5", new LongType())
                            .addScalar("app_6", new LongType())
                            .addScalar("app_7", new LongType())
                            .addScalar("app_8", new LongType())
                            .addScalar("app_9", new LongType())
                            .addScalar("app_10", new LongType())
                            .addScalar("app_11", new LongType())
                            .addScalar("app_12", new LongType())
                            .addScalar("app_13", new LongType())
                            .addScalar("app_14", new LongType())
                            .addScalar("app_15", new LongType())
                            .addScalar("app_16", new LongType())
                            .addScalar("app_17", new LongType())
                            .addScalar("app_18", new LongType())
                            .addScalar("app_19", new LongType())
                            .addScalar("app_20", new LongType())
                            .addScalar("app_21", new LongType())
                            .addScalar("app_22", new LongType())
                            .addScalar("app_23", new LongType())
                            .addScalar("count_app", new LongType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                ExportAppByUnderwriterPerHour item = new ExportAppByUnderwriterPerHour();
                item.setUnderwriter(row[0].toString());
                item.setApp0Hour(Long.parseLong(row[1].toString()));
                item.setApp1Hour(Long.parseLong(row[2].toString()));
                item.setApp2Hour(Long.parseLong(row[3].toString()));
                item.setApp3Hour(Long.parseLong(row[4].toString()));
                item.setApp4Hour(Long.parseLong(row[5].toString()));
                item.setApp5Hour(Long.parseLong(row[6].toString()));
                item.setApp6Hour(Long.parseLong(row[7].toString()));
                item.setApp7Hour(Long.parseLong(row[8].toString()));
                item.setApp8Hour(Long.parseLong(row[9].toString()));
                item.setApp9Hour(Long.parseLong(row[10].toString()));
                item.setApp10Hour(Long.parseLong(row[11].toString()));
                item.setApp11Hour(Long.parseLong(row[12].toString()));
                item.setApp12Hour(Long.parseLong(row[13].toString()));
                item.setApp13Hour(Long.parseLong(row[14].toString()));
                item.setApp14Hour(Long.parseLong(row[15].toString()));
                item.setApp15Hour(Long.parseLong(row[16].toString()));
                item.setApp16Hour(Long.parseLong(row[17].toString()));
                item.setApp17Hour(Long.parseLong(row[18].toString()));
                item.setApp18Hour(Long.parseLong(row[19].toString()));
                item.setApp19Hour(Long.parseLong(row[20].toString()));
                item.setApp20Hour(Long.parseLong(row[21].toString()));
                item.setApp21Hour(Long.parseLong(row[22].toString()));
                item.setApp22Hour(Long.parseLong(row[23].toString()));
                item.setApp23Hour(Long.parseLong(row[24].toString()));
                item.setAppCount(Long.parseLong(row[25].toString()));
                list.add(item);
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportAppProcessedByUnderwriter> getExportAppProcessedByUnderwriterList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportAppProcessedByUnderwriter> list = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "       la.requested_at as app_date,\n" +
                            "       la.underwriter_decision_made_at as decision_at,\n" +
                            "       la.underwriter_account_info as underwriter,\n" +
                            "       la.client_type,\n" +
                            "       la.id,\n" +
                            "       la.status,\n" +
                            "       la.rejected_by,\n" +
                            "       rr.reject_reason_vi,\n" +
                            "       rr.reject_reason_en,\n" +
                            "       la.sent_to_client_for_revision_at as sent_to_revision_at,\n" +
                            "       la.sent_to_client_for_revision_comment as revision_comment\n" +
                            "from loan_application la\n" +
                            "left join reject_reason rr on la.localized_rejection_reason_id = rr.id\n" +
                            "where la.underwriter_account_id is not null\n" +
                            "and date(la.requested_at) >= :dateFrom\n" +
                            "and date(la.requested_at) <= :dateTo\n" +
                            "order by la.requested_at asc;")
                            .addScalar("app_date", new LocalDateTimeType())
                            .addScalar("decision_at", new LocalDateTimeType())
                            .addScalar("underwriter", new StringType())
                            .addScalar("client_type", new StringType())
                            .addScalar("id", new LongType())
                            .addScalar("status", new StringType())
                            .addScalar("rejected_by", new StringType())
                            .addScalar("reject_reason_vi", new StringType())
                            .addScalar("reject_reason_en", new StringType())
                            .addScalar("sent_to_revision_at", new LocalDateTimeType())
                            .addScalar("revision_comment", new StringType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                ExportAppProcessedByUnderwriter item = new ExportAppProcessedByUnderwriter();
                if (row[0] != null) {
                    item.setAppDate(LocalDateTime.parse(row[0].toString()));
                }
                if (row[1] != null) {
                    item.setDecisionAt(LocalDateTime.parse(row[1].toString()));
                }
                if (row[2] != null) {
                    item.setUnderwriter(row[2].toString());
                }
                if (row[3] != null) {
                    item.setClientType(row[3].toString());
                }
                if (row[4] != null) {
                    item.setAppId(Long.parseLong(row[4].toString()));
                }
                if (row[5] != null) {
                    item.setStatus(row[5].toString());
                }
                if (row[6] != null) {
                    item.setRejectedBy(row[6].toString());
                }
                if (row[7] != null) {
                    item.setRejectReasonVn(row[7].toString());
                }
                if (row[8] != null) {
                    item.setRejectReasonEn(row[8].toString());
                }
                if (row[9] != null) {
                    item.setSentToRevisionAt(LocalDateTime.parse(row[9].toString()));
                }
                if (row[10] != null) {
                    item.setRevisionComment(row[10].toString());
                }

                if (item.getAppId() != null && item.getUnderwriter() != null && item.getAppDate() != null) {
                    list.add(item);
                }
            }
        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UnderwritersResultWithApp> getUnderwritersResultWithAppList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<UnderwritersResultWithApp> list = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "       la.underwriter_account_info,\n" +
                            "       count(la.id) as count_app,\n" +
                            "       sum(case when la.status = 'OUTGOING_PAYMENT_SUCCEED' then 1 else 0 end) as count_issued_loan,\n" +
                            "       sum(case when debt.days_overdue > 4 then 1 else 0 end) as count_loan_with_dpd_more_4_days\n" +
                            "from loan_application la\n" +
                            "left join loan l on la.id = l.application_id\n" +
                            "left join\n" +
                            "(select dc.loan_id, max(dc.days_overdue) as days_overdue from debt_collection dc\n" +
                            "group by dc.loan_id) as debt\n" +
                            "on debt.loan_id = l.id\n" +
                            "where la.underwriter_account_id is not null\n" +
                            "  and date(la.underwriter_decision_made_at) >= :dateFrom\n" +
                            "  and date(la.underwriter_decision_made_at) <= :dateTo\n" +
                            "group by la.underwriter_account_info;")
                            .addScalar("underwriter_account_info", new StringType())
                            .addScalar("count_app", new LongType())
                            .addScalar("count_issued_loan", new LongType())
                            .addScalar("count_loan_with_dpd_more_4_days", new LongType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                UnderwritersResultWithApp item = new UnderwritersResultWithApp();
                item.setUnderwriter(row[0].toString());
                item.setCountApps(Long.parseLong(row[1].toString()));
                item.setCountIssuedLoan(Long.parseLong(row[2].toString()));
                item.setCountLoanWithDpd(Long.parseLong(row[3].toString()));
                list.add(item);
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return list;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ExportDateHourCountAmount> getExportDateHourCountAmountPaymentList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportDateHourCountAmount> list = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                                    "       date(lo.activated_at) as date,\n" +
                                    "       cast(date_part('hour', lo.activated_at) as INTEGER) as hour,\n" +
                                    "       count(lo.id) as count,\n" +
                                    "       sum(lo.payment_amount) as amount,\n" +
                                    "       sum(case when la.client_type = 'POTENTIAL' then 1 else 0 end) as count_new,\n" +
                                    "       sum(case when la.client_type = 'POTENTIAL' then lo.payment_amount else 0 end) as amount_new,\n" +
                                    "       sum(case when la.client_type = 'REPEAT' then 1 else 0 end) as count_repeat,\n" +
                                    "       sum(case when la.client_type = 'REPEAT' then lo.payment_amount else 0 end) as amount_repeat\n" +
                                    "from loan_operation lo\n" +
                                    "left join loan l on lo.loan_id = l.id\n" +
                                    "left join loan_application la on l.application_id = la.id\n" +
                                    "where lo.type = 'PAYMENT'\n" +
                                    "  and lo.status = 'ACTIVE'\n" +
                                    "  and lo.payment_amount > 0\n" +
                                    "  and date(lo.activated_at) >= :dateFrom\n" +
                                    "  and date(lo.activated_at) <= :dateTo\n" +
                                    "group by date(lo.activated_at), date_part('hour', lo.activated_at);")
                            .addScalar("date", new LocalDateType())
                            .addScalar("hour", new IntegerType())
                            .addScalar("count", new LongType())
                            .addScalar("amount", new DoubleType())
                            .addScalar("count_new", new LongType())
                            .addScalar("amount_new", new DoubleType())
                            .addScalar("count_repeat", new LongType())
                            .addScalar("amount_repeat", new DoubleType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                ExportDateHourCountAmount item = new ExportDateHourCountAmount();
                item.setDate(LocalDate.parse(row[0].toString()));
                item.setHour(Integer.parseInt(row[1].toString()));
                item.setCountTotal(Long.parseLong(row[2].toString()));
                item.setAmountTotal(Double.parseDouble(row[3].toString()));
                item.setCountNew(Long.parseLong(row[4].toString()));
                item.setAmountNew(Double.parseDouble(row[5].toString()));
                item.setCountRepeat(Long.parseLong(row[6].toString()));
                item.setAmountRepeat(Double.parseDouble(row[7].toString()));

                list.add(item);
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportDateHourCountAmount> getExportDateHourCountAmountLoanList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportDateHourCountAmount> list = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                                    "       date(l.issued_at) as date,\n" +
                                    "       cast(date_part('hour',l.issued_at) as INTEGER) as hour,\n" +
                                    "       count(l.id) as count,\n" +
                                    "       sum(l.amount) as amount,\n" +
                                    "       sum(case when la.client_type = 'POTENTIAL' then 1 else 0 end) as count_new,\n" +
                                    "       sum(case when la.client_type = 'POTENTIAL' then l.amount else 0 end) as amount_new,\n" +
                                    "       sum(case when la.client_type = 'REPEAT' then 1 else 0 end) as count_repeat,\n" +
                                    "       sum(case when la.client_type = 'REPEAT' then l.amount else 0 end) as amount_repeat\n" +
                                    "from loan l\n" +
                                    "left join loan_application la on l.application_id = la.id\n" +
                                    "where date(l.issued_at) >= :dateFrom\n" +
                                    "  and date(l.issued_at) <= :dateTo\n" +
                                    "group by date(l.issued_at),date_part('hour',l.issued_at);")
                            .addScalar("date", new LocalDateType())
                            .addScalar("hour", new IntegerType())
                            .addScalar("count", new LongType())
                            .addScalar("amount", new DoubleType())
                            .addScalar("count_new", new LongType())
                            .addScalar("amount_new", new DoubleType())
                            .addScalar("count_repeat", new LongType())
                            .addScalar("amount_repeat", new DoubleType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                ExportDateHourCountAmount item = new ExportDateHourCountAmount();
                item.setDate(LocalDate.parse(row[0].toString()));
                item.setHour(Integer.parseInt(row[1].toString()));
                item.setCountTotal(Long.parseLong(row[2].toString()));
                item.setAmountTotal(Double.parseDouble(row[3].toString()));
                item.setCountNew(Long.parseLong(row[4].toString()));
                item.setAmountNew(Double.parseDouble(row[5].toString()));
                item.setCountRepeat(Long.parseLong(row[6].toString()));
                item.setAmountRepeat(Double.parseDouble(row[7].toString()));

                list.add(item);
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }

        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportLoansAndPaymentByDate> getExportLoansAndPaymentByDateList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportLoansAndPaymentByDate> exportLoansAndPaymentByDateList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "       ll.date,\n" +
                            "       ll.amount_loans,\n" +
                            "       pp.amount_paid\n" +
                            "from\n" +
                            "(select\n" +
                            "       date(l.issued_at) as date,\n" +
                            "       sum(l.amount) as amount_loans\n" +
                            "       from loan l\n" +
                            "where date(l.issued_at) >= :dateFrom\n" +
                            "and date(l.issued_at) <= :dateTo\n" +
                            "group by date(l.issued_at)) as ll\n" +
                            "left join\n" +
                            "(select\n" +
                            "       ls.date,\n" +
                            "       sum(ls.paid_for_date) amount_paid\n" +
                            "from loan_snapshot ls\n" +
                            "where ls.date >= :dateFrom\n" +
                            "  and ls.date <= :dateTo\n" +
                            "  and ls.paid_for_date > 0\n" +
                            "group by ls.date) as pp\n" +
                            "on ll.date = pp.date;")
                            .addScalar("date", new LocalDateType())
                            .addScalar("amount_loans", new DoubleType())
                            .addScalar("amount_paid", new DoubleType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                ExportLoansAndPaymentByDate exportLoansAndPaymentByDate = new ExportLoansAndPaymentByDate();
                exportLoansAndPaymentByDate.setDate(LocalDate.parse(row[0].toString()));
                exportLoansAndPaymentByDate.setAmountLoans(Double.parseDouble(row[1].toString()));
                exportLoansAndPaymentByDate.setAmountPayments(Double.parseDouble(row[2].toString()));

                exportLoansAndPaymentByDateList.add(exportLoansAndPaymentByDate);
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return exportLoansAndPaymentByDateList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ExportLoansBySource> getExportLoansBySourceList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportLoansBySource> exportLoansBySourceList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                                    "       laf.title as source_name,\n" +
                                    "       l.issued_at as loan_date,\n" +
                                    "       la.id as application_id,\n" +
                                    "       la.affiliate_utm_source,\n" +
                                    "       la.affiliate_utm_campaign ,\n" +
                                    "       la.affiliate_utm_content ,\n" +
                                    "       la.affiliate_utm_medium ,\n" +
                                    "       la.affiliate_utm_referrer ,\n" +
                                    "       la.affiliate_utm_term,\n" +
                                    "       la.client_type\n" +
                                    "from loan l\n" +
                                    "         left join loan_application la\n" +
                                    "                   on la.id = l.application_id\n" +
                                    "         left join loan_affiliate laf\n" +
                                    "                   on laf.code = la.affiliate_utm_source\n" +
                                    "where\n" +
                                    "    la.affiliate_utm_source is not null\n" +
                                    "  and date(l.issued_at) >= :dateFrom\n" +
                                    "  and date(l.issued_at) <= :dateTo ;")
                            .addScalar("source_name", new StringType())
                            .addScalar("loan_date", new LocalDateTimeType())
                            .addScalar("application_id", new LongType())
                            .addScalar("affiliate_utm_source", new StringType())
                            .addScalar("affiliate_utm_campaign", new StringType())
                            .addScalar("affiliate_utm_content", new StringType())
                            .addScalar("affiliate_utm_medium", new StringType())
                            .addScalar("affiliate_utm_referrer", new StringType())
                            .addScalar("affiliate_utm_term", new StringType())
                            .addScalar("client_type", new StringType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                ExportLoansBySource exportLoansBySource = new ExportLoansBySource();
                if (row[0] != null) {
                    exportLoansBySource.setSourceName(row[0].toString());
                }
                if (row[1] != null) {
                    exportLoansBySource.setDateTime(LocalDateTime.parse(row[1].toString()));
                }
                if (row[2] != null) {
                    exportLoansBySource.setApplicationId(Long.parseLong(row[2].toString()));
                }
                if (row[3] != null) {
                    exportLoansBySource.setUtmSource(row[3].toString());
                }
                if (row[4] != null) {
                    exportLoansBySource.setUtmCampaign(row[4].toString());
                }
                if (row[5] != null) {
                    exportLoansBySource.setUtmContent(row[5].toString());
                }
                if (row[6] != null) {
                    exportLoansBySource.setUtmMedium(row[6].toString());
                }
                if (row[7] != null) {
                    exportLoansBySource.setUtmReferrer(row[7].toString());
                }
                if (row[8] != null) {
                    exportLoansBySource.setUtmTerm(row[8].toString());
                }
                if (row[9] != null) {
                    exportLoansBySource.setClientType(row[9].toString());
                }

                exportLoansBySourceList.add(exportLoansBySource);
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return exportLoansBySourceList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportAppsBySource> getExportAppsBySourceList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportAppsBySource> exportAppsBySourceList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "       laf.title as source_name,\n" +
                            "       la.requested_at as application_date,\n" +
                            "       la.id as application_id,\n" +
                            "       la.affiliate_utm_source,\n" +
                            "       la.affiliate_utm_campaign ,\n" +
                            "       la.affiliate_utm_content ,\n" +
                            "       la.affiliate_utm_medium ,\n" +
                            "       la.affiliate_utm_referrer ,\n" +
                            "       la.affiliate_utm_term,\n" +
                            "       la.status,\n" +
                            "       la.client_type\n" +
                            "from loan_application la\n" +
                            "         left join loan_affiliate laf\n" +
                            "                   on laf.code = la.affiliate_utm_source\n" +
                            "where\n" +
                            "    la.affiliate_utm_source is not null\n" +
                            "  and date(la.requested_at) >= :dateFrom\n" +
                            "  and date(la.requested_at) <= :dateTo ;")
                            .addScalar("source_name", new StringType())
                            .addScalar("application_date", new LocalDateTimeType())
                            .addScalar("application_id", new LongType())
                            .addScalar("affiliate_utm_source", new StringType())
                            .addScalar("affiliate_utm_campaign", new StringType())
                            .addScalar("affiliate_utm_content", new StringType())
                            .addScalar("affiliate_utm_medium", new StringType())
                            .addScalar("affiliate_utm_referrer", new StringType())
                            .addScalar("affiliate_utm_term", new StringType())
                            .addScalar("status", new StringType())
                            .addScalar("client_type", new StringType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                ExportAppsBySource exportAppsBySource = new ExportAppsBySource();
                if (row[0] != null) {
                    exportAppsBySource.setSourceName(row[0].toString());
                }
                if (row[1] != null) {
                    exportAppsBySource.setDateTime(LocalDateTime.parse(row[1].toString()));
                }
                if (row[2] != null) {
                    exportAppsBySource.setApplicationId(Long.parseLong(row[2].toString()));
                }
                if (row[3] != null) {
                    exportAppsBySource.setUtmSource(row[3].toString());
                }
                if (row[4] != null) {
                    exportAppsBySource.setUtmCampaign(row[4].toString());
                }
                if (row[5] != null) {
                    exportAppsBySource.setUtmContent(row[5].toString());
                }
                if (row[6] != null) {
                    exportAppsBySource.setUtmMedium(row[6].toString());
                }
                if (row[7] != null) {
                    exportAppsBySource.setUtmReferrer(row[7].toString());
                }
                if (row[8] != null) {
                    exportAppsBySource.setUtmTerm(row[8].toString());
                }
                if (row[9] != null) {
                    exportAppsBySource.setStatus(row[9].toString());
                }
                if (row[10] != null) {
                    exportAppsBySource.setClientType(row[10].toString());
                }

                exportAppsBySourceList.add(exportAppsBySource);
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return exportAppsBySourceList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PaymentsAmountInPeriod getPaymentsAmountInPeriod(LocalDate dateFrom, LocalDate dateTo,
                                                            Integer lastDayOverdueStage1,
                                                            Integer lastDayOverdueStage2) {
        Session session = sessionFactory.openSession();
        PaymentsAmountInPeriod paymentsAmountInPeriod = new PaymentsAmountInPeriod();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "       sum(sn.paid_for_date) as paid,\n" +
                            "       sum(sn.paid_principal_for_date) as paid_principle,\n" +
                            "       sum(case when (ls3.id is null)\n" +
                            "           or (ls3.loan_status not in ('GRACE', 'EXPIRED', 'PROLONGED'))\n" +
                            "           or (ls3.loan_status = 'PROLONGED' and li.id is not null)\n" +
                            "                    then sn.paid_for_date else 0 end) as paid_on_active,\n" +
                            "       sum(case when (ls3.id is null)\n" +
                            "           or (ls3.loan_status not in ('GRACE', 'EXPIRED', 'PROLONGED'))\n" +
                            "           or (ls3.loan_status = 'PROLONGED' and li.id is not null)\n" +
                            "                    then sn.paid_principal_for_date else 0 end) as paid_principle_on_active,\n" +
                            "       sum(case when (ls3.loan_status = 'GRACE')\n" +
                            "           or (ls3.loan_status = 'PROLONGED' and li.id is null)\n" +
                            "           or (ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= 1 and ls3.days_overdue <= :preLastDayOverdueStage1)\n" +
                            "                    then sn.paid_for_date else 0 end) as paid_on_expired_stage_1,\n" +
                            "       sum(case when (ls3.loan_status = 'GRACE')\n" +
                            "           or (ls3.loan_status = 'PROLONGED' and li.id is null)\n" +
                            "           or (ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= 1 and ls3.days_overdue <= :preLastDayOverdueStage1)\n" +
                            "                    then sn.paid_principal_for_date else 0 end) as paid_principle_on_expired_stage_1,\n" +
                            "       sum(case when ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= :lastDayOverdueStage1 and ls3.days_overdue <= :preLastDayOverdueStage2\n" +
                            "                    then sn.paid_for_date else 0 end) as paid_on_expired_stage_2,\n" +
                            "       sum(case when ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= :lastDayOverdueStage1 and ls3.days_overdue <= :preLastDayOverdueStage2\n" +
                            "                    then sn.paid_principal_for_date else 0 end) as paid_principle_on_expired_stage_2,\n" +
                            "       sum(case when ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= :lastDayOverdueStage2\n" +
                            "                    then sn.paid_for_date else 0 end) as paid_on_expired_stage_other,\n" +
                            "       sum(case when ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= :lastDayOverdueStage2\n" +
                            "                    then sn.paid_principal_for_date else 0 end) as paid_principle_on_expired_stage_other,\n" +
                            "    sum(case when la.client_type = 'POTENTIAL' then sn.paid_for_date else 0 end) as paid_new,\n" +
                            "        sum(case when la.client_type = 'POTENTIAL' then sn.paid_principal_for_date else 0 end) as paid_principle_new,\n" +
                            "       sum(case when ((ls3.id is null)\n" +
                            "           or (ls3.loan_status not in ('GRACE', 'EXPIRED', 'PROLONGED'))\n" +
                            "           or (ls3.loan_status = 'PROLONGED' and li.id is not null))\n" +
                            "           and la.client_type = 'POTENTIAL'\n" +
                            "                    then sn.paid_for_date else 0 end) as paid_on_active_new,\n" +
                            "       sum(case when ((ls3.id is null)\n" +
                            "           or (ls3.loan_status not in ('GRACE', 'EXPIRED', 'PROLONGED'))\n" +
                            "           or (ls3.loan_status = 'PROLONGED' and li.id is not null))\n" +
                            "           and la.client_type = 'POTENTIAL'\n" +
                            "                    then sn.paid_principal_for_date else 0 end) as paid_principle_on_active_new,\n" +
                            "       sum(case when ((ls3.loan_status = 'GRACE')\n" +
                            "           or (ls3.loan_status = 'PROLONGED' and li.id is null)\n" +
                            "           or (ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= 1 and ls3.days_overdue <= :preLastDayOverdueStage1))\n" +
                            "           and la.client_type = 'POTENTIAL'\n" +
                            "                    then sn.paid_for_date else 0 end) as paid_on_expired_stage_1_new,\n" +
                            "       sum(case when ((ls3.loan_status = 'GRACE')\n" +
                            "           or (ls3.loan_status = 'PROLONGED' and li.id is null)\n" +
                            "           or (ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= 1 and ls3.days_overdue <= :preLastDayOverdueStage1))\n" +
                            "           and la.client_type = 'POTENTIAL'\n" +
                            "                    then sn.paid_principal_for_date else 0 end) as paid_principle_on_expired_stage_1_new,\n" +
                            "       sum(case when ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= :lastDayOverdueStage1 and ls3.days_overdue <= :preLastDayOverdueStage2\n" +
                            "           and la.client_type = 'POTENTIAL'\n" +
                            "                    then sn.paid_for_date else 0 end) as paid_on_expired_stage_2_new,\n" +
                            "       sum(case when ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= :lastDayOverdueStage1 and ls3.days_overdue <= :preLastDayOverdueStage2\n" +
                            "           and la.client_type = 'POTENTIAL'\n" +
                            "                    then sn.paid_principal_for_date else 0 end) as paid_principle_on_expired_stage_2_new,\n" +
                            "       sum(case when ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= :lastDayOverdueStage2\n" +
                            "           and la.client_type = 'POTENTIAL'\n" +
                            "                    then sn.paid_for_date else 0 end) as paid_on_expired_stage_other_new,\n" +
                            "       sum(case when ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= :lastDayOverdueStage2\n" +
                            "           and la.client_type = 'POTENTIAL'\n" +
                            "                    then sn.paid_principal_for_date else 0 end) as paid_principle_on_expired_stage_other_new,\n" +
                            "       sum(case when la.client_type = 'REPEAT' then sn.paid_for_date else 0 end) as paid_repeat,\n" +
                            "       sum(case when la.client_type = 'REPEAT' then sn.paid_principal_for_date else 0 end) as paid_principle_repeat,\n" +
                            "       sum(case when ((ls3.id is null)\n" +
                            "           or (ls3.loan_status not in ('GRACE', 'EXPIRED', 'PROLONGED'))\n" +
                            "           or (ls3.loan_status = 'PROLONGED' and li.id is not null))\n" +
                            "           and la.client_type = 'REPEAT'\n" +
                            "                    then sn.paid_for_date else 0 end) as paid_on_active_repeat,\n" +
                            "       sum(case when ((ls3.id is null)\n" +
                            "           or (ls3.loan_status not in ('GRACE', 'EXPIRED', 'PROLONGED'))\n" +
                            "           or (ls3.loan_status = 'PROLONGED' and li.id is not null))\n" +
                            "           and la.client_type = 'REPEAT'\n" +
                            "                    then sn.paid_principal_for_date else 0 end) as paid_principle_on_active_repeat,\n" +
                            "       sum(case when ((ls3.loan_status = 'GRACE')\n" +
                            "           or (ls3.loan_status = 'PROLONGED' and li.id is null)\n" +
                            "           or (ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= 1 and ls3.days_overdue <= :preLastDayOverdueStage1))\n" +
                            "           and la.client_type = 'REPEAT'\n" +
                            "                    then sn.paid_for_date else 0 end) as paid_on_expired_stage_1_repeat,\n" +
                            "       sum(case when ((ls3.loan_status = 'GRACE')\n" +
                            "           or (ls3.loan_status = 'PROLONGED' and li.id is null)\n" +
                            "           or (ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= 1 and ls3.days_overdue <= :preLastDayOverdueStage1))\n" +
                            "           and la.client_type = 'REPEAT'\n" +
                            "                    then sn.paid_principal_for_date else 0 end) as paid_principle_on_expired_stage_1_repeat,\n" +
                            "       sum(case when ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= :lastDayOverdueStage1 and ls3.days_overdue <= :preLastDayOverdueStage2\n" +
                            "           and la.client_type = 'REPEAT'\n" +
                            "                    then sn.paid_for_date else 0 end) as paid_on_expired_stage_2_repeat,\n" +
                            "       sum(case when ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= :lastDayOverdueStage1 and ls3.days_overdue <= :preLastDayOverdueStage2\n" +
                            "           and la.client_type = 'REPEAT'\n" +
                            "                    then sn.paid_principal_for_date else 0 end) as paid_principle_on_expired_stage_2_repeat,\n" +
                            "       sum(case when ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= :lastDayOverdueStage2\n" +
                            "           and la.client_type = 'REPEAT'\n" +
                            "                    then sn.paid_for_date else 0 end) as paid_on_expired_stage_other_repeat,\n" +
                            "       sum(case when ls3.loan_status = 'EXPIRED' and ls3.days_overdue >= :lastDayOverdueStage2\n" +
                            "           and la.client_type = 'REPEAT'\n" +
                            "                    then sn.paid_principal_for_date else 0 end) as paid_principle_on_expired_stage_other_repeat\n" +
                            "       from\n" +
                            "(select\n" +
                            "    ls.date as date,\n" +
                            "    ls.loan_id,\n" +
                            "    ls.paid_for_date,\n" +
                            "    (case when (coalesce((select ls2.al_principal from public.loan_snapshot ls2 where\n" +
                            "            ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_principal, ls.al_principal) * -1) >= 0 then (coalesce((select ls2.al_principal from public.loan_snapshot ls2 where\n" +
                            "            ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_principal, ls.al_principal) * -1) else (coalesce((select ls2.al_principal from public.loan_snapshot ls2 where\n" +
                            "            ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_principal, ls.al_principal) * 1) end) as paid_principal_for_date\n" +
                            "from public.loan_snapshot ls\n" +
                            "where ls.paid_for_date > 0\n" +
                            "  and to_date(to_char(ls.date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= :dateFrom\n" +
                            "  and to_date(to_char(ls.date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= :dateTo) as sn\n" +
                            "    left join loan l on l.id = sn.loan_id\n" +
                            "    left join loan_application la on l.application_id = la.id\n" +
                            "    left join loan_snapshot ls3 on ls3.loan_id = sn.loan_id and ls3.date = sn.date - 1\n" +
                            "    left join loan_installment li on sn.loan_id = li.loan_id and li.period_start >= sn.date and li.period_end <= sn.date;")
                            .addScalar("paid",new DoubleType())
                            .addScalar("paid_principle",new DoubleType())
                            .addScalar("paid_on_active",new DoubleType())
                            .addScalar("paid_principle_on_active",new DoubleType())
                            .addScalar("paid_on_expired_stage_1",new DoubleType())
                            .addScalar("paid_principle_on_expired_stage_1",new DoubleType())
                            .addScalar("paid_on_expired_stage_2",new DoubleType())
                            .addScalar("paid_principle_on_expired_stage_2",new DoubleType())
                            .addScalar("paid_on_expired_stage_other",new DoubleType())
                            .addScalar("paid_principle_on_expired_stage_other",new DoubleType())
                            .addScalar("paid_new",new DoubleType())
                            .addScalar("paid_principle_new",new DoubleType())
                            .addScalar("paid_on_active_new",new DoubleType())
                            .addScalar("paid_principle_on_active_new",new DoubleType())
                            .addScalar("paid_on_expired_stage_1_new",new DoubleType())
                            .addScalar("paid_principle_on_expired_stage_1_new",new DoubleType())
                            .addScalar("paid_on_expired_stage_2_new",new DoubleType())
                            .addScalar("paid_principle_on_expired_stage_2_new",new DoubleType())
                            .addScalar("paid_on_expired_stage_other_new",new DoubleType())
                            .addScalar("paid_principle_on_expired_stage_other_new",new DoubleType())
                            .addScalar("paid_repeat",new DoubleType())
                            .addScalar("paid_principle_repeat",new DoubleType())
                            .addScalar("paid_on_active_repeat",new DoubleType())
                            .addScalar("paid_principle_on_active_repeat",new DoubleType())
                            .addScalar("paid_on_expired_stage_1_repeat",new DoubleType())
                            .addScalar("paid_principle_on_expired_stage_1_repeat",new DoubleType())
                            .addScalar("paid_on_expired_stage_2_repeat",new DoubleType())
                            .addScalar("paid_principle_on_expired_stage_2_repeat",new DoubleType())
                            .addScalar("paid_on_expired_stage_other_repeat",new DoubleType())
                            .addScalar("paid_principle_on_expired_stage_other_repeat",new DoubleType())
                            .setParameter("preLastDayOverdueStage1", lastDayOverdueStage1 - 1)
                            .setParameter("lastDayOverdueStage1", lastDayOverdueStage1)
                            .setParameter("preLastDayOverdueStage2", lastDayOverdueStage2 - 1)
                            .setParameter("lastDayOverdueStage2", lastDayOverdueStage2)
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            if (!rows.isEmpty()) {
                paymentsAmountInPeriod.setPaidCommonAllTotal(Double.parseDouble(rows.get(0)[0].toString()));
                paymentsAmountInPeriod.setPaidPrincipleAllTotal(Double.parseDouble(rows.get(0)[1].toString()));
                paymentsAmountInPeriod.setPaidCommonInActiveTotal(Double.parseDouble(rows.get(0)[2].toString()));
                paymentsAmountInPeriod.setPaidPrincipleInActiveTotal(Double.parseDouble(rows.get(0)[3].toString()));
                paymentsAmountInPeriod.setPaidCommonInExpiredStage1Total(Double.parseDouble(rows.get(0)[4].toString()));
                paymentsAmountInPeriod.setPaidPrincipleInExpiredStage1Total(Double.parseDouble(rows.get(0)[5].toString()));
                paymentsAmountInPeriod.setPaidCommonInExpiredStage2Total(Double.parseDouble(rows.get(0)[6].toString()));
                paymentsAmountInPeriod.setPaidPrincipleInExpiredStage2Total(Double.parseDouble(rows.get(0)[7].toString()));
                paymentsAmountInPeriod.setPaidCommonInExpiredStageOtherTotal(Double.parseDouble(rows.get(0)[8].toString()));
                paymentsAmountInPeriod.setPaidPrincipleInExpiredStageOtherTotal(Double.parseDouble(rows.get(0)[9].toString()));
                paymentsAmountInPeriod.setPaidCommonAllNew(Double.parseDouble(rows.get(0)[10].toString()));
                paymentsAmountInPeriod.setPaidPrincipleAllNew(Double.parseDouble(rows.get(0)[11].toString()));
                paymentsAmountInPeriod.setPaidCommonInActiveNew(Double.parseDouble(rows.get(0)[12].toString()));
                paymentsAmountInPeriod.setPaidPrincipleInActiveNew(Double.parseDouble(rows.get(0)[13].toString()));
                paymentsAmountInPeriod.setPaidCommonInExpiredStage1New(Double.parseDouble(rows.get(0)[14].toString()));
                paymentsAmountInPeriod.setPaidPrincipleInExpiredStage1New(Double.parseDouble(rows.get(0)[15].toString()));
                paymentsAmountInPeriod.setPaidCommonInExpiredStage2New(Double.parseDouble(rows.get(0)[16].toString()));
                paymentsAmountInPeriod.setPaidPrincipleInExpiredStage2New(Double.parseDouble(rows.get(0)[17].toString()));
                paymentsAmountInPeriod.setPaidCommonInExpiredStageOtherNew(Double.parseDouble(rows.get(0)[18].toString()));
                paymentsAmountInPeriod.setPaidPrincipleInExpiredStageOtherNew(Double.parseDouble(rows.get(0)[19].toString()));
                paymentsAmountInPeriod.setPaidCommonAllRepeat(Double.parseDouble(rows.get(0)[20].toString()));
                paymentsAmountInPeriod.setPaidPrincipleAllRepeat(Double.parseDouble(rows.get(0)[21].toString()));
                paymentsAmountInPeriod.setPaidCommonInActiveRepeat(Double.parseDouble(rows.get(0)[22].toString()));
                paymentsAmountInPeriod.setPaidPrincipleInActiveRepeat(Double.parseDouble(rows.get(0)[23].toString()));
                paymentsAmountInPeriod.setPaidCommonInExpiredStage1Repeat(Double.parseDouble(rows.get(0)[24].toString()));
                paymentsAmountInPeriod.setPaidPrincipleInExpiredStage1Repeat(Double.parseDouble(rows.get(0)[25].toString()));
                paymentsAmountInPeriod.setPaidCommonInExpiredStage2Repeat(Double.parseDouble(rows.get(0)[26].toString()));
                paymentsAmountInPeriod.setPaidPrincipleInExpiredStage2Repeat(Double.parseDouble(rows.get(0)[27].toString()));
                paymentsAmountInPeriod.setPaidCommonInExpiredStageOtherRepeat(Double.parseDouble(rows.get(0)[28].toString()));
                paymentsAmountInPeriod.setPaidPrincipleInExpiredStageOtherRepeat(Double.parseDouble(rows.get(0)[29].toString()));


                paymentsAmountInPeriod.setPaidIncomeAllTotal(paymentsAmountInPeriod.getPaidCommonAllTotal() -
                        paymentsAmountInPeriod.getPaidPrincipleAllTotal());
                paymentsAmountInPeriod.setPaidIncomeAllNew(paymentsAmountInPeriod.getPaidCommonAllNew() -
                        paymentsAmountInPeriod.getPaidPrincipleAllNew());
                paymentsAmountInPeriod.setPaidIncomeAllRepeat(paymentsAmountInPeriod.getPaidCommonAllRepeat() -
                        paymentsAmountInPeriod.getPaidPrincipleAllRepeat());

                paymentsAmountInPeriod.setPaidIncomeInActiveTotal(paymentsAmountInPeriod.getPaidCommonInActiveTotal() -
                        paymentsAmountInPeriod.getPaidPrincipleInActiveTotal());
                paymentsAmountInPeriod.setPaidIncomeInActiveNew(paymentsAmountInPeriod.getPaidCommonInActiveNew() -
                        paymentsAmountInPeriod.getPaidPrincipleInActiveNew());
                paymentsAmountInPeriod.setPaidIncomeInActiveRepeat(paymentsAmountInPeriod.getPaidCommonInActiveRepeat() -
                        paymentsAmountInPeriod.getPaidPrincipleInActiveRepeat());

                paymentsAmountInPeriod.setPaidIncomeInExpiredStage1Total(paymentsAmountInPeriod.getPaidCommonInExpiredStage1Total() -
                        paymentsAmountInPeriod.getPaidPrincipleInExpiredStage1Total());
                paymentsAmountInPeriod.setPaidIncomeInExpiredStage1New(paymentsAmountInPeriod.getPaidCommonInExpiredStage1New() -
                        paymentsAmountInPeriod.getPaidPrincipleInExpiredStage1New());
                paymentsAmountInPeriod.setPaidIncomeInExpiredStage1Repeat(paymentsAmountInPeriod.getPaidCommonInExpiredStage1Repeat() -
                        paymentsAmountInPeriod.getPaidPrincipleInExpiredStage1Repeat());

                paymentsAmountInPeriod.setPaidIncomeInExpiredStage2Total(paymentsAmountInPeriod.getPaidCommonInExpiredStage2Total() -
                        paymentsAmountInPeriod.getPaidPrincipleInExpiredStage2Total());
                paymentsAmountInPeriod.setPaidIncomeInExpiredStage2New(paymentsAmountInPeriod.getPaidCommonInExpiredStage2New() -
                        paymentsAmountInPeriod.getPaidPrincipleInExpiredStage2New());
                paymentsAmountInPeriod.setPaidIncomeInExpiredStage2Repeat(paymentsAmountInPeriod.getPaidCommonInExpiredStage2Repeat() -
                        paymentsAmountInPeriod.getPaidPrincipleInExpiredStage2Repeat());

                paymentsAmountInPeriod.setPaidIncomeInExpiredStageOtherTotal(paymentsAmountInPeriod.getPaidCommonInExpiredStageOtherTotal() -
                        paymentsAmountInPeriod.getPaidPrincipleInExpiredStageOtherTotal());
                paymentsAmountInPeriod.setPaidIncomeInExpiredStageOtherNew(paymentsAmountInPeriod.getPaidCommonInExpiredStageOtherNew() -
                        paymentsAmountInPeriod.getPaidPrincipleInExpiredStageOtherNew());
                paymentsAmountInPeriod.setPaidIncomeInExpiredStageOtherRepeat(paymentsAmountInPeriod.getPaidCommonInExpiredStageOtherRepeat() -
                        paymentsAmountInPeriod.getPaidPrincipleInExpiredStageOtherRepeat());

            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) +
                    " --> ReportCreditConveyorDaoImpl --> getPaymentsAmountInPeriod: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return paymentsAmountInPeriod;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ProlongationAmountInPeriod getProlongationAmountInPeriodForDateWhichRequestedThatDate(LocalDate dateFrom,
                                                                                                 Integer lastDayOverdueStage1,
                                                                                                 Integer lastDayOverdueStage2) {
        Session session = sessionFactory.openSession();
        ProlongationAmountInPeriod prolongationAmountInPeriod = new ProlongationAmountInPeriod();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select sum(ls2.principal) as started_prolongation,\n" +
                                    "       sum(case when la.client_type = 'POTENTIAL' then ls2.principal else 0 end) as started_prolongation_new,\n" +
                                    "       sum(case when la.client_type = 'REPEAT' then ls2.principal else 0 end) as started_prolongation_repeat,\n" +
                                    "       sum(case when (ls2.loan_status = 'ACTIVE') then ls2.principal else 0 end) as started_prolongation_from_active,\n" +
                                    "       sum(case when (ls2.loan_status = 'ACTIVE') and la.client_type = 'POTENTIAL' then ls2.principal else 0 end) as started_prolongation_from_active_new,\n" +
                                    "       sum(case when (ls2.loan_status = 'ACTIVE') and la.client_type = 'REPEAT' then ls2.principal else 0 end) as started_prolongation_from_active_repeat,\n" +
                                    "       sum(case when (ls2.loan_status = 'GRACE' or (ls2.loan_status = 'PROLONGED' and li2.id is not null\n" +
                                    "           and li.activation_date = li.period_start) or (ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= 1\n" +
                                    "           and ls2.days_overdue <= :preLastDayOverdueStage1)) then ls2.principal else 0 end) as started_prolongation_from_expired_stage_1,\n" +
                                    "       sum(case when (ls2.loan_status = 'GRACE' or (ls2.loan_status = 'PROLONGED' and li2.id is not null\n" +
                                    "           and li.activation_date = li.period_start) or (ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= 1\n" +
                                    "           and ls2.days_overdue <= :preLastDayOverdueStage1))\n" +
                                    "           and la.client_type = 'POTENTIAL' then ls2.principal else 0 end) as started_prolongation_from_expired_stage_1_new,\n" +
                                    "       sum(case when (ls2.loan_status = 'GRACE' or (ls2.loan_status = 'PROLONGED' and li2.id is not null\n" +
                                    "           and li.activation_date = li.period_start) or (ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= 1\n" +
                                    "           and ls2.days_overdue <= :preLastDayOverdueStage1))\n" +
                                    "           and la.client_type = 'REPEAT' then ls2.principal else 0 end) as started_prolongation_from_expired_stage_1_repeat,\n" +
                                    "       sum(case when ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= :lastDayOverdueStage1 and ls2.days_overdue <= :preLastDayOverdueStage2\n" +
                                    "                    then ls2.principal else 0 end) as started_prolongation_from_expired_stage_2,\n" +
                                    "       sum(case when ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= :lastDayOverdueStage1 and ls2.days_overdue <= :preLastDayOverdueStage2\n" +
                                    "           and la.client_type = 'POTENTIAL'\n" +
                                    "                    then ls2.principal else 0 end) as started_prolongation_from_expired_stage_2_new,\n" +
                                    "       sum(case when ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= :lastDayOverdueStage1 and ls2.days_overdue <= :preLastDayOverdueStage2\n" +
                                    "           and la.client_type = 'REPEAT'\n" +
                                    "                    then ls2.principal else 0 end) as started_prolongation_from_expired_stage_2_repeat,\n" +
                                    "       sum(case when ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= :lastDayOverdueStage2\n" +
                                    "                    then ls2.principal else 0 end) as started_prolongation_from_expired_stage_other,\n" +
                                    "       sum(case when ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= :lastDayOverdueStage2\n" +
                                    "           and la.client_type = 'POTENTIAL'\n" +
                                    "                    then ls2.principal else 0 end) as started_prolongation_from_expired_stage_other_new,\n" +
                                    "       sum(case when ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= :lastDayOverdueStage2\n" +
                                    "           and la.client_type = 'REPEAT'\n" +
                                    "                    then ls2.principal else 0 end) as started_prolongation_from_expired_stage_other_repeat\n" +
                                    "from loan_installment li\n" +
                                    "         left join loan l on l.id = li.loan_id\n" +
                                    "         left join loan_application la on l.application_id = la.id\n" +
                                    "         left join loan_snapshot ls on li.loan_id = ls.loan_id and li.period_start = ls.date\n" +
                                    "         left join loan_snapshot ls2 on li.loan_id = ls2.loan_id and li.period_start - 1 = ls2.date\n" +
                                    "         left join loan_installment li2 on li2.loan_id = li.loan_id and li2.period_end + 1 = li.period_start\n" +
                                    "where li.period_start = :dateFrom\n" +
                                    "  and ls.date = :dateFrom\n" +
                                    "  and ls2.date = :preDateFrom\n" +
                                    "  and li.activation_date = li.period_start;")
                            .addScalar("started_prolongation", new DoubleType())
                            .addScalar("started_prolongation_new", new DoubleType())
                            .addScalar("started_prolongation_repeat", new DoubleType())
                            .addScalar("started_prolongation_from_active", new DoubleType())
                            .addScalar("started_prolongation_from_active_new", new DoubleType())
                            .addScalar("started_prolongation_from_active_repeat", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_1", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_1_new", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_1_repeat", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_2", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_2_new", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_2_repeat", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_other", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_other_new", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_other_repeat", new DoubleType())
                            .setParameter("preLastDayOverdueStage1", lastDayOverdueStage1 - 1)
                            .setParameter("lastDayOverdueStage1", lastDayOverdueStage1)
                            .setParameter("preLastDayOverdueStage2", lastDayOverdueStage2 - 1)
                            .setParameter("lastDayOverdueStage2", lastDayOverdueStage2)
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("preDateFrom", dateFrom.minusDays(1))
                            .list();

            if (!rows.isEmpty()) {
                prolongationAmountInPeriod.setProlongationAllTotal(Double.parseDouble(rows.get(0)[0].toString()));
                prolongationAmountInPeriod.setProlongationAllNew(Double.parseDouble(rows.get(0)[1].toString()));
                prolongationAmountInPeriod.setProlongationAllRepeat(Double.parseDouble(rows.get(0)[2].toString()));
                prolongationAmountInPeriod.setProlongationFromActiveTotal(Double.parseDouble(rows.get(0)[3].toString()));
                prolongationAmountInPeriod.setProlongationFromActiveNew(Double.parseDouble(rows.get(0)[4].toString()));
                prolongationAmountInPeriod.setProlongationFromActiveRepeat(Double.parseDouble(rows.get(0)[5].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStage1Total(Double.parseDouble(rows.get(0)[6].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStage1New(Double.parseDouble(rows.get(0)[7].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStage1Repeat(Double.parseDouble(rows.get(0)[8].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStage2Total(Double.parseDouble(rows.get(0)[9].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStage2New(Double.parseDouble(rows.get(0)[10].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStage2Repeat(Double.parseDouble(rows.get(0)[11].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStageOtherTotal(Double.parseDouble(rows.get(0)[12].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStageOtherNew(Double.parseDouble(rows.get(0)[13].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStageOtherRepeat(Double.parseDouble(rows.get(0)[14].toString()));
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) +
                    " --> ReportCreditConveyorDaoImpl --> getProlongationAmountInPeriodForDateWhichRequestedThatDate: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return prolongationAmountInPeriod;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ProlongationAmountInPeriod getProlongationAmountInPeriodFromNextDate(LocalDate dateFrom, LocalDate dateTo,
                                                                                Integer lastDayOverdueStage1,
                                                                                Integer lastDayOverdueStage2) {
        Session session = sessionFactory.openSession();
        ProlongationAmountInPeriod prolongationAmountInPeriod = new ProlongationAmountInPeriod();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "       sum(ls2.principal) as started_prolongation,\n" +
                            "       sum(case when la.client_type = 'POTENTIAL' then ls2.principal else 0 end) as started_prolongation_new,\n" +
                            "       sum(case when la.client_type = 'REPEAT' then ls2.principal else 0 end) as started_prolongation_repeat,\n" +
                            "       sum(case when (ls2.loan_status = 'ACTIVE' or (ls2.loan_status = 'PROLONGED' and li2.id is not null\n" +
                            "           and li.activation_date < li.period_start)) then ls2.principal else 0 end) as started_prolongation_from_active,\n" +
                            "       sum(case when (ls2.loan_status = 'ACTIVE' or (ls2.loan_status = 'PROLONGED' and li2.id is not null\n" +
                            "           and li.activation_date < li.period_start)) and la.client_type = 'POTENTIAL' then ls2.principal else 0 end) as started_prolongation_from_active_new,\n" +
                            "       sum(case when (ls2.loan_status = 'ACTIVE' or (ls2.loan_status = 'PROLONGED' and li2.id is not null\n" +
                            "           and li.activation_date < li.period_start)) and la.client_type = 'REPEAT' then ls2.principal else 0 end) as started_prolongation_from_active_repeat,\n" +
                            "       sum(case when (ls2.loan_status = 'GRACE' or (ls2.loan_status = 'PROLONGED' and li2.id is not null\n" +
                            "           and li.activation_date = li.period_start) or (ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= 1\n" +
                            "           and ls2.days_overdue <= :preLastDayOverdueStage1)) then ls2.principal else 0 end) as started_prolongation_from_expired_stage_1,\n" +
                            "       sum(case when (ls2.loan_status = 'GRACE' or (ls2.loan_status = 'PROLONGED' and li2.id is not null\n" +
                            "           and li.activation_date = li.period_start) or (ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= 1\n" +
                            "           and ls2.days_overdue <= :preLastDayOverdueStage1))\n" +
                            "           and la.client_type = 'POTENTIAL' then ls2.principal else 0 end) as started_prolongation_from_expired_stage_1_new,\n" +
                            "       sum(case when (ls2.loan_status = 'GRACE' or (ls2.loan_status = 'PROLONGED' and li2.id is not null\n" +
                            "           and li.activation_date = li.period_start) or (ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= 1\n" +
                            "           and ls2.days_overdue <= :preLastDayOverdueStage1))\n" +
                            "           and la.client_type = 'REPEAT' then ls2.principal else 0 end) as started_prolongation_from_expired_stage_1_repeat,\n" +
                            "       sum(case when ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= :lastDayOverdueStage1 and ls2.days_overdue <= :preLastDayOverdueStage2\n" +
                            "                    then ls2.principal else 0 end) as started_prolongation_from_expired_stage_2,\n" +
                            "       sum(case when ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= :lastDayOverdueStage1 and ls2.days_overdue <= :preLastDayOverdueStage2\n" +
                            "           and la.client_type = 'POTENTIAL'\n" +
                            "                    then ls2.principal else 0 end) as started_prolongation_from_expired_stage_2_new,\n" +
                            "       sum(case when ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= :lastDayOverdueStage1 and ls2.days_overdue <= :preLastDayOverdueStage2\n" +
                            "           and la.client_type = 'REPEAT'\n" +
                            "                    then ls2.principal else 0 end) as started_prolongation_from_expired_stage_2_repeat,\n" +
                            "       sum(case when ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= :lastDayOverdueStage2\n" +
                            "                    then ls2.principal else 0 end) as started_prolongation_from_expired_stage_other,\n" +
                            "       sum(case when ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= :lastDayOverdueStage2\n" +
                            "           and la.client_type = 'POTENTIAL'\n" +
                            "                    then ls2.principal else 0 end) as started_prolongation_from_expired_stage_other_new,\n" +
                            "       sum(case when ls2.loan_status = 'EXPIRED' and ls2.days_overdue >= :lastDayOverdueStage2\n" +
                            "           and la.client_type = 'REPEAT'\n" +
                            "                    then ls2.principal else 0 end) as started_prolongation_from_expired_stage_other_repeat\n" +
                            "from loan_installment li\n" +
                            "         left join loan l on l.id = li.loan_id\n" +
                            "         left join loan_application la on l.application_id = la.id\n" +
                            "         left join loan_snapshot ls on li.loan_id = ls.loan_id and li.period_start = ls.date\n" +
                            "         left join loan_snapshot ls2 on li.loan_id = ls2.loan_id and li.period_start - 1 = ls2.date\n" +
                            "         left join loan_installment li2 on li2.loan_id = li.loan_id and li2.period_end + 1 = li.period_start\n" +
                            "where li.prolonged is true\n" +
                            "  and li.period_start >= :afterDateFrom\n" +
                            "  and li.period_start <= :dateTo\n" +
                            "  and ls.date >= :afterDateFrom\n" +
                            "  and ls.date <= :dateTo\n" +
                            "  and ls2.date >= :dateFrom\n" +
                            "  and ls2.date <= :preDateTo ;")
                            .addScalar("started_prolongation", new DoubleType())
                            .addScalar("started_prolongation_new", new DoubleType())
                            .addScalar("started_prolongation_repeat", new DoubleType())
                            .addScalar("started_prolongation_from_active", new DoubleType())
                            .addScalar("started_prolongation_from_active_new", new DoubleType())
                            .addScalar("started_prolongation_from_active_repeat", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_1", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_1_new", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_1_repeat", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_2", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_2_new", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_2_repeat", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_other", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_other_new", new DoubleType())
                            .addScalar("started_prolongation_from_expired_stage_other_repeat", new DoubleType())
                            .setParameter("preLastDayOverdueStage1", lastDayOverdueStage1 - 1)
                            .setParameter("lastDayOverdueStage1", lastDayOverdueStage1)
                            .setParameter("preLastDayOverdueStage2", lastDayOverdueStage2 - 1)
                            .setParameter("lastDayOverdueStage2", lastDayOverdueStage2)
                            .setParameter("afterDateFrom", dateFrom.plusDays(1))
                            .setParameter("dateTo", dateTo)
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("preDateTo", dateTo.minusDays(1))
                            .list();

            if (!rows.isEmpty()) {
                prolongationAmountInPeriod.setProlongationAllTotal(Double.parseDouble(rows.get(0)[0].toString()));
                prolongationAmountInPeriod.setProlongationAllNew(Double.parseDouble(rows.get(0)[1].toString()));
                prolongationAmountInPeriod.setProlongationAllRepeat(Double.parseDouble(rows.get(0)[2].toString()));
                prolongationAmountInPeriod.setProlongationFromActiveTotal(Double.parseDouble(rows.get(0)[3].toString()));
                prolongationAmountInPeriod.setProlongationFromActiveNew(Double.parseDouble(rows.get(0)[4].toString()));
                prolongationAmountInPeriod.setProlongationFromActiveRepeat(Double.parseDouble(rows.get(0)[5].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStage1Total(Double.parseDouble(rows.get(0)[6].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStage1New(Double.parseDouble(rows.get(0)[7].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStage1Repeat(Double.parseDouble(rows.get(0)[8].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStage2Total(Double.parseDouble(rows.get(0)[9].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStage2New(Double.parseDouble(rows.get(0)[10].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStage2Repeat(Double.parseDouble(rows.get(0)[11].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStageOtherTotal(Double.parseDouble(rows.get(0)[12].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStageOtherNew(Double.parseDouble(rows.get(0)[13].toString()));
                prolongationAmountInPeriod.setProlongationFromExpiredStageOtherRepeat(Double.parseDouble(rows.get(0)[14].toString()));
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) +
                    " --> ReportCreditConveyorDaoImpl --> getProlongationAmountInPeriodFromNextDate: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return prolongationAmountInPeriod;
    }

    @Override
    @SuppressWarnings("unchecked")
    public IssuedAmountInPeriod getIssuedAmountInPeriod(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        IssuedAmountInPeriod issuedAmountInPeriod = new IssuedAmountInPeriod();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "       sum(l.amount) as amount,\n" +
                            "       sum(case when la.client_type = 'POTENTIAL' then l.amount else 0 end) as amount_new,\n" +
                            "       sum(case when la.client_type = 'REPEAT' then l.amount else 0 end) as amount_repeat\n" +
                            "from loan l\n" +
                            "         left join loan_application la on l.application_id = la.id\n" +
                            "where date(l.issued_at) >= :dateFrom\n" +
                            "  and date(l.issued_at) <= :dateTo ;")
                            .addScalar("amount", new DoubleType())
                            .addScalar("amount_new", new DoubleType())
                            .addScalar("amount_repeat", new DoubleType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            if (!rows.isEmpty()) {
                issuedAmountInPeriod.setAmountTotal(Double.parseDouble(rows.get(0)[0].toString()));
                issuedAmountInPeriod.setAmountNew(Double.parseDouble(rows.get(0)[1].toString()));
                issuedAmountInPeriod.setAmountRepeat(Double.parseDouble(rows.get(0)[2].toString()));
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) +
                    " --> ReportCreditConveyorDaoImpl --> getIssuedAmountInPeriod: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return issuedAmountInPeriod;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NewStartedOverdueAmountInPeriod getNewStartedOverdueAmountInPeriodOnlyAfterProlongation(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        NewStartedOverdueAmountInPeriod newStartedOverdueAmountInPeriod = new NewStartedOverdueAmountInPeriod();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                                    "       sum(case when ((ls.loan_status in ('FINISHED', 'EXPIRED'))\n" +
                                    "           or(ls.loan_status = 'PROLONGED' and li2.id is not null and li2.activation_date = li2.period_start))\n" +
                                    "                    then ls2.principal else 0 end) as start_to_expired_stage_1,\n" +
                                    "       sum(case when ((ls.loan_status in ('FINISHED', 'EXPIRED'))\n" +
                                    "           or(ls.loan_status = 'PROLONGED' and li2.id is not null and li2.activation_date = li2.period_start))\n" +
                                    "           and la.client_type = 'POTENTIAL'\n" +
                                    "                    then ls2.principal else 0 end) as start_to_expired_stage_1_new,\n" +
                                    "       sum(case when ((ls.loan_status in ('FINISHED', 'EXPIRED'))\n" +
                                    "           or(ls.loan_status = 'PROLONGED' and li2.id is not null and li2.activation_date = li2.period_start))\n" +
                                    "           and la.client_type = 'REPEAT'\n" +
                                    "                    then ls2.principal else 0 end) as start_to_expired_stage_1_repeat\n" +
                                    "from loan_installment li\n" +
                                    "         left join loan_snapshot ls on li.loan_id = ls.loan_id and li.period_end + 1 = ls.date\n" +
                                    "         left join loan_snapshot ls2 on li.loan_id = ls2.loan_id and li.period_end = ls2.date\n" +
                                    "         left join loan l on li.loan_id = l.id\n" +
                                    "         left join loan_application la on l.application_id = la.id\n" +
                                    "         left join loan_installment li2 on li2.loan_id = li.loan_id and li2.period_start = li.period_end + 1\n" +
                                    "where li.prolonged is true\n" +
                                    "  and li.period_end >= :dateFrom\n" +
                                    "  and li.period_end <= :preDateTo\n" +
                                    "  and ls2.date >= :dateFrom\n" +
                                    "  and ls2.date <= :preDateTo\n" +
                                    "  and ls.date >= :afterDateFrom\n" +
                                    "  and ls.date <= :dateTo ;")
                            .addScalar("start_to_expired_stage_1", new DoubleType())
                            .addScalar("start_to_expired_stage_1_new", new DoubleType())
                            .addScalar("start_to_expired_stage_1_repeat", new DoubleType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("preDateTo", dateTo.minusDays(1))
                            .setParameter("afterDateFrom", dateFrom.plusDays(1))
                            .setParameter("dateTo", dateTo)
                            .list();

            if (!rows.isEmpty()) {
                newStartedOverdueAmountInPeriod.setPrincipleStage1Total(Double.parseDouble(rows.get(0)[0].toString()));
                newStartedOverdueAmountInPeriod.setPrincipleStage1New(Double.parseDouble(rows.get(0)[1].toString()));
                newStartedOverdueAmountInPeriod.setPrincipleStage1Repeat(Double.parseDouble(rows.get(0)[2].toString()));
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) +
                    " --> ReportCreditConveyorDaoImpl --> getNewStartedOverdueAmountInPeriod: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return newStartedOverdueAmountInPeriod;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NewStartedOverdueAmountInPeriod getNewStartedOverdueAmountInPeriodWithoutProlongation(LocalDate dateFrom, LocalDate dateTo,
                                                                                                 Integer lastDayOverdueStage1,
                                                                                                 Integer lastDayOverdueStage2) {
        Session session = sessionFactory.openSession();
        NewStartedOverdueAmountInPeriod newStartedOverdueAmountInPeriod = new NewStartedOverdueAmountInPeriod();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                            "       sum(case when ls.loan_status = 'GRACE' then ls.principal else 0 end) as start_to_expired_stage_1,\n" +
                            "       sum(case when ls.loan_status = 'EXPIRED' and ls.days_overdue = :lastDayOverdueStage1 then ls.principal else 0 end) as start_to_expired_stage_2,\n" +
                            "       sum(case when ls.loan_status = 'EXPIRED' and ls.days_overdue = :lastDayOverdueStage2 then ls.principal else 0 end) as start_to_expired_stage_other,\n" +
                            "       sum(case when ls.loan_status = 'GRACE'\n" +
                            "                         and la.client_type = 'POTENTIAL' then ls.principal else 0 end) as start_to_expired_stage_1_new,\n" +
                            "       sum(case when ls.loan_status = 'EXPIRED'\n" +
                            "                        and ls.days_overdue = :lastDayOverdueStage1\n" +
                            "                         and la.client_type = 'POTENTIAL' then ls.principal else 0 end) as start_to_expired_stage_2_new,\n" +
                            "       sum(case when ls.loan_status = 'EXPIRED'\n" +
                            "                         and ls.days_overdue = :lastDayOverdueStage2\n" +
                            "                         and la.client_type = 'POTENTIAL' then ls.principal else 0 end) as start_to_expired_stage_other_new,\n" +
                            "       sum(case when ls.loan_status = 'GRACE'\n" +
                            "                         and la.client_type = 'REPEAT' then ls.principal else 0 end) as start_to_expired_stage_1_repeat,\n" +
                            "       sum(case when ls.loan_status = 'EXPIRED'\n" +
                            "                         and ls.days_overdue = :lastDayOverdueStage1\n" +
                            "                         and la.client_type = 'REPEAT' then ls.principal else 0 end) as start_to_expired_stage_2_repeat,\n" +
                            "       sum(case when ls.loan_status = 'EXPIRED'\n" +
                            "                         and ls.days_overdue = :lastDayOverdueStage2\n" +
                            "                         and la.client_type = 'REPEAT' then ls.principal else 0 end) as start_to_expired_stage_other_repeat\n" +
                            "from loan_snapshot ls\n" +
                            "         left join loan l on ls.loan_id = l.id\n" +
                            "         left join loan_application la on l.application_id = la.id\n" +
                            "where date(ls.date) >= :dateFrom\n" +
                            "  and date(ls.date) <= :preDateTo\n" +
                            "  and ls.loan_status in ('GRACE', 'EXPIRED');")
                            .addScalar("start_to_expired_stage_1", new DoubleType())
                            .addScalar("start_to_expired_stage_2", new DoubleType())
                            .addScalar("start_to_expired_stage_other", new DoubleType())
                            .addScalar("start_to_expired_stage_1_new", new DoubleType())
                            .addScalar("start_to_expired_stage_2_new", new DoubleType())
                            .addScalar("start_to_expired_stage_other_new", new DoubleType())
                            .addScalar("start_to_expired_stage_1_repeat", new DoubleType())
                            .addScalar("start_to_expired_stage_2_repeat", new DoubleType())
                            .addScalar("start_to_expired_stage_other_repeat", new DoubleType())
                            .setParameter("lastDayOverdueStage1",lastDayOverdueStage1)
                            .setParameter("lastDayOverdueStage2", lastDayOverdueStage2)
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("preDateTo", dateTo.minusDays(1))
                            .list();

            if (!rows.isEmpty()) {
                newStartedOverdueAmountInPeriod.setPrincipleStage1Total(Double.parseDouble(rows.get(0)[0].toString()));
                newStartedOverdueAmountInPeriod.setPrincipleStage2Total(Double.parseDouble(rows.get(0)[1].toString()));
                newStartedOverdueAmountInPeriod.setPrincipleStageOtherTotal(Double.parseDouble(rows.get(0)[2].toString()));
                newStartedOverdueAmountInPeriod.setPrincipleStage1New(Double.parseDouble(rows.get(0)[3].toString()));
                newStartedOverdueAmountInPeriod.setPrincipleStage2New(Double.parseDouble(rows.get(0)[4].toString()));
                newStartedOverdueAmountInPeriod.setPrincipleStageOtherNew(Double.parseDouble(rows.get(0)[5].toString()));
                newStartedOverdueAmountInPeriod.setPrincipleStage1Repeat(Double.parseDouble(rows.get(0)[6].toString()));
                newStartedOverdueAmountInPeriod.setPrincipleStage2Repeat(Double.parseDouble(rows.get(0)[7].toString()));
                newStartedOverdueAmountInPeriod.setPrincipleStageOtherRepeat(Double.parseDouble(rows.get(0)[8].toString()));
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) +
                    " --> ReportCreditConveyorDaoImpl --> getNewStartedOverdueAmountInPeriod: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return newStartedOverdueAmountInPeriod;
    }

    @Override
    @SuppressWarnings("unchecked")
    public LoanPortfolioOfDate getLoanPortfolioOfDate(LocalDate date, Integer lastDayOverdueStage1,
                                                      Integer lastDayOverdueStage2) {
        Session session = sessionFactory.openSession();
        LoanPortfolioOfDate loanPortfolioOfDate = new LoanPortfolioOfDate();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                                    "       sum(case when (ls.loan_status not in ('FINISHED', 'EXPIRED', 'GRACE', 'PROLONGED'))\n" +
                                    "           or (ls.loan_status = 'PROLONGED' and li.id is not null)\n" +
                                    "           or (ls.loan_status = 'PROLONGED' and li2.id is not null) then ls.principal else 0 end) as principle_active,\n" +
                                    "       sum(case when (ls.loan_status = 'GRACE')\n" +
                                    "                         or (ls.loan_status = 'PROLONGED' and li.id is null and li2.id is null)\n" +
                                    "                         or (ls.loan_status = 'EXPIRED'\n" +
                                    "                         and ls.days_overdue >= 1 and ls.days_overdue <= :preLastDayOverdueStage1) then ls.principal else 0 end) as expired_stage_1,\n" +
                                    "       sum(case when ls.loan_status = 'EXPIRED'\n" +
                                    "                         and ls.days_overdue >= :lastDayOverdueStage1 and ls.days_overdue <= :preLastDayOverdueStage2 then ls.principal else 0 end) as expired_stage_2,\n" +
                                    "       sum(case when ls.loan_status = 'EXPIRED'\n" +
                                    "                         and ls.days_overdue >= :lastDayOverdueStage2 then ls.principal else 0 end) as expired_stage_other,\n" +
                                    "       sum(case when ((ls.loan_status not in ('FINISHED', 'EXPIRED', 'GRACE', 'PROLONGED'))\n" +
                                    "           or (ls.loan_status = 'PROLONGED' and li.id is not null)\n" +
                                    "           or (ls.loan_status = 'PROLONGED' and li2.id is not null))\n" +
                                    "           and la.client_type = 'POTENTIAL' then ls.principal else 0 end) as principle_active_new,\n" +
                                    "       sum(case when ((ls.loan_status = 'GRACE')\n" +
                                    "           or (ls.loan_status = 'PROLONGED' and li.id is null and li2.id is null)\n" +
                                    "           or (ls.loan_status = 'EXPIRED'\n" +
                                    "               and ls.days_overdue >= 1 and ls.days_overdue <= :preLastDayOverdueStage1))\n" +
                                    "           and la.client_type = 'POTENTIAL' then ls.principal else 0 end) as expired_stage_1_new,\n" +
                                    "       sum(case when ls.loan_status = 'EXPIRED'\n" +
                                    "                         and ls.days_overdue >= :lastDayOverdueStage1 and ls.days_overdue <= :preLastDayOverdueStage2\n" +
                                    "                         and la.client_type = 'POTENTIAL' then ls.principal else 0 end) as expired_stage_2_new,\n" +
                                    "       sum(case when ls.loan_status = 'EXPIRED'\n" +
                                    "                         and ls.days_overdue >= :lastDayOverdueStage2\n" +
                                    "                         and la.client_type = 'POTENTIAL' then ls.principal else 0 end) as expired_stage_other_new,\n" +
                                    "       sum(case when ((ls.loan_status not in ('FINISHED', 'EXPIRED', 'GRACE', 'PROLONGED'))\n" +
                                    "           or (ls.loan_status = 'PROLONGED' and li.id is not null)\n" +
                                    "           or (ls.loan_status = 'PROLONGED' and li2.id is not null))\n" +
                                    "           and la.client_type = 'REPEAT' then ls.principal else 0 end) as principle_active_repeat,\n" +
                                    "       sum(case when ((ls.loan_status = 'GRACE')\n" +
                                    "           or (ls.loan_status = 'PROLONGED' and li.id is null and li2.id is null)\n" +
                                    "           or (ls.loan_status = 'EXPIRED'\n" +
                                    "               and ls.days_overdue >= 1 and ls.days_overdue <= :preLastDayOverdueStage1))\n" +
                                    "           and la.client_type = 'REPEAT' then ls.principal else 0 end) as expired_stage_1_repeat,\n" +
                                    "       sum(case when ls.loan_status = 'EXPIRED'\n" +
                                    "                         and ls.days_overdue >= :lastDayOverdueStage1 and ls.days_overdue <= :preLastDayOverdueStage2\n" +
                                    "                         and la.client_type = 'REPEAT' then ls.principal else 0 end) as expired_stage_2_repeat,\n" +
                                    "       sum(case when ls.loan_status = 'EXPIRED'\n" +
                                    "                         and ls.days_overdue >= :lastDayOverdueStage2\n" +
                                    "                         and la.client_type = 'REPEAT' then ls.principal else 0 end) as expired_stage_other_repeat\n" +
                                    "from loan_snapshot ls\n" +
                                    "         left join loan l on ls.loan_id = l.id\n" +
                                    "         left join loan_application la on l.application_id = la.id\n" +
                                    "         left join loan_installment li on ls.loan_id = li.loan_id and ls.date >= li.period_start and ls.date < li.period_end\n" +
                                    "         left join loan_installment li2 on ls.loan_id = li2.loan_id and li2.period_start = :date and li2.activation_date < :date\n" +
                                    "where date(ls.date) = :preDate\n" +
                                    "  and ls.loan_status <> 'FINISHED';")
                            .addScalar("principle_active", new DoubleType())
                            .addScalar("expired_stage_1", new DoubleType())
                            .addScalar("expired_stage_2", new DoubleType())
                            .addScalar("expired_stage_other", new DoubleType())
                            .addScalar("principle_active_new", new DoubleType())
                            .addScalar("expired_stage_1_new", new DoubleType())
                            .addScalar("expired_stage_2_new", new DoubleType())
                            .addScalar("expired_stage_other_new", new DoubleType())
                            .addScalar("principle_active_repeat", new DoubleType())
                            .addScalar("expired_stage_1_repeat", new DoubleType())
                            .addScalar("expired_stage_2_repeat", new DoubleType())
                            .addScalar("expired_stage_other_repeat", new DoubleType())
                            .setParameter("preLastDayOverdueStage1", lastDayOverdueStage1 - 1)
                            .setParameter("lastDayOverdueStage1", lastDayOverdueStage1)
                            .setParameter("preLastDayOverdueStage2", lastDayOverdueStage2 - 1)
                            .setParameter("lastDayOverdueStage2", lastDayOverdueStage2)
                            .setParameter("preDate", date.minusDays(1))
                            .setParameter("date", date)
                            .list();

            if (!rows.isEmpty()) {
                loanPortfolioOfDate.setPrincipleActiveTotal(Double.parseDouble(rows.get(0)[0].toString()));
                loanPortfolioOfDate.setPrincipleExpiredStage1Total(Double.parseDouble(rows.get(0)[1].toString()));
                loanPortfolioOfDate.setPrincipleExpiredStage2Total(Double.parseDouble(rows.get(0)[2].toString()));
                loanPortfolioOfDate.setPrincipleExpiredStageOtherTotal(Double.parseDouble(rows.get(0)[3].toString()));
                loanPortfolioOfDate.setPrincipleActiveNew(Double.parseDouble(rows.get(0)[4].toString()));
                loanPortfolioOfDate.setPrincipleExpiredStage1New(Double.parseDouble(rows.get(0)[5].toString()));
                loanPortfolioOfDate.setPrincipleExpiredStage2New(Double.parseDouble(rows.get(0)[6].toString()));
                loanPortfolioOfDate.setPrincipleExpiredStageOtherNew(Double.parseDouble(rows.get(0)[7].toString()));
                loanPortfolioOfDate.setPrincipleActiveRepeat(Double.parseDouble(rows.get(0)[8].toString()));
                loanPortfolioOfDate.setPrincipleExpiredStage1Repeat(Double.parseDouble(rows.get(0)[9].toString()));
                loanPortfolioOfDate.setPrincipleExpiredStage2Repeat(Double.parseDouble(rows.get(0)[10].toString()));
                loanPortfolioOfDate.setPrincipleExpiredStageOtherRepeat(Double.parseDouble(rows.get(0)[11].toString()));
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) +
                    " --> ReportCreditConveyorDaoImpl --> getLoanPortfolioOfDate: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return loanPortfolioOfDate;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<AppAndLoanBySource> getAppAndLoanBySourceList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<AppAndLoanBySource> appAndLoanBySourceList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (r.source) \n" +
                                    "r.source, r.count_apps, r.count_apps_new, " +
                                    "r.count_apps_repeat, r.count_loans, r.count_loans_new, r.count_loans_repeat, " +
                                    "r.amount, r.amount_new, r.amount_repeat \n" +
                                    "from \n" +
                                    "(select\n" +
                                    "    (case when apps.source is null then loans.source else apps.source end) as source,\n" +
                                    "    apps.count_apps,\n" +
                                    "    apps.count_apps_new,\n" +
                                    "    apps.count_apps_repeat,\n" +
                                    "    loans.count_loans,\n" +
                                    "    loans.count_loans_new,\n" +
                                    "    loans.count_loans_repeat,\n" +
                                    "    loans.amount,\n" +
                                    "    loans.amount_new,\n" +
                                    "    loans.amount_repeat\n" +
                                    "from\n" +
                                    "    (select\n" +
                                    "            (case when la.affiliate_utm_source is null then 'Organic' else laf.title end) as source,\n" +
                                    "            count(la.id) as count_apps,\n" +
                                    "            sum(case when la.client_type = 'POTENTIAL' then 1 else 0 end) as count_apps_new,\n" +
                                    "            sum(case when la.client_type = 'REPEAT' then 1 else 0 end) as count_apps_repeat\n" +
                                    "     from loan_application la\n" +
                                    "              left join loan_affiliate laf\n" +
                                    "                        on laf.code = la.affiliate_utm_source\n" +
                                    "     where date(la.requested_at) >= :dateFrom\n" +
                                    "       and date(la.requested_at) <= :dateTo\n" +
                                    "     group by (case when la.affiliate_utm_source is null then 'Organic' else laf.title end)) as apps\n" +
                                    "        left join\n" +
                                    "    (select\n" +
                                    "            (case when la.affiliate_utm_source is null then 'Organic' else laf.title end) as source,\n" +
                                    "            count(l.id) as count_loans,\n" +
                                    "            sum(case when la.client_type = 'POTENTIAL' then 1 else 0 end) as count_loans_new,\n" +
                                    "            sum(case when la.client_type = 'REPEAT' then 1 else 0 end) as count_loans_repeat,\n" +
                                    "            sum(l.amount) as amount,\n" +
                                    "            sum(case when la.client_type = 'POTENTIAL' then l.amount else 0 end) as amount_new,\n" +
                                    "            sum(case when la.client_type = 'REPEAT' then l.amount else 0 end) as amount_repeat\n" +
                                    "     from loan l\n" +
                                    "              left join loan_application la\n" +
                                    "                        on la.id = l.application_id\n" +
                                    "              left join loan_affiliate laf\n" +
                                    "                        on laf.code = la.affiliate_utm_source\n" +
                                    "     where date(l.issued_at) >= :dateFrom\n" +
                                    "       and date(l.issued_at) <= :dateTo\n" +
                                    "     group by (case when la.affiliate_utm_source is null then 'Organic' else laf.title end)) as loans\n" +
                                    "    on loans.source = apps.source\n" +
                                    "union\n" +
                                    "select\n" +
                                    "    (case when apps.source is null then loans.source else apps.source end) as source,\n" +
                                    "    apps.count_apps,\n" +
                                    "    apps.count_apps_new,\n" +
                                    "    apps.count_apps_repeat,\n" +
                                    "    loans.count_loans,\n" +
                                    "    loans.count_loans_new,\n" +
                                    "    loans.count_loans_repeat,\n" +
                                    "    loans.amount,\n" +
                                    "    loans.amount_new,\n" +
                                    "    loans.amount_repeat\n" +
                                    "from\n" +
                                    "    (select\n" +
                                    "            (case when la.affiliate_utm_source is null then 'Organic' else laf.title end) as source,\n" +
                                    "            count(la.id) as count_apps,\n" +
                                    "            sum(case when la.client_type = 'POTENTIAL' then 1 else 0 end) as count_apps_new,\n" +
                                    "            sum(case when la.client_type = 'REPEAT' then 1 else 0 end) as count_apps_repeat\n" +
                                    "     from loan_application la\n" +
                                    "              left join loan_affiliate laf\n" +
                                    "                        on laf.code = la.affiliate_utm_source\n" +
                                    "     where date(la.requested_at) >= :dateFrom\n" +
                                    "       and date(la.requested_at) <= :dateTo\n" +
                                    "     group by (case when la.affiliate_utm_source is null then 'Organic' else laf.title end)) as apps\n" +
                                    "        right join\n" +
                                    "    (select\n" +
                                    "            (case when la.affiliate_utm_source is null then 'Organic' else laf.title end) as source,\n" +
                                    "            count(l.id) as count_loans,\n" +
                                    "            sum(case when la.client_type = 'POTENTIAL' then 1 else 0 end) as count_loans_new,\n" +
                                    "            sum(case when la.client_type = 'REPEAT' then 1 else 0 end) as count_loans_repeat,\n" +
                                    "            sum(l.amount) as amount,\n" +
                                    "            sum(case when la.client_type = 'POTENTIAL' then l.amount else 0 end) as amount_new,\n" +
                                    "            sum(case when la.client_type = 'REPEAT' then l.amount else 0 end) as amount_repeat\n" +
                                    "     from loan l\n" +
                                    "              left join loan_application la\n" +
                                    "                        on la.id = l.application_id\n" +
                                    "              left join loan_affiliate laf\n" +
                                    "                        on laf.code = la.affiliate_utm_source\n" +
                                    "     where date(l.issued_at) >= :dateFrom\n" +
                                    "       and date(l.issued_at) <= :dateTo\n" +
                                    "     group by (case when la.affiliate_utm_source is null then 'Organic' else laf.title end)) as loans\n" +
                                    "    on loans.source = apps.source) as r ;")
                            .addScalar("source", new StringType())
                            .addScalar("count_apps", new LongType())
                            .addScalar("count_apps_new", new LongType())
                            .addScalar("count_apps_repeat", new LongType())
                            .addScalar("count_loans", new LongType())
                            .addScalar("count_loans_new", new LongType())
                            .addScalar("count_loans_repeat", new LongType())
                            .addScalar("amount", new DoubleType())
                            .addScalar("amount_new", new DoubleType())
                            .addScalar("amount_repeat", new DoubleType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                AppAndLoanBySource appAndLoanBySource = new AppAndLoanBySource();
                if (row[0] != null) {
                    appAndLoanBySource.setSource(row[0].toString());
                }
                if (row[1] != null) {
                    appAndLoanBySource.setCountAppsTotal(Long.parseLong(row[1].toString()));
                } else {
                    appAndLoanBySource.setCountAppsTotal(0L);
                }

                if (row[2] != null) {
                    appAndLoanBySource.setCountAppsNew(Long.parseLong(row[2].toString()));
                } else {
                    appAndLoanBySource.setCountAppsNew(0L);
                }

                if (row[3] != null) {
                    appAndLoanBySource.setCountAppsRepeat(Long.parseLong(row[3].toString()));
                } else {
                    appAndLoanBySource.setCountAppsRepeat(0L);
                }

                if (row[4] != null) {
                    appAndLoanBySource.setCountLoansTotal(Long.parseLong(row[4].toString()));
                } else {
                    appAndLoanBySource.setCountLoansTotal(0L);
                }

                if (row[5] != null) {
                    appAndLoanBySource.setCountLoansNew(Long.parseLong(row[5].toString()));
                } else {
                    appAndLoanBySource.setCountLoansNew(0L);
                }

                if (row[6] != null) {
                    appAndLoanBySource.setCountLoansRepeat(Long.parseLong(row[6].toString()));
                } else {
                    appAndLoanBySource.setCountLoansRepeat(0L);
                }

                if (row[7] != null) {
                    appAndLoanBySource.setAmountLoanTotal(Double.parseDouble(row[7].toString()));
                } else {
                    appAndLoanBySource.setAmountLoanTotal((double) 0);
                }

                if (row[8] != null) {
                    appAndLoanBySource.setAmountLoanNew(Double.parseDouble(row[8].toString()));
                } else {
                    appAndLoanBySource.setAmountLoanNew((double) 0);
                }

                if (row[9] != null) {
                    appAndLoanBySource.setAmountLoanRepeat(Double.parseDouble(row[9].toString()));
                } else {
                    appAndLoanBySource.setAmountLoanRepeat((double) 0);
                }

                appAndLoanBySourceList.add(appAndLoanBySource);
            }

        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + "ReportCreditConveyorDaoImpl: " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return appAndLoanBySourceList;
    }

}
