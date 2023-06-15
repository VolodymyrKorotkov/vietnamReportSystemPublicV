package com.korotkov.creditCRM.model.reportsCreditConveyor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ExportPaymentForCrossCheck {
    private LocalDate date;
    private Long loanId;
    private Double payment;
    private String contract;
    private String docId;
    private Double auto;
    private Double manual;
    private String account;
}
