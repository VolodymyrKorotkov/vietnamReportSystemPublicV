package com.korotkov.creditCRM.model.mainDailyReport;

import com.korotkov.mainCurrentApp.config.ConfigConstants;
import com.korotkov.mainCurrentApp.model.CurrencyRate;
import com.korotkov.mainCurrentApp.model.ModelPlan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class MainDailyReportObject implements ConfigConstants {

    private LocalDateTime createdAt;
    private LocalDate reportDateFrom;
    private LocalDate reportDateTo;
    private List<ExportApplicationsInfoDate> exportApplicationsInfoDateList;
    private List<ExportCollectionDebtsInfoDate> exportCollectionDebtsInfoDateList;
    private List<ExportLoansInfoDate> exportLoansInfoDateList;
    private List<ExportPaymentsInfoDate> exportPaymentsInfoDateList;
    private List<ExportProlongationsInfoDate> exportProlongationsInfoDateList;

    private List<ExportApplicationsInfoDate> exportApplicationsInfoMonthList;
    private List<ExportCollectionDebtsInfoDate> exportCollectionDebtsInfoMonthList;
    private List<ExportLoansInfoDate> exportLoansInfoMonthList;
    private List<ExportPaymentsInfoDate> exportPaymentsInfoMonthList;
    private List<ExportProlongationsInfoDate> exportProlongationsInfoMonthList;

    private List<LocalDate> dateList;

    private List<ModelPlan> modelPlanList;

    private List<LocalDate> monthList;

    private List<CurrencyRate> currencyRateList;

    private Map<LocalDate, Double> mapMonthAndAverageCurrencyRateUsdVnd;

    public MainDailyReportObject() {}

    public MainDailyReportObject(LocalDate reportDateFrom, LocalDate reportDateTo,
                                 List<ExportApplicationsInfoDate> applicationsInfoDateList,
                                 List<ExportCollectionDebtsInfoDate> collectionDebtsInfoDateList,
                                 List<ExportLoansInfoDate> loansInfoDateList,
                                 List<ExportPaymentsInfoDate> paymentsInfoDateList,
                                 List<ExportProlongationsInfoDate> prolongationsInfoDateList,
                                 List<ModelPlan> modelPlanList,
                                 List<CurrencyRate> currencyRateList) {
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        this.reportDateFrom = reportDateFrom;
        this.reportDateTo = reportDateTo;
        this.exportApplicationsInfoDateList = applicationsInfoDateList;
        this.exportCollectionDebtsInfoDateList = collectionDebtsInfoDateList;
        this.exportLoansInfoDateList = loansInfoDateList;
        this.exportPaymentsInfoDateList = paymentsInfoDateList;
        this.exportProlongationsInfoDateList = prolongationsInfoDateList;
        this.modelPlanList = modelPlanList;
        this.currencyRateList = currencyRateList;
        this.dateList = getDateList(this.exportApplicationsInfoDateList,
                this.exportLoansInfoDateList,
                this.exportPaymentsInfoDateList,
                this.exportProlongationsInfoDateList);
        this.monthList = getMonthList(this.dateList);
        this.mapMonthAndAverageCurrencyRateUsdVnd = getMapAverageMonthCurrencyRateUsdVnd(this.currencyRateList, this.monthList);
        this.exportApplicationsInfoMonthList = getMonthApplicationsInfoDateList(this.monthList, this.exportApplicationsInfoDateList);
        this.exportCollectionDebtsInfoMonthList = getMonthCollectionDebtsInfoDateList(this.monthList, this.exportCollectionDebtsInfoDateList);
        this.exportLoansInfoMonthList = getMonthLoansInfoDateList(this.monthList, this.exportLoansInfoDateList);
        this.exportPaymentsInfoMonthList = getMonthPaymentsInfoDateList(this.monthList, this.exportPaymentsInfoDateList);
        this.exportProlongationsInfoMonthList = getMonthProlongationsInfoDateList(this.monthList, this.exportProlongationsInfoDateList);
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

    private List<LocalDate> getMonthList (List<LocalDate> dateList) {
        Set<LocalDate> months = new HashSet<>();
        for (int a = 0; a < dateList.size(); a++) {
            months.add(dateList.get(a).withDayOfMonth(dateList.get(a).getMonth().length(dateList.get(a).isLeapYear())));
        }
        List<LocalDate> allMonths = new ArrayList<>(months);
        Collections.sort(allMonths);
        return allMonths;
    }

    private List<ExportProlongationsInfoDate> getMonthProlongationsInfoDateList(List<LocalDate> monthList,
                                                                                List<ExportProlongationsInfoDate> prolongationsInfoDateList) {
        List<ExportProlongationsInfoDate> prolongationsInfoMonthList = new ArrayList<>();
        for (LocalDate date : monthList) {
            ExportProlongationsInfoDate exportProlongationsInfoDate = new ExportProlongationsInfoDate();
            exportProlongationsInfoDate.setDate(date);
            Long countProlonged = 0L;
            Double principalAmountProlonged = 0.00;
            Long countProlongedNew = 0L;
            Double principalAmountProlongedNew = 0.00;
            Long countProlongedRepeat = 0L;
            Double principalAmountProlongedRepeat = 0.00;
            for (int a = 0; a < prolongationsInfoDateList.size(); a++) {
                if (date.equals(prolongationsInfoDateList.get(a).getDate()
                        .withDayOfMonth(prolongationsInfoDateList.get(a).getDate().getMonth()
                                .length(prolongationsInfoDateList.get(a).getDate().isLeapYear())))) {
                    countProlonged += prolongationsInfoDateList.get(a).getCountProlonged();
                    principalAmountProlonged += prolongationsInfoDateList.get(a).getPrincipalAmountProlonged();
                    countProlongedNew += prolongationsInfoDateList.get(a).getCountProlongedNew();
                    principalAmountProlongedNew += prolongationsInfoDateList.get(a).getPrincipalAmountProlongedNew();
                    countProlongedRepeat += prolongationsInfoDateList.get(a).getCountProlongedRepeat();
                    principalAmountProlongedRepeat += prolongationsInfoDateList.get(a).getPrincipalAmountProlongedRepeat();
                }
            }
            exportProlongationsInfoDate.setCountProlonged(countProlonged);
            exportProlongationsInfoDate.setPrincipalAmountProlonged(principalAmountProlonged);
            exportProlongationsInfoDate.setCountProlongedNew(countProlongedNew);
            exportProlongationsInfoDate.setPrincipalAmountProlongedNew(principalAmountProlongedNew);
            exportProlongationsInfoDate.setCountProlongedRepeat(countProlongedRepeat);
            exportProlongationsInfoDate.setPrincipalAmountProlongedRepeat(principalAmountProlongedRepeat);
            prolongationsInfoMonthList.add(exportProlongationsInfoDate);
        }
        return prolongationsInfoMonthList;
    }

    private List<ExportPaymentsInfoDate> getMonthPaymentsInfoDateList(List<LocalDate> monthList,
                                                                      List<ExportPaymentsInfoDate> paymentsInfoDateList) {
        List<ExportPaymentsInfoDate> paymentsInfoMonthList = new ArrayList<>();
        for (LocalDate date : monthList) {
            ExportPaymentsInfoDate exportPaymentsInfoDate = new ExportPaymentsInfoDate();
            exportPaymentsInfoDate.setDate(date);
            Double paidTotal = 0.00;
            Double paidPrincipal = 0.00;
            Double paidIncome = 0.00;
            Double paidTotalNew = 0.00;
            Double paidPrincipalNew = 0.00;
            Double paidIncomeNew = 0.00;
            Double paidTotalRepeat = 0.00;
            Double paidPrincipalRepeat = 0.00;
            Double paidIncomeRepeat = 0.00;
            for (int a = 0; a < paymentsInfoDateList.size(); a++) {
                if (date.equals(paymentsInfoDateList.get(a).getDate()
                        .withDayOfMonth(paymentsInfoDateList.get(a).getDate().getMonth()
                                .length(paymentsInfoDateList.get(a).getDate().isLeapYear())))) {
                    paidTotal += paymentsInfoDateList.get(a).getPaidTotal();
                    paidPrincipal += paymentsInfoDateList.get(a).getPaidPrincipal();
                    paidIncome += paymentsInfoDateList.get(a).getPaidIncome();
                    paidTotalNew += paymentsInfoDateList.get(a).getPaidTotalNew();
                    paidPrincipalNew += paymentsInfoDateList.get(a).getPaidPrincipalNew();
                    paidIncomeNew += paymentsInfoDateList.get(a).getPaidIncomeNew();
                    paidTotalRepeat += paymentsInfoDateList.get(a).getPaidTotalRepeat();
                    paidPrincipalRepeat += paymentsInfoDateList.get(a).getPaidPrincipalRepeat();
                    paidIncomeRepeat += paymentsInfoDateList.get(a).getPaidIncomeRepeat();
                }
            }
            exportPaymentsInfoDate.setPaidTotal(paidTotal);
            exportPaymentsInfoDate.setPaidPrincipal(paidPrincipal);
            exportPaymentsInfoDate.setPaidIncome(paidIncome);
            exportPaymentsInfoDate.setPaidTotalNew(paidTotalNew);
            exportPaymentsInfoDate.setPaidPrincipalNew(paidPrincipalNew);
            exportPaymentsInfoDate.setPaidIncomeNew(paidIncomeNew);
            exportPaymentsInfoDate.setPaidTotalRepeat(paidTotalRepeat);
            exportPaymentsInfoDate.setPaidPrincipalRepeat(paidPrincipalRepeat);
            exportPaymentsInfoDate.setPaidIncomeRepeat(paidIncomeRepeat);
            paymentsInfoMonthList.add(exportPaymentsInfoDate);
        }
        return paymentsInfoMonthList;
    }

    private List<ExportLoansInfoDate> getMonthLoansInfoDateList(List<LocalDate> monthList,
                                                                List<ExportLoansInfoDate> loansInfoDateList) {
        List<ExportLoansInfoDate> loansInfoMonthList = new ArrayList<>();
        for (LocalDate date : monthList) {
            ExportLoansInfoDate exportLoansInfoDate = new ExportLoansInfoDate();
            exportLoansInfoDate.setDate(date);
            Long countLoan = 0L;
            Double amountLoan = 0.00;
            Long countLoanNew = 0L;
            Double amountLoanNew = 0.00;
            Long countLoanRepeat = 0L;
            Double amountLoanRepeat = 0.00;
            for (int a = 0; a < loansInfoDateList.size(); a++) {
                if (date.equals(loansInfoDateList.get(a).getDate()
                        .withDayOfMonth(loansInfoDateList.get(a).getDate().getMonth()
                                .length(loansInfoDateList.get(a).getDate().isLeapYear())))) {
                    countLoan += loansInfoDateList.get(a).getCountLoan();
                    amountLoan += loansInfoDateList.get(a).getAmountLoan();
                    countLoanNew += loansInfoDateList.get(a).getCountLoanNew();
                    amountLoanNew += loansInfoDateList.get(a).getAmountLoanNew();
                    countLoanRepeat += loansInfoDateList.get(a).getCountLoanRepeat();
                    amountLoanRepeat += loansInfoDateList.get(a).getAmountLoanRepeat();
                }
            }
            exportLoansInfoDate.setCountLoan(countLoan);
            exportLoansInfoDate.setAmountLoan(amountLoan);
            exportLoansInfoDate.setCountLoanNew(countLoanNew);
            exportLoansInfoDate.setAmountLoanNew(amountLoanNew);
            exportLoansInfoDate.setCountLoanRepeat(countLoanRepeat);
            exportLoansInfoDate.setAmountLoanRepeat(amountLoanRepeat);
            loansInfoMonthList.add(exportLoansInfoDate);
        }
        return loansInfoMonthList;
    }

    private List<ExportCollectionDebtsInfoDate> getMonthCollectionDebtsInfoDateList(List<LocalDate> monthList,
                                                                                    List<ExportCollectionDebtsInfoDate> collectionDebtsInfoDateList) {
        List<ExportCollectionDebtsInfoDate> collectionDebtsInfoMonthList = new ArrayList<>();
        for (LocalDate date : monthList) {
            ExportCollectionDebtsInfoDate collectionDebtsInfoDate = new ExportCollectionDebtsInfoDate();
            collectionDebtsInfoDate.setDate(date);
            Double totalIssuedAmount = 0.00;
            Double principalRepaid = 0.00;
            Double percentRepaid = 0.00;
            Double currentDebtPrincipal = 0.00;
            Double gracePeriodAmount = 0.00;
            Double expiredAmountTo30Days = 0.00;
            Double expiredAmountFrom31To60Days = 0.00;
            Double expiredAmountFrom61To90Days = 0.00;
            Double expiredAmountFrom91To120Days = 0.00;
            Double expiredAmountFrom121To180Days = 0.00;
            Double expiredAmountFrom181Days = 0.00;
            Double expiredAmountTotal = 0.00;
            Double totalIssuedAmountNew = 0.00;
            Double principalRepaidNew = 0.00;
            Double percentRepaidNew = 0.00;
            Double currentDebtPrincipalNew = 0.00;
            Double gracePeriodAmountNew = 0.00;
            Double expiredAmountTo30DaysNew = 0.00;
            Double expiredAmountFrom31To60DaysNew = 0.00;
            Double expiredAmountFrom61To90DaysNew = 0.00;
            Double expiredAmountFrom91To120DaysNew = 0.00;
            Double expiredAmountFrom121To180DaysNew = 0.00;
            Double expiredAmountFrom181DaysNew = 0.00;
            Double expiredAmountTotalNew = 0.00;
            Double totalIssuedAmountRepeat = 0.00;
            Double principalRepaidRepeat = 0.00;
            Double percentRepaidRepeat = 0.00;
            Double currentDebtPrincipalRepeat = 0.00;
            Double gracePeriodAmountRepeat = 0.00;
            Double expiredAmountTo30DaysRepeat = 0.00;
            Double expiredAmountFrom31To60DaysRepeat = 0.00;
            Double expiredAmountFrom61To90DaysRepeat = 0.00;
            Double expiredAmountFrom91To120DaysRepeat = 0.00;
            Double expiredAmountFrom121To180DaysRepeat = 0.00;
            Double expiredAmountFrom181DaysRepeat = 0.00;
            Double expiredAmountTotalRepeat = 0.00;
            for (int a = 0; a < collectionDebtsInfoDateList.size(); a++) {
                if (date.equals(collectionDebtsInfoDateList.get(a).getDate()
                        .withDayOfMonth(collectionDebtsInfoDateList.get(a).getDate().getMonth()
                                .length(collectionDebtsInfoDateList.get(a).getDate().isLeapYear())))) {
                    totalIssuedAmount += collectionDebtsInfoDateList.get(a).getTotalIssuedAmount();
                    principalRepaid += collectionDebtsInfoDateList.get(a).getPrincipalRepaid();
                    percentRepaid += collectionDebtsInfoDateList.get(a).getPercentRepaid();
                    currentDebtPrincipal += collectionDebtsInfoDateList.get(a).getCurrentDebtPrincipal();
                    gracePeriodAmount += collectionDebtsInfoDateList.get(a).getGracePeriodAmount();
                    expiredAmountTo30Days += collectionDebtsInfoDateList.get(a).getExpiredAmountTo30Days();
                    expiredAmountFrom31To60Days += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom31To60Days();
                    expiredAmountFrom61To90Days += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom61To90Days();
                    expiredAmountFrom91To120Days += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom91To120Days();
                    expiredAmountFrom121To180Days += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom121To180Days();
                    expiredAmountFrom181Days += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom181Days();
                    expiredAmountTotal += collectionDebtsInfoDateList.get(a).getExpiredAmountTotal();
                    totalIssuedAmountNew += collectionDebtsInfoDateList.get(a).getTotalIssuedAmountNew();
                    principalRepaidNew += collectionDebtsInfoDateList.get(a).getPrincipalRepaidNew();
                    percentRepaidNew += collectionDebtsInfoDateList.get(a).getPercentRepaidNew();
                    currentDebtPrincipalNew += collectionDebtsInfoDateList.get(a).getCurrentDebtPrincipalNew();
                    gracePeriodAmountNew += collectionDebtsInfoDateList.get(a).getGracePeriodAmountNew();
                    expiredAmountTo30DaysNew += collectionDebtsInfoDateList.get(a).getExpiredAmountTo30DaysNew();
                    expiredAmountFrom31To60DaysNew += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom31To60DaysNew();
                    expiredAmountFrom61To90DaysNew += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom61To90DaysNew();
                    expiredAmountFrom91To120DaysNew += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom91To120DaysNew();
                    expiredAmountFrom121To180DaysNew += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom121To180DaysNew();
                    expiredAmountFrom181DaysNew += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom181DaysNew();
                    expiredAmountTotalNew += collectionDebtsInfoDateList.get(a).getExpiredAmountTotalNew();
                    totalIssuedAmountRepeat += collectionDebtsInfoDateList.get(a).getTotalIssuedAmountRepeat();
                    principalRepaidRepeat += collectionDebtsInfoDateList.get(a).getPrincipalRepaidRepeat();
                    percentRepaidRepeat += collectionDebtsInfoDateList.get(a).getPercentRepaidRepeat();
                    currentDebtPrincipalRepeat += collectionDebtsInfoDateList.get(a).getCurrentDebtPrincipalRepeat();
                    gracePeriodAmountRepeat += collectionDebtsInfoDateList.get(a).getGracePeriodAmountRepeat();
                    expiredAmountTo30DaysRepeat += collectionDebtsInfoDateList.get(a).getExpiredAmountTo30DaysRepeat();
                    expiredAmountFrom31To60DaysRepeat += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom31To60DaysRepeat();
                    expiredAmountFrom61To90DaysRepeat += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom61To90DaysRepeat();
                    expiredAmountFrom91To120DaysRepeat += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom91To120DaysRepeat();
                    expiredAmountFrom121To180DaysRepeat += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom121To180DaysRepeat();
                    expiredAmountFrom181DaysRepeat += collectionDebtsInfoDateList.get(a).getExpiredAmountFrom181DaysRepeat();
                    expiredAmountTotalRepeat += collectionDebtsInfoDateList.get(a).getExpiredAmountTotalRepeat();
                }
            }
            collectionDebtsInfoDate.setTotalIssuedAmount(totalIssuedAmount);
            collectionDebtsInfoDate.setPrincipalRepaid(principalRepaid);
            collectionDebtsInfoDate.setPercentRepaid(percentRepaid);
            collectionDebtsInfoDate.setCurrentDebtPrincipal(currentDebtPrincipal);
            collectionDebtsInfoDate.setGracePeriodAmount(gracePeriodAmount);
            collectionDebtsInfoDate.setExpiredAmountTo30Days(expiredAmountTo30Days);
            collectionDebtsInfoDate.setExpiredAmountFrom31To60Days(expiredAmountFrom31To60Days);
            collectionDebtsInfoDate.setExpiredAmountFrom61To90Days(expiredAmountFrom61To90Days);
            collectionDebtsInfoDate.setExpiredAmountFrom91To120Days(expiredAmountFrom91To120Days);
            collectionDebtsInfoDate.setExpiredAmountFrom121To180Days(expiredAmountFrom121To180Days);
            collectionDebtsInfoDate.setExpiredAmountFrom181Days(expiredAmountFrom181Days);
            collectionDebtsInfoDate.setExpiredAmountTotal(expiredAmountTotal);
            collectionDebtsInfoDate.setTotalIssuedAmountNew(totalIssuedAmountNew);
            collectionDebtsInfoDate.setPrincipalRepaidNew(principalRepaidNew);
            collectionDebtsInfoDate.setPercentRepaidNew(percentRepaidNew);
            collectionDebtsInfoDate.setCurrentDebtPrincipalNew(currentDebtPrincipalNew);
            collectionDebtsInfoDate.setGracePeriodAmountNew(gracePeriodAmountNew);
            collectionDebtsInfoDate.setExpiredAmountTo30DaysNew(expiredAmountTo30DaysNew);
            collectionDebtsInfoDate.setExpiredAmountFrom31To60DaysNew(expiredAmountFrom31To60DaysNew);
            collectionDebtsInfoDate.setExpiredAmountFrom61To90DaysNew(expiredAmountFrom61To90DaysNew);
            collectionDebtsInfoDate.setExpiredAmountFrom91To120DaysNew(expiredAmountFrom91To120DaysNew);
            collectionDebtsInfoDate.setExpiredAmountFrom121To180DaysNew(expiredAmountFrom121To180DaysNew);
            collectionDebtsInfoDate.setExpiredAmountFrom181DaysNew(expiredAmountFrom181DaysNew);
            collectionDebtsInfoDate.setExpiredAmountTotalNew(expiredAmountTotalNew);
            collectionDebtsInfoDate.setTotalIssuedAmountRepeat(totalIssuedAmountRepeat);
            collectionDebtsInfoDate.setPrincipalRepaidRepeat(principalRepaidRepeat);
            collectionDebtsInfoDate.setPercentRepaidRepeat(percentRepaidRepeat);
            collectionDebtsInfoDate.setCurrentDebtPrincipalRepeat(currentDebtPrincipalRepeat);
            collectionDebtsInfoDate.setGracePeriodAmountRepeat(gracePeriodAmountRepeat);
            collectionDebtsInfoDate.setExpiredAmountTo30DaysRepeat(expiredAmountTo30DaysRepeat);
            collectionDebtsInfoDate.setExpiredAmountFrom31To60DaysRepeat(expiredAmountFrom31To60DaysRepeat);
            collectionDebtsInfoDate.setExpiredAmountFrom61To90DaysRepeat(expiredAmountFrom61To90DaysRepeat);
            collectionDebtsInfoDate.setExpiredAmountFrom91To120DaysRepeat(expiredAmountFrom91To120DaysRepeat);
            collectionDebtsInfoDate.setExpiredAmountFrom121To180DaysRepeat(expiredAmountFrom121To180DaysRepeat);
            collectionDebtsInfoDate.setExpiredAmountFrom181DaysRepeat(expiredAmountFrom181DaysRepeat);
            collectionDebtsInfoDate.setExpiredAmountTotalRepeat(expiredAmountTotalRepeat);
            collectionDebtsInfoMonthList.add(collectionDebtsInfoDate);
        }
        return collectionDebtsInfoMonthList;
    }

    private List<ExportApplicationsInfoDate> getMonthApplicationsInfoDateList(List<LocalDate> monthList,
                                                                              List<ExportApplicationsInfoDate> applicationsInfoDateList) {
        List<ExportApplicationsInfoDate> applicationsInfoMonthList = new ArrayList<>();
        for (LocalDate date : monthList) {
            ExportApplicationsInfoDate applicationsInfoDate = new ExportApplicationsInfoDate();
            applicationsInfoDate.setDate(date);
            Long countAppsTotal = 0L;
            Long countAppsInProgress = 0L;
            Long countAppsCanceledBySystem = 0L;
            Long countAppsCanceledByClient = 0L;
            Long countAppsRejected = 0L;
            Long countAppsIssued = 0L;
            Long countAppsWasApproved = 0L;
            Long countAppsTotalNew = 0L;
            Long countAppsInProgressNew = 0L;
            Long countAppsCanceledBySystemNew = 0L;
            Long countAppsCanceledByClientNew = 0L;
            Long countAppsRejectedNew = 0L;
            Long countAppsIssuedNew = 0L;
            Long countAppsWasApprovedNew = 0L;
            Long countAppsTotalRepeat = 0L;
            Long countAppsInProgressRepeat = 0L;
            Long countAppsCanceledBySystemRepeat = 0L;
            Long countAppsCanceledByClientRepeat = 0L;
            Long countAppsRejectedRepeat = 0L;
            Long countAppsIssuedRepeat = 0L;
            Long countAppsWasApprovedRepeat = 0L;
            for (int a = 0; a < applicationsInfoDateList.size(); a++) {
                if (date.equals(applicationsInfoDateList.get(a).getDate()
                        .withDayOfMonth(applicationsInfoDateList.get(a).getDate().getMonth()
                                .length(applicationsInfoDateList.get(a).getDate().isLeapYear())))) {
                    countAppsTotal += applicationsInfoDateList.get(a).getCountAppsTotal();
                    countAppsInProgress += applicationsInfoDateList.get(a).getCountAppsInProgress();
                    countAppsCanceledBySystem += applicationsInfoDateList.get(a).getCountAppsCanceledBySystem();
                    countAppsCanceledByClient += applicationsInfoDateList.get(a).getCountAppsCanceledByClient();
                    countAppsRejected += applicationsInfoDateList.get(a).getCountAppsRejected();
                    countAppsIssued += applicationsInfoDateList.get(a).getCountAppsIssued();
                    countAppsWasApproved += applicationsInfoDateList.get(a).getCountAppsWasApproved();
                    countAppsTotalNew += applicationsInfoDateList.get(a).getCountAppsTotalNew();
                    countAppsInProgressNew += applicationsInfoDateList.get(a).getCountAppsInProgressNew();
                    countAppsCanceledBySystemNew += applicationsInfoDateList.get(a).getCountAppsCanceledBySystemNew();
                    countAppsCanceledByClientNew += applicationsInfoDateList.get(a).getCountAppsCanceledByClientNew();
                    countAppsRejectedNew += applicationsInfoDateList.get(a).getCountAppsRejectedNew();
                    countAppsIssuedNew += applicationsInfoDateList.get(a).getCountAppsIssuedNew();
                    countAppsWasApprovedNew += applicationsInfoDateList.get(a).getCountAppsWasApprovedNew();
                    countAppsTotalRepeat += applicationsInfoDateList.get(a).getCountAppsTotalRepeat();
                    countAppsInProgressRepeat += applicationsInfoDateList.get(a).getCountAppsInProgressRepeat();
                    countAppsCanceledBySystemRepeat += applicationsInfoDateList.get(a).getCountAppsCanceledBySystemRepeat();
                    countAppsCanceledByClientRepeat += applicationsInfoDateList.get(a).getCountAppsCanceledByClientRepeat();
                    countAppsRejectedRepeat += applicationsInfoDateList.get(a).getCountAppsRejectedRepeat();
                    countAppsIssuedRepeat += applicationsInfoDateList.get(a).getCountAppsIssuedRepeat();
                    countAppsWasApprovedRepeat += applicationsInfoDateList.get(a).getCountAppsWasApprovedRepeat();
                }
            }
            applicationsInfoDate.setCountAppsTotal(countAppsTotal);
            applicationsInfoDate.setCountAppsInProgress(countAppsInProgress);
            applicationsInfoDate.setCountAppsCanceledBySystem(countAppsCanceledBySystem);
            applicationsInfoDate.setCountAppsCanceledByClient(countAppsCanceledByClient);
            applicationsInfoDate.setCountAppsRejected(countAppsRejected);
            applicationsInfoDate.setCountAppsIssued(countAppsIssued);
            applicationsInfoDate.setCountAppsWasApproved(countAppsWasApproved);
            applicationsInfoDate.setCountAppsTotalNew(countAppsTotalNew);
            applicationsInfoDate.setCountAppsInProgressNew(countAppsInProgressNew);
            applicationsInfoDate.setCountAppsCanceledBySystemNew(countAppsCanceledBySystemNew);
            applicationsInfoDate.setCountAppsCanceledByClientNew(countAppsCanceledByClientNew);
            applicationsInfoDate.setCountAppsRejectedNew(countAppsRejectedNew);
            applicationsInfoDate.setCountAppsIssuedNew(countAppsIssuedNew);
            applicationsInfoDate.setCountAppsWasApprovedNew(countAppsWasApprovedNew);
            applicationsInfoDate.setCountAppsTotalRepeat(countAppsTotalRepeat);
            applicationsInfoDate.setCountAppsInProgressRepeat(countAppsInProgressRepeat);
            applicationsInfoDate.setCountAppsCanceledBySystemRepeat(countAppsCanceledBySystemRepeat);
            applicationsInfoDate.setCountAppsCanceledByClientRepeat(countAppsCanceledByClientRepeat);
            applicationsInfoDate.setCountAppsRejectedRepeat(countAppsRejectedRepeat);
            applicationsInfoDate.setCountAppsIssuedRepeat(countAppsIssuedRepeat);
            applicationsInfoDate.setCountAppsWasApprovedRepeat(countAppsWasApprovedRepeat);
            applicationsInfoMonthList.add(applicationsInfoDate);
        }
        return applicationsInfoMonthList;
    }


    private List<LocalDate> getDateList (List<ExportApplicationsInfoDate> applicationsInfoDateList,
                                         List<ExportLoansInfoDate> loansInfoDateList,
                                         List<ExportPaymentsInfoDate> paymentsInfoDateList,
                                         List<ExportProlongationsInfoDate> prolongationsInfoDateList) {
        Set<LocalDate> dates = new HashSet<>();
        for (int a = 0; a < applicationsInfoDateList.size(); a++) {
            dates.add(applicationsInfoDateList.get(a).getDate());
        }
        for (int a = 0; a < loansInfoDateList.size(); a++) {
            dates.add(loansInfoDateList.get(a).getDate());
        }
        for (int a = 0; a < prolongationsInfoDateList.size(); a++) {
            dates.add(prolongationsInfoDateList.get(a).getDate());
        }
        for (int a = 0; a < paymentsInfoDateList.size(); a++) {
            dates.add(paymentsInfoDateList.get(a).getDate());
        }
        List<LocalDate> allDates = new ArrayList<>(dates);
        Collections.sort(allDates);
        return allDates;
    }



    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<ExportApplicationsInfoDate> getExportApplicationsInfoDateList() {
        return exportApplicationsInfoDateList;
    }

    public List<ExportApplicationsInfoDate> getExportApplicationsInfoMonthList() {
        return exportApplicationsInfoMonthList;
    }

    public List<ExportCollectionDebtsInfoDate> getExportCollectionDebtsInfoDateList() {
        return exportCollectionDebtsInfoDateList;
    }

    public List<ExportCollectionDebtsInfoDate> getExportCollectionDebtsInfoMonthList() {
        return exportCollectionDebtsInfoMonthList;
    }

    public List<ExportLoansInfoDate> getExportLoansInfoDateList() {
        return exportLoansInfoDateList;
    }

    public List<ExportLoansInfoDate> getExportLoansInfoMonthList() {
        return exportLoansInfoMonthList;
    }

    public List<ExportPaymentsInfoDate> getExportPaymentsInfoDateList() {
        return exportPaymentsInfoDateList;
    }

    public List<ExportPaymentsInfoDate> getExportPaymentsInfoMonthList() {
        return exportPaymentsInfoMonthList;
    }

    public List<ExportProlongationsInfoDate> getExportProlongationsInfoDateList() {
        return exportProlongationsInfoDateList;
    }

    public List<ExportProlongationsInfoDate> getExportProlongationsInfoMonthList() {
        return exportProlongationsInfoMonthList;
    }

    public List<LocalDate> getDateList() {
        return dateList;
    }

    public LocalDate getReportDateFrom() {
        return reportDateFrom;
    }

    public LocalDate getReportDateTo() {
        return reportDateTo;
    }

    public List<ModelPlan> getModelPlanList() {
        return modelPlanList;
    }

    public void setDateList(List<LocalDate> dateList) {
        this.dateList = dateList;
    }

    public void setExportApplicationsInfoDateList(List<ExportApplicationsInfoDate> exportApplicationsInfoDateList) {
        this.exportApplicationsInfoDateList = exportApplicationsInfoDateList;
    }

    public void setExportApplicationsInfoMonthList(List<ExportApplicationsInfoDate> exportApplicationsInfoMonthList) {
        this.exportApplicationsInfoMonthList = exportApplicationsInfoMonthList;
    }

    public void setExportCollectionDebtsInfoDateList(List<ExportCollectionDebtsInfoDate> exportCollectionDebtsInfoDateList) {
        this.exportCollectionDebtsInfoDateList = exportCollectionDebtsInfoDateList;
    }

    public void setExportCollectionDebtsInfoMonthList(List<ExportCollectionDebtsInfoDate> exportCollectionDebtsInfoMonthList) {
        this.exportCollectionDebtsInfoMonthList = exportCollectionDebtsInfoMonthList;
    }

    public void setExportLoansInfoDateList(List<ExportLoansInfoDate> exportLoansInfoDateList) {
        this.exportLoansInfoDateList = exportLoansInfoDateList;
    }

    public void setExportLoansInfoMonthList(List<ExportLoansInfoDate> exportLoansInfoMonthList) {
        this.exportLoansInfoMonthList = exportLoansInfoMonthList;
    }

    public void setExportPaymentsInfoDateList(List<ExportPaymentsInfoDate> exportPaymentsInfoDateList) {
        this.exportPaymentsInfoDateList = exportPaymentsInfoDateList;
    }

    public void setExportPaymentsInfoMonthList(List<ExportPaymentsInfoDate> exportPaymentsInfoMonthList) {
        this.exportPaymentsInfoMonthList = exportPaymentsInfoMonthList;
    }

    public void setExportProlongationsInfoDateList(List<ExportProlongationsInfoDate> exportProlongationsInfoDateList) {
        this.exportProlongationsInfoDateList = exportProlongationsInfoDateList;
    }

    public void setExportProlongationsInfoMonthList(List<ExportProlongationsInfoDate> exportProlongationsInfoMonthList) {
        this.exportProlongationsInfoMonthList = exportProlongationsInfoMonthList;
    }

    public void setModelPlanList(List<ModelPlan> modelPlanList) {
        this.modelPlanList = modelPlanList;
    }

    public void setReportDateTo(LocalDate reportDateTo) {
        this.reportDateTo = reportDateTo;
    }

    public void setReportDateFrom(LocalDate reportDateFrom) {
        this.reportDateFrom = reportDateFrom;
    }

    public List<LocalDate> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<LocalDate> monthList) {
        this.monthList = monthList;
    }

    public List<CurrencyRate> getCurrencyRateList() {
        return currencyRateList;
    }

    public void setCurrencyRateList(List<CurrencyRate> currencyRateList) {
        this.currencyRateList = currencyRateList;
    }

    public Map<LocalDate, Double> getMapMonthAndAverageCurrencyRateUsdVnd() {
        return mapMonthAndAverageCurrencyRateUsdVnd;
    }

    public void setMapMonthAndAverageCurrencyRateUsdVnd(Map<LocalDate, Double> mapMonthAndAverageCurrencyRateUsdVnd) {
        this.mapMonthAndAverageCurrencyRateUsdVnd = mapMonthAndAverageCurrencyRateUsdVnd;
    }

}
