package com.korotkov.mainCurrentApp.scoringMachine.dao;

import com.korotkov.mainCurrentApp.scoringMachine.model.ScoringSettingsModel;

public interface ScoringSettingsModelDao {
    void create(ScoringSettingsModel scoringSettingsModel);
    void update(ScoringSettingsModel scoringSettingsModel);
    ScoringSettingsModel getById(Long id);
    ScoringSettingsModel findByUser();
}
