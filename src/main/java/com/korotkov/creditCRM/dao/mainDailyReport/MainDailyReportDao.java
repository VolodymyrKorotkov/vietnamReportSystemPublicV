package com.korotkov.creditCRM.dao.mainDailyReport;

import com.korotkov.creditCRM.model.mainDailyReport.*;

import java.time.LocalDate;
import java.util.List;

public interface MainDailyReportDao {
    List<ExportApplicationsInfoDate> getExportApplicationsInfoDateList(LocalDate dateFrom,
                                                                       LocalDate dateTo);
    List<ExportCollectionDebtsInfoDate> getExportCollectionDebtsInfoDateList(LocalDate dateFrom,
                                                                             LocalDate dateTo);
    List<ExportLoansInfoDate> getExportLoansInfoDateList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportPaymentsInfoDate> getExportPaymentsInfoDateList(LocalDate dateFrom, LocalDate dateTo);
    List<ExportProlongationsInfoDate> getExportProlongationsInfoDateList(LocalDate dateFrom, LocalDate dateTo);
}
