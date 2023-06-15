package com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnalyticalPartForRepaymentReport {
    private Double repaymentLevelInActiveStageTotal;
    private Double incomeLevelInActiveStageTotal;

    private Double amountInWorkInExpiredStage1Total;
    private Double repaymentLevelInExpiredStage1Total;
    private Double incomeLevelInExpiredStage1Total;
    private Double commonRepaymentInExpiredStage1Total;

    private Double amountInWorkInExpiredStage2Total;
    private Double repaymentLevelInExpiredStage2Total;
    private Double incomeLevelInExpiredStage2Total;
    private Double commonRepaymentInExpiredStage2Total;

    private Double amountInWorkInExpiredStageOtherTotal;
    private Double repaymentLevelInExpiredStageOtherTotal;
    private Double incomeLevelInExpiredStageOtherTotal;
    private Double commonRepaymentInExpiredStageOtherTotal;

    private Double repaymentLevelInActiveStageNew;
    private Double incomeLevelInActiveStageNew;

    private Double amountInWorkInExpiredStage1New;
    private Double repaymentLevelInExpiredStage1New;
    private Double incomeLevelInExpiredStage1New;
    private Double commonRepaymentInExpiredStage1New;

    private Double amountInWorkInExpiredStage2New;
    private Double repaymentLevelInExpiredStage2New;
    private Double incomeLevelInExpiredStage2New;
    private Double commonRepaymentInExpiredStage2New;

    private Double amountInWorkInExpiredStageOtherNew;
    private Double repaymentLevelInExpiredStageOtherNew;
    private Double incomeLevelInExpiredStageOtherNew;
    private Double commonRepaymentInExpiredStageOtherNew;

    private Double repaymentLevelInActiveStageRepeat;
    private Double incomeLevelInActiveStageRepeat;

    private Double amountInWorkInExpiredStage1Repeat;
    private Double repaymentLevelInExpiredStage1Repeat;
    private Double incomeLevelInExpiredStage1Repeat;
    private Double commonRepaymentInExpiredStage1Repeat;

    private Double amountInWorkInExpiredStage2Repeat;
    private Double repaymentLevelInExpiredStage2Repeat;
    private Double incomeLevelInExpiredStage2Repeat;
    private Double commonRepaymentInExpiredStage2Repeat;

    private Double amountInWorkInExpiredStageOtherRepeat;
    private Double repaymentLevelInExpiredStageOtherRepeat;
    private Double incomeLevelInExpiredStageOtherRepeat;
    private Double commonRepaymentInExpiredStageOtherRepeat;
}
