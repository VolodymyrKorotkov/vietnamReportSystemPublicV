package com.korotkov.mainCurrentApp.scoringMachine.dao;


import com.korotkov.mainCurrentApp.scoringMachine.model.TestScoringModel;

import java.util.List;

public interface TestScoringModelDao {
    void create(TestScoringModel testScoringModel);
    void update(TestScoringModel testScoringModel);
    void delete(TestScoringModel testScoringModel);
    TestScoringModel getById(Long id);
    TestScoringModel findEarliestTestByModel(Long scoringModelId);
    TestScoringModel findLastTestByModel(Long scoringModelId);
    List<TestScoringModel> getAllTestingModels(int page);
    List<TestScoringModel> getTestingModelsWithFilterTitleTestingModel(int page, String titleTestingModel);
    List<TestScoringModel> getTestingModelsWithFilterTitleScoringModel(int page, String titleScoringModel);
    List<TestScoringModel> getTestingModelWithFilterTitlesScoringAndTestingModel(int page,
                                                                                 String titleTestingModel,
                                                                                 String titleScoringModel);
    Long getCountTestingModelsWithFilter(String titleTestingModel, String titleScoringModel);
    TestScoringModel getByUsernameAndId(Long testScoringModelId);
    List<TestScoringModel> getAllTestsForScoringModel(Long scoringModelId);
}
