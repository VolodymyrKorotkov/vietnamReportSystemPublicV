package com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IssuedAmountInPeriod {
    private Double amountTotal;
    private Double amountNew;
    private Double amountRepeat;
}
