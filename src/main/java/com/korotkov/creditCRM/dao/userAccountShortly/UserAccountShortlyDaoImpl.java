package com.korotkov.creditCRM.dao.userAccountShortly;

import com.korotkov.creditCRM.dao.collection.commonDataWithAssignedCollectorReport.CommonDataWithAssignedCollectorReportDaoImpl;
import com.korotkov.creditCRM.model.BackUserAccountShortly;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserAccountShortlyDaoImpl implements UserAccountShortlyDao {
    private static final Logger logger = LoggerFactory.getLogger(UserAccountShortlyDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryCRM")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public BackUserAccountShortly getBackUserAccountShortlyByEmail(String email) {
        Session session = sessionFactory.openSession();
        BackUserAccountShortly backUserAccountShortly = new BackUserAccountShortly();
        try {
            List<Object[]> rows =
                    session.createSQLQuery("select\n" +
                                    "bua.email as user_email,\n" +
                                    "concat(bua.last_name, ' ', bua.first_name) as user_name,\n" +
                                    "bur.\"name\" as role_name,\n" +
                                    "bua.id as user_id \n" +
                                    "from back_user_account bua \n" +
                                    "left join back_user_role bur \n" +
                                    "on bur.id = bua.role_id \n" +
                                    "where bua.email = :email")
                            .addScalar("user_email", new StringType())
                            .addScalar("user_name", new StringType())
                            .addScalar("role_name", new StringType())
                            .addScalar("user_id", new LongType())
                            .setParameter("email", email)
                            .list();
            backUserAccountShortly.setUserEmail(rows.get(0)[0].toString());
            backUserAccountShortly.setUserName(rows.get(0)[1].toString());
            backUserAccountShortly.setRoleName(rows.get(0)[2].toString());
            backUserAccountShortly.setUserId(Long.parseLong(rows.get(0)[3].toString()));

        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        } finally {
            session.close();
        }
        return backUserAccountShortly;
    }
}
