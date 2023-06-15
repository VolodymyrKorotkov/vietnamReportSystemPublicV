package com.korotkov.creditCRM.model.reportsCreditConveyor;

import com.korotkov.creditCRM.model.mainDailyReport.ExportApplicationsInfoDate;
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
public class ExportApplicationsInfoDateReportObject {
    private List<ExportApplicationsInfoDate> exportApplicationsInfoDateList;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime createdAt;
    private ExportApplicationsInfoDate total;

    public ExportApplicationsInfoDateReportObject(List<ExportApplicationsInfoDate> exportApplicationsInfoDateList,
                                                  LocalDate dateFrom, LocalDate dateTo) {
        this.exportApplicationsInfoDateList = exportApplicationsInfoDateList;
        this.total = createTotal(exportApplicationsInfoDateList);
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }

    private ExportApplicationsInfoDate createTotal(List<ExportApplicationsInfoDate> exportApplicationsInfoDateList) {
        ExportApplicationsInfoDate total = new ExportApplicationsInfoDate();
        total.setCountAppsTotal(0L);
        total.setCountAppsInProgress(0L);
        total.setCountAppsCanceledBySystem(0L);
        total.setCountAppsCanceledByClient(0L);
        total.setCountAppsRejected(0L);
        total.setCountAppsIssued(0L);
        total.setCountAppsWasApproved(0L);
        total.setCountAppsTotalNew(0L);
        total.setCountAppsInProgressNew(0L);
        total.setCountAppsCanceledBySystemNew(0L);
        total.setCountAppsCanceledByClientNew(0L);
        total.setCountAppsRejectedNew(0L);
        total.setCountAppsIssuedNew(0L);
        total.setCountAppsWasApprovedNew(0L);
        total.setCountAppsTotalRepeat(0L);
        total.setCountAppsInProgressRepeat(0L);
        total.setCountAppsCanceledBySystemRepeat(0L);
        total.setCountAppsCanceledByClientRepeat(0L);
        total.setCountAppsRejectedRepeat(0L);
        total.setCountAppsIssuedRepeat(0L);
        total.setCountAppsWasApprovedRepeat(0L);

        for (ExportApplicationsInfoDate item : exportApplicationsInfoDateList) {
            total.setCountAppsTotal(total.getCountAppsTotal() + item.getCountAppsTotal());
            total.setCountAppsInProgress(total.getCountAppsInProgress() + item.getCountAppsInProgress());
            total.setCountAppsCanceledBySystem(total.getCountAppsCanceledBySystem() + item.getCountAppsCanceledBySystem());
            total.setCountAppsCanceledByClient(total.getCountAppsCanceledByClient() + item.getCountAppsCanceledByClient());
            total.setCountAppsRejected(total.getCountAppsRejected() + item.getCountAppsRejected());
            total.setCountAppsIssued(total.getCountAppsIssued() + item.getCountAppsIssued());
            total.setCountAppsWasApproved(total.getCountAppsWasApproved() + item.getCountAppsWasApproved());
            total.setCountAppsTotalNew(total.getCountAppsTotalNew() + item.getCountAppsTotalNew());
            total.setCountAppsInProgressNew(total.getCountAppsInProgressNew() + item.getCountAppsInProgressNew());
            total.setCountAppsCanceledBySystemNew(total.getCountAppsCanceledBySystemNew() + item.getCountAppsCanceledBySystemNew());
            total.setCountAppsCanceledByClientNew(total.getCountAppsCanceledByClientNew() + item.getCountAppsCanceledByClientNew());
            total.setCountAppsRejectedNew(total.getCountAppsRejectedNew() + item.getCountAppsRejectedNew());
            total.setCountAppsIssuedNew(total.getCountAppsIssuedNew() + item.getCountAppsIssuedNew());
            total.setCountAppsWasApprovedNew(total.getCountAppsWasApprovedNew() + item.getCountAppsWasApprovedNew());
            total.setCountAppsTotalRepeat(total.getCountAppsTotalRepeat() + item.getCountAppsTotalRepeat());
            total.setCountAppsInProgressRepeat(total.getCountAppsInProgressRepeat() + item.getCountAppsInProgressRepeat());
            total.setCountAppsCanceledBySystemRepeat(total.getCountAppsCanceledBySystemRepeat() + item.getCountAppsCanceledBySystemRepeat());
            total.setCountAppsCanceledByClientRepeat(total.getCountAppsCanceledByClientRepeat() + item.getCountAppsCanceledByClientRepeat());
            total.setCountAppsRejectedRepeat(total.getCountAppsRejectedRepeat() + item.getCountAppsRejectedRepeat());
            total.setCountAppsIssuedRepeat(total.getCountAppsIssuedRepeat() + item.getCountAppsIssuedRepeat());
            total.setCountAppsWasApprovedRepeat(total.getCountAppsWasApprovedRepeat() + item.getCountAppsWasApprovedRepeat());
        }
        return total;
    }
}
