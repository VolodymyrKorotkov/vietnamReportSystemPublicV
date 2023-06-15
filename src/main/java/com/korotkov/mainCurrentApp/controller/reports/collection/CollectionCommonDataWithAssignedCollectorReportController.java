package com.korotkov.mainCurrentApp.controller.reports.collection;

import com.korotkov.creditCRM.model.collection.commonDataWithAssignedCollectorReport.CommonDataAssignedCollectorReportObject;
import com.korotkov.mainCurrentApp.service.reportBuilder.ReportBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = "reportObject")
public class CollectionCommonDataWithAssignedCollectorReportController {
    ReportBuilderService reportBuilderService;

    @Autowired
    public void setReportBuilderService(ReportBuilderService reportBuilderService) {
        this.reportBuilderService = reportBuilderService;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/COLLECTION_COMMON_DATA_WITH_ASSIGNED_COLLECTOR_REPORT", method = RequestMethod.GET)
    public ModelAndView collectionCommonDataWithAssignedCollectorReportPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/reports/collectionReports/collectionCommonDataWithAssignedCollectorReport");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/COLLECTION_COMMON_DATA_WITH_ASSIGNED_COLLECTOR_REPORT", method = RequestMethod.POST)
    public synchronized ModelAndView collectionCommonDataWithAssignedCollectorReportProcess() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("urlBack", "/reports/COLLECTION_COMMON_DATA_WITH_ASSIGNED_COLLECTOR_REPORT");
        modelAndView.addObject("urlPost", "/reports/COLLECTION_COMMON_DATA_WITH_ASSIGNED_COLLECTOR_REPORT/export");
        modelAndView.addObject("reportObject", reportBuilderService.getCommonDataWithAssignedCollectorReport());
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/COLLECTION_COMMON_DATA_WITH_ASSIGNED_COLLECTOR_REPORT/export", method = RequestMethod.POST)
    public ModelAndView collectionCommonDataWithAssignedCollectorReportExport(
            @ModelAttribute("reportObject") CommonDataAssignedCollectorReportObject commonDataAssignedCollectorReportObject) {
        return new ModelAndView("exportExcelCollectionCommonDataWithAssignedCollectorReport", "modelObject", commonDataAssignedCollectorReportObject);
    }
}
