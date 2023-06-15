package com.korotkov.mainCurrentApp.scoringMachine.service.scoringModel.testingCore;


import com.korotkov.mainCurrentApp.model.UserAccount;

import java.util.ArrayList;

public interface TestingCalculationService {
    void createNewTest(ArrayList<ArrayList<String>> listFromFileExcel, UserAccount userAccount);
}
