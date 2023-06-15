package com.korotkov.mainCurrentApp.dao.scheduledTask;

import com.korotkov.mainCurrentApp.model.ScheduledTask;

import java.util.List;

public interface ScheduledTaskDao {
    void create(ScheduledTask scheduledTask);
    void update(ScheduledTask scheduledTask);
    ScheduledTask getById(Long id);
    Long getCountAllScheduledTasks();
    List<ScheduledTask> getAllScheduledTasks(int page);
}
