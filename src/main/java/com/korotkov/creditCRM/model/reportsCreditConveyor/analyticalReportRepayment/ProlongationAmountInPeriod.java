package com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProlongationAmountInPeriod {
    private Double prolongationAllTotal;
    private Double prolongationAllNew;
    private Double prolongationAllRepeat;
    private Double prolongationFromActiveTotal;
    private Double prolongationFromActiveNew;
    private Double prolongationFromActiveRepeat;
    private Double prolongationFromExpiredStage1Total;
    private Double prolongationFromExpiredStage1New;
    private Double prolongationFromExpiredStage1Repeat;
    private Double prolongationFromExpiredStage2Total;
    private Double prolongationFromExpiredStage2New;
    private Double prolongationFromExpiredStage2Repeat;
    private Double prolongationFromExpiredStageOtherTotal;
    private Double prolongationFromExpiredStageOtherNew;
    private Double prolongationFromExpiredStageOtherRepeat;
}
