package com.korotkov.creditCRM.model.payments;

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
public class ExportPaymentsWithPaidFeesReportObject {
    List<ExportPaymentWithPaidFees> exportPaymentWithPaidFeesList;
    List<ExportPaymentsWithFeesGroupedByDate> exportPaymentsWithFeesGroupedByDateList;
    LocalDate dateFrom;
    LocalDate dateTo;
    LocalDateTime dateWasCreated;

    public ExportPaymentsWithPaidFeesReportObject(List<ExportPaymentWithPaidFees> exportPaymentWithPaidFeesList,
                                                  List<ExportPaymentsWithFeesGroupedByDate> exportPaymentsWithFeesGroupedByDateList,
                                                  LocalDate dateFrom, LocalDate dateTo) {
        this.exportPaymentWithPaidFeesList = exportPaymentWithPaidFeesList;
        this.exportPaymentsWithFeesGroupedByDateList = exportPaymentsWithFeesGroupedByDateList;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dateWasCreated = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }
}
