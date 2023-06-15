package com.korotkov.creditCRM.model.collection.preSoftBonusReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PreSoftActivePromissedPaymentsForAgent {
    String userEmail;
    String userFullName;
    Double currentDebt;
    Integer daysOverdue;
    LocalDateTime dateTimeCall;
    LocalDate maxDateForPayment;
    Long debtId;
    Long clientId;
}
