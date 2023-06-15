package com.korotkov.mainCurrentApp.service.reportBuilder.collection;

import com.korotkov.creditCRM.model.BackUserAccountShortly;
import com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport.CollectionPaymentWithAssigned;
import com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport.StagesWithAssignedBonusReportObject;
import com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport.StagesWithAssignedUserResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class StagesWithAssignedBonusReportBuilder {

    private final static double MINIMUM_PLAN_DPD_1_15 = 0.00;
    private final static double PLAN_AMOUNT_STEP_1_DPD_1_15 = 100_000_000.00;
    private final static double PLAN_AMOUNT_STEP_2_DPD_1_15 = 150_000_000.00;
    private final static double PLAN_AMOUNT_STEP_3_DPD_1_15 = 220_000_000.00;

    private final static double MINIMUM_PLAN_DPD_16_24 = 65_000_000.00;
    private final static double PLAN_AMOUNT_STEP_1_DPD_16_24 = 110_000_000.00;
    private final static double PLAN_AMOUNT_STEP_2_DPD_16_24 = 200_000_000.00;
    private final static double PLAN_AMOUNT_STEP_3_DPD_16_24 = 310_000_000.00;

    private final static double MINIMUM_PLAN_DPD_25_60 = 65_000_000.00;
    private final static double PLAN_AMOUNT_STEP_1_DPD_25_60 = 110_000_000.00;
    private final static double PLAN_AMOUNT_STEP_2_DPD_25_60 = 200_000_000.00;
    private final static double PLAN_AMOUNT_STEP_3_DPD_25_60 = 310_000_000.00;

    private final static double MINIMUM_PLAN_DPD_61_AND_MORE = 30_000_000.00;
    private final static double PLAN_AMOUNT_STEP_1_DPD_61_AND_MORE = 50_000_000.00;
    private final static double PLAN_AMOUNT_STEP_2_DPD_61_AND_MORE = 75_000_000.00;
    private final static double PLAN_AMOUNT_STEP_3_DPD_61_AND_MORE = 110_000_000.00;

    private final static double BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_1 = 0.03;
    private final static double BONUS_PERCENT_FOR_16_24_OVERDUE_NOT_COMPLETED_PLAN_STEP_1 = 0.05;
    private final static double BONUS_PERCENT_FOR_25_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_1 = 0.05;
    private final static double BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_1 = 0.12;

    private final static double BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_2 = 0.03;
    private final static double BONUS_PERCENT_FOR_16_24_OVERDUE_NOT_COMPLETED_PLAN_STEP_2 = 0.09;
    private final static double BONUS_PERCENT_FOR_25_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_2 = 0.09;
    private final static double BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_2 = 0.17;

    private final static double BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_3 = 0.03;
    private final static double BONUS_PERCENT_FOR_16_24_OVERDUE_NOT_COMPLETED_PLAN_STEP_3 = 0.12;
    private final static double BONUS_PERCENT_FOR_25_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_3 = 0.12;
    private final static double BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_3 = 0.19;

    private final static double BONUS_PERCENT_FOR_1_15_OVERDUE_COMPLETED_PLAN = 0.03;
    private final static double BONUS_PERCENT_FOR_16_24_OVERDUE_COMPLETED_PLAN = 0.20;
    private final static double BONUS_PERCENT_FOR_25_60_OVERDUE_COMPLETED_PLAN = 0.20;
    private final static double BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_COMPLETED_PLAN = 0.23;



    public static StagesWithAssignedBonusReportObject buildReport(List<CollectionPaymentWithAssigned> collectionPaymentWithAssignedList,
                                                                  List<BackUserAccountShortly> backUserAccountShortlyList,
                                                                  LocalDate dateFrom, LocalDate dateTo) {
        Set<Long> userIdSet = new HashSet<>();
        for (CollectionPaymentWithAssigned payment : collectionPaymentWithAssignedList) {
            if (payment.getUserId() != null) {
                userIdSet.add(payment.getUserId());
            }
        }

        List<StagesWithAssignedUserResult> stagesWithAssignedUserResultList = new ArrayList<>();

        for (Long id : userIdSet) {
            StagesWithAssignedUserResult stagesWithAssignedUserResult = new StagesWithAssignedUserResult();
            for(BackUserAccountShortly user : backUserAccountShortlyList) {
                if (user.getUserId().equals(id)) {
                    stagesWithAssignedUserResult.setUserId(id);
                    stagesWithAssignedUserResult.setUserFullName(user.getUserName());
                    stagesWithAssignedUserResult.setUserEmail(user.getUserEmail());
                    stagesWithAssignedUserResult.setUserRole(user.getRoleName());
                }
            }
            stagesWithAssignedUserResultList.add(stagesWithAssignedUserResult);
        }
        for (StagesWithAssignedUserResult userResult : stagesWithAssignedUserResultList) {
            if (userResult.getUserId() != null) {
                for (CollectionPaymentWithAssigned payment : collectionPaymentWithAssignedList) {
                    if (payment.getUserId() != null && payment.getPaymentAmount() != null && payment.getDaysOverdue() != null) {
                        if (userResult.getUserId().equals(payment.getUserId())) {
                            userResult.setTotalPaymentCount(userResult.getTotalPaymentCount() + 1);
                            userResult.setTotalPaymentAmount(userResult.getTotalPaymentAmount() +
                                    payment.getPaymentAmount());

                            if (payment.getDaysOverdue() >= 1 && payment.getDaysOverdue() <= 15) {
                                userResult
                                        .setPaymentCountFrom1To15OverdueDays(userResult
                                                .getPaymentCountFrom1To15OverdueDays() + 1);
                                userResult
                                        .setPaymentAmountFrom1To15OverdueDays(userResult
                                                .getPaymentAmountFrom1To15OverdueDays() + payment.getPaymentAmount());
                            } else if (payment.getDaysOverdue() >= 16 && payment.getDaysOverdue() <= 60) {
                                userResult
                                        .setPaymentCountFrom16To29OverdueDays(userResult
                                                .getPaymentCountFrom16To29OverdueDays() + 1);
                                userResult
                                        .setPaymentAmountFrom16To29OverdueDays(userResult
                                                .getPaymentAmountFrom16To29OverdueDays() + payment.getPaymentAmount());
                            }
//                            else if (payment.getDaysOverdue() >= 0 && payment.getDaysOverdue() <= 0) {
//                                userResult
//                                        .setPaymentCountFrom30To60OverdueDays(userResult
//                                                .getPaymentCountFrom30To60OverdueDays() + 1);
//                                userResult
//                                        .setPaymentAmountFrom30To60OverdueDays(userResult
//                                                .getPaymentAmountFrom30To60OverdueDays() + payment.getPaymentAmount());
//                            }
                            else if (payment.getDaysOverdue() >= 61) {
                                userResult
                                        .setPaymentCountFrom61AndMoreOverdueDays(userResult
                                                .getPaymentCountFrom61AndMoreOverdueDays() + 1);
                                userResult
                                        .setPaymentAmountFrom61AndMoreOverdueDays(userResult
                                                .getPaymentAmountFrom61AndMoreOverdueDays() + payment.getPaymentAmount());
                            }
                        }
                    }
                }

                //start bonus

                if (Double.compare(userResult.getPaymentAmountFrom1To15OverdueDays(), MINIMUM_PLAN_DPD_1_15) >= 0) {
                    if (Double.compare(userResult.getPaymentAmountFrom1To15OverdueDays(), PLAN_AMOUNT_STEP_1_DPD_1_15) < 0) {
                        userResult.setBonusAmountFrom1To15DaysOverdue(userResult.getPaymentAmountFrom1To15OverdueDays() *
                                BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_1);
                    } else if (Double.compare(userResult.getPaymentAmountFrom1To15OverdueDays(), PLAN_AMOUNT_STEP_1_DPD_1_15) >= 0 &&
                            Double.compare(userResult.getPaymentAmountFrom1To15OverdueDays(), PLAN_AMOUNT_STEP_2_DPD_1_15) < 0 &&
                            Double.compare(userResult.getPaymentAmountFrom1To15OverdueDays(), PLAN_AMOUNT_STEP_3_DPD_1_15) < 0) {
                        userResult.setBonusAmountFrom1To15DaysOverdue(userResult.getPaymentAmountFrom1To15OverdueDays() *
                                BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_2);
                    } else if (Double.compare(userResult.getPaymentAmountFrom1To15OverdueDays(), PLAN_AMOUNT_STEP_2_DPD_1_15) >= 0 &&
                            Double.compare(userResult.getPaymentAmountFrom1To15OverdueDays(), PLAN_AMOUNT_STEP_3_DPD_1_15) < 0) {
                        userResult.setBonusAmountFrom1To15DaysOverdue(userResult.getPaymentAmountFrom1To15OverdueDays() *
                                BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_3);
                    } else if (Double.compare(userResult.getPaymentAmountFrom1To15OverdueDays(), PLAN_AMOUNT_STEP_3_DPD_1_15) >= 0) {
                        userResult.setBonusAmountFrom1To15DaysOverdue(userResult.getPaymentAmountFrom1To15OverdueDays() *
                                BONUS_PERCENT_FOR_1_15_OVERDUE_COMPLETED_PLAN);
                    }
                }

                if (Double.compare(userResult.getPaymentAmountFrom16To29OverdueDays(), MINIMUM_PLAN_DPD_16_24) >= 0) {
                    if (Double.compare(userResult.getPaymentAmountFrom16To29OverdueDays(), PLAN_AMOUNT_STEP_1_DPD_16_24) < 0) {
                        userResult.setBonusAmountFrom16To29DaysOverdue(userResult.getPaymentAmountFrom16To29OverdueDays() *
                                BONUS_PERCENT_FOR_16_24_OVERDUE_NOT_COMPLETED_PLAN_STEP_1);
                    } else if (Double.compare(userResult.getPaymentAmountFrom16To29OverdueDays(), PLAN_AMOUNT_STEP_1_DPD_16_24) >= 0 &&
                            Double.compare(userResult.getPaymentAmountFrom16To29OverdueDays(), PLAN_AMOUNT_STEP_2_DPD_16_24) < 0 &&
                            Double.compare(userResult.getPaymentAmountFrom16To29OverdueDays(), PLAN_AMOUNT_STEP_3_DPD_16_24) < 0) {
                        userResult.setBonusAmountFrom16To29DaysOverdue(userResult.getPaymentAmountFrom16To29OverdueDays() *
                                BONUS_PERCENT_FOR_16_24_OVERDUE_NOT_COMPLETED_PLAN_STEP_2);
                    } else if (Double.compare(userResult.getPaymentAmountFrom16To29OverdueDays(), PLAN_AMOUNT_STEP_2_DPD_16_24) >= 0 &&
                            Double.compare(userResult.getPaymentAmountFrom16To29OverdueDays(), PLAN_AMOUNT_STEP_3_DPD_16_24) < 0) {
                        userResult.setBonusAmountFrom16To29DaysOverdue(userResult.getPaymentAmountFrom16To29OverdueDays() *
                                BONUS_PERCENT_FOR_16_24_OVERDUE_NOT_COMPLETED_PLAN_STEP_3);
                    } else if (Double.compare(userResult.getPaymentAmountFrom16To29OverdueDays(), PLAN_AMOUNT_STEP_3_DPD_16_24) >= 0) {
                        userResult.setBonusAmountFrom16To29DaysOverdue(userResult.getPaymentAmountFrom16To29OverdueDays() *
                                BONUS_PERCENT_FOR_16_24_OVERDUE_COMPLETED_PLAN);
                    }
                }

                if (Double.compare(userResult.getPaymentAmountFrom30To60OverdueDays(), MINIMUM_PLAN_DPD_25_60) >= 0) {
                    if (Double.compare(userResult.getPaymentAmountFrom30To60OverdueDays(), PLAN_AMOUNT_STEP_1_DPD_25_60) < 0) {
                        userResult.setBonusAmountFrom30To60DaysOverdue(userResult.getPaymentAmountFrom30To60OverdueDays() *
                                BONUS_PERCENT_FOR_25_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_1);
                    } else if (Double.compare(userResult.getPaymentAmountFrom30To60OverdueDays(), PLAN_AMOUNT_STEP_1_DPD_25_60) >= 0 &&
                            Double.compare(userResult.getPaymentAmountFrom30To60OverdueDays(), PLAN_AMOUNT_STEP_2_DPD_25_60) < 0 &&
                            Double.compare(userResult.getPaymentAmountFrom30To60OverdueDays(), PLAN_AMOUNT_STEP_3_DPD_25_60) < 0) {
                        userResult.setBonusAmountFrom30To60DaysOverdue(userResult.getPaymentAmountFrom30To60OverdueDays() *
                                BONUS_PERCENT_FOR_25_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_2);
                    } else if (Double.compare(userResult.getPaymentAmountFrom30To60OverdueDays(), PLAN_AMOUNT_STEP_2_DPD_25_60) >= 0 &&
                            Double.compare(userResult.getPaymentAmountFrom30To60OverdueDays(), PLAN_AMOUNT_STEP_3_DPD_25_60) < 0) {
                        userResult.setBonusAmountFrom30To60DaysOverdue(userResult.getPaymentAmountFrom30To60OverdueDays() *
                                BONUS_PERCENT_FOR_25_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_3);
                    } else if (Double.compare(userResult.getPaymentAmountFrom30To60OverdueDays(), PLAN_AMOUNT_STEP_3_DPD_25_60) >= 0) {
                        userResult.setBonusAmountFrom30To60DaysOverdue(userResult.getPaymentAmountFrom30To60OverdueDays() *
                                BONUS_PERCENT_FOR_25_60_OVERDUE_COMPLETED_PLAN);
                    }
                }

                if (Double.compare(userResult.getPaymentAmountFrom61AndMoreOverdueDays(), MINIMUM_PLAN_DPD_61_AND_MORE) >= 0) {
                    if (Double.compare(userResult.getPaymentAmountFrom61AndMoreOverdueDays(), PLAN_AMOUNT_STEP_1_DPD_61_AND_MORE) < 0) {
                        userResult.setBonusAmountFrom61AndMoreDaysOverdue(userResult.getPaymentAmountFrom61AndMoreOverdueDays() *
                                BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_1);
                    } else if (Double.compare(userResult.getPaymentAmountFrom61AndMoreOverdueDays(), PLAN_AMOUNT_STEP_1_DPD_61_AND_MORE) >= 0 &&
                            Double.compare(userResult.getPaymentAmountFrom61AndMoreOverdueDays(), PLAN_AMOUNT_STEP_2_DPD_61_AND_MORE) < 0 &&
                            Double.compare(userResult.getPaymentAmountFrom61AndMoreOverdueDays(), PLAN_AMOUNT_STEP_3_DPD_61_AND_MORE) < 0) {
                        userResult.setBonusAmountFrom61AndMoreDaysOverdue(userResult.getPaymentAmountFrom61AndMoreOverdueDays() *
                                BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_2);
                    } else if (Double.compare(userResult.getPaymentAmountFrom61AndMoreOverdueDays(), PLAN_AMOUNT_STEP_2_DPD_61_AND_MORE) >= 0 &&
                            Double.compare(userResult.getPaymentAmountFrom61AndMoreOverdueDays(), PLAN_AMOUNT_STEP_3_DPD_61_AND_MORE) < 0) {
                        userResult.setBonusAmountFrom61AndMoreDaysOverdue(userResult.getPaymentAmountFrom61AndMoreOverdueDays() *
                                BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_3);
                    } else if (Double.compare(userResult.getPaymentAmountFrom61AndMoreOverdueDays(), PLAN_AMOUNT_STEP_3_DPD_61_AND_MORE) >= 0) {
                        userResult.setBonusAmountFrom61AndMoreDaysOverdue(userResult.getPaymentAmountFrom61AndMoreOverdueDays() *
                                BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_COMPLETED_PLAN);
                    }
                }

                userResult.setBonusAmountTotal(userResult.getBonusAmountFrom1To15DaysOverdue() +
                        userResult.getBonusAmountFrom16To29DaysOverdue() +
                        userResult.getBonusAmountFrom30To60DaysOverdue() +
                        userResult.getBonusAmountFrom61AndMoreDaysOverdue());
            }
        }

        return new StagesWithAssignedBonusReportObject(stagesWithAssignedUserResultList, dateFrom, dateTo);
    }


    public static double getMinimumPlanDpd61AndMore() {
        return MINIMUM_PLAN_DPD_61_AND_MORE;
    }

    public static double getMinimumPlanDpd115() {
        return MINIMUM_PLAN_DPD_1_15;
    }

    public static double getMinimumPlanDpd1624() {
        return MINIMUM_PLAN_DPD_16_24;
    }

    public static double getMinimumPlanDpd2560() {
        return MINIMUM_PLAN_DPD_25_60;
    }

    public static double getPlanAmountStep1Dpd61AndMore() {
        return PLAN_AMOUNT_STEP_1_DPD_61_AND_MORE;
    }

    public static double getPlanAmountStep1Dpd115() {
        return PLAN_AMOUNT_STEP_1_DPD_1_15;
    }

    public static double getPlanAmountStep1Dpd1624() {
        return PLAN_AMOUNT_STEP_1_DPD_16_24;
    }

    public static double getPlanAmountStep1Dpd2560() {
        return PLAN_AMOUNT_STEP_1_DPD_25_60;
    }

    public static double getPlanAmountStep2Dpd61AndMore() {
        return PLAN_AMOUNT_STEP_2_DPD_61_AND_MORE;
    }

    public static double getPlanAmountStep2Dpd115() {
        return PLAN_AMOUNT_STEP_2_DPD_1_15;
    }

    public static double getPlanAmountStep2Dpd1624() {
        return PLAN_AMOUNT_STEP_2_DPD_16_24;
    }

    public static double getPlanAmountStep2Dpd2560() {
        return PLAN_AMOUNT_STEP_2_DPD_25_60;
    }

    public static double getPlanAmountStep3Dpd61AndMore() {
        return PLAN_AMOUNT_STEP_3_DPD_61_AND_MORE;
    }

    public static double getPlanAmountStep3Dpd115() {
        return PLAN_AMOUNT_STEP_3_DPD_1_15;
    }

    public static double getPlanAmountStep3Dpd1624() {
        return PLAN_AMOUNT_STEP_3_DPD_16_24;
    }

    public static double getPlanAmountStep3Dpd2560() {
        return PLAN_AMOUNT_STEP_3_DPD_25_60;
    }

    public static double getBonusPercentFor61AndMoreOverdueCompletedPlan() {
        return BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_COMPLETED_PLAN;
    }

    public static double getBonusPercentFor61AndMoreOverdueNotCompletedPlanStep1() {
        return BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_1;
    }

    public static double getBonusPercentFor61AndMoreOverdueNotCompletedPlanStep2() {
        return BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_2;
    }

    public static double getBonusPercentFor61AndMoreOverdueNotCompletedPlanStep3() {
        return BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_COMPLETED_PLAN_STEP_3;
    }

    public static double getBonusPercentFor115OverdueCompletedPlan() {
        return BONUS_PERCENT_FOR_1_15_OVERDUE_COMPLETED_PLAN;
    }

    public static double getBonusPercentFor115OverdueNotCompletedPlanStep1() {
        return BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_1;
    }

    public static double getBonusPercentFor115OverdueNotCompletedPlanStep2() {
        return BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_2;
    }

    public static double getBonusPercentFor115OverdueNotCompletedPlanStep3() {
        return BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_COMPLETED_PLAN_STEP_3;
    }

    public static double getBonusPercentFor1624OverdueCompletedPlan() {
        return BONUS_PERCENT_FOR_16_24_OVERDUE_COMPLETED_PLAN;
    }

    public static double getBonusPercentFor1624OverdueNotCompletedPlanStep1() {
        return BONUS_PERCENT_FOR_16_24_OVERDUE_NOT_COMPLETED_PLAN_STEP_1;
    }

    public static double getBonusPercentFor1624OverdueNotCompletedPlanStep2() {
        return BONUS_PERCENT_FOR_16_24_OVERDUE_NOT_COMPLETED_PLAN_STEP_2;
    }

    public static double getBonusPercentFor1624OverdueNotCompletedPlanStep3() {
        return BONUS_PERCENT_FOR_16_24_OVERDUE_NOT_COMPLETED_PLAN_STEP_3;
    }

    public static double getBonusPercentFor2560OverdueCompletedPlan() {
        return BONUS_PERCENT_FOR_25_60_OVERDUE_COMPLETED_PLAN;
    }

    public static double getBonusPercentFor2560OverdueNotCompletedPlanStep1() {
        return BONUS_PERCENT_FOR_25_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_1;
    }

    public static double getBonusPercentFor2560OverdueNotCompletedPlanStep2() {
        return BONUS_PERCENT_FOR_25_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_2;
    }

    public static double getBonusPercentFor2560OverdueNotCompletedPlanStep3() {
        return BONUS_PERCENT_FOR_25_60_OVERDUE_NOT_COMPLETED_PLAN_STEP_3;
    }
}
