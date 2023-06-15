package com.korotkov.creditCRM.model.loansInfo;

import com.korotkov.mainCurrentApp.model.CurrencyRate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Getter
@Setter
@NoArgsConstructor
public class ExportLoansInfoExpiredInfoReportObject {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime createdAt;
    private List<LocalDate> dateList;
    private List<LocalDate> monthList;
    private List<LoanInfoExpiredInfo> loanInfoDateList;
    private List<LoanInfoExpiredInfo> loanInfoMonthList;
    private List<CurrencyRate> currencyRateList;

    private Map<LocalDate, Double> mapMonthAndAverageCurrencyRateUsdVnd;

    public ExportLoansInfoExpiredInfoReportObject (LocalDate dateFrom, LocalDate dateTo,
                                                   List<LoanInfoExpiredInfo> loanInfoDateList,
                                                   List<CurrencyRate> currencyRateList) {
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.loanInfoDateList = loanInfoDateList;
        this.currencyRateList = currencyRateList;
        this.dateList = getDateList(this.loanInfoDateList);
        this.monthList = getMonthList(this.dateList);
        this.loanInfoMonthList = getLoanInfoMonthList(this.monthList, this.loanInfoDateList);
        this.mapMonthAndAverageCurrencyRateUsdVnd = getMapAverageMonthCurrencyRateUsdVnd(this.currencyRateList,
                this.monthList);

    }

    private Map<LocalDate, Double> getMapAverageMonthCurrencyRateUsdVnd(List<CurrencyRate> currencyRateList,
                                                                        List<LocalDate> monthList) {
        Map<LocalDate, Double> averageMonthsCurrencyUsdVnd = new HashMap<>();
        for (int a = 0; a < monthList.size(); a++) {
            double sumCurrencyRates = 0.00;
            int countDays = 0;
            for (int b = 0; b < currencyRateList.size(); b++) {
                if (monthList.get(a).getMonth().equals(currencyRateList.get(b).getDate().getMonth()) &&
                        monthList.get(a).getYear() == currencyRateList.get(b).getDate().getYear()) {
                    sumCurrencyRates += currencyRateList.get(b).getUsdVnd();
                    countDays++;
                }
            }
            averageMonthsCurrencyUsdVnd.put(monthList.get(a), sumCurrencyRates / countDays);
        }
        return averageMonthsCurrencyUsdVnd;
    }

    private List<LoanInfoExpiredInfo> getLoanInfoMonthList (List<LocalDate> monthList,
                                                            List<LoanInfoExpiredInfo> loanInfoDateList) {
        List<LoanInfoExpiredInfo> loanInfoMonthList = new ArrayList<>();
        for (LocalDate dateMonth : monthList) {
            LoanInfoExpiredInfo loanInfo = new LoanInfoExpiredInfo();
            loanInfo.setDate(dateMonth);
            Long totalIssuedLoanCount = 0L;
            Double totalIssuedLoanAmount = 0.00;
            Long potentialIssuedLoanCount = 0L;
            Long repeatIssuedLoanCount = 0L;
            Double potentialIssuedLoanAmount = 0.00;
            Double repeatIssuedLoanAmount = 0.00;
            Long totalActiveLoanCount = 0L;
            Long potentialActiveLoanCount = 0L;
            Long repeatActiveLoanCount = 0L;
            Long totalOn2DpdCount = 0L;
            Long potentialOn2DpdCount = 0L;
            Long repeatOn2DpdCount = 0L;
            Double totalOn2DpdAmount = 0.00;
            Double potentialOn2DpdAmount = 0.00;
            Double repeatOn2DpdAmount = 0.00;
            Long totalOn5DpdCount = 0L;
            Long potentialOn5DpdCount = 0L;
            Long repeatOn5DpdCount = 0L;
            Double totalOn5DpdAmount = 0.00;
            Double potentialOn5DpdAmount = 0.00;
            Double repeatOn5DpdAmount = 0.00;
            Long totalOn10DpdCount = 0L;
            Long potentialOn10DpdCount = 0L;
            Long repeatOn10DpdCount = 0L;
            Double totalOn10DpdAmount = 0.00;
            Double potentialOn10DpdAmount = 0.00;
            Double repeatOn10DpdAmount = 0.00;
            Long totalOn15DpdCount = 0L;
            Long potentialOn15DpdCount = 0L;
            Long repeatOn15DpdCount = 0L;
            Double totalOn15DpdAmount = 0.00;
            Double potentialOn15DpdAmount = 0.00;
            Double repeatOn15DpdAmount = 0.00;
            Long totalOn20DpdCount = 0L;
            Long potentialOn20DpdCount = 0L;
            Long repeatOn20DpdCount = 0L;
            Double totalOn20DpdAmount = 0.00;
            Double potentialOn20DpdAmount = 0.00;
            Double repeatOn20DpdAmount = 0.00;
            Long totalOn25DpdCount = 0L;
            Long potentialOn25DpdCount = 0L;
            Long repeatOn25DpdCount = 0L;
            Double totalOn25DpdAmount = 0.00;
            Double potentialOn25DpdAmount = 0.00;
            Double repeatOn25DpdAmount = 0.00;
            Long totalOn30DpdCount = 0L;
            Long potentialOn30DpdCount = 0L;
            Long repeatOn30DpdCount = 0L;
            Double totalOn30DpdAmount = 0.00;
            Double potentialOn30DpdAmount = 0.00;
            Double repeatOn30DpdAmount = 0.00;
            Long totalOn40DpdCount = 0L;
            Long potentialOn40DpdCount = 0L;
            Long repeatOn40DpdCount = 0L;
            Double totalOn40DpdAmount = 0.00;
            Double potentialOn40DpdAmount = 0.00;
            Double repeatOn40DpdAmount = 0.00;
            Long totalOn50DpdCount = 0L;
            Long potentialOn50DpdCount = 0L;
            Long repeatOn50DpdCount = 0L;
            Double totalOn50DpdAmount = 0.00;
            Double potentialOn50DpdAmount = 0.00;
            Double repeatOn50DpdAmount = 0.00;
            Long totalOn60DpdCount = 0L;
            Long potentialOn60DpdCount = 0L;
            Long repeatOn60DpdCount = 0L;
            Double totalOn60DpdAmount = 0.00;
            Double potentialOn60DpdAmount = 0.00;
            Double repeatOn60DpdAmount = 0.00;
            Long totalOn90DpdCount = 0L;
            Long potentialOn90DpdCount = 0L;
            Long repeatOn90DpdCount = 0L;
            Double totalOn90DpdAmount = 0.00;
            Double potentialOn90DpdAmount = 0.00;
            Double repeatOn90DpdAmount = 0.00;
            Long totalOn120DpdCount = 0L;
            Long potentialOn120DpdCount = 0L;
            Long repeatOn120DpdCount = 0L;
            Double totalOn120DpdAmount = 0.00;
            Double potentialOn120DpdAmount = 0.00;
            Double repeatOn120DpdAmount = 0.00;
            Long totalOn150DpdCount = 0L;
            Long potentialOn150DpdCount = 0L;
            Long repeatOn150DpdCount = 0L;
            Double totalOn150DpdAmount = 0.00;
            Double potentialOn150DpdAmount = 0.00;
            Double repeatOn150DpdAmount = 0.00;
            Long totalOn180DpdCount = 0L;
            Long potentialOn180DpdCount = 0L;
            Long repeatOn180DpdCount = 0L;
            Double totalOn180DpdAmount = 0.00;
            Double potentialOn180DpdAmount = 0.00;
            Double repeatOn180DpdAmount = 0.00;
            Long totalOn250DpdCount = 0L;
            Long potentialOn250DpdCount = 0L;
            Long repeatOn250DpdCount = 0L;
            Double totalOn250DpdAmount = 0.00;
            Double potentialOn250DpdAmount = 0.00;
            Double repeatOn250DpdAmount = 0.00;
            Long totalOn300DpdCount = 0L;
            Long potentialOn300DpdCount = 0L;
            Long repeatOn300DpdCount = 0L;
            Double totalOn300DpdAmount = 0.00;
            Double potentialOn300DpdAmount = 0.00;
            Double repeatOn300DpdAmount = 0.00;
            Long totalOn500DpdCount = 0L;
            Long potentialOn500DpdCount = 0L;
            Long repeatOn500DpdCount = 0L;
            Double totalOn500DpdAmount = 0.00;
            Double potentialOn500DpdAmount = 0.00;
            Double repeatOn500DpdAmount = 0.00;
            
            for (LoanInfoExpiredInfo item : loanInfoDateList) {
                if (dateMonth.equals(item.getDate()
                        .withDayOfMonth(item.getDate().getMonth()
                                .length(item.getDate().isLeapYear())))) {
                    if (item.getTotalIssuedLoanCount() != null) {
                        totalIssuedLoanCount += item.getTotalIssuedLoanCount();
                    }
                    if (item.getTotalIssuedLoanAmount() != null) {
                        totalIssuedLoanAmount += item.getTotalIssuedLoanAmount();
                    }
                    if (item.getPotentialIssuedLoanCount() != null) {
                        potentialIssuedLoanCount += item.getPotentialIssuedLoanCount();
                    }
                    if (item.getRepeatIssuedLoanCount() != null) {
                        repeatIssuedLoanCount += item.getRepeatIssuedLoanCount();
                    }
                    if (item.getPotentialIssuedLoanAmount() != null) {
                        potentialIssuedLoanAmount += item.getPotentialIssuedLoanAmount();
                    }
                    if (item.getRepeatIssuedLoanAmount() != null) {
                        repeatIssuedLoanAmount += item.getRepeatIssuedLoanAmount();
                    }
                    if (item.getTotalActiveLoanCount() != null) {
                        totalActiveLoanCount += item.getTotalActiveLoanCount();
                    }
                    if (item.getPotentialActiveLoanCount() != null) {
                        potentialActiveLoanCount += item.getPotentialActiveLoanCount();
                    }
                    if (item.getRepeatActiveLoanCount() != null) {
                        repeatActiveLoanCount += item.getRepeatActiveLoanCount();
                    }
                    if (item.getTotalOn2DpdCount() != null) {
                        totalOn2DpdCount += item.getTotalOn2DpdCount();
                    }
                    if (item.getPotentialOn2DpdCount() != null) {
                        potentialOn2DpdCount += item.getPotentialOn2DpdCount();
                    }
                    if (item.getRepeatOn2DpdCount() != null) {
                        repeatOn2DpdCount += item.getRepeatOn2DpdCount();
                    }
                    if (item.getTotalOn2DpdAmount() != null) {
                        totalOn2DpdAmount += item.getTotalOn2DpdAmount();
                    }
                    if (item.getPotentialOn2DpdAmount() != null) {
                        potentialOn2DpdAmount += item.getPotentialOn2DpdAmount();
                    }
                    if (item.getRepeatOn2DpdAmount() != null) {
                        repeatOn2DpdAmount += item.getRepeatOn2DpdAmount();
                    }
                    if (item.getTotalOn5DpdCount() != null) {
                        totalOn5DpdCount += item.getTotalOn5DpdCount();
                    }
                    if (item.getPotentialOn5DpdCount() != null) {
                        potentialOn5DpdCount += item.getPotentialOn5DpdCount();
                    }
                    if (item.getRepeatOn5DpdCount() != null) {
                        repeatOn5DpdCount += item.getRepeatOn5DpdCount();
                    }
                    if (item.getTotalOn5DpdAmount() != null) {
                        totalOn5DpdAmount += item.getTotalOn5DpdAmount();
                    }
                    if (item.getPotentialOn5DpdAmount() != null) {
                        potentialOn5DpdAmount += item.getPotentialOn5DpdAmount();
                    }
                    if (item.getRepeatOn5DpdAmount() != null) {
                        repeatOn5DpdAmount += item.getRepeatOn5DpdAmount();
                    }
                    if (item.getTotalOn10DpdCount() != null) {
                        totalOn10DpdCount += item.getTotalOn10DpdCount();
                    }
                    if (item.getPotentialOn10DpdCount() != null) {
                        potentialOn10DpdCount += item.getPotentialOn10DpdCount();
                    }
                    if (item.getRepeatOn10DpdCount() != null) {
                        repeatOn10DpdCount += item.getRepeatOn10DpdCount();
                    }
                    if (item.getTotalOn10DpdAmount() != null) {
                        totalOn10DpdAmount += item.getTotalOn10DpdAmount();
                    }
                    if (item.getPotentialOn10DpdAmount() != null) {
                        potentialOn10DpdAmount += item.getPotentialOn10DpdAmount();
                    }
                    if (item.getRepeatOn10DpdAmount() != null) {
                        repeatOn10DpdAmount += item.getRepeatOn10DpdAmount();
                    }
                    if (item.getTotalOn15DpdCount() != null) {
                        totalOn15DpdCount += item.getTotalOn15DpdCount();
                    }
                    if (item.getPotentialOn15DpdCount() != null) {
                        potentialOn15DpdCount += item.getPotentialOn15DpdCount();
                    }
                    if (item.getRepeatOn15DpdCount() != null) {
                        repeatOn15DpdCount += item.getRepeatOn15DpdCount();
                    }
                    if (item.getTotalOn15DpdAmount() != null) {
                        totalOn15DpdAmount += item.getTotalOn15DpdAmount();
                    }
                    if (item.getPotentialOn15DpdAmount() != null) {
                        potentialOn15DpdAmount += item.getPotentialOn15DpdAmount();
                    }
                    if (item.getRepeatOn15DpdAmount() != null) {
                        repeatOn15DpdAmount += item.getRepeatOn15DpdAmount();
                    }
                    if (item.getTotalOn20DpdCount() != null) {
                        totalOn20DpdCount += item.getTotalOn20DpdCount();
                    }
                    if (item.getPotentialOn20DpdCount() != null) {
                        potentialOn20DpdCount += item.getPotentialOn20DpdCount();
                    }
                    if (item.getRepeatOn20DpdCount() != null) {
                        repeatOn20DpdCount += item.getRepeatOn20DpdCount();
                    }
                    if (item.getTotalOn20DpdAmount() != null) {
                        totalOn20DpdAmount += item.getTotalOn20DpdAmount();
                    }
                    if (item.getPotentialOn20DpdAmount() != null) {
                        potentialOn20DpdAmount += item.getPotentialOn20DpdAmount();
                    }
                    if (item.getRepeatOn20DpdAmount() != null) {
                        repeatOn20DpdAmount += item.getRepeatOn20DpdAmount();
                    }
                    if (item.getTotalOn25DpdCount() != null) {
                        totalOn25DpdCount += item.getTotalOn25DpdCount();
                    }
                    if (item.getPotentialOn25DpdCount() != null) {
                        potentialOn25DpdCount += item.getPotentialOn25DpdCount();
                    }
                    if (item.getRepeatOn25DpdCount() != null) {
                        repeatOn25DpdCount += item.getRepeatOn25DpdCount();
                    }
                    if (item.getTotalOn25DpdAmount() != null) {
                        totalOn25DpdAmount += item.getTotalOn25DpdAmount();
                    }
                    if (item.getPotentialOn25DpdAmount() != null) {
                        potentialOn25DpdAmount += item.getPotentialOn25DpdAmount();
                    }
                    if (item.getRepeatOn25DpdAmount() != null) {
                        repeatOn25DpdAmount += item.getRepeatOn25DpdAmount();
                    }
                    if (item.getTotalOn30DpdCount() != null) {
                        totalOn30DpdCount += item.getTotalOn30DpdCount();
                    }
                    if (item.getPotentialOn30DpdCount() != null) {
                        potentialOn30DpdCount += item.getPotentialOn30DpdCount();
                    }
                    if (item.getRepeatOn30DpdCount() != null) {
                        repeatOn30DpdCount += item.getRepeatOn30DpdCount();
                    }
                    if (item.getTotalOn30DpdAmount() != null) {
                        totalOn30DpdAmount += item.getTotalOn30DpdAmount();
                    }
                    if (item.getPotentialOn30DpdAmount() != null) {
                        potentialOn30DpdAmount += item.getPotentialOn30DpdAmount();
                    }
                    if (item.getRepeatOn30DpdAmount() != null) {
                        repeatOn30DpdAmount += item.getRepeatOn30DpdAmount();
                    }
                    if (item.getTotalOn40DpdCount() != null) {
                        totalOn40DpdCount += item.getTotalOn40DpdCount();
                    }
                    if (item.getPotentialOn40DpdCount() != null) {
                        potentialOn40DpdCount += item.getPotentialOn40DpdCount();
                    }
                    if (item.getRepeatOn40DpdCount() != null) {
                        repeatOn40DpdCount += item.getRepeatOn40DpdCount();
                    }
                    if (item.getTotalOn40DpdAmount() != null) {
                        totalOn40DpdAmount += item.getTotalOn40DpdAmount();
                    }
                    if (item.getPotentialOn40DpdAmount() != null) {
                        potentialOn40DpdAmount += item.getPotentialOn40DpdAmount();
                    }
                    if (item.getRepeatOn40DpdAmount() != null) {
                        repeatOn40DpdAmount += item.getRepeatOn40DpdAmount();
                    }
                    if (item.getTotalOn50DpdCount() != null) {
                        totalOn50DpdCount += item.getTotalOn50DpdCount();
                    }
                    if (item.getPotentialOn50DpdCount() != null) {
                        potentialOn50DpdCount += item.getPotentialOn50DpdCount();
                    }
                    if (item.getRepeatOn50DpdCount() != null) {
                        repeatOn50DpdCount += item.getRepeatOn50DpdCount();
                    }
                    if (item.getTotalOn50DpdAmount() != null) {
                        totalOn50DpdAmount += item.getTotalOn50DpdAmount();
                    }
                    if (item.getPotentialOn50DpdAmount() != null) {
                        potentialOn50DpdAmount += item.getPotentialOn50DpdAmount();
                    }
                    if (item.getRepeatOn50DpdAmount() != null) {
                        repeatOn50DpdAmount += item.getRepeatOn50DpdAmount();
                    }
                    if (item.getTotalOn60DpdCount() != null) {
                        totalOn60DpdCount += item.getTotalOn60DpdCount();
                    }
                    if (item.getPotentialOn60DpdCount() != null) {
                        potentialOn60DpdCount += item.getPotentialOn60DpdCount();
                    }
                    if (item.getRepeatOn60DpdCount() != null) {
                        repeatOn60DpdCount += item.getRepeatOn60DpdCount();
                    }
                    if (item.getTotalOn60DpdAmount() != null) {
                        totalOn60DpdAmount += item.getTotalOn60DpdAmount();
                    }
                    if (item.getPotentialOn60DpdAmount() != null) {
                        potentialOn60DpdAmount += item.getPotentialOn60DpdAmount();
                    }
                    if (item.getRepeatOn60DpdAmount() != null) {
                        repeatOn60DpdAmount += item.getRepeatOn60DpdAmount();
                    }
                    if (item.getTotalOn90DpdCount() != null) {
                        totalOn90DpdCount += item.getTotalOn90DpdCount();
                    }
                    if (item.getPotentialOn90DpdCount() != null) {
                        potentialOn90DpdCount += item.getPotentialOn90DpdCount();
                    }
                    if (item.getRepeatOn90DpdCount() != null) {
                        repeatOn90DpdCount += item.getRepeatOn90DpdCount();
                    }
                    if (item.getTotalOn90DpdAmount() != null) {
                        totalOn90DpdAmount += item.getTotalOn90DpdAmount();
                    }
                    if (item.getPotentialOn90DpdAmount() != null) {
                        potentialOn90DpdAmount += item.getPotentialOn90DpdAmount();
                    }
                    if (item.getRepeatOn90DpdAmount() != null) {
                        repeatOn90DpdAmount += item.getRepeatOn90DpdAmount();
                    }
                    if (item.getTotalOn120DpdCount() != null) {
                        totalOn120DpdCount += item.getTotalOn120DpdCount();
                    }
                    if (item.getPotentialOn120DpdCount() != null) {
                        potentialOn120DpdCount += item.getPotentialOn120DpdCount();
                    }
                    if (item.getRepeatOn120DpdCount() != null) {
                        repeatOn120DpdCount += item.getRepeatOn120DpdCount();
                    }
                    if (item.getTotalOn120DpdAmount() != null) {
                        totalOn120DpdAmount += item.getTotalOn120DpdAmount();
                    }
                    if (item.getPotentialOn120DpdAmount() != null) {
                        potentialOn120DpdAmount += item.getPotentialOn120DpdAmount();
                    }
                    if (item.getRepeatOn120DpdAmount() != null) {
                        repeatOn120DpdAmount += item.getRepeatOn120DpdAmount();
                    }
                    if (item.getTotalOn150DpdCount() != null) {
                        totalOn150DpdCount += item.getTotalOn150DpdCount();
                    }
                    if (item.getPotentialOn150DpdCount() != null) {
                        potentialOn150DpdCount += item.getPotentialOn150DpdCount();
                    }
                    if (item.getRepeatOn150DpdCount() != null) {
                        repeatOn150DpdCount += item.getRepeatOn150DpdCount();
                    }
                    if (item.getTotalOn150DpdAmount() != null) {
                        totalOn150DpdAmount += item.getTotalOn150DpdAmount();
                    }
                    if (item.getPotentialOn150DpdAmount() != null) {
                        potentialOn150DpdAmount += item.getPotentialOn150DpdAmount();
                    }
                    if (item.getRepeatOn150DpdAmount() != null) {
                        repeatOn150DpdAmount += item.getRepeatOn150DpdAmount();
                    }
                    if (item.getTotalOn180DpdCount() != null) {
                        totalOn180DpdCount += item.getTotalOn180DpdCount();
                    }
                    if (item.getPotentialOn180DpdCount() != null) {
                        potentialOn180DpdCount += item.getPotentialOn180DpdCount();
                    }
                    if (item.getRepeatOn180DpdCount() != null) {
                        repeatOn180DpdCount += item.getRepeatOn180DpdCount();
                    }
                    if (item.getTotalOn180DpdAmount() != null) {
                        totalOn180DpdAmount += item.getTotalOn180DpdAmount();
                    }
                    if (item.getPotentialOn180DpdAmount() != null) {
                        potentialOn180DpdAmount += item.getPotentialOn180DpdAmount();
                    }
                    if (item.getRepeatOn180DpdAmount() != null) {
                        repeatOn180DpdAmount += item.getRepeatOn180DpdAmount();
                    }
                    if (item.getTotalOn250DpdCount() != null) {
                        totalOn250DpdCount += item.getTotalOn250DpdCount();
                    }
                    if (item.getPotentialOn250DpdCount() != null) {
                        potentialOn250DpdCount += item.getPotentialOn250DpdCount();
                    }
                    if (item.getRepeatOn250DpdCount() != null) {
                        repeatOn250DpdCount += item.getRepeatOn250DpdCount();
                    }
                    if (item.getTotalOn250DpdAmount() != null) {
                        totalOn250DpdAmount += item.getTotalOn250DpdAmount();
                    }
                    if (item.getPotentialOn250DpdAmount() != null) {
                        potentialOn250DpdAmount += item.getPotentialOn250DpdAmount();
                    }
                    if (item.getRepeatOn250DpdAmount() != null) {
                        repeatOn250DpdAmount += item.getRepeatOn250DpdAmount();
                    }
                    if (item.getTotalOn300DpdCount() != null) {
                        totalOn300DpdCount += item.getTotalOn300DpdCount();
                    }
                    if (item.getPotentialOn300DpdCount() != null) {
                        potentialOn300DpdCount += item.getPotentialOn300DpdCount();
                    }
                    if (item.getRepeatOn300DpdCount() != null) {
                        repeatOn300DpdCount += item.getRepeatOn300DpdCount();
                    }
                    if (item.getTotalOn300DpdAmount() != null) {
                        totalOn300DpdAmount += item.getTotalOn300DpdAmount();
                    }
                    if (item.getPotentialOn300DpdAmount() != null) {
                        potentialOn300DpdAmount += item.getPotentialOn300DpdAmount();
                    }
                    if (item.getRepeatOn300DpdAmount() != null) {
                        repeatOn300DpdAmount += item.getRepeatOn300DpdAmount();
                    }
                    if (item.getTotalOn500DpdCount() != null) {
                        totalOn500DpdCount += item.getTotalOn500DpdCount();
                    }
                    if (item.getPotentialOn500DpdCount() != null) {
                        potentialOn500DpdCount += item.getPotentialOn500DpdCount();
                    }
                    if (item.getRepeatOn500DpdCount() != null) {
                        repeatOn500DpdCount += item.getRepeatOn500DpdCount();
                    }
                    if (item.getTotalOn500DpdAmount() != null) {
                        totalOn500DpdAmount += item.getTotalOn500DpdAmount();
                    }
                    if (item.getPotentialOn500DpdAmount() != null) {
                        potentialOn500DpdAmount += item.getPotentialOn500DpdAmount();
                    }
                    if (item.getRepeatOn500DpdAmount() != null) {
                        repeatOn500DpdAmount += item.getRepeatOn500DpdAmount();
                    }
                }
            }

            loanInfo.setTotalIssuedLoanCount(totalIssuedLoanCount);
            loanInfo.setTotalIssuedLoanAmount(totalIssuedLoanAmount);
            loanInfo.setPotentialIssuedLoanCount(potentialIssuedLoanCount);
            loanInfo.setRepeatIssuedLoanCount(repeatIssuedLoanCount);
            loanInfo.setPotentialIssuedLoanAmount(potentialIssuedLoanAmount);
            loanInfo.setRepeatIssuedLoanAmount(repeatIssuedLoanAmount);
            loanInfo.setTotalActiveLoanCount(totalActiveLoanCount);
            loanInfo.setPotentialActiveLoanCount(potentialActiveLoanCount);
            loanInfo.setRepeatActiveLoanCount(repeatActiveLoanCount);
            loanInfo.setTotalOn2DpdCount(totalOn2DpdCount);
            loanInfo.setPotentialOn2DpdCount(potentialOn2DpdCount);
            loanInfo.setRepeatOn2DpdCount(repeatOn2DpdCount);
            loanInfo.setTotalOn2DpdAmount(totalOn2DpdAmount);
            loanInfo.setPotentialOn2DpdAmount(potentialOn2DpdAmount);
            loanInfo.setRepeatOn2DpdAmount(repeatOn2DpdAmount);
            loanInfo.setTotalOn5DpdCount(totalOn5DpdCount);
            loanInfo.setPotentialOn5DpdCount(potentialOn5DpdCount);
            loanInfo.setRepeatOn5DpdCount(repeatOn5DpdCount);
            loanInfo.setTotalOn5DpdAmount(totalOn5DpdAmount);
            loanInfo.setPotentialOn5DpdAmount(potentialOn5DpdAmount);
            loanInfo.setRepeatOn5DpdAmount(repeatOn5DpdAmount);
            loanInfo.setTotalOn10DpdCount(totalOn10DpdCount);
            loanInfo.setPotentialOn10DpdCount(potentialOn10DpdCount);
            loanInfo.setRepeatOn10DpdCount(repeatOn10DpdCount);
            loanInfo.setTotalOn10DpdAmount(totalOn10DpdAmount);
            loanInfo.setPotentialOn10DpdAmount(potentialOn10DpdAmount);
            loanInfo.setRepeatOn10DpdAmount(repeatOn10DpdAmount);
            loanInfo.setTotalOn15DpdCount(totalOn15DpdCount);
            loanInfo.setPotentialOn15DpdCount(potentialOn15DpdCount);
            loanInfo.setRepeatOn15DpdCount(repeatOn15DpdCount);
            loanInfo.setTotalOn15DpdAmount(totalOn15DpdAmount);
            loanInfo.setPotentialOn15DpdAmount(potentialOn15DpdAmount);
            loanInfo.setRepeatOn15DpdAmount(repeatOn15DpdAmount);
            loanInfo.setTotalOn20DpdCount(totalOn20DpdCount);
            loanInfo.setPotentialOn20DpdCount(potentialOn20DpdCount);
            loanInfo.setRepeatOn20DpdCount(repeatOn20DpdCount);
            loanInfo.setTotalOn20DpdAmount(totalOn20DpdAmount);
            loanInfo.setPotentialOn20DpdAmount(potentialOn20DpdAmount);
            loanInfo.setRepeatOn20DpdAmount(repeatOn20DpdAmount);
            loanInfo.setTotalOn25DpdCount(totalOn25DpdCount);
            loanInfo.setPotentialOn25DpdCount(potentialOn25DpdCount);
            loanInfo.setRepeatOn25DpdCount(repeatOn25DpdCount);
            loanInfo.setTotalOn25DpdAmount(totalOn25DpdAmount);
            loanInfo.setPotentialOn25DpdAmount(potentialOn25DpdAmount);
            loanInfo.setRepeatOn25DpdAmount(repeatOn25DpdAmount);
            loanInfo.setTotalOn30DpdCount(totalOn30DpdCount);
            loanInfo.setPotentialOn30DpdCount(potentialOn30DpdCount);
            loanInfo.setRepeatOn30DpdCount(repeatOn30DpdCount);
            loanInfo.setTotalOn30DpdAmount(totalOn30DpdAmount);
            loanInfo.setPotentialOn30DpdAmount(potentialOn30DpdAmount);
            loanInfo.setRepeatOn30DpdAmount(repeatOn30DpdAmount);
            loanInfo.setTotalOn40DpdCount(totalOn40DpdCount);
            loanInfo.setPotentialOn40DpdCount(potentialOn40DpdCount);
            loanInfo.setRepeatOn40DpdCount(repeatOn40DpdCount);
            loanInfo.setTotalOn40DpdAmount(totalOn40DpdAmount);
            loanInfo.setPotentialOn40DpdAmount(potentialOn40DpdAmount);
            loanInfo.setRepeatOn40DpdAmount(repeatOn40DpdAmount);
            loanInfo.setTotalOn50DpdCount(totalOn50DpdCount);
            loanInfo.setPotentialOn50DpdCount(potentialOn50DpdCount);
            loanInfo.setRepeatOn50DpdCount(repeatOn50DpdCount);
            loanInfo.setTotalOn50DpdAmount(totalOn50DpdAmount);
            loanInfo.setPotentialOn50DpdAmount(potentialOn50DpdAmount);
            loanInfo.setRepeatOn50DpdAmount(repeatOn50DpdAmount);
            loanInfo.setTotalOn60DpdCount(totalOn60DpdCount);
            loanInfo.setPotentialOn60DpdCount(potentialOn60DpdCount);
            loanInfo.setRepeatOn60DpdCount(repeatOn60DpdCount);
            loanInfo.setTotalOn60DpdAmount(totalOn60DpdAmount);
            loanInfo.setPotentialOn60DpdAmount(potentialOn60DpdAmount);
            loanInfo.setRepeatOn60DpdAmount(repeatOn60DpdAmount);
            loanInfo.setTotalOn90DpdCount(totalOn90DpdCount);
            loanInfo.setPotentialOn90DpdCount(potentialOn90DpdCount);
            loanInfo.setRepeatOn90DpdCount(repeatOn90DpdCount);
            loanInfo.setTotalOn90DpdAmount(totalOn90DpdAmount);
            loanInfo.setPotentialOn90DpdAmount(potentialOn90DpdAmount);
            loanInfo.setRepeatOn90DpdAmount(repeatOn90DpdAmount);
            loanInfo.setTotalOn120DpdCount(totalOn120DpdCount);
            loanInfo.setPotentialOn120DpdCount(potentialOn120DpdCount);
            loanInfo.setRepeatOn120DpdCount(repeatOn120DpdCount);
            loanInfo.setTotalOn120DpdAmount(totalOn120DpdAmount);
            loanInfo.setPotentialOn120DpdAmount(potentialOn120DpdAmount);
            loanInfo.setRepeatOn120DpdAmount(repeatOn120DpdAmount);
            loanInfo.setTotalOn150DpdCount(totalOn150DpdCount);
            loanInfo.setPotentialOn150DpdCount(potentialOn150DpdCount);
            loanInfo.setRepeatOn150DpdCount(repeatOn150DpdCount);
            loanInfo.setTotalOn150DpdAmount(totalOn150DpdAmount);
            loanInfo.setPotentialOn150DpdAmount(potentialOn150DpdAmount);
            loanInfo.setRepeatOn150DpdAmount(repeatOn150DpdAmount);
            loanInfo.setTotalOn180DpdCount(totalOn180DpdCount);
            loanInfo.setPotentialOn180DpdCount(potentialOn180DpdCount);
            loanInfo.setRepeatOn180DpdCount(repeatOn180DpdCount);
            loanInfo.setTotalOn180DpdAmount(totalOn180DpdAmount);
            loanInfo.setPotentialOn180DpdAmount(potentialOn180DpdAmount);
            loanInfo.setRepeatOn180DpdAmount(repeatOn180DpdAmount);
            loanInfo.setTotalOn250DpdCount(totalOn250DpdCount);
            loanInfo.setPotentialOn250DpdCount(potentialOn250DpdCount);
            loanInfo.setRepeatOn250DpdCount(repeatOn250DpdCount);
            loanInfo.setTotalOn250DpdAmount(totalOn250DpdAmount);
            loanInfo.setPotentialOn250DpdAmount(potentialOn250DpdAmount);
            loanInfo.setRepeatOn250DpdAmount(repeatOn250DpdAmount);
            loanInfo.setTotalOn300DpdCount(totalOn300DpdCount);
            loanInfo.setPotentialOn300DpdCount(potentialOn300DpdCount);
            loanInfo.setRepeatOn300DpdCount(repeatOn300DpdCount);
            loanInfo.setTotalOn300DpdAmount(totalOn300DpdAmount);
            loanInfo.setPotentialOn300DpdAmount(potentialOn300DpdAmount);
            loanInfo.setRepeatOn300DpdAmount(repeatOn300DpdAmount);
            loanInfo.setTotalOn500DpdCount(totalOn500DpdCount);
            loanInfo.setPotentialOn500DpdCount(potentialOn500DpdCount);
            loanInfo.setRepeatOn500DpdCount(repeatOn500DpdCount);
            loanInfo.setTotalOn500DpdAmount(totalOn500DpdAmount);
            loanInfo.setPotentialOn500DpdAmount(potentialOn500DpdAmount);
            loanInfo.setRepeatOn500DpdAmount(repeatOn500DpdAmount);
            loanInfoMonthList.add(loanInfo);
        }
        return loanInfoMonthList;
    }

    private List<LocalDate> getMonthList (List<LocalDate> dateList) {
        Set<LocalDate> months = new HashSet<>();
        for (LocalDate date : dateList) {
            months.add(date.withDayOfMonth(date.getMonth().length(date.isLeapYear())));
        }
        List<LocalDate> allMonths = new ArrayList<>(months);
        Collections.sort(allMonths);
        return allMonths;
    }

    private List<LocalDate> getDateList (List<LoanInfoExpiredInfo> loanInfoDateList) {
        Set<LocalDate> dates = new HashSet<>();
        for (LoanInfoExpiredInfo item : loanInfoDateList) {
            dates.add(item.getDate());
        }
        List<LocalDate> allDates = new ArrayList<>(dates);
        Collections.sort(allDates);
        return allDates;
    }

}
