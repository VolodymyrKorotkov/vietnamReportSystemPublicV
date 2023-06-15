package com.korotkov.mainCurrentApp.dao.uploadingClientPhones;

import com.korotkov.mainCurrentApp.model.UploadingClientPhones;

import java.time.LocalDate;
import java.util.List;

public interface UploadingClientPhonesDAO {
    void create(UploadingClientPhones uploadingClientPhones);
    void update(UploadingClientPhones uploadingClientPhones);
    UploadingClientPhones getById(Long id);
    Long getCountAllUploadingClientPhones();
    List<UploadingClientPhones> getAllUploadingClientPhones(int page);
    Long getCountUploadingClientPhonesForDates(LocalDate dateFrom, LocalDate dateTo);
    List<UploadingClientPhones> getUploadingClientPhonesForDates(LocalDate dateFrom, LocalDate dateTo, int page);
    Long getCountUploadingClientPhonesForDateFrom(LocalDate dateFrom);
    List<UploadingClientPhones> getUploadingClientPhonesForDateFrom(LocalDate dateFrom, int page);
    Long getCountUploadingClientPhonesForDateTo(LocalDate dateTo);
    List<UploadingClientPhones> getUploadingClientPhonesForDateTo(LocalDate dateTo, int page);
}
