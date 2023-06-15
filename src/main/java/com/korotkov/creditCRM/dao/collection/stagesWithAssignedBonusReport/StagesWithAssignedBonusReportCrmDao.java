package com.korotkov.creditCRM.dao.collection.stagesWithAssignedBonusReport;

import com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport.CollectionPaymentWithAssigned;

import java.time.LocalDate;
import java.util.List;

public interface StagesWithAssignedBonusReportCrmDao {
    List<CollectionPaymentWithAssigned> getCollectionPaymentWithAssignedList(LocalDate dateFrom,
                                                                             LocalDate dateTo);
}
