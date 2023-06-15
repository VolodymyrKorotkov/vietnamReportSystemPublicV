package com.korotkov.mainCurrentApp.service.modelPlan;

import com.korotkov.mainCurrentApp.model.ModelPlan;
import com.korotkov.mainCurrentApp.model.UserAccount;

import java.time.LocalDate;
import java.util.List;

public interface ModelPlanService {
    void create(ModelPlan modelPlan);
    void update(ModelPlan modelPlan);
    ModelPlan getById(Long id);
    List<ModelPlan> getAllModelPlans(int page);
    Long getCountAllModelPlans();
    boolean createNewPlan(ModelPlan modelPlan, UserAccount userAccount);
    void updatePlan(Long id, ModelPlan modelPlan, UserAccount userAccount);
    Long getIdCreatedModelByDateMonth(ModelPlan modelPlan);
    List<ModelPlan> getModelPlansWithDates(LocalDate dateFrom, LocalDate dateTo);
}
