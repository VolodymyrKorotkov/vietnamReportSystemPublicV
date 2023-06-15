package com.korotkov.mainCurrentApp.service.reportBuilder;

import com.korotkov.creditCRM.model.BackUserAccountShortly;
import com.korotkov.creditCRM.model.clients.ExportClientDocumentForCheckingInsuranceReportObject;
import com.korotkov.creditCRM.model.collection.commonDataWithAssignedCollectorReport.CommonDataAssignedCollectorReportObject;
import com.korotkov.creditCRM.model.collection.preSoftBonusReport.*;
import com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport.IndividualBonusStagesWithAssignedDebts;
import com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport.StagesWithAssignedBonusReportObject;
import com.korotkov.creditCRM.model.loansInfo.ExportLoansAndPaymentByDateReportObject;
import com.korotkov.creditCRM.model.loansInfo.ExportLoansInfoExpiredInfoReportObject;
import com.korotkov.creditCRM.model.mainDailyReport.MainDailyReportObject;
import com.korotkov.creditCRM.model.reportsCreditConveyor.*;
import com.korotkov.creditCRM.model.payments.ExportPaymentsWithPaidFeesReportObject;
import com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment.AnalyticalReportRepaymentObject;

import java.time.LocalDate;
import java.util.List;

public interface ReportBuilderService {
    MainDailyReportObject getMainDailyReport(LocalDate dateFrom, LocalDate dateTo);
    CommonDataAssignedCollectorReportObject getCommonDataWithAssignedCollectorReport();

    PreSoftResultedPTPCallsReportObject buildReportOfResultedPTPCalls();

    PreSoftBonusReportObject getPreSoftBonusReport(LocalDate dateFrom, LocalDate dateTo);
    StagesWithAssignedBonusReportObject getStagesWithAssignedBonusReportObject(LocalDate dateFrom,
                                                                               LocalDate dateTo);
    IndividualBonusPreSoft getIndividualBonusPreSoft(String userEmail, LocalDate dateFrom,
                                                     LocalDate dateTo);
    IndividualBonusStagesWithAssignedDebts getIndividualBonusStagesWithAssignedDebts(BackUserAccountShortly userAccountShortly,
                                                                                     LocalDate dateFrom,
                                                                                     LocalDate dateTo);
    ExportPaymentsWithPaidFeesReportObject getExportPaymentsWithFeesReport(LocalDate dateFrom, LocalDate dateTo,
                                                                           boolean isCorrectly);
    ExportClientDocumentForCheckingInsuranceReportObject getClientDocumentForCheckInsuranceReport();
    ExportLoansInfoExpiredInfoReportObject getLoansInfoExpiredInfoReportObject(LocalDate dateFrom, LocalDate dateTo);
    List<ClientWithIDAndDebtId> getClientWithIDAndActiveDebtIdList(List<Long> clientIdList);
    AppAndLoanBySourceReportObject getAppAndLoanBySourceReportObject(LocalDate dateFrom,
                                                                     LocalDate dateTo);
    ExportAppsBySourceReportObject getExportAppsBySourceReportObject(LocalDate dateFrom,
                                                                     LocalDate dateTo);
    ExportLoansBySourceReportObject getExportLoansBySourceReportObject(LocalDate dateFrom,
                                                                       LocalDate dateTo);
    ExportLoansAndPaymentByDateReportObject getExportLoansAndPaymentByDateReportObject(LocalDate dateFrom,
                                                                                       LocalDate dateTo);
    ExportDateHourCountAmountReportObject getExportDateHourCountAmountLoanReportObject(LocalDate dateFrom,
                                                                                       LocalDate dateTo);
    ExportDateHourCountAmountReportObject getExportDateHourCountAmountPaymentReportObject(LocalDate dateFrom,
                                                                                          LocalDate dateTo);
    UnderwritersResultWithAppReportObject getUnderwritersResultWithAppReportObject(LocalDate dateFrom,
                                                                                   LocalDate dateTo);
    ExportAppProcessedByUnderwriterReportObject getExportAppProcessedByUnderwriterReportObject(LocalDate dateFrom,
                                                                                               LocalDate dateTo);
    ExportAppByUnderwriterPerHourReportObject getExportAppByUnderwriterPerHourReportObject(LocalDate dateFrom,
                                                                                           LocalDate dateTo);
    ExportApplicationsInfoDateReportObject getExportApplicationsInfoDateReportObject(LocalDate dateFrom,
                                                                                     LocalDate dateTo);
    ExportRefundOverpaymentReportObject getExportRefundOverpaymentReportObject(LocalDate dateFrom,
                                                                               LocalDate dateTo);
    ExportDeactivatedPaymentsReportObject getExportDeactivatedPaymentsReportObject(LocalDate dateFrom,
                                                                                   LocalDate dateTo);
    ExportPassiveClientsForSMSReportObject getExportPassiveClientsForSMSReportObject();
    ExportPaymentForCrossCheckReportObject getExportPaymentForCrossCheckReportObject(LocalDate dateFrom,
                                                                                     LocalDate dateTo);
    ExportIssuedLoanForCrossCheckReportObject getExportIssuedLoanForCrossCheckReportObject(LocalDate dateFrom,
                                                                                           LocalDate dateTo);
    AnalyticalReportRepaymentObject getAnalyticalReportRepaymentObject(LocalDate dateFrom, LocalDate dateTo,
                                                                       Integer lastDayOverdueStage1,
                                                                       Integer lastDayOverdueStage2);
}
