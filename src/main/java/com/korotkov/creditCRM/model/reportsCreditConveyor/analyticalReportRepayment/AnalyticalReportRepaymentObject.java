package com.korotkov.creditCRM.model.reportsCreditConveyor.analyticalReportRepayment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Getter
@Setter
@NoArgsConstructor
public class AnalyticalReportRepaymentObject {
    LocalDate dateFrom;
    LocalDate dateTo;
    Integer lastDayOverdueStage1;
    Integer lastDayOverdueStage2;
    LocalDateTime createdAt;

    private LoanPortfolioOfDate loanPortfolioOfDate;
    private IssuedAmountInPeriod issuedAmountInPeriod;
    private ProlongationAmountInPeriod prolongationAmountInPeriod;
    private ContractsAmountInPeriod contractsAmountInPeriod;
    private NewStartedOverdueAmountInPeriod newStartedOverdueAmountInPeriod;
    private PaymentsAmountInPeriod paymentsAmountInPeriod;

    private RepaymentsInPeriod repaymentsInPeriod;

    private AnalyticalPartForRepaymentReport analyticalPartForRepaymentReport;
    private RepaymentStructure repaymentStructure;

    public AnalyticalReportRepaymentObject(LocalDate dateFrom, LocalDate dateTo,
                                           Integer lastDayOverdueStage1,
                                           Integer lastDayOverdueStage2,
                                           LoanPortfolioOfDate loanPortfolioOfDate,
                                           NewStartedOverdueAmountInPeriod newStartedOverdueAmountInPeriodWithoutProlongation,
                                           NewStartedOverdueAmountInPeriod newStartedOverdueAmountInPeriodOnlyAfterProlongation,
                                           IssuedAmountInPeriod issuedAmountInPeriod,
                                           ProlongationAmountInPeriod prolongationAmountInPeriodFromNextDate,
                                           ProlongationAmountInPeriod prolongationAmountInPeriodForDateWhichRequestedThatDate,
                                           PaymentsAmountInPeriod paymentsAmountInPeriod) {
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.lastDayOverdueStage1 = lastDayOverdueStage1;
        this.lastDayOverdueStage2 = lastDayOverdueStage2;

        this.loanPortfolioOfDate = loanPortfolioOfDate;
        this.newStartedOverdueAmountInPeriod =
                sumStartedOverdueObjects(newStartedOverdueAmountInPeriodWithoutProlongation,
                        newStartedOverdueAmountInPeriodOnlyAfterProlongation);
        this.issuedAmountInPeriod = issuedAmountInPeriod;
        this.prolongationAmountInPeriod = sumProlongationsObjects(prolongationAmountInPeriodFromNextDate,
                prolongationAmountInPeriodForDateWhichRequestedThatDate);
        this.paymentsAmountInPeriod = paymentsAmountInPeriod;
        this.contractsAmountInPeriod = createContractsAmountObject(this.issuedAmountInPeriod,
                this.prolongationAmountInPeriod);
        this.repaymentsInPeriod = createRepaymentsInPeriod(this.prolongationAmountInPeriod,
                this.paymentsAmountInPeriod);
        this.analyticalPartForRepaymentReport = createAnalyticalPart(this.paymentsAmountInPeriod,
                this.prolongationAmountInPeriod, this.contractsAmountInPeriod,
                this.loanPortfolioOfDate, this.newStartedOverdueAmountInPeriod,
                this.repaymentsInPeriod);
        this.repaymentStructure = createRepaymentStructure(this.paymentsAmountInPeriod,
                this.prolongationAmountInPeriod, this.repaymentsInPeriod);
    }

    private RepaymentStructure createRepaymentStructure(PaymentsAmountInPeriod payments,
                                                        ProlongationAmountInPeriod prolongations,
                                                        RepaymentsInPeriod repayments) {
        RepaymentStructure repaymentStructure = new RepaymentStructure();
        repaymentStructure.setCommonProfitabilityInCompanyTotal(payments.getPaidIncomeAllTotal() /
                (payments.getPaidPrincipleAllTotal() + prolongations.getProlongationAllTotal()));
        repaymentStructure.setCommonProfitabilityInCompanyNew(payments.getPaidIncomeAllNew() /
                (payments.getPaidPrincipleAllNew() + prolongations.getProlongationAllNew()));
        repaymentStructure.setCommonProfitabilityInCompanyRepeat(payments.getPaidIncomeAllRepeat() /
                (payments.getPaidPrincipleAllRepeat() + prolongations.getProlongationAllRepeat()));

        repaymentStructure.setShareLevelRepaymentInActiveStageTotal(repayments.getTotalRepaymentInActiveTotal() /
                repayments.getTotalRepaymentInAllTotal());
        repaymentStructure.setShareLevelRepaymentInActiveStageNew(repayments.getTotalRepaymentInActiveNew() /
                repayments.getTotalRepaymentInAllNew());
        repaymentStructure.setShareLevelRepaymentInActiveStageRepeat(repayments.getTotalRepaymentInActiveRepeat() /
                repayments.getTotalRepaymentInAllRepeat());

        repaymentStructure.setShareLevelRepaymentInExpiredStage1Total(repayments.getTotalRepaymentInExpiredStage1Total() /
                repayments.getTotalRepaymentInAllTotal());
        repaymentStructure.setShareLevelRepaymentInExpiredStage1New(repayments.getTotalRepaymentInExpiredStage1New() /
                repayments.getTotalRepaymentInAllNew());
        repaymentStructure.setShareLevelRepaymentInExpiredStage1Repeat(repayments.getTotalRepaymentInExpiredStage1Repeat() /
                repayments.getTotalRepaymentInAllRepeat());

        repaymentStructure.setShareLevelRepaymentInExpiredStage2Total(repayments.getTotalRepaymentInExpiredStage2Total() /
                repayments.getTotalRepaymentInAllTotal());
        repaymentStructure.setShareLevelRepaymentInExpiredStage2New(repayments.getTotalRepaymentInExpiredStage2New() /
                repayments.getTotalRepaymentInAllNew());
        repaymentStructure.setShareLevelRepaymentInExpiredStage2Repeat(repayments.getTotalRepaymentInExpiredStage2Repeat() /
                repayments.getTotalRepaymentInAllRepeat());

        repaymentStructure.setShareLevelRepaymentInExpiredStageOtherTotal(repayments.getTotalRepaymentInExpiredStageOtherTotal() /
                repayments.getTotalRepaymentInAllTotal());
        repaymentStructure.setShareLevelRepaymentInExpiredStageOtherNew(repayments.getTotalRepaymentInExpiredStageOtherNew() /
                repayments.getTotalRepaymentInAllNew());
        repaymentStructure.setShareLevelRepaymentInExpiredStageOtherRepeat(repayments.getTotalRepaymentInExpiredStageOtherRepeat() /
                repayments.getTotalRepaymentInAllRepeat());


        repaymentStructure.setShareLevelIncomeInActiveStageTotal(payments.getPaidIncomeInActiveTotal() /
                payments.getPaidIncomeAllTotal());
        repaymentStructure.setShareLevelIncomeInActiveStageNew(payments.getPaidIncomeInActiveNew() /
                payments.getPaidIncomeAllNew());
        repaymentStructure.setShareLevelIncomeInActiveStageRepeat(payments.getPaidIncomeInActiveRepeat() /
                payments.getPaidIncomeAllRepeat());

        repaymentStructure.setShareLevelIncomeInExpiredStage1Total(payments.getPaidIncomeInExpiredStage1Total() /
                payments.getPaidIncomeAllTotal());
        repaymentStructure.setShareLevelIncomeInExpiredStage1New(payments.getPaidIncomeInExpiredStage1New() /
                payments.getPaidIncomeAllNew());
        repaymentStructure.setShareLevelIncomeInExpiredStage1Repeat(payments.getPaidIncomeInExpiredStage1Repeat() /
                payments.getPaidIncomeAllRepeat());

        repaymentStructure.setShareLevelIncomeInExpiredStage2Total(payments.getPaidIncomeInExpiredStage2Total() /
                payments.getPaidIncomeAllTotal());
        repaymentStructure.setShareLevelIncomeInExpiredStage2New(payments.getPaidIncomeInExpiredStage2New() /
                payments.getPaidIncomeAllNew());
        repaymentStructure.setShareLevelIncomeInExpiredStage2Repeat(payments.getPaidIncomeInExpiredStage2Repeat() /
                payments.getPaidIncomeAllRepeat());

        repaymentStructure.setShareLevelIncomeInExpiredStageOtherTotal(payments.getPaidIncomeInExpiredStageOtherTotal() /
                payments.getPaidIncomeAllTotal());
        repaymentStructure.setShareLevelIncomeInExpiredStageOtherNew(payments.getPaidIncomeInExpiredStageOtherNew() /
                payments.getPaidIncomeAllNew());
        repaymentStructure.setShareLevelIncomeInExpiredStageOtherRepeat(payments.getPaidIncomeInExpiredStageOtherRepeat() /
                payments.getPaidIncomeAllRepeat());

        return repaymentStructure;
    }

    private AnalyticalPartForRepaymentReport createAnalyticalPart(PaymentsAmountInPeriod payments,
                                                                  ProlongationAmountInPeriod prolongations,
                                                                  ContractsAmountInPeriod contracts,
                                                                  LoanPortfolioOfDate portfolio,
                                                                  NewStartedOverdueAmountInPeriod newStartedOverdue,
                                                                  RepaymentsInPeriod repayments) {
        AnalyticalPartForRepaymentReport analyticalPart = new AnalyticalPartForRepaymentReport();

        analyticalPart.setRepaymentLevelInActiveStageTotal((payments.getPaidPrincipleInActiveTotal() +
                prolongations.getProlongationFromActiveTotal()) / contracts.getAmountTotal());
        analyticalPart.setIncomeLevelInActiveStageTotal(payments.getPaidIncomeInActiveTotal() /
                (payments.getPaidPrincipleInActiveTotal() +
                        prolongations.getProlongationFromActiveTotal()));

        analyticalPart.setRepaymentLevelInActiveStageNew((payments.getPaidPrincipleInActiveNew() +
                prolongations.getProlongationFromActiveNew()) / contracts.getAmountNew());
        analyticalPart.setIncomeLevelInActiveStageNew(payments.getPaidIncomeInActiveNew() /
                (payments.getPaidPrincipleInActiveNew() +
                        prolongations.getProlongationFromActiveNew()));

        analyticalPart.setRepaymentLevelInActiveStageRepeat((payments.getPaidPrincipleInActiveRepeat() +
                prolongations.getProlongationFromActiveRepeat()) / contracts.getAmountRepeat());
        analyticalPart.setIncomeLevelInActiveStageRepeat(payments.getPaidIncomeInActiveRepeat() /
                (payments.getPaidPrincipleInActiveRepeat() +
                        prolongations.getProlongationFromActiveRepeat()));

        analyticalPart.setAmountInWorkInExpiredStage1Total(portfolio.getPrincipleExpiredStage1Total() +
                newStartedOverdue.getPrincipleStage1Total());
        analyticalPart.setRepaymentLevelInExpiredStage1Total((payments.getPaidPrincipleInExpiredStage1Total() +
                prolongations.getProlongationFromExpiredStage1Total()) /
                analyticalPart.getAmountInWorkInExpiredStage1Total());
        analyticalPart.setIncomeLevelInExpiredStage1Total(payments.getPaidIncomeInExpiredStage1Total() /
                (payments.getPaidPrincipleInExpiredStage1Total() +
                        prolongations.getProlongationFromExpiredStage1Total()));
        analyticalPart.setCommonRepaymentInExpiredStage1Total(repayments.getTotalRepaymentInExpiredStage1Total() /
                analyticalPart.getAmountInWorkInExpiredStage1Total());

        analyticalPart.setAmountInWorkInExpiredStage1New(portfolio.getPrincipleExpiredStage1New() +
                newStartedOverdue.getPrincipleStage1New());
        analyticalPart.setRepaymentLevelInExpiredStage1New((payments.getPaidPrincipleInExpiredStage1New() +
                prolongations.getProlongationFromExpiredStage1New()) /
                analyticalPart.getAmountInWorkInExpiredStage1New());
        analyticalPart.setIncomeLevelInExpiredStage1New(payments.getPaidIncomeInExpiredStage1New() /
                (payments.getPaidPrincipleInExpiredStage1New() +
                        prolongations.getProlongationFromExpiredStage1New()));
        analyticalPart.setCommonRepaymentInExpiredStage1New(repayments.getTotalRepaymentInExpiredStage1New() /
                analyticalPart.getAmountInWorkInExpiredStage1New());

        analyticalPart.setAmountInWorkInExpiredStage1Repeat(portfolio.getPrincipleExpiredStage1Repeat() +
                newStartedOverdue.getPrincipleStage1Repeat());
        analyticalPart.setRepaymentLevelInExpiredStage1Repeat((payments.getPaidPrincipleInExpiredStage1Repeat() +
                prolongations.getProlongationFromExpiredStage1Repeat()) /
                analyticalPart.getAmountInWorkInExpiredStage1Repeat());
        analyticalPart.setIncomeLevelInExpiredStage1Repeat(payments.getPaidIncomeInExpiredStage1Repeat() /
                (payments.getPaidPrincipleInExpiredStage1Repeat() +
                        prolongations.getProlongationFromExpiredStage1Repeat()));
        analyticalPart.setCommonRepaymentInExpiredStage1Repeat(repayments.getTotalRepaymentInExpiredStage1Repeat() /
                analyticalPart.getAmountInWorkInExpiredStage1Repeat());

        analyticalPart.setAmountInWorkInExpiredStage2Total(portfolio.getPrincipleExpiredStage2Total() +
                newStartedOverdue.getPrincipleStage2Total());
        analyticalPart.setRepaymentLevelInExpiredStage2Total((payments.getPaidPrincipleInExpiredStage2Total() +
                prolongations.getProlongationFromExpiredStage2Total()) /
                analyticalPart.getAmountInWorkInExpiredStage2Total());
        analyticalPart.setIncomeLevelInExpiredStage2Total(payments.getPaidIncomeInExpiredStage2Total() /
                (payments.getPaidPrincipleInExpiredStage2Total() +
                        prolongations.getProlongationFromExpiredStage2Total()));
        analyticalPart.setCommonRepaymentInExpiredStage2Total(repayments.getTotalRepaymentInExpiredStage2Total() /
                analyticalPart.getAmountInWorkInExpiredStage2Total());

        analyticalPart.setAmountInWorkInExpiredStage2New(portfolio.getPrincipleExpiredStage2New() +
                newStartedOverdue.getPrincipleStage2New());
        analyticalPart.setRepaymentLevelInExpiredStage2New((payments.getPaidPrincipleInExpiredStage2New() +
                prolongations.getProlongationFromExpiredStage2New()) /
                analyticalPart.getAmountInWorkInExpiredStage2New());
        analyticalPart.setIncomeLevelInExpiredStage2New(payments.getPaidIncomeInExpiredStage2New() /
                (payments.getPaidPrincipleInExpiredStage2New() +
                        prolongations.getProlongationFromExpiredStage2New()));
        analyticalPart.setCommonRepaymentInExpiredStage2New(repayments.getTotalRepaymentInExpiredStage2New() /
                analyticalPart.getAmountInWorkInExpiredStage2New());

        analyticalPart.setAmountInWorkInExpiredStage2Repeat(portfolio.getPrincipleExpiredStage2Repeat() +
                newStartedOverdue.getPrincipleStage2Repeat());
        analyticalPart.setRepaymentLevelInExpiredStage2Repeat((payments.getPaidPrincipleInExpiredStage2Repeat() +
                prolongations.getProlongationFromExpiredStage2Repeat()) /
                analyticalPart.getAmountInWorkInExpiredStage2Repeat());
        analyticalPart.setIncomeLevelInExpiredStage2Repeat(payments.getPaidIncomeInExpiredStage2Repeat() /
                (payments.getPaidPrincipleInExpiredStage2Repeat() +
                        prolongations.getProlongationFromExpiredStage2Repeat()));
        analyticalPart.setCommonRepaymentInExpiredStage2Repeat(repayments.getTotalRepaymentInExpiredStage2Repeat() /
                analyticalPart.getAmountInWorkInExpiredStage2Repeat());

        analyticalPart.setAmountInWorkInExpiredStageOtherTotal(portfolio.getPrincipleExpiredStageOtherTotal() +
                newStartedOverdue.getPrincipleStageOtherTotal());
        analyticalPart.setRepaymentLevelInExpiredStageOtherTotal((payments.getPaidPrincipleInExpiredStageOtherTotal() +
                prolongations.getProlongationFromExpiredStageOtherTotal()) /
                analyticalPart.getAmountInWorkInExpiredStageOtherTotal());
        analyticalPart.setIncomeLevelInExpiredStageOtherTotal(payments.getPaidIncomeInExpiredStageOtherTotal() /
                (payments.getPaidPrincipleInExpiredStageOtherTotal() +
                        prolongations.getProlongationFromExpiredStageOtherTotal()));
        analyticalPart.setCommonRepaymentInExpiredStageOtherTotal(repayments.getTotalRepaymentInExpiredStageOtherTotal() /
                analyticalPart.getAmountInWorkInExpiredStageOtherTotal());

        analyticalPart.setAmountInWorkInExpiredStageOtherNew(portfolio.getPrincipleExpiredStageOtherNew() +
                newStartedOverdue.getPrincipleStageOtherNew());
        analyticalPart.setRepaymentLevelInExpiredStageOtherNew((payments.getPaidPrincipleInExpiredStageOtherNew() +
                prolongations.getProlongationFromExpiredStageOtherNew()) /
                analyticalPart.getAmountInWorkInExpiredStageOtherNew());
        analyticalPart.setIncomeLevelInExpiredStageOtherNew(payments.getPaidIncomeInExpiredStageOtherNew() /
                (payments.getPaidPrincipleInExpiredStageOtherNew() +
                        prolongations.getProlongationFromExpiredStageOtherNew()));
        analyticalPart.setCommonRepaymentInExpiredStageOtherNew(repayments.getTotalRepaymentInExpiredStageOtherNew() /
                analyticalPart.getAmountInWorkInExpiredStageOtherNew());

        analyticalPart.setAmountInWorkInExpiredStageOtherRepeat(portfolio.getPrincipleExpiredStageOtherRepeat() +
                newStartedOverdue.getPrincipleStageOtherRepeat());
        analyticalPart.setRepaymentLevelInExpiredStageOtherRepeat((payments.getPaidPrincipleInExpiredStageOtherRepeat() +
                prolongations.getProlongationFromExpiredStageOtherRepeat()) /
                analyticalPart.getAmountInWorkInExpiredStageOtherRepeat());
        analyticalPart.setIncomeLevelInExpiredStageOtherRepeat(payments.getPaidIncomeInExpiredStageOtherRepeat() /
                (payments.getPaidPrincipleInExpiredStageOtherRepeat() +
                        prolongations.getProlongationFromExpiredStageOtherRepeat()));
        analyticalPart.setCommonRepaymentInExpiredStageOtherRepeat(repayments.getTotalRepaymentInExpiredStageOtherRepeat() /
                analyticalPart.getAmountInWorkInExpiredStageOtherRepeat());

        return analyticalPart;
    }

    private RepaymentsInPeriod createRepaymentsInPeriod(ProlongationAmountInPeriod prolongations,
                                                        PaymentsAmountInPeriod payments) {
        RepaymentsInPeriod repaymentsInPeriod = new RepaymentsInPeriod();

        repaymentsInPeriod.setTotalRepaymentInAllTotal(payments.getPaidCommonAllTotal() +
                prolongations.getProlongationAllTotal());

        repaymentsInPeriod.setTotalRepaymentInActiveTotal(payments.getPaidCommonInActiveTotal() +
                prolongations.getProlongationFromActiveTotal());
        repaymentsInPeriod.setTotalRepaymentInExpiredStage1Total(payments.getPaidCommonInExpiredStage1Total() +
                prolongations.getProlongationFromExpiredStage1Total());
        repaymentsInPeriod.setTotalRepaymentInExpiredStage2Total(payments.getPaidCommonInExpiredStage2Total() +
                prolongations.getProlongationFromExpiredStage2Total());
        repaymentsInPeriod.setTotalRepaymentInExpiredStageOtherTotal(payments.getPaidCommonInExpiredStageOtherTotal() +
                prolongations.getProlongationFromExpiredStageOtherTotal());

        repaymentsInPeriod.setTotalRepaymentInAllNew(payments.getPaidCommonAllNew() +
                prolongations.getProlongationAllNew());

        repaymentsInPeriod.setTotalRepaymentInActiveNew(payments.getPaidCommonInActiveNew() +
                prolongations.getProlongationFromActiveNew());
        repaymentsInPeriod.setTotalRepaymentInExpiredStage1New(payments.getPaidCommonInExpiredStage1New() +
                prolongations.getProlongationFromExpiredStage1New());
        repaymentsInPeriod.setTotalRepaymentInExpiredStage2New(payments.getPaidCommonInExpiredStage2New() +
                prolongations.getProlongationFromExpiredStage2New());
        repaymentsInPeriod.setTotalRepaymentInExpiredStageOtherNew(payments.getPaidCommonInExpiredStageOtherNew() +
                prolongations.getProlongationFromExpiredStageOtherNew());

        repaymentsInPeriod.setTotalRepaymentInAllRepeat(payments.getPaidCommonAllRepeat() +
                prolongations.getProlongationAllRepeat());

        repaymentsInPeriod.setTotalRepaymentInActiveRepeat(payments.getPaidCommonInActiveRepeat() +
                prolongations.getProlongationFromActiveRepeat());
        repaymentsInPeriod.setTotalRepaymentInExpiredStage1Repeat(payments.getPaidCommonInExpiredStage1Repeat() +
                prolongations.getProlongationFromExpiredStage1Repeat());
        repaymentsInPeriod.setTotalRepaymentInExpiredStage2Repeat(payments.getPaidCommonInExpiredStage2Repeat() +
                prolongations.getProlongationFromExpiredStage2Repeat());
        repaymentsInPeriod.setTotalRepaymentInExpiredStageOtherRepeat(payments.getPaidCommonInExpiredStageOtherRepeat() +
                prolongations.getProlongationFromExpiredStageOtherRepeat());

        return repaymentsInPeriod;
    }

    private ContractsAmountInPeriod createContractsAmountObject(IssuedAmountInPeriod loans,
                                                                ProlongationAmountInPeriod prolongations) {
        ContractsAmountInPeriod contracts = new ContractsAmountInPeriod();
        contracts.setAmountTotal(loans.getAmountTotal() + prolongations.getProlongationAllTotal());
        contracts.setAmountNew(loans.getAmountNew() + prolongations.getProlongationAllNew());
        contracts.setAmountRepeat(loans.getAmountRepeat() + prolongations.getProlongationAllRepeat());
        return contracts;
    }

    private NewStartedOverdueAmountInPeriod sumStartedOverdueObjects(NewStartedOverdueAmountInPeriod newStartedOverdueAmountInPeriodWithoutProlongation,
                                                                     NewStartedOverdueAmountInPeriod newStartedOverdueAmountInPeriodOnlyAfterProlongation) {
        newStartedOverdueAmountInPeriodWithoutProlongation
                .setPrincipleStage1Total(newStartedOverdueAmountInPeriodWithoutProlongation.getPrincipleStage1Total() +
                        newStartedOverdueAmountInPeriodOnlyAfterProlongation.getPrincipleStage1Total());
        newStartedOverdueAmountInPeriodWithoutProlongation
                .setPrincipleStage1New(newStartedOverdueAmountInPeriodWithoutProlongation.getPrincipleStage1New() +
                        newStartedOverdueAmountInPeriodOnlyAfterProlongation.getPrincipleStage1New());
        newStartedOverdueAmountInPeriodWithoutProlongation
                .setPrincipleStage1Repeat(newStartedOverdueAmountInPeriodWithoutProlongation.getPrincipleStage1Repeat() +
                        newStartedOverdueAmountInPeriodOnlyAfterProlongation.getPrincipleStage1Repeat());

        return newStartedOverdueAmountInPeriodWithoutProlongation;
    }

    private ProlongationAmountInPeriod sumProlongationsObjects(ProlongationAmountInPeriod prolongationAmountInPeriodFromNextDate,
                                                               ProlongationAmountInPeriod prolongationAmountInPeriodForDateWhichRequestedThatDate) {
        prolongationAmountInPeriodFromNextDate
                .setProlongationAllTotal(prolongationAmountInPeriodFromNextDate.getProlongationAllTotal() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationAllTotal());
        prolongationAmountInPeriodFromNextDate
                .setProlongationFromActiveTotal(prolongationAmountInPeriodFromNextDate.getProlongationFromActiveTotal() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationFromActiveTotal());
        prolongationAmountInPeriodFromNextDate
                .setProlongationFromExpiredStage1Total(prolongationAmountInPeriodFromNextDate.getProlongationFromExpiredStage1Total() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationFromExpiredStage1Total());
        prolongationAmountInPeriodFromNextDate
                .setProlongationFromExpiredStage2Total(prolongationAmountInPeriodFromNextDate.getProlongationFromExpiredStage2Total() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationFromExpiredStage2Total());
        prolongationAmountInPeriodFromNextDate
                .setProlongationFromExpiredStageOtherTotal(prolongationAmountInPeriodFromNextDate.getProlongationFromExpiredStageOtherTotal() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationFromExpiredStageOtherTotal());

        prolongationAmountInPeriodFromNextDate
                .setProlongationAllNew(prolongationAmountInPeriodFromNextDate.getProlongationAllNew() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationAllNew());
        prolongationAmountInPeriodFromNextDate
                .setProlongationFromActiveNew(prolongationAmountInPeriodFromNextDate.getProlongationFromActiveNew() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationFromActiveNew());
        prolongationAmountInPeriodFromNextDate
                .setProlongationFromExpiredStage1New(prolongationAmountInPeriodFromNextDate.getProlongationFromExpiredStage1New() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationFromExpiredStage1New());
        prolongationAmountInPeriodFromNextDate
                .setProlongationFromExpiredStage2New(prolongationAmountInPeriodFromNextDate.getProlongationFromExpiredStage2New() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationFromExpiredStage2New());
        prolongationAmountInPeriodFromNextDate
                .setProlongationFromExpiredStageOtherNew(prolongationAmountInPeriodFromNextDate.getProlongationFromExpiredStageOtherNew() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationFromExpiredStageOtherNew());

        prolongationAmountInPeriodFromNextDate
                .setProlongationAllRepeat(prolongationAmountInPeriodFromNextDate.getProlongationAllRepeat() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationAllRepeat());
        prolongationAmountInPeriodFromNextDate
                .setProlongationFromActiveRepeat(prolongationAmountInPeriodFromNextDate.getProlongationFromActiveRepeat() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationFromActiveRepeat());
        prolongationAmountInPeriodFromNextDate
                .setProlongationFromExpiredStage1Repeat(prolongationAmountInPeriodFromNextDate.getProlongationFromExpiredStage1Repeat() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationFromExpiredStage1Repeat());
        prolongationAmountInPeriodFromNextDate
                .setProlongationFromExpiredStage2Repeat(prolongationAmountInPeriodFromNextDate.getProlongationFromExpiredStage2Repeat() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationFromExpiredStage2Repeat());
        prolongationAmountInPeriodFromNextDate
                .setProlongationFromExpiredStageOtherRepeat(prolongationAmountInPeriodFromNextDate.getProlongationFromExpiredStageOtherRepeat() +
                        prolongationAmountInPeriodForDateWhichRequestedThatDate.getProlongationFromExpiredStageOtherRepeat());

        return prolongationAmountInPeriodFromNextDate;
    }

}
