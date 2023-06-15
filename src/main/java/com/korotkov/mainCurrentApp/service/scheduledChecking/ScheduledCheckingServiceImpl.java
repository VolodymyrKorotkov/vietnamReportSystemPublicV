package com.korotkov.mainCurrentApp.service.scheduledChecking;

import com.korotkov.mainCurrentApp.dao.scheduledChecking.ScheduledCheckingDao;
import com.korotkov.mainCurrentApp.enums.ScheduledCheckingStatusEnum;
import com.korotkov.mainCurrentApp.model.ScheduledChecking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class ScheduledCheckingServiceImpl implements ScheduledCheckingService {
    private ScheduledCheckingDao scheduledCheckingDao;

    @Autowired
    public void setScheduledCheckingDao(ScheduledCheckingDao scheduledCheckingDao) {
        this.scheduledCheckingDao = scheduledCheckingDao;
    }



    @Override
    @Transactional("transactionManagerMain")
    public void create(ScheduledChecking scheduledChecking) {
        scheduledCheckingDao.create(scheduledChecking);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void createNewScheduledCheckingAsStarted(String title, int count) {
        ScheduledChecking scheduledChecking = new ScheduledChecking();
        scheduledChecking.setTitle(title);
        scheduledChecking.setCount(count);
        scheduledChecking.setStatus(String.valueOf(ScheduledCheckingStatusEnum.STARTED));
        scheduledChecking.setStartedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scheduledCheckingDao.create(scheduledChecking);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void createNewScheduledCheckingAsStarted(String title) {
        ScheduledChecking scheduledChecking = new ScheduledChecking();
        scheduledChecking.setTitle(title);
        scheduledChecking.setStatus(String.valueOf(ScheduledCheckingStatusEnum.STARTED));
        scheduledChecking.setStartedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        scheduledCheckingDao.create(scheduledChecking);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void update(ScheduledChecking scheduledChecking) {
        scheduledCheckingDao.update(scheduledChecking);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ScheduledChecking getById(Long id) {
        return scheduledCheckingDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAllScheduledChecking() {
        return scheduledCheckingDao.getCountAllScheduledChecking();
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScheduledChecking> getAllScheduledChecking(int page) {
        return scheduledCheckingDao.getAllScheduledChecking(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScheduledChecking> getScheduledCheckingForDates(LocalDate dateFrom, LocalDate dateTo, int page) {
        return scheduledCheckingDao.getScheduledCheckingForDates(dateFrom, dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountScheduledCheckingForDates(LocalDate dateFrom, LocalDate dateTo) {
        return scheduledCheckingDao.getCountScheduledCheckingForDates(dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScheduledChecking> getScheduledCheckingForDateFrom(LocalDate dateFrom, int page) {
        return scheduledCheckingDao.getScheduledCheckingForDateFrom(dateFrom, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountScheduledCheckingForDateFrom(LocalDate dateFrom) {
        return scheduledCheckingDao.getCountScheduledCheckingForDateFrom(dateFrom);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ScheduledChecking> getScheduledCheckingForDateTo(LocalDate dateTo, int page) {
        return scheduledCheckingDao.getScheduledCheckingForDateTo(dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountScheduledCheckingForDateTo(LocalDate dateTo) {
        return scheduledCheckingDao.getCountScheduledCheckingForDateTo(dateTo);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ScheduledChecking getUniqueNotFinishedScheduledChecking(String titleScheduledChecking) {
        return scheduledCheckingDao.getUniqueNotFinishedScheduledChecking(titleScheduledChecking);
    }

}
