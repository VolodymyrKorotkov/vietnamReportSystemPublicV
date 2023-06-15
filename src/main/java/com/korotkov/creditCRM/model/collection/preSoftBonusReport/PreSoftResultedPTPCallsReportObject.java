package com.korotkov.creditCRM.model.collection.preSoftBonusReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Getter
@Setter
@NoArgsConstructor
public class PreSoftResultedPTPCallsReportObject {
    List<PreSoftActivePromissedPaymentsForAgent> resultedPTPCallList;
    LocalDateTime createdAt;

    public PreSoftResultedPTPCallsReportObject(List<PreSoftActivePromissedPaymentsForAgent> resultedPTPCallList) {
        this.resultedPTPCallList = resultedPTPCallList;
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }
}
