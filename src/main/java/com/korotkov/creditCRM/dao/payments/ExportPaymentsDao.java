package com.korotkov.creditCRM.dao.payments;

import com.korotkov.creditCRM.model.payments.ExportPaymentWithPaidFees;

import java.time.LocalDate;
import java.util.List;

public interface ExportPaymentsDao {
    List<ExportPaymentWithPaidFees> getExportPaymentWithPaidList(LocalDate dateFrom, LocalDate dateTo);
}
