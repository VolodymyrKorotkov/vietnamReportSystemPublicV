package com.korotkov.mainCurrentApp.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoleEnum implements GrantedAuthority {
    SUPER_ADMIN,
    ADMIN,
    ACCOUNTANT,
    COLLECTION_AGENT,
    COLLECTION_SUPERVISOR,
    CC_AGENT,
    CC_SUPERVISOR,
    UNDERWRITER_AGENT,
    UNDERWRITER_SUPERVISOR,
    MARKETER,
    ANALYST,
    T_T,
    ANALYST_SUPERVISOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
