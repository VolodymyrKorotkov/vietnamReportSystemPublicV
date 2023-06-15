package com.korotkov.mainCurrentApp.service.reportBuilder.collection;

import com.korotkov.creditCRM.model.BackUserAccountShortly;
import com.korotkov.creditCRM.model.collection.preSoftBonusReport.*;
import com.korotkov.vicidial.model.collection.preSoftBonusReport.PreSoftBonusExportCall;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public abstract class PreSoftBonusReportBuilder {

    private final static String MANUAL_CALL_TYPE = "MANUAL";
    private final static String INBOUND_CALL_TYPE = "INBOUND";
    private final static int MIN_NEEDED_TALK_SEC = 20;
    private final static long COUNT_DAYS_FOR_RESULT = 2;
    private final static String[] RESULT_STATUSES = new String[] {"okPog", "Okcont", "zRelat", "%reque", "reque"};

    private final static double MINIMUM_PLAN = 132_000_000.00;
    private final static double PLAN_AMOUNT_STEP_1 = 214_500_000.00;
    private final static double PLAN_AMOUNT_STEP_2 = 324_500_000.00;
    private final static double PLAN_AMOUNT_STEP_3 = 434_500_000.00;

    private final static double BONUS_AMOUNT_FOR_RESULTED_CALL_IN_REMINDING_PTP = 1_000.00;

    private final static double BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1 = 0.0001;
    private final static double BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1 = 0.0002;
    private final static double BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1 = 0.0003;
    private final static double BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1 = 0.0004;

    private final static double BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1 = 0.02;
    private final static double BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1 = 0.04;
    private final static double BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1 = 0.06;
    private final static double BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1 = 0.10;

    private final static double BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2 = 0.004;
    private final static double BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2 = 0.005;
    private final static double BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2 = 0.006;
    private final static double BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2 = 0.007;

    private final static double BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2 = 0.04;
    private final static double BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2 = 0.05;
    private final static double BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2 = 0.07;
    private final static double BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2 = 0.12;

    private final static double BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3 = 0.007;
    private final static double BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3 = 0.008;
    private final static double BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3 = 0.009;
    private final static double BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3 = 0.01;

    private final static double BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3 = 0.06;
    private final static double BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3 = 0.08;
    private final static double BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3 = 0.10;
    private final static double BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3 = 0.15;

    private final static double BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_COMPLETED_PLAN = 0.01;
    private final static double BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_COMPLETED_PLAN = 0.015;
    private final static double BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_COMPLETED_PLAN = 0.018;
    private final static double BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_COMPLETED_PLAN = 0.02;

    private final static double BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN = 0.07;
    private final static double BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN = 0.10;
    private final static double BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN = 0.12;
    private final static double BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN = 0.18;






    public static PreSoftBonusReportObject buildReport(List<PreSoftBonusExportCall> preSoftBonusExportCallList,
                                                       List<PreSoftBonusExportCall> preSoftBonusExportCallListOnlyRemindingPromisedPayments,
                                                       List<ClientPhone> clientPhoneList,
                                                       List<CollectionPayment> collectionPaymentList,
                                                       List<BackUserAccountShortly> backUserAccountShortlyList,
                                                       LocalDate dateFrom, LocalDate dateTo) {
        Set<String> userEmails = new HashSet<>();
        for (PreSoftBonusExportCall preSoftBonusExportCall : preSoftBonusExportCallList) {
            if (preSoftBonusExportCall.getUserEmail() != null) {
                userEmails.add(preSoftBonusExportCall.getUserEmail());
            }
        }

        for (PreSoftBonusExportCall preSoftBonusExportCall : preSoftBonusExportCallListOnlyRemindingPromisedPayments) {
            if (preSoftBonusExportCall.getUserEmail() != null) {
                userEmails.add(preSoftBonusExportCall.getUserEmail());
            }
        }

        List<PreSoftBackUserResult> backUserResultList = new ArrayList<>();

        for (String userEmail : userEmails) {
            PreSoftBackUserResult backUserResult = new PreSoftBackUserResult();
            for (BackUserAccountShortly user : backUserAccountShortlyList) {
                if (user.getUserEmail() != null && user.getUserName() != null) {
                    if (userEmail.equals(user.getUserEmail())) {
                        backUserResult.setUserEmail(userEmail);
                        backUserResult.setUserFullName(user.getUserName());
                        backUserResult.setUserRole(user.getRoleName());
                    }
                }
            }
            if (backUserResult.getUserRole() != null && backUserResult.getUserFullName() != null) {
                backUserResultList.add(backUserResult);
            }
        }

        for(PreSoftBonusExportCall preSoftBonusExportCall : preSoftBonusExportCallListOnlyRemindingPromisedPayments) {
            if (preSoftBonusExportCall.getUserEmail() != null) {
                for (PreSoftBackUserResult preSoftBackUserResult : backUserResultList) {
                    if (preSoftBonusExportCall.getUserEmail().equals(preSoftBackUserResult.getUserEmail())) {

                        preSoftBackUserResult
                                .setCountCallsInListRemindingPromisedPayments(preSoftBackUserResult.getCountCallsInListRemindingPromisedPayments() + 1);

                        if (preSoftBonusExportCall.getTalkSec() >= MIN_NEEDED_TALK_SEC) {
                            for (String resultStatus : RESULT_STATUSES) {
                                if (preSoftBonusExportCall.getCallStatus() != null) {
                                    if (preSoftBonusExportCall.getCallStatus().equals(resultStatus)) {
                                        preSoftBackUserResult
                                                .setCountCallsInListRemindingPromisedPaymentsWithMinTalkSecAndResultStatus(preSoftBackUserResult
                                                        .getCountCallsInListRemindingPromisedPaymentsWithMinTalkSecAndResultStatus() + 1);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        for(PreSoftBonusExportCall preSoftBonusExportCall : preSoftBonusExportCallList) {
            if (preSoftBonusExportCall.getUserEmail() != null) {
                for (PreSoftBackUserResult preSoftBackUserResult : backUserResultList) {
                    if (preSoftBonusExportCall.getUserEmail().equals(preSoftBackUserResult.getUserEmail())) {

                        preSoftBackUserResult.setTotalCountCalls(preSoftBackUserResult.getTotalCountCalls() + 1);

                        if (preSoftBonusExportCall.getCallType() == null) {
                            preSoftBackUserResult.setCountOutboundCalls(preSoftBackUserResult.getCountOutboundCalls() + 1);
                        } else if (preSoftBonusExportCall.getCallType().equals(MANUAL_CALL_TYPE)) {
                            preSoftBackUserResult.setCountManualCalls(preSoftBackUserResult.getCountManualCalls() + 1);
                        } else if (preSoftBonusExportCall.getCallType().equals(INBOUND_CALL_TYPE)) {
                            preSoftBackUserResult.setCountInboundCalls(preSoftBackUserResult.getCountInboundCalls() + 1);
                        }

                        if (preSoftBonusExportCall.getTalkSec() >= MIN_NEEDED_TALK_SEC) {
                            if (preSoftBonusExportCall.getCallType() == null || !preSoftBonusExportCall.getCallType().equals(MANUAL_CALL_TYPE)) {
                                preSoftBackUserResult.setCountCallsWithMinTalkSec(preSoftBackUserResult.getCountCallsWithMinTalkSec() + 1);

                                for (int a = 0; a < RESULT_STATUSES.length; a++) {
                                    if (preSoftBonusExportCall.getCallStatus() != null) {
                                        if (preSoftBonusExportCall.getCallStatus().equals(RESULT_STATUSES[a])) {
                                            preSoftBackUserResult
                                                    .setCountCallsWithMinTalkAndNeededStatuses(preSoftBackUserResult
                                                            .getCountCallsWithMinTalkAndNeededStatuses() + 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for(CollectionPayment collectionPayment : collectionPaymentList) {
            String email = null;
            LocalDateTime dateTimeCallResult = null;
            Map<String, LocalDateTime> emailMap = new HashMap<>();


            for(ClientPhone clientPhone : clientPhoneList) {
                
                if (collectionPayment.getClientId().equals(clientPhone.getClientId())) {
                    
                    for (int a = 0; a < preSoftBonusExportCallList.size(); a++) {
                        
                        if (preSoftBonusExportCallList.get(a).getPhoneNumber()
                                .equals(clientPhone.getPhone()) &&
                        preSoftBonusExportCallList.get(a).getTalkSec() >= MIN_NEEDED_TALK_SEC) {

                            if (preSoftBonusExportCallList.get(a).getCallType() == null || !preSoftBonusExportCallList.get(a).getCallType().equals(MANUAL_CALL_TYPE)) {
                                if (preSoftBonusExportCallList.get(a).getCallStatus() != null) {
                                    for (int b = 0; b < RESULT_STATUSES.length; b++) {
                                        if (preSoftBonusExportCallList.get(a).getCallStatus()
                                                .equals(RESULT_STATUSES[b])) {

                                            if (preSoftBonusExportCallList.get(a).getCallDate()
                                                    .isBefore(collectionPayment.getPaymentDate()) &&
                                                    (preSoftBonusExportCallList.get(a).getCallDate().toLocalDate()
                                                            .isAfter(collectionPayment.getPaymentDate().toLocalDate().minusDays(COUNT_DAYS_FOR_RESULT)) ||
                                                            preSoftBonusExportCallList.get(a).getCallDate().toLocalDate()
                                                                    .equals(collectionPayment.getPaymentDate().toLocalDate().minusDays(COUNT_DAYS_FOR_RESULT)))) {


//                                                if (collectionPayment.getClientId().equals(1033083L)) {
//                                                    int n = 1;
//                                                }

                                                emailMap.put(preSoftBonusExportCallList.get(a).getUserEmail(),
                                                        preSoftBonusExportCallList.get(a).getCallDate());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (Map.Entry<String, LocalDateTime> entry : emailMap.entrySet()) {
                String key = entry.getKey();
                LocalDateTime value = entry.getValue();

                if (email == null || dateTimeCallResult == null) {
                    email = key;
                    dateTimeCallResult = value;
                } else if (dateTimeCallResult.isAfter(value)) {
                    email = key;
                    dateTimeCallResult = value;
                }
            }

            if (email != null) {

                for (PreSoftBackUserResult backUserResult : backUserResultList) {
                    if (backUserResult.getUserEmail().equals(email)) {

                        backUserResult.setCountResultPayment(backUserResult.getCountResultPayment() + 1);

                        if (collectionPayment.getCollectorAccountId() == null) {

                            if (collectionPayment.getDaysOverdue() >= 1 && collectionPayment.getDaysOverdue() <= 15) {
                                backUserResult
                                        .setPaymentAmountFrom1To15OverdueDaysNoAssigned(backUserResult
                                                .getPaymentAmountFrom1To15OverdueDaysNoAssigned() + collectionPayment.getPaymentAmount());
                            } else if (collectionPayment.getDaysOverdue() >= 16 && collectionPayment.getDaysOverdue() <= 29) {
                                backUserResult
                                        .setPaymentAmountFrom16To29OverdueDaysNoAssigned(backUserResult
                                                .getPaymentAmountFrom16To29OverdueDaysNoAssigned() + collectionPayment.getPaymentAmount());
                            } else if (collectionPayment.getDaysOverdue() >= 30 && collectionPayment.getDaysOverdue() <= 60) {
                                backUserResult
                                        .setPaymentAmountFrom30To60OverdueDaysNoAssigned(backUserResult
                                                .getPaymentAmountFrom30To60OverdueDaysNoAssigned() + collectionPayment.getPaymentAmount());
                            } else if (collectionPayment.getDaysOverdue() >= 61) {
                                backUserResult
                                        .setPaymentAmountFrom61AndMoreOverdueDaysNoAssigned(backUserResult
                                                .getPaymentAmountFrom61AndMoreOverdueDaysNoAssigned() + collectionPayment.getPaymentAmount());
                            }
                        } else {

                            if (collectionPayment.getDaysOverdue() >= 1 && collectionPayment.getDaysOverdue() <= 15) {
                                backUserResult
                                        .setPaymentAmountFrom1To15OverdueDaysWithAssigned(backUserResult
                                                .getPaymentAmountFrom1To15OverdueDaysWithAssigned() + collectionPayment.getPaymentAmount());
                            } else if (collectionPayment.getDaysOverdue() >= 16 && collectionPayment.getDaysOverdue() <= 29) {
                                backUserResult
                                        .setPaymentAmountFrom16To29OverdueDaysWithAssigned(backUserResult
                                                .getPaymentAmountFrom16To29OverdueDaysWithAssigned() + collectionPayment.getPaymentAmount());
                            } else if (collectionPayment.getDaysOverdue() >= 30 && collectionPayment.getDaysOverdue() <= 60) {
                                backUserResult
                                        .setPaymentAmountFrom30To60OverdueDaysWithAssigned(backUserResult
                                                .getPaymentAmountFrom30To60OverdueDaysWithAssigned() + collectionPayment.getPaymentAmount());
                            } else if (collectionPayment.getDaysOverdue() >= 61) {
                                backUserResult
                                        .setPaymentAmountFrom61AndMoreOverdueDaysWithAssigned(backUserResult
                                                .getPaymentAmountFrom61AndMoreOverdueDaysWithAssigned() + collectionPayment.getPaymentAmount());
                            }
                        }
                    }
                }
            }
        }

        for (PreSoftBackUserResult backUserResult : backUserResultList) {
            backUserResult
                    .setTotalPaymentAmountNoAssigned(backUserResult.getPaymentAmountFrom1To15OverdueDaysNoAssigned() +
                            backUserResult.getPaymentAmountFrom16To29OverdueDaysNoAssigned() +
                            backUserResult.getPaymentAmountFrom30To60OverdueDaysNoAssigned() +
                            backUserResult.getPaymentAmountFrom61AndMoreOverdueDaysNoAssigned());
            backUserResult
                    .setTotalPaymentAmountWithAssigned(backUserResult.getPaymentAmountFrom1To15OverdueDaysWithAssigned() +
                            backUserResult.getPaymentAmountFrom16To29OverdueDaysWithAssigned() +
                            backUserResult.getPaymentAmountFrom30To60OverdueDaysWithAssigned() +
                            backUserResult.getPaymentAmountFrom61AndMoreOverdueDaysWithAssigned());
            backUserResult
                    .setTotalPaymentAmount(backUserResult.getTotalPaymentAmountNoAssigned() +
                            backUserResult.getTotalPaymentAmountWithAssigned());
            backUserResult
                    .setPaymentAmountFrom1To15OverdueDays(backUserResult.getPaymentAmountFrom1To15OverdueDaysNoAssigned() +
                            backUserResult.getPaymentAmountFrom1To15OverdueDaysWithAssigned());
            backUserResult
                    .setPaymentAmountFrom16To29OverdueDays(backUserResult.getPaymentAmountFrom16To29OverdueDaysNoAssigned() +
                    backUserResult.getPaymentAmountFrom16To29OverdueDaysWithAssigned());
            backUserResult
                    .setPaymentAmountFrom30To60OverdueDays(backUserResult.getPaymentAmountFrom30To60OverdueDaysNoAssigned() +
                            backUserResult.getPaymentAmountFrom30To60OverdueDaysWithAssigned());
            backUserResult
                    .setPaymentAmountFrom61AndMoreOverdueDays(backUserResult.getPaymentAmountFrom61AndMoreOverdueDaysNoAssigned() +
                            backUserResult.getPaymentAmountFrom61AndMoreOverdueDaysWithAssigned());
        }

        updateBonusAmountForPeSoftBackUserResults(backUserResultList);

        return new PreSoftBonusReportObject(backUserResultList, dateFrom, dateTo);

    }


    public static List<PreSoftActivePromissedPaymentsForAgent> getPromissedPaymentsForEmployees(List<PreSoftBonusExportCall> preSoftBonusExportCallList,
                                                                                                List<ClientPhone> clientPhoneList,
                                                                                                List<CollectionPayment> collectionPaymentList,
                                                                                                List<BackUserAccountShortly> backUserAccountShortlyList) {
        Set<String> userEmails = new HashSet<>();
        for (PreSoftBonusExportCall preSoftBonusExportCall : preSoftBonusExportCallList) {
            if (preSoftBonusExportCall.getUserEmail() != null) {
                userEmails.add(preSoftBonusExportCall.getUserEmail());
            }
        }

        Map<String, String> userEmailsAndFullNames = new HashMap<>();

        for (String userEmail : userEmails) {
            for (BackUserAccountShortly user : backUserAccountShortlyList) {
                if (user.getUserEmail() != null &&
                        user.getUserEmail().equals(userEmail)) {
                    userEmailsAndFullNames.put(user.getUserEmail(), user.getUserName());
                }
            }
        }

//        List<PreSoftActivePromissedPaymentsForAgent> preSoftActivePTPList = new ArrayList<>();

        List<PreSoftBonusExportCall> preSoftBonusResultedCallList = new ArrayList<>();

        // delete success calls from list of calls by preSoft
        //Concurrent exception, need copy all objects and then delete that

        for(PreSoftBonusExportCall preSoftBonusExportCall : preSoftBonusExportCallList) {
            boolean needRemove = true;
            if (preSoftBonusExportCall.getUserEmail() != null) {
                for (Map.Entry<String, String> user : userEmailsAndFullNames.entrySet()) {
                    if (preSoftBonusExportCall.getUserEmail().equals(user.getKey())) {
                        if (preSoftBonusExportCall.getTalkSec() >= MIN_NEEDED_TALK_SEC) {
                            if (preSoftBonusExportCall.getCallType() == null || !preSoftBonusExportCall.getCallType().equals(MANUAL_CALL_TYPE)) {
                                for (int a = 0; a < RESULT_STATUSES.length; a++) {
                                    if (preSoftBonusExportCall.getCallStatus() != null) {
                                        if (preSoftBonusExportCall.getCallStatus().equals(RESULT_STATUSES[a])) {
                                            needRemove = false;

                                            for (ClientPhone clientPhone : clientPhoneList) {
                                                if (clientPhone.getPhone().equals(preSoftBonusExportCall.getPhoneNumber())) {
                                                    for (CollectionPayment collectionPayment : collectionPaymentList) {
                                                        if (collectionPayment.getClientId().equals(clientPhone.getClientId())) {
                                                            if (preSoftBonusExportCall.getCallDate()
                                                                    .isBefore(collectionPayment.getPaymentDate()) &&
                                                                    preSoftBonusExportCall.getCallDate().toLocalDate()
                                                                            .isAfter(collectionPayment.getPaymentDate().toLocalDate().minusDays(COUNT_DAYS_FOR_RESULT)) &&
                                                                    preSoftBonusExportCall.getCallDate().toLocalDate()
                                                                            .equals(collectionPayment.getPaymentDate().toLocalDate().minusDays(COUNT_DAYS_FOR_RESULT))) {
                                                                needRemove = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!needRemove) {
                preSoftBonusResultedCallList.add(preSoftBonusExportCall);
            }
        }

        Map<Long, PreSoftActivePromissedPaymentsForAgent> mapClientIdAndResultedCall = new HashMap<>();

        for(PreSoftBonusExportCall preSoftBonusExportCall : preSoftBonusResultedCallList) {
            if (preSoftBonusExportCall.getUserEmail() != null) {
                for (Map.Entry<String, String> user : userEmailsAndFullNames.entrySet()) {
                    if (preSoftBonusExportCall.getUserEmail().equals(user.getKey())) {
                        if (preSoftBonusExportCall.getTalkSec() >= MIN_NEEDED_TALK_SEC) {
                            if (preSoftBonusExportCall.getCallType() == null || !preSoftBonusExportCall.getCallType().equals(MANUAL_CALL_TYPE)) {
                                for (int a = 0; a < RESULT_STATUSES.length; a++) {
                                    if (preSoftBonusExportCall.getCallStatus() != null) {
                                        if (preSoftBonusExportCall.getCallStatus().equals(RESULT_STATUSES[a])) {
                                            for (ClientPhone clientPhone : clientPhoneList) {
                                                if (clientPhone.getPhone().equals(preSoftBonusExportCall.getPhoneNumber())) {
                                                    PreSoftActivePromissedPaymentsForAgent activeCall = new PreSoftActivePromissedPaymentsForAgent();
                                                    activeCall.setClientId(clientPhone.getClientId());
                                                    activeCall.setDateTimeCall(preSoftBonusExportCall.getCallDate());
                                                    activeCall.setUserEmail(user.getKey());
                                                    activeCall.setUserFullName(user.getValue());
                                                    activeCall.setMaxDateForPayment(preSoftBonusExportCall.getCallDate()
                                                            .toLocalDate().plusDays(COUNT_DAYS_FOR_RESULT));

                                                    mapClientIdAndResultedCall.put(clientPhone.getClientId(),
                                                            activeCall);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        return new ArrayList<>(mapClientIdAndResultedCall.values());

    }


    private static void updateBonusAmountForPeSoftBackUserResults(List<PreSoftBackUserResult> backUserResultList) {
        for (PreSoftBackUserResult backUserResult : backUserResultList) {

            if (Double.compare(backUserResult.getTotalPaymentAmount(),MINIMUM_PLAN) > 0) {
                backUserResult
                        .setBonusAmountForRemindingCalls(backUserResult
                                .getCountCallsInListRemindingPromisedPaymentsWithMinTalkSecAndResultStatus() *
                                BONUS_AMOUNT_FOR_RESULTED_CALL_IN_REMINDING_PTP);



                if (Double.compare(backUserResult.getTotalPaymentAmount(),PLAN_AMOUNT_STEP_1) < 0) {
                    // меньше план 1

                    backUserResult
                            .setBonusAmountFrom1To15DaysOverdue((backUserResult
                                    .getPaymentAmountFrom1To15OverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1) +
                                    (backUserResult.getPaymentAmountFrom1To15OverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1));
                    backUserResult
                            .setBonusAmountFrom16To29DaysOverdue((backUserResult
                                    .getPaymentAmountFrom16To29OverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1) +
                                    (backUserResult.getPaymentAmountFrom16To29OverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1));
                    backUserResult
                            .setBonusAmountFrom30To60DaysOverdue((backUserResult
                                    .getPaymentAmountFrom30To60OverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1) +
                                    (backUserResult.getPaymentAmountFrom30To60OverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1));
                    backUserResult
                            .setBonusAmountFrom61AndMoreDaysOverdue((backUserResult
                                    .getPaymentAmountFrom61AndMoreOverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1) +
                                    (backUserResult.getPaymentAmountFrom61AndMoreOverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1));
                    backUserResult
                            .setBonusAmountTotal(backUserResult.getBonusAmountFrom1To15DaysOverdue() +
                                    backUserResult.getBonusAmountFrom16To29DaysOverdue() +
                                    backUserResult.getBonusAmountFrom30To60DaysOverdue() +
                                    backUserResult.getBonusAmountFrom61AndMoreDaysOverdue() +
                                    backUserResult.getBonusAmountForRemindingCalls());

                } else if (Double.compare(backUserResult.getTotalPaymentAmount(),PLAN_AMOUNT_STEP_1) >= 0 &&
                        Double.compare(backUserResult.getTotalPaymentAmount(),PLAN_AMOUNT_STEP_2) < 0 &&
                        Double.compare(backUserResult.getTotalPaymentAmount(),PLAN_AMOUNT_STEP_3) < 0) {
                    // между план 1 и 2

                    backUserResult
                            .setBonusAmountFrom1To15DaysOverdue((backUserResult
                                    .getPaymentAmountFrom1To15OverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2) +
                                    (backUserResult.getPaymentAmountFrom1To15OverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2));
                    backUserResult
                            .setBonusAmountFrom16To29DaysOverdue((backUserResult
                                    .getPaymentAmountFrom16To29OverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2) +
                                    (backUserResult.getPaymentAmountFrom16To29OverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2));
                    backUserResult
                            .setBonusAmountFrom30To60DaysOverdue((backUserResult
                                    .getPaymentAmountFrom30To60OverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2) +
                                    (backUserResult.getPaymentAmountFrom30To60OverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2));
                    backUserResult
                            .setBonusAmountFrom61AndMoreDaysOverdue((backUserResult
                                    .getPaymentAmountFrom61AndMoreOverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2) +
                                    (backUserResult.getPaymentAmountFrom61AndMoreOverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2));
                    backUserResult
                            .setBonusAmountTotal(backUserResult.getBonusAmountFrom1To15DaysOverdue() +
                                    backUserResult.getBonusAmountFrom16To29DaysOverdue() +
                                    backUserResult.getBonusAmountFrom30To60DaysOverdue() +
                                    backUserResult.getBonusAmountFrom61AndMoreDaysOverdue() +
                                    backUserResult.getBonusAmountForRemindingCalls());

                } else if (Double.compare(backUserResult.getTotalPaymentAmount(),PLAN_AMOUNT_STEP_2) >= 0 &&
                        Double.compare(backUserResult.getTotalPaymentAmount(),PLAN_AMOUNT_STEP_3) < 0) {
                    // между план 2 и 3

                    backUserResult
                            .setBonusAmountFrom1To15DaysOverdue((backUserResult
                                    .getPaymentAmountFrom1To15OverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3) +
                                    (backUserResult.getPaymentAmountFrom1To15OverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3));
                    backUserResult
                            .setBonusAmountFrom16To29DaysOverdue((backUserResult
                                    .getPaymentAmountFrom16To29OverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3) +
                                    (backUserResult.getPaymentAmountFrom16To29OverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3));
                    backUserResult
                            .setBonusAmountFrom30To60DaysOverdue((backUserResult
                                    .getPaymentAmountFrom30To60OverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3) +
                                    (backUserResult.getPaymentAmountFrom30To60OverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3));
                    backUserResult
                            .setBonusAmountFrom61AndMoreDaysOverdue((backUserResult
                                    .getPaymentAmountFrom61AndMoreOverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3) +
                                    (backUserResult.getPaymentAmountFrom61AndMoreOverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3));
                    backUserResult
                            .setBonusAmountTotal(backUserResult.getBonusAmountFrom1To15DaysOverdue() +
                                    backUserResult.getBonusAmountFrom16To29DaysOverdue() +
                                    backUserResult.getBonusAmountFrom30To60DaysOverdue() +
                                    backUserResult.getBonusAmountFrom61AndMoreDaysOverdue() +
                                    backUserResult.getBonusAmountForRemindingCalls());

                } else if (Double.compare(backUserResult.getTotalPaymentAmount(),PLAN_AMOUNT_STEP_3) >= 0) {
                    // больше план 3

                    backUserResult
                            .setBonusAmountFrom1To15DaysOverdue((backUserResult
                                    .getPaymentAmountFrom1To15OverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN) +
                                    (backUserResult.getPaymentAmountFrom1To15OverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_COMPLETED_PLAN));
                    backUserResult
                            .setBonusAmountFrom16To29DaysOverdue((backUserResult
                                    .getPaymentAmountFrom16To29OverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN) +
                                    (backUserResult.getPaymentAmountFrom16To29OverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_COMPLETED_PLAN));
                    backUserResult
                            .setBonusAmountFrom30To60DaysOverdue((backUserResult
                                    .getPaymentAmountFrom30To60OverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN) +
                                    (backUserResult.getPaymentAmountFrom30To60OverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_COMPLETED_PLAN));
                    backUserResult
                            .setBonusAmountFrom61AndMoreDaysOverdue((backUserResult
                                    .getPaymentAmountFrom61AndMoreOverdueDaysNoAssigned() *
                                    BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN) +
                                    (backUserResult.getPaymentAmountFrom61AndMoreOverdueDaysWithAssigned() *
                                            BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_COMPLETED_PLAN));
                    backUserResult
                            .setBonusAmountTotal(backUserResult.getBonusAmountFrom1To15DaysOverdue() +
                                    backUserResult.getBonusAmountFrom16To29DaysOverdue() +
                                    backUserResult.getBonusAmountFrom30To60DaysOverdue() +
                                    backUserResult.getBonusAmountFrom61AndMoreDaysOverdue() +
                                    backUserResult.getBonusAmountForRemindingCalls());

                }
            }
        }
    }


    public static double getMinimumPlan() {
        return MINIMUM_PLAN;
    }

    public static int getMinNeededTalkSec() {
        return MIN_NEEDED_TALK_SEC;
    }
    public static long getCountDaysForResult() {
        return COUNT_DAYS_FOR_RESULT;
    }
    public static String[] getResultStatuses() {
        return RESULT_STATUSES;
    }

    public static double getBonusAmountForResultedCallInRemindingPtp() {
        return BONUS_AMOUNT_FOR_RESULTED_CALL_IN_REMINDING_PTP;
    }

    public static double getBonusPercentFor61AndMoreOverdueAssignedCompletedPlan() {
        return BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_COMPLETED_PLAN;
    }

    public static double getBonusPercentFor61AndMoreOverdueAssignedNotCompletedPlanStep1() {
        return BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1;
    }

    public static double getBonusPercentFor61AndMoreOverdueAssignedNotCompletedPlanStep2() {
        return BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2;
    }

    public static double getBonusPercentFor61AndMoreOverdueAssignedNotCompletedPlanStep3() {
        return BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3;
    }

    public static double getBonusPercentFor61AndMoreOverdueNotAssignedCompletedPlan() {
        return BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN;
    }

    public static double getBonusPercentFor61AndMoreOverdueNotAssignedNotCompletedPlanStep1() {
        return BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1;
    }

    public static double getBonusPercentFor61AndMoreOverdueNotAssignedNotCompletedPlanStep2() {
        return BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2;
    }

    public static double getBonusPercentFor61AndMoreOverdueNotAssignedNotCompletedPlanStep3() {
        return BONUS_PERCENT_FOR_61_AND_MORE_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3;
    }

    public static double getBonusPercentFor115OverdueAssignedCompletedPlan() {
        return BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_COMPLETED_PLAN;
    }

    public static double getBonusPercentFor115OverdueAssignedNotCompletedPlanStep1() {
        return BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1;
    }

    public static double getBonusPercentFor115OverdueAssignedNotCompletedPlanStep2() {
        return BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2;
    }

    public static double getBonusPercentFor115OverdueAssignedNotCompletedPlanStep3() {
        return BONUS_PERCENT_FOR_1_15_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3;
    }

    public static double getBonusPercentFor115OverdueNotAssignedCompletedPlan() {
        return BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN;
    }

    public static double getBonusPercentFor115OverdueNotAssignedNotCompletedPlanStep1() {
        return BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1;
    }

    public static double getBonusPercentFor115OverdueNotAssignedNotCompletedPlanStep2() {
        return BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2;
    }

    public static double getBonusPercentFor115OverdueNotAssignedNotCompletedPlanStep3() {
        return BONUS_PERCENT_FOR_1_15_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3;
    }

    public static double getBonusPercentFor1629OverdueAssignedCompletedPlan() {
        return BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_COMPLETED_PLAN;
    }

    public static double getBonusPercentFor1629OverdueAssignedNotCompletedPlanStep1() {
        return BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1;
    }

    public static double getBonusPercentFor1629OverdueAssignedNotCompletedPlanStep2() {
        return BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2;
    }

    public static double getBonusPercentFor1629OverdueAssignedNotCompletedPlanStep3() {
        return BONUS_PERCENT_FOR_16_29_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3;
    }

    public static double getBonusPercentFor1629OverdueNotAssignedCompletedPlan() {
        return BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN;
    }

    public static double getBonusPercentFor3060OverdueAssignedNotCompletedPlanStep1() {
        return BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1;
    }

    public static double getBonusPercentFor1629OverdueNotAssignedNotCompletedPlanStep1() {
        return BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1;
    }

    public static double getBonusPercentFor1629OverdueNotAssignedNotCompletedPlanStep2() {
        return BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2;
    }

    public static double getBonusPercentFor1629OverdueNotAssignedNotCompletedPlanStep3() {
        return BONUS_PERCENT_FOR_16_29_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3;
    }

    public static double getBonusPercentFor3060OverdueAssignedCompletedPlan() {
        return BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_COMPLETED_PLAN;
    }

    public static double getBonusPercentFor3060OverdueAssignedNotCompletedPlanStep2() {
        return BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2;
    }

    public static double getBonusPercentFor3060OverdueAssignedNotCompletedPlanStep3() {
        return BONUS_PERCENT_FOR_30_60_OVERDUE_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3;
    }

    public static double getBonusPercentFor3060OverdueNotAssignedCompletedPlan() {
        return BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_COMPLETED_PLAN;
    }

    public static double getBonusPercentFor3060OverdueNotAssignedNotCompletedPlanStep1() {
        return BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_1;
    }

    public static double getBonusPercentFor3060OverdueNotAssignedNotCompletedPlanStep2() {
        return BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_2;
    }

    public static double getBonusPercentFor3060OverdueNotAssignedNotCompletedPlanStep3() {
        return BONUS_PERCENT_FOR_30_60_OVERDUE_NOT_ASSIGNED_NOT_COMPLETED_PLAN_STEP_3;
    }

    public static double getPlanAmountStep1() {
        return PLAN_AMOUNT_STEP_1;
    }

    public static double getPlanAmountStep2() {
        return PLAN_AMOUNT_STEP_2;
    }

    public static double getPlanAmountStep3() {
        return PLAN_AMOUNT_STEP_3;
    }

}
