package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.scoringModelService;

import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModel;
import com.korotkov.mainCurrentApp.scoringMachine.dao.ScoringModelDao;
import com.korotkov.mainCurrentApp.scoringMachine.enums.ScoringModelStatus;
import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModelParameter;
import com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.additionalService.additionalEntityForPortal.ScoringParameterPortalCommon;
import com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.additionalService.additionalEntityForPortal.ScoringParameterPortalRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class ScoringModelServiceImpl implements ScoringModelService {
    ScoringModelDao scoringModelDao;

    @Autowired
    public void setScoringModelDao(ScoringModelDao scoringModelDao){
        this.scoringModelDao = scoringModelDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScoringModel> getAllModels(int page){
        return scoringModelDao.getAllModels(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScoringModel> getModelsWithFilter(int page, String scoringModelTitle,
                                                 String scoringModelStatus){
        return scoringModelDao.getModelsWithFilter(page, scoringModelTitle,
                scoringModelStatus);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScoringModel> getModelsWithFilter(int page, String scoringModelTitle){
        return scoringModelDao.getModelsWithFilter(page, scoringModelTitle);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScoringModel> getModelsWithFilter(String scoringModelStatus, int page){
        return scoringModelDao.getModelsWithFilter(scoringModelStatus, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountModelsWithFilter(String scoringModelTitle, String scoringModelStatus){
        return scoringModelDao.getCountModelsWithFilter(scoringModelTitle, scoringModelStatus);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ScoringModel getScoringModelByIdAndUser(Long modelId){
        return scoringModelDao.getScoringModelByIdAndUsername(modelId);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScoringParameterPortalCommon> getScoringModelRecommendedParametersList(Long modelId,
                                                                                String scoringParameterType){
        List<ScoringModelParameter> scoringModelParameterList =
                scoringModelDao.getScoringModelRecommendedParametersList(modelId,
                scoringParameterType);
        Set<String> scoringModelParameterNameList = new HashSet<>();
        for(int a = 0; a < scoringModelParameterList.size(); a++){
            scoringModelParameterNameList.add(scoringModelParameterList.get(a).getNameParameter());
        }
        List<ScoringParameterPortalCommon> scoringParameterPortalCommonList = new ArrayList<>();
        for (String key: scoringModelParameterNameList) {
            ScoringParameterPortalCommon scoringParameterPortalCommon = new ScoringParameterPortalCommon(key);
            List<ScoringParameterPortalRow> scoringParameterPortalRowList = new ArrayList<>();
            for(int a = 0; a < scoringModelParameterList.size(); a++){
                if(key.equals(scoringModelParameterList.get(a).getNameParameter())){
                    if(scoringModelParameterList.get(a).isTotal()){
                        scoringParameterPortalCommon.setGoodCountTotal(scoringModelParameterList.get(a).getGoodCount());
                        scoringParameterPortalCommon.setBadCountTotal(scoringModelParameterList.get(a).getBadCount());
                        scoringParameterPortalCommon.setTotalCountTotal(scoringModelParameterList.get(a).getTotalCount());
                        scoringParameterPortalCommon.setIvTotal(scoringModelParameterList.get(a).getIv());
                        scoringParameterPortalCommon.setGoodRateTotal(scoringModelParameterList.get(a).getGoodRate());
                        scoringParameterPortalCommon.setBadRateTotal(scoringModelParameterList.get(a).getBadRate());
                    } else {
                        scoringParameterPortalRowList.add(new ScoringParameterPortalRow(
                                scoringModelParameterList.get(a).getTitle(),
                                scoringModelParameterList.get(a).getGoodCount(),
                                scoringModelParameterList.get(a).getBadCount(),
                                scoringModelParameterList.get(a).getGoodRate(),
                                scoringModelParameterList.get(a).getBadRate(),
                                scoringModelParameterList.get(a).getTotalCount(),
                                scoringModelParameterList.get(a).getGoodPopulationPercent(),
                                scoringModelParameterList.get(a).getBadPopulationPercent(),
                                scoringModelParameterList.get(a).getTotalPopulationPercent(),
                                scoringModelParameterList.get(a).getGiG(),
                                scoringModelParameterList.get(a).getBiB(),
                                scoringModelParameterList.get(a).getPgPb(),
                                scoringModelParameterList.get(a).getWoe(),
                                scoringModelParameterList.get(a).getIv(),
                                scoringModelParameterList.get(a).getScore(),
                                scoringModelParameterList.get(a).getId()));
                    }
                }
            }
            scoringParameterPortalCommon.setScoringParameterPortalRows(scoringParameterPortalRowList);
            scoringParameterPortalCommonList.add(scoringParameterPortalCommon);
        }
        return scoringParameterPortalCommonList;
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScoringModelParameter> getScoringModelAllParametersTotalList(Long modelId,
                                                                        String scoringParameterType){
        return scoringModelDao.getScoringModelAllParametersTotalList(modelId, scoringParameterType);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void updateTitleAndDescriptionInScoringModel(Long modelId, ScoringModel scoringModel){
        ScoringModel scoringModelFromDB = scoringModelDao.getScoringModelByIdAndUsername(
                modelId);
        if(!scoringModelFromDB.getTitle().equals(scoringModel.getTitle()) ||
                !scoringModelFromDB.getDescription().equals(scoringModel.getDescription())){
            scoringModelFromDB.setTitle(scoringModel.getTitle());
            scoringModelFromDB.setDescription(scoringModel.getDescription());
            scoringModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            scoringModelDao.update(scoringModelFromDB);
        }
    }

    @Override
    @Transactional("transactionManagerMain")
    public void deleteScoringModel(Long modelId){
        scoringModelDao.delete(scoringModelDao.getScoringModelByIdAndUsername(
                modelId));
    }

    @Override
    @Transactional("transactionManagerMain")
    public void activateScoringModel(Long modelId){
        ScoringModel scoringModelFromDB = scoringModelDao.getScoringModelByIdAndUsername(
                modelId);
        if(!scoringModelFromDB.getStatus().equals(String.valueOf(ScoringModelStatus.ACTIVE))){
            List<ScoringModel> scoringModelList =
                    scoringModelDao.findModelsWithStatus(String.valueOf(ScoringModelStatus.ACTIVE));
            for (ScoringModel sm : scoringModelList) {
                sm.setStatus(String.valueOf(ScoringModelStatus.INACTIVE));
                sm.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                scoringModelDao.update(sm);
            }
            scoringModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            scoringModelFromDB.setStatus(String.valueOf(ScoringModelStatus.ACTIVE));
            scoringModelDao.update(scoringModelFromDB);
        }
    }

    @Override
    @Transactional("transactionManagerMain")
    public void deactivateScoringModel(Long modelId){
        ScoringModel scoringModelFromDB = scoringModelDao.getScoringModelByIdAndUsername(
                modelId);
        if(!scoringModelFromDB.getStatus().equals(String.valueOf(ScoringModelStatus.INACTIVE))){
            scoringModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            scoringModelFromDB.setStatus(String.valueOf(ScoringModelStatus.INACTIVE));
            scoringModelDao.update(scoringModelFromDB);
        }
    }

    @Override
    @Transactional("transactionManagerMain")
    public ScoringModel findActiveModelByUser(){
        return scoringModelDao.findActiveModelByUser();
    }
}
