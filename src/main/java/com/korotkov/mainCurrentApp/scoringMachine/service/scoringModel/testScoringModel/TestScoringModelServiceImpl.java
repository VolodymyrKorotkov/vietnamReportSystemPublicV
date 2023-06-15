package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.testScoringModel;

import com.korotkov.mainCurrentApp.scoringMachine.dao.ScoringModelDao;
import com.korotkov.mainCurrentApp.scoringMachine.dao.TestScoringModelDao;
import com.korotkov.mainCurrentApp.scoringMachine.dao.TestScoringModelResultDao;
import com.korotkov.mainCurrentApp.scoringMachine.model.TestScoringModel;
import com.korotkov.mainCurrentApp.scoringMachine.model.TestScoringModelResult;
import com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.additionalService.additionalEntityForPortal.TestPortal;
import com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.additionalService.additionalEntityForPortal.TestResultsPortal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class TestScoringModelServiceImpl implements TestScoringModelService {
    ScoringModelDao scoringModelDao;
    TestScoringModelDao testScoringModelDao;
    TestScoringModelResultDao testScoringModelResultDao;

    @Autowired
    public void setScoringModelDao(ScoringModelDao scoringModelDao){
        this.scoringModelDao = scoringModelDao;
    }

    @Autowired
    public void setTestScoringModelDao(TestScoringModelDao testScoringModelDao){
        this.testScoringModelDao = testScoringModelDao;
    }

    @Autowired
    public void setTestScoringModelResultDao(TestScoringModelResultDao testScoringModelResultDao){
        this.testScoringModelResultDao = testScoringModelResultDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public TestScoringModel getById(Long id){
        return testScoringModelDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean isPossibleBuildNewTest(){
        return scoringModelDao.isHavingActiveModelUser();
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<TestScoringModel> getAllTestingModels(int page){
        return testScoringModelDao.getAllTestingModels(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<TestScoringModel> getTestingModelsWithFilterTitleTestingModel(int page,
                                                                              String titleTestingModel){
        return testScoringModelDao.getTestingModelsWithFilterTitleTestingModel(page,
                titleTestingModel);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<TestScoringModel> getTestingModelsWithFilterTitleScoringModel(int page,
                                                                              String titleScoringModel){
        return testScoringModelDao.getTestingModelsWithFilterTitleScoringModel(page,
                titleScoringModel);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<TestScoringModel> getTestingModelWithFilterTitlesScoringAndTestingModel(int page,
                                                                                        String titleTestingModel,
                                                                                        String titleScoringModel){
        return testScoringModelDao.getTestingModelWithFilterTitlesScoringAndTestingModel(page,
                titleTestingModel, titleScoringModel);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountTestingModelsWithFilter(String titleTestingModel, String titleScoringModel){
        return testScoringModelDao.getCountTestingModelsWithFilter(titleTestingModel,
                titleScoringModel);
    }

    @Override
    @Transactional("transactionManagerMain")
    public TestScoringModel getByUserAndTestId(Long testScoringModelId){
        return testScoringModelDao.getByUsernameAndId(testScoringModelId);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void updateTitleAndDescriptionForTestScoringModel(Long testScoringModelId,
                                                             TestScoringModel testScoringModel){
        TestScoringModel testScoringModelFromDB = testScoringModelDao.getByUsernameAndId(testScoringModelId);
        if(!testScoringModelFromDB.getTitle().equals(testScoringModel.getTitle()) ||
                !testScoringModelFromDB.getDescription().equals(testScoringModel.getDescription())){
            testScoringModelFromDB.setTitle(testScoringModel.getTitle());
            testScoringModelFromDB.setDescription(testScoringModel.getDescription());
            testScoringModelFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            testScoringModelDao.update(testScoringModelFromDB);
        }
    }

    @Override
    @Transactional("transactionManagerMain")
    public void deleteTestScoringModel(Long testScoringModelId){
        testScoringModelDao.delete(testScoringModelDao.getByUsernameAndId(testScoringModelId));
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<TestPortal> getAllModelsForScoringModelPortal(Long scoringModelId){
        List<TestScoringModel> testScoringModelList = testScoringModelDao.getAllTestsForScoringModel(scoringModelId);
        if(testScoringModelList.isEmpty()){
            return new ArrayList<>();
        } else {
            List<TestPortal> testPortalList = new ArrayList<>();
            for (int a = 0; a < testScoringModelList.size(); a++){
                List<TestResultsPortal> testResultsPortalList = new ArrayList<>();
                List<TestScoringModelResult> testScoringModelResultList = testScoringModelResultDao
                        .getListTestResultsByModelIdAndUsername(testScoringModelList.get(a).getId());
                for(int b = 0; b < testScoringModelResultList.size(); b++){
                   testResultsPortalList.add(new TestResultsPortal(testScoringModelResultList.get(b).getScore(),
                           testScoringModelResultList.get(b).getCountTotalItems(),
                           testScoringModelResultList.get(b).getCountGoodItems(),
                           testScoringModelResultList.get(b).getCountBadItems(),
                           testScoringModelResultList.get(b).getBadRate(),
                           testScoringModelResultList.get(b).getGiniResult(),
                           testScoringModelResultList.get(b).getOrderNumberRow()));
                }
                TestScoringModelResult testScoringModelResultTotal = testScoringModelResultDao
                        .getByTestModelIdAndUsernameTotalResult(testScoringModelList.get(a).getId());
                testPortalList.add(new TestPortal(testScoringModelList.get(a).getId(),
                        testScoringModelList.get(a).getCreatedAt(),
                        testScoringModelList.get(a).getTitle(),
                        testScoringModelResultTotal.getCountTotalItems(),
                        testScoringModelResultTotal.getCountGoodItems(),
                        testScoringModelResultTotal.getCountBadItems(),
                        testScoringModelResultTotal.getBadRate(),
                        testScoringModelResultTotal.getGiniResult(),
                        testResultsPortalList));
            }
            return testPortalList;
        }
    }
}
