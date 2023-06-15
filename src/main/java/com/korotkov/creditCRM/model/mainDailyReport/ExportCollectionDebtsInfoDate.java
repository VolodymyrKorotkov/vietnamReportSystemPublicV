package com.korotkov.creditCRM.model.mainDailyReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ExportCollectionDebtsInfoDate {
    LocalDate date;
    Double totalIssuedAmount;
    Double principalRepaid;
    Double percentRepaid;
    Double currentDebtPrincipal;
    Double gracePeriodAmount;
    Double expiredAmountTo30Days;
    Double expiredAmountFrom31To60Days;
    Double expiredAmountFrom61To90Days;
    Double expiredAmountFrom91To120Days;
    Double expiredAmountFrom121To180Days;
    Double expiredAmountFrom181Days;
    Double expiredAmountTotal;
    Double totalIssuedAmountNew;
    Double principalRepaidNew;
    Double percentRepaidNew;
    Double currentDebtPrincipalNew;
    Double gracePeriodAmountNew;
    Double expiredAmountTo30DaysNew;
    Double expiredAmountFrom31To60DaysNew;
    Double expiredAmountFrom61To90DaysNew;
    Double expiredAmountFrom91To120DaysNew;
    Double expiredAmountFrom121To180DaysNew;
    Double expiredAmountFrom181DaysNew;
    Double expiredAmountTotalNew;
    Double totalIssuedAmountRepeat;
    Double principalRepaidRepeat;
    Double percentRepaidRepeat;
    Double currentDebtPrincipalRepeat;
    Double gracePeriodAmountRepeat;
    Double expiredAmountTo30DaysRepeat;
    Double expiredAmountFrom31To60DaysRepeat;
    Double expiredAmountFrom61To90DaysRepeat;
    Double expiredAmountFrom91To120DaysRepeat;
    Double expiredAmountFrom121To180DaysRepeat;
    Double expiredAmountFrom181DaysRepeat;
    Double expiredAmountTotalRepeat;



}
