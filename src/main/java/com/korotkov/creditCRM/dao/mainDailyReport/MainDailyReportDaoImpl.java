package com.korotkov.creditCRM.dao.mainDailyReport;

import com.korotkov.creditCRM.dao.collection.commonDataWithAssignedCollectorReport.CommonDataWithAssignedCollectorReportDaoImpl;
import com.korotkov.creditCRM.dao.mainDailyReport.MainDailyReportDao;
import com.korotkov.creditCRM.model.mainDailyReport.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LocalDateType;
import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class MainDailyReportDaoImpl implements MainDailyReportDao {
    private static final Logger logger = LoggerFactory.getLogger(MainDailyReportDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryCRM")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportApplicationsInfoDate> getExportApplicationsInfoDateList(LocalDate dateFrom,
                                                                              LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportApplicationsInfoDate> exportApplicationsInfoDateList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                                    " to_date(to_char(la.requested_at , 'yyyy-mm-dd'), 'yyyy-mm-dd') as date,\n" +
                                    " count(la.id) as count_apps,\n" +
                                    " sum(case when la.status in ('INITIAL', 'SENT_TO_CLIENT_FOR_REVISION',\n" +
                                    " 'SENT_TO_UNDERWRITING', 'UNDERWITING_IN_PROGRESS', 'APPROVED', 'PREPARED_FOR_OUTGOING_PAYMENT',\n" +
                                    " 'MAKE_DECISION_IN_PROGRESS') then 1 else 0 end) as count_in_progress,\n" +
                                    " sum(case when la.status in ('CANCELED_BY_SYSTEM', 'OUTGOING_PAYMENT_CANCELED') then 1 else 0 end) as count_canceled_by_system,\n" +
                                    " sum(case when la.status = 'CANCELED_BY_CLIENT' then 1 else 0 end) as count_canceled_by_client,\n" +
                                    " sum(case when la.status = 'REJECTED' then 1 else 0 end) as count_reject,\n" +
                                    " sum(case when la.status = 'OUTGOING_PAYMENT_SUCCEED' then 1 else 0 end) as count_issued,\n" +
                                    " sum(case when la.was_approved is true then 1 else 0 end) as count_was_approved,\n" +
                                    " sum(case when la.client_type = 'POTENTIAL' then 1 else 0 end) as count_apps_new,\n" +
                                    " sum(case when la.status in ('INITIAL', 'SENT_TO_CLIENT_FOR_REVISION',\n" +
                                    " 'SENT_TO_UNDERWRITING', 'UNDERWITING_IN_PROGRESS', 'APPROVED', 'PREPARED_FOR_OUTGOING_PAYMENT',\n" +
                                    " 'MAKE_DECISION_IN_PROGRESS') and la.client_type = 'POTENTIAL' then 1 else 0 end) as count_in_progress_new,\n" +
                                    " sum(case when la.status in ('CANCELED_BY_SYSTEM', 'OUTGOING_PAYMENT_CANCELED') and la.client_type = 'POTENTIAL' then 1 else 0 end) as count_canceled_by_system_new,\n" +
                                    " sum(case when la.status = 'CANCELED_BY_CLIENT' and la.client_type = 'POTENTIAL' then 1 else 0 end) as count_canceled_by_client_new,\n" +
                                    " sum(case when la.status = 'REJECTED' and la.client_type = 'POTENTIAL' then 1 else 0 end) as count_reject_new,\n" +
                                    " sum(case when la.status = 'OUTGOING_PAYMENT_SUCCEED' and la.client_type = 'POTENTIAL' then 1 else 0 end) as count_issued_new,\n" +
                                    " sum(case when la.was_approved is true and la.client_type = 'POTENTIAL' then 1 else 0 end) as count_was_approved_new,\n" +
                                    " sum(case when la.client_type = 'REPEAT' then 1 else 0 end) as count_apps_repeat,\n" +
                                    " sum(case when la.status in ('INITIAL', 'SENT_TO_CLIENT_FOR_REVISION',\n" +
                                    " 'SENT_TO_UNDERWRITING', 'UNDERWITING_IN_PROGRESS', 'APPROVED', 'PREPARED_FOR_OUTGOING_PAYMENT',\n" +
                                    " 'MAKE_DECISION_IN_PROGRESS') and la.client_type = 'REPEAT' then 1 else 0 end) as count_in_progress_repeat,\n" +
                                    " sum(case when la.status in ('CANCELED_BY_SYSTEM', 'OUTGOING_PAYMENT_CANCELED') and la.client_type = 'REPEAT' then 1 else 0 end) as count_canceled_by_system_repeat,\n" +
                                    " sum(case when la.status = 'CANCELED_BY_CLIENT' and la.client_type = 'REPEAT' then 1 else 0 end) as count_canceled_by_client_repeat,\n" +
                                    " sum(case when la.status = 'REJECTED' and la.client_type = 'REPEAT' then 1 else 0 end) as count_reject_repeat,\n" +
                                    " sum(case when la.status = 'OUTGOING_PAYMENT_SUCCEED' and la.client_type = 'REPEAT' then 1 else 0 end) as count_issued_repeat,\n" +
                                    " sum(case when la.was_approved is true and la.client_type = 'REPEAT' then 1 else 0 end) as count_was_approved_repeat\n" +
                                    " from loan_application la\n" +
                                    " where \n" +
                                    " to_date(to_char(la.requested_at , 'yyyy-mm-dd'), 'yyyy-mm-dd') >= :dateFrom\n" +
                                    " and to_date(to_char(la.requested_at , 'yyyy-mm-dd'), 'yyyy-mm-dd') <= :dateTo\n" +
                                    " group by to_date(to_char(la.requested_at , 'yyyy-mm-dd'), 'yyyy-mm-dd')\n" +
                                    " order by to_date(to_char(la.requested_at , 'yyyy-mm-dd'), 'yyyy-mm-dd') asc")
                            .addScalar("date", new LocalDateType())
                            .addScalar("count_apps", new LongType())
                            .addScalar("count_in_progress", new LongType())
                            .addScalar("count_canceled_by_system", new LongType())
                            .addScalar("count_canceled_by_client", new LongType())
                            .addScalar("count_reject", new LongType())
                            .addScalar("count_issued", new LongType())
                            .addScalar("count_was_approved", new LongType())
                            .addScalar("count_apps_new", new LongType())
                            .addScalar("count_in_progress_new", new LongType())
                            .addScalar("count_canceled_by_system_new", new LongType())
                            .addScalar("count_canceled_by_client_new", new LongType())
                            .addScalar("count_reject_new", new LongType())
                            .addScalar("count_issued_new", new LongType())
                            .addScalar("count_was_approved_new", new LongType())
                            .addScalar("count_apps_repeat", new LongType())
                            .addScalar("count_in_progress_repeat", new LongType())
                            .addScalar("count_canceled_by_system_repeat", new LongType())
                            .addScalar("count_canceled_by_client_repeat", new LongType())
                            .addScalar("count_reject_repeat", new LongType())
                            .addScalar("count_issued_repeat", new LongType())
                            .addScalar("count_was_approved_repeat", new LongType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();
            for (Object[] row : rows) {
                ExportApplicationsInfoDate exportApplicationsInfoDate = new ExportApplicationsInfoDate();
                exportApplicationsInfoDate.setDate(LocalDate.parse(row[0].toString()));
                exportApplicationsInfoDate.setCountAppsTotal(Long.parseLong(row[1].toString()));
                exportApplicationsInfoDate.setCountAppsInProgress(Long.parseLong(row[2].toString()));
                exportApplicationsInfoDate.setCountAppsCanceledBySystem(Long.parseLong(row[3].toString()));
                exportApplicationsInfoDate.setCountAppsCanceledByClient(Long.parseLong(row[4].toString()));
                exportApplicationsInfoDate.setCountAppsRejected(Long.parseLong(row[5].toString()));
                exportApplicationsInfoDate.setCountAppsIssued(Long.parseLong(row[6].toString()));
                exportApplicationsInfoDate.setCountAppsWasApproved(Long.parseLong(row[7].toString()));
                exportApplicationsInfoDate.setCountAppsTotalNew(Long.parseLong(row[8].toString()));
                exportApplicationsInfoDate.setCountAppsInProgressNew(Long.parseLong(row[9].toString()));
                exportApplicationsInfoDate.setCountAppsCanceledBySystemNew(Long.parseLong(row[10].toString()));
                exportApplicationsInfoDate.setCountAppsCanceledByClientNew(Long.parseLong(row[11].toString()));
                exportApplicationsInfoDate.setCountAppsRejectedNew(Long.parseLong(row[12].toString()));
                exportApplicationsInfoDate.setCountAppsIssuedNew(Long.parseLong(row[13].toString()));
                exportApplicationsInfoDate.setCountAppsWasApprovedNew(Long.parseLong(row[14].toString()));
                exportApplicationsInfoDate.setCountAppsTotalRepeat(Long.parseLong(row[15].toString()));
                exportApplicationsInfoDate.setCountAppsInProgressRepeat(Long.parseLong(row[16].toString()));
                exportApplicationsInfoDate.setCountAppsCanceledBySystemRepeat(Long.parseLong(row[17].toString()));
                exportApplicationsInfoDate.setCountAppsCanceledByClientRepeat(Long.parseLong(row[18].toString()));
                exportApplicationsInfoDate.setCountAppsRejectedRepeat(Long.parseLong(row[19].toString()));
                exportApplicationsInfoDate.setCountAppsIssuedRepeat(Long.parseLong(row[20].toString()));
                exportApplicationsInfoDate.setCountAppsWasApprovedRepeat(Long.parseLong(row[21].toString()));
                exportApplicationsInfoDateList.add(exportApplicationsInfoDate);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return exportApplicationsInfoDateList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportCollectionDebtsInfoDate> getExportCollectionDebtsInfoDateList(LocalDate dateFrom,
                                                                                    LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportCollectionDebtsInfoDate> exportCollectionDebtsInfoDateList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select \n" +
                                    "loan_t.issued_date as date,\n" +
                                    "sum(loan_t.amount) as total_issued_amount,\n" +
                                    "sum(loan_t.al_principal) as principal_repaid,\n" +
                                    "sum(loan_t.paid_perc) as percent_repaid,\n" +
                                    "sum(case when loan_t.status not in ('EXPIRED', 'GRACE', 'FINISHED') then loan_t.principal else 0 end) as current_debt_principal,\n" +
                                    "sum(case when loan_t.status = 'GRACE' then loan_t.principal else 0 end) as grace_period,\n" +
                                    "sum(case when loan_t.status = 'EXPIRED' and loan_t.days_overdue <= 30 then loan_t.principal else 0 end) as exp_30_days,\n" +
                                    "sum(case when loan_t.status = 'EXPIRED' and loan_t.days_overdue > 30\n" +
                                    "and loan_t.days_overdue <= 60 then loan_t.principal else 0 end) as exp_60_days,\n" +
                                    "sum(case when loan_t.status = 'EXPIRED' and loan_t.days_overdue > 60\n" +
                                    "and loan_t.days_overdue <= 90 then loan_t.principal else 0 end) as exp_90_days,\n" +
                                    "sum(case when loan_t.status = 'EXPIRED' and loan_t.days_overdue > 90\n" +
                                    "and loan_t.days_overdue <= 120 then loan_t.principal else 0 end) as exp_120_days,\n" +
                                    "sum(case when loan_t.status = 'EXPIRED' and loan_t.days_overdue > 120\n" +
                                    "and loan_t.days_overdue <= 180 then loan_t.principal else 0 end) as exp_180_days,\n" +
                                    "sum(case when loan_t.status = 'EXPIRED' and loan_t.days_overdue > 180 then loan_t.principal else 0 end) as exp_more_181_days,\n" +
                                    "sum(case when loan_t.client_type = 'POTENTIAL' then loan_t.amount else 0 end) as total_issued_amount_new,\n" +
                                    "sum(case when loan_t.client_type = 'POTENTIAL' then loan_t.al_principal else 0 end) as principal_repaid_new,\n" +
                                    "sum(case when loan_t.client_type = 'POTENTIAL' then loan_t.paid_perc else 0 end) as percent_repaid_new,\n" +
                                    "sum(case when loan_t.client_type = 'POTENTIAL' and loan_t.status not in ('EXPIRED', 'GRACE', 'FINISHED') then loan_t.principal else 0 end) as current_debt_principal_new,\n" +
                                    "sum(case when loan_t.client_type = 'POTENTIAL' and loan_t.status = 'GRACE' then loan_t.principal else 0 end) as grace_period_new,\n" +
                                    "sum(case when loan_t.client_type = 'POTENTIAL' and loan_t.status = 'EXPIRED' and loan_t.days_overdue <= 30 then loan_t.principal else 0 end) as exp_30_days_new,\n" +
                                    "sum(case when loan_t.client_type = 'POTENTIAL' and loan_t.status = 'EXPIRED' and loan_t.days_overdue > 30\n" +
                                    "and loan_t.days_overdue <= 60 then loan_t.principal else 0 end) as exp_60_days_new,\n" +
                                    "sum(case when loan_t.client_type = 'POTENTIAL' and loan_t.status = 'EXPIRED' and loan_t.days_overdue > 60\n" +
                                    "and loan_t.days_overdue <= 90 then loan_t.principal else 0 end) as exp_90_days_new,\n" +
                                    "sum(case when loan_t.client_type = 'POTENTIAL' and loan_t.status = 'EXPIRED' and loan_t.days_overdue > 90\n" +
                                    "and loan_t.days_overdue <= 120 then loan_t.principal else 0 end) as exp_120_days_new,\n" +
                                    "sum(case when loan_t.client_type = 'POTENTIAL' and loan_t.status = 'EXPIRED' and loan_t.days_overdue > 120\n" +
                                    "and loan_t.days_overdue <= 180 then loan_t.principal else 0 end) as exp_180_days_new,\n" +
                                    "sum(case when loan_t.client_type = 'POTENTIAL' and loan_t.status = 'EXPIRED' and loan_t.days_overdue > 180 then loan_t.principal else 0 end) as exp_more_181_days_new,\n" +
                                    "sum(case when loan_t.client_type = 'REPEAT' then loan_t.amount else 0 end) as total_issued_amount_repeat,\n" +
                                    "sum(case when loan_t.client_type = 'REPEAT' then loan_t.al_principal else 0 end) as principal_repaid_repeat,\n" +
                                    "sum(case when loan_t.client_type = 'REPEAT' then loan_t.paid_perc else 0 end) as percent_repaid_repeat,\n" +
                                    "sum(case when loan_t.client_type = 'REPEAT' and loan_t.status not in ('EXPIRED', 'GRACE', 'FINISHED') then loan_t.principal else 0 end) as current_debt_principal_repeat,\n" +
                                    "sum(case when loan_t.client_type = 'REPEAT' and loan_t.status = 'GRACE' then loan_t.principal else 0 end) as grace_period_repeat,\n" +
                                    "sum(case when loan_t.client_type = 'REPEAT' and loan_t.status = 'EXPIRED' and loan_t.days_overdue <= 30 then loan_t.principal else 0 end) as exp_30_days_repeat,\n" +
                                    "sum(case when loan_t.client_type = 'REPEAT' and loan_t.status = 'EXPIRED' and loan_t.days_overdue > 30\n" +
                                    "and loan_t.days_overdue <= 60 then loan_t.principal else 0 end) as exp_60_days_repeat,\n" +
                                    "sum(case when loan_t.client_type = 'REPEAT' and loan_t.status = 'EXPIRED' and loan_t.days_overdue > 60\n" +
                                    "and loan_t.days_overdue <= 90 then loan_t.principal else 0 end) as exp_90_days_repeat,\n" +
                                    "sum(case when loan_t.client_type = 'REPEAT' and loan_t.status = 'EXPIRED' and loan_t.days_overdue > 90\n" +
                                    "and loan_t.days_overdue <= 120 then loan_t.principal else 0 end) as exp_120_days_repeat,\n" +
                                    "sum(case when loan_t.client_type = 'REPEAT' and loan_t.status = 'EXPIRED' and loan_t.days_overdue > 120\n" +
                                    "and loan_t.days_overdue <= 180 then loan_t.principal else 0 end) as exp_180_days_repeat,\n" +
                                    "sum(case when loan_t.client_type = 'REPEAT' and loan_t.status = 'EXPIRED' and loan_t.days_overdue > 180 then loan_t.principal else 0 end) as exp_more_181_days_repeat,\n" +
                                    "sum(case when loan_t.status = 'EXPIRED'\n" +
                                    "then loan_t.principal else 0 end) as exp_total,\n" +
                                    "sum(case when loan_t.client_type = 'POTENTIAL' and loan_t.status = 'EXPIRED'\n" +
                                    "then loan_t.principal else 0 end) as exp_total_new,\n" +
                                    "sum(case when loan_t.client_type = 'REPEAT' and loan_t.status = 'EXPIRED'\n" +
                                    "then loan_t.principal else 0 end) as exp_total_repeat\n" +
                                    "from \n" +
                                    "(select \n" +
                                    "\t\t\t\t\t\t\tto_date(to_char(l.issued_at, 'yyyy-mm-dd'),'yyyy-mm-dd') as issued_date,\n" +
                                    "\t\t\t\t\t\t\tl.client_id,\n" +
                                    "\t\t\t\t\t\t\tl.status,\n" +
                                    "\t\t\t\t\t\t\tls_t2.days_overdue ,\n" +
                                    "\t\t\t\t\t\t\tls_t2.al_principal ,\n" +
                                    "\t\t\t\t\t\t\tls_t2.principal ,\n" +
                                    "\t\t\t\t\t\t\t(ls_t2.paid_total - ls_t2.al_principal) as paid_perc,\n" +
                                    "\t\t\t\t\t\t\tl.id,\n" +
                                    "\t\t\t\t\t\t\tl.affiliate_utm_source,\n" +
                                    "\t\t\t\t\t\t\tl.amount,\n" +
                                    "\t\t\t\t\t\t\tla.client_type\n" +
                                    "\t\t\t\t\t\t\tfrom loan l \n" +
                                    "\t\t\t\t\t\t\tleft join\t\t\t\t\t\t\t\n" +
                                    "\t\t\t\t\t\t\tloan_application la \n" +
                                    "\t\t\t\t\t\t\ton la.id = l.application_id \n" +
                                    "\t\t\t\t\t\t\tleft join \n" +
                                    "(select\n" +
                                    "ls.loan_id ,\n" +
                                    "ls.days_overdue ,\n" +
                                    "ls.al_principal ,\n" +
                                    "ls.principal ,\n" +
                                    "ls.paid_total\n" +
                                    "from public.loan_snapshot ls \n" +
                                    "inner join\n" +
                                    "(select\n" +
                                    "ls.loan_id,\n" +
                                    "max(ls.date) as date\n" +
                                    "from public.loan_snapshot ls\n" +
                                    "group by ls.loan_id ) ls_t\n" +
                                    "on\n" +
                                    "ls.loan_id = ls_t.loan_id\n" +
                                    "and \n" +
                                    "ls.date = ls_t.date \n" +
                                    ")ls_t2\n" +
                                    "on l.id = ls_t2.loan_id \n" +
                                    "where \n" +
                                    "to_date(to_char(l.issued_at, 'yyyy-mm-dd'),'yyyy-mm-dd') >= :dateFrom\n" +
                                    "and to_date(to_char(l.issued_at, 'yyyy-mm-dd'),'yyyy-mm-dd') <= :dateTo\n" +
                                    ") as loan_t\n" +
                                    "group by loan_t.issued_date\n" +
                                    "order by loan_t.issued_date asc ")
                            .addScalar("date", new LocalDateType())
                            .addScalar("total_issued_amount", new DoubleType())
                            .addScalar("principal_repaid", new DoubleType())
                            .addScalar("percent_repaid", new DoubleType())
                            .addScalar("current_debt_principal", new DoubleType())
                            .addScalar("grace_period", new DoubleType())
                            .addScalar("exp_30_days", new DoubleType())
                            .addScalar("exp_60_days", new DoubleType())
                            .addScalar("exp_90_days", new DoubleType())
                            .addScalar("exp_120_days", new DoubleType())
                            .addScalar("exp_180_days", new DoubleType())
                            .addScalar("exp_more_181_days", new DoubleType())
                            .addScalar("total_issued_amount_new", new DoubleType())
                            .addScalar("principal_repaid_new", new DoubleType())
                            .addScalar("percent_repaid_new", new DoubleType())
                            .addScalar("current_debt_principal_new", new DoubleType())
                            .addScalar("grace_period_new", new DoubleType())
                            .addScalar("exp_30_days_new", new DoubleType())
                            .addScalar("exp_60_days_new", new DoubleType())
                            .addScalar("exp_90_days_new", new DoubleType())
                            .addScalar("exp_120_days_new", new DoubleType())
                            .addScalar("exp_180_days_new", new DoubleType())
                            .addScalar("exp_more_181_days_new", new DoubleType())
                            .addScalar("total_issued_amount_repeat", new DoubleType())
                            .addScalar("principal_repaid_repeat", new DoubleType())
                            .addScalar("percent_repaid_repeat", new DoubleType())
                            .addScalar("current_debt_principal_repeat", new DoubleType())
                            .addScalar("grace_period_repeat", new DoubleType())
                            .addScalar("exp_30_days_repeat", new DoubleType())
                            .addScalar("exp_60_days_repeat", new DoubleType())
                            .addScalar("exp_90_days_repeat", new DoubleType())
                            .addScalar("exp_120_days_repeat", new DoubleType())
                            .addScalar("exp_180_days_repeat", new DoubleType())
                            .addScalar("exp_more_181_days_repeat", new DoubleType())
                            .addScalar("exp_total", new DoubleType())
                            .addScalar("exp_total_new", new DoubleType())
                            .addScalar("exp_total_repeat", new DoubleType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();
            for (Object[] row : rows) {
                ExportCollectionDebtsInfoDate exportCollectionDebtsInfoDate = new ExportCollectionDebtsInfoDate();
                exportCollectionDebtsInfoDate.setDate(LocalDate.parse(row[0].toString()));
                exportCollectionDebtsInfoDate.setTotalIssuedAmount(Double.parseDouble(row[1].toString()));
                exportCollectionDebtsInfoDate.setPrincipalRepaid(Double.parseDouble(row[2].toString()));
                exportCollectionDebtsInfoDate.setPercentRepaid(Double.parseDouble(row[3].toString()));
                exportCollectionDebtsInfoDate.setCurrentDebtPrincipal(Double.parseDouble(row[4].toString()));
                exportCollectionDebtsInfoDate.setGracePeriodAmount(Double.parseDouble(row[5].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountTo30Days(Double.parseDouble(row[6].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom31To60Days(Double.parseDouble(row[7].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom61To90Days(Double.parseDouble(row[8].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom91To120Days(Double.parseDouble(row[9].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom121To180Days(Double.parseDouble(row[10].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom181Days(Double.parseDouble(row[11].toString()));
                exportCollectionDebtsInfoDate.setTotalIssuedAmountNew(Double.parseDouble(row[12].toString()));
                exportCollectionDebtsInfoDate.setPrincipalRepaidNew(Double.parseDouble(row[13].toString()));
                exportCollectionDebtsInfoDate.setPercentRepaidNew(Double.parseDouble(row[14].toString()));
                exportCollectionDebtsInfoDate.setCurrentDebtPrincipalNew(Double.parseDouble(row[15].toString()));
                exportCollectionDebtsInfoDate.setGracePeriodAmountNew(Double.parseDouble(row[16].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountTo30DaysNew(Double.parseDouble(row[17].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom31To60DaysNew(Double.parseDouble(row[18].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom61To90DaysNew(Double.parseDouble(row[19].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom91To120DaysNew(Double.parseDouble(row[20].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom121To180DaysNew(Double.parseDouble(row[21].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom181DaysNew(Double.parseDouble(row[22].toString()));
                exportCollectionDebtsInfoDate.setTotalIssuedAmountRepeat(Double.parseDouble(row[23].toString()));
                exportCollectionDebtsInfoDate.setPrincipalRepaidRepeat(Double.parseDouble(row[24].toString()));
                exportCollectionDebtsInfoDate.setPercentRepaidRepeat(Double.parseDouble(row[25].toString()));
                exportCollectionDebtsInfoDate.setCurrentDebtPrincipalRepeat(Double.parseDouble(row[26].toString()));
                exportCollectionDebtsInfoDate.setGracePeriodAmountRepeat(Double.parseDouble(row[27].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountTo30DaysRepeat(Double.parseDouble(row[28].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom31To60DaysRepeat(Double.parseDouble(row[29].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom61To90DaysRepeat(Double.parseDouble(row[30].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom91To120DaysRepeat(Double.parseDouble(row[31].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom121To180DaysRepeat(Double.parseDouble(row[32].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountFrom181DaysRepeat(Double.parseDouble(row[33].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountTotal(Double.parseDouble(row[34].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountTotalNew(Double.parseDouble(row[35].toString()));
                exportCollectionDebtsInfoDate.setExpiredAmountTotalRepeat(Double.parseDouble(row[36].toString()));
                exportCollectionDebtsInfoDateList.add(exportCollectionDebtsInfoDate);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return exportCollectionDebtsInfoDateList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ExportLoansInfoDate> getExportLoansInfoDateList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportLoansInfoDate> exportLoansInfoDateList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                                    "to_date(to_char(l.issued_at , 'yyyy-mm-dd'), 'yyyy-mm-dd') as date,\n" +
                                    "count(l.id) as count_loan,\n" +
                                    "sum(l.amount) as amount_loan,\n" +
                                    "sum(case when la.client_type = 'POTENTIAL' then 1 else 0 end) as count_loan_new,\n" +
                                    "sum(case when la.client_type = 'POTENTIAL' then l.amount else 0 end) as amount_loan_new,\n" +
                                    "sum(case when la.client_type = 'REPEAT' then 1 else 0 end) as count_loan_repeat,\n" +
                                    "sum(case when la.client_type = 'REPEAT' then l.amount else 0 end) as amount_loan_repeat\n" +
                                    "from loan l\n" +
                                    "left join loan_application la \n" +
                                    "on la.id = l.application_id \n" +
                                    "where to_date(to_char(l.issued_at, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= :dateFrom\n" +
                                    "and to_date(to_char(l.issued_at, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= :dateTo\n" +
                                    "group by to_date(to_char(l.issued_at , 'yyyy-mm-dd'), 'yyyy-mm-dd')\n" +
                                    "order by to_date(to_char(l.issued_at , 'yyyy-mm-dd'), 'yyyy-mm-dd') asc")
                            .addScalar("date", new LocalDateType())
                            .addScalar("count_loan", new LongType())
                            .addScalar("amount_loan", new DoubleType())
                            .addScalar("count_loan_new", new LongType())
                            .addScalar("amount_loan_new", new DoubleType())
                            .addScalar("count_loan_repeat", new LongType())
                            .addScalar("amount_loan_repeat", new DoubleType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();
            for (Object[] row : rows) {
                ExportLoansInfoDate exportLoansInfoDate = new ExportLoansInfoDate();
                exportLoansInfoDate.setDate(LocalDate.parse(row[0].toString()));
                exportLoansInfoDate.setCountLoan(Long.parseLong(row[1].toString()));
                exportLoansInfoDate.setAmountLoan(Double.parseDouble(row[2].toString()));
                exportLoansInfoDate.setCountLoanNew(Long.parseLong(row[3].toString()));
                exportLoansInfoDate.setAmountLoanNew(Double.parseDouble(row[4].toString()));
                exportLoansInfoDate.setCountLoanRepeat(Long.parseLong(row[5].toString()));
                exportLoansInfoDate.setAmountLoanRepeat(Double.parseDouble(row[6].toString()));
                exportLoansInfoDateList.add(exportLoansInfoDate);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return exportLoansInfoDateList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportPaymentsInfoDate> getExportPaymentsInfoDateList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportPaymentsInfoDate> exportPaymentsInfoDateList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select qq.date,\n" +
                                    "sum(qq.paid_for_date) as paid_for_date,\n" +
                                    "sum(qq.paid_principal_for_date) as paid_principal_for_date,\n" +
                                    "sum(qq.paid_for_date) - sum(qq.paid_principal_for_date) as paid_income_for_date,\n" +
                                    "sum(case when la.client_type = 'POTENTIAL' then qq.paid_for_date else 0 end) as paid_for_date_new,\n" +
                                    "sum(case when la.client_type = 'POTENTIAL' then qq.paid_principal_for_date else 0 end) as paid_principal_for_date_new,\n" +
                                    "sum(case when la.client_type = 'POTENTIAL' then qq.paid_for_date else 0 end) - \n" +
                                    "sum(case when la.client_type = 'POTENTIAL' then qq.paid_principal_for_date else 0 end) as paid_income_for_date_new,\n" +
                                    "sum(case when la.client_type = 'REPEAT' then qq.paid_for_date else 0 end) as paid_for_date_repeat,\n" +
                                    "sum(case when la.client_type = 'REPEAT' then qq.paid_principal_for_date else 0 end) as paid_principal_for_date_repeat,\n" +
                                    "sum(case when la.client_type = 'REPEAT' then qq.paid_for_date else 0 end) -\n" +
                                    "sum(case when la.client_type = 'REPEAT' then qq.paid_principal_for_date else 0 end) as paid_income_for_date_repeat\n" +
                                    "from\n" +
                                    "(select \n" +
                                    "ls.date as date,\n" +
                                    "ls.loan_id,\n" +
                                    "ls.paid_for_date,\n" +
                                    "(case when (coalesce((select ls2.al_principal from public.loan_snapshot ls2 where \n" +
                                    "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_principal, ls.al_principal) * -1) >= 0 then (coalesce((select ls2.al_principal from public.loan_snapshot ls2 where \n" +
                                    "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_principal, ls.al_principal) * -1) else (coalesce((select ls2.al_principal from public.loan_snapshot ls2 where \n" +
                                    "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_principal, ls.al_principal) * 1) end) as paid_principal_for_date\n" +
                                    "from public.loan_snapshot ls \n" +
                                    "where ls.paid_for_date > 0\n" +
                                    "and to_date(to_char(ls.date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= :dateFrom\n" +
                                    "and to_date(to_char(ls.date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= :dateTo) as qq\n" +
                                    "left join loan l \n" +
                                    "on l.id = qq.loan_id\n" +
                                    "left join loan_application la \n" +
                                    "on la.id = l.application_id \n" +
                                    "group by qq.date")
                            .addScalar("date", new LocalDateType())
                            .addScalar("paid_for_date", new DoubleType())
                            .addScalar("paid_principal_for_date", new DoubleType())
                            .addScalar("paid_income_for_date", new DoubleType())
                            .addScalar("paid_for_date_new", new DoubleType())
                            .addScalar("paid_principal_for_date_new", new DoubleType())
                            .addScalar("paid_income_for_date_new", new DoubleType())
                            .addScalar("paid_for_date_repeat", new DoubleType())
                            .addScalar("paid_principal_for_date_repeat", new DoubleType())
                            .addScalar("paid_income_for_date_repeat", new DoubleType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();
            for (Object[] row : rows) {
                ExportPaymentsInfoDate exportPaymentsInfoDate = new ExportPaymentsInfoDate();
                exportPaymentsInfoDate.setDate(LocalDate.parse(row[0].toString()));
                exportPaymentsInfoDate.setPaidTotal(Double.parseDouble(row[1].toString()));
                exportPaymentsInfoDate.setPaidPrincipal(Double.parseDouble(row[2].toString()));
                exportPaymentsInfoDate.setPaidIncome(Double.parseDouble(row[3].toString()));
                exportPaymentsInfoDate.setPaidTotalNew(Double.parseDouble(row[4].toString()));
                exportPaymentsInfoDate.setPaidPrincipalNew(Double.parseDouble(row[5].toString()));
                exportPaymentsInfoDate.setPaidIncomeNew(Double.parseDouble(row[6].toString()));
                exportPaymentsInfoDate.setPaidTotalRepeat(Double.parseDouble(row[7].toString()));
                exportPaymentsInfoDate.setPaidPrincipalRepeat(Double.parseDouble(row[8].toString()));
                exportPaymentsInfoDate.setPaidIncomeRepeat(Double.parseDouble(row[9].toString()));
                exportPaymentsInfoDateList.add(exportPaymentsInfoDate);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return exportPaymentsInfoDateList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ExportProlongationsInfoDate> getExportProlongationsInfoDateList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportProlongationsInfoDate> exportProlongationsInfoDateList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select \n" +
                                    "to_date(to_char(li.activation_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') as date,\n" +
                                    "count(li.id) as count_prolong,\n" +
                                    "sum(li.principal) as principal_prolong,\n" +
                                    "sum(case when la.client_type = 'POTENTIAL' then 1 else 0 end) as count_prolong_new,\n" +
                                    "sum(case when la.client_type = 'POTENTIAL' then li.principal else 0 end) as principal_prolong_new,\n" +
                                    "sum(case when la.client_type = 'REPEAT' then 1 else 0 end) as count_prolong_repeat,\n" +
                                    "sum(case when la.client_type = 'REPEAT' then li.principal else 0 end) as principal_prolong_repeat\n" +
                                    "from loan_installment li\n" +
                                    "left join loan l\n" +
                                    "on l.id = li.loan_id \n" +
                                    "left join loan_application la \n" +
                                    "on la.id = l.application_id \n" +
                                    "where li.prolonged is true\n" +
                                    "and to_date(to_char(li.activation_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') >= :dateFrom\n" +
                                    "and to_date(to_char(li.activation_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') <= :dateTo\n" +
                                    "group by to_date(to_char(li.activation_date, 'yyyy-mm-dd'), 'yyyy-mm-dd')\n" +
                                    "order by to_date(to_char(li.activation_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') asc")
                            .addScalar("date", new LocalDateType())
                            .addScalar("count_prolong", new LongType())
                            .addScalar("principal_prolong", new DoubleType())
                            .addScalar("count_prolong_new", new LongType())
                            .addScalar("principal_prolong_new", new DoubleType())
                            .addScalar("count_prolong_repeat", new LongType())
                            .addScalar("principal_prolong_repeat", new DoubleType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();
            for (Object[] row : rows) {
                ExportProlongationsInfoDate exportProlongationsInfoDate = new ExportProlongationsInfoDate();
                exportProlongationsInfoDate.setDate(LocalDate.parse(row[0].toString()));
                exportProlongationsInfoDate.setCountProlonged(Long.parseLong(row[1].toString()));
                exportProlongationsInfoDate.setPrincipalAmountProlonged(Double.parseDouble(row[2].toString()));
                exportProlongationsInfoDate.setCountProlongedNew(Long.parseLong(row[3].toString()));
                exportProlongationsInfoDate.setPrincipalAmountProlongedNew(Double.parseDouble(row[4].toString()));
                exportProlongationsInfoDate.setCountProlongedRepeat(Long.parseLong(row[5].toString()));
                exportProlongationsInfoDate.setPrincipalAmountProlongedRepeat(Double.parseDouble(row[6].toString()));
                exportProlongationsInfoDateList.add(exportProlongationsInfoDate);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return exportProlongationsInfoDateList;
    }


}
