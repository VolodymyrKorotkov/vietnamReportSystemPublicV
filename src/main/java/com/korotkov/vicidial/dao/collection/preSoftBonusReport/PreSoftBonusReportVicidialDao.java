package com.korotkov.vicidial.dao.collection.preSoftBonusReport;

import com.korotkov.vicidial.model.collection.preSoftBonusReport.PreSoftBonusExportCall;

import java.time.LocalDate;
import java.util.List;

public interface PreSoftBonusReportVicidialDao {
    List<PreSoftBonusExportCall> getPreSoftBonusExportCallList(LocalDate dateFrom, LocalDate dateTo);
    List<PreSoftBonusExportCall> getPreSoftBonusExportCallListOnlyRemindingPromisedPayments(LocalDate dateFrom, LocalDate dateTo);
}
