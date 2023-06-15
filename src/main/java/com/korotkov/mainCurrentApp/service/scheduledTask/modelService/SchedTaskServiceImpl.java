package com.korotkov.mainCurrentApp.service.scheduledTask.modelService;

import com.korotkov.mainCurrentApp.dao.scheduledTask.ScheduledTaskDao;
import com.korotkov.mainCurrentApp.model.ScheduledTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class SchedTaskServiceImpl implements SchedTaskService{
    private ScheduledTaskDao scheduledTaskDao;

    @Autowired
    public void setScheduledTaskDao(ScheduledTaskDao scheduledTaskDao) {
        this.scheduledTaskDao = scheduledTaskDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public void create(String taskName, String status, String comment) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.setTask(taskName);
        scheduledTask.setStatus(status);
        scheduledTask.setComment(comment);
        scheduledTask.setDoneAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scheduledTaskDao.create(scheduledTask);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAllScheduledTasks() {
        return scheduledTaskDao.getCountAllScheduledTasks();
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScheduledTask> getAllScheduledTasks(int page) {
        return scheduledTaskDao.getAllScheduledTasks(page);
    }
}
