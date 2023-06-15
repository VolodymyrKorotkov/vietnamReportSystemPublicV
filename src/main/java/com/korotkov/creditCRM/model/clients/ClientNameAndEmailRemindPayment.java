package com.korotkov.creditCRM.model.clients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ClientNameAndEmailRemindPayment {
    private String email;
    private String name;
    private String contactNumber;
    private LocalDate maturityDate;
    private String virtualAccount;
    private String documentNumber;
    private Double debtAmount;
    private LocalDateTime contactSignedAt;
}
