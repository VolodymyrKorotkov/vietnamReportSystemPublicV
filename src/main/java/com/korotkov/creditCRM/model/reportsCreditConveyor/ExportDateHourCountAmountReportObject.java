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
public class ExportDateHourCountAmountReportObject {
    private List<ExportDateHourCountAmount> exportDateHourCountAmountList;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime createdAt;
    private ExportDateHourCountAmount total;

    public ExportDateHourCountAmountReportObject(List<ExportDateHourCountAmount> exportDateHourCountAmountList,
                                                 LocalDate dateFrom, LocalDate dateTo) {
        this.exportDateHourCountAmountList = exportDateHourCountAmountList;
        this.total = createTotal(exportDateHourCountAmountList);
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }

    private ExportDateHourCountAmount createTotal(List<ExportDateHourCountAmount> exportDateHourCountAmountList) {
        ExportDateHourCountAmount total = new ExportDateHourCountAmount();
        total.setCountTotal(0L);
        total.setAmountTotal((double) 0);
        total.setCountNew(0L);
        total.setAmountNew((double) 0);
        total.setCountRepeat(0L);
        total.setAmountRepeat((double) 0);

        for (ExportDateHourCountAmount i : exportDateHourCountAmountList) {
            total.setCountTotal(total.getCountTotal() + i.getCountTotal());
            total.setAmountTotal(total.getAmountTotal() + i.getAmountTotal());
            total.setCountNew(total.getCountNew() + i.getCountNew());
            total.setAmountNew(total.getAmountNew() + i.getAmountNew());
            total.setCountRepeat(total.getCountRepeat() + i.getCountRepeat());
            total.setAmountRepeat(total.getAmountRepeat() + i.getAmountRepeat());
        }

        return total;
    }
}
