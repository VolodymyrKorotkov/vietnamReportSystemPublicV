package com.korotkov.mainCurrentApp.controller.reports;

import com.korotkov.creditCRM.model.forRequest.DatesRequest;
import com.korotkov.creditCRM.model.mainDailyReport.MainDailyReportObject;
import com.korotkov.mainCurrentApp.api.currencyConverterApi.CurrencyConverterApiService;
import com.korotkov.mainCurrentApp.config.ConfigConstants;
import com.korotkov.mainCurrentApp.model.CurrencyRate;
import com.korotkov.mainCurrentApp.model.ModelPlan;
import com.korotkov.mainCurrentApp.model.Report;
import com.korotkov.mainCurrentApp.model.UserAccount;
import com.korotkov.mainCurrentApp.service.currencyRate.CurrencyRateService;
import com.korotkov.mainCurrentApp.service.modelPlan.ModelPlanService;
import com.korotkov.mainCurrentApp.service.report.ReportService;
import com.korotkov.mainCurrentApp.service.reportBuilder.ReportBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ReportController implements ConfigConstants {
    CurrencyRateService currencyRateService;
    CurrencyConverterApiService currencyConverterApiService;
    ModelPlanService modelPlanService;
    ReportService reportService;

    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    @Autowired
    public void setCurrencyRateService(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @Autowired
    public void setCurrencyConverterApiService(CurrencyConverterApiService currencyConverterApiService) {
        this.currencyConverterApiService = currencyConverterApiService;
    }

    @Autowired
    public void setModelPlanService(ModelPlanService modelPlanService) {
        this.modelPlanService = modelPlanService;
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
    @RequestMapping(value = "/reports/currency-rate", method = RequestMethod.GET)
    public ModelAndView currencyRateListView(@RequestParam(defaultValue = "NaN") String sf,
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
            String view = "redirect:/reports/currency-rate";
            if ((dateFromString != null && !dateFromString.isEmpty()) || (dateToString != null && !dateToString.isEmpty())) {
                view += "?dateFrom=" + dateFromString + "&dateTo=" + dateToString;
            }
            modelAndView.setViewName(view);
            return modelAndView;
        }
        List<CurrencyRate> currencyRateList;
        Long currencyRateCount;
        if (dateFrom != null && dateTo != null) {
            currencyRateList = currencyRateService.getCurrencyRateForDates(dateFrom, dateTo, page);
            currencyRateCount = currencyRateService.getCountCurrencyRateForDates(dateFrom, dateTo);
        } else if (dateFrom != null) {
            currencyRateList = currencyRateService.getCurrencyRateForDateFrom(dateFrom, page);
            currencyRateCount = currencyRateService.getCountCurrencyRateForDateFrom(dateFrom);
        } else if (dateTo != null) {
            currencyRateList = currencyRateService.getCurrencyRateForDateTo(dateTo, page);
            currencyRateCount = currencyRateService.getCountCurrencyRateForDateTo(dateTo);
        } else {
            currencyRateList = currencyRateService.getAllCurrencyRates(page);
            currencyRateCount = currencyRateService.getCountAllCurrencyRates();
        }
        int pagesCount = (int) ((currencyRateCount + 9) / 10);
        modelAndView.setViewName("/reports/currencyRateList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("currencyRateList", currencyRateList);
        modelAndView.addObject("currencyRateCount", currencyRateCount);
        modelAndView.addObject("pagesCount", pagesCount);
        if (action.equals("currency-updated")) {
            modelAndView.addObject("messageGood", "reportsPage.successMessageUpdateCurrency");
        } else if (action.equals("currency-not-updated")) {
            modelAndView.addObject("messageBad", "reportsPage.badMessageUpdateCurrency");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/currency-rate/update-today-currency-rate", method = RequestMethod.GET)
    public ModelAndView updateTodayCurrencyRateProcess() {
        ModelAndView modelAndView = new ModelAndView();
        if (!currencyConverterApiService.apiRequestUsdToVnd()) {
            modelAndView.setViewName("redirect:/reports/currency-rate?action=currency-not-updated");
        } else {
            modelAndView.setViewName("redirect:/reports/currency-rate?action=currency-updated");
        }
        return modelAndView;
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
    @RequestMapping(value = "/reports/currency-rate/{id}", method = RequestMethod.GET)
    public ModelAndView currencyRatePageView(@PathVariable(value = "id") Long id,
                                             @RequestParam(defaultValue = "NaN") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/reports/currencyRateView");
        modelAndView.addObject("currencyRate", currencyRateService.getById(id));
        if (action.equals("changed")) {
            modelAndView.addObject("message", "reportsPage.messageChangedCurrencyRate");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/currency-rate/{id}/edit", method = RequestMethod.GET)
    public ModelAndView currencyRatePageEdit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/reports/currencyRateEdit");
        modelAndView.addObject("currencyRate", currencyRateService.getById(id));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/currency-rate/{id}/edit", method = RequestMethod.POST)
    public ModelAndView currencyRateProcessEdit(@PathVariable(value = "id") Long id,
                                                @ModelAttribute("currencyRate") CurrencyRate currencyRate,
                                                @AuthenticationPrincipal UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView();
        if (currencyRate.getUsdVnd().compareTo((double) 1) < 0) {
            modelAndView.setViewName("/reports/currencyRateEdit");
            modelAndView.addObject("usdVndError", "common.errorValueCanNotBeLess1");
            return modelAndView;
        }
        currencyRateService.updateByUser(id, currencyRate, userAccount);
        modelAndView.setViewName("redirect:/reports/currency-rate/" + id + "?action=changed");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/model-plans", method = RequestMethod.GET)
    public ModelAndView modelPlanListPageView(@RequestParam(defaultValue = "1") String pageString) {
        ModelAndView modelAndView = new ModelAndView();
        int page;
        if (pageString.equals("NaN")) {
            page = 1;
        } else {
            page = Integer.parseInt(pageString);
        }
        List<ModelPlan> modelPlanList = modelPlanService.getAllModelPlans(page);
        Long countModelPlans = modelPlanService.getCountAllModelPlans();
        int pagesCount = (int) ((countModelPlans + 9) / 10);
        modelAndView.setViewName("/reports/modelPlanList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("pagesCount", pagesCount);
        modelAndView.addObject("modelPlanList", modelPlanList);
        modelAndView.addObject("countModelPlans", countModelPlans);
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/model-plans/{id}", method = RequestMethod.GET)
    public ModelAndView modelPlanPageView(@PathVariable(value = "id") Long id,
                                          @RequestParam(defaultValue = "NaN") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/reports/modelPlanView");
        modelAndView.addObject("modelPlan", modelPlanService.getById(id));
        if (action.equals("created")) {
            modelAndView.addObject("message", "reportsPage.modelPlanMessageCreatedPlan");
        } else if (action.equals("edited")) {
            modelAndView.addObject("message", "reportsPage.modelPlanMessageChangedPlan");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/model-plans/{id}/edit", method = RequestMethod.GET)
    public ModelAndView modelPlanPageEdit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/reports/modelPlanEdit");
        modelAndView.addObject("modelPlan", modelPlanService.getById(id));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/model-plans/{id}/edit", method = RequestMethod.POST)
    public ModelAndView modelPlanProcessEdit(@PathVariable(value = "id") Long id,
                                             @ModelAttribute("modelPlan") ModelPlan modelPlan,
                                             @AuthenticationPrincipal UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView();
        if (modelPlan.getIssuedAmountNew() == null || modelPlan.getIssuedAmountNew().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanEdit");
            modelAndView.addObject("issuedAmountNewError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getIssuedAmountRepeat() == null || modelPlan.getIssuedAmountRepeat().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanEdit");
            modelAndView.addObject("issuedAmountRepeatError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getProlongedAmount() == null || modelPlan.getProlongedAmount().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanEdit");
            modelAndView.addObject("prolongedAmountError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getRepaymentPrincipalAmountNew() == null || modelPlan.getRepaymentPrincipalAmountNew().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanEdit");
            modelAndView.addObject("repaymentPrincipalAmountNewError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getRepaymentPrincipalAmountRepeat() == null || modelPlan.getRepaymentPrincipalAmountRepeat().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanEdit");
            modelAndView.addObject("repaymentPrincipalAmountRepeatError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getRepaymentIncomeAmountNew() == null || modelPlan.getRepaymentIncomeAmountNew().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanEdit");
            modelAndView.addObject("repaymentIncomeAmountNewError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getRepaymentIncomeAmountRepeat() == null || modelPlan.getRepaymentIncomeAmountRepeat().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanEdit");
            modelAndView.addObject("repaymentIncomeAmountRepeatError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        modelPlanService.updatePlan(id, modelPlan, userAccount);
        modelAndView.setViewName("redirect:/reports/model-plans/" + id + "?action=edited");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/model-plans/create-new", method = RequestMethod.GET)
    public ModelAndView modelPlanPageCreate() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/reports/modelPlanCreate");
        modelAndView.addObject("modelPlan", new ModelPlan());
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).ANALYST_SUPERVISOR)")
    @RequestMapping(value = "/reports/model-plans/create-new", method = RequestMethod.POST)
    public ModelAndView modelPlanProcessCreate(@ModelAttribute("modelPlan") ModelPlan modelPlan,
                                               @AuthenticationPrincipal UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView();
        if (modelPlan.getIssuedAmountNew() == null || modelPlan.getIssuedAmountNew().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanCreate");
            modelAndView.addObject("issuedAmountNewError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getIssuedAmountRepeat() == null || modelPlan.getIssuedAmountRepeat().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanCreate");
            modelAndView.addObject("issuedAmountRepeatError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getProlongedAmount() == null || modelPlan.getProlongedAmount().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanCreate");
            modelAndView.addObject("prolongedAmountError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getRepaymentPrincipalAmountNew() == null || modelPlan.getRepaymentPrincipalAmountNew().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanCreate");
            modelAndView.addObject("repaymentPrincipalAmountNewError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getRepaymentPrincipalAmountRepeat() == null || modelPlan.getRepaymentPrincipalAmountRepeat().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanCreate");
            modelAndView.addObject("repaymentPrincipalAmountRepeatError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getRepaymentIncomeAmountNew() == null || modelPlan.getRepaymentIncomeAmountNew().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanCreate");
            modelAndView.addObject("repaymentIncomeAmountNewError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getRepaymentIncomeAmountRepeat() == null || modelPlan.getRepaymentIncomeAmountRepeat().compareTo((double) 0) < 0) {
            modelAndView.setViewName("/reports/modelPlanCreate");
            modelAndView.addObject("repaymentIncomeAmountRepeatError", "common.errorValueCanNotBeLess0");
            return modelAndView;
        }
        if (modelPlan.getDateMonth() == null) {
            modelAndView.setViewName("/reports/modelPlanCreate");
            modelAndView.addObject("dateMonthError", "common.errorValueCanNotBeEmpty");
            return modelAndView;
        }
        if (!modelPlanService.createNewPlan(modelPlan, userAccount)) {
            modelAndView.setViewName("/reports/modelPlanCreate");
            modelAndView.addObject("dateMonthError", "reportsPage.modelPlanErrorDateDuplicate");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/reports/model-plans/" + modelPlanService.getIdCreatedModelByDateMonth(modelPlan) + "?action=created");
        return modelAndView;
    }


    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public ModelAndView usersListView(@RequestParam(defaultValue = "1") String pageString,
                                      @RequestParam(defaultValue = "") String name,
                                      @RequestParam(defaultValue = "NaN") String sf) {
        int page;
        if (pageString.equals("NaN")) {
            page = 1;
        } else {
            page = Integer.parseInt(pageString);
        }
        ModelAndView modelAndView = new ModelAndView();
        if (!sf.equals("NaN")) {
            modelAndView.setViewName("redirect:/reports?" + "name=" + name);
            return modelAndView;
        }
        List<Report> reportList;
        Long reportCount;

        if (!name.equals("")) {
            reportList = reportService.getReportsWithFilterName(page, name);
            reportCount = reportService.getCountReportsWithFilterName(name);
        } else {
            reportList = reportService.getAllReports(page);
            reportCount = reportService.getCountAllReports();
        }

        int pagesCount = (int) ((reportCount + 9) / 10);
        modelAndView.setViewName("/reports/reportList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("reportList", reportList);
        modelAndView.addObject("reportCount", reportCount);
        modelAndView.addObject("pagesCount", pagesCount);
        return modelAndView;

    }



}
