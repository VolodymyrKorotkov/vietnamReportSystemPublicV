package com.korotkov.mainCurrentApp.dao.currencyRate;

import com.korotkov.mainCurrentApp.model.CurrencyRate;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRateDao {
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
    CurrencyRate getCurrencyRateByDate(LocalDate date);
    CurrencyRate getLastCurrencyRate();
    List<CurrencyRate> getCurrencyRatesWithDates(LocalDate dateFrom, LocalDate dateTo);
}
