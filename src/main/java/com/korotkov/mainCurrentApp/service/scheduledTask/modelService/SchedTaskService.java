package com.korotkov.mainCurrentApp.service.scheduledTask.modelService;

import com.korotkov.mainCurrentApp.model.ScheduledTask;

import java.util.List;

public interface SchedTaskService {
    void create(String taskName, String status, String comment);
    Long getCountAllScheduledTasks();
    List<ScheduledTask> getAllScheduledTasks(int page);
}
