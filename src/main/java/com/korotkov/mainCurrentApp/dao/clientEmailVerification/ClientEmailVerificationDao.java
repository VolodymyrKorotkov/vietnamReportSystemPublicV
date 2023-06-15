package com.korotkov.mainCurrentApp.dao.clientEmailVerification;

import com.korotkov.mainCurrentApp.model.ClientEmailVerification;

public interface ClientEmailVerificationDao {
    void create(ClientEmailVerification clientEmailVerification);
    void update(ClientEmailVerification clientEmailVerification);
    ClientEmailVerification getById(Long id);
    ClientEmailVerification getByClientId(Long clientId);
    void delete(ClientEmailVerification clientEmailVerification);
    Long getCountSuccessfulConfirmedEmails(Long clientId);
}
