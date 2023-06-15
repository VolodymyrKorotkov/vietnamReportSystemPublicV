package com.korotkov.mainCurrentApp.scoringMachine.dao;

import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringSettingsModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ScoringSettingsModelDaoImpl implements ScoringSettingsModelDao{
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(ScoringSettingsModel scoringSettingsModel){
        Session session = sessionFactory.getCurrentSession();
        session.persist(scoringSettingsModel);
    }

    @Override
    public void update(ScoringSettingsModel scoringSettingsModel){
        Session session = sessionFactory.getCurrentSession();
        session.update(scoringSettingsModel);
    }

    @Override
    public ScoringSettingsModel getById(Long id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(ScoringSettingsModel.class, id);
    }

    @Override
    public ScoringSettingsModel findByUser(){
        Session session = sessionFactory.getCurrentSession();
        return (ScoringSettingsModel) session.createQuery("select ssm from ScoringSettingsModel ssm")
                .uniqueResult();
    }

}
