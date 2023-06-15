package com.korotkov.creditCRM.model.collection.preSoftBonusReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PreSoftBackUserResult {
    String userFullName;
    String userEmail;
    String userRole;
    int totalCountCalls;
    int countManualCalls;
    int countInboundCalls;
    int countOutboundCalls;
    int countCallsWithMinTalkSec;
    int countCallsWithMinTalkAndNeededStatuses;
    int countCallsInListRemindingPromisedPayments;
    int countCallsInListRemindingPromisedPaymentsWithMinTalkSecAndResultStatus;
    int countResultPayment;
    double totalPaymentAmount;
    double paymentAmountFrom1To15OverdueDays;
    double paymentAmountFrom16To29OverdueDays;
    double paymentAmountFrom30To60OverdueDays;
    double paymentAmountFrom61AndMoreOverdueDays;
    double totalPaymentAmountWithAssigned;
    double paymentAmountFrom1To15OverdueDaysWithAssigned;
    double paymentAmountFrom16To29OverdueDaysWithAssigned;
    double paymentAmountFrom30To60OverdueDaysWithAssigned;
    double paymentAmountFrom61AndMoreOverdueDaysWithAssigned;
    double totalPaymentAmountNoAssigned;
    double paymentAmountFrom1To15OverdueDaysNoAssigned;
    double paymentAmountFrom16To29OverdueDaysNoAssigned;
    double paymentAmountFrom30To60OverdueDaysNoAssigned;
    double paymentAmountFrom61AndMoreOverdueDaysNoAssigned;

    double bonusAmountFrom1To15DaysOverdue;
    double bonusAmountFrom16To29DaysOverdue;
    double bonusAmountFrom30To60DaysOverdue;
    double bonusAmountFrom61AndMoreDaysOverdue;
    double bonusAmountForRemindingCalls;
    double bonusAmountTotal;
}
