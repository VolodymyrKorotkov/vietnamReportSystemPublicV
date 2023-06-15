package com.korotkov.creditCRM.model.collection.preSoftBonusReport;

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
public class PreSoftBonusReportObject {
    List<PreSoftBackUserResult> preSoftBackUserResultList;
    LocalDate dateFrom;
    LocalDate dateTo;
    LocalDateTime dateWasCreated;

    public PreSoftBonusReportObject(List<PreSoftBackUserResult> preSoftBackUserResultList,
                                    LocalDate dateFrom, LocalDate dateTo) {
        this.preSoftBackUserResultList = preSoftBackUserResultList;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dateWasCreated = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }
}
