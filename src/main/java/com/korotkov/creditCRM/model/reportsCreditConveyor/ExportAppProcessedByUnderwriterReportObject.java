package com.korotkov.creditCRM.model.reportsCreditConveyor;

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
public class ExportAppProcessedByUnderwriterReportObject {
    private List<ExportAppProcessedByUnderwriter> exportAppProcessedByUnderwriterList;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime createdAt;

    public ExportAppProcessedByUnderwriterReportObject(List<ExportAppProcessedByUnderwriter> exportAppProcessedByUnderwriterList,
                                                       LocalDate dateFrom, LocalDate dateTo) {
        this.exportAppProcessedByUnderwriterList = exportAppProcessedByUnderwriterList;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }

}
