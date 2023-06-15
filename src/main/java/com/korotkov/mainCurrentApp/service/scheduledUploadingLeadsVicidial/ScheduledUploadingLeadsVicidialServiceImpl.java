package com.korotkov.mainCurrentApp.service.scheduledUploadingLeadsVicidial;

import com.korotkov.mainCurrentApp.dao.scheduledUploadingLeadsVicidial.ScheduledUploadingLeadsVicidialDao;
import com.korotkov.mainCurrentApp.model.ScheduledUploadingLeadsVicidial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduledUploadingLeadsVicidialServiceImpl implements ScheduledUploadingLeadsVicidialService {
    private ScheduledUploadingLeadsVicidialDao scheduledUploadingLeadsVicidialDao;

    @Autowired
    public void setScheduledUploadingLeadsVicidialDao(ScheduledUploadingLeadsVicidialDao scheduledUploadingLeadsVicidialDao) {
        this.scheduledUploadingLeadsVicidialDao = scheduledUploadingLeadsVicidialDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public void create(ScheduledUploadingLeadsVicidial leadsVicidial) {
        scheduledUploadingLeadsVicidialDao.create(leadsVicidial);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ScheduledUploadingLeadsVicidial getById(Long id) {
        return scheduledUploadingLeadsVicidialDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScheduledUploadingLeadsVicidial> getAllScheduledUploadingLeadsVicidialList(int page) {
        return scheduledUploadingLeadsVicidialDao.getAllScheduledUploadingLeadsVicidialList(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAllScheduledUploadingLeadsVicidial() {
        return scheduledUploadingLeadsVicidialDao.getCountAllScheduledUploadingLeadsVicidial();
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScheduledUploadingLeadsVicidial> getScheduledUploadingLeadsVicidialListForDates(LocalDate dateFrom, LocalDate dateTo, int page) {
        return scheduledUploadingLeadsVicidialDao.getScheduledUploadingLeadsVicidialListForDates(dateFrom, dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountScheduledUploadingLeadsVicidialForDates(LocalDate dateFrom, LocalDate dateTo) {
        return scheduledUploadingLeadsVicidialDao.getCountScheduledUploadingLeadsVicidialForDates(dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScheduledUploadingLeadsVicidial> getScheduledUploadingLeadsVicidialListForDateFrom(LocalDate dateFrom, int page) {
        return scheduledUploadingLeadsVicidialDao.getScheduledUploadingLeadsVicidialListForDateFrom(dateFrom, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountScheduledUploadingLeadsVicidialForDateFrom(LocalDate dateFrom) {
        return scheduledUploadingLeadsVicidialDao.getCountScheduledUploadingLeadsVicidialForDateFrom(dateFrom);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScheduledUploadingLeadsVicidial> getScheduledUploadingLeadsVicidialListForDateTo(LocalDate dateTo, int page) {
        return scheduledUploadingLeadsVicidialDao.getScheduledUploadingLeadsVicidialListForDateTo(dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountScheduledUploadingLeadsVicidialForDateTo(LocalDate dateTo) {
        return scheduledUploadingLeadsVicidialDao.getCountScheduledUploadingLeadsVicidialForDateTo(dateTo);
    }


}
