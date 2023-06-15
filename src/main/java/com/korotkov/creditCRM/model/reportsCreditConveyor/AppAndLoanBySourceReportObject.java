package com.korotkov.creditCRM.model.reportsCreditConveyor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Getter
@Setter
@NoArgsConstructor
public class AppAndLoanBySourceReportObject {
    private List<AppAndLoanBySource> appAndLoanBySourceList;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime dateWasCreated;
    private AppAndLoanBySource total;

    public AppAndLoanBySourceReportObject(List<AppAndLoanBySource> appAndLoanBySourceList,
                                          LocalDate dateFrom, LocalDate dateTo) {
        this.appAndLoanBySourceList = appAndLoanBySourceList;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dateWasCreated = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        this.total = createTotalResult(appAndLoanBySourceList);
    }


    private AppAndLoanBySource createTotalResult(List<AppAndLoanBySource> appAndLoanBySourceList) {
        AppAndLoanBySource total = new AppAndLoanBySource();
        total.setSource("Total");
        total.setCountAppsTotal(0L);
        total.setCountAppsNew(0L);
        total.setCountAppsRepeat(0L);
        total.setCountLoansTotal(0L);
        total.setCountLoansNew(0L);
        total.setCountLoansRepeat(0L);
        total.setAmountLoanTotal((double) 0);
        total.setAmountLoanNew((double) 0);
        total.setAmountLoanRepeat((double) 0);

        for (AppAndLoanBySource item : appAndLoanBySourceList) {
            total.setCountAppsTotal(total.getCountAppsTotal() + item.getCountAppsTotal());
            total.setCountAppsNew(total.getCountAppsNew() + item.getCountAppsNew());
            total.setCountAppsRepeat(total.getCountAppsRepeat() + item.getCountAppsRepeat());
            total.setCountLoansTotal(total.getCountLoansTotal() + item.getCountLoansTotal());
            total.setCountLoansNew(total.getCountLoansNew() + item.getCountLoansNew());
            total.setCountLoansRepeat(total.getCountLoansRepeat() + item.getCountLoansRepeat());
            total.setAmountLoanTotal(total.getAmountLoanTotal() + item.getAmountLoanTotal());
            total.setAmountLoanNew(total.getAmountLoanNew() + item.getAmountLoanNew());
            total.setAmountLoanRepeat(total.getAmountLoanRepeat() + item.getAmountLoanRepeat());
        }

        return total;
    }

}
