package com.korotkov.mainCurrentApp.api.currencyConverterApi;

import com.google.gson.Gson;
import com.korotkov.mainCurrentApp.config.ConfigConstants;
import com.korotkov.mainCurrentApp.dao.currencyRate.CurrencyRateDao;
import com.korotkov.mainCurrentApp.model.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class CurrencyConverterApiServiceImpl implements CurrencyConverterApiService, ConfigConstants, ApiCurrencyConverterConfig {
    private RestTemplate restTemplate;
    private CurrencyRateDao currencyRateDao;


    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setCurrencyRateDao(CurrencyRateDao currencyRateDao) {
        this.currencyRateDao = currencyRateDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean apiRequestUsdToVnd() {
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(URL_WHOLE_REQUEST +
                    API_KEY, String.class);
        } catch (Exception e) {
            return false;
        }
        if (responseEntity.getStatusCodeValue() != 200) {
            return false;
        }
        Gson gson = new Gson();
        UsdVndResponseModel usdVndResponseModel = gson.fromJson(responseEntity.getBody(), UsdVndResponseModel.class);
        if (usdVndResponseModel == null) {
            return false;
        }
        LocalDate dateToday = LocalDate.from(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        CurrencyRate currencyRateFromDB = currencyRateDao.getCurrencyRateByDate(dateToday);
        if (currencyRateFromDB != null) {
            currencyRateFromDB.setUsdVnd(usdVndResponseModel.getUSD_VND());
            currencyRateFromDB.setGetByApi(true);
            currencyRateFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            currencyRateFromDB.setLastModifiedBy(null);
            currencyRateDao.update(currencyRateFromDB);
            return true;
        } else {
            CurrencyRate currencyRate = new CurrencyRate();
            currencyRate.setDate(dateToday);
            currencyRate.setUsdVnd(usdVndResponseModel.getUSD_VND());
            currencyRate.setGetByApi(true);
            currencyRate.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            currencyRate.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            currencyRate.setLastModifiedBy(null);
            currencyRateDao.create(currencyRate);
            return true;
        }
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean apiSchedulerRequestCurrencyRateUsdToVnd() {
        LocalDate dateToday = LocalDate.from(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        CurrencyRate currencyRateFromDB = currencyRateDao.getCurrencyRateByDate(dateToday);
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL_WHOLE_REQUEST +
                    API_KEY, String.class);
            if (responseEntity.getStatusCodeValue() == 200) {
                Gson gson = new Gson();
                UsdVndResponseModel usdVndResponseModel = gson.fromJson(responseEntity.getBody(), UsdVndResponseModel.class);
                if (usdVndResponseModel != null) {
                    if (currencyRateFromDB != null) {
                        currencyRateFromDB.setUsdVnd(usdVndResponseModel.getUSD_VND());
                        currencyRateFromDB.setGetByApi(true);
                        currencyRateFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                        currencyRateFromDB.setLastModifiedBy(null);
                        currencyRateDao.update(currencyRateFromDB);
                    } else {
                        CurrencyRate currencyRate = new CurrencyRate();
                        currencyRate.setDate(dateToday);
                        currencyRate.setUsdVnd(usdVndResponseModel.getUSD_VND());
                        currencyRate.setGetByApi(true);
                        currencyRate.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                        currencyRate.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                        currencyRate.setLastModifiedBy(null);
                        currencyRateDao.create(currencyRate);
                    }
                    return true;
                }
                return false;
            } else {
                CurrencyRate currencyRateFromDBLast = currencyRateDao.getLastCurrencyRate();
                if (currencyRateFromDB == null) {
                    CurrencyRate currencyRate = new CurrencyRate();
                    currencyRate.setDate(dateToday);
                    currencyRate.setUsdVnd(currencyRateFromDBLast.getUsdVnd());
                    currencyRate.setGetByApi(false);
                    currencyRate.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                    currencyRate.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                    currencyRate.setLastModifiedBy(null);
                    currencyRateDao.create(currencyRate);
                }
            }
        } catch (Exception e) {
            CurrencyRate currencyRateFromDBLast = currencyRateDao.getLastCurrencyRate();
            if (currencyRateFromDB == null) {
                CurrencyRate currencyRate = new CurrencyRate();
                currencyRate.setDate(dateToday);
                currencyRate.setUsdVnd(currencyRateFromDBLast.getUsdVnd());
                currencyRate.setGetByApi(false);
                currencyRate.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                currencyRate.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
                currencyRate.setLastModifiedBy(null);
                currencyRateDao.create(currencyRate);
            }
        }
        return false;
    }


}
