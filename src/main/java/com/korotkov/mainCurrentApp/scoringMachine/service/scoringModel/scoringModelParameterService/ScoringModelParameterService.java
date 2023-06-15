package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.scoringModelParameterService;

import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModelParameter;

public interface ScoringModelParameterService {
    ScoringModelParameter getModelAttributeValue(Long modelId, Long idInDataBase);
    void setChangedScoreInAttributeValue(Long modelId, Long idInDataBase,
                                         ScoringModelParameter scoringModelParameter);
}
