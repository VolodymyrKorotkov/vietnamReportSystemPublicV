package com.korotkov.creditCRM.model.collection.preSoftBonusReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

@Getter
@Setter
@NoArgsConstructor
public class IndividualBonusPreSoft {
    LocalDate dateFrom;
    LocalDate dateTo;
    PreSoftBackUserResult preSoftBackUserResultOfPerson;
    PreSoftBackUserResult preSoftBackUserResultTheBest;
    int positionOfPerson;
    int countEmployees;

    public IndividualBonusPreSoft(PreSoftBonusReportObject preSoftBonusReportObject,
                                  String personEmail) {
        this.dateFrom = preSoftBonusReportObject.getDateFrom();
        this.dateTo = preSoftBonusReportObject.getDateTo();

        preSoftBonusReportObject.getPreSoftBackUserResultList()
                .sort(Comparator.comparing(PreSoftBackUserResult::getTotalPaymentAmount).reversed());

        preSoftBonusReportObject.getPreSoftBackUserResultList()
                .sort(Comparator.comparing(PreSoftBackUserResult::getBonusAmountTotal).reversed());

        this.preSoftBackUserResultTheBest = preSoftBonusReportObject.getPreSoftBackUserResultList().get(0);

        countEmployees = preSoftBonusReportObject.getPreSoftBackUserResultList().size();

        int position = 0;

        for (int a = 0; a < preSoftBonusReportObject.getPreSoftBackUserResultList().size(); a++) {
            position++;
            if (preSoftBonusReportObject.getPreSoftBackUserResultList().get(a).getUserEmail()
                    .equals(personEmail)) {
                this.preSoftBackUserResultOfPerson =
                        preSoftBonusReportObject.getPreSoftBackUserResultList().get(a);
                this.positionOfPerson = position;
                break;
            }
        }

    }
}
