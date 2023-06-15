package com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CollectionPaymentWithAssigned {
    Long userId;
    Double paymentAmount;
    Integer daysOverdue;
}
