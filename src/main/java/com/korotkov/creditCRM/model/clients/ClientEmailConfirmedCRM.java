package com.korotkov.creditCRM.model.clients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientEmailConfirmedCRM {
    private Long clientId;
    private String email;
    private boolean emailConfirmed;
    private String name;
}
