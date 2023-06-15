package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.scoringSettings;

import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringSettingsModel;

public interface ScoringSettingsModelService {
    void create(ScoringSettingsModel scoringSettingsModel);
    void update(ScoringSettingsModel scoringSettingsModel);
    ScoringSettingsModel getById(Long id);
    ScoringSettingsModel findByUser();
    boolean updateScoringSettings(ScoringSettingsModel scoringSettingsModel);
    void restoreDefaultScoringSettings();
    void updateScoringAdvanceSettings(ScoringSettingsModel scoringSettingsModel);
    void restoreDefaultTestingModelSettings();
    void updateTestingModelSettings(ScoringSettingsModel scoringSettingsModel);
}
