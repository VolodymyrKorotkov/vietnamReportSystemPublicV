package com.korotkov.creditCRM.model.reportsCreditConveyor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExportAppProcessedByUnderwriter {
    private LocalDateTime appDate;
    private LocalDateTime decisionAt;
    private String underwriter;
    private String clientType;
    private Long appId;
    private String status;
    private String rejectedBy;
    private String rejectReasonVn;
    private String rejectReasonEn;
    private LocalDateTime sentToRevisionAt;
    private String revisionComment;
}
