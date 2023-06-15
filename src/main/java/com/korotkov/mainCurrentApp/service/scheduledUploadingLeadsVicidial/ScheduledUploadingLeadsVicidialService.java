package com.korotkov.mainCurrentApp.service.scheduledUploadingLeadsVicidial;

import com.korotkov.mainCurrentApp.model.ScheduledUploadingLeadsVicidial;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduledUploadingLeadsVicidialService {
    void create(ScheduledUploadingLeadsVicidial leadsVicidial);
    ScheduledUploadingLeadsVicidial getById(Long id);
    List<ScheduledUploadingLeadsVicidial> getAllScheduledUploadingLeadsVicidialList(int page);
    Long getCountAllScheduledUploadingLeadsVicidial();
    List<ScheduledUploadingLeadsVicidial> getScheduledUploadingLeadsVicidialListForDates(LocalDate dateFrom, LocalDate dateTo, int page);
    Long getCountScheduledUploadingLeadsVicidialForDates(LocalDate dateFrom, LocalDate dateTo);
    List<ScheduledUploadingLeadsVicidial> getScheduledUploadingLeadsVicidialListForDateFrom(LocalDate dateFrom, int page);
    Long getCountScheduledUploadingLeadsVicidialForDateFrom(LocalDate dateFrom);
    List<ScheduledUploadingLeadsVicidial> getScheduledUploadingLeadsVicidialListForDateTo(LocalDate dateTo, int page);
    Long getCountScheduledUploadingLeadsVicidialForDateTo(LocalDate dateTo);
}
