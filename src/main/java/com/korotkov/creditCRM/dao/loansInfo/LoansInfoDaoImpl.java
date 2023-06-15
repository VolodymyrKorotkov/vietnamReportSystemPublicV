package com.korotkov.creditCRM.dao.loansInfo;

import com.korotkov.creditCRM.model.loansInfo.LoanInfoExpiredInfo;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Repository
public class LoansInfoDaoImpl implements LoansInfoDao {
    private static final Logger logger = LoggerFactory.getLogger(LoansInfoDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryCRM")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LoanInfoExpiredInfo> getLoanInfoExpiredInfoList (LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<LoanInfoExpiredInfo> loanInfoExpiredInfoList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select \n" +
                            "date(l.issued_at) as issued_at,\n" +
                            "count(l.id) as total_issued_loan_count,\n" +
                            "sum(l.amount) as total_issued_loan_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_issued_loan_count,\n" +
                            "sum(case when la.client_type = 'REPEAT' then 1 else 0 end) as repeat_issued_loan_count,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then l.amount else 0 end) as potential_issued_loan_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then l.amount else 0 end) as repeat_issued_loan_amount,\n" +
                            "sum(case when l.status not in ('FINISHED', 'EXPIRED', 'GRACE') then 1 else 0 end) as total_active_loan_count,\n" +
                            "sum(case when l.status not in ('FINISHED', 'EXPIRED', 'GRACE') and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_active_loan_count,\n" +
                            "sum(case when l.status not in ('FINISHED', 'EXPIRED', 'GRACE') and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_active_loan_count,\n" +
                            "sum(case when s.on_2_dpd > 0 then 1 else 0 end) as total_on_2_dpd_count,\n" +
                            "sum(case when s.on_2_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_2_dpd_count,\n" +
                            "sum(case when s.on_2_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_2_dpd_count,\n" +
                            "sum(s.on_2_dpd) as total_on_2_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_2_dpd else 0 end) as potential_on_2_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_2_dpd else 0 end) as repeat_on_2_dpd_amount,\n" +
                            "sum(case when s.on_5_dpd > 0 then 1 else 0 end) as total_on_5_dpd_count,\n" +
                            "sum(case when s.on_5_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_5_dpd_count,\n" +
                            "sum(case when s.on_5_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_5_dpd_count,\n" +
                            "sum(s.on_5_dpd) as total_on_5_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_5_dpd else 0 end) as potential_on_5_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_5_dpd else 0 end) as repeat_on_5_dpd_amount,\n" +
                            "sum(case when s.on_10_dpd > 0 then 1 else 0 end) as total_on_10_dpd_count,\n" +
                            "sum(case when s.on_10_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_10_dpd_count,\n" +
                            "sum(case when s.on_10_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_10_dpd_count,\n" +
                            "sum(s.on_10_dpd) as total_on_10_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_10_dpd else 0 end) as potential_on_10_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_10_dpd else 0 end) as repeat_on_10_dpd_amount,\n" +
                            "sum(case when s.on_15_dpd > 0 then 1 else 0 end) as total_on_15_dpd_count,\n" +
                            "sum(case when s.on_15_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_15_dpd_count,\n" +
                            "sum(case when s.on_15_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_15_dpd_count,\n" +
                            "sum(s.on_15_dpd) as total_on_15_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_15_dpd else 0 end) as potential_on_15_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_15_dpd else 0 end) as repeat_on_15_dpd_amount,\n" +
                            "sum(case when s.on_20_dpd > 0 then 1 else 0 end) as total_on_20_dpd_count,\n" +
                            "sum(case when s.on_20_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_20_dpd_count,\n" +
                            "sum(case when s.on_20_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_20_dpd_count,\n" +
                            "sum(s.on_20_dpd) as total_on_20_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_20_dpd else 0 end) as potential_on_20_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_20_dpd else 0 end) as repeat_on_20_dpd_amount,\n" +
                            "sum(case when s.on_25_dpd > 0 then 1 else 0 end) as total_on_25_dpd_count,\n" +
                            "sum(case when s.on_25_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_25_dpd_count,\n" +
                            "sum(case when s.on_25_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_25_dpd_count,\n" +
                            "sum(s.on_25_dpd) as total_on_25_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_25_dpd else 0 end) as potential_on_25_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_25_dpd else 0 end) as repeat_on_25_dpd_amount,\n" +
                            "sum(case when s.on_30_dpd > 0 then 1 else 0 end) as total_on_30_dpd_count,\n" +
                            "sum(case when s.on_30_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_30_dpd_count,\n" +
                            "sum(case when s.on_30_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_30_dpd_count,\n" +
                            "sum(s.on_30_dpd) as total_on_30_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_30_dpd else 0 end) as potential_on_30_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_30_dpd else 0 end) as repeat_on_30_dpd_amount,\n" +
                            "sum(case when s.on_40_dpd > 0 then 1 else 0 end) as total_on_40_dpd_count,\n" +
                            "sum(case when s.on_40_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_40_dpd_count,\n" +
                            "sum(case when s.on_40_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_40_dpd_count,\n" +
                            "sum(s.on_40_dpd) as total_on_40_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_40_dpd else 0 end) as potential_on_40_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_40_dpd else 0 end) as repeat_on_40_dpd_amount,\n" +
                            "sum(case when s.on_50_dpd > 0 then 1 else 0 end) as total_on_50_dpd_count,\n" +
                            "sum(case when s.on_50_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_50_dpd_count,\n" +
                            "sum(case when s.on_50_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_50_dpd_count,\n" +
                            "sum(s.on_50_dpd) as total_on_50_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_50_dpd else 0 end) as potential_on_50_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_50_dpd else 0 end) as repeat_on_50_dpd_amount,\n" +
                            "sum(case when s.on_60_dpd > 0 then 1 else 0 end) as total_on_60_dpd_count,\n" +
                            "sum(case when s.on_60_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_60_dpd_count,\n" +
                            "sum(case when s.on_60_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_60_dpd_count,\n" +
                            "sum(s.on_60_dpd) as total_on_60_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_60_dpd else 0 end) as potential_on_60_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_60_dpd else 0 end) as repeat_on_60_dpd_amount,\n" +
                            "sum(case when s.on_90_dpd > 0 then 1 else 0 end) as total_on_90_dpd_count,\n" +
                            "sum(case when s.on_90_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_90_dpd_count,\n" +
                            "sum(case when s.on_90_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_90_dpd_count,\n" +
                            "sum(s.on_90_dpd) as total_on_90_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_90_dpd else 0 end) as potential_on_90_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_90_dpd else 0 end) as repeat_on_90_dpd_amount,\n" +
                            "sum(case when s.on_120_dpd > 0 then 1 else 0 end) as total_on_120_dpd_count,\n" +
                            "sum(case when s.on_120_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_120_dpd_count,\n" +
                            "sum(case when s.on_120_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_120_dpd_count,\n" +
                            "sum(s.on_120_dpd) as total_on_120_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_120_dpd else 0 end) as potential_on_120_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_120_dpd else 0 end) as repeat_on_120_dpd_amount,\n" +
                            "sum(case when s.on_150_dpd > 0 then 1 else 0 end) as total_on_150_dpd_count,\n" +
                            "sum(case when s.on_150_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_150_dpd_count,\n" +
                            "sum(case when s.on_150_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_150_dpd_count,\n" +
                            "sum(s.on_150_dpd) as total_on_150_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_150_dpd else 0 end) as potential_on_150_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_150_dpd else 0 end) as repeat_on_150_dpd_amount,\n" +
                            "sum(case when s.on_180_dpd > 0 then 1 else 0 end) as total_on_180_dpd_count,\n" +
                            "sum(case when s.on_180_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_180_dpd_count,\n" +
                            "sum(case when s.on_180_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_180_dpd_count,\n" +
                            "sum(s.on_180_dpd) as total_on_180_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_180_dpd else 0 end) as potential_on_180_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_180_dpd else 0 end) as repeat_on_180_dpd_amount,\n" +
                            "sum(case when s.on_250_dpd > 0 then 1 else 0 end) as total_on_250_dpd_count,\n" +
                            "sum(case when s.on_250_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_250_dpd_count,\n" +
                            "sum(case when s.on_250_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_250_dpd_count,\n" +
                            "sum(s.on_250_dpd) as total_on_250_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_250_dpd else 0 end) as potential_on_250_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_250_dpd else 0 end) as repeat_on_250_dpd_amount,\n" +
                            "sum(case when s.on_300_dpd > 0 then 1 else 0 end) as total_on_300_dpd_count,\n" +
                            "sum(case when s.on_300_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_300_dpd_count,\n" +
                            "sum(case when s.on_300_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_300_dpd_count,\n" +
                            "sum(s.on_300_dpd) as total_on_300_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_300_dpd else 0 end) as potential_on_300_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_300_dpd else 0 end) as repeat_on_300_dpd_amount,\n" +
                            "sum(case when s.on_500_dpd > 0 then 1 else 0 end) as total_on_500_dpd_count,\n" +
                            "sum(case when s.on_500_dpd > 0 and la.client_type = 'POTENTIAL' then 1 else 0 end) as potential_on_500_dpd_count,\n" +
                            "sum(case when s.on_500_dpd > 0 and la.client_type = 'REPEAT' then 1 else 0 end) as repeat_on_500_dpd_count,\n" +
                            "sum(s.on_500_dpd) as total_on_500_dpd_amount,\n" +
                            "sum(case when la.client_type = 'POTENTIAL' then s.on_500_dpd else 0 end) as potential_on_500_dpd_amount,\n" +
                            "sum(case when la.client_type = 'REPEAT' then s.on_500_dpd else 0 end) as repeat_on_500_dpd_amount\n" +
                            "from loan l\n" +
                            "left join loan_application la \n" +
                            "on la.id = l.application_id \n" +
                            "left join\n" +
                            "(select snap.loan_id,\n" +
                            "sum(case when snap.days_overdue = 1 then snap.principal else 0 end) as on_2_dpd,\n" +
                            "sum(case when snap.days_overdue = 4 then snap.principal else 0 end) as on_5_dpd,\n" +
                            "sum(case when snap.days_overdue = 9 then snap.principal else 0 end) as on_10_dpd,\n" +
                            "sum(case when snap.days_overdue = 14 then snap.principal else 0 end) as on_15_dpd,\n" +
                            "sum(case when snap.days_overdue = 19 then snap.principal else 0 end) as on_20_dpd,\n" +
                            "sum(case when snap.days_overdue = 24 then snap.principal else 0 end) as on_25_dpd,\n" +
                            "sum(case when snap.days_overdue = 29 then snap.principal else 0 end) as on_30_dpd,\n" +
                            "sum(case when snap.days_overdue = 39 then snap.principal else 0 end) as on_40_dpd,\n" +
                            "sum(case when snap.days_overdue = 49 then snap.principal else 0 end) as on_50_dpd,\n" +
                            "sum(case when snap.days_overdue = 59 then snap.principal else 0 end) as on_60_dpd,\n" +
                            "sum(case when snap.days_overdue = 89 then snap.principal else 0 end) as on_90_dpd,\n" +
                            "sum(case when snap.days_overdue = 119 then snap.principal else 0 end) as on_120_dpd,\n" +
                            "sum(case when snap.days_overdue = 149 then snap.principal else 0 end) as on_150_dpd,\n" +
                            "sum(case when snap.days_overdue = 179 then snap.principal else 0 end) as on_180_dpd,\n" +
                            "sum(case when snap.days_overdue = 249 then snap.principal else 0 end) as on_250_dpd,\n" +
                            "sum(case when snap.days_overdue = 299 then snap.principal else 0 end) as on_300_dpd,\n" +
                            "sum(case when snap.days_overdue = 499 then snap.principal else 0 end) as on_500_dpd\n" +
                            "from\n" +
                            "(select ls.loan_id, ls.days_overdue, max(ls.principal) as principal from loan_snapshot ls \n" +
                            "where ls.loan_status = 'EXPIRED'\n" +
                            "and ls.\"date\" between :dateFrom and :dateYesterday \n" +
                            "and ls.days_overdue in (1,4,9,14,19,24,29,39,49,59,89,119,149,179,249,299,499)\n" +
                            "group by ls.loan_id, ls.days_overdue\n" +
                            ") as snap\n" +
                            "group by snap.loan_id) as s\n" +
                            "on s.loan_id = l.id\n" +
                            "where date(l.issued_at) between :dateFrom and :dateTo \n" +
                            "group by date(l.issued_at)")
                            .addScalar("issued_at", new LocalDateType())
                            .addScalar("total_issued_loan_count", new LongType())
                            .addScalar("total_issued_loan_amount", new DoubleType())
                            .addScalar("potential_issued_loan_count", new LongType())
                            .addScalar("repeat_issued_loan_count", new LongType())
                            .addScalar("potential_issued_loan_amount", new DoubleType())
                            .addScalar("repeat_issued_loan_amount", new DoubleType())
                            .addScalar("total_active_loan_count", new LongType())
                            .addScalar("potential_active_loan_count", new LongType())
                            .addScalar("repeat_active_loan_count", new LongType())
                            .addScalar("total_on_2_dpd_count", new LongType())
                            .addScalar("potential_on_2_dpd_count", new LongType())
                            .addScalar("repeat_on_2_dpd_count", new LongType())
                            .addScalar("total_on_2_dpd_amount", new DoubleType())
                            .addScalar("potential_on_2_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_2_dpd_amount", new DoubleType())
                            .addScalar("total_on_5_dpd_count", new LongType())
                            .addScalar("potential_on_5_dpd_count", new LongType())
                            .addScalar("repeat_on_5_dpd_count", new LongType())
                            .addScalar("total_on_5_dpd_amount", new DoubleType())
                            .addScalar("potential_on_5_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_5_dpd_amount", new DoubleType())
                            .addScalar("total_on_10_dpd_count", new LongType())
                            .addScalar("potential_on_10_dpd_count", new LongType())
                            .addScalar("repeat_on_10_dpd_count", new LongType())
                            .addScalar("total_on_10_dpd_amount", new DoubleType())
                            .addScalar("potential_on_10_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_10_dpd_amount", new DoubleType())
                            .addScalar("total_on_15_dpd_count", new LongType())
                            .addScalar("potential_on_15_dpd_count", new LongType())
                            .addScalar("repeat_on_15_dpd_count", new LongType())
                            .addScalar("total_on_15_dpd_amount", new DoubleType())
                            .addScalar("potential_on_15_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_15_dpd_amount", new DoubleType())
                            .addScalar("total_on_20_dpd_count", new LongType())
                            .addScalar("potential_on_20_dpd_count", new LongType())
                            .addScalar("repeat_on_20_dpd_count", new LongType())
                            .addScalar("total_on_20_dpd_amount", new DoubleType())
                            .addScalar("potential_on_20_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_20_dpd_amount", new DoubleType())
                            .addScalar("total_on_25_dpd_count", new LongType())
                            .addScalar("potential_on_25_dpd_count", new LongType())
                            .addScalar("repeat_on_25_dpd_count", new LongType())
                            .addScalar("total_on_25_dpd_amount", new DoubleType())
                            .addScalar("potential_on_25_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_25_dpd_amount", new DoubleType())
                            .addScalar("total_on_30_dpd_count", new LongType())
                            .addScalar("potential_on_30_dpd_count", new LongType())
                            .addScalar("repeat_on_30_dpd_count", new LongType())
                            .addScalar("total_on_30_dpd_amount", new DoubleType())
                            .addScalar("potential_on_30_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_30_dpd_amount", new DoubleType())
                            .addScalar("total_on_40_dpd_count", new LongType())
                            .addScalar("potential_on_40_dpd_count", new LongType())
                            .addScalar("repeat_on_40_dpd_count", new LongType())
                            .addScalar("total_on_40_dpd_amount", new DoubleType())
                            .addScalar("potential_on_40_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_40_dpd_amount", new DoubleType())
                            .addScalar("total_on_50_dpd_count", new LongType())
                            .addScalar("potential_on_50_dpd_count", new LongType())
                            .addScalar("repeat_on_50_dpd_count", new LongType())
                            .addScalar("total_on_50_dpd_amount", new DoubleType())
                            .addScalar("potential_on_50_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_50_dpd_amount", new DoubleType())
                            .addScalar("total_on_60_dpd_count", new LongType())
                            .addScalar("potential_on_60_dpd_count", new LongType())
                            .addScalar("repeat_on_60_dpd_count", new LongType())
                            .addScalar("total_on_60_dpd_amount", new DoubleType())
                            .addScalar("potential_on_60_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_60_dpd_amount", new DoubleType())
                            .addScalar("total_on_90_dpd_count", new LongType())
                            .addScalar("potential_on_90_dpd_count", new LongType())
                            .addScalar("repeat_on_90_dpd_count", new LongType())
                            .addScalar("total_on_90_dpd_amount", new DoubleType())
                            .addScalar("potential_on_90_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_90_dpd_amount", new DoubleType())
                            .addScalar("total_on_120_dpd_count", new LongType())
                            .addScalar("potential_on_120_dpd_count", new LongType())
                            .addScalar("repeat_on_120_dpd_count", new LongType())
                            .addScalar("total_on_120_dpd_amount", new DoubleType())
                            .addScalar("potential_on_120_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_120_dpd_amount", new DoubleType())
                            .addScalar("total_on_150_dpd_count", new LongType())
                            .addScalar("potential_on_150_dpd_count", new LongType())
                            .addScalar("repeat_on_150_dpd_count", new LongType())
                            .addScalar("total_on_150_dpd_amount", new DoubleType())
                            .addScalar("potential_on_150_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_150_dpd_amount", new DoubleType())
                            .addScalar("total_on_180_dpd_count", new LongType())
                            .addScalar("potential_on_180_dpd_count", new LongType())
                            .addScalar("repeat_on_180_dpd_count", new LongType())
                            .addScalar("total_on_180_dpd_amount", new DoubleType())
                            .addScalar("potential_on_180_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_180_dpd_amount", new DoubleType())
                            .addScalar("total_on_250_dpd_count", new LongType())
                            .addScalar("potential_on_250_dpd_count", new LongType())
                            .addScalar("repeat_on_250_dpd_count", new LongType())
                            .addScalar("total_on_250_dpd_amount", new DoubleType())
                            .addScalar("potential_on_250_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_250_dpd_amount", new DoubleType())
                            .addScalar("total_on_300_dpd_count", new LongType())
                            .addScalar("potential_on_300_dpd_count", new LongType())
                            .addScalar("repeat_on_300_dpd_count", new LongType())
                            .addScalar("total_on_300_dpd_amount", new DoubleType())
                            .addScalar("potential_on_300_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_300_dpd_amount", new DoubleType())
                            .addScalar("total_on_500_dpd_count", new LongType())
                            .addScalar("potential_on_500_dpd_count", new LongType())
                            .addScalar("repeat_on_500_dpd_count", new LongType())
                            .addScalar("total_on_500_dpd_amount", new DoubleType())
                            .addScalar("potential_on_500_dpd_amount", new DoubleType())
                            .addScalar("repeat_on_500_dpd_amount", new DoubleType())
                            .setParameter("dateYesterday", LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(1))
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();
            for (Object[] row : rows) {
                LoanInfoExpiredInfo loanInfoExpiredInfo = new LoanInfoExpiredInfo();
                if (row[0] != null) {
                    loanInfoExpiredInfo.setDate(LocalDate.parse(row[0].toString()));
                }
                if (row[1] != null) {
                    loanInfoExpiredInfo.setTotalIssuedLoanCount(Long.parseLong(row[1].toString()));
                }
                if (row[2] != null) {
                    loanInfoExpiredInfo.setTotalIssuedLoanAmount(Double.parseDouble(row[2].toString()));
                }
                if (row[3] != null) {
                    loanInfoExpiredInfo.setPotentialIssuedLoanCount(Long.parseLong(row[3].toString()));
                }
                if (row[4] != null) {
                    loanInfoExpiredInfo.setRepeatIssuedLoanCount(Long.parseLong(row[4].toString()));
                }
                if (row[5] != null) {
                    loanInfoExpiredInfo.setPotentialIssuedLoanAmount(Double.parseDouble(row[5].toString()));
                }
                if (row[6] != null) {
                    loanInfoExpiredInfo.setRepeatIssuedLoanAmount(Double.parseDouble(row[6].toString()));
                }
                if (row[7] != null) {
                    loanInfoExpiredInfo.setTotalActiveLoanCount(Long.parseLong(row[7].toString()));
                }
                if (row[8] != null) {
                    loanInfoExpiredInfo.setPotentialActiveLoanCount(Long.parseLong(row[8].toString()));
                }
                if (row[9] != null) {
                    loanInfoExpiredInfo.setRepeatActiveLoanCount(Long.parseLong(row[9].toString()));
                }
                if (row[10] != null) {
                    loanInfoExpiredInfo.setTotalOn2DpdCount(Long.parseLong(row[10].toString()));
                }
                if (row[11] != null) {
                    loanInfoExpiredInfo.setPotentialOn2DpdCount(Long.parseLong(row[11].toString()));
                }
                if (row[12] != null) {
                    loanInfoExpiredInfo.setRepeatOn2DpdCount(Long.parseLong(row[12].toString()));
                }
                if (row[13] != null) {
                    loanInfoExpiredInfo.setTotalOn2DpdAmount(Double.parseDouble(row[13].toString()));
                }
                if (row[14] != null) {
                    loanInfoExpiredInfo.setPotentialOn2DpdAmount(Double.parseDouble(row[14].toString()));
                }
                if (row[15] != null) {
                    loanInfoExpiredInfo.setRepeatOn2DpdAmount(Double.parseDouble(row[15].toString()));
                }
                if (row[16] != null) {
                    loanInfoExpiredInfo.setTotalOn5DpdCount(Long.parseLong(row[16].toString()));
                }
                if (row[17] != null) {
                    loanInfoExpiredInfo.setPotentialOn5DpdCount(Long.parseLong(row[17].toString()));
                }
                if (row[18] != null) {
                    loanInfoExpiredInfo.setRepeatOn5DpdCount(Long.parseLong(row[18].toString()));
                }
                if (row[19] != null) {
                    loanInfoExpiredInfo.setTotalOn5DpdAmount(Double.parseDouble(row[19].toString()));
                }
                if (row[20] != null) {
                    loanInfoExpiredInfo.setPotentialOn5DpdAmount(Double.parseDouble(row[20].toString()));
                }
                if (row[21] != null) {
                    loanInfoExpiredInfo.setRepeatOn5DpdAmount(Double.parseDouble(row[21].toString()));
                }
                if (row[22] != null) {
                    loanInfoExpiredInfo.setTotalOn10DpdCount(Long.parseLong(row[22].toString()));
                }
                if (row[23] != null) {
                    loanInfoExpiredInfo.setPotentialOn10DpdCount(Long.parseLong(row[23].toString()));
                }
                if (row[24] != null) {
                    loanInfoExpiredInfo.setRepeatOn10DpdCount(Long.parseLong(row[24].toString()));
                }
                if (row[25] != null) {
                    loanInfoExpiredInfo.setTotalOn10DpdAmount(Double.parseDouble(row[25].toString()));
                }
                if (row[26] != null) {
                    loanInfoExpiredInfo.setPotentialOn10DpdAmount(Double.parseDouble(row[26].toString()));
                }
                if (row[27] != null) {
                    loanInfoExpiredInfo.setRepeatOn10DpdAmount(Double.parseDouble(row[27].toString()));
                }
                if (row[28] != null) {
                    loanInfoExpiredInfo.setTotalOn15DpdCount(Long.parseLong(row[28].toString()));
                }
                if (row[29] != null) {
                    loanInfoExpiredInfo.setPotentialOn15DpdCount(Long.parseLong(row[29].toString()));
                }
                if (row[30] != null) {
                    loanInfoExpiredInfo.setRepeatOn15DpdCount(Long.parseLong(row[30].toString()));
                }
                if (row[31] != null) {
                    loanInfoExpiredInfo.setTotalOn15DpdAmount(Double.parseDouble(row[31].toString()));
                }
                if (row[32] != null) {
                    loanInfoExpiredInfo.setPotentialOn15DpdAmount(Double.parseDouble(row[32].toString()));
                }
                if (row[33] != null) {
                    loanInfoExpiredInfo.setRepeatOn15DpdAmount(Double.parseDouble(row[33].toString()));
                }
                if (row[34] != null) {
                    loanInfoExpiredInfo.setTotalOn20DpdCount(Long.parseLong(row[34].toString()));
                }
                if (row[35] != null) {
                    loanInfoExpiredInfo.setPotentialOn20DpdCount(Long.parseLong(row[35].toString()));
                }
                if (row[36] != null) {
                    loanInfoExpiredInfo.setRepeatOn20DpdCount(Long.parseLong(row[36].toString()));
                }
                if (row[37] != null) {
                    loanInfoExpiredInfo.setTotalOn20DpdAmount(Double.parseDouble(row[37].toString()));
                }
                if (row[38] != null) {
                    loanInfoExpiredInfo.setPotentialOn20DpdAmount(Double.parseDouble(row[38].toString()));
                }
                if (row[39] != null) {
                    loanInfoExpiredInfo.setRepeatOn20DpdAmount(Double.parseDouble(row[39].toString()));
                }
                if (row[40] != null) {
                    loanInfoExpiredInfo.setTotalOn25DpdCount(Long.parseLong(row[40].toString()));
                }
                if (row[41] != null) {
                    loanInfoExpiredInfo.setPotentialOn25DpdCount(Long.parseLong(row[41].toString()));
                }
                if (row[42] != null) {
                    loanInfoExpiredInfo.setRepeatOn25DpdCount(Long.parseLong(row[42].toString()));
                }
                if (row[43] != null) {
                    loanInfoExpiredInfo.setTotalOn25DpdAmount(Double.parseDouble(row[43].toString()));
                }
                if (row[44] != null) {
                    loanInfoExpiredInfo.setPotentialOn25DpdAmount(Double.parseDouble(row[44].toString()));
                }
                if (row[45] != null) {
                    loanInfoExpiredInfo.setRepeatOn25DpdAmount(Double.parseDouble(row[45].toString()));
                }
                if (row[46] != null) {
                    loanInfoExpiredInfo.setTotalOn30DpdCount(Long.parseLong(row[46].toString()));
                }
                if (row[47] != null) {
                    loanInfoExpiredInfo.setPotentialOn30DpdCount(Long.parseLong(row[47].toString()));
                }
                if (row[48] != null) {
                    loanInfoExpiredInfo.setRepeatOn30DpdCount(Long.parseLong(row[48].toString()));
                }
                if (row[49] != null) {
                    loanInfoExpiredInfo.setTotalOn30DpdAmount(Double.parseDouble(row[49].toString()));
                }
                if (row[50] != null) {
                    loanInfoExpiredInfo.setPotentialOn30DpdAmount(Double.parseDouble(row[50].toString()));
                }
                if (row[51] != null) {
                    loanInfoExpiredInfo.setRepeatOn30DpdAmount(Double.parseDouble(row[51].toString()));
                }
                if (row[52] != null) {
                    loanInfoExpiredInfo.setTotalOn40DpdCount(Long.parseLong(row[52].toString()));
                }
                if (row[53] != null) {
                    loanInfoExpiredInfo.setPotentialOn40DpdCount(Long.parseLong(row[53].toString()));
                }
                if (row[54] != null) {
                    loanInfoExpiredInfo.setRepeatOn40DpdCount(Long.parseLong(row[54].toString()));
                }
                if (row[55] != null) {
                    loanInfoExpiredInfo.setTotalOn40DpdAmount(Double.parseDouble(row[55].toString()));
                }
                if (row[56] != null) {
                    loanInfoExpiredInfo.setPotentialOn40DpdAmount(Double.parseDouble(row[56].toString()));
                }
                if (row[57] != null) {
                    loanInfoExpiredInfo.setRepeatOn40DpdAmount(Double.parseDouble(row[57].toString()));
                }
                if (row[58] != null) {
                    loanInfoExpiredInfo.setTotalOn50DpdCount(Long.parseLong(row[58].toString()));
                }
                if (row[59] != null) {
                    loanInfoExpiredInfo.setPotentialOn50DpdCount(Long.parseLong(row[59].toString()));
                }
                if (row[60] != null) {
                    loanInfoExpiredInfo.setRepeatOn50DpdCount(Long.parseLong(row[60].toString()));
                }
                if (row[61] != null) {
                    loanInfoExpiredInfo.setTotalOn50DpdAmount(Double.parseDouble(row[61].toString()));
                }
                if (row[62] != null) {
                    loanInfoExpiredInfo.setPotentialOn50DpdAmount(Double.parseDouble(row[62].toString()));
                }
                if (row[63] != null) {
                    loanInfoExpiredInfo.setRepeatOn50DpdAmount(Double.parseDouble(row[63].toString()));
                }
                if (row[64] != null) {
                    loanInfoExpiredInfo.setTotalOn60DpdCount(Long.parseLong(row[64].toString()));
                }
                if (row[65] != null) {
                    loanInfoExpiredInfo.setPotentialOn60DpdCount(Long.parseLong(row[65].toString()));
                }
                if (row[66] != null) {
                    loanInfoExpiredInfo.setRepeatOn60DpdCount(Long.parseLong(row[66].toString()));
                }
                if (row[67] != null) {
                    loanInfoExpiredInfo.setTotalOn60DpdAmount(Double.parseDouble(row[67].toString()));
                }
                if (row[68] != null) {
                    loanInfoExpiredInfo.setPotentialOn60DpdAmount(Double.parseDouble(row[68].toString()));
                }
                if (row[69] != null) {
                    loanInfoExpiredInfo.setRepeatOn60DpdAmount(Double.parseDouble(row[69].toString()));
                }
                if (row[70] != null) {
                    loanInfoExpiredInfo.setTotalOn90DpdCount(Long.parseLong(row[70].toString()));
                }
                if (row[71] != null) {
                    loanInfoExpiredInfo.setPotentialOn90DpdCount(Long.parseLong(row[71].toString()));
                }
                if (row[72] != null) {
                    loanInfoExpiredInfo.setRepeatOn90DpdCount(Long.parseLong(row[72].toString()));
                }
                if (row[73] != null) {
                    loanInfoExpiredInfo.setTotalOn90DpdAmount(Double.parseDouble(row[73].toString()));
                }
                if (row[74] != null) {
                    loanInfoExpiredInfo.setPotentialOn90DpdAmount(Double.parseDouble(row[74].toString()));
                }
                if (row[75] != null) {
                    loanInfoExpiredInfo.setRepeatOn90DpdAmount(Double.parseDouble(row[75].toString()));
                }
                if (row[76] != null) {
                    loanInfoExpiredInfo.setTotalOn120DpdCount(Long.parseLong(row[76].toString()));
                }
                if (row[77] != null) {
                    loanInfoExpiredInfo.setPotentialOn120DpdCount(Long.parseLong(row[77].toString()));
                }
                if (row[78] != null) {
                    loanInfoExpiredInfo.setRepeatOn120DpdCount(Long.parseLong(row[78].toString()));
                }
                if (row[79] != null) {
                    loanInfoExpiredInfo.setTotalOn120DpdAmount(Double.parseDouble(row[79].toString()));
                }
                if (row[80] != null) {
                    loanInfoExpiredInfo.setPotentialOn120DpdAmount(Double.parseDouble(row[80].toString()));
                }
                if (row[81] != null) {
                    loanInfoExpiredInfo.setRepeatOn120DpdAmount(Double.parseDouble(row[81].toString()));
                }
                if (row[82] != null) {
                    loanInfoExpiredInfo.setTotalOn150DpdCount(Long.parseLong(row[82].toString()));
                }
                if (row[83] != null) {
                    loanInfoExpiredInfo.setPotentialOn150DpdCount(Long.parseLong(row[83].toString()));
                }
                if (row[84] != null) {
                    loanInfoExpiredInfo.setRepeatOn150DpdCount(Long.parseLong(row[84].toString()));
                }
                if (row[85] != null) {
                    loanInfoExpiredInfo.setTotalOn150DpdAmount(Double.parseDouble(row[85].toString()));
                }
                if (row[86] != null) {
                    loanInfoExpiredInfo.setPotentialOn150DpdAmount(Double.parseDouble(row[86].toString()));
                }
                if (row[87] != null) {
                    loanInfoExpiredInfo.setRepeatOn150DpdAmount(Double.parseDouble(row[87].toString()));
                }
                if (row[88] != null) {
                    loanInfoExpiredInfo.setTotalOn180DpdCount(Long.parseLong(row[88].toString()));
                }
                if (row[89] != null) {
                    loanInfoExpiredInfo.setPotentialOn180DpdCount(Long.parseLong(row[89].toString()));
                }
                if (row[90] != null) {
                    loanInfoExpiredInfo.setRepeatOn180DpdCount(Long.parseLong(row[90].toString()));
                }
                if (row[91] != null) {
                    loanInfoExpiredInfo.setTotalOn180DpdAmount(Double.parseDouble(row[91].toString()));
                }
                if (row[92] != null) {
                    loanInfoExpiredInfo.setPotentialOn180DpdAmount(Double.parseDouble(row[92].toString()));
                }
                if (row[93] != null) {
                    loanInfoExpiredInfo.setRepeatOn180DpdAmount(Double.parseDouble(row[93].toString()));
                }
                if (row[94] != null) {
                    loanInfoExpiredInfo.setTotalOn250DpdCount(Long.parseLong(row[94].toString()));
                }
                if (row[95] != null) {
                    loanInfoExpiredInfo.setPotentialOn250DpdCount(Long.parseLong(row[95].toString()));
                }
                if (row[96] != null) {
                    loanInfoExpiredInfo.setRepeatOn250DpdCount(Long.parseLong(row[96].toString()));
                }
                if (row[97] != null) {
                    loanInfoExpiredInfo.setTotalOn250DpdAmount(Double.parseDouble(row[97].toString()));
                }
                if (row[98] != null) {
                    loanInfoExpiredInfo.setPotentialOn250DpdAmount(Double.parseDouble(row[98].toString()));
                }
                if (row[99] != null) {
                    loanInfoExpiredInfo.setRepeatOn250DpdAmount(Double.parseDouble(row[99].toString()));
                }
                if (row[100] != null) {
                    loanInfoExpiredInfo.setTotalOn300DpdCount(Long.parseLong(row[100].toString()));
                }
                if (row[101] != null) {
                    loanInfoExpiredInfo.setPotentialOn300DpdCount(Long.parseLong(row[101].toString()));
                }
                if (row[102] != null) {
                    loanInfoExpiredInfo.setRepeatOn300DpdCount(Long.parseLong(row[102].toString()));
                }
                if (row[103] != null) {
                    loanInfoExpiredInfo.setTotalOn300DpdAmount(Double.parseDouble(row[103].toString()));
                }
                if (row[104] != null) {
                    loanInfoExpiredInfo.setPotentialOn300DpdAmount(Double.parseDouble(row[104].toString()));
                }
                if (row[105] != null) {
                    loanInfoExpiredInfo.setRepeatOn300DpdAmount(Double.parseDouble(row[105].toString()));
                }
                if (row[106] != null) {
                    loanInfoExpiredInfo.setTotalOn500DpdCount(Long.parseLong(row[106].toString()));
                }
                if (row[107] != null) {
                    loanInfoExpiredInfo.setPotentialOn500DpdCount(Long.parseLong(row[107].toString()));
                }
                if (row[108] != null) {
                    loanInfoExpiredInfo.setRepeatOn500DpdCount(Long.parseLong(row[108].toString()));
                }
                if (row[109] != null) {
                    loanInfoExpiredInfo.setTotalOn500DpdAmount(Double.parseDouble(row[109].toString()));
                }
                if (row[110] != null) {
                    loanInfoExpiredInfo.setPotentialOn500DpdAmount(Double.parseDouble(row[110].toString()));
                }
                if (row[111] != null) {
                    loanInfoExpiredInfo.setRepeatOn500DpdAmount(Double.parseDouble(row[111].toString()));
                }
                if (loanInfoExpiredInfo.getDate() != null) {
                    loanInfoExpiredInfoList.add(loanInfoExpiredInfo);
                }
            }
        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + ": " + e.getMessage() + " in " + e.getClass());
        } finally {
            session.close();
        }
        return loanInfoExpiredInfoList;
    }

}
