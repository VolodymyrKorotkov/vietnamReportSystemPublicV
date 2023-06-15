package com.korotkov.creditCRM.model.reportsCreditConveyor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExportDeactivatedPayments {
    private LocalDateTime deactivatedAt;
    private Long loanId;
    private String deactivatedBy;
    private String contract;
    private Double amount;
    private String comment;
    private LocalDateTime activatedAt;
    private LocalDateTime createdAt;
}
