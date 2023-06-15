package com.korotkov.creditCRM.model.reportsCreditConveyor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AppAndLoanBySource {
    private String source;
    private Long countAppsTotal;
    private Long countAppsNew;
    private Long countAppsRepeat;
    private Long countLoansTotal;
    private Long countLoansNew;
    private Long countLoansRepeat;
    private Double amountLoanTotal;
    private Double amountLoanNew;
    private Double amountLoanRepeat;
}
