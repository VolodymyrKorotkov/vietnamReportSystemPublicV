package com.korotkov.mainCurrentApp.scoringMachine.dao;

import com.korotkov.mainCurrentApp.scoringMachine.model.TestScoringModelResult;

import java.util.List;

public interface TestScoringModelResultDao {
    void create(TestScoringModelResult testScoringModelResult);
    void update(TestScoringModelResult testScoringModelResult);
    void delete(TestScoringModelResult testScoringModelResult);
    TestScoringModelResult getById(Long id);
    TestScoringModelResult getByModelAndOrderRowNumber(Long idTestModel, Integer orderRowNumber);
    TestScoringModelResult getByTestModelIdAndUsernameTotalResult(Long testModelId);
    List<TestScoringModelResult> getListTestResultsByModelIdAndUsername(Long testModelId);
}
