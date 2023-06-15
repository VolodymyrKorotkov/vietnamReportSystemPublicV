package com.korotkov.mainCurrentApp.dao.manualUkraineEmails;

import com.korotkov.mainCurrentApp.model.ManualUkraineEmails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ManualUkraineEmailsDaoImpl implements ManualUkraineEmailsDao {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(ManualUkraineEmails manualUkraineEmails) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(manualUkraineEmails);
    }
}
