package com.korotkov.creditCRM.model.reportsCreditConveyor;

import com.korotkov.creditCRM.model.clients.exportForUploadingNewLeads.ExportPassiveClients;
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
public class ExportPassiveClientsForSMSReportObject {
    private List<ExportPassiveClients> passiveClientsList;
    private LocalDateTime createdAt;

    public ExportPassiveClientsForSMSReportObject(List<ExportPassiveClients> passiveClientsList) {
        this.passiveClientsList = passiveClientsList;
        this.createdAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
    }
}
