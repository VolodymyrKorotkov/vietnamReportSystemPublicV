package com.korotkov.mainCurrentApp.controller.reports;

import com.korotkov.creditCRM.model.forRequest.DatesRequest;
import com.korotkov.creditCRM.model.mainDailyReport.MainDailyReportObject;
import com.korotkov.mainCurrentApp.config.ConfigConstants;
import com.korotkov.mainCurrentApp.service.reportBuilder.ReportBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.ZoneId;

@Controller
@SessionAttributes(value = "reportObject")
public class MainDailyReportController implements ConfigConstants {

    ReportBuilderService reportBuilderService;

    @Autowired
    public void setReportBuilderService(ReportBuilderService reportBuilderService) {
        this.reportBuilderService = reportBuilderService;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/MAIN_DAILY_REPORT", method = RequestMethod.GET)
    public ModelAndView mainDailyReportPageView(@RequestParam(defaultValue = "NaN") String action,
                                                SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/reports/mainDailyReport/mainDailyReport");
        modelAndView.addObject("datesRequest", new DatesRequest());
        if (action.equals("success")) {
            modelAndView.addObject("messageGood", "reportsPage.messageSuccessExportReport");
        } else if (action.equals("error")) {
            modelAndView.addObject("messageBad", "reportsPage.messageErrorExportReport");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/MAIN_DAILY_REPORT", method = RequestMethod.POST)
    public synchronized ModelAndView mainDailyReportProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.setViewName("/reports/mainDailyReport/mainDailyReport");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/MAIN_DAILY_REPORT");
        modelAndView.addObject("urlPost", "/reports/MAIN_DAILY_REPORT/export");
        modelAndView.addObject("reportObject", reportBuilderService.getMainDailyReport(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/MAIN_DAILY_REPORT/export", method = RequestMethod.POST)
    public ModelAndView mainDailyReportExport(
            @ModelAttribute("reportObject") MainDailyReportObject mainDailyReportObject) {
        return new ModelAndView("exportExcelMainDailyReport", "modelObject", mainDailyReportObject);
    }
}
