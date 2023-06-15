package com.korotkov.mainCurrentApp.controller.reports.collection;

import com.korotkov.creditCRM.model.collection.preSoftBonusReport.PreSoftBonusReportObject;
import com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport.StagesWithAssignedBonusReportObject;
import com.korotkov.creditCRM.model.forRequest.DatesRequest;
import com.korotkov.mainCurrentApp.service.reportBuilder.ReportBuilderService;
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
public class StagesWithAssignedBonusReportController {
    ReportBuilderService reportBuilderService;

    @Autowired
    public void setReportBuilderService(ReportBuilderService reportBuilderService) {
        this.reportBuilderService = reportBuilderService;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/STAGES_WITH_ASSIGNED_BONUS_REPORT", method = RequestMethod.GET)
    public ModelAndView stagesWithAssignedBonusReportPageView(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("datesRequest", new DatesRequest());
        modelAndView.setViewName("/reports/collectionReports/stagesWithAssignedBonusReport");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/STAGES_WITH_ASSIGNED_BONUS_REPORT", method = RequestMethod.POST)
    public synchronized ModelAndView stagesWithAssignedBonusReportProcess(@ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.setViewName("/reports/collectionReports/stagesWithAssignedBonusReport");
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        modelAndView.addObject("urlBack", "/reports/STAGES_WITH_ASSIGNED_BONUS_REPORT");
        modelAndView.addObject("urlPost", "/reports/STAGES_WITH_ASSIGNED_BONUS_REPORT/export");
        modelAndView.addObject("reportObject", reportBuilderService.getStagesWithAssignedBonusReportObject(datesRequest.getFrom(), datesRequest.getTo()));
        modelAndView.setViewName("/reports/reportCompleted");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/STAGES_WITH_ASSIGNED_BONUS_REPORT/export", method = RequestMethod.POST)
    public ModelAndView stagesWithAssignedBonusReportExport(
            @ModelAttribute("reportObject") StagesWithAssignedBonusReportObject stagesWithAssignedBonusReportObject) {
        return new ModelAndView("exportExcelStagesWithAssignedBonusReport", "modelObject", stagesWithAssignedBonusReportObject);
    }
}
