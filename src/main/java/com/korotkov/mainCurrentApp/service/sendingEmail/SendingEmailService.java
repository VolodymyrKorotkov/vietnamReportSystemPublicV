package com.korotkov.mainCurrentApp.service.sendingEmail;

import com.korotkov.mainCurrentApp.model.SendingEmail;
import com.korotkov.mainCurrentApp.model.TemplateSendingEmail;
import com.korotkov.mainCurrentApp.model.UserAccount;

import java.time.LocalDate;
import java.util.List;

public interface SendingEmailService {
    Long createAutoAsStartedAndGetId(String title, Integer countEmailsTotal);
    void updateAsCompleted(Long sendingEmailId, int countEmailsSent);
    Long getCountAllSendingEmails();
    List<SendingEmail> getAllSendingEmails(int page);
    List<SendingEmail> getSendingEmailsForDates(LocalDate dateFrom, LocalDate dateTo, int page);
    Long getCountSendingEmailsForDates(LocalDate dateFrom, LocalDate dateTo);
    List<SendingEmail> getSendingEmailsForDateFrom(LocalDate dateFrom, int page);
    Long getCountSendingEmailsForDateFrom(LocalDate dateFrom);
    List<SendingEmail> getSendingEmailsForDateTo(LocalDate dateTo, int page);
    Long getCountSendingEmailsForDateTo(LocalDate dateTo);
    SendingEmail getById(Long id);
    Long createManualAsPlannedAndGetId(TemplateSendingEmail templateSendingEmail,
                                       SendingEmail sendingEmail,
                                       UserAccount userAccount);
    void updateAsStarted(SendingEmail sendingEmail, int countEmailsTotal);
    SendingEmail getOnePlannedSendingEmailForStart();
    void updateStatusAsCanceledForNotCompletedSendingEmail(LocalDate dateBefore);
}
