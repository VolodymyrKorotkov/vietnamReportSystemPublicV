package com.korotkov.vicidial.dao.collection.preSoftBonusReport;

import com.korotkov.creditCRM.dao.collection.commonDataWithAssignedCollectorReport.CommonDataWithAssignedCollectorReportDaoImpl;
import com.korotkov.vicidial.model.collection.preSoftBonusReport.PreSoftBonusExportCall;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LocalDateTimeType;
import org.hibernate.type.StringType;
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
public class PreSoftBonusReportVicidialDaoImpl implements PreSoftBonusReportVicidialDao {
    private static final Logger logger = LoggerFactory.getLogger(PreSoftBonusReportVicidialDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryVicidial")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PreSoftBonusExportCall> getPreSoftBonusExportCallList(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<PreSoftBonusExportCall> preSoftBonusExportCallList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select \n" +
                                    "vala.event_time as call_date ,\n" +
                                    "vu.email ,\n" +
                                    "vala.talk_sec ,\n" +
                                    "vala.status as call_status ,\n" +
                                    "vala.comments as call_type,\n" +
                                    "vl.phone_number\n" +
                                    "from asterisk.vicidial_agent_log_archive vala\n" +
                                    "left join asterisk.vicidial_list vl \n" +
                                    "on vl.lead_id = vala.lead_id  \n" +
                                    "left join asterisk.vicidial_users vu \n" +
                                    "on vu.`user` = vala.`user` \n" +
                                    "where vala.user_group = 'collection'\n" +
                                    "and vala.lead_id is not null\n" +
                                    "and vala.campaign_id = 'PRESOFT'\n" +
                                    "and DATE_FORMAT(vala.event_time, '%Y-%m-%d') >= :dateFrom\n" +
                                    "and DATE_FORMAT(vala.event_time, '%Y-%m-%d') <= :dateTo\n" +
                                    "and vl.list_id not in ('1504', '1505')\n" +
                                    "order by vala.event_time desc")
                            .addScalar("call_date", new LocalDateTimeType())
                            .addScalar("email", new StringType())
                            .addScalar("talk_sec", new IntegerType())
                            .addScalar("call_status", new StringType())
                            .addScalar("call_type", new StringType())
                            .addScalar("phone_number", new StringType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();
            for (Object[] row : rows) {
                PreSoftBonusExportCall preSoftBonusExportCall = new PreSoftBonusExportCall();
                if (row[0] != null) {
                    preSoftBonusExportCall.setCallDate(LocalDateTime.parse(row[0].toString()));
                }
                if (row[1] != null) {
                    preSoftBonusExportCall.setUserEmail(row[1].toString());
                }
                if (row[2] != null) {
                    preSoftBonusExportCall.setTalkSec(Integer.parseInt(row[2].toString()));
                }
                if (row[3] != null) {
                    preSoftBonusExportCall.setCallStatus(row[3].toString());
                }
                if (row[4] != null) {
                    preSoftBonusExportCall.setCallType(row[4].toString());
                }
                if (row[5] != null) {
                    preSoftBonusExportCall.setPhoneNumber(row[5].toString());
                }
                preSoftBonusExportCallList.add(preSoftBonusExportCall);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return preSoftBonusExportCallList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PreSoftBonusExportCall> getPreSoftBonusExportCallListOnlyRemindingPromisedPayments(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.openSession();
        List<PreSoftBonusExportCall> preSoftBonusExportCallList = new ArrayList<>();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select \n" +
                                    "vala.event_time as call_date ,\n" +
                                    "vu.email ,\n" +
                                    "vala.talk_sec ,\n" +
                                    "vala.status as call_status ,\n" +
                                    "vala.comments as call_type,\n" +
                                    "vl.phone_number\n" +
                                    "from asterisk.vicidial_agent_log_archive vala\n" +
                                    "left join asterisk.vicidial_list vl \n" +
                                    "on vl.lead_id = vala.lead_id  \n" +
                                    "left join asterisk.vicidial_users vu \n" +
                                    "on vu.`user` = vala.`user` \n" +
                                    "where vala.user_group = 'collection'\n" +
                                    "and vala.lead_id is not null\n" +
                                    "and vala.campaign_id = 'PRESOFT'\n" +
                                    "and DATE_FORMAT(vala.event_time, '%Y-%m-%d') >= :dateFrom\n" +
                                    "and DATE_FORMAT(vala.event_time, '%Y-%m-%d') <= :dateTo\n" +
                                    "and vl.list_id in ('1504', '1505')\n" +
                                    "order by vala.event_time asc")
                            .addScalar("call_date", new LocalDateTimeType())
                            .addScalar("email", new StringType())
                            .addScalar("talk_sec", new IntegerType())
                            .addScalar("call_status", new StringType())
                            .addScalar("call_type", new StringType())
                            .addScalar("phone_number", new StringType())
                            .setParameter("dateFrom", dateFrom)
                            .setParameter("dateTo", dateTo)
                            .list();
            for (Object[] row : rows) {
                PreSoftBonusExportCall preSoftBonusExportCall = new PreSoftBonusExportCall();
                if (row[0] != null) {
                    preSoftBonusExportCall.setCallDate(LocalDateTime.parse(row[0].toString()));
                }
                if (row[1] != null) {
                    preSoftBonusExportCall.setUserEmail(row[1].toString());
                }
                if (row[2] != null) {
                    preSoftBonusExportCall.setTalkSec(Integer.parseInt(row[2].toString()));
                }
                if (row[3] != null) {
                    preSoftBonusExportCall.setCallStatus(row[3].toString());
                }
                if (row[4] != null) {
                    preSoftBonusExportCall.setCallType(row[4].toString());
                }
                if (row[5] != null) {
                    preSoftBonusExportCall.setPhoneNumber(row[5].toString());
                }
                preSoftBonusExportCallList.add(preSoftBonusExportCall);
            }
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return preSoftBonusExportCallList;
    }
}
