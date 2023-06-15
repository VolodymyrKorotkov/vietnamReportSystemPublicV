package com.korotkov.mainCurrentApp.model.helpedObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ClientPhoneInsertCRM {
    LocalDateTime createdAt;
    LocalDateTime lastModifiedAt;
    Long clientId;
    String phone;
    String relationshipName;
    String relationshipType;
    String source;
    String comment;
}
