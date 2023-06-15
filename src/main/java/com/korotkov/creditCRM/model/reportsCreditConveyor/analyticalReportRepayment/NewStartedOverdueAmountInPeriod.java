package com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewStartedOverdueAmountInPeriod {
    private Double principleStage1Total;
    private Double principleStage2Total;
    private Double principleStageOtherTotal;
    private Double principleStage1New;
    private Double principleStage2New;
    private Double principleStageOtherNew;
    private Double principleStage1Repeat;
    private Double principleStage2Repeat;
    private Double principleStageOtherRepeat;
}
