package com.korotkov.mainCurrentApp.service.currencyRate;

import com.korotkov.mainCurrentApp.model.CurrencyRate;
import com.korotkov.mainCurrentApp.model.UserAccount;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRateService {
    void create(CurrencyRate currencyRate);
    void update(CurrencyRate currencyRate);
    CurrencyRate getById(Long id);
    Long getCountAllCurrencyRates();
    List<CurrencyRate> getAllCurrencyRates(int page);
    List<CurrencyRate> getCurrencyRateForDates(LocalDate dateFrom, LocalDate dateTo, int page);
    Long getCountCurrencyRateForDates(LocalDate dateFrom, LocalDate dateTo);
    Long getCountCurrencyRateForDateFrom(LocalDate dateFrom);
    Long getCountCurrencyRateForDateTo(LocalDate dateTo);
    List<CurrencyRate> getCurrencyRateForDateFrom(LocalDate dateFrom, int page);
    List<CurrencyRate> getCurrencyRateForDateTo(LocalDate dateTo, int page);
    void updateByUser(Long id, CurrencyRate currencyRate, UserAccount editedBy);
    boolean isCurrencyRateForToday();
    List<CurrencyRate> getCurrencyRatesWithDates(LocalDate dateFrom, LocalDate dateTo);
}
