package com.korotkov.creditCRM.model.clients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SendingForCheckingInsurance {
    Long clientId;
    LocalDateTime createdAt;
    String createdByEmail;
}
