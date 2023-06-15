package com.korotkov.creditCRM.model.collection.preSoftBonusReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientWithIDAndDebtId {
    Long clientId;
    Long debtId;
    Integer daysOverdue;
    Double currentDebtAmount;
}
