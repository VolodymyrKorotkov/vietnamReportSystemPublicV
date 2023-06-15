package com.korotkov.mainCurrentApp.service.scheduledChecking;

import com.korotkov.mainCurrentApp.model.ScheduledChecking;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ScheduledCheckingService {
    void create(ScheduledChecking scheduledChecking);
    void createNewScheduledCheckingAsStarted(String title, int count);
    void createNewScheduledCheckingAsStarted(String title);
    void update(ScheduledChecking scheduledChecking);
    ScheduledChecking getById(Long id);
    Long getCountAllScheduledChecking();
    List<ScheduledChecking> getAllScheduledChecking(int page);
    List<ScheduledChecking> getScheduledCheckingForDates(LocalDate dateFrom, LocalDate dateTo, int page);
    Long getCountScheduledCheckingForDates(LocalDate dateFrom, LocalDate dateTo);
    List<ScheduledChecking> getScheduledCheckingForDateFrom(LocalDate dateFrom, int page);
    Long getCountScheduledCheckingForDateFrom(LocalDate dateFrom);
    List<ScheduledChecking> getScheduledCheckingForDateTo(LocalDate dateTo, int page);
    Long getCountScheduledCheckingForDateTo(LocalDate dateTo);
    ScheduledChecking getUniqueNotFinishedScheduledChecking(String titleScheduledChecking);
}
