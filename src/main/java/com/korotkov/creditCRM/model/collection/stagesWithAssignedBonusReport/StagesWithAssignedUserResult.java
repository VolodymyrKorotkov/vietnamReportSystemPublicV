package com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StagesWithAssignedUserResult {
    Long userId;
    String userFullName;
    String userEmail;
    String userRole;

    int totalPaymentCount;
    int paymentCountFrom1To15OverdueDays;
    int paymentCountFrom16To29OverdueDays;
    int paymentCountFrom30To60OverdueDays;
    int paymentCountFrom61AndMoreOverdueDays;

    double totalPaymentAmount;
    double paymentAmountFrom1To15OverdueDays;
    double paymentAmountFrom16To29OverdueDays;
    double paymentAmountFrom30To60OverdueDays;
    double paymentAmountFrom61AndMoreOverdueDays;

    double bonusAmountFrom1To15DaysOverdue;
    double bonusAmountFrom16To29DaysOverdue;
    double bonusAmountFrom30To60DaysOverdue;
    double bonusAmountFrom61AndMoreDaysOverdue;
    double bonusAmountTotal;
}
