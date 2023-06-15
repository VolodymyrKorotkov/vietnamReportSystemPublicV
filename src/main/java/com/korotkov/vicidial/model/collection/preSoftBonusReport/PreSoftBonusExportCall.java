package com.korotkov.vicidial.model.collection.preSoftBonusReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PreSoftBonusExportCall {
    LocalDateTime callDate;
    String userEmail;
    Integer talkSec;
    String callStatus;
    String callType;
    String phoneNumber;
}
