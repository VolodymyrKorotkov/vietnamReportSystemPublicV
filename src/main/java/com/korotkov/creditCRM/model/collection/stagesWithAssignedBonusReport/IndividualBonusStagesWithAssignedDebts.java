package com.korotkov.creditCRM.model.collection.stagesWithAssignedBonusReport;


import com.korotkov.creditCRM.model.BackUserAccountShortly;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Comparator;

@Getter
@Setter
@NoArgsConstructor
public class IndividualBonusStagesWithAssignedDebts {
    LocalDate dateFrom;
    LocalDate dateTo;
    int positionOfPerson;
    int countEmployees;
    StagesWithAssignedUserResult stagesWithAssignedUserResultOfPerson;
    StagesWithAssignedUserResult stagesWithAssignedUserResultOfTheBest;

    public IndividualBonusStagesWithAssignedDebts (StagesWithAssignedBonusReportObject stagesWithAssignedBonusReportObject,
                                                   BackUserAccountShortly userAccountShortly) {
        this.dateFrom = stagesWithAssignedBonusReportObject.getDateFrom();
        this.dateTo = stagesWithAssignedBonusReportObject.getDateTo();

        stagesWithAssignedBonusReportObject.getStagesWithAssignedUserResultList()
                .sort(Comparator.comparing(StagesWithAssignedUserResult::getTotalPaymentAmount).reversed());

        stagesWithAssignedBonusReportObject.getStagesWithAssignedUserResultList()
                .sort(Comparator.comparing(StagesWithAssignedUserResult::getBonusAmountTotal).reversed());


        int countEmployees = 0;

        for (int a = stagesWithAssignedBonusReportObject.getStagesWithAssignedUserResultList().size() - 1; a >= 0; a--) {
            if (stagesWithAssignedBonusReportObject.getStagesWithAssignedUserResultList().get(a).getUserRole()
                    .equals(userAccountShortly.getRoleName())) {
                countEmployees++;
                this.stagesWithAssignedUserResultOfTheBest =
                        stagesWithAssignedBonusReportObject.getStagesWithAssignedUserResultList().get(a);
            }
        }

        this.countEmployees = countEmployees;

        int position = 0;

        for (int a = 0; a < stagesWithAssignedBonusReportObject.getStagesWithAssignedUserResultList().size(); a++) {
            if (stagesWithAssignedBonusReportObject.getStagesWithAssignedUserResultList().get(a).getUserRole()
                    .equals(userAccountShortly.getRoleName())) {
                position++;
                if (stagesWithAssignedBonusReportObject.getStagesWithAssignedUserResultList().get(a).getUserEmail()
                        .equals(userAccountShortly.getUserEmail())) {
                    this.stagesWithAssignedUserResultOfPerson =
                            stagesWithAssignedBonusReportObject.getStagesWithAssignedUserResultList().get(a);
                    this.positionOfPerson = position;
                    break;
                }
            }
        }
    }
}
