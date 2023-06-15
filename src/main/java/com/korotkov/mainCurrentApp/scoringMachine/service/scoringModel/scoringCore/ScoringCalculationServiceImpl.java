package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.scoringCore;

import com.korotkov.mainCurrentApp.model.UserAccount;
import com.korotkov.mainCurrentApp.scoringMachine.dao.ScoringModelDao;
import com.korotkov.mainCurrentApp.scoringMachine.dao.ScoringModelParameterDao;
import com.korotkov.mainCurrentApp.scoringMachine.dao.ScoringSettingsModelDao;
import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModel;
import com.korotkov.mainCurrentApp.service.email.EmailCommonService;
import com.korotkov.mainCurrentApp.scoringMachine.enums.ScoringModelStatus;
import com.korotkov.mainCurrentApp.scoringMachine.enums.ScoringParameterType;
import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModelParameter;
import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringSettingsModel;
import com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.additionalService.ScoringDefaultSettingsLevelTwoMain;
import com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.additionalService.dataAnalysis.CalculateScoringParameter;
import com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.additionalService.dataAnalysis.ScoringParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.MAIN_DOMAIN_URL;
import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class ScoringCalculationServiceImpl implements ScoringCalculationService, ScoringDefaultSettingsLevelTwoMain {
    private static final Logger logger = LoggerFactory.getLogger(ScoringCalculationServiceImpl.class);
    ScoringModelDao scoringModelDao;
    ScoringModelParameterDao scoringModelParameterDao;
    ScoringSettingsModelDao scoringSettingsModelDao;
    EmailCommonService emailService;

    @Autowired
    public void setScoringModelDao(ScoringModelDao scoringModelDao){
        this.scoringModelDao = scoringModelDao;
    }

    @Autowired
    public void setScoringModelParameterDao(ScoringModelParameterDao scoringModelParameterDao){
        this.scoringModelParameterDao = scoringModelParameterDao;
    }

    @Autowired
    public void setScoringSettingsModelDao(ScoringSettingsModelDao scoringSettingsModelDao){
        this.scoringSettingsModelDao = scoringSettingsModelDao;
    }

    @Autowired
    public void setEmailService(EmailCommonService emailService) {
        this.emailService = emailService;
    }

    @Override
    @Transactional("transactionManagerMain")
    public void createScoringModel(ScoringModel scoringModel){
        scoringModelDao.create(scoringModel);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void updateScoringModel(ScoringModel scoringModel){
        scoringModelDao.update(scoringModel);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void deleteScoringModel(ScoringModel scoringModel){
        scoringModelDao.delete(scoringModel);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ScoringModel getByIdScoringModel(Long id){
        return scoringModelDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ScoringModel findEarliestModelByUser(){
        return scoringModelDao.findEarliestModelByUser();
    }

    @Override
    @Transactional("transactionManagerMain")
    public ScoringModel findActiveModelByUser(){
        return scoringModelDao.findActiveModelByUser();
    }

    @Override
    @Transactional("transactionManagerMain")
    public void createScoringModelParameter(ScoringModelParameter scoringModelParameter){
        scoringModelParameterDao.create(scoringModelParameter);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void updateScoringModelParameter(ScoringModelParameter scoringModelParameter){
        scoringModelParameterDao.update(scoringModelParameter);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void deleteScoringModelParameter(ScoringModelParameter scoringModelParameter){
        scoringModelParameterDao.delete(scoringModelParameter);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ScoringModelParameter getByIdScoringModelParameter(Long id){
        return scoringModelParameterDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScoringModelParameter> findAllParametersByModel(ScoringModel scoringModel){
        return scoringModelParameterDao.findAllByModel(scoringModel.getId());
    }

    @Override
    @Transactional("transactionManagerMain")
    public void deleteAllParametersByModel(ScoringModel scoringModel){
        scoringModelParameterDao.deleteAllByModel(scoringModel.getId());
    }


    @Override
    @Transactional("transactionManagerMain")
    @Async("scoringExecutor")
    public synchronized void createNewScoringModel(ArrayList<ArrayList<String>> listFromFileExcel, UserAccount userAccount){
        Map<String,Object> emailModel = new HashMap<>();
        emailModel.put("mainUrl",MAIN_DOMAIN_URL);
        emailModel.put("to", userAccount.getUsername());
        try {
            ScoringModel scoringModelForSave = new ScoringModel();
            scoringModelForSave.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            scoringModelForSave.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            scoringModelForSave.setTitle("New Scoring Model");
            scoringModelForSave.setStatus(String.valueOf(ScoringModelStatus.NOT_FINISHED));
            scoringModelDao.create(scoringModelForSave);
            ScoringModel scoringModelSaved = scoringModelDao.findLastCreatedModelByUser();
            ScoringSettingsModel scoringSettingsModel = scoringSettingsModelDao.findByUser();
            setToDBScoringParametersTwo(listFromFileExcel, scoringSettingsModel, scoringModelSaved);
            setToDBScoringParametersOne(listFromFileExcel, scoringSettingsModel, scoringModelSaved);
            scoringModelSaved.setStatus(String.valueOf(ScoringModelStatus.INACTIVE));
            scoringModelDao.update(scoringModelSaved);
            emailModel.put("subject", "New Scoring Model was created");
            emailModel.put("linkCreated", MAIN_DOMAIN_URL + "/creating-model/scoring-model/" + scoringModelSaved.getId() + "?action=created-new");
            emailService.sendEmail("newModelWasCreated.vm", emailModel);
        }
        catch (Exception e) {
            logger.error("Error during building scoring model (client id: " + userAccount.getId() + "): " + e.getMessage());
            emailModel.put("subject", "New Scoring Model was NOT created");
            emailModel.put("linkNotCreated", MAIN_DOMAIN_URL + "/creating-model/creating-new-model?action=fileException");
            emailService.sendEmail("newModelWasNotCreated.vm", emailModel);
        }
    }


    private void setToDBScoringParametersOne(ArrayList<ArrayList<String>> listFromFileExcel,
                                             ScoringSettingsModel scoringSettingsModel,
                                             ScoringModel scoringModel){
        ArrayList<String> arrayGoodBad = new ArrayList<>();
        for(int c = 1; c < listFromFileExcel.size(); c++){
            arrayGoodBad.add(listFromFileExcel.get(c).get(0));
        }
        for(int a = 1; a < listFromFileExcel.get(0).size(); a++){
                ArrayList<String> arrayParameter = new ArrayList<>();
                for(int b = 1; b < listFromFileExcel.size(); b++){
                    arrayParameter.add(listFromFileExcel.get(b).get(a));
                }
                ScoringParameter scoringParameter = CalculateScoringParameter.calculateNewParameter(
                        listFromFileExcel.get(0).get(a), arrayGoodBad, arrayParameter,
                        scoringSettingsModel.getFactor(), scoringSettingsModel.getOffset(),
                        scoringSettingsModel.getGoodResult(), scoringSettingsModel.getBadResult());
                if (isInfluenceOneParameter(scoringParameter, scoringSettingsModel) &&
                        !scoringModelParameterDao.isScoringParameterWithPartOfTitle(scoringModel.getId(),
                                listFromFileExcel.get(0).get(a))){
                    createAndSaveNewScoringModelParameter(true,
                            ScoringParameterType.ONE_PARAMETER, scoringParameter,scoringModel);
                } else {
                    createAndSaveNewScoringModelParameter(false,
                            ScoringParameterType.ONE_PARAMETER, scoringParameter,scoringModel);
                }
        }
    }

    private void setToDBScoringParametersTwo(ArrayList<ArrayList<String>> listFromFileExcel,
                                                    ScoringSettingsModel scoringSettingsModel,
                                                    ScoringModel scoringModel){
        ArrayList<String> arrayGoodBad = new ArrayList<>();
        for(int c = 1; c < listFromFileExcel.size(); c++){
            arrayGoodBad.add(listFromFileExcel.get(c).get(0));
        }
        for(int i = 1; i < listFromFileExcel.get(0).size(); i++){
            if(!scoringModelParameterDao.isScoringParameterWithPartOfTitle(scoringModel.getId(),
                    listFromFileExcel.get(0).get(i))){
                for(int q = 2; q < listFromFileExcel.get(0).size() - 1; q++){
                    if(!listFromFileExcel.get(0).get(i).equals(listFromFileExcel.get(0).get(q))){
                        if(!scoringModelParameterDao.isScoringParameterWithPartOfTitle(scoringModel.getId(),
                                listFromFileExcel.get(0).get(q)) &&
                                !scoringModelParameterDao.isScoringParameterWithPartOfTitle(scoringModel.getId(),
                                listFromFileExcel.get(0).get(i))){
                            ArrayList<String> arrayParameter = new ArrayList<>();
                            for(int b = 1; b < listFromFileExcel.size(); b++){
                                arrayParameter.add(listFromFileExcel.get(b).get(i) + parameterConnector +
                                        listFromFileExcel.get(b).get(q));
                            }
                            ScoringParameter scoringParameter = CalculateScoringParameter.calculateNewParameter(
                                    listFromFileExcel.get(0).get(i) + parameterConnector +
                                            listFromFileExcel.get(0).get(q),arrayGoodBad,arrayParameter,
                                    scoringSettingsModel.getFactor(), scoringSettingsModel.getOffset(),
                                    scoringSettingsModel.getGoodResult(),scoringSettingsModel.getBadResult());
                            if(isInfluenceTwoParameter(scoringParameter,scoringSettingsModel)){
                                createAndSaveNewScoringModelParameter(true,
                                        ScoringParameterType.TWO_PARAMETERS, scoringParameter,
                                        scoringModel);
                            }
                        }
                    }
                }
            }
        }
    }



    private boolean isInfluenceOneParameter(ScoringParameter scoringParameter, ScoringSettingsModel scoringSettingsModel){
        if((Double.compare(scoringParameter.getIvTotal(),
                scoringSettingsModel.getMinimumNeededIVForParameterOne()) >= 0) &&
        Double.compare((scoringParameter.getIvTotal() / scoringParameter.getMapIV().size()),
                scoringSettingsModel.getMinimumNeededAverageIVForKeyOfParameterOne()) >= 0){
            return true;
        } else {
            return false;
        }
    }

    private boolean isInfluenceTwoParameter(ScoringParameter scoringParameter, ScoringSettingsModel scoringSettingsModel){
        if((Double.compare(scoringParameter.getIvTotal(),
                scoringSettingsModel.getMinimumNeededIVForParameterTwo()) >= 0) &&
                (Double.compare((scoringParameter.getIvTotal() / scoringParameter.getMapIV().size()),
                        scoringSettingsModel.getMinimumNeededAverageIVForKeyOfParameterTwo()) >= 0) &&
                (scoringParameter.getMapIV().size() <= scoringSettingsModel.getMaxRowsForInfluenceParameterTwo())){
            return true;
        } else {
            return false;
        }
    }

    private void createAndSaveNewScoringModelParameter(boolean isRecommended,
                                                       ScoringParameterType scoringParameterType,
                                                       ScoringParameter scoringParameter,
                                                       ScoringModel scoringModel){
        ScoringModelParameter scoringModelParameterTotal = new ScoringModelParameter();
        scoringModelParameterTotal.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringModelParameterTotal.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scoringModelParameterTotal.setScoringModel(scoringModel);
        scoringModelParameterTotal.setTitle("Total");
        scoringModelParameterTotal.setTotal(true);
        scoringModelParameterTotal.setNameParameter(scoringParameter.getNameParameter());
        scoringModelParameterTotal.setGoodCount(scoringParameter.getGoodCountTotal());
        scoringModelParameterTotal.setBadCount(scoringParameter.getBadCountTotal());
        scoringModelParameterTotal.setGoodRate(new BigDecimal(
                scoringParameter.getGoodRateTotal() * 100)
                .setScale(2, RoundingMode.HALF_UP).doubleValue());
        scoringModelParameterTotal.setBadRate(new BigDecimal(
                scoringParameter.getBadRateTotal() * 100)
                .setScale(2,RoundingMode.HALF_UP).doubleValue());
        scoringModelParameterTotal.setTotalCount(scoringParameter.getTotalCountTotal());
        scoringModelParameterTotal.setIv(new BigDecimal(scoringParameter.getIvTotal())
                .setScale(8, RoundingMode.HALF_UP).doubleValue());
        scoringModelParameterTotal.setTypeParameter(String.valueOf(scoringParameterType));
        scoringModelParameterTotal.setRecommended(isRecommended);
        scoringModelParameterDao.create(scoringModelParameterTotal);
        for(String key : scoringParameter.getListTitleOfParameter()){
            ScoringModelParameter scoringModelParameter = new ScoringModelParameter();
            scoringModelParameter.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            scoringModelParameter.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            scoringModelParameter.setScoringModel(scoringModel);
            scoringModelParameter.setTitle(key);
            scoringModelParameter.setTotal(false);
            scoringModelParameter.setNameParameter(scoringParameter.getNameParameter());
            scoringModelParameter.setGoodCount(scoringParameter.getMapGoodCount().get(key));
            scoringModelParameter.setBadCount(scoringParameter.getMapBadCount().get(key));
            scoringModelParameter.setGoodRate(new BigDecimal(
                    scoringParameter.getMapGoodRate().get(key) * 100)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());
            scoringModelParameter.setBadRate(new BigDecimal(
                    scoringParameter.getMapBadRate().get(key) * 100)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());
            scoringModelParameter.setTotalCount(scoringParameter.getMapTotalCount().get(key));
            scoringModelParameter.setGoodPopulationPercent(new BigDecimal(
                    scoringParameter.getMapPopulationGoodPercent().get(key) * 100)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());
            scoringModelParameter.setBadPopulationPercent(new BigDecimal(
                    scoringParameter.getMapPopulationBadPercent().get(key) * 100)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());
            scoringModelParameter.setTotalPopulationPercent(new BigDecimal(
                    scoringParameter.getMapPopulationTotalPercent().get(key) * 100)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue());
            scoringModelParameter.setGiG(new BigDecimal(
                    scoringParameter.getMapPopulationGoodPercent().get(key))
                    .setScale(8, RoundingMode.HALF_UP).doubleValue());
            scoringModelParameter.setBiB(new BigDecimal(
                    scoringParameter.getMapPopulationBadPercent().get(key))
                    .setScale(8, RoundingMode.HALF_UP).doubleValue());
            scoringModelParameter.setPgPb(new BigDecimal(
                    scoringParameter.getMapPgPb().get(key))
                    .setScale(8, RoundingMode.HALF_UP).doubleValue());
            scoringModelParameter.setWoe(new BigDecimal(
                    scoringParameter.getMapWOE().get(key))
                    .setScale(8, RoundingMode.HALF_UP).doubleValue());
            scoringModelParameter.setIv(new BigDecimal(
                    scoringParameter.getMapIV().get(key))
                    .setScale(8, RoundingMode.HALF_UP).doubleValue());
            scoringModelParameter.setScore(scoringParameter.getMapScore().get(key));
            scoringModelParameter.setTypeParameter(String.valueOf(scoringParameterType));
            scoringModelParameter.setRecommended(isRecommended);
            scoringModelParameterDao.create(scoringModelParameter);
        }
    }

    private void updateAllActiveModelsToInactive(){
        List<ScoringModel> scoringModelList =
                scoringModelDao.findModelsWithStatus(String.valueOf(ScoringModelStatus.ACTIVE));
        for (ScoringModel sm : scoringModelList) {
            sm.setStatus(String.valueOf(ScoringModelStatus.INACTIVE));
            sm.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            scoringModelDao.update(sm);
        }
    }
}
