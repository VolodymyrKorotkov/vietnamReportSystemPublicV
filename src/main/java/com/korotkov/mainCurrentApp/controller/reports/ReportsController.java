package com.korotkov.mainCurrentApp.controller.reports;

import com.korotkov.creditCRM.model.clients.ExportClientDocumentForCheckingInsuranceReportObject;
import com.korotkov.creditCRM.model.collection.preSoftBonusReport.PreSoftResultedPTPCallsReportObject;
import com.korotkov.creditCRM.model.forRequest.DatesRequest;
import com.korotkov.creditCRM.model.forRequest.DatesRequestAndTwoIntegers;
import com.korotkov.creditCRM.model.loansInfo.ExportLoansAndPaymentByDateReportObject;
import com.korotkov.creditCRM.model.loansInfo.ExportLoansInfoExpiredInfoReportObject;
import com.korotkov.creditCRM.model.reportsCreditConveyor.*;
import com.korotkov.creditCRM.model.payments.ExportPaymentsWithPaidFeesReportObject;
import com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment.AnalyticalReportRepaymentObject;
import com.korotkov.mainCurrentApp.enums.SystemSettingsNameEnum;
import com.korotkov.mainCurrentApp.service.reportBuilder.ReportBuilderService;
import com.korotkov.mainCurrentApp.service.systemSetting.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.ZoneId;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Controller
@SessionAttributes(value = "reportObject")
public class ReportsController {
    ReportBuilderService reportBuilderService;
    SystemSettingService systemSettingService;

    @Autowired
    public void setSystemSettingService(SystemSettingService systemSettingService) {
        this.systemSettingService = systemSettingService;
    }

    @Autowired
    public void setReportBuilderService(ReportBuilderService reportBuilderService) {
        this.reportBuilderService = reportBuilderService;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_PHONE_NUMBERS_OF_PASSIVE_CLIENTS_FOR_SMS", method = RequestMethod.GET)
    public ModelAndView exportPassiveClientsForSmsReport(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reportName", "EXPORT_LIST_PHONE_NUMBERS_OF_PASSIVE_CLIENTS_FOR_SMS");
        modelAndView.addObject("description", "EXPORT_LIST_PHONE_NUMBERS_OF_PASSIVE_CLIENTS_FOR_SMS.description");
        modelAndView.setViewName("reports/templateReportsWithOnlyLink");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_PHONE_NUMBERS_OF_PASSIVE_CLIENTS_FOR_SMS", method = RequestMethod.POST)
    public ModelAndView exportPassiveClientsForSmsReportProcess() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("urlBack", "/reports/EXPORT_LIST_PHONE_NUMBERS_OF_PASSIVE_CLIENTS_FOR_SMS");
        modelAndView.addObject("urlPost", "/reports/EXPORT_LIST_PHONE_NUMBERS_OF_PASSIVE_CLIENTS_FOR_SMS/export");
        modelAndView.addObject("reportObject", reportBuilderService.getExportPassiveClientsForSMSReportObject());
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_PHONE_NUMBERS_OF_PASSIVE_CLIENTS_FOR_SMS/export", method = RequestMethod.POST)
    public ModelAndView exportPassiveClientsForSmsReportExport(
            @ModelAttribute("reportObject") ExportPassiveClientsForSMSReportObject object) {
        return new ModelAndView("exportPassiveClientsForSmsReport", "modelObject", object);
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/PTP_PRESOFT_RESULTED_CALLS", method = RequestMethod.GET)
    public ModelAndView ptpPreSoftResultedCallsReport(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reportName", "PTP_PRESOFT_RESULTED_CALLS");
        modelAndView.addObject("description", "PTP_PRESOFT_RESULTED_CALLS.description");
        modelAndView.setViewName("reports/templateReportsWithOnlyLink");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/PTP_PRESOFT_RESULTED_CALLS", method = RequestMethod.POST)
    public ModelAndView ptpPreSoftResultedCallsReportProcess() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("urlBack", "/reports/PTP_PRESOFT_RESULTED_CALLS");
        modelAndView.addObject("urlPost", "/reports/PTP_PRESOFT_RESULTED_CALLS/export");
        modelAndView.addObject("reportObject", reportBuilderService.buildReportOfResultedPTPCalls());
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/PTP_PRESOFT_RESULTED_CALLS/export", method = RequestMethod.POST)
    public ModelAndView ptpPreSoftResultedCallsReportExport(
            @ModelAttribute("reportObject") PreSoftResultedPTPCallsReportObject object) {
        return new ModelAndView("exportPreSoftPtPCalls", "modelObject", object);
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/CLIENT_DOCUMENTS_EXPORT_FOR_CHECK_INSURANCE", method = RequestMethod.GET)
    public ModelAndView exportClientDocumentsForCheckingInsurance(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reportName", "CLIENT_DOCUMENTS_EXPORT_FOR_CHECK_INSURANCE");
        modelAndView.addObject("description", "CLIENT_DOCUMENTS_EXPORT_FOR_CHECK_INSURANCE.description");
        modelAndView.setViewName("reports/templateReportsWithOnlyLink");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/CLIENT_DOCUMENTS_EXPORT_FOR_CHECK_INSURANCE", method = RequestMethod.POST)
    public ModelAndView exportClientDocumentsForCheckingInsuranceProcess() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("urlBack", "/reports/CLIENT_DOCUMENTS_EXPORT_FOR_CHECK_INSURANCE");
        modelAndView.addObject("urlPost", "/reports/CLIENT_DOCUMENTS_EXPORT_FOR_CHECK_INSURANCE/export");
        modelAndView.addObject("reportObject", reportBuilderService.getClientDocumentForCheckInsuranceReport());
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/CLIENT_DOCUMENTS_EXPORT_FOR_CHECK_INSURANCE/export", method = RequestMethod.POST)
    public ModelAndView exportClientDocumentsForCheckingInsuranceExport(
            @ModelAttribute("reportObject") ExportClientDocumentForCheckingInsuranceReportObject object) {
        return new ModelAndView("exportClientDocumentsForCheckInsuranceReport", "modelObject", object);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/PAYMENTS_EXPORT_WITH_PAID_FEES", method = RequestMethod.GET)
    public ModelAndView paymentsExportWithPaidFeesPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "PAYMENTS_EXPORT_WITH_PAID_FEES");
        modelAndView.addObject("description", "PAYMENTS_EXPORT_WITH_PAID_FEES.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/APPS_AND_LOANS_WITH_SOURCE", method = RequestMethod.GET)
    public ModelAndView appsAndLoansWithSourcePageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "APPS_AND_LOANS_WITH_SOURCE");
        modelAndView.addObject("description", "APPS_AND_LOANS_WITH_SOURCE.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).CC_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_DEACTIVATED_PAYMENTS", method = RequestMethod.GET)
    public ModelAndView exportDeactivatedPaymentsView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "EXPORT_LIST_DEACTIVATED_PAYMENTS");
        modelAndView.addObject("description", "EXPORT_LIST_DEACTIVATED_PAYMENTS.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).CC_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_DEACTIVATED_PAYMENTS", method = RequestMethod.POST)
    public ModelAndView exportDeactivatedPaymentsProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "EXPORT_LIST_DEACTIVATED_PAYMENTS");
            modelAndView.addObject("description", "EXPORT_LIST_DEACTIVATED_PAYMENTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "EXPORT_LIST_DEACTIVATED_PAYMENTS");
            modelAndView.addObject("description", "EXPORT_LIST_DEACTIVATED_PAYMENTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "EXPORT_LIST_DEACTIVATED_PAYMENTS");
            modelAndView.addObject("description", "EXPORT_LIST_DEACTIVATED_PAYMENTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "EXPORT_LIST_DEACTIVATED_PAYMENTS");
            modelAndView.addObject("description", "EXPORT_LIST_DEACTIVATED_PAYMENTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/EXPORT_LIST_DEACTIVATED_PAYMENTS");
        modelAndView.addObject("urlPost", "/reports/EXPORT_LIST_DEACTIVATED_PAYMENTS/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportDeactivatedPaymentsReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).CC_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_DEACTIVATED_PAYMENTS/export", method = RequestMethod.POST)
    public ModelAndView exportDeactivatedPaymentsExport(
            @ModelAttribute("reportObject") ExportDeactivatedPaymentsReportObject reportObject) {
        return new ModelAndView("exportDeactivatedPaymentsReport", "modelObject", reportObject);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).CC_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_REFUND_OVERPAYMENTS", method = RequestMethod.GET)
    public ModelAndView exportRefundOverpaymentsPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "EXPORT_LIST_REFUND_OVERPAYMENTS");
        modelAndView.addObject("description", "EXPORT_LIST_REFUND_OVERPAYMENTS.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).CC_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_REFUND_OVERPAYMENTS", method = RequestMethod.POST)
    public ModelAndView exportRefundOverpaymentsProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "EXPORT_LIST_REFUND_OVERPAYMENTS");
            modelAndView.addObject("description", "EXPORT_LIST_REFUND_OVERPAYMENTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "EXPORT_LIST_REFUND_OVERPAYMENTS");
            modelAndView.addObject("description", "EXPORT_LIST_REFUND_OVERPAYMENTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "EXPORT_LIST_REFUND_OVERPAYMENTS");
            modelAndView.addObject("description", "EXPORT_LIST_REFUND_OVERPAYMENTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "EXPORT_LIST_REFUND_OVERPAYMENTS");
            modelAndView.addObject("description", "EXPORT_LIST_REFUND_OVERPAYMENTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/EXPORT_LIST_REFUND_OVERPAYMENTS");
        modelAndView.addObject("urlPost", "/reports/EXPORT_LIST_REFUND_OVERPAYMENTS/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportRefundOverpaymentReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).CC_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_REFUND_OVERPAYMENTS/export", method = RequestMethod.POST)
    public ModelAndView exportRefundOverpaymentsExport(
            @ModelAttribute("reportObject") ExportRefundOverpaymentReportObject reportObject) {
        return new ModelAndView("exportRefundOverpaymentsReport", "modelObject", reportObject);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_RESULTS_OF_APPLICATION_BY_DATE", method = RequestMethod.GET)
    public ModelAndView exportApplicationInfoByDatePageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "EXPORT_RESULTS_OF_APPLICATION_BY_DATE");
        modelAndView.addObject("description", "EXPORT_RESULTS_OF_APPLICATION_BY_DATE.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_RESULTS_OF_APPLICATION_BY_DATE", method = RequestMethod.POST)
    public ModelAndView exportApplicationInfoByDateProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "EXPORT_RESULTS_OF_APPLICATION_BY_DATE");
            modelAndView.addObject("description", "EXPORT_RESULTS_OF_APPLICATION_BY_DATE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "EXPORT_RESULTS_OF_APPLICATION_BY_DATE");
            modelAndView.addObject("description", "EXPORT_RESULTS_OF_APPLICATION_BY_DATE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "EXPORT_RESULTS_OF_APPLICATION_BY_DATE");
            modelAndView.addObject("description", "EXPORT_RESULTS_OF_APPLICATION_BY_DATE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "EXPORT_RESULTS_OF_APPLICATION_BY_DATE");
            modelAndView.addObject("description", "EXPORT_RESULTS_OF_APPLICATION_BY_DATE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/EXPORT_RESULTS_OF_APPLICATION_BY_DATE");
        modelAndView.addObject("urlPost", "/reports/EXPORT_RESULTS_OF_APPLICATION_BY_DATE/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportApplicationsInfoDateReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_RESULTS_OF_APPLICATION_BY_DATE/export", method = RequestMethod.POST)
    public ModelAndView exportApplicationInfoByDateExport(
            @ModelAttribute("reportObject") ExportApplicationsInfoDateReportObject reportObject) {
        return new ModelAndView("exportApplicationsInfoDateReport", "modelObject", reportObject);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/APP_BY_UNDERWRITER_PER_HOUR", method = RequestMethod.GET)
    public ModelAndView exportAppsByUnderwritersPerHourPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "APP_BY_UNDERWRITER_PER_HOUR");
        modelAndView.addObject("description", "APP_BY_UNDERWRITER_PER_HOUR.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/APP_BY_UNDERWRITER_PER_HOUR", method = RequestMethod.POST)
    public ModelAndView exportAppsByUnderwritersPerHourProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "APP_BY_UNDERWRITER_PER_HOUR");
            modelAndView.addObject("description", "APP_BY_UNDERWRITER_PER_HOUR.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "APP_BY_UNDERWRITER_PER_HOUR");
            modelAndView.addObject("description", "APP_BY_UNDERWRITER_PER_HOUR.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "APP_BY_UNDERWRITER_PER_HOUR");
            modelAndView.addObject("description", "APP_BY_UNDERWRITER_PER_HOUR.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "APP_BY_UNDERWRITER_PER_HOUR");
            modelAndView.addObject("description", "APP_BY_UNDERWRITER_PER_HOUR.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/APP_BY_UNDERWRITER_PER_HOUR");
        modelAndView.addObject("urlPost", "/reports/APP_BY_UNDERWRITER_PER_HOUR/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportAppByUnderwriterPerHourReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/APP_BY_UNDERWRITER_PER_HOUR/export", method = RequestMethod.POST)
    public ModelAndView exportAppsByUnderwritersPerHourExport(
            @ModelAttribute("reportObject") ExportAppByUnderwriterPerHourReportObject reportObject) {
        return new ModelAndView("exportAppByUnderwriterPerHourReport", "modelObject", reportObject);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_APPS_BY_UNDERWRITERS", method = RequestMethod.GET)
    public ModelAndView exportAppsByUnderwritersPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "EXPORT_APPS_BY_UNDERWRITERS");
        modelAndView.addObject("description", "EXPORT_APPS_BY_UNDERWRITERS.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_APPS_BY_UNDERWRITERS", method = RequestMethod.POST)
    public ModelAndView exportAppsByUnderwritersProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "EXPORT_APPS_BY_UNDERWRITERS");
            modelAndView.addObject("description", "EXPORT_APPS_BY_UNDERWRITERS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "EXPORT_APPS_BY_UNDERWRITERS");
            modelAndView.addObject("description", "EXPORT_APPS_BY_UNDERWRITERS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "EXPORT_APPS_BY_UNDERWRITERS");
            modelAndView.addObject("description", "EXPORT_APPS_BY_UNDERWRITERS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "EXPORT_APPS_BY_UNDERWRITERS");
            modelAndView.addObject("description", "EXPORT_APPS_BY_UNDERWRITERS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/EXPORT_APPS_BY_UNDERWRITERS");
        modelAndView.addObject("urlPost", "/reports/EXPORT_APPS_BY_UNDERWRITERS/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportAppProcessedByUnderwriterReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_APPS_BY_UNDERWRITERS/export", method = RequestMethod.POST)
    public ModelAndView exportAppsByUnderwritersExport(
            @ModelAttribute("reportObject") ExportAppProcessedByUnderwriterReportObject reportObject) {
        return new ModelAndView("exportAppsProcessedByUnderwriterReport", "modelObject", reportObject);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_AGENT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_UNDERWRITERS_RESULTS", method = RequestMethod.GET)
    public ModelAndView exportUnderwritersResultsPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "EXPORT_UNDERWRITERS_RESULTS");
        modelAndView.addObject("description", "EXPORT_UNDERWRITERS_RESULTS.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_AGENT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_UNDERWRITERS_RESULTS", method = RequestMethod.POST)
    public ModelAndView exportUnderwritersResultsProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "EXPORT_UNDERWRITERS_RESULTS");
            modelAndView.addObject("description", "EXPORT_UNDERWRITERS_RESULTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "EXPORT_UNDERWRITERS_RESULTS");
            modelAndView.addObject("description", "EXPORT_UNDERWRITERS_RESULTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "EXPORT_UNDERWRITERS_RESULTS");
            modelAndView.addObject("description", "EXPORT_UNDERWRITERS_RESULTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "EXPORT_UNDERWRITERS_RESULTS");
            modelAndView.addObject("description", "EXPORT_UNDERWRITERS_RESULTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/EXPORT_UNDERWRITERS_RESULTS");
        modelAndView.addObject("urlPost", "/reports/EXPORT_UNDERWRITERS_RESULTS/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getUnderwritersResultWithAppReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_AGENT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_UNDERWRITERS_RESULTS/export", method = RequestMethod.POST)
    public ModelAndView exportUnderwritersResultsExport(
            @ModelAttribute("reportObject") UnderwritersResultWithAppReportObject reportObject) {
        return new ModelAndView("exportExcelUnderwriterResultWithAppReport", "modelObject", reportObject);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/BIG_REPORT_COLLECTION_PAYMENT", method = RequestMethod.GET)
    public ModelAndView exportBigReportForPaymentOnStagesPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequestAndTwoIntegers());
        modelAndView.setViewName("/reports/bigReportForPaymentOnStages");
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/BIG_REPORT_COLLECTION_PAYMENT", method = RequestMethod.POST)
    public ModelAndView exportBigReportForPaymentOnStagesProcess(@ModelAttribute("datesRequest") DatesRequestAndTwoIntegers datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getDateFrom() == null) {
            modelAndView.setViewName("/reports/bigReportForPaymentOnStages");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getDateTo() == null || datesRequest.getDateTo().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE)).minusDays(3))) {
            modelAndView.setViewName("/reports/bigReportForPaymentOnStages");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getInteger1() == null) {
            modelAndView.setViewName("/reports/bigReportForPaymentOnStages");
            modelAndView.addObject("integer1Error", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        if (datesRequest.getInteger2() == null) {
            modelAndView.setViewName("/reports/bigReportForPaymentOnStages");
            modelAndView.addObject("integer2Error", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        if (datesRequest.getDateFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getDateFrom().isAfter(datesRequest.getDateTo())) {
            modelAndView.setViewName("/reports/bigReportForPaymentOnStages");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getDateTo().isAfter(datesRequest.getDateFrom().plusDays(400))) {
            modelAndView.setViewName("/reports/bigReportForPaymentOnStages");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        if (datesRequest.getInteger1() >= datesRequest.getInteger2() || datesRequest.getInteger1() <= 1 ||
        datesRequest.getInteger2() <= 5) {
            modelAndView.setViewName("/reports/bigReportForPaymentOnStages");
            modelAndView.addObject("integer1Error", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }

        modelAndView.addObject("urlBack", "/reports/BIG_REPORT_COLLECTION_PAYMENT");
        modelAndView.addObject("urlPost", "/reports/BIG_REPORT_COLLECTION_PAYMENT/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getAnalyticalReportRepaymentObject(datesRequest.getDateFrom(), datesRequest.getDateTo(),
                        datesRequest.getInteger1(), datesRequest.getInteger2()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/BIG_REPORT_COLLECTION_PAYMENT/export", method = RequestMethod.POST)
    public ModelAndView exportLoanForCrossCheckExport(
            @ModelAttribute("reportObject") AnalyticalReportRepaymentObject reportObject) {
        return new ModelAndView("exportAnalyticalReportRepayment", "modelObject", reportObject);
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_ISSUED_LOAN", method = RequestMethod.GET)
    public ModelAndView exportLoanForCrossCheckPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "EXPORT_LIST_ISSUED_LOAN");
        modelAndView.addObject("description", "EXPORT_LIST_ISSUED_LOAN.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_ISSUED_LOAN", method = RequestMethod.POST)
    public ModelAndView exportLoanForCrossCheckProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "EXPORT_LIST_ISSUED_LOAN");
            modelAndView.addObject("description", "EXPORT_LIST_ISSUED_LOAN.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "EXPORT_LIST_ISSUED_LOAN");
            modelAndView.addObject("description", "EXPORT_LIST_ISSUED_LOAN.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "EXPORT_LIST_ISSUED_LOAN");
            modelAndView.addObject("description", "EXPORT_LIST_ISSUED_LOAN.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "EXPORT_LIST_ISSUED_LOAN");
            modelAndView.addObject("description", "EXPORT_LIST_ISSUED_LOAN.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/EXPORT_LIST_ISSUED_LOAN");
        modelAndView.addObject("urlPost", "/reports/EXPORT_LIST_ISSUED_LOAN/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportIssuedLoanForCrossCheckReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_ISSUED_LOAN/export", method = RequestMethod.POST)
    public ModelAndView exportLoanForCrossCheckExport(
            @ModelAttribute("reportObject") ExportIssuedLoanForCrossCheckReportObject reportObject) {
        return new ModelAndView("exportLoanForCrossCheckerReport", "modelObject", reportObject);
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_ACTIVE_PAYMENTS", method = RequestMethod.GET)
    public ModelAndView exportPaymentForCrossCheckPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "EXPORT_LIST_ACTIVE_PAYMENTS");
        modelAndView.addObject("description", "EXPORT_LIST_ACTIVE_PAYMENTS.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_ACTIVE_PAYMENTS", method = RequestMethod.POST)
    public ModelAndView exportPaymentForCrossCheckProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "EXPORT_LIST_ACTIVE_PAYMENTS");
            modelAndView.addObject("description", "EXPORT_LIST_ACTIVE_PAYMENTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "EXPORT_LIST_ACTIVE_PAYMENTS");
            modelAndView.addObject("description", "EXPORT_LIST_ACTIVE_PAYMENTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "EXPORT_LIST_ACTIVE_PAYMENTS");
            modelAndView.addObject("description", "EXPORT_LIST_ACTIVE_PAYMENTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "EXPORT_LIST_ACTIVE_PAYMENTS");
            modelAndView.addObject("description", "EXPORT_LIST_ACTIVE_PAYMENTS.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/EXPORT_LIST_ACTIVE_PAYMENTS");
        modelAndView.addObject("urlPost", "/reports/EXPORT_LIST_ACTIVE_PAYMENTS/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportPaymentForCrossCheckReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LIST_ACTIVE_PAYMENTS/export", method = RequestMethod.POST)
    public ModelAndView exportPaymentForCrossCheckExport(
            @ModelAttribute("reportObject") ExportPaymentForCrossCheckReportObject reportObject) {
        return new ModelAndView("exportPaymentForCrossCheckerReport", "modelObject", reportObject);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_PAYMENT_BY_DATE_AND_HOUR", method = RequestMethod.GET)
    public ModelAndView exportPaymentByDateAndHourPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "EXPORT_PAYMENT_BY_DATE_AND_HOUR");
        modelAndView.addObject("description", "EXPORT_PAYMENT_BY_DATE_AND_HOUR.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_PAYMENT_BY_DATE_AND_HOUR", method = RequestMethod.POST)
    public ModelAndView exportPaymentByDateAndHourProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "EXPORT_PAYMENT_BY_DATE_AND_HOUR");
            modelAndView.addObject("description", "EXPORT_PAYMENT_BY_DATE_AND_HOUR.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "EXPORT_PAYMENT_BY_DATE_AND_HOUR");
            modelAndView.addObject("description", "EXPORT_PAYMENT_BY_DATE_AND_HOUR.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "EXPORT_PAYMENT_BY_DATE_AND_HOUR");
            modelAndView.addObject("description", "EXPORT_PAYMENT_BY_DATE_AND_HOUR.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "EXPORT_PAYMENT_BY_DATE_AND_HOUR");
            modelAndView.addObject("description", "EXPORT_PAYMENT_BY_DATE_AND_HOUR.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/EXPORT_PAYMENT_BY_DATE_AND_HOUR");
        modelAndView.addObject("urlPost", "/reports/EXPORT_PAYMENT_BY_DATE_AND_HOUR/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportDateHourCountAmountPaymentReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_PAYMENT_BY_DATE_AND_HOUR/export", method = RequestMethod.POST)
    public ModelAndView exportPaymentByDateAndHourExport(
            @ModelAttribute("reportObject") ExportDateHourCountAmountReportObject reportObject) {
        return new ModelAndView("exportExcelDateHourCountAmountPaymentReport", "modelObject", reportObject);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LOAN_BY_DATE_AND_HOUR", method = RequestMethod.GET)
    public ModelAndView exportLoanByDateAndHourPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "EXPORT_LOAN_BY_DATE_AND_HOUR");
        modelAndView.addObject("description", "EXPORT_LOAN_BY_DATE_AND_HOUR.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LOAN_BY_DATE_AND_HOUR", method = RequestMethod.POST)
    public ModelAndView exportLoanByDateAndHourProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "EXPORT_LOAN_BY_DATE_AND_HOUR");
            modelAndView.addObject("description", "EXPORT_LOAN_BY_DATE_AND_HOUR.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "EXPORT_LOAN_BY_DATE_AND_HOUR");
            modelAndView.addObject("description", "EXPORT_LOAN_BY_DATE_AND_HOUR.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "EXPORT_LOAN_BY_DATE_AND_HOUR");
            modelAndView.addObject("description", "EXPORT_LOAN_BY_DATE_AND_HOUR.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "EXPORT_LOAN_BY_DATE_AND_HOUR");
            modelAndView.addObject("description", "EXPORT_LOAN_BY_DATE_AND_HOUR.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/EXPORT_LOAN_BY_DATE_AND_HOUR");
        modelAndView.addObject("urlPost", "/reports/EXPORT_LOAN_BY_DATE_AND_HOUR/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportDateHourCountAmountLoanReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LOAN_BY_DATE_AND_HOUR/export", method = RequestMethod.POST)
    public ModelAndView exportLoanByDateAndHourExport(
            @ModelAttribute("reportObject") ExportDateHourCountAmountReportObject reportObject) {
        return new ModelAndView("exportExcelDateHourCountAmountLoanReport", "modelObject", reportObject);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING", method = RequestMethod.GET)
    public ModelAndView exportLoansAndPaymentsByDatePageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING");
        modelAndView.addObject("description", "EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING", method = RequestMethod.POST)
    public ModelAndView exportLoansAndPaymentsByDateProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING");
            modelAndView.addObject("description", "EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING");
            modelAndView.addObject("description", "EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING");
            modelAndView.addObject("description", "EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING");
            modelAndView.addObject("description", "EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING");
        modelAndView.addObject("urlPost", "/reports/EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportLoansAndPaymentByDateReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LOAN_AND_PAYMENT_BY_DATE_FOR_CROSS_CHECKING/export", method = RequestMethod.POST)
    public ModelAndView exportAppsWithSourceReportExport(
            @ModelAttribute("reportObject") ExportLoansAndPaymentByDateReportObject exportLoansAndPaymentByDateReportObject) {
        return new ModelAndView("exportLoansAndPaymentsGroupByDateReport", "modelObject", exportLoansAndPaymentByDateReportObject);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_APP_LIST_WITH_SOURCE", method = RequestMethod.GET)
    public ModelAndView exportAppsWithSourcePageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "EXPORT_APP_LIST_WITH_SOURCE");
        modelAndView.addObject("description", "EXPORT_APP_LIST_WITH_SOURCE.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_APP_LIST_WITH_SOURCE", method = RequestMethod.POST)
    public ModelAndView exportAppsWithSourceProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "EXPORT_APP_LIST_WITH_SOURCE");
            modelAndView.addObject("description", "EXPORT_APP_LIST_WITH_SOURCE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "EXPORT_APP_LIST_WITH_SOURCE");
            modelAndView.addObject("description", "EXPORT_APP_LIST_WITH_SOURCE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "EXPORT_APP_LIST_WITH_SOURCE");
            modelAndView.addObject("description", "EXPORT_APP_LIST_WITH_SOURCE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "EXPORT_APP_LIST_WITH_SOURCE");
            modelAndView.addObject("description", "EXPORT_APP_LIST_WITH_SOURCE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/EXPORT_APP_LIST_WITH_SOURCE");
        modelAndView.addObject("urlPost", "/reports/EXPORT_APP_LIST_WITH_SOURCE/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportAppsBySourceReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_APP_LIST_WITH_SOURCE/export", method = RequestMethod.POST)
    public ModelAndView exportAppsWithSourceReportExport(
            @ModelAttribute("reportObject") ExportAppsBySourceReportObject exportAppsBySourceReportObject) {
        return new ModelAndView("exportAppsBySourceReport", "modelObject", exportAppsBySourceReportObject);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LOAN_LIST_WITH_SOURCE", method = RequestMethod.GET)
    public ModelAndView exportLoansWithSourcePageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "EXPORT_LOAN_LIST_WITH_SOURCE");
        modelAndView.addObject("description", "EXPORT_LOAN_LIST_WITH_SOURCE.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LOAN_LIST_WITH_SOURCE", method = RequestMethod.POST)
    public ModelAndView exportLoansWithSourceProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "EXPORT_LOAN_LIST_WITH_SOURCE");
            modelAndView.addObject("description", "EXPORT_LOAN_LIST_WITH_SOURCE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "EXPORT_LOAN_LIST_WITH_SOURCE");
            modelAndView.addObject("description", "EXPORT_LOAN_LIST_WITH_SOURCE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "EXPORT_LOAN_LIST_WITH_SOURCE");
            modelAndView.addObject("description", "EXPORT_LOAN_LIST_WITH_SOURCE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "EXPORT_LOAN_LIST_WITH_SOURCE");
            modelAndView.addObject("description", "EXPORT_LOAN_LIST_WITH_SOURCE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/EXPORT_LOAN_LIST_WITH_SOURCE");
        modelAndView.addObject("urlPost", "/reports/EXPORT_LOAN_LIST_WITH_SOURCE/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportLoansBySourceReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/EXPORT_LOAN_LIST_WITH_SOURCE/export", method = RequestMethod.POST)
    public ModelAndView exportLoansWithSourceReportExport(
            @ModelAttribute("reportObject") ExportLoansBySourceReportObject exportLoansBySourceReportObject) {
        return new ModelAndView("exportLoansBySourceReport", "modelObject", exportLoansBySourceReportObject);
    }





    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/APPS_AND_LOANS_WITH_SOURCE", method = RequestMethod.POST)
    public ModelAndView appsAndLoansWithSourceProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "APPS_AND_LOANS_WITH_SOURCE");
            modelAndView.addObject("description", "APPS_AND_LOANS_WITH_SOURCE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "APPS_AND_LOANS_WITH_SOURCE");
            modelAndView.addObject("description", "APPS_AND_LOANS_WITH_SOURCE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "APPS_AND_LOANS_WITH_SOURCE");
            modelAndView.addObject("description", "APPS_AND_LOANS_WITH_SOURCE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "APPS_AND_LOANS_WITH_SOURCE");
            modelAndView.addObject("description", "APPS_AND_LOANS_WITH_SOURCE.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/APPS_AND_LOANS_WITH_SOURCE");
        modelAndView.addObject("urlPost", "/reports/APPS_AND_LOANS_WITH_SOURCE/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getAppAndLoanBySourceReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/APPS_AND_LOANS_WITH_SOURCE/export", method = RequestMethod.POST)
    public ModelAndView appsAndLoansWithSourceReportExport(
            @ModelAttribute("reportObject") AppAndLoanBySourceReportObject appAndLoanBySourceReportObject) {
        return new ModelAndView("exportAppAndLoansWithSourceReport", "modelObject", appAndLoanBySourceReportObject);
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/PAYMENTS_EXPORT_WITH_PAID_FEES", method = RequestMethod.POST)
    public synchronized ModelAndView paymentsExportWithPaidFeesProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "PAYMENTS_EXPORT_WITH_PAID_FEES");
            modelAndView.addObject("description", "PAYMENTS_EXPORT_WITH_PAID_FEES.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "PAYMENTS_EXPORT_WITH_PAID_FEES");
            modelAndView.addObject("description", "PAYMENTS_EXPORT_WITH_PAID_FEES.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "PAYMENTS_EXPORT_WITH_PAID_FEES");
            modelAndView.addObject("description", "PAYMENTS_EXPORT_WITH_PAID_FEES.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "PAYMENTS_EXPORT_WITH_PAID_FEES");
            modelAndView.addObject("description", "PAYMENTS_EXPORT_WITH_PAID_FEES.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/PAYMENTS_EXPORT_WITH_PAID_FEES");
        modelAndView.addObject("urlPost", "/reports/PAYMENTS_EXPORT_WITH_PAID_FEES/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportPaymentsWithFeesReport(datesRequest.getFrom(), datesRequest.getTo(), true));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/PAYMENTS_EXPORT_WITH_PAID_FEES/export", method = RequestMethod.POST)
    public ModelAndView paymentsExportWithPaidFeesReportExport(
            @ModelAttribute("reportObject")ExportPaymentsWithPaidFeesReportObject exportPaymentsWithPaidFeesReportObject) {
        return new ModelAndView("exportExcelPaymentsWithFeesReport", "modelObject", exportPaymentsWithPaidFeesReportObject);
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).T_T," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT", method = RequestMethod.GET)
    public ModelAndView paymentsExportWithPaidFeesIncorrectPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT");
        modelAndView.addObject("description", "PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT.description");
        modelAndView.addObject("additionalInfo",
                systemSettingService.getSystemSettingValueByTitle(String.valueOf(SystemSettingsNameEnum
                        .OPTIMIZE_PERCENT_FOR_PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT)) + "%");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).T_T," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT", method = RequestMethod.POST)
    public synchronized ModelAndView paymentsExportWithPaidFeesIncorrectProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT");
            modelAndView.addObject("description", "PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT");
            modelAndView.addObject("description", "PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT");
            modelAndView.addObject("description", "PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT");
            modelAndView.addObject("description", "PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod100");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT");
        modelAndView.addObject("urlPost", "/reports/PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getExportPaymentsWithFeesReport(datesRequest.getFrom(), datesRequest.getTo(), false));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).T_T," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/PAYMENTS_EXPORT_WITH_PAID_FEES_INCORRECT/export", method = RequestMethod.POST)
    public ModelAndView paymentsExportWithPaidFeesReportIncorrectExport(
            @ModelAttribute("reportObject")ExportPaymentsWithPaidFeesReportObject exportPaymentsWithPaidFeesReportObject) {
        return new ModelAndView("exportExcelPaymentsWithFeesReport", "modelObject", exportPaymentsWithPaidFeesReportObject);
    }


    //


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/LOANS_INFO_EXPIRED_INFO_REPORT", method = RequestMethod.GET)
    public ModelAndView loansInfoExpiredInfoReportPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.addObject("reportName", "LOANS_INFO_EXPIRED_INFO_REPORT");
        modelAndView.addObject("description", "LOANS_INFO_EXPIRED_INFO_REPORT.description");
        modelAndView.setViewName("/reports/templateReportsWithDates");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/LOANS_INFO_EXPIRED_INFO_REPORT", method = RequestMethod.POST)
    public synchronized ModelAndView loansInfoExpiredInfoReportProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if(datesRequest.getFrom() == null) {
            modelAndView.addObject("reportName", "LOANS_INFO_EXPIRED_INFO_REPORT");
            modelAndView.addObject("description", "LOANS_INFO_EXPIRED_INFO_REPORT.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if(datesRequest.getTo() == null) {
            modelAndView.addObject("reportName", "LOANS_INFO_EXPIRED_INFO_REPORT");
            modelAndView.addObject("description", "LOANS_INFO_EXPIRED_INFO_REPORT.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.addObject("reportName", "LOANS_INFO_EXPIRED_INFO_REPORT");
            modelAndView.addObject("description", "LOANS_INFO_EXPIRED_INFO_REPORT.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(400))) {
            modelAndView.addObject("reportName", "LOANS_INFO_EXPIRED_INFO_REPORT");
            modelAndView.addObject("description", "LOANS_INFO_EXPIRED_INFO_REPORT.description");
            modelAndView.setViewName("/reports/templateReportsWithDates");
            modelAndView.addObject("toError", "reportsPage.errorMaxPeriod400");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/LOANS_INFO_EXPIRED_INFO_REPORT");
        modelAndView.addObject("urlPost", "/reports/LOANS_INFO_EXPIRED_INFO_REPORT/export");
        modelAndView.addObject("reportObject", reportBuilderService
                .getLoansInfoExpiredInfoReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/LOANS_INFO_EXPIRED_INFO_REPORT/export", method = RequestMethod.POST)
    public ModelAndView loansInfoExpiredInfoReportExport(
            @ModelAttribute("reportObject") ExportLoansInfoExpiredInfoReportObject exportLoansInfoExpiredInfoReportObject) {
        return new ModelAndView("exportLoansInfoExpiredInfoReport", "modelObject", exportLoansInfoExpiredInfoReportObject);
    }


}
