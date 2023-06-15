package com.korotkov.mainCurrentApp.controller;

import com.korotkov.creditCRM.model.BackUserAccountShortly;
import com.korotkov.creditCRM.model.collection.preSoftBonusReport.PreSoftActivePromissedPaymentsForAgent;
import com.korotkov.creditCRM.model.collection.preSoftBonusReport.PreSoftResultedPTPCallsReportObject;
import com.korotkov.creditCRM.model.forRequest.DatesRequest;
import com.korotkov.creditCRM.service.userAccount.UserAccountShortlyService;
import com.korotkov.mainCurrentApp.model.UserAccount;
import com.korotkov.mainCurrentApp.service.reportBuilder.ReportBuilderService;
import com.korotkov.mainCurrentApp.service.reportBuilder.collection.PreSoftBonusReportBuilder;
import com.korotkov.mainCurrentApp.service.reportBuilder.collection.StagesWithAssignedBonusReportBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.MAX_COUNT_DAYS_FOR_BONUS_EXPORT;
import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Controller
public class MyBonusController {
    UserAccountShortlyService userAccountShortlyService;
    ReportBuilderService reportBuilderService;

    @Autowired
    public void setReportBuilderService(ReportBuilderService reportBuilderService) {
        this.reportBuilderService = reportBuilderService;
    }

    @Autowired
    public void setUserAccountShortlyService(UserAccountShortlyService userAccountShortlyService) {
        this.userAccountShortlyService = userAccountShortlyService;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_AGENT)")
    @RequestMapping(value = "/mybonus/pre-soft/active-ptp-call-list", method = RequestMethod.GET)
    public synchronized ModelAndView myBonusPagePreSoftActivePtPCallList(@AuthenticationPrincipal UserAccount userAccount) {
        BackUserAccountShortly backUserCrm = userAccountShortlyService.getBackUserAccountShortlyByEmail(userAccount.getEmail());
        ModelAndView modelAndView = new ModelAndView();
        if (backUserCrm.getRoleName() != null) {
            if (backUserCrm.getRoleName().equals("Agent of PreSoft Collection")) {

                List<PreSoftActivePromissedPaymentsForAgent> ptpList =
                        getPtPCallListForIndividualAgent(reportBuilderService.buildReportOfResultedPTPCalls(),
                                userAccount.getEmail());

                modelAndView.setViewName("/myBonus/listResultedCallsAsPtP");
                modelAndView.addObject("user", userAccount.getLastName() + " " + userAccount.getFirstName());
                modelAndView.addObject("listPtP", ptpList);
                modelAndView.addObject("countRows", ptpList.size());

            } else {
                modelAndView.setViewName("redirect:/mybonus");
            }
        }
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_AGENT)")
    @RequestMapping(value = "/mybonus", method = RequestMethod.GET)
    public ModelAndView myBonusPage(@AuthenticationPrincipal UserAccount userAccount) {
        BackUserAccountShortly backUserCrm = userAccountShortlyService.getBackUserAccountShortlyByEmail(userAccount.getEmail());
        ModelAndView modelAndView = new ModelAndView();
        if (backUserCrm.getRoleName() != null) {
            if (backUserCrm.getRoleName().equals("Agent of PreSoft Collection")) {
                modelAndView.setViewName("/myBonus/myBonusPreSoftAvailable");
                modelAndView.addObject("datesRequest", new DatesRequest());
                addCommonStaticConfigPreSoftBonusAsObjects(modelAndView);
                return modelAndView;
            } else if (backUserCrm.getRoleName().equals("Agent of Soft Collection") ||
                    backUserCrm.getRoleName().equals("Agent of Hard Collection") ||
                    backUserCrm.getRoleName().equals("Agent of NPL Collection")) {
                modelAndView.setViewName("/myBonus/myBonusStagesWithAssignedDebtsAvailable");
                modelAndView.addObject("datesRequest", new DatesRequest());
                addCommonStaticConfigStageWithAssignedBonusAsObjects(modelAndView);
                return modelAndView;
            }
        }
        modelAndView.setViewName("/myBonus/myBonusUnavailable");
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_AGENT)")
    @RequestMapping(value = "/mybonus-presoft", method = RequestMethod.POST)
    public synchronized ModelAndView myBonusPageProcess(@AuthenticationPrincipal UserAccount userAccount,
                                           @ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        addCommonStaticConfigPreSoftBonusAsObjects(modelAndView);
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.setViewName("/myBonus/myBonusPreSoftAvailable");
            modelAndView.addObject("datesRequest", datesRequest);
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }

        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(MAX_COUNT_DAYS_FOR_BONUS_EXPORT))) {
            modelAndView.setViewName("/myBonus/myBonusPreSoftAvailable");
            modelAndView.addObject("datesRequest", datesRequest);
            modelAndView.addObject("toError", "myBonusPage.errorMaxPeriod");
            return modelAndView;
        }

        modelAndView.setViewName("/myBonus/myBonusPreSoftDone");
        modelAndView.addObject("messageGood", "myBonusPage.goodMessage");
        modelAndView.addObject("individualBonusForPreSoft",
                reportBuilderService.getIndividualBonusPreSoft(userAccount.getEmail(), datesRequest.getFrom(),
                        datesRequest.getTo()));

        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority(T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).SUPER_ADMIN," +
            "T(com.korotkov.mainCurrentApp.enums.UserRoleEnum).COLLECTION_AGENT)")
    @RequestMapping(value = "/mybonus-stage-with-assigned-debt", method = RequestMethod.POST)
    public synchronized ModelAndView myBonusAssignedCollectionPageProcess(@AuthenticationPrincipal UserAccount userAccount,
                                                             @ModelAttribute("datesRequest") DatesRequest datesRequest) {
        ModelAndView modelAndView = new ModelAndView();
        addCommonStaticConfigStageWithAssignedBonusAsObjects(modelAndView);
        if (datesRequest.getFrom().isAfter(LocalDate.now(ZoneId.of(TIME_ZONE))) ||
                datesRequest.getFrom().isAfter(datesRequest.getTo())) {
            modelAndView.setViewName("/myBonus/myBonusStagesWithAssignedDebtsAvailable");
            modelAndView.addObject("datesRequest", datesRequest);
            modelAndView.addObject("fromError", "reportsPage.fromErrorMessage");
            return modelAndView;
        }
        if (datesRequest.getTo().isAfter(datesRequest.getFrom().plusDays(MAX_COUNT_DAYS_FOR_BONUS_EXPORT))) {
            modelAndView.setViewName("/myBonus/myBonusStagesWithAssignedDebtsAvailable");
            modelAndView.addObject("datesRequest", datesRequest);
            modelAndView.addObject("toError", "myBonusPage.errorMaxPeriod");
            return modelAndView;
        }
        modelAndView.setViewName("/myBonus/myBonusStagesWithAssignedDebtsDone");
        modelAndView.addObject("messageGood", "myBonusPage.goodMessage");
        modelAndView.addObject("individualBonus",
                reportBuilderService.getIndividualBonusStagesWithAssignedDebts(userAccountShortlyService.getBackUserAccountShortlyByEmail(userAccount.getEmail()),
                        datesRequest.getFrom(), datesRequest.getTo()));
        return modelAndView;
    }





    private void addCommonStaticConfigStageWithAssignedBonusAsObjects(ModelAndView modelAndView) {
        modelAndView.addObject("MINIMUM_PLAN_DPD_1_15", StagesWithAssignedBonusReportBuilder.getMinimumPlanDpd115());
        modelAndView.addObject("PLAN_AMOUNT_STEP_1_DPD_1_15", StagesWithAssignedBonusReportBuilder.getPlanAmountStep1Dpd115());
        modelAndView.addObject("PLAN_AMOUNT_STEP_2_DPD_1_15", StagesWithAssignedBonusReportBuilder.getPlanAmountStep2Dpd115());
        modelAndView.addObject("PLAN_AMOUNT_STEP_3_DPD_1_15", StagesWithAssignedBonusReportBuilder.getPlanAmountStep3Dpd115());
        modelAndView.addObject("MINIMUM_PLAN_DPD_16_29", StagesWithAssignedBonusReportBuilder.getMinimumPlanDpd1624());
        modelAndView.addObject("PLAN_AMOUNT_STEP_1_DPD_16_29", StagesWithAssignedBonusReportBuilder.getPlanAmountStep1Dpd1624());
        modelAndView.addObject("PLAN_AMOUNT_STEP_2_DPD_16_29", StagesWithAssignedBonusReportBuilder.getPlanAmountStep2Dpd1624());
        modelAndView.addObject("PLAN_AMOUNT_STEP_3_DPD_16_29", StagesWithAssignedBonusReportBuilder.getPlanAmountStep3Dpd1624());
        modelAndView.addObject("MINIMUM_PLAN_DPD_30_60", StagesWithAssignedBonusReportBuilder.getMinimumPlanDpd2560());
        modelAndView.addObject("PLAN_AMOUNT_STEP_1_DPD_30_60", StagesWithAssignedBonusReportBuilder.getPlanAmountStep1Dpd2560());
        modelAndView.addObject("PLAN_AMOUNT_STEP_2_DPD_30_60", StagesWithAssignedBonusReportBuilder.getPlanAmountStep2Dpd2560());
        modelAndView.addObject("PLAN_AMOUNT_STEP_3_DPD_30_60", StagesWithAssignedBonusReportBuilder.getPlanAmountStep3Dpd2560());
        modelAndView.addObject("MINIMUM_PLAN_DPD_61_AND_MORE", StagesWithAssignedBonusReportBuilder.getMinimumPlanDpd61AndMore());
        modelAndView.addObject("PLAN_AMOUNT_STEP_1_DPD_61_AND_MORE", StagesWithAssignedBonusReportBuilder.getPlanAmountStep1Dpd61AndMore());
        modelAndView.addObject("PLAN_AMOUNT_STEP_2_DPD_61_AND_MORE", StagesWithAssignedBonusReportBuilder.getPlanAmountStep2Dpd61AndMore());
        modelAndView.addObject("PLAN_AMOUNT_STEP_3_DPD_61_AND_MORE", StagesWithAssignedBonusReportBuilder.getPlanAmountStep3Dpd61AndMore());
        modelAndView.addObject("BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_1", StagesWithAssignedBonusReportBuilder.getBonusPercentFor115OverdueNotCompletedPlanStep1());
        modelAndView.addObject("BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_COMPLETED_PLAN_STEP_1", StagesWithAssignedBonusReportBuilder.getBonusPercentFor1624OverdueNotCompletedPlanStep1());
        modelAndView.addObject("BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_1", StagesWithAssignedBonusReportBuilder.getBonusPercentFor2560OverdueNotCompletedPlanStep1());
        modelAndView.addObject("BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_1", StagesWithAssignedBonusReportBuilder.getBonusPercentFor61AndMoreOverdueNotCompletedPlanStep1());
        modelAndView.addObject("BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_2", StagesWithAssignedBonusReportBuilder.getBonusPercentFor115OverdueNotCompletedPlanStep2());
        modelAndView.addObject("BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_COMPLETED_PLAN_STEP_2", StagesWithAssignedBonusReportBuilder.getBonusPercentFor1624OverdueNotCompletedPlanStep2());
        modelAndView.addObject("BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_2", StagesWithAssignedBonusReportBuilder.getBonusPercentFor2560OverdueNotCompletedPlanStep2());
        modelAndView.addObject("BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_2", StagesWithAssignedBonusReportBuilder.getBonusPercentFor61AndMoreOverdueNotCompletedPlanStep2());
        modelAndView.addObject("BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_3", StagesWithAssignedBonusReportBuilder.getBonusPercentFor115OverdueNotCompletedPlanStep3());
        modelAndView.addObject("BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_COMPLETED_PLAN_STEP_3", StagesWithAssignedBonusReportBuilder.getBonusPercentFor1624OverdueNotCompletedPlanStep3());
        modelAndView.addObject("BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_3", StagesWithAssignedBonusReportBuilder.getBonusPercentFor2560OverdueNotCompletedPlanStep3());
        modelAndView.addObject("BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_3", StagesWithAssignedBonusReportBuilder.getBonusPercentFor61AndMoreOverdueNotCompletedPlanStep3());
        modelAndView.addObject("BONUS_PERCENT_FOR_1_15_OVERDUE_COMPLETED_PLAN", StagesWithAssignedBonusReportBuilder.getBonusPercentFor115OverdueCompletedPlan());
        modelAndView.addObject("BONUS_PERCENT_FOR_16_29_OVERDUE_COMPLETED_PLAN", StagesWithAssignedBonusReportBuilder.getBonusPercentFor1624OverdueCompletedPlan());
        modelAndView.addObject("BONUS_PERCENT_FOR_30_60_OVERDUE_COMPLETED_PLAN", StagesWithAssignedBonusReportBuilder.getBonusPercentFor2560OverdueCompletedPlan());
        modelAndView.addObject("BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_COMPLETED_PLAN", StagesWithAssignedBonusReportBuilder.getBonusPercentFor61AndMoreOverdueCompletedPlan());
    }



    private void addCommonStaticConfigPreSoftBonusAsObjects(ModelAndView modelAndView) {
        modelAndView.addObject("minNeededTalkSec", PreSoftBonusReportBuilder.getMinNeededTalkSec());
        modelAndView.addObject("countDaysForResult", PreSoftBonusReportBuilder.getCountDaysForResult());
        modelAndView.addObject("resultStatuses", Arrays.toString(PreSoftBonusReportBuilder.getResultStatuses()));
        modelAndView.addObject("minimumPlan", PreSoftBonusReportBuilder.getMinimumPlan());
        modelAndView.addObject("planAmountStep1", PreSoftBonusReportBuilder.getPlanAmountStep1());
        modelAndView.addObject("planAmountStep2", PreSoftBonusReportBuilder.getPlanAmountStep2());
        modelAndView.addObject("planAmountStep3", PreSoftBonusReportBuilder.getPlanAmountStep3());
        modelAndView.addObject("bonusAmountForResultedCallInPtp", PreSoftBonusReportBuilder.getBonusAmountForResultedCallInRemindingPtp());
        modelAndView.addObject("BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1",
                PreSoftBonusReportBuilder.getBonusPercentFor115OverdueAssignedNotCompletedPlanStep1());
        modelAndView.addObject("BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1",
                PreSoftBonusReportBuilder.getBonusPercentFor1629OverdueAssignedNotCompletedPlanStep1());
        modelAndView.addObject("BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1",
                PreSoftBonusReportBuilder.getBonusPercentFor3060OverdueAssignedNotCompletedPlanStep1());
        modelAndView.addObject("BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1",
                PreSoftBonusReportBuilder.getBonusPercentFor61AndMoreOverdueAssignedNotCompletedPlanStep1());
        modelAndView.addObject("BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1",
                PreSoftBonusReportBuilder.getBonusPercentFor115OverdueNotAssignedNotCompletedPlanStep1());
        modelAndView.addObject("BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1",
                PreSoftBonusReportBuilder.getBonusPercentFor1629OverdueNotAssignedNotCompletedPlanStep1());
        modelAndView.addObject("BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1",
                PreSoftBonusReportBuilder.getBonusPercentFor3060OverdueNotAssignedNotCompletedPlanStep1());
        modelAndView.addObject("BONUS_PERCENT_FOR_61_AND_MORE_NOT_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1",
                PreSoftBonusReportBuilder.getBonusPercentFor61AndMoreOverdueNotAssignedNotCompletedPlanStep1());
        modelAndView.addObject("BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2",
                PreSoftBonusReportBuilder.getBonusPercentFor115OverdueAssignedNotCompletedPlanStep2());
        modelAndView.addObject("BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2",
                PreSoftBonusReportBuilder.getBonusPercentFor1629OverdueAssignedNotCompletedPlanStep2());
        modelAndView.addObject("BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2",
                PreSoftBonusReportBuilder.getBonusPercentFor3060OverdueAssignedNotCompletedPlanStep2());
        modelAndView.addObject("BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2",
                PreSoftBonusReportBuilder.getBonusPercentFor61AndMoreOverdueAssignedNotCompletedPlanStep2());
        modelAndView.addObject("BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2",
                PreSoftBonusReportBuilder.getBonusPercentFor115OverdueNotAssignedNotCompletedPlanStep2());
        modelAndView.addObject("BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2",
                PreSoftBonusReportBuilder.getBonusPercentFor1629OverdueNotAssignedNotCompletedPlanStep2());
        modelAndView.addObject("BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2",
                PreSoftBonusReportBuilder.getBonusPercentFor3060OverdueNotAssignedNotCompletedPlanStep2());
        modelAndView.addObject("BONUS_PERCENT_FOR_61_AND_MORE_NOT_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2",
                PreSoftBonusReportBuilder.getBonusPercentFor61AndMoreOverdueNotAssignedNotCompletedPlanStep2());
        modelAndView.addObject("BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3",
                PreSoftBonusReportBuilder.getBonusPercentFor115OverdueAssignedNotCompletedPlanStep3());
        modelAndView.addObject("BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3",
                PreSoftBonusReportBuilder.getBonusPercentFor1629OverdueAssignedNotCompletedPlanStep3());
        modelAndView.addObject("BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3",
                PreSoftBonusReportBuilder.getBonusPercentFor3060OverdueAssignedNotCompletedPlanStep3());
        modelAndView.addObject("BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3",
                PreSoftBonusReportBuilder.getBonusPercentFor61AndMoreOverdueAssignedNotCompletedPlanStep3());
        modelAndView.addObject("BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3",
                PreSoftBonusReportBuilder.getBonusPercentFor115OverdueNotAssignedNotCompletedPlanStep3());
        modelAndView.addObject("BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3",
                PreSoftBonusReportBuilder.getBonusPercentFor1629OverdueNotAssignedNotCompletedPlanStep3());
        modelAndView.addObject("BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3",
                PreSoftBonusReportBuilder.getBonusPercentFor3060OverdueNotAssignedNotCompletedPlanStep3());
        modelAndView.addObject("BONUS_PERCENT_FOR_61_AND_MORE_NOT_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3",
                PreSoftBonusReportBuilder.getBonusPercentFor61AndMoreOverdueNotAssignedNotCompletedPlanStep3());
        modelAndView.addObject("BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_COMPLETED_PLAN",
                PreSoftBonusReportBuilder.getBonusPercentFor115OverdueAssignedCompletedPlan());
        modelAndView.addObject("BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_COMPLETED_PLAN",
                PreSoftBonusReportBuilder.getBonusPercentFor1629OverdueAssignedCompletedPlan());
        modelAndView.addObject("BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_COMPLETED_PLAN",
                PreSoftBonusReportBuilder.getBonusPercentFor3060OverdueAssignedCompletedPlan());
        modelAndView.addObject("BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_COMPLETED_PLAN",
                PreSoftBonusReportBuilder.getBonusPercentFor61AndMoreOverdueAssignedCompletedPlan());
        modelAndView.addObject("BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN",
                PreSoftBonusReportBuilder.getBonusPercentFor115OverdueNotAssignedCompletedPlan());
        modelAndView.addObject("BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN",
                PreSoftBonusReportBuilder.getBonusPercentFor1629OverdueNotAssignedCompletedPlan());
        modelAndView.addObject("BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN",
                PreSoftBonusReportBuilder.getBonusPercentFor3060OverdueNotAssignedCompletedPlan());
        modelAndView.addObject("BONUS_PERCENT_FOR_61_AND_MORE_NOT_OVERDUE_ASSIGNED_COMPLETED_PLAN",
                PreSoftBonusReportBuilder.getBonusPercentFor61AndMoreOverdueNotAssignedCompletedPlan());
    }



    private List<PreSoftActivePromissedPaymentsForAgent> getPtPCallListForIndividualAgent(PreSoftResultedPTPCallsReportObject reportObject,
                                                                                          String userEmail) {
        List<PreSoftActivePromissedPaymentsForAgent> ptpCallList = new ArrayList<>();

        for (PreSoftActivePromissedPaymentsForAgent ptpCall : reportObject.getResultedPTPCallList()) {
            if (ptpCall.getUserEmail().equals(userEmail)) {
                ptpCallList.add(ptpCall);
            }
        }

        return ptpCallList;
    }

}
