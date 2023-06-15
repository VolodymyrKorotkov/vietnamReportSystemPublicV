package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.testScoringModelResult;

import com.korotkov.mainCurrentApp.scoringMachine.dao.TestScoringModelResultDao;
import com.korotkov.mainCurrentApp.scoringMachine.model.TestScoringModelResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestScoringModelResultServiceImpl implements TestScoringModelResultService{
    TestScoringModelResultDao testScoringModelResultDao;

    @Autowired
    public void setTestScoringModelResultDao(TestScoringModelResultDao testScoringModelResultDao){
        this.testScoringModelResultDao = testScoringModelResultDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public TestScoringModelResult getTestScoringModelResultTotal(Long testModelId){
        return testScoringModelResultDao.getByTestModelIdAndUsernameTotalResult(testModelId);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<TestScoringModelResult> getListTestResultsWithoutTotal(Long testModelId){
        return testScoringModelResultDao.getListTestResultsByModelIdAndUsername(testModelId);
    }
}
