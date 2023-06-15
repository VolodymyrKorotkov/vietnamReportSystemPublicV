package com.korotkov.creditCRM.service.clientCRM;

import com.korotkov.creditCRM.dao.gettingClients.ClientCRMDao;
import com.korotkov.creditCRM.model.clients.*;
import com.korotkov.creditCRM.model.clients.exportForUploadingNewLeads.ExportPassiveClients;
import com.korotkov.mainCurrentApp.model.helpedObjects.ClientAddressInsertCRM;
import com.korotkov.mainCurrentApp.model.helpedObjects.ClientPhoneInsertCRM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientCRMServiceImpl implements ClientCRMService {
    ClientCRMDao clientCRMDao;

    @Autowired
    public void setClientCRMDao(ClientCRMDao clientCRMDao) {
        this.clientCRMDao = clientCRMDao;
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientNameAndEmail> getRegisteredClientsWithoutApplications(List<LocalDate> dateRegisteredList) {
        return clientCRMDao.getRegisteredClientsWithoutApplications(dateRegisteredList);
    }


    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientNameAndEmail> getPassiveClientsWithoutLoans(List<LocalDate> dateLastFinishedLoanList) {
        return clientCRMDao.getPassiveClientsWithoutLoans(dateLastFinishedLoanList);
    }


    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientNameAndEmail> getClientsWithExpiredLoansForNow(List<Integer> daysOverdueList) {
        return clientCRMDao.getClientsWithExpiredLoansForNow(daysOverdueList);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientNameAndEmail> getClientsWithSomeLastApplications(List<LocalDate> dateAppChangedStatusList,
                                                                String applicationStatus) {
        return clientCRMDao.getClientsWithSomeLastApplications(dateAppChangedStatusList, applicationStatus);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public ClientEmailConfirmedCRM getClientEmailConfirmedById(Long clientId) {
        return clientCRMDao.getClientEmailConfirmedById(clientId);
    }


    @Override
    @Transactional("transactionManagerCRM")
    public void setEmailConfirmedToTrue(Long clientId) {
        clientCRMDao.setEmailConfirmedToTrue(clientId);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public void setEmailConfirmedToTrueAndAddAttempt(Long clientId) {
        clientCRMDao.setEmailConfirmedToTrueAndAddAttempt(clientId);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientEmailConfirmedCRM> getClientEmailConfirmedCRMList(List<LocalDate> dateRegisteredList) {
        return clientCRMDao.getClientEmailConfirmedCRMList(dateRegisteredList);
    }


    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientNameAndEmail> getClientsWithBirthDate(LocalDate birthDate) {
        return clientCRMDao.getClientsWithBirthDate(birthDate);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientNameAndEmail> getClientsWithActiveLoans() {
        return clientCRMDao.getClientsWithActiveLoans();
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientNameAndEmail> getPassiveClientsWithoutLoans(LocalDate dateFrom, LocalDate dateTo) {
        return clientCRMDao.getPassiveClientsWithoutLoans(dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientNameAndEmail> getClientsWithExpiredLoansForNow(Integer daysFrom, Integer daysTo) {
        return clientCRMDao.getClientsWithExpiredLoansForNow(daysFrom, daysTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientNameAndEmail> getRegisteredClientsWithoutApplications(LocalDate dateFrom, LocalDate dateTo) {
        return clientCRMDao.getRegisteredClientsWithoutApplications(dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientNameAndEmailRemindPayment> getClientsForRemindPayment(List<LocalDate> maturityDateList) {
        return clientCRMDao.getClientsForRemindPayment(maturityDateList);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientNameAndEmailGracePeriod> getClientsForRemindPaymentGracePeriod() {
        return clientCRMDao.getClientsForRemindPaymentGracePeriod();
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientNameAndEmailExpiredLoan> getClientsWithExpiredLoansMoreInfo(List<Integer> overdueDays) {
        return clientCRMDao.getClientsWithExpiredLoansMoreInfo(overdueDays);
    }


    @Override
    @Transactional("transactionManagerCRM")
    public List<String> getClientContactsByClientId(Long clientId) {
        return clientCRMDao.getClientContactsByClientId(clientId);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<String> getClientPhonesByClientId(Long clientId) {
        return clientCRMDao.getClientPhonesByClientId(clientId);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<Long> getClientIdsByDocumentNumber(String documentNumber) {
        return clientCRMDao.getClientIdsByDocumentNumber(documentNumber);
    }


    @Override
    @Transactional("transactionManagerCRM")
    public boolean existClientContact(String contact, Long clientId) {
        return clientCRMDao.existClientContact(contact, clientId);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public boolean existClientPhone(String phone, Long clientId) {
        return clientCRMDao.existClientPhone(phone, clientId);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public void createClientPhone(ClientPhoneInsertCRM phoneInsertCRM) {
        clientCRMDao.createClientPhone(phoneInsertCRM);
    }


    @Override
    @Transactional("transactionManagerCRM")
    public void createClientAddress(ClientAddressInsertCRM addressInsertCRM) {
        clientCRMDao.createClientAddress(addressInsertCRM);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public boolean existClientAddressInStreet(String fullAddress, Long clientId) {
        return clientCRMDao.existClientAddressInStreet(fullAddress, clientId);
    }

    @Override
    @Async("addingClientPhones")
    @Transactional("transactionManagerCRM")
    public void createSendingForCheckingInsurance(List<String> documentNumberList, String createdByEmail) {
        clientCRMDao.createSendingForCheckingInsurance(documentNumberList, createdByEmail);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ClientDocumentAndName> getClientDocumentForCheckInsurance() {
        return clientCRMDao.getClientDocumentForCheckInsurance();
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ExportPassiveClients> getPassiveClientsListForVicidial(List<LocalDate> dateLastFinishedLoanList) {
        return clientCRMDao.getPassiveClientsListForVicidial(dateLastFinishedLoanList);
    }



    @Override
    @Transactional("transactionManagerCRM")
    public List<ExportClientsWithExpiredForVicidial> getExpiredDebtsNotOwnNumbersForVicidial(List<Integer> overdueDays) {
        return clientCRMDao.getExpiredDebtsNotOwnNumbersForVicidial(overdueDays);
    }

    @Override
    @Transactional("transactionManagerCRM")
    public List<ExportClientsWithExpiredForVicidial> getExpiredDebtsOwnNumbersForVicidial(List<Integer> overdueDays) {
        return clientCRMDao.getExpiredDebtsOwnNumbersForVicidial(overdueDays);
    }
}
