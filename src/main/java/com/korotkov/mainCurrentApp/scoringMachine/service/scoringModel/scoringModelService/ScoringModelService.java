package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.scoringModelService;

import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModel;
import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModelParameter;
import com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.additionalService.additionalEntityForPortal.ScoringParameterPortalCommon;

import java.util.List;

public interface ScoringModelService {
    List<ScoringModel> getAllModels(int page);
    List<ScoringModel> getModelsWithFilter(int page, String scoringModelTitle,
                                           String scoringModelStatus);
    List<ScoringModel> getModelsWithFilter(int page, String scoringModelTitle);
    List<ScoringModel> getModelsWithFilter(String scoringModelStatus, int page);
    Long getCountModelsWithFilter(String scoringModelTitle, String scoringModelStatus);
    ScoringModel getScoringModelByIdAndUser(Long modelId);
    List<ScoringParameterPortalCommon> getScoringModelRecommendedParametersList(Long modelId,
                                                                                String scoringParameterType);
    List<ScoringModelParameter> getScoringModelAllParametersTotalList(Long modelId,
                                                                 String scoringParameterType);
    void updateTitleAndDescriptionInScoringModel(Long modelId, ScoringModel scoringModel);
    void deleteScoringModel(Long modelId);
    void activateScoringModel(Long modelId);
    void deactivateScoringModel(Long modelId);
    ScoringModel findActiveModelByUser();
}
