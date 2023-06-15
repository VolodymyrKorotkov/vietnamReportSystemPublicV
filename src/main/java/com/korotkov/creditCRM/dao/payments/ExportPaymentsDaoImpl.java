package com.korotkov.creditCRM.dao.payments;

import com.korotkov.creditCRM.model.payments.ExportPaymentWithPaidFees;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LocalDateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Repository
public class ExportPaymentsDaoImpl implements ExportPaymentsDao {
    private static final Logger logger = LoggerFactory.getLogger(ExportPaymentsDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryCRM")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExportPaymentWithPaidFees> getExportPaymentWithPaidList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<ExportPaymentWithPaidFees> exportPaymentWithPaidFeesList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select distinct on (t_6.date, t_6.loan_id)\n" +
                            "t_6.date,\n" +
                            "t_6.loan_id,\n" +
                            "t_5.app_id,\n" +
                            "t_6.balance,\n" +
                            "t_6.loan_status,\n" +
                            "t_5.broker_name,\n" +
                            "t_5.pawnshop_name,\n" +
                            "t_5.document_id_number as CMND,\n" +
                            "t_5.first_name as client_name,\n" +
                            "t_5.email,\n" +
                            "t_5.loan_agreement,\n" +
                            "t_5.consulting_agreement,\n" +
                            "t_6.paid_for_date,\n" +
                            "t_6.paid_prolongation_for_date,\n" +
                            "t_6.paid_consulting_for_date,\n" +
                            "t_6.paid_collection_fee_for_date,\n" +
                            "t_6.paid_interest_for_date,\n" +
                            "t_6.paid_penalty_interest_for_date,\n" +
                            "t_6.paid_principal_for_date\n" +
                            "from \n" +
                            "(select \n" +
                            "ls.date,\n" +
                            "ls.id,\n" +
                            "ls.loan_id,\n" +
                            "ls.balance,\n" +
                            "ls.loan_status,\n" +
                            "ls.paid_for_date,\n" +
                            "(case when (coalesce((select ls2.al_prolongation_fee from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_prolongation_fee, ls.al_prolongation_fee) * -1) >= 0 then (coalesce((select ls2.al_prolongation_fee from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_prolongation_fee, ls.al_prolongation_fee) * -1) else (coalesce((select ls2.al_prolongation_fee from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_prolongation_fee, ls.al_prolongation_fee) * 1) end) as paid_prolongation_for_date,\n" +
                            "(case when (coalesce((select ls2.al_consulting_fee from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_consulting_fee, ls.al_consulting_fee) * -1) >= 0 then (coalesce((select ls2.al_consulting_fee from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_consulting_fee, ls.al_consulting_fee) * -1) else (coalesce((select ls2.al_consulting_fee from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_consulting_fee, ls.al_consulting_fee) * 1) end) as paid_consulting_for_date,\n" +
                            "(case when (coalesce((select (ls2.al_collection_fee + ls2.al_gp_collection_fee) as collection_fee from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - (ls.al_collection_fee + ls.al_gp_collection_fee),\n" +
                            "(ls.al_collection_fee + ls.al_gp_collection_fee) ) * -1) >= 0 then (coalesce((select (ls2.al_collection_fee + ls2.al_gp_collection_fee) as collection_fee from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - (ls.al_collection_fee + ls.al_gp_collection_fee),\n" +
                            "(ls.al_collection_fee + ls.al_gp_collection_fee) ) * -1) else (coalesce((select (ls2.al_collection_fee + ls2.al_gp_collection_fee) as collection_fee from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - (ls.al_collection_fee + ls.al_gp_collection_fee),\n" +
                            "(ls.al_collection_fee + ls.al_gp_collection_fee) ) * 1) end) as paid_collection_fee_for_date,\n" +
                            "(case when (coalesce((select ls2.al_interest from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_interest, ls.al_interest) * -1) >= 0 then (coalesce((select ls2.al_interest from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_interest, ls.al_interest) * -1) else (coalesce((select ls2.al_interest from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_interest, ls.al_interest) * 1) end) as paid_interest_for_date,\n" +
                            "(case when (coalesce((select (ls2.al_gp_penalty_interest + ls2.al_penalty_interest) as penalty_interest from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - (ls.al_gp_penalty_interest + ls.al_penalty_interest),\n" +
                            "(ls.al_gp_penalty_interest + ls.al_penalty_interest) ) * -1) >= 0 then (coalesce((select (ls2.al_gp_penalty_interest + ls2.al_penalty_interest) as penalty_interest from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - (ls.al_gp_penalty_interest + ls.al_penalty_interest),\n" +
                            "(ls.al_gp_penalty_interest + ls.al_penalty_interest) ) * -1) else (coalesce((select (ls2.al_gp_penalty_interest + ls2.al_penalty_interest) as penalty_interest from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - (ls.al_gp_penalty_interest + ls.al_penalty_interest),\n" +
                            "(ls.al_gp_penalty_interest + ls.al_penalty_interest) ) * 1) end) as paid_penalty_interest_for_date,\n" +
                            "(case when (coalesce((select ls2.al_principal from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_principal, ls.al_principal) * -1) >= 0 then (coalesce((select ls2.al_principal from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_principal, ls.al_principal) * -1) else (coalesce((select ls2.al_principal from public.loan_snapshot ls2 where \n" +
                            "ls2.loan_id = ls.loan_id and ls.date = ls2.date + 1) - ls.al_principal, ls.al_principal) * 1) end) as paid_principal_for_date\n" +
                            "from public.loan_snapshot ls \n" +
                            "where ls.paid_for_date > 0) as t_6\n" +
                            "left join \n" +
                            "(select \n" +
                            "l.id as loan_id,\n" +
                            "l.application_id as app_id,\n" +
                            "l.client_id,\n" +
                            "l.product_id,\n" +
                            "lp.lending_company_id,\n" +
                            "lp.credit_broker_id,\n" +
                            "cb.title as broker_name,\n" +
                            "lc.title as pawnshop_name,\n" +
                            "cua.document_id_number,\n" +
                            "cua.first_name,\n" +
                            "cua.email,\n" +
                            "l.contract_number as loan_agreement,\n" +
                            "concat(l.contract_number, ' ', '/BCA') as consulting_agreement\n" +
                            "from public.loan l \n" +
                            "left join\n" +
                            "public.loan_product lp \n" +
                            "on \n" +
                            "l.product_id = lp.id\n" +
                            "left join \n" +
                            "public.credit_broker cb \n" +
                            "on \n" +
                            "lp.credit_broker_id = cb.id\n" +
                            "left join \n" +
                            "public.lending_company lc \n" +
                            "on \n" +
                            "lp.lending_company_id = lc.id\n" +
                            "left join \n" +
                            "public.client_user_account cua \n" +
                            "on \n" +
                            "cua.id = l.client_id) as t_5\n" +
                            "on \n" +
                            "t_5.loan_id = t_6.loan_id\n" +
                            "\t\t\twhere\n" +
                            "date(t_6.date) >= :dateFrom\n" +
                            "and\n" +
                            "date(t_6.date) <= :dateTo")
                            .addScalar("date", new LocalDateType())
                            .addScalar("loan_id", new LongType())
                            .addScalar("app_id", new LongType())
                            .addScalar("balance", new DoubleType())
                            .addScalar("loan_status", new StringType())
                            .addScalar("broker_name", new StringType())
                            .addScalar("pawnshop_name", new StringType())
                            .addScalar("cmnd", new StringType())
                            .addScalar("client_name", new StringType())
                            .addScalar("email", new StringType())
                            .addScalar("loan_agreement", new StringType())
                            .addScalar("consulting_agreement", new StringType())
                            .addScalar("paid_for_date", new DoubleType())
                            .addScalar("paid_prolongation_for_date", new DoubleType())
                            .addScalar("paid_consulting_for_date", new DoubleType())
                            .addScalar("paid_collection_fee_for_date", new DoubleType())
                            .addScalar("paid_interest_for_date", new DoubleType())
                            .addScalar("paid_penalty_interest_for_date", new DoubleType())
                            .addScalar("paid_principal_for_date", new DoubleType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();
            for (Object[] row : rows) {
                ExportPaymentWithPaidFees paymentWithPaidFees = new ExportPaymentWithPaidFees();
                if (row[0] != null) {
                    paymentWithPaidFees.setDate(LocalDate.parse(row[0].toString()));
                }
                if (row[1] != null) {
                    paymentWithPaidFees.setLoanId(Long.parseLong(row[1].toString()));
                }
                if (row[2] != null) {
                    paymentWithPaidFees.setAppId(Long.parseLong(row[2].toString()));
                }
                if (row[3] != null) {
                    paymentWithPaidFees.setBalance(BigDecimal.valueOf(Double.parseDouble(row[3].toString())));
                }
                if (row[4] != null) {
                    paymentWithPaidFees.setLoanStatus(row[4].toString());
                }
                if (row[5] != null) {
                    paymentWithPaidFees.setBrokerName(row[5].toString());
                }
                if (row[6] != null) {
                    paymentWithPaidFees.setPawnshopName(row[6].toString());
                }
                if (row[7] != null) {
                    paymentWithPaidFees.setDocumentNumber(row[7].toString());
                }
                if (row[8] != null) {
                    paymentWithPaidFees.setClientName(row[8].toString());
                }
                if (row[9] != null) {
                    paymentWithPaidFees.setEmail(row[9].toString());
                }
                if (row[10] != null) {
                    paymentWithPaidFees.setLoanAgreement(row[10].toString());
                }
                if (row[11] != null) {
                    paymentWithPaidFees.setConsultingAgreement(row[11].toString());
                }
                if (row[12] != null) {
                    paymentWithPaidFees.setPaidForDate(BigDecimal.valueOf(Double.parseDouble(row[12].toString())));
                }
                if (row[13] != null) {
                    paymentWithPaidFees.setPaidProlongationForDate(BigDecimal.valueOf(Double.parseDouble(row[13].toString())));
                }
                if (row[14] != null) {
                    paymentWithPaidFees.setPaidConsultingForDate(BigDecimal.valueOf(Double.parseDouble(row[14].toString())));
                }
                if (row[15] != null) {
                    paymentWithPaidFees.setPaidCollectionFeeForDate(BigDecimal.valueOf(Double.parseDouble(row[15].toString())));
                }
                if (row[16] != null) {
                    paymentWithPaidFees.setPaidInterestForDate(BigDecimal.valueOf(Double.parseDouble(row[16].toString())));
                }
                if (row[17] != null) {
                    paymentWithPaidFees.setPaidPenaltyInterestForDate(BigDecimal.valueOf(Double.parseDouble(row[17].toString())));
                }
                if (row[18] != null) {
                    paymentWithPaidFees.setPaidPrincipalForDate(BigDecimal.valueOf(Double.parseDouble(row[18].toString())));
                }

                if (paymentWithPaidFees.getPaidForDate() != null) {
                    paymentWithPaidFees.setTotalRow(false);
                    exportPaymentWithPaidFeesList.add(paymentWithPaidFees);
                }
            }
        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + ": " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return exportPaymentWithPaidFeesList;
    }



}
