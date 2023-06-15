package com.korotkov.creditCRM.dao.collection.preSoftBonusReport;

import com.korotkov.creditCRM.model.BackUserAccountShortly;
import com.korotkov.creditCRM.model.collection.preSoftBonusReport.ClientPhone;
import com.korotkov.creditCRM.model.collection.preSoftBonusReport.ClientWithIDAndDebtId;
import com.korotkov.creditCRM.model.collection.preSoftBonusReport.CollectionPayment;

import java.time.LocalDate;
import java.util.List;

public interface PreSoftBonusReportCrmDao {
    List<CollectionPayment> getCollectionPaymentList(LocalDate dateFrom, LocalDate dateTo);

    @SuppressWarnings("unchecked")
    List<ClientPhone> getClientPhoneListByPhonesWithOnlyActiveDebt(List<String> phones);

    List<ClientPhone> getClientPhoneList(List<Long> clientIdList);
    List<BackUserAccountShortly> getBackUserAccountShortlyList();
    List<ClientWithIDAndDebtId> getClientWithIDAndActiveDebtIds(List<Long> clientIdList);
}
