package com.korotkov.mainCurrentApp.dao.applicationsQueueChecking;

import com.korotkov.mainCurrentApp.model.ApplicationsQueueChecking;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationsQueueCheckingDao {
    void create(ApplicationsQueueChecking applicationsQueueChecking);
    void update(ApplicationsQueueChecking applicationsQueueChecking);
    ApplicationsQueueChecking getById(Long id);
    Long getCountAllAppsQueueChecking();
    List<ApplicationsQueueChecking> getAllAppsQueueChecking(int page);
    List<ApplicationsQueueChecking> getAppsQueueCheckingForDates(LocalDate dateFrom, LocalDate dateTo,
                                                                 int page);
    Long getCountAppsQueueCheckingForDates(LocalDate dateFrom, LocalDate dateTo);
    List<ApplicationsQueueChecking> getAppsQueueCheckingForDateFrom(LocalDate dateFrom, int page);
    Long getCountAppsQueueCheckingForDateFrom(LocalDate dateFrom);
    List<ApplicationsQueueChecking> getAppsQueueCheckingForDateTo(LocalDate dateTo, int page);
    Long getCountAppsQueueCheckingForDateTo(LocalDate dateTo);
}
