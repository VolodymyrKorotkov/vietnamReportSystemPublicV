package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.scoringSettings;

import com.korotkov.mainCurrentApp.scoringMachine.dao.ScoringSettingsModelDao;
import com.korotkov.mainCurrentApp.scoringMachine.enums.ScoringSettingsQualityLevel;
import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringSettingsModel;
import com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.additionalService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class ScoringSettingsModelServiceImpl implements ScoringSettingsModelService,
        ScoringDefaultSettingsLevelTwoMain, ScoringSettingsLevelOne, ScoringSettingsLevelThree,
        ScoringSettingsLevelFour, ScoringSettingsLevelFive {
    private ScoringSettingsModelDao scoringSettingsModelDao;

    @Autowired
    public void setScoringSettingsModelDao(ScoringSettingsModelDao scoringSettingsModelDao){
        this.scoringSettingsModelDao = scoringSettingsModelDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public void create(ScoringSettingsModel scoringSettingsModel){
        scoringSettingsModelDao.create(scoringSettingsModel);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void update(ScoringSettingsModel scoringSettingsModel){
        scoringSettingsModelDao.update(scoringSettingsModel);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ScoringSettingsModel getById(Long id){
        return scoringSettingsModelDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ScoringSettingsModel findByUser(){
        return scoringSettingsModelDao.findByUser();
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean updateScoringSettings(ScoringSettingsModel scoringSettingsModel){
        ScoringSettingsModel scoringSettingsModelFromDB = scoringSettingsModelDao.findByUser();
        scoringSettingsModelFromDB.setGoodResult(scoringSettingsModel.getGoodResult());
        scoringSettingsModelFromDB.setBadResult(scoringSettingsModel.getBadResult());
        scoringSettingsModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        if(scoringSettingsModel.getModelQualityLevel().equals(String.valueOf(ScoringSettingsQualityLevel.LEVEL_1))){
            scoringSettingsModelFromDB.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_1));
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOneLevelOne);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOneLevelOne);
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwoLevelOne);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwoLevelOne);
            scoringSettingsModelFromDB.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwoLevelOne);
            scoringSettingsModelDao.update(scoringSettingsModelFromDB);
            return true;
        }
        if(scoringSettingsModel.getModelQualityLevel().equals(String.valueOf(ScoringSettingsQualityLevel.LEVEL_2))){
            scoringSettingsModelFromDB.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_2));
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOne);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOne);
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwo);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwo);
            scoringSettingsModelFromDB.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwo);
            scoringSettingsModelDao.update(scoringSettingsModelFromDB);
            return true;
        }
        if(scoringSettingsModel.getModelQualityLevel().equals(String.valueOf(ScoringSettingsQualityLevel.LEVEL_3))){
            scoringSettingsModelFromDB.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_3));
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOneLevelThree);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOneLevelThree);
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwoLevelThree);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwoLevelThree);
            scoringSettingsModelFromDB.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwoLevelThree);
            scoringSettingsModelDao.update(scoringSettingsModelFromDB);
            return true;
        }
        if(scoringSettingsModel.getModelQualityLevel().equals(String.valueOf(ScoringSettingsQualityLevel.LEVEL_4))){
            scoringSettingsModelFromDB.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_4));
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOneLevelFour);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOneLevelFour);
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwoLevelFour);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwoLevelFour);
            scoringSettingsModelFromDB.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwoLevelFour);
            scoringSettingsModelDao.update(scoringSettingsModelFromDB);
            return true;
        }
        if(scoringSettingsModel.getModelQualityLevel().equals(String.valueOf(ScoringSettingsQualityLevel.LEVEL_5))){
            scoringSettingsModelFromDB.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_5));
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOneLevelFive);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOneLevelFive);
            scoringSettingsModelFromDB.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwoLevelFive);
            scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwoLevelFive);
            scoringSettingsModelFromDB.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwoLevelFive);
            scoringSettingsModelDao.update(scoringSettingsModelFromDB);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    @Transactional("transactionManagerMain")
    public void updateScoringAdvanceSettings(ScoringSettingsModel scoringSettingsModel){
        ScoringSettingsModel scoringSettingsModelFromDB = scoringSettingsModelDao.findByUser();
        scoringSettingsModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringSettingsModelFromDB.setGoodResult(scoringSettingsModel.getGoodResult());
        scoringSettingsModelFromDB.setBadResult(scoringSettingsModel.getBadResult());
        scoringSettingsModelFromDB
                .setMinimumNeededIVForParameterOne(scoringSettingsModel.getMinimumNeededIVForParameterOne());
        scoringSettingsModelFromDB
                .setMinimumNeededAverageIVForKeyOfParameterOne(scoringSettingsModel
                        .getMinimumNeededAverageIVForKeyOfParameterOne());
        scoringSettingsModelFromDB
                .setMinimumNeededIVForParameterTwo(scoringSettingsModel
                        .getMinimumNeededIVForParameterTwo());
        scoringSettingsModelFromDB
                .setMinimumNeededAverageIVForKeyOfParameterTwo(scoringSettingsModel
                        .getMinimumNeededAverageIVForKeyOfParameterTwo());
        scoringSettingsModelFromDB
                .setMaxRowsForInfluenceParameterTwo(scoringSettingsModel.getMaxRowsForInfluenceParameterTwo());
        scoringSettingsModelFromDB
                .setFactor(scoringSettingsModel.getFactor());
        scoringSettingsModelFromDB.setOffset(scoringSettingsModel.getOffset());
        scoringSettingsModelFromDB
                .setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_CUSTOM));
        scoringSettingsModelDao.update(scoringSettingsModelFromDB);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void updateTestingModelSettings(ScoringSettingsModel scoringSettingsModel){
        ScoringSettingsModel scoringSettingsModelFromDB = scoringSettingsModelDao.findByUser();
        scoringSettingsModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringSettingsModelFromDB.setGoodResult(scoringSettingsModel.getGoodResult());
        scoringSettingsModelFromDB.setBadResult(scoringSettingsModel.getBadResult());
        scoringSettingsModelFromDB.setNumberWishedRowsForCalcTestModel(scoringSettingsModel
                .getNumberWishedRowsForCalcTestModel());
        scoringSettingsModelDao.update(scoringSettingsModelFromDB);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void restoreDefaultScoringSettings(){
        ScoringSettingsModel scoringSettingsModelFromDB = scoringSettingsModelDao.findByUser();
        scoringSettingsModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringSettingsModelFromDB.setGoodResult(goodResult);
        scoringSettingsModelFromDB.setBadResult(badResult);
        scoringSettingsModelFromDB.setMinimumNeededIVForParameterOne(minimumNeededIVForParameterOne);
        scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterOne(minimumNeededAverageIVForKeyOfParameterOne);
        scoringSettingsModelFromDB.setMinimumNeededIVForParameterTwo(minimumNeededIVForParameterTwo);
        scoringSettingsModelFromDB.setMinimumNeededAverageIVForKeyOfParameterTwo(minimumNeededAverageIVForKeyOfParameterTwo);
        scoringSettingsModelFromDB.setMaxRowsForInfluenceParameterTwo(maxRowsForInfluenceParameterTwo);
        scoringSettingsModelFromDB.setFactor(factor);
        scoringSettingsModelFromDB.setOffset(offset);
        scoringSettingsModelFromDB.setModelQualityLevel(String.valueOf(ScoringSettingsQualityLevel.LEVEL_2));
        scoringSettingsModelDao.update(scoringSettingsModelFromDB);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void restoreDefaultTestingModelSettings(){
        ScoringSettingsModel scoringSettingsModelFromDB = scoringSettingsModelDao.findByUser();
        scoringSettingsModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringSettingsModelFromDB.setGoodResult(goodResult);
        scoringSettingsModelFromDB.setBadResult(badResult);
        scoringSettingsModelFromDB.setNumberWishedRowsForCalcTestModel(numberWishedRowsForCalcTestModel);
        scoringSettingsModelDao.update(scoringSettingsModelFromDB);
    }

}
