package com.korotkov.mainCurrentApp.controller;

import com.korotkov.mainCurrentApp.model.ApplicationsQueueChecking;
import com.korotkov.mainCurrentApp.service.applicationsQueueChecking.ApplicationsQueueCheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CreditRiskDepController {

    private ApplicationsQueueCheckingService applicationsQueueCheckingService;

    @Autowired
    public void setApplicationsQueueCheckingService(ApplicationsQueueCheckingService applicationsQueueCheckingService) {
        this.applicationsQueueCheckingService = applicationsQueueCheckingService;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ACCOUNTANT," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).CC_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).UNDERWRITER_SUPERVISOR," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).MARKETER," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/credit-risk-dep/queue-apps", method = RequestMethod.GET)
    public ModelAndView queueAppsPageViewList(@RequestParam(defaultValue = "NaN") String sf,
                                              @RequestParam(defaultValue = "NaN") String action,
                                              @RequestParam(defaultValue = "1") String pageString,
                                              @RequestParam(value = "dateFrom", required = false) String dateFromString,
                                              @RequestParam(value = "dateTo", required = false) String dateToString) {
        ModelAndView modelAndView = new ModelAndView();
        LocalDate dateFrom = null;
        LocalDate dateTo = null;
        if (dateFromString != null && !dateFromString.isEmpty()) {
            dateFrom = LocalDate.parse(dateFromString);
        }
        if (dateToString != null && !dateToString.isEmpty()) {
            dateTo = LocalDate.parse(dateToString);
        }
        int page;
        if (pageString.equals("NaN")) {
            page = 1;
        } else {
            page = Integer.parseInt(pageString);
        }
        if (!sf.equals("NaN")) {
            String view = "redirect:/credit-risk-dep/queue-apps";
            if ((dateFromString != null && !dateFromString.isEmpty()) || (dateToString != null && !dateToString.isEmpty())) {
                view += "?dateFrom=" + dateFromString + "&dateTo=" + dateToString;
            }
            modelAndView.setViewName(view);
            return modelAndView;
        }

        List<ApplicationsQueueChecking> appsQueueCheckingList;
        Long appsQueueCheckingCount;

        if (dateFrom != null && dateTo != null) {
            appsQueueCheckingList = applicationsQueueCheckingService.getAppsQueueCheckingForDates(dateFrom, dateTo, page);
            appsQueueCheckingCount = applicationsQueueCheckingService.getCountAppsQueueCheckingForDates(dateFrom, dateTo);
        } else if (dateFrom != null) {
            appsQueueCheckingList = applicationsQueueCheckingService.getAppsQueueCheckingForDateFrom(dateFrom, page);
            appsQueueCheckingCount = applicationsQueueCheckingService.getCountAppsQueueCheckingForDateFrom(dateFrom);
        } else if (dateTo != null) {
            appsQueueCheckingList = applicationsQueueCheckingService.getAppsQueueCheckingForDateTo(dateTo, page);
            appsQueueCheckingCount = applicationsQueueCheckingService.getCountAppsQueueCheckingForDateTo(dateTo);
        } else {
            appsQueueCheckingList = applicationsQueueCheckingService.getAllAppsQueueChecking(page);
            appsQueueCheckingCount = applicationsQueueCheckingService.getCountAllAppsQueueChecking();
        }

        int pagesCount = (int) ((appsQueueCheckingCount + 9) / 10);

        modelAndView.setViewName("/creditRiskDep/appsQueueCheckingList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("pagesCount", pagesCount);
        modelAndView.addObject("appsQueueCheckingList", appsQueueCheckingList);
        modelAndView.addObject("appsQueueCheckingCount", appsQueueCheckingCount);

        return modelAndView;
    }
}
