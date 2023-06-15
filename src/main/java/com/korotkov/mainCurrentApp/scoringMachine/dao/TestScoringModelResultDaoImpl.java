package com.korotkov.mainCurrentApp.scoringMachine.dao;

import com.korotkov.mainCurrentApp.scoringMachine.model.TestScoringModelResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestScoringModelResultDaoImpl implements TestScoringModelResultDao{
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(TestScoringModelResult testScoringModelResult){
        Session session = sessionFactory.getCurrentSession();
        session.persist(testScoringModelResult);
    }

    @Override
    public void update(TestScoringModelResult testScoringModelResult){
        Session session = sessionFactory.getCurrentSession();
        session.update(testScoringModelResult);
    }

    @Override
    public void delete(TestScoringModelResult testScoringModelResult){
        Session session = sessionFactory.getCurrentSession();
        session.delete(testScoringModelResult);
    }

    @Override
    public TestScoringModelResult getById(Long id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(TestScoringModelResult.class,id);
    }

    @Override
    public TestScoringModelResult getByModelAndOrderRowNumber(Long idTestModel, Integer orderRowNumber){
        Session session = sessionFactory.getCurrentSession();
        return (TestScoringModelResult) session.createQuery("select tsmr from TestScoringModelResult tsmr where tsmr.orderNumberRow = :orderRowNumber and tsmr.testScoringModel.id = :idTestModel and tsmr.total <> :booleanTotal")
                .setParameter("orderRowNumber", orderRowNumber)
                .setParameter("idTestModel", idTestModel)
                .setParameter("booleanTotal", true)
                .uniqueResult();
    }

    @Override
    public TestScoringModelResult getByTestModelIdAndUsernameTotalResult(Long testModelId){
        Session session = sessionFactory.getCurrentSession();
        return (TestScoringModelResult) session.createQuery("select tsmr from TestScoringModelResult tsmr where tsmr.testScoringModel.id = :testModelId and tsmr.total = :booleanTotalTrue")
                .setParameter("testModelId", testModelId)
                .setParameter("booleanTotalTrue", true)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TestScoringModelResult> getListTestResultsByModelIdAndUsername(Long testModelId){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tsmr from TestScoringModelResult tsmr where tsmr.testScoringModel.id = :testModelId and tsmr.total <> :booleanTotalTrue")
                .setParameter("testModelId", testModelId)
                .setParameter("booleanTotalTrue", true)
                .list();
    }
}
