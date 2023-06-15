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
public class ExportIssuedLoanForCrossCheckReportObject {
    private List<ExportIssuedLoanForCrossCheck> exportIssuedLoanForCrossCheckList;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime createdAt;
    private ExportIssuedLoanForCrossCheck total;

    public ExportIssuedLoanForCrossCheckReportObject(List<ExportIssuedLoanForCrossCheck> exportIssuedLoanForCrossCheckList,
                                                     LocalDate dateFrom, LocalDate dateTo) {
        this.exportIssuedLoanForCrossCheckList = exportIssuedLoanForCrossCheckList;
        this.total = createTotal(exportIssuedLoanForCrossCheckList);
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }

    private ExportIssuedLoanForCrossCheck createTotal(List<ExportIssuedLoanForCrossCheck> exportIssuedLoanForCrossCheckList) {
        ExportIssuedLoanForCrossCheck total = new ExportIssuedLoanForCrossCheck();
        total.setAmount((double) 0);
        for (ExportIssuedLoanForCrossCheck i : exportIssuedLoanForCrossCheckList) {
            total.setAmount(total.getAmount() + i.getAmount());
        }
        return total;
    }
}
