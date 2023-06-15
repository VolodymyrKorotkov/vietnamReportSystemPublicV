package com.korotkov.mainCurrentApp.service.applicationsQueueChecking;


import com.korotkov.mainCurrentApp.dao.applicationsQueueChecking.ApplicationsQueueCheckingDao;
import com.korotkov.mainCurrentApp.model.ApplicationsQueueChecking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class ApplicationsQueueCheckingServiceImpl implements ApplicationsQueueCheckingService {
    private ApplicationsQueueCheckingDao applicationsQueueCheckingDao;

    @Autowired
    public void setApplicationsQueueCheckingDao(ApplicationsQueueCheckingDao applicationsQueueCheckingDao) {
        this.applicationsQueueCheckingDao = applicationsQueueCheckingDao;
    }


    @Override
    @Transactional("transactionManagerMain")
    public void create(ApplicationsQueueChecking applicationsQueueChecking) {
        applicationsQueueCheckingDao.create(applicationsQueueChecking);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void create(int count) {
        ApplicationsQueueChecking applicationsQueueChecking = new ApplicationsQueueChecking();
        applicationsQueueChecking.setDateTime(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        applicationsQueueChecking.setCount(count);
        applicationsQueueCheckingDao.create(applicationsQueueChecking);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void update(ApplicationsQueueChecking applicationsQueueChecking) {
        applicationsQueueCheckingDao.update(applicationsQueueChecking);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ApplicationsQueueChecking getById(Long id) {
        return applicationsQueueCheckingDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAllAppsQueueChecking() {
        return applicationsQueueCheckingDao.getCountAllAppsQueueChecking();
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ApplicationsQueueChecking> getAllAppsQueueChecking(int page) {
        return applicationsQueueCheckingDao.getAllAppsQueueChecking(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ApplicationsQueueChecking> getAppsQueueCheckingForDates(LocalDate dateFrom, LocalDate dateTo,
                                                                        int page) {
        return applicationsQueueCheckingDao.getAppsQueueCheckingForDates(dateFrom, dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAppsQueueCheckingForDates(LocalDate dateFrom, LocalDate dateTo) {
        return applicationsQueueCheckingDao.getCountAppsQueueCheckingForDates(dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ApplicationsQueueChecking> getAppsQueueCheckingForDateFrom(LocalDate dateFrom, int page) {
        return applicationsQueueCheckingDao.getAppsQueueCheckingForDateFrom(dateFrom, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAppsQueueCheckingForDateFrom(LocalDate dateFrom) {
        return applicationsQueueCheckingDao.getCountAppsQueueCheckingForDateFrom(dateFrom);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<ApplicationsQueueChecking> getAppsQueueCheckingForDateTo(LocalDate dateTo, int page) {
        return applicationsQueueCheckingDao.getAppsQueueCheckingForDateTo(dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAppsQueueCheckingForDateTo(LocalDate dateTo) {
        return applicationsQueueCheckingDao.getCountAppsQueueCheckingForDateTo(dateTo);
    }
}
