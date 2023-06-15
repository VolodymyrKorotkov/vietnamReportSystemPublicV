package com.korotkov.creditCRM.model.collection.commonDataWithAssignedCollectorReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CommonDataWithAssignedCollectorReport {
    Long debtId;
    Long applicationId;
    String collectorAccountInfo;
    Double paymentAmount;
    Double currentDebt;
    Integer daysOverdue;
    LocalDate lastActivityDate;
    String activityEn;
    String activityVn;
    String activityResultEn;
    String activityResultVn;
    String lastComment;
    LocalDate lastPromisedPaymentDate;
    String status;
    String stage;
    String firstName;
    String documentIdNumber;
}
