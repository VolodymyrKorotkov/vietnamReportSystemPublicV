package com.korotkov.creditCRM.model.clients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientNameAndEmail {
    private String email;
    private String name;
}
