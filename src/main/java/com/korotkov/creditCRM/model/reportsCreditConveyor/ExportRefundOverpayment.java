package com.korotkov.creditCRM.model.reportsCreditConveyor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExportRefundOverpayment {
    private LocalDateTime activatedAt;
    private Long loanId;
    private String contractNumber;
    private Double amount;
    private String comment;
    private LocalDateTime createdAt;
    private String createdBy;
}
