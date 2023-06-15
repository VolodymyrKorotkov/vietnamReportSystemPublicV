package com.korotkov.creditCRM.model.clients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExportClientDocumentForCheckingInsuranceReportObject {
    List<ClientDocumentAndName> clientDocumentAndNameList;

    public ExportClientDocumentForCheckingInsuranceReportObject(List<ClientDocumentAndName> clientDocumentAndNameList) {
        this.clientDocumentAndNameList = clientDocumentAndNameList;
    }
}
