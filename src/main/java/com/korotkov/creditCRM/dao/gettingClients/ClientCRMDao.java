package com.korotkov.creditCRM.dao.gettingClients;

import com.korotkov.creditCRM.model.clients.*;
import com.korotkov.creditCRM.model.clients.exportForUploadingNewLeads.ExportPassiveClients;
import com.korotkov.mainCurrentApp.model.helpedObjects.ClientAddressInsertCRM;
import com.korotkov.mainCurrentApp.model.helpedObjects.ClientPhoneInsertCRM;

import java.time.LocalDate;
import java.util.List;

public interface ClientCRMDao {
    List<ClientNameAndEmail> getRegisteredClientsWithoutApplications(List<LocalDate> dateRegisteredList);
    List<ClientNameAndEmail> getPassiveClientsWithoutLoans(List<LocalDate> dateLastFinishedLoanList);
    List<ClientNameAndEmail> getClientsWithExpiredLoansForNow(List<Integer> daysOverdueList);
    List<ClientNameAndEmail> getClientsWithSomeLastApplications(List<LocalDate> dateAppChangedStatusList,
                                                                String applicationStatus);
    ClientEmailConfirmedCRM getClientEmailConfirmedById(Long clientId);
    void setEmailConfirmedToTrue(Long clientId);
    void setEmailConfirmedToTrueAndAddAttempt(Long clientId);
    List<ClientEmailConfirmedCRM> getClientEmailConfirmedCRMList(List<LocalDate> dateRegisteredList);
    List<ClientNameAndEmail> getClientsWithBirthDate(LocalDate birthDate);
    List<ClientNameAndEmail> getClientsWithActiveLoans();
    List<ClientNameAndEmail> getPassiveClientsWithoutLoans(LocalDate dateFrom, LocalDate dateTo);
    List<ClientNameAndEmail> getClientsWithExpiredLoansForNow(Integer daysFrom, Integer daysTo);
    List<ClientNameAndEmail> getRegisteredClientsWithoutApplications(LocalDate dateFrom, LocalDate dateTo);
    List<ClientNameAndEmailRemindPayment> getClientsForRemindPayment(List<LocalDate> maturityDateList);
    List<ClientNameAndEmailGracePeriod> getClientsForRemindPaymentGracePeriod();
    List<ClientNameAndEmailExpiredLoan> getClientsWithExpiredLoansMoreInfo(List<Integer> overdueDays);
    List<String> getClientContactsByClientId(Long clientId);
    List<String> getClientPhonesByClientId(Long clientId);
    List<Long> getClientIdsByDocumentNumber(String documentNumber);
    boolean existClientContact(String contact, Long clientId);
    boolean existClientPhone(String phone, Long clientId);
    void createClientPhone(ClientPhoneInsertCRM phoneInsertCRM);
    void createClientAddress(ClientAddressInsertCRM addressInsertCRM);
    boolean existClientAddressInStreet(String fullAddress, Long clientId);
    void createSendingForCheckingInsurance(List<String> documentNumberList, String createdByEmail);
    List<ClientDocumentAndName> getClientDocumentForCheckInsurance();
    List<ExportClientsWithExpiredForVicidial> getExpiredDebtsNotOwnNumbersForVicidial(List<Integer> overdueDays);
    List<ExportClientsWithExpiredForVicidial> getExpiredDebtsOwnNumbersForVicidial(List<Integer> overdueDays);
    List<ExportPassiveClients> getPassiveClientsListForVicidial(List<LocalDate> dateLastFinishedLoanList);
}
