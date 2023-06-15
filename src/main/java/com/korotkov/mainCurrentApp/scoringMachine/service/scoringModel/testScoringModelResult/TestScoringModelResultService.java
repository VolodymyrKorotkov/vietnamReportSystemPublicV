package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.testScoringModelResult;

import com.korotkov.mainCurrentApp.scoringMachine.model.TestScoringModelResult;
import java.util.List;

public interface TestScoringModelResultService {
    TestScoringModelResult getTestScoringModelResultTotal(Long testModelId);
    List<TestScoringModelResult> getListTestResultsWithoutTotal(Long testModelId);
}
