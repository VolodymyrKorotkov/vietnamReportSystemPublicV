package com.korotkov.creditCRM.dao.reportCreditConveyor;

import com.korotkov.creditCRM.model.loansInfo.ExportLoansAndPaymentByDate;
import com.korotkov.creditCRM.model.reportsCreditConveyor.*;
import com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment.*;

import java.time.LocalDate;
import java.util.List;

public interface ReportCreditConveyorDao {
    List<AppAndLoanBySource> getAppAndLoanBySourceList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportAppsBySource> getExportAppsBySourceList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportLoansBySource> getExportLoansBySourceList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportLoansAndPaymentByDate> getExportLoansAndPaymentByDateList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportDateHourCountAmount> getExportDateHourCountAmountPaymentList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportDateHourCountAmount> getExportDateHourCountAmountLoanList(LocalDate dateFrom, LocalDate dateTo);
    List<UnderwritersResultWithApp> getUnderwritersResultWithAppList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportAppProcessedByUnderwriter> getExportAppProcessedByUnderwriterList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportAppByUnderwriterPerHour> getExportAppByUnderwriterPerHourList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportRefundOverpayment> getExportRefundOverpaymentList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportDeactivatedPayments> getExportDeactivatedPaymentsList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportPaymentForCrossCheck> getExportPaymentForCrossCheckList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportIssuedLoanForCrossCheck> getExportIssuedLoanForCrossCheckList(LocalDate dateFrom, LocalDate dateTo);
    LoanPortfolioOfDate getLoanPortfolioOfDate(LocalDate date, Integer lastDayOverdueStage1,
                                               Integer lastDayOverdueStage2);
    NewStartedOverdueAmountInPeriod getNewStartedOverdueAmountInPeriodWithoutProlongation(LocalDate dateFrom, LocalDate dateTo,
                                                                                          Integer lastDayOverdueStage1,
                                                                                          Integer lastDayOverdueStage2);
    NewStartedOverdueAmountInPeriod getNewStartedOverdueAmountInPeriodOnlyAfterProlongation(LocalDate dateFrom, LocalDate dateTo);
    IssuedAmountInPeriod getIssuedAmountInPeriod(LocalDate dateFrom, LocalDate dateTo);
    ProlongationAmountInPeriod getProlongationAmountInPeriodFromNextDate(LocalDate dateFrom, LocalDate dateTo,
                                                                         Integer lastDayOverdueStage1,
                                                                         Integer lastDayOverdueStage2);
    ProlongationAmountInPeriod getProlongationAmountInPeriodForDateWhichRequestedThatDate(LocalDate dateFrom,
                                                                                          Integer lastDayOverdueStage1,
                                                                                          Integer lastDayOverdueStage2);
    PaymentsAmountInPeriod getPaymentsAmountInPeriod(LocalDate dateFrom, LocalDate dateTo,
                                                     Integer lastDayOverdueStage1,
                                                     Integer lastDayOverdueStage2);
}
