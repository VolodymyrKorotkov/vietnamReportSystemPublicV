package com.korotkov.mainCurrentApp.service.clientEmailVerification;

import com.korotkov.mainCurrentApp.dao.clientEmailVerification.ClientEmailVerificationDao;
import com.korotkov.mainCurrentApp.model.ClientEmailVerification;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class ClientEmailVerificationServiceImpl implements ClientEmailVerificationService {
    private ClientEmailVerificationDao clientEmailVerificationDao;

    @Autowired
    public void setClientEmailVerificationDao (ClientEmailVerificationDao clientEmailVerificationDao) {
        this.clientEmailVerificationDao = clientEmailVerificationDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public void create(ClientEmailVerification clientEmailVerification) {
        clientEmailVerificationDao.create(clientEmailVerification);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void update(ClientEmailVerification clientEmailVerification) {
        clientEmailVerificationDao.update(clientEmailVerification);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ClientEmailVerification getById(Long id) {
        return clientEmailVerificationDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public ClientEmailVerification getByClientId(Long clientId) {
        return clientEmailVerificationDao.getByClientId(clientId);
    }

    @Override
    @Transactional("transactionManagerMain")
    public String createNewAndGetCode(Long clientId, String email) {
        ClientEmailVerification clientEmailVerificationFromDB =
                clientEmailVerificationDao.getByClientId(clientId);
        String code = RandomStringUtils.randomAlphanumeric(50, 100);
        if (clientEmailVerificationFromDB == null) {
            ClientEmailVerification clientEmailVerificationNew = new ClientEmailVerification();
            clientEmailVerificationNew.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            clientEmailVerificationNew.setClientId(clientId);
            clientEmailVerificationNew.setEmail(email);
            clientEmailVerificationNew.setCode(code);
            clientEmailVerificationNew.setDone(false);
            clientEmailVerificationDao.create(clientEmailVerificationNew);
        } else {
            clientEmailVerificationFromDB.setEmail(email);
            clientEmailVerificationFromDB.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            clientEmailVerificationFromDB.setCode(code);
            clientEmailVerificationFromDB.setDone(false);
            clientEmailVerificationDao.update(clientEmailVerificationFromDB);
        }
        return code;
    }


    @Override
    @Transactional("transactionManagerMain")
    public void delete(ClientEmailVerification clientEmailVerification) {
        clientEmailVerificationDao.delete(clientEmailVerification);
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean needAddAttemptWithConfirmEmail(Long clientId) {
        return clientEmailVerificationDao.getCountSuccessfulConfirmedEmails(clientId).intValue() == 0;
    }

}
