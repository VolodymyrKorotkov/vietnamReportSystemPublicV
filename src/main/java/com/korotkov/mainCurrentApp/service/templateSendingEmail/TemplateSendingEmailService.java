package com.korotkov.mainCurrentApp.service.templateSendingEmail;

import com.korotkov.mainCurrentApp.model.TemplateSendingEmail;
import com.korotkov.mainCurrentApp.model.UserAccount;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TemplateSendingEmailService {
    Long createAndGetId(TemplateSendingEmail templateSendingEmail, UserAccount userAccount);
    boolean update(TemplateSendingEmail templateSendingEmail, UserAccount userAccount);
    TemplateSendingEmail getById(Long id);
    Long getCountAllTemplatesSendingEmails();
    List<TemplateSendingEmail> getAllTemplatesSendingEmails(int page);
    Long getCountTemplatesSendingEmailsForDates(LocalDate dateFrom, LocalDate dateTo);
    List<TemplateSendingEmail> getTemplatesSendingEmailsForDates(LocalDate dateFrom,
                                                                 LocalDate dateTo,
                                                                 int page);
    Long getCountTemplatesSendingEmailsForDateFrom(LocalDate dateFrom);
    List<TemplateSendingEmail> getTemplatesSendingEmailsForDateFrom(LocalDate dateFrom,
                                                                    int page);
    Long getCountTemplatesSendingEmailsForDateTo(LocalDate dateTo);
    List<TemplateSendingEmail> getTemplatesSendingEmailsForDateTo(LocalDate dateTo,
                                                                  int page);
    boolean delete(Long id);
}
