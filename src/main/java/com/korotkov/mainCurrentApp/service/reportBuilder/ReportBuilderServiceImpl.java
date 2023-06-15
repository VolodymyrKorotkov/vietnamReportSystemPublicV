package com.korotkov.mainCurrentApp.service.reportBuilder;

import com.korotkov.creditCRM.dao.collection.commonDataWithAssignedCollectorReport.CommonDataWithAssignedCollectorReportDao;
import com.korotkov.creditCRM.dao.collection.preSoftBonusReport.PreSoftBonusReportCrmDao;
import com.korotkov.creditCRM.dao.collection.stagesWithAssignedBonusReport.StagesWithAssignedBonusReportCrmDao;
import com.korotkov.creditCRM.dao.mainDailyReport.MainDailyReportDao;
import com.korotkov.creditCRM.dao.reportCreditConveyor.ReportCreditConveyorDao;
import com.korotkov.creditCRM.dao.payments.ExportPaymentsDao;
import com.korotkov.creditCRM.model.BackUserAccountShortly;
import com.korotkov.creditCRM.model.clients.ExportClientDocumentForCheckingInsuranceReportObject;
import com.korotkov.creditCRM.model.clients.exportForUploadingNewLeads.ExportPassiveClients;
import com.korotkov.creditCRM.model.collection.commonDataWithAssignedCollectorReport.CommonDataAssignedCollectorReportObject;
import com.korotkov.creditCRM.model.collection.preSoftBonusReport.*;
import com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport.CollectionPaymentWithAssigned;
import com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport.IndividualBonusStagesWithAssignedDebts;
import com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport.StagesWithAssignedBonusReportObject;
import com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport.StagesWithAssignedUserResult;
import com.korotkov.creditCRM.model.loansInfo.ExportLoansAndPaymentByDateReportObject;
import com.korotkov.creditCRM.model.loansInfo.ExportLoansInfoExpiredInfoReportObject;
import com.korotkov.creditCRM.model.mainDailyReport.*;
import com.korotkov.creditCRM.model.reportsCreditConveyor.*;
import com.korotkov.creditCRM.model.payments.ExportPaymentWithPaidFees;
import com.korotkov.creditCRM.model.payments.ExportPaymentsWithFeesGroupedByDate;
import com.korotkov.creditCRM.model.payments.ExportPaymentsWithPaidFeesReportObject;
import com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment.*;
import com.korotkov.creditCRM.service.clientCRM.ClientCRMService;
import com.korotkov.creditCRM.service.loansInfo.LoansInfoService;
import com.korotkov.mainCurrentApp.api.currencyConverterApi.CurrencyConverterApiService;
import com.korotkov.mainCurrentApp.enums.SystemSettingsNameEnum;
import com.korotkov.mainCurrentApp.service.currencyRate.CurrencyRateService;
import com.korotkov.mainCurrentApp.service.modelPlan.ModelPlanService;
import com.korotkov.mainCurrentApp.service.reportBuilder.collection.PreSoftBonusReportBuilder;
import com.korotkov.mainCurrentApp.service.reportBuilder.collection.StagesWithAssignedBonusReportBuilder;
import com.korotkov.mainCurrentApp.service.systemSetting.SystemSettingService;
import com.korotkov.vicidial.dao.collection.preSoftBonusReport.PreSoftBonusReportVicidialDao;
import com.korotkov.vicidial.model.collection.preSoftBonusReport.PreSoftBonusExportCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class ReportBuilderServiceImpl implements ReportBuilderService {
    private MainDailyReportDao mainDailyReportDao;
    private CurrencyRateService currencyRateService;
    private ModelPlanService modelPlanService;
    private CurrencyConverterApiService currencyConverterApiService;
    private CommonDataWithAssignedCollectorReportDao commonDataWithAssignedCollectorReportDao;
    private PreSoftBonusReportVicidialDao preSoftBonusReportVicidialDao;
    private PreSoftBonusReportCrmDao preSoftBonusReportCrmDao;
    private StagesWithAssignedBonusReportCrmDao stagesWithAssignedBonusReportCrmDao;
    private ExportPaymentsDao exportPaymentsDao;
    private SystemSettingService systemSettingService;
    private ClientCRMService clientCRMService;
    private LoansInfoService loansInfoService;
    private ReportCreditConveyorDao reportCreditConveyorDao;

    @Autowired
    public void setMarketingDao(ReportCreditConveyorDao reportCreditConveyorDao) {
        this.reportCreditConveyorDao = reportCreditConveyorDao;
    }

    @Autowired
    public void setLoansInfoService(LoansInfoService loansInfoService) {
        this.loansInfoService = loansInfoService;
    }

    @Autowired
    public void setClientCRMService(ClientCRMService clientCRMService) {
        this.clientCRMService = clientCRMService;
    }

    @Autowired
    public void setSystemSettingService(SystemSettingService systemSettingService) {
        this.systemSettingService = systemSettingService;
    }

    @Autowired
    public void setExportPaymentsDao(ExportPaymentsDao exportPaymentsDao) {
        this.exportPaymentsDao = exportPaymentsDao;
    }

    @Autowired
    public void setStagesWithAssignedBonusReportCrmDao(StagesWithAssignedBonusReportCrmDao stagesWithAssignedBonusReportCrmDao) {
        this.stagesWithAssignedBonusReportCrmDao = stagesWithAssignedBonusReportCrmDao;
    }

    @Autowired
    public void setPreSoftBonusReportCrmDao(PreSoftBonusReportCrmDao preSoftBonusReportCrmDao) {
        this.preSoftBonusReportCrmDao = preSoftBonusReportCrmDao;
    }

    @Autowired
    public void setPreSoftBonusReportVicidialDao(PreSoftBonusReportVicidialDao preSoftBonusReportVicidialDao) {
        this.preSoftBonusReportVicidialDao = preSoftBonusReportVicidialDao;
    }

    @Autowired
    public void setCommonDataWithAssignedCollectorReportDao(CommonDataWithAssignedCollectorReportDao commonDataWithAssignedCollectorReportDao) {
        this.commonDataWithAssignedCollectorReportDao = commonDataWithAssignedCollectorReportDao;
    }

    @Autowired
    public void setMainDailyReportDao(MainDailyReportDao mainDailyReportDao) {
        this.mainDailyReportDao = mainDailyReportDao;
    }

    @Autowired
    public void setCurrencyConverterApiService(CurrencyConverterApiService currencyConverterApiService) {
        this.currencyConverterApiService = currencyConverterApiService;
    }

    @Autowired
    public void setCurrencyRateService(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @Autowired
    public void setModelPlanService(ModelPlanService modelPlanService) {
        this.modelPlanService = modelPlanService;
    }



    @Override
    public ExportLoansInfoExpiredInfoReportObject getLoansInfoExpiredInfoReportObject(LocalDate dateFrom, LocalDate dateTo) {
        if (!currencyRateService.isCurrencyRateForToday()) {
            currencyConverterApiService.apiSchedulerRequestCurrencyRateUsdToVnd();
        }
        return new ExportLoansInfoExpiredInfoReportObject(dateFrom, dateTo,
                loansInfoService.getLoanInfoExpiredInfoList(dateFrom, dateTo),
                currencyRateService.getCurrencyRatesWithDates(dateFrom, dateTo));
    }

    @Override
    public MainDailyReportObject getMainDailyReport(LocalDate dateFrom, LocalDate dateTo) {
        if (!currencyRateService.isCurrencyRateForToday()) {
            currencyConverterApiService.apiSchedulerRequestCurrencyRateUsdToVnd();
        }
        return new MainDailyReportObject(dateFrom, dateTo,
                getApplicationsInfoDateList(dateFrom, dateTo),
                getCollectionDebtsInfoDateList(dateFrom, dateTo),
                getLoansInfoDateList(dateFrom, dateTo),
                getPaymentsInfoDateList(dateFrom, dateTo),
                getProlongationsInfoDateList(dateFrom, dateTo),
                modelPlanService.getModelPlansWithDates(dateFrom, dateTo),
                currencyRateService.getCurrencyRatesWithDates(dateFrom, dateTo));
    }

    @Override
    public ExportApplicationsInfoDateReportObject getExportApplicationsInfoDateReportObject(LocalDate dateFrom,
                                                                                            LocalDate dateTo) {
        return new ExportApplicationsInfoDateReportObject(getApplicationsInfoDateList(dateFrom, dateTo),
                dateFrom, dateTo);
    }

    @Override
    public ExportPaymentsWithPaidFeesReportObject getExportPaymentsWithFeesReport(LocalDate dateFrom, LocalDate dateTo,
                                                                                  boolean isCorrectly) {
        List<ExportPaymentWithPaidFees> exportPaymentWithPaidFeesList = getExportPaymentWithPaidFeesList(dateFrom, dateTo);
        if (!isCorrectly) {
            changeExportPaymentsWithFeesListForOptimiseVAT(exportPaymentWithPaidFeesList);
        }
        addAdditionalFieldsToPaymentWithPaidFeesList(exportPaymentWithPaidFeesList);
        addTotalRowObjectToPaymentWithPaidFeesList(exportPaymentWithPaidFeesList);
        List<ExportPaymentsWithFeesGroupedByDate> exportPaymentsWithFeesGroupedByDateList =
                createListPaymentsWithFeesGroupedByDate(exportPaymentWithPaidFeesList);
        return new ExportPaymentsWithPaidFeesReportObject(exportPaymentWithPaidFeesList,
                exportPaymentsWithFeesGroupedByDateList, dateFrom, dateTo);
    }


    @Override
    public IndividualBonusPreSoft getIndividualBonusPreSoft(String userEmail, LocalDate dateFrom,
                                                            LocalDate dateTo) {
        return new IndividualBonusPreSoft(getPreSoftBonusReport(dateFrom, dateTo), userEmail);
    }

    @Override
    public IndividualBonusStagesWithAssignedDebts getIndividualBonusStagesWithAssignedDebts(BackUserAccountShortly userAccountShortly,
                                                                                            LocalDate dateFrom,
                                                                                            LocalDate dateTo) {
        return new IndividualBonusStagesWithAssignedDebts(getStagesWithAssignedBonusReportObject(dateFrom, dateTo),
                userAccountShortly);
    }


    @Override
    public PreSoftResultedPTPCallsReportObject buildReportOfResultedPTPCalls() {
        List<PreSoftBonusExportCall> preSoftBonusExportCallList =
                getPreSoftBonusExportCallList(LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(PreSoftBonusReportBuilder.getCountDaysForResult()),
                        LocalDate.now(ZoneId.of(TIME_ZONE)));
        List<CollectionPayment> collectionPaymentList = getCollectionPaymentList(LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(PreSoftBonusReportBuilder.getCountDaysForResult()),
                LocalDate.now(ZoneId.of(TIME_ZONE)));
        List<ClientPhone> clientPhoneList = getClientPhoneListByPhones(preSoftBonusExportCallList);
        List<BackUserAccountShortly> backUserAccountShortlyList = getBackUserAccountShortlyList();


        List<PreSoftActivePromissedPaymentsForAgent> ptpCalls =
                PreSoftBonusReportBuilder.getPromissedPaymentsForEmployees(
                        preSoftBonusExportCallList, clientPhoneList,
                        collectionPaymentList, backUserAccountShortlyList
                );

        updatePtpCallsAndAddNewInfo(ptpCalls);

        return new PreSoftResultedPTPCallsReportObject(ptpCalls);

    }



    @Override
    public PreSoftBonusReportObject getPreSoftBonusReport(LocalDate dateFrom, LocalDate dateTo) {
        List<PreSoftBonusExportCall> preSoftBonusExportCallList =
                getPreSoftBonusExportCallList(dateFrom.minusDays(PreSoftBonusReportBuilder.getCountDaysForResult()),
                        dateTo);
        List<PreSoftBonusExportCall> preSoftBonusExportCallListOnlyRemindingPromisedPayments =
                preSoftBonusExportCallListOnlyRemindingPromisedPayments(dateFrom, dateTo);
        List<CollectionPayment> collectionPaymentList = getCollectionPaymentList(dateFrom, dateTo);
        List<ClientPhone> clientPhoneList = getClientPhoneList(collectionPaymentList);
        List<BackUserAccountShortly> backUserAccountShortlyList = getBackUserAccountShortlyList();

        return PreSoftBonusReportBuilder.buildReport(preSoftBonusExportCallList,
                preSoftBonusExportCallListOnlyRemindingPromisedPayments, clientPhoneList, collectionPaymentList,
                backUserAccountShortlyList, dateFrom, dateTo);
    }

    @Override
    public StagesWithAssignedBonusReportObject getStagesWithAssignedBonusReportObject(LocalDate dateFrom,
                                                                                      LocalDate dateTo) {
        return StagesWithAssignedBonusReportBuilder
                .buildReport(getCollectionPaymentWithAssignedList(dateFrom, dateTo),
                        getBackUserAccountShortlyList(), dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public ExportAppByUnderwriterPerHourReportObject getExportAppByUnderwriterPerHourReportObject(LocalDate dateFrom,
                                                                                                  LocalDate dateTo) {
        return new ExportAppByUnderwriterPerHourReportObject(reportCreditConveyorDao
                .getExportAppByUnderwriterPerHourList(dateFrom, dateTo), dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public ExportAppProcessedByUnderwriterReportObject getExportAppProcessedByUnderwriterReportObject(LocalDate dateFrom,
                                                                                                      LocalDate dateTo) {
        return new ExportAppProcessedByUnderwriterReportObject(reportCreditConveyorDao
                .getExportAppProcessedByUnderwriterList(dateFrom, dateTo), dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public UnderwritersResultWithAppReportObject getUnderwritersResultWithAppReportObject(LocalDate dateFrom,
                                                                                          LocalDate dateTo) {
        return new UnderwritersResultWithAppReportObject(reportCreditConveyorDao
                .getUnderwritersResultWithAppList(dateFrom, dateTo), dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public ExportDateHourCountAmountReportObject getExportDateHourCountAmountLoanReportObject(LocalDate dateFrom,
                                                                                          LocalDate dateTo) {
        return new ExportDateHourCountAmountReportObject(reportCreditConveyorDao
                .getExportDateHourCountAmountLoanList(dateFrom, dateTo), dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public ExportDateHourCountAmountReportObject getExportDateHourCountAmountPaymentReportObject(LocalDate dateFrom,
                                                                                          LocalDate dateTo) {
        return new ExportDateHourCountAmountReportObject(reportCreditConveyorDao
                .getExportDateHourCountAmountPaymentList(dateFrom, dateTo), dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public ExportLoansAndPaymentByDateReportObject getExportLoansAndPaymentByDateReportObject(LocalDate dateFrom,
                                                                                              LocalDate dateTo) {
        return new ExportLoansAndPaymentByDateReportObject(reportCreditConveyorDao
                .getExportLoansAndPaymentByDateList(dateFrom, dateTo), dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public ExportAppsBySourceReportObject getExportAppsBySourceReportObject(LocalDate dateFrom,
                                                                            LocalDate dateTo) {
        return new ExportAppsBySourceReportObject(reportCreditConveyorDao.getExportAppsBySourceList(dateFrom, dateTo),
                dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public ExportDeactivatedPaymentsReportObject getExportDeactivatedPaymentsReportObject(LocalDate dateFrom,
                                                                                          LocalDate dateTo) {
        return new ExportDeactivatedPaymentsReportObject(reportCreditConveyorDao
                .getExportDeactivatedPaymentsList(dateFrom, dateTo), dateFrom, dateTo);
    }


    @Override
    @Transactional("transactionManagerCRM")
    public ExportRefundOverpaymentReportObject getExportRefundOverpaymentReportObject(LocalDate dateFrom,
                                                                                      LocalDate dateTo) {
        return new ExportRefundOverpaymentReportObject(reportCreditConveyorDao
                .getExportRefundOverpaymentList(dateFrom, dateTo), dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public ExportIssuedLoanForCrossCheckReportObject getExportIssuedLoanForCrossCheckReportObject(LocalDate dateFrom,
                                                                                                  LocalDate dateTo) {
        return new ExportIssuedLoanForCrossCheckReportObject(reportCreditConveyorDao
                .getExportIssuedLoanForCrossCheckList(dateFrom, dateTo), dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public ExportPaymentForCrossCheckReportObject getExportPaymentForCrossCheckReportObject(LocalDate dateFrom,
                                                                                            LocalDate dateTo) {
        return new ExportPaymentForCrossCheckReportObject(reportCreditConveyorDao
                .getExportPaymentForCrossCheckList(dateFrom, dateTo), dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public ExportLoansBySourceReportObject getExportLoansBySourceReportObject(LocalDate dateFrom,
                                                                              LocalDate dateTo) {
        return new ExportLoansBySourceReportObject(reportCreditConveyorDao.getExportLoansBySourceList(dateFrom, dateTo),
                dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public AppAndLoanBySourceReportObject getAppAndLoanBySourceReportObject(LocalDate dateFrom,
                                                                            LocalDate dateTo) {
        return new AppAndLoanBySourceReportObject(reportCreditConveyorDao.getAppAndLoanBySourceList(dateFrom, dateTo),
                dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public CommonDataAssignedCollectorReportObject getCommonDataWithAssignedCollectorReport() {
         return new CommonDataAssignedCollectorReportObject(commonDataWithAssignedCollectorReportDao.getExportCommonDataWithAssignedCollectorList());
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientWithIDAndDebtId> getClientWithIDAndActiveDebtIdList(List<Long> clientIdList) {
        return preSoftBonusReportCrmDao.getClientWithIDAndActiveDebtIds(clientIdList);
    }

    @Override
    public AnalyticalReportRepaymentObject getAnalyticalReportRepaymentObject(LocalDate dateFrom, LocalDate dateTo,
                                                                              Integer lastDayOverdueStage1,
                                                                              Integer lastDayOverdueStage2) {
        return new AnalyticalReportRepaymentObject(dateFrom, dateTo,
                lastDayOverdueStage1, lastDayOverdueStage2,
                getLoanPortfolioOfDate(dateFrom, lastDayOverdueStage1, lastDayOverdueStage2),
                getNewStartedOverdueAmountInPeriodWithoutProlongation(dateFrom, dateTo, lastDayOverdueStage1,
                        lastDayOverdueStage2),
                getNewStartedOverdueAmountInPeriodOnlyAfterProlongation(dateFrom, dateTo),
                getIssuedAmountInPeriod(dateFrom, dateTo),
                getProlongationAmountInPeriodFromNextDate(dateFrom, dateTo, lastDayOverdueStage1, lastDayOverdueStage2),
                getProlongationAmountInPeriodForDateWhichRequestedThatDate(dateFrom, lastDayOverdueStage1,
                        lastDayOverdueStage2),
                getPaymentsAmountInPeriod(dateFrom, dateTo, lastDayOverdueStage1, lastDayOverdueStage2));
    }

    @Override
    public ExportClientDocumentForCheckingInsuranceReportObject getClientDocumentForCheckInsuranceReport() {
        return new ExportClientDocumentForCheckingInsuranceReportObject(clientCRMService.getClientDocumentForCheckInsurance());
    }

    @Override
    public ExportPassiveClientsForSMSReportObject getExportPassiveClientsForSMSReportObject() {

        int[] passiveDays = new int[] {1,3,7,10,13,17,20,23,27,30,33,34,37,40,43,46,50,55,60,65,70,75,80,85,
                90,95,100,110,120,130,140,150,160,170,180,190,200,210,220,230,240,250,260,270,280,290,300,
                320,340,360,380,400,420,440,460,480,500,520,540,560,580,600,620,630,650,670,690,700,720,750,770,800,
                820,830,850,870,890,900,920,940,960,980,1000,1050,1100,1150,1200,1250,1300,1350,1400,1450,1500};
        List<LocalDate> dateList = new ArrayList<>();
        for (int a : passiveDays) {
            dateList.add(LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(a));
        }
        List<ExportPassiveClients> passiveClientsList = clientCRMService.getPassiveClientsListForVicidial(dateList);
        passiveClientsList
        .sort(Comparator.comparing(ExportPassiveClients::getPassiveDays));

        return new ExportPassiveClientsForSMSReportObject(passiveClientsList);
    }

    @Transactional("transactionManagerCRM")
    PaymentsAmountInPeriod getPaymentsAmountInPeriod(LocalDate dateFrom, LocalDate dateTo,
                                                     Integer lastDayOverdueStage1,
                                                     Integer lastDayOverdueStage2) {
        return reportCreditConveyorDao
                .getPaymentsAmountInPeriod(dateFrom, dateTo, lastDayOverdueStage1, lastDayOverdueStage2);
    }

    @Transactional("transactionManagerCRM")
    ProlongationAmountInPeriod getProlongationAmountInPeriodForDateWhichRequestedThatDate(LocalDate dateFrom,
                                                                                          Integer lastDayOverdueStage1,
                                                                                          Integer lastDayOverdueStage2) {
        return reportCreditConveyorDao
                .getProlongationAmountInPeriodForDateWhichRequestedThatDate(dateFrom, lastDayOverdueStage1, lastDayOverdueStage2);
    }

    @Transactional("transactionManagerCRM")
    ProlongationAmountInPeriod getProlongationAmountInPeriodFromNextDate(LocalDate dateFrom, LocalDate dateTo,
                                                                         Integer lastDayOverdueStage1,
                                                                         Integer lastDayOverdueStage2) {
        return reportCreditConveyorDao
                .getProlongationAmountInPeriodFromNextDate(dateFrom, dateTo, lastDayOverdueStage1, lastDayOverdueStage2);
    }

    @Transactional("transactionManagerCRM")
    IssuedAmountInPeriod getIssuedAmountInPeriod(LocalDate dateFrom, LocalDate dateTo) {
        return reportCreditConveyorDao.getIssuedAmountInPeriod(dateFrom, dateTo);
    }

    @Transactional("transactionManagerCRM")
    NewStartedOverdueAmountInPeriod getNewStartedOverdueAmountInPeriodOnlyAfterProlongation(LocalDate dateFrom,
                                                                                            LocalDate dateTo) {
        return reportCreditConveyorDao
                .getNewStartedOverdueAmountInPeriodOnlyAfterProlongation(dateFrom, dateTo);
    }

    @Transactional("transactionManagerCRM")
    NewStartedOverdueAmountInPeriod getNewStartedOverdueAmountInPeriodWithoutProlongation(LocalDate dateFrom, LocalDate dateTo,
                                                                                          Integer lastDayOverdueStage1,
                                                                                          Integer lastDayOverdueStage2) {
        return reportCreditConveyorDao
                .getNewStartedOverdueAmountInPeriodWithoutProlongation(dateFrom, dateTo,
                        lastDayOverdueStage1, lastDayOverdueStage2);
    }

    @Transactional("transactionManagerCRM")
    LoanPortfolioOfDate getLoanPortfolioOfDate(LocalDate date, Integer lastDayOverdueStage1,
                                                      Integer lastDayOverdueStage2) {
        return reportCreditConveyorDao.getLoanPortfolioOfDate(date, lastDayOverdueStage1, lastDayOverdueStage2);
    }


    @Transactional("transactionManagerCRM")
    List<CollectionPaymentWithAssigned> getCollectionPaymentWithAssignedList(LocalDate dateFrom, LocalDate dateTo) {
        return stagesWithAssignedBonusReportCrmDao.getCollectionPaymentWithAssignedList(dateFrom, dateTo);
    }

    @Transactional("transactionManagerCRM")
    public List<ExportApplicationsInfoDate> getApplicationsInfoDateList(LocalDate dateFrom, LocalDate dateTo) {
        return mainDailyReportDao.getExportApplicationsInfoDateList(dateFrom, dateTo);
    }

    @Transactional("transactionManagerCRM")
    List<ExportLoansInfoDate> getLoansInfoDateList(LocalDate dateFrom, LocalDate dateTo) {
        return mainDailyReportDao.getExportLoansInfoDateList(dateFrom, dateTo);
    }

    @Transactional("transactionManagerCRM")
    List<ExportProlongationsInfoDate> getProlongationsInfoDateList(LocalDate dateFrom, LocalDate dateTo) {
        return mainDailyReportDao.getExportProlongationsInfoDateList(dateFrom, dateTo);
    }

    @Transactional("transactionManagerCRM")
    List<ExportPaymentsInfoDate> getPaymentsInfoDateList(LocalDate dateFrom, LocalDate dateTo) {
        return mainDailyReportDao.getExportPaymentsInfoDateList(dateFrom, dateTo);
    }

    @Transactional("transactionManagerCRM")
    List<ExportCollectionDebtsInfoDate> getCollectionDebtsInfoDateList(LocalDate dateFrom, LocalDate dateTo) {
        return mainDailyReportDao.getExportCollectionDebtsInfoDateList(dateFrom, dateTo);
    }

    @Transactional("transactionManagerVicidial")
    List<PreSoftBonusExportCall> getPreSoftBonusExportCallList(LocalDate dateFrom, LocalDate dateTo) {
        return preSoftBonusReportVicidialDao.getPreSoftBonusExportCallList(dateFrom, dateTo);
    }

    @Transactional("transactionManagerVicidial")
    List<PreSoftBonusExportCall> preSoftBonusExportCallListOnlyRemindingPromisedPayments(LocalDate dateFrom, LocalDate dateTo) {
        return preSoftBonusReportVicidialDao.getPreSoftBonusExportCallListOnlyRemindingPromisedPayments(dateFrom, dateTo);
    }

    @Transactional("transactionManagerCRM")
    List<CollectionPayment> getCollectionPaymentList(LocalDate dateFrom, LocalDate dateTo) {
        return preSoftBonusReportCrmDao.getCollectionPaymentList(dateFrom, dateTo);
    }

    @Transactional("transactionManagerCRM")
    List<ClientPhone> getClientPhoneListByPhones(List<PreSoftBonusExportCall> preSoftBonusExportCallList) {
        Set<String> phoneSet = new HashSet<>();
        for (PreSoftBonusExportCall call : preSoftBonusExportCallList) {
            phoneSet.add(call.getPhoneNumber());
        }
        List<String> phoneList = new ArrayList<>(phoneSet);

        return preSoftBonusReportCrmDao.getClientPhoneListByPhonesWithOnlyActiveDebt(phoneList);
    }

    @Transactional("transactionManagerCRM")
    List<ClientPhone> getClientPhoneList(List<CollectionPayment> collectionPaymentList) {
        Set<Long> clientIdSet = new HashSet<>();
        for (CollectionPayment collectionPayment : collectionPaymentList) {
            clientIdSet.add(collectionPayment.getClientId());
        }
        List<Long> clientIdList = new ArrayList<>(clientIdSet);

        return preSoftBonusReportCrmDao.getClientPhoneList(clientIdList);
    }

    @Transactional("transactionManagerCRM")
    List<BackUserAccountShortly> getBackUserAccountShortlyList() {
        return preSoftBonusReportCrmDao.getBackUserAccountShortlyList();
    }

    @Transactional("transactionManagerCRM")
    List<ExportPaymentWithPaidFees> getExportPaymentWithPaidFeesList(LocalDate dateFrom, LocalDate dateTo) {
        return exportPaymentsDao.getExportPaymentWithPaidList(dateFrom, dateTo);
    }

    private void addTotalRowObjectToPaymentWithPaidFeesList(List<ExportPaymentWithPaidFees> exportPaymentWithPaidFeesList) {
        ExportPaymentWithPaidFees paymentTotal = new ExportPaymentWithPaidFees();
        paymentTotal.setTotalRow(true);
        for (ExportPaymentWithPaidFees payment : exportPaymentWithPaidFeesList) {
            if (!payment.isTotalRow()) {

                if (payment.getBalance() != null) {
                    if (paymentTotal.getBalance() == null) {
                        paymentTotal.setBalance(payment.getBalance());
                    } else {
                        paymentTotal.setBalance(paymentTotal.getBalance()
                                .add(payment.getBalance()));
                    }
                }
                if (payment.getPaidForDate() != null) {
                    if (paymentTotal.getPaidForDate() == null) {
                        paymentTotal.setPaidForDate(payment.getPaidForDate());
                    } else {
                        paymentTotal.setPaidForDate(paymentTotal.getPaidForDate().add(payment.getPaidForDate()));
                    }
                }
                if (payment.getPaidProlongationForDate() != null) {
                    if (paymentTotal.getPaidProlongationForDate() == null) {
                        paymentTotal.setPaidProlongationForDate(payment.getPaidProlongationForDate());
                    } else {
                        paymentTotal.setPaidProlongationForDate(paymentTotal.getPaidProlongationForDate()
                                .add(payment.getPaidProlongationForDate()));
                    }
                }
                if (payment.getPaidConsultingForDate() != null) {
                    if (paymentTotal.getPaidConsultingForDate() == null) {
                        paymentTotal.setPaidConsultingForDate(payment.getPaidConsultingForDate());
                    } else {
                        paymentTotal.setPaidConsultingForDate(paymentTotal.getPaidConsultingForDate()
                                .add(payment.getPaidConsultingForDate()));
                    }
                }
                if (payment.getPaidCollectionFeeForDate() != null) {
                    if (paymentTotal.getPaidCollectionFeeForDate() == null) {
                        paymentTotal.setPaidCollectionFeeForDate(payment.getPaidCollectionFeeForDate());
                    } else {
                        paymentTotal.setPaidCollectionFeeForDate(paymentTotal.getPaidCollectionFeeForDate()
                                .add(payment.getPaidCollectionFeeForDate()));
                    }
                }
                if (payment.getPaidInterestForDate() != null) {
                    if (paymentTotal.getPaidInterestForDate() == null) {
                        paymentTotal.setPaidInterestForDate(payment.getPaidInterestForDate());
                    } else {
                        paymentTotal.setPaidInterestForDate(paymentTotal.getPaidInterestForDate()
                                .add(payment.getPaidInterestForDate()));
                    }
                }
                if (payment.getPaidPenaltyInterestForDate() != null) {
                    if (paymentTotal.getPaidPenaltyInterestForDate() == null) {
                        paymentTotal.setPaidPenaltyInterestForDate(payment.getPaidPenaltyInterestForDate());
                    } else {
                        paymentTotal.setPaidPenaltyInterestForDate(paymentTotal.getPaidPenaltyInterestForDate()
                                .add(payment.getPaidPenaltyInterestForDate()));
                    }
                }
                if (payment.getPaidPrincipalForDate() != null) {
                    if (paymentTotal.getPaidPrincipalForDate() == null) {
                        paymentTotal.setPaidPrincipalForDate(payment.getPaidPrincipalForDate());
                    } else {
                        paymentTotal.setPaidPrincipalForDate(paymentTotal.getPaidPrincipalForDate()
                                .add(payment.getPaidPrincipalForDate()));
                    }
                }
                if (payment.getPaidTotalIncomeBroker() != null) {
                    if (paymentTotal.getPaidTotalIncomeBroker() == null) {
                        paymentTotal.setPaidTotalIncomeBroker(payment.getPaidTotalIncomeBroker());
                    } else {
                        paymentTotal.setPaidTotalIncomeBroker(paymentTotal.getPaidTotalIncomeBroker()
                                .add(payment.getPaidTotalIncomeBroker()));
                    }
                }
                if (payment.getRevenueBroker() != null) {
                    if (paymentTotal.getRevenueBroker() == null) {
                        paymentTotal.setRevenueBroker(payment.getRevenueBroker());
                    } else {
                        paymentTotal.setRevenueBroker(paymentTotal.getRevenueBroker()
                                .add(payment.getRevenueBroker()));
                    }
                }
                if (payment.getOutgoingVATBroker() != null) {
                    if (paymentTotal.getOutgoingVATBroker() == null) {
                        paymentTotal.setOutgoingVATBroker(payment.getOutgoingVATBroker());
                    } else {
                        paymentTotal.setOutgoingVATBroker(paymentTotal.getOutgoingVATBroker()
                                .add(payment.getOutgoingVATBroker()));
                    }
                }
                if (payment.getPaidTotalIncomePawnShop() != null) {
                    if (paymentTotal.getPaidTotalIncomePawnShop() == null) {
                        paymentTotal.setPaidTotalIncomePawnShop(payment.getPaidTotalIncomePawnShop());
                    } else {
                        paymentTotal.setPaidTotalIncomePawnShop(paymentTotal.getPaidTotalIncomePawnShop()
                                .add(payment.getPaidTotalIncomePawnShop()));
                    }
                }
                if (payment.getRevenuePawnShop() != null) {
                    if (paymentTotal.getRevenuePawnShop() == null) {
                        paymentTotal.setRevenuePawnShop(payment.getRevenuePawnShop());
                    } else {
                        paymentTotal.setRevenuePawnShop(paymentTotal.getRevenuePawnShop()
                                .add(payment.getRevenuePawnShop()));
                    }
                }
                if (payment.getOutgoingVATPawnShop() != null) {
                    if (paymentTotal.getOutgoingVATPawnShop() == null) {
                        paymentTotal.setOutgoingVATPawnShop(payment.getOutgoingVATPawnShop());
                    } else {
                        paymentTotal.setOutgoingVATPawnShop(paymentTotal.getOutgoingVATPawnShop()
                                .add(payment.getOutgoingVATPawnShop()));
                    }
                }
            }
        }
        exportPaymentWithPaidFeesList.sort(Comparator.comparing(ExportPaymentWithPaidFees::getDate));
        exportPaymentWithPaidFeesList.add(paymentTotal);
    }

    private void addAdditionalFieldsToPaymentWithPaidFeesList(List<ExportPaymentWithPaidFees> exportPaymentWithPaidFeesList) {
        for (ExportPaymentWithPaidFees paymentWithPaidFees : exportPaymentWithPaidFeesList) {
            if (!paymentWithPaidFees.isTotalRow()) {
                paymentWithPaidFees
                        .setPaidTotalIncomeBroker(paymentWithPaidFees.getPaidProlongationForDate()
                                .add(paymentWithPaidFees.getPaidConsultingForDate())
                                .add(paymentWithPaidFees.getPaidCollectionFeeForDate()));
                paymentWithPaidFees
                        .setRevenueBroker(paymentWithPaidFees.getPaidTotalIncomeBroker()
                                .divide(BigDecimal.valueOf(1.1),10, RoundingMode.HALF_UP));
                paymentWithPaidFees
                        .setOutgoingVATBroker(paymentWithPaidFees.getRevenueBroker()
                                .multiply(BigDecimal.valueOf(0.1)));
                paymentWithPaidFees
                        .setPaidTotalIncomePawnShop(paymentWithPaidFees.getPaidInterestForDate()
                                .add(paymentWithPaidFees.getPaidPenaltyInterestForDate()));
                paymentWithPaidFees
                        .setRevenuePawnShop(paymentWithPaidFees.getPaidTotalIncomePawnShop()
                                .divide(BigDecimal.valueOf(1.1), 10, RoundingMode.HALF_UP));
                paymentWithPaidFees
                        .setOutgoingVATPawnShop(paymentWithPaidFees.getRevenuePawnShop()
                                .multiply(BigDecimal.valueOf(0.1)));
            }
        }
    }

    private List<ExportPaymentsWithFeesGroupedByDate> createListPaymentsWithFeesGroupedByDate(List<ExportPaymentWithPaidFees> exportPaymentWithPaidFeesList) {
        List<ExportPaymentsWithFeesGroupedByDate> paymentsWithFeesGroupedByDateList = new ArrayList<>();
        Set<LocalDate> dateSet = new HashSet<>();
        for(ExportPaymentWithPaidFees paymentWithPaidFees : exportPaymentWithPaidFeesList) {
            if (!paymentWithPaidFees.isTotalRow()) {
                dateSet.add(paymentWithPaidFees.getDate());
            }
        }
        for (LocalDate date : dateSet) {
            ExportPaymentsWithFeesGroupedByDate paymentsWithFeesGroupedByDate = new ExportPaymentsWithFeesGroupedByDate();
            paymentsWithFeesGroupedByDate.setDate(date);
            paymentsWithFeesGroupedByDate.setTotal(false);
            for (ExportPaymentWithPaidFees paymentWithPaidFees : exportPaymentWithPaidFeesList) {
                if (!paymentWithPaidFees.isTotalRow()) {

                    if (date.equals(paymentWithPaidFees.getDate())) {

                        if (paymentWithPaidFees.getRevenueBroker() != null) {
                            if (paymentsWithFeesGroupedByDate.getRevenueBroker() == null) {
                                paymentsWithFeesGroupedByDate.setRevenueBroker(paymentWithPaidFees.getRevenueBroker());
                            } else {
                                paymentsWithFeesGroupedByDate
                                        .setRevenueBroker(paymentsWithFeesGroupedByDate
                                                .getRevenueBroker().add(paymentWithPaidFees.getRevenueBroker()));
                            }
                        }

                        if (paymentWithPaidFees.getOutgoingVATBroker() != null) {
                            if (paymentsWithFeesGroupedByDate.getOutgoingVATBroker() == null) {
                                paymentsWithFeesGroupedByDate.setOutgoingVATBroker(paymentWithPaidFees.getOutgoingVATBroker());
                            } else {
                                paymentsWithFeesGroupedByDate
                                        .setOutgoingVATBroker(paymentsWithFeesGroupedByDate.getOutgoingVATBroker()
                                                .add(paymentWithPaidFees.getOutgoingVATBroker()));
                            }
                        }

                        if (paymentWithPaidFees.getRevenuePawnShop() != null) {
                            if (paymentsWithFeesGroupedByDate.getRevenuePawnShop() == null) {
                                paymentsWithFeesGroupedByDate.setRevenuePawnShop(paymentWithPaidFees.getRevenuePawnShop());
                            } else {
                                paymentsWithFeesGroupedByDate
                                        .setRevenuePawnShop(paymentsWithFeesGroupedByDate
                                                .getRevenuePawnShop().add(paymentWithPaidFees.getRevenuePawnShop()));
                            }
                        }

                        if (paymentWithPaidFees.getOutgoingVATPawnShop() != null) {
                            if (paymentsWithFeesGroupedByDate.getOutgoingVATPawnShop() == null) {
                                paymentsWithFeesGroupedByDate.setOutgoingVATPawnShop(paymentWithPaidFees.getOutgoingVATPawnShop());
                            } else {
                                paymentsWithFeesGroupedByDate
                                        .setOutgoingVATPawnShop(paymentsWithFeesGroupedByDate.getOutgoingVATPawnShop()
                                                .add(paymentWithPaidFees.getOutgoingVATPawnShop()));
                            }
                        }

                        if (paymentWithPaidFees.getPaidPrincipalForDate() != null) {
                            if (paymentsWithFeesGroupedByDate.getPaidPrincipal() == null) {
                                paymentsWithFeesGroupedByDate.setPaidPrincipal(paymentWithPaidFees.getPaidPrincipalForDate());
                            } else {
                                paymentsWithFeesGroupedByDate
                                        .setPaidPrincipal(paymentsWithFeesGroupedByDate.getPaidPrincipal()
                                                .add(paymentWithPaidFees.getPaidPrincipalForDate()));
                            }
                        }
                    }
                }
            }
            if (paymentsWithFeesGroupedByDate.getRevenueBroker() == null) {
                paymentsWithFeesGroupedByDate.setTotalRevenue(paymentsWithFeesGroupedByDate.getRevenuePawnShop());
            } else if (paymentsWithFeesGroupedByDate.getRevenuePawnShop() == null) {
                paymentsWithFeesGroupedByDate.setTotalRevenue(paymentsWithFeesGroupedByDate.getRevenueBroker());
            } else {
                paymentsWithFeesGroupedByDate.setTotalRevenue(paymentsWithFeesGroupedByDate.getRevenueBroker()
                        .add(paymentsWithFeesGroupedByDate.getRevenuePawnShop()));
            }
            if (paymentsWithFeesGroupedByDate.getOutgoingVATBroker() == null) {
                paymentsWithFeesGroupedByDate.setTotalOutgoingVAT(paymentsWithFeesGroupedByDate.getOutgoingVATPawnShop());
            } else if (paymentsWithFeesGroupedByDate.getOutgoingVATPawnShop() == null) {
                paymentsWithFeesGroupedByDate.setTotalOutgoingVAT(paymentsWithFeesGroupedByDate.getOutgoingVATBroker());
            } else {
                paymentsWithFeesGroupedByDate.setTotalOutgoingVAT(paymentsWithFeesGroupedByDate.getOutgoingVATBroker()
                        .add(paymentsWithFeesGroupedByDate.getOutgoingVATPawnShop()));
            }
            paymentsWithFeesGroupedByDateList.add(paymentsWithFeesGroupedByDate);
        }
        ExportPaymentsWithFeesGroupedByDate paymentsWithFeesGroupedByDate = new ExportPaymentsWithFeesGroupedByDate();
        paymentsWithFeesGroupedByDate.setTotal(true);
        for (ExportPaymentsWithFeesGroupedByDate payments : paymentsWithFeesGroupedByDateList) {
            if (payments.getRevenueBroker() != null) {
                if (paymentsWithFeesGroupedByDate.getRevenueBroker() == null) {
                    paymentsWithFeesGroupedByDate.setRevenueBroker(payments.getRevenueBroker());
                } else {
                    paymentsWithFeesGroupedByDate.setRevenueBroker(paymentsWithFeesGroupedByDate.getRevenueBroker()
                            .add(payments.getRevenueBroker()));
                }
            }

            if (payments.getOutgoingVATBroker() != null) {
                if (paymentsWithFeesGroupedByDate.getOutgoingVATBroker() == null) {
                    paymentsWithFeesGroupedByDate.setOutgoingVATBroker(payments.getOutgoingVATBroker());
                } else {
                    paymentsWithFeesGroupedByDate.setOutgoingVATBroker(paymentsWithFeesGroupedByDate.getOutgoingVATBroker()
                            .add(payments.getOutgoingVATBroker()));
                }
            }

            if (payments.getRevenuePawnShop() != null) {
                if (paymentsWithFeesGroupedByDate.getRevenuePawnShop() == null) {
                    paymentsWithFeesGroupedByDate.setRevenuePawnShop(payments.getRevenuePawnShop());
                } else {
                    paymentsWithFeesGroupedByDate
                            .setRevenuePawnShop(paymentsWithFeesGroupedByDate.getRevenuePawnShop()
                                    .add(payments.getRevenuePawnShop()));
                }
            }

            if (payments.getOutgoingVATPawnShop() != null) {
                if (paymentsWithFeesGroupedByDate.getOutgoingVATPawnShop() == null) {
                    paymentsWithFeesGroupedByDate.setOutgoingVATPawnShop(payments.getOutgoingVATPawnShop());
                } else {
                    paymentsWithFeesGroupedByDate
                            .setOutgoingVATPawnShop(paymentsWithFeesGroupedByDate.getOutgoingVATPawnShop()
                                    .add(payments.getOutgoingVATPawnShop()));
                }
            }

            if (payments.getPaidPrincipal() != null) {
                if (paymentsWithFeesGroupedByDate.getPaidPrincipal() == null) {
                    paymentsWithFeesGroupedByDate.setPaidPrincipal(payments.getPaidPrincipal());
                } else {
                    paymentsWithFeesGroupedByDate.setPaidPrincipal(paymentsWithFeesGroupedByDate.getPaidPrincipal()
                            .add(payments.getPaidPrincipal()));
                }
            }

            if (payments.getTotalRevenue() != null) {
                if (paymentsWithFeesGroupedByDate.getTotalRevenue() == null) {
                    paymentsWithFeesGroupedByDate.setTotalRevenue(payments.getTotalRevenue());
                } else {
                    paymentsWithFeesGroupedByDate
                            .setTotalRevenue(paymentsWithFeesGroupedByDate.getTotalRevenue()
                                    .add(payments.getTotalRevenue()));
                }
            }

            if (payments.getTotalOutgoingVAT() != null) {
                if (paymentsWithFeesGroupedByDate.getTotalOutgoingVAT() == null) {
                    paymentsWithFeesGroupedByDate.setTotalOutgoingVAT(payments.getTotalOutgoingVAT());
                } else {
                    paymentsWithFeesGroupedByDate
                            .setTotalOutgoingVAT(paymentsWithFeesGroupedByDate.getTotalOutgoingVAT()
                                    .add(payments.getTotalOutgoingVAT()));
                }
            }

        }
        paymentsWithFeesGroupedByDateList.sort(Comparator.comparing(ExportPaymentsWithFeesGroupedByDate::getDate));
        paymentsWithFeesGroupedByDateList.add(paymentsWithFeesGroupedByDate);
        return paymentsWithFeesGroupedByDateList;
    }


    public void changeExportPaymentsWithFeesListForOptimiseVAT(List<ExportPaymentWithPaidFees> exportPaymentWithPaidFeesList) {
        BigDecimal percentForOptimize = BigDecimal
                .valueOf(Double
                        .parseDouble(systemSettingService
                                .getSystemSettingValueByTitle(String
                                        .valueOf(SystemSettingsNameEnum
                                                .OPTIMIZE_PERCENT_FOR_PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT))))
                .divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);

        for (ExportPaymentWithPaidFees payment : exportPaymentWithPaidFeesList) {
            if (!payment.isTotalRow()) {
                BigDecimal optimizeAmount = BigDecimal.valueOf(0.00);
                if (payment.getPaidProlongationForDate() != null &&
                        !payment.getPaidProlongationForDate().equals(BigDecimal.ZERO)) {
                    BigDecimal amount =
                            payment.getPaidProlongationForDate().multiply(percentForOptimize);
                    payment.setPaidProlongationForDate(payment.getPaidProlongationForDate()
                            .subtract(amount));
                    optimizeAmount = optimizeAmount.add(amount);
                }
                if (payment.getPaidConsultingForDate() != null &&
                !payment.getPaidConsultingForDate().equals(BigDecimal.ZERO)) {
                    BigDecimal amount =
                            payment.getPaidConsultingForDate().multiply(percentForOptimize);
                    payment.setPaidConsultingForDate(payment.getPaidConsultingForDate().subtract(amount));
                    optimizeAmount = optimizeAmount.add(amount);
                }
                if (payment.getPaidCollectionFeeForDate() != null &&
                        !payment.getPaidCollectionFeeForDate().equals(BigDecimal.ZERO)) {
                    BigDecimal amount =
                            payment.getPaidCollectionFeeForDate().multiply(percentForOptimize);
                    payment.setPaidCollectionFeeForDate(payment.getPaidCollectionFeeForDate().subtract(amount));
                    optimizeAmount = optimizeAmount.add(amount);
                }
                payment.setPaidPrincipalForDate(payment.getPaidPrincipalForDate().add(optimizeAmount));
            }
        }
    }



    private void updatePtpCallsAndAddNewInfo(List<PreSoftActivePromissedPaymentsForAgent> ptpCalls) {
        List<Long> clientIdList = new ArrayList<>();
        for (PreSoftActivePromissedPaymentsForAgent call : ptpCalls) {
            clientIdList.add(call.getClientId());
        }

        List<ClientWithIDAndDebtId> clientWithIDAndDebtIdList = getClientWithIDAndActiveDebtIdList(clientIdList);

        for (PreSoftActivePromissedPaymentsForAgent ptp : ptpCalls) {
            for (ClientWithIDAndDebtId client : clientWithIDAndDebtIdList) {
                if (ptp.getClientId().equals(client.getClientId())) {
                    ptp.setDebtId(client.getDebtId());
                    ptp.setDaysOverdue(client.getDaysOverdue());
                    ptp.setCurrentDebt(client.getCurrentDebtAmount());
                }
            }
        }
    }


}
