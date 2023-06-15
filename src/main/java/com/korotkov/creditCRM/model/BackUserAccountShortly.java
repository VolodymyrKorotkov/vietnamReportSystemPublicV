package com.korotkov.creditCRM.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BackUserAccountShortly {
    Long userId;
    String userEmail;
    String userName;
    String roleName;
}
