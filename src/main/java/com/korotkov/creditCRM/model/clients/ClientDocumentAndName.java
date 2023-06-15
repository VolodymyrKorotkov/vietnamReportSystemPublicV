package com.korotkov.creditCRM.model.clients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ClientDocumentAndName {
    String documentNumber;
    String fullName;
    LocalDate birthDate;
}
