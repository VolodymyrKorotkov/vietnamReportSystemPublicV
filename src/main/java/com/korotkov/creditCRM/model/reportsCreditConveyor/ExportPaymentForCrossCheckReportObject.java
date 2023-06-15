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
public class ExportPaymentForCrossCheckReportObject {
    private List<ExportPaymentForCrossCheck> exportPaymentForCrossCheckList;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime createdAt;
    private ExportPaymentForCrossCheck total;

    public ExportPaymentForCrossCheckReportObject(List<ExportPaymentForCrossCheck> exportPaymentForCrossCheckList,
                                                  LocalDate dateFrom, LocalDate dateTo) {
        this.exportPaymentForCrossCheckList = exportPaymentForCrossCheckList;
        this.total = createTotal(exportPaymentForCrossCheckList);
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }

    private ExportPaymentForCrossCheck createTotal(List<ExportPaymentForCrossCheck> exportPaymentForCrossCheckList) {
        ExportPaymentForCrossCheck total = new ExportPaymentForCrossCheck();
        total.setPayment((double) 0);
        total.setAuto((double) 0);
        total.setManual((double) 0);

        for (ExportPaymentForCrossCheck i : exportPaymentForCrossCheckList) {
            total.setPayment(total.getPayment() + i.getPayment());
            total.setAuto(total.getAuto() + i.getAuto());
            total.setManual(total.getManual() + i.getManual());
        }
        return total;
    }
}
