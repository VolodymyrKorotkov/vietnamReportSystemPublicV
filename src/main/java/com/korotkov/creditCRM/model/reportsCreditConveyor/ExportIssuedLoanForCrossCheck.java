package com.korotkov.creditCRM.model.reportsCreditConveyor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ExportIssuedLoanForCrossCheck {
    private LocalDate date;
    private String docId;
    private String contract;
    private Double amount;
    private String txId;
    private Long id;
}
