package com.korotkov.mainCurrentApp.dao.modelPlan;

import com.korotkov.mainCurrentApp.model.ModelPlan;

import java.time.LocalDate;
import java.util.List;

public interface ModelPlanDao {
    void create(ModelPlan modelPlan);
    void update(ModelPlan modelPlan);
    ModelPlan getById(Long id);
    List<ModelPlan> getAllModelPlans(int page);
    Long getCountAllModelPlans();
    ModelPlan getByDateMonth(LocalDate date);
    List<ModelPlan> getModelPlansWithDates(LocalDate dateFrom, LocalDate dateTo);
}
