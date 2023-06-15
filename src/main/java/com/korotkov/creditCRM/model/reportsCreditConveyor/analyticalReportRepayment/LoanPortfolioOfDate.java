package com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoanPortfolioOfDate {
    private Double principleActiveTotal;
    private Double principleExpiredStage1Total;
    private Double principleExpiredStage2Total;
    private Double principleExpiredStageOtherTotal;
    private Double principleActiveNew;
    private Double principleExpiredStage1New;
    private Double principleExpiredStage2New;
    private Double principleExpiredStageOtherNew;
    private Double principleActiveRepeat;
    private Double principleExpiredStage1Repeat;
    private Double principleExpiredStage2Repeat;
    private Double principleExpiredStageOtherRepeat;
}
