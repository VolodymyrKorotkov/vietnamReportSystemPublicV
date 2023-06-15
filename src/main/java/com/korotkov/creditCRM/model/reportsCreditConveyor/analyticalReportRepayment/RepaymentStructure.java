package com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RepaymentStructure {
    private Double commonProfitabilityInCompanyTotal;
    private Double shareLevelRepaymentInActiveStageTotal;
    private Double shareLevelRepaymentInExpiredStage1Total;
    private Double shareLevelRepaymentInExpiredStage2Total;
    private Double shareLevelRepaymentInExpiredStageOtherTotal;
    private Double shareLevelIncomeInActiveStageTotal;
    private Double shareLevelIncomeInExpiredStage1Total;
    private Double shareLevelIncomeInExpiredStage2Total;
    private Double shareLevelIncomeInExpiredStageOtherTotal;

    private Double commonProfitabilityInCompanyNew;
    private Double shareLevelRepaymentInActiveStageNew;
    private Double shareLevelRepaymentInExpiredStage1New;
    private Double shareLevelRepaymentInExpiredStage2New;
    private Double shareLevelRepaymentInExpiredStageOtherNew;
    private Double shareLevelIncomeInActiveStageNew;
    private Double shareLevelIncomeInExpiredStage1New;
    private Double shareLevelIncomeInExpiredStage2New;
    private Double shareLevelIncomeInExpiredStageOtherNew;

    private Double commonProfitabilityInCompanyRepeat;
    private Double shareLevelRepaymentInActiveStageRepeat;
    private Double shareLevelRepaymentInExpiredStage1Repeat;
    private Double shareLevelRepaymentInExpiredStage2Repeat;
    private Double shareLevelRepaymentInExpiredStageOtherRepeat;
    private Double shareLevelIncomeInActiveStageRepeat;
    private Double shareLevelIncomeInExpiredStage1Repeat;
    private Double shareLevelIncomeInExpiredStage2Repeat;
    private Double shareLevelIncomeInExpiredStageOtherRepeat;
}
