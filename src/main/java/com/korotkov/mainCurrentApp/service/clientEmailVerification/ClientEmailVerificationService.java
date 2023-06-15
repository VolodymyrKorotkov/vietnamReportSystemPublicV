package com.korotkov.mainCurrentApp.service.clientEmailVerification;

import com.korotkov.mainCurrentApp.model.ClientEmailVerification;

public interface ClientEmailVerificationService {
    void create(ClientEmailVerification clientEmailVerification);
    void update(ClientEmailVerification clientEmailVerification);
    ClientEmailVerification getById(Long id);
    ClientEmailVerification getByClientId(Long clientId);
    String createNewAndGetCode(Long clientId, String email);
    void delete(ClientEmailVerification clientEmailVerification);
    boolean needAddAttemptWithConfirmEmail(Long clientId);
}
