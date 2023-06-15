package com.korotkov.creditCRM.model.clients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExportClientsWithExpiredForVicidial {
    String phone;
    String fullName;
    String relationshipName;
    Integer currentDebt;
    Integer daysOverdue;
    String documentNumber;
    String bankAccount;
    String bankShortName;
    boolean own;
    int listId;
}
