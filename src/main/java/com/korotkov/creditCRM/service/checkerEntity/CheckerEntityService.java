package com.korotkov.creditCRM.service.checkerEntity;

import com.korotkov.creditCRM.model.CheckerEntity;

import java.util.List;

public interface CheckerEntityService {
    List<CheckerEntity> getApplicationsOnLongDMS(int minutesAgoForCheck);
    Long getCountLoansWithExpiredAndWithoutActiveDebtCollection();
    Long getCountLoansWhichNotCalculated();
    CheckerEntity getLastCreatedApplication();
    CheckerEntity getLastIssuedLoan();
    CheckerEntity getLastRegisteredClient();
    CheckerEntity getLastPayment();
    List<CheckerEntity> getApplicationsWithPayoutError();
    Long getCountAppsOnStatusSentToUnderwriting();
}
