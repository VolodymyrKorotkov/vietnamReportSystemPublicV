package com.korotkov.mainCurrentApp.scoringMachine.dao;

import com.korotkov.mainCurrentApp.scoringMachine.enums.ScoringModelStatus;
import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModel;
import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModelParameter;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScoringModelDaoImpl implements ScoringModelDao{
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    private void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(ScoringModel scoringModel){
        Session session = sessionFactory.getCurrentSession();
        session.persist(scoringModel);
    }

    @Override
    public void update(ScoringModel scoringModel){
        Session session = sessionFactory.getCurrentSession();
        session.update(scoringModel);
    }

    @Override
    public void delete(ScoringModel scoringModel){
        Session session = sessionFactory.getCurrentSession();
        session.delete(scoringModel);
    }

    @Override
    public ScoringModel getById(Long id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(ScoringModel.class,id);
    }

    @Override
    public ScoringModel findEarliestModelByUser(){
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictAllRegions();
        }
        return (ScoringModel) session.createQuery("select sm from ScoringModel sm order by sm.createdAt asc")
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    public ScoringModel findLastCreatedModelByUser(){
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictAllRegions();
        }
        return (ScoringModel) session.createQuery("select sm from ScoringModel sm order by sm.createdAt desc")
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    public ScoringModel findActiveModelByUser(){
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        Cache cache = sessionFactory.getCache();
        if (cache != null) {
            cache.evictAllRegions();
        }
        return (ScoringModel) session.createQuery("select sm from ScoringModel sm where sm.status = :activeStatus")
                .setParameter("activeStatus", String.valueOf(ScoringModelStatus.ACTIVE))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModel> findModelsWithStatus(String status){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sm from ScoringModel sm where sm.status = :status")
                .setParameter("status", status)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModel> getAllModels(int page){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sm from ScoringModel sm where sm.status <> :notFinishedStatus order by sm.lastModifiedAt desc")
                .setParameter("notFinishedStatus", String.valueOf(ScoringModelStatus.NOT_FINISHED))
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModel> getModelsWithFilter(int page, String scoringModelTitle,
                                                  String scoringModelStatus){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sm from ScoringModel sm where sm.status = :scoringModelStatus and sm.title like :modelTitle order by sm.lastModifiedAt desc")
                .setParameter("scoringModelStatus", scoringModelStatus)
                .setParameter("modelTitle","%" + scoringModelTitle + "%")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModel> getModelsWithFilter(int page, String scoringModelTitle){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sm from ScoringModel sm where sm.status <> :notFinishedStatus and sm.title like :modelTitle order by sm.lastModifiedAt desc")
                .setParameter("notFinishedStatus", String.valueOf(ScoringModelStatus.NOT_FINISHED))
                .setParameter("modelTitle","%" + scoringModelTitle + "%")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModel> getModelsWithFilter(String scoringModelStatus, int page){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select sm from ScoringModel sm where sm.status = :scoringModelStatus order by sm.lastModifiedAt desc")
                .setParameter("scoringModelStatus", scoringModelStatus)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountModelsWithFilter(String scoringModelTitle, String scoringModelStatus){
        Session session = sessionFactory.getCurrentSession();
        if((!scoringModelTitle.equals("") && !scoringModelTitle.equals("NaN")) && !scoringModelStatus.equals("NaN")){
            return (Long) session.createQuery("select count(sm.id) from ScoringModel sm where sm.status = :statusModel and sm.title like :titleModel")
                    .setParameter("statusModel", scoringModelStatus)
                    .setParameter("titleModel","%" + scoringModelTitle + "%")
                    .uniqueResult();
        } else if(!scoringModelTitle.equals("") && !scoringModelTitle.equals("NaN")){
            return (Long) session.createQuery("select count(sm.id) from ScoringModel sm where sm.status <> :notFinishedStatus and sm.title like :titleModel")
                    .setParameter("notFinishedStatus", String.valueOf(ScoringModelStatus.NOT_FINISHED))
                    .setParameter("titleModel","%" + scoringModelTitle + "%")
                    .uniqueResult();
        } else if(!scoringModelStatus.equals("NaN")){
            return (Long) session.createQuery("select count(sm.id) from ScoringModel sm where sm.status = :statusModel")
                    .setParameter("statusModel", scoringModelStatus)
                    .uniqueResult();
        } else {
            return (Long) session.createQuery("select count(sm.id) from ScoringModel sm where sm.status <> :notFinishedStatus")
                    .setParameter("notFinishedStatus", String.valueOf(ScoringModelStatus.NOT_FINISHED))
                    .uniqueResult();
        }
    }

    @Override
    public ScoringModel getScoringModelByIdAndUsername(Long modelId){
        Session session = sessionFactory.getCurrentSession();
        return (ScoringModel) session.createQuery("select sm from ScoringModel sm where sm.id = :modelId")
                .setParameter("modelId", modelId)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModelParameter> getScoringModelRecommendedParametersList(Long modelId,
                                                                                String scoringParameterType){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select smp from ScoringModelParameter smp where smp.scoringModel.id = :modelId and smp.recommended = true and smp.typeParameter = :scoringParameterType")
                .setParameter("modelId", modelId)
                .setParameter("scoringParameterType", scoringParameterType)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScoringModelParameter> getScoringModelAllParametersTotalList(Long modelId,
                                                                        String scoringParameterType){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select smp from ScoringModelParameter smp where smp.scoringModel.id = :modelId and smp.typeParameter = :scoringParameterType and smp.total = :booleanTotal")
                .setParameter("modelId", modelId)
                .setParameter("scoringParameterType", scoringParameterType)
                .setParameter("booleanTotal", true)
                .list();
    }


    @Override
    public boolean isHavingActiveModelUser(){
        Session session = sessionFactory.getCurrentSession();
        return !session.createQuery("select sm from ScoringModel sm where sm.status = 'ACTIVE'")
                .list().isEmpty();
    }

}
