package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.testScoringModel;

import com.korotkov.mainCurrentApp.scoringMachine.model.TestScoringModel;
import com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.additionalService.additionalEntityForPortal.TestPortal;

import java.util.List;

public interface TestScoringModelService {
    TestScoringModel getById(Long id);
    boolean isPossibleBuildNewTest();
    List<TestScoringModel> getAllTestingModels(int page);
    List<TestScoringModel> getTestingModelsWithFilterTitleTestingModel(int page,
                                                                       String titleTestingModel);
    List<TestScoringModel> getTestingModelsWithFilterTitleScoringModel(int page,
                                                                       String titleScoringModel);
    List<TestScoringModel> getTestingModelWithFilterTitlesScoringAndTestingModel(int page,
                                                                                 String titleTestingModel,
                                                                                 String titleScoringModel);
    Long getCountTestingModelsWithFilter(String titleTestingModel, String titleScoringModel);
    TestScoringModel getByUserAndTestId(Long testScoringModelId);
    void updateTitleAndDescriptionForTestScoringModel(Long testScoringModelId,
                                                      TestScoringModel testScoringModel);
    void deleteTestScoringModel(Long testScoringModelId);
    List<TestPortal> getAllModelsForScoringModelPortal(Long scoringModelId);
}
