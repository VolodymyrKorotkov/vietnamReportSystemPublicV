package com.korotkov.creditCRM.dao.collection.commonDataWithAssignedCollectorReport;

import com.korotkov.creditCRM.model.collection.commonDataWithAssignedCollectorReport.CommonDataWithAssignedCollectorReport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.*;
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
public class CommonDataWithAssignedCollectorReportDaoImpl implements CommonDataWithAssignedCollectorReportDao{
    private static final Logger logger = LoggerFactory.getLogger(CommonDataWithAssignedCollectorReportDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryCRM")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CommonDataWithAssignedCollectorReport> getExportCommonDataWithAssignedCollectorList() {
        Session session = sessionFactory.openSession();
        List<CommonDataWithAssignedCollectorReport> commonDataWithAssignedCollectorReportList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select dc.id as debt_id,\n" +
                                    "l.application_id, \n" +
                                    "dc.collector_account_info,\n" +
                                    "payments.payment_amount,\n" +
                                    "dc.current_debt,\n" +
                                    "dc.days_overdue,\n" +
                                    "dc.last_activity_date,\n" +
                                    "da.activity_en,\n" +
                                    "da.activity_vi,\n" +
                                    "dar.activity_result_en ,\n" +
                                    "dar.activity_result_vi ,\n" +
                                    "dc.last_comment,\n" +
                                    "dc.last_promised_payment_date,\n" +
                                    "dc.status,\n" +
                                    "ds.stage, \n" +
                                    "cua.first_name ,\n" +
                                    "cua.document_id_number \n" +
                                    "from debt_collection dc \n" +
                                    "left join client_user_account cua \n" +
                                    "on cua.id = dc.client_id " +
                                    "left join debt_activity da \n" +
                                    "on dc.last_activity_id = da.id \n" +
                                    "left join debt_activity_result dar \n" +
                                    "on dc.last_activity_result_id = dar.id \n" +
                                    "left join debt_stage ds \n" +
                                    "on ds.id = dc.stage_id\n" +
                                    "left join loan l\n" +
                                    "on l.id = dc.loan_id \n" +
                                    "left join\n" +
                                    "(select dcp.collector_account_id,\n" +
                                    "dcp.debt_collection_id,\n" +
                                    "sum(lo.payment_amount) as payment_amount \n" +
                                    "from debt_collection_payments dcp \n" +
                                    "left join loan_operation lo \n" +
                                    "on lo.id = dcp.operation_id \n" +
                                    "where lo.\"type\" = 'PAYMENT'\n" +
                                    "and lo.status = 'ACTIVE'\n" +
                                    "group by dcp.collector_account_id, dcp.debt_collection_id) as payments\n" +
                                    "on payments.collector_account_id = dc.collector_account_id \n" +
                                    "and dc.id = payments.debt_collection_id\n" +
                                    "where dc.finished is false \n" +
                                    "and dc.collector_account_id is not null")
                            .addScalar("debt_id", new LongType())
                            .addScalar("application_id", new LongType())
                            .addScalar("collector_account_info", new StringType())
                            .addScalar("payment_amount", new DoubleType())
                            .addScalar("current_debt", new DoubleType())
                            .addScalar("days_overdue", new IntegerType())
                            .addScalar("last_activity_date", new LocalDateType())
                            .addScalar("activity_en", new StringType())
                            .addScalar("activity_vi", new StringType())
                            .addScalar("activity_result_en", new StringType())
                            .addScalar("activity_result_vi", new StringType())
                            .addScalar("last_comment", new StringType())
                            .addScalar("last_promised_payment_date", new LocalDateType())
                            .addScalar("status", new StringType())
                            .addScalar("stage", new StringType())
                            .addScalar("first_name", new StringType())
                            .addScalar("document_id_number", new StringType())
                            .list();
            for (Object[] row : rows) {
                CommonDataWithAssignedCollectorReport commonDataWithAssignedCollectorReport = new CommonDataWithAssignedCollectorReport();
                if (row[0] != null) {
                    commonDataWithAssignedCollectorReport.setDebtId(Long.parseLong(row[0].toString()));
                }
                if (row[1] != null) {
                    commonDataWithAssignedCollectorReport.setApplicationId(Long.parseLong(row[1].toString()));
                }
                if (row[2] != null) {
                    commonDataWithAssignedCollectorReport.setCollectorAccountInfo(row[2].toString());
                }
                if (row[3] != null) {
                    commonDataWithAssignedCollectorReport.setPaymentAmount(Double.parseDouble(row[3].toString()));
                }
                if (row[4] != null) {
                    commonDataWithAssignedCollectorReport.setCurrentDebt(Double.parseDouble(row[4].toString()));
                }
                if (row[5] != null) {
                    commonDataWithAssignedCollectorReport.setDaysOverdue(Integer.parseInt(row[5].toString()));
                }
                if (row[6] != null) {
                    commonDataWithAssignedCollectorReport.setLastActivityDate(LocalDate.parse(row[6].toString()));
                }
                if (row[7] != null) {
                    commonDataWithAssignedCollectorReport.setActivityEn(row[7].toString());
                }
                if (row[8] != null) {
                    commonDataWithAssignedCollectorReport.setActivityVn(row[8].toString());
                }
                if (row[9] != null) {
                    commonDataWithAssignedCollectorReport.setActivityResultEn(row[9].toString());
                }
                if (row[10] != null) {
                    commonDataWithAssignedCollectorReport.setActivityResultVn(row[10].toString());
                }
                if (row[11] != null) {
                    commonDataWithAssignedCollectorReport.setLastComment(row[11].toString());
                }
                if (row[12] != null) {
                    commonDataWithAssignedCollectorReport.setLastPromisedPaymentDate(LocalDate.parse(row[12].toString()));
                }
                if (row[13] != null) {
                    commonDataWithAssignedCollectorReport.setStatus(row[13].toString());
                }
                if (row[14] != null) {
                    commonDataWithAssignedCollectorReport.setStage(row[14].toString());
                }
                if (row[15] != null) {
                    commonDataWithAssignedCollectorReport.setFirstName(row[15].toString());
                }
                if (row[16] != null) {
                    commonDataWithAssignedCollectorReport.setDocumentIdNumber(row[16].toString());
                }
                commonDataWithAssignedCollectorReportList.add(commonDataWithAssignedCollectorReport);
            }

        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return commonDataWithAssignedCollectorReportList;
    }
}
