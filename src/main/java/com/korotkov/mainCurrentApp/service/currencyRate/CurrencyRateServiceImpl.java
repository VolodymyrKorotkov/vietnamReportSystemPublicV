package com.korotkov.mainCurrentApp.service.currencyRate;

import com.korotkov.mainCurrentApp.config.ConfigConstants;
import com.korotkov.mainCurrentApp.dao.currencyRate.CurrencyRateDao;
import com.korotkov.mainCurrentApp.model.CurrencyRate;
import com.korotkov.mainCurrentApp.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService, ConfigConstants {
    private CurrencyRateDao currencyRateDao;

    @Autowired
    public void setCurrencyRateDao(CurrencyRateDao currencyRateDao) {
        this.currencyRateDao = currencyRateDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public void create(CurrencyRate currencyRate) {
        currencyRateDao.create(currencyRate);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void update(CurrencyRate currencyRate) {
        currencyRateDao.update(currencyRate);
    }

    @Override
    @Transactional("transactionManagerMain")
    public CurrencyRate getById(Long id) {
        return currencyRateDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAllCurrencyRates() {
        return currencyRateDao.getCountAllCurrencyRates();
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<CurrencyRate> getAllCurrencyRates(int page) {
        return currencyRateDao.getAllCurrencyRates(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<CurrencyRate> getCurrencyRateForDates(LocalDate dateFrom, LocalDate dateTo, int page) {
        return currencyRateDao.getCurrencyRateForDates(dateFrom, dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountCurrencyRateForDates(LocalDate dateFrom, LocalDate dateTo) {
        return currencyRateDao.getCountCurrencyRateForDates(dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountCurrencyRateForDateFrom(LocalDate dateFrom) {
        return currencyRateDao.getCountCurrencyRateForDateFrom(dateFrom);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountCurrencyRateForDateTo(LocalDate dateTo) {
        return currencyRateDao.getCountCurrencyRateForDateTo(dateTo);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<CurrencyRate> getCurrencyRateForDateFrom(LocalDate dateFrom, int page) {
        return currencyRateDao.getCurrencyRateForDateFrom(dateFrom, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<CurrencyRate> getCurrencyRateForDateTo(LocalDate dateTo, int page) {
        return currencyRateDao.getCurrencyRateForDateTo(dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void updateByUser(Long id, CurrencyRate currencyRate, UserAccount editedBy) {
        CurrencyRate currencyRateFromDB = currencyRateDao.getById(id);
        currencyRateFromDB.setUsdVnd(currencyRate.getUsdVnd());
        currencyRateFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        currencyRateFromDB.setLastModifiedBy(editedBy);
        currencyRateDao.update(currencyRateFromDB);
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean isCurrencyRateForToday() {
        CurrencyRate currencyRateFromDB = currencyRateDao.getCurrencyRateByDate(LocalDate.from(LocalDateTime.now(ZoneId.of(TIME_ZONE))));
        if (currencyRateFromDB == null) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<CurrencyRate> getCurrencyRatesWithDates(LocalDate dateFrom, LocalDate dateTo) {
        return currencyRateDao.getCurrencyRatesWithDates(dateFrom, dateTo);
    }

}
