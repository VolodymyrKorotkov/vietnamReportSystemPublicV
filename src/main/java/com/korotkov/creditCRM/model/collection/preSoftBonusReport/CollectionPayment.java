package com.korotkov.creditCRM.model.collection.preSoftBonusReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CollectionPayment {
    Long clientId;
    Long collectorAccountId;
    LocalDateTime paymentDate;
    Double paymentAmount;
    Integer daysOverdue;
}
