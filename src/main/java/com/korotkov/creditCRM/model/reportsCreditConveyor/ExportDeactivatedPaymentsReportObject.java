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
public class ExportDeactivatedPaymentsReportObject {
    private List<ExportDeactivatedPayments> exportDeactivatedPaymentsList;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime createdAt;

    public ExportDeactivatedPaymentsReportObject(List<ExportDeactivatedPayments> exportDeactivatedPaymentsList,
                                                 LocalDate dateFrom, LocalDate dateTo) {
        this.exportDeactivatedPaymentsList = exportDeactivatedPaymentsList;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }
}
