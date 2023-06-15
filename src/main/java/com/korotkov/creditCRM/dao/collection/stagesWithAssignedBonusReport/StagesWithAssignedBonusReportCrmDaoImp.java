package com.korotkov.creditCRM.dao.collection.stagesWithAssignedBonusReport;

import com.korotkov.creditCRM.dao.collection.commonDataWithAssignedCollectorReport.CommonDataWithAssignedCollectorReportDaoImpl;
import com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport.CollectionPaymentWithAssigned;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
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
public class StagesWithAssignedBonusReportCrmDaoImp implements StagesWithAssignedBonusReportCrmDao {
    private static final Logger logger = LoggerFactory.getLogger(StagesWithAssignedBonusReportCrmDaoImp.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryCRM")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CollectionPaymentWithAssigned> getCollectionPaymentWithAssignedList(LocalDate dateFrom,
                                                                                    LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<CollectionPaymentWithAssigned> collectionPaymentWithAssignedList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                                    "dcp.collector_account_id as user_id,\n" +
                                    "lo.payment_amount,\n" +
                                    "(case when ls.days_overdue > 0 then ls.days_overdue else ls_previous_day.days_overdue + 1 end) as days_overdue \n" +
                                    "from debt_collection_payments dcp\n" +
                                    "left join loan_operation lo \n" +
                                    "on lo.id = dcp.operation_id\n" +
                                    "left join loan_snapshot ls \n" +
                                    "on ls.loan_id = dcp.loan_id \n" +
                                    "and date(lo.activated_at) = ls.\"date\"\n" +
                                    "left join loan_snapshot ls_previous_day\n" +
                                    "on ls_previous_day.loan_id = dcp.loan_id \n" +
                                    "and date(lo.activated_at) - 1 = ls_previous_day.\"date\"\n" +
                                    "where lo.\"type\" = 'PAYMENT'\n" +
                                    "and lo.status = 'ACTIVE'\n" +
                                    "and dcp.collector_account_id is not null\n" +
                                    "and date(lo.activated_at) >= :dateFrom\n" +
                                    "and date(lo.activated_at) <= :dateTo")
                            .addScalar("user_id", new LongType())
                            .addScalar("payment_amount", new DoubleType())
                            .addScalar("days_overdue", new IntegerType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                CollectionPaymentWithAssigned collectionPaymentWithAssigned = new CollectionPaymentWithAssigned();

                if (row[0] != null) {
                    collectionPaymentWithAssigned.setUserId(Long.parseLong(row[0].toString()));
                }
                if (row[1] != null) {
                    collectionPaymentWithAssigned.setPaymentAmount(Double.parseDouble(row[1].toString()));
                }
                if (row[2] != null) {
                    collectionPaymentWithAssigned.setDaysOverdue(Integer.parseInt(row[2].toString()));
                }
                collectionPaymentWithAssignedList.add(collectionPaymentWithAssigned);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return collectionPaymentWithAssignedList;
    }
}
