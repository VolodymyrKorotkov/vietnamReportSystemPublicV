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
public class UnderwritersResultWithAppReportObject {
    private List<UnderwritersResultWithApp> underwritersResultWithAppList;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime createdAt;
    private UnderwritersResultWithApp total;

    public UnderwritersResultWithAppReportObject(List<UnderwritersResultWithApp> underwritersResultWithAppList,
                                                 LocalDate dateFrom, LocalDate dateTo) {
        this.underwritersResultWithAppList = underwritersResultWithAppList;
        this.total = createTotal(underwritersResultWithAppList);
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }

    private UnderwritersResultWithApp createTotal(List<UnderwritersResultWithApp> underwritersResultWithAppList) {
        UnderwritersResultWithApp total = new UnderwritersResultWithApp();
        total.setUnderwriter("Total");
        total.setCountApps(0L);
        total.setCountIssuedLoan(0L);
        total.setCountLoanWithDpd(0L);
        for (UnderwritersResultWithApp i : underwritersResultWithAppList) {
            total.setCountApps(total.getCountApps() + i.getCountApps());
            total.setCountIssuedLoan(total.getCountIssuedLoan() + i.getCountIssuedLoan());
            total.setCountLoanWithDpd(total.getCountLoanWithDpd() + i.getCountLoanWithDpd());
        }
        return total;
    }
}
