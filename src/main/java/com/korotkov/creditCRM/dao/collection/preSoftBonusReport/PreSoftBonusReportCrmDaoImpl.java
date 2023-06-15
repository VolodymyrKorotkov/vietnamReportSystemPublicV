package com.korotkov.creditCRM.dao.collection.preSoftBonusReport;

import com.korotkov.creditCRM.model.BackUserAccountShortly;
import com.korotkov.creditCRM.model.collection.preSoftBonusReport.ClientPhone;
import com.korotkov.creditCRM.model.collection.preSoftBonusReport.ClientWithIDAndDebtId;
import com.korotkov.creditCRM.model.collection.preSoftBonusReport.CollectionPayment;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class PreSoftBonusReportCrmDaoImpl implements PreSoftBonusReportCrmDao {
    private static final Logger logger = LoggerFactory.getLogger(PreSoftBonusReportCrmDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryCRM")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ClientWithIDAndDebtId> getClientWithIDAndActiveDebtIds(List<Long> clientIdList) {
        Session session = sessionFactory.openSession();
        List<ClientWithIDAndDebtId> clientWithIDAndDebtIdList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select dc.client_id, dc.id, dc.days_overdue, dc.current_debt from debt_collection dc \n" +
                            "where dc.finished is false \n" +
                            "and dc.client_id in (:clientIdList)")
                            .addScalar("client_id", new LongType())
                            .addScalar("id", new LongType())
                            .addScalar("days_overdue", new IntegerType())
                            .addScalar("current_debt", new DoubleType())
                            .setParameter("clientIdList", clientIdList)
                            .list();
            for (Object[] row : rows) {
                ClientWithIDAndDebtId clientWithIDAndDebtId = new ClientWithIDAndDebtId();
                if (row[0] != null) {
                    clientWithIDAndDebtId.setClientId(Long.parseLong(row[0].toString()));
                }
                if (row[1] != null) {
                    clientWithIDAndDebtId.setDebtId(Long.parseLong(row[1].toString()));
                }
                if (row[2] != null) {
                    clientWithIDAndDebtId.setDaysOverdue(Integer.parseInt(row[2].toString()));
                }
                if (row[3] != null) {
                    clientWithIDAndDebtId.setCurrentDebtAmount(Double.parseDouble(row[3].toString()));
                }
                if (clientWithIDAndDebtId.getDebtId() != null) {
                    clientWithIDAndDebtIdList.add(clientWithIDAndDebtId);
                }
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return clientWithIDAndDebtIdList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<CollectionPayment> getCollectionPaymentList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<CollectionPayment> collectionPaymentList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                                    "dc.client_id ,\n" +
                                    "dcp.collector_account_id,\n" +
                                    "lo.activated_at as payment_date,\n" +
                                    "lo.payment_amount,\n" +
                                    "(case when ls.days_overdue > 0 then ls.days_overdue else ls_previous_day.days_overdue + 1 end) as days_overdue \n" +
                                    "from debt_collection_payments dcp\n" +
                                    "left join debt_collection dc \n" +
                                    "on dc.id = dcp.debt_collection_id \n" +
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
                                    "and date(lo.activated_at) >= :dateFrom\n" +
                                    "and date(lo.activated_at) <= :dateTo")
                            .addScalar("client_id", new LongType())
                            .addScalar("collector_account_id", new LongType())
                            .addScalar("payment_date", new LocalDateTimeType())
                            .addScalar("payment_amount", new DoubleType())
                            .addScalar("days_overdue", new IntegerType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();

            for (Object[] row : rows) {
                CollectionPayment collectionPayment = new CollectionPayment();
                if (row[0] != null) {
                    collectionPayment.setClientId(Long.parseLong(row[0].toString()));
                }
                if (row[1] != null) {
                    collectionPayment.setCollectorAccountId(Long.parseLong(row[1].toString()));
                }
                if (row[2] != null) {
                    collectionPayment.setPaymentDate(LocalDateTime.parse(row[2].toString()));
                }
                if (row[3] != null) {
                    collectionPayment.setPaymentAmount(Double.parseDouble(row[3].toString()));
                }
                if (row[4] != null) {
                    collectionPayment.setDaysOverdue(Integer.parseInt(row[4].toString()));
                }
                collectionPaymentList.add(collectionPayment);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return collectionPaymentList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ClientPhone> getClientPhoneListByPhonesWithOnlyActiveDebt(List<String> phones) {
        Session session = sessionFactory.openSession();
        List<ClientPhone> clientPhoneList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select cp.client_id, cp.phone from client_phones cp \n" +
                                    "where cp.phone in (:phones)\n" +
                                    "and exists (select dc.id from debt_collection dc\n" +
                                    "where dc.finished is false \n" +
                                    "and dc.client_id = cp.client_id )")
                            .addScalar("client_id", new LongType())
                            .addScalar("phone", new StringType())
                            .setParameter("phones", phones)
                            .list();

            for (Object[] row : rows) {
                ClientPhone clientPhone = new ClientPhone();
                if (row[0] != null) {
                    clientPhone.setClientId(Long.parseLong(row[0].toString()));
                }
                if (row[1] != null) {
                    clientPhone.setPhone(row[1].toString());
                }
                clientPhoneList.add(clientPhone);
            }

        }
        catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return clientPhoneList;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ClientPhone> getClientPhoneList(List<Long> clientIdList) {
        Session session = sessionFactory.openSession();
        List<ClientPhone> clientPhoneList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select cp.client_id, cp.phone from client_phones cp\n" +
                                    "where cp.client_id in (:clientIdList)")
                            .addScalar("client_id", new LongType())
                            .addScalar("phone", new StringType())
                            .setParameter("clientIdList", clientIdList)
                            .list();

            for (Object[] row : rows) {
                ClientPhone clientPhone = new ClientPhone();
                if (row[0] != null) {
                    clientPhone.setClientId(Long.parseLong(row[0].toString()));
                }
                if (row[1] != null) {
                    clientPhone.setPhone(row[1].toString());
                }
                clientPhoneList.add(clientPhone);
            }

        }
        catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return clientPhoneList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BackUserAccountShortly> getBackUserAccountShortlyList() {
        Session session = sessionFactory.openSession();
        List<BackUserAccountShortly> backUserAccountShortlyList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                                    "bua.email as user_email,\n" +
                                    "concat(bua.last_name, ' ', bua.first_name) as user_name,\n" +
                                    "bur.\"name\" as role_name,\n" +
                                    "bua.id as user_id \n" +
                                    "from back_user_account bua \n" +
                                    "left join back_user_role bur \n" +
                                    "on bur.id = bua.role_id")
                            .addScalar("user_email", new StringType())
                            .addScalar("user_name", new StringType())
                            .addScalar("role_name", new StringType())
                            .addScalar("user_id", new LongType())
                            .list();

            for (Object[] row : rows) {
                BackUserAccountShortly backUserAccountShortly = new BackUserAccountShortly();
                if (row[0] != null) {
                    backUserAccountShortly.setUserEmail(row[0].toString());
                }
                if (row[1] != null) {
                    backUserAccountShortly.setUserName(row[1].toString());
                }
                if (row[2] != null) {
                    backUserAccountShortly.setRoleName(row[2].toString());
                }
                if (row[3] != null) {
                    backUserAccountShortly.setUserId(Long.parseLong(row[3].toString()));
                }
                backUserAccountShortlyList.add(backUserAccountShortly);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return backUserAccountShortlyList;
    }

}
