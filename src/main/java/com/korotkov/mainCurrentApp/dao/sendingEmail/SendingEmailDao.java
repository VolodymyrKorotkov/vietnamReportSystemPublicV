package com.korotkov.mainCurrentApp.dao.sendingEmail;

import com.korotkov.mainCurrentApp.model.SendingEmail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SendingEmailDao {
    void create(SendingEmail sendingEmail);
    void update(SendingEmail sendingEmail);
    SendingEmail getById(Long id);
    Long getCountAllSendingEmails();
    List<SendingEmail> getAllSendingEmails(int page);
    Long getIdLastCreatedSendingEmail(LocalDateTime createdAt, String title, boolean auto);
    List<SendingEmail> getSendingEmailsForDates(LocalDate dateFrom, LocalDate dateTo, int page);
    Long getCountSendingEmailsForDates(LocalDate dateFrom, LocalDate dateTo);
    List<SendingEmail> getSendingEmailsForDateFrom(LocalDate dateFrom, int page);
    Long getCountSendingEmailsForDateFrom(LocalDate dateFrom);
    List<SendingEmail> getSendingEmailsForDateTo(LocalDate dateTo, int page);
    Long getCountSendingEmailsForDateTo(LocalDate dateTo);
    SendingEmail getOnePlannedSendingEmailForStart();
    void updateStatusAsCanceledForNotCompletedSendingEmail(LocalDate dateBefore);
}
