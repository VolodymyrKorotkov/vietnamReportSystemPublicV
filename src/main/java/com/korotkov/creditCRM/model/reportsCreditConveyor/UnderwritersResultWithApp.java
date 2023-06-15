package com.korotkov.creditCRM.model.reportsCreditConveyor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnderwritersResultWithApp {
    private String underwriter;
    private Long countApps;
    private Long countIssuedLoan;
    private Long countLoanWithDpd;
}
