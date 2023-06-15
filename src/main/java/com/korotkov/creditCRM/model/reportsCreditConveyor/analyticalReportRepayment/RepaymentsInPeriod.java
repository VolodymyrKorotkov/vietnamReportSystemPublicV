package com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RepaymentsInPeriod {
    private Double totalRepaymentInAllTotal;
    private Double totalRepaymentInAllNew;
    private Double totalRepaymentInAllRepeat;

    private Double totalRepaymentInActiveTotal;
    private Double totalRepaymentInActiveNew;
    private Double totalRepaymentInActiveRepeat;

    private Double totalRepaymentInExpiredStage1Total;
    private Double totalRepaymentInExpiredStage1New;
    private Double totalRepaymentInExpiredStage1Repeat;

    private Double totalRepaymentInExpiredStage2Total;
    private Double totalRepaymentInExpiredStage2New;
    private Double totalRepaymentInExpiredStage2Repeat;

    private Double totalRepaymentInExpiredStageOtherTotal;
    private Double totalRepaymentInExpiredStageOtherNew;
    private Double totalRepaymentInExpiredStageOtherRepeat;
}
