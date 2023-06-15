package com.korotkov.creditCRM.model.reportsCreditConveyor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExportAppsBySource {
    private String sourceName;
    private LocalDateTime dateTime;
    private Long applicationId;
    private String utmSource;
    private String utmCampaign;
    private String utmContent;
    private String utmMedium;
    private String utmReferrer;
    private String utmTerm;
    private String status;
    private String clientType;
}
