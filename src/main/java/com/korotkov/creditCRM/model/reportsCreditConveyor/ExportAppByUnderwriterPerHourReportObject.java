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
public class ExportAppByUnderwriterPerHourReportObject {
    private List<ExportAppByUnderwriterPerHour> exportAppByUnderwriterPerHourList;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime createdAt;
    private ExportAppByUnderwriterPerHour total;

    public ExportAppByUnderwriterPerHourReportObject(List<ExportAppByUnderwriterPerHour> exportAppByUnderwriterPerHourList,
                                                     LocalDate dateFrom, LocalDate dateTo) {
        this.exportAppByUnderwriterPerHourList = exportAppByUnderwriterPerHourList;
        this.total = createTotal(exportAppByUnderwriterPerHourList);
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }

    private ExportAppByUnderwriterPerHour createTotal(List<ExportAppByUnderwriterPerHour> exportAppByUnderwriterPerHourList) {
        ExportAppByUnderwriterPerHour total = new ExportAppByUnderwriterPerHour();
        total.setUnderwriter("Total");
        total.setApp0Hour(0L);
        total.setApp1Hour(0L);
        total.setApp2Hour(0L);
        total.setApp3Hour(0L);
        total.setApp4Hour(0L);
        total.setApp5Hour(0L);
        total.setApp6Hour(0L);
        total.setApp7Hour(0L);
        total.setApp8Hour(0L);
        total.setApp9Hour(0L);
        total.setApp10Hour(0L);
        total.setApp11Hour(0L);
        total.setApp12Hour(0L);
        total.setApp13Hour(0L);
        total.setApp14Hour(0L);
        total.setApp15Hour(0L);
        total.setApp16Hour(0L);
        total.setApp17Hour(0L);
        total.setApp18Hour(0L);
        total.setApp19Hour(0L);
        total.setApp20Hour(0L);
        total.setApp21Hour(0L);
        total.setApp22Hour(0L);
        total.setApp23Hour(0L);
        total.setAppCount(0L);
        for(ExportAppByUnderwriterPerHour i : exportAppByUnderwriterPerHourList) {
            total.setApp0Hour(total.getApp0Hour() + i.getApp0Hour());
            total.setApp1Hour(total.getApp1Hour() + i.getApp1Hour());
            total.setApp2Hour(total.getApp2Hour() + i.getApp2Hour());
            total.setApp3Hour(total.getApp3Hour() + i.getApp3Hour());
            total.setApp4Hour(total.getApp4Hour() + i.getApp4Hour());
            total.setApp5Hour(total.getApp5Hour() + i.getApp5Hour());
            total.setApp6Hour(total.getApp6Hour() + i.getApp6Hour());
            total.setApp7Hour(total.getApp7Hour() + i.getApp7Hour());
            total.setApp8Hour(total.getApp8Hour() + i.getApp8Hour());
            total.setApp9Hour(total.getApp9Hour() + i.getApp9Hour());
            total.setApp10Hour(total.getApp10Hour() + i.getApp10Hour());
            total.setApp11Hour(total.getApp11Hour() + i.getApp11Hour());
            total.setApp12Hour(total.getApp12Hour() + i.getApp12Hour());
            total.setApp13Hour(total.getApp13Hour() + i.getApp13Hour());
            total.setApp14Hour(total.getApp14Hour() + i.getApp14Hour());
            total.setApp15Hour(total.getApp15Hour() + i.getApp15Hour());
            total.setApp16Hour(total.getApp16Hour() + i.getApp16Hour());
            total.setApp17Hour(total.getApp17Hour() + i.getApp17Hour());
            total.setApp18Hour(total.getApp18Hour() + i.getApp18Hour());
            total.setApp19Hour(total.getApp19Hour() + i.getApp19Hour());
            total.setApp20Hour(total.getApp20Hour() + i.getApp20Hour());
            total.setApp21Hour(total.getApp21Hour() + i.getApp21Hour());
            total.setApp22Hour(total.getApp22Hour() + i.getApp22Hour());
            total.setApp23Hour(total.getApp23Hour() + i.getApp23Hour());
            total.setAppCount(total.getAppCount() + i.getAppCount());
        }
        return total;
    }
}
