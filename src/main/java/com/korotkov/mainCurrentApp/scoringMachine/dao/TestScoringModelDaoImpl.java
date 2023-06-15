package com.korotkov.mainCurrentApp.scoringMachine.dao;

import com.korotkov.mainCurrentApp.scoringMachine.model.TestScoringModel;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestScoringModelDaoImpl implements TestScoringModelDao{
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(TestScoringModel testScoringModel){
        Session session = sessionFactory.getCurrentSession();
        session.persist(testScoringModel);
    }

    @Override
    public void update(TestScoringModel testScoringModel){
        Session session = sessionFactory.getCurrentSession();
        session.update(testScoringModel);
    }

    @Override
    public void delete(TestScoringModel testScoringModel){
        Session session = sessionFactory.getCurrentSession();
        session.delete(testScoringModel);
    }

    @Override
    public TestScoringModel getById(Long id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(TestScoringModel.class,id);
    }

    @Override
    public TestScoringModel findEarliestTestByModel(Long scoringModelId){
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictAllRegions();
        }
        return (TestScoringModel) session.createQuery("select tsm from TestScoringModel tsm where tsm.scoringModel.id = :scoringModelId order by tsm.createdAt asc")
                .setMaxResults(1)
                .setParameter("scoringModelId", scoringModelId)
                .uniqueResult();
    }

    @Override
    public TestScoringModel findLastTestByModel(Long scoringModelId){
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictAllRegions();
        }
        return (TestScoringModel) session.createQuery("select tsm from TestScoringModel tsm where tsm.scoringModel.id = :scoringModelId order by tsm.createdAt desc")
                .setMaxResults(1)
                .setParameter("scoringModelId", scoringModelId)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TestScoringModel> getAllTestingModels(int page){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tsm from TestScoringModel tsm order by tsm.lastModifiedAt desc")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TestScoringModel> getTestingModelsWithFilterTitleTestingModel(int page, String titleTestingModel){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tsm from TestScoringModel tsm where tsm.title like :titleTestingModel order by tsm.lastModifiedAt desc")
                .setParameter("titleTestingModel", "%" + titleTestingModel + "%")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TestScoringModel> getTestingModelsWithFilterTitleScoringModel(int page, String titleScoringModel){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tsm from TestScoringModel tsm where tsm.scoringModel.title like :titleScoringModel order by tsm.lastModifiedAt desc")
                .setParameter("titleScoringModel", "%" + titleScoringModel + "%")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TestScoringModel> getTestingModelWithFilterTitlesScoringAndTestingModel(int page,
                                                                                        String titleTestingModel,
                                                                                        String titleScoringModel){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tsm from TestScoringModel tsm where tsm.title like :titleTestingModel and tsm.scoringModel.title like :titleScoringModel order by tsm.lastModifiedAt desc")
                .setParameter("titleTestingModel", "%" + titleTestingModel + "%")
                .setParameter("titleScoringModel", "%" + titleScoringModel + "%")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountTestingModelsWithFilter(String titleTestingModel, String titleScoringModel){
        Session session = sessionFactory.getCurrentSession();
        if(!titleTestingModel.equals("") && !titleScoringModel.equals("")){
            return (Long) session.createQuery("select count(tsm.id) from TestScoringModel tsm where tsm.title like :titleTestingModel and tsm.scoringModel.title like :titleScoringModel")
                    .setParameter("titleTestingModel", "%" + titleTestingModel + "%")
                    .setParameter("titleScoringModel", "%" + titleScoringModel + "%")
                    .uniqueResult();
        } else if (!titleTestingModel.equals("")){
            return (Long) session.createQuery("select count(tsm.id) from TestScoringModel tsm where tsm.title like :titleTestingModel")
                    .setParameter("titleTestingModel", "%" + titleTestingModel + "%")
                    .uniqueResult();
        } else if (!titleScoringModel.equals("")){
            return (Long) session.createQuery("select count(tsm.id) from TestScoringModel tsm where tsm.scoringModel.title like :titleScoringModel")
                    .setParameter("titleScoringModel", "%" + titleScoringModel + "%")
                    .uniqueResult();
        } else {
            return (Long) session.createQuery("select count(tsm.id) from TestScoringModel tsm")
                    .uniqueResult();
        }
    }

    @Override
    public TestScoringModel getByUsernameAndId(Long testScoringModelId){
        Session session = sessionFactory.getCurrentSession();
        return (TestScoringModel) session.createQuery("select tsm from TestScoringModel tsm where tsm.id = :testScoringModelId")
                .setParameter("testScoringModelId", testScoringModelId)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TestScoringModel> getAllTestsForScoringModel(Long scoringModelId){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select tsm from TestScoringModel tsm where tsm.scoringModel.id = :scoringModelId")
                .setParameter("scoringModelId", scoringModelId)
                .list();
    }
}
