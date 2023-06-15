package com.korotkov.creditCRM.model.clients.exportForUploadingNewLeads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExportPassiveClients {
    String phone;
    String fullName;
    Integer passiveDays;
}
