package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.scoringCore;

import com.korotkov.mainCurrentApp.model.UserAccount;
import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModel;
import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringModelParameter;

import java.util.ArrayList;
import java.util.List;

public interface ScoringCalculationService {
    void createScoringModel(ScoringModel scoringModel);
    void updateScoringModel(ScoringModel scoringModel);
    void deleteScoringModel(ScoringModel scoringModel);
    ScoringModel getByIdScoringModel(Long id);
    ScoringModel findEarliestModelByUser();
    ScoringModel findActiveModelByUser();
    void createScoringModelParameter(ScoringModelParameter scoringModelParameter);
    void updateScoringModelParameter(ScoringModelParameter scoringModelParameter);
    void deleteScoringModelParameter(ScoringModelParameter scoringModelParameter);
    ScoringModelParameter getByIdScoringModelParameter(Long id);
    List<ScoringModelParameter> findAllParametersByModel(ScoringModel scoringModel);
    void deleteAllParametersByModel(ScoringModel scoringModel);
    void createNewScoringModel(ArrayList<ArrayList<String>> listFromFileExcel, UserAccount userAccount);
}
