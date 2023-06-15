package com.korotkov.creditCRM.service.checkerEntity;

import com.korotkov.creditCRM.dao.checkerEntity.CheckerEntityDao;
import com.korotkov.creditCRM.model.CheckerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckerEntityServiceImpl implements CheckerEntityService {
    private CheckerEntityDao checkerEntityDao;

    @Autowired
    public void setCheckerEntityDao(CheckerEntityDao checkerEntityDao) {
        this.checkerEntityDao = checkerEntityDao;
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<CheckerEntity> getApplicationsOnLongDMS(int minutesAgoForCheck) {
        return checkerEntityDao.getApplicationsOnLongDMS(minutesAgoForCheck);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public Long getCountLoansWithExpiredAndWithoutActiveDebtCollection() {
        return checkerEntityDao.getCountLoansWithExpiredAndWithoutActiveDebtCollection();
    }

    @Override
    @Transactional("transactionManagerCRM")
    public Long getCountLoansWhichNotCalculated() {
        return checkerEntityDao.getCountLoansWhichNotCalculated();
    }

    @Override
    @Transactional("transactionManagerCRM")
    public CheckerEntity getLastCreatedApplication() {
        return checkerEntityDao.getLastCreatedApplication();
    }

    @Override
    @Transactional("transactionManagerCRM")
    public CheckerEntity getLastIssuedLoan() {
        return checkerEntityDao.getLastIssuedLoan();
    }

    @Override
    @Transactional("transactionManagerCRM")
    public CheckerEntity getLastRegisteredClient() {
        return checkerEntityDao.getLastRegisteredClient();
    }

    @Override
    @Transactional("transactionManagerCRM")
    public CheckerEntity getLastPayment() {
        return checkerEntityDao.getLastPayment();
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<CheckerEntity> getApplicationsWithPayoutError() {
        return checkerEntityDao.getApplicationsWithPayoutError();
    }

    @Override
    @Transactional("transactionManagerCRM")
    public Long getCountAppsOnStatusSentToUnderwriting() {
        return checkerEntityDao.getCountAppsOnStatusSentToUnderwriting();
    }
}
