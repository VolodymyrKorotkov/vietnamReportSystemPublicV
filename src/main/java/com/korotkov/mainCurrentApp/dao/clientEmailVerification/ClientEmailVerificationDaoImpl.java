package com.korotkov.mainCurrentApp.dao.clientEmailVerification;

import com.korotkov.mainCurrentApp.model.ClientEmailVerification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ClientEmailVerificationDaoImpl implements ClientEmailVerificationDao {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(ClientEmailVerification clientEmailVerification) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(clientEmailVerification);
    }

    @Override
    public void update(ClientEmailVerification clientEmailVerification) {
        Session session = sessionFactory.getCurrentSession();
        session.update(clientEmailVerification);
    }

    @Override
    public ClientEmailVerification getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(ClientEmailVerification.class,id);
    }

    @Override
    public ClientEmailVerification getByClientId(Long clientId) {
        Session session = sessionFactory.getCurrentSession();
        return (ClientEmailVerification) session.createQuery("select cev from ClientEmailVerification cev where cev.clientId = :clientId and done = false")
                .setParameter("clientId", clientId)
                .uniqueResult();
    }

    @Override
    public void delete(ClientEmailVerification clientEmailVerification) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(clientEmailVerification);
    }

    @Override
    public Long getCountSuccessfulConfirmedEmails(Long clientId) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(cev.id) from ClientEmailVerification cev where cev.clientId = :clientId and done = true")
                .setParameter("clientId", clientId)
                .uniqueResult();
    }
}
