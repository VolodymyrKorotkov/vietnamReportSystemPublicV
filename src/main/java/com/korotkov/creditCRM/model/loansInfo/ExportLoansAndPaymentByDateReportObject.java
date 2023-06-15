package com.korotkov.creditCRM.model.loansInfo;

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
public class ExportLoansAndPaymentByDateReportObject {
    List<ExportLoansAndPaymentByDate> exportLoansAndPaymentByDateList;
    LocalDate dateFrom;
    LocalDate dateTo;
    LocalDateTime createdAt;
    ExportLoansAndPaymentByDate total;

    public ExportLoansAndPaymentByDateReportObject(List<ExportLoansAndPaymentByDate> exportLoansAndPaymentByDateList,
                                                   LocalDate dateFrom, LocalDate dateTo) {
        this.exportLoansAndPaymentByDateList = exportLoansAndPaymentByDateList;
        this.total = createTotal(exportLoansAndPaymentByDateList);
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }

    private ExportLoansAndPaymentByDate createTotal(List<ExportLoansAndPaymentByDate> exportLoansAndPaymentByDateList) {
        ExportLoansAndPaymentByDate total = new ExportLoansAndPaymentByDate();
        total.setAmountLoans((double) 0);
        total.setAmountPayments((double) 0);
        for (ExportLoansAndPaymentByDate i : exportLoansAndPaymentByDateList) {
            total.setAmountPayments(total.getAmountPayments() + i.getAmountPayments());
            total.setAmountLoans(total.getAmountLoans() + i.getAmountLoans());
        }
        return total;
    }
}
