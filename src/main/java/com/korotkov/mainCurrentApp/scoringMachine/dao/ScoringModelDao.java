package com.korotkov.mainCurrentApp.scoringMachine.dao;

import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModel;
import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModelParameter;

import java.util.List;

public interface ScoringModelDao {
    void create(ScoringModel scoringModel);
    void update(ScoringModel scoringModel);
    void delete(ScoringModel scoringModel);
    ScoringModel getById(Long id);
    ScoringModel findEarliestModelByUser();
    ScoringModel findActiveModelByUser();
    ScoringModel findLastCreatedModelByUser();
    List<ScoringModel> getAllModels(int page);
    List<ScoringModel> findModelsWithStatus(String status);
    List<ScoringModel> getModelsWithFilter(int page, String scoringModelTitle,
                                           String scoringModelStatus);
    List<ScoringModel> getModelsWithFilter(int page, String scoringModelTitle);
    List<ScoringModel> getModelsWithFilter(String scoringModelStatus, int page);
    Long getCountModelsWithFilter(String scoringModelTitle, String scoringModelStatus);
    ScoringModel getScoringModelByIdAndUsername(Long modelId);
    List<ScoringModelParameter> getScoringModelRecommendedParametersList(Long modelId,
                                                                         String scoringParameterType);
    List<ScoringModelParameter> getScoringModelAllParametersTotalList(Long modelId,
                                                                 String scoringParameterType);
    boolean isHavingActiveModelUser();
}
