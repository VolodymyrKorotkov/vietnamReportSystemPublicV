package com.korotkov.mainCurrentApp.dao.templateSendingEmail;

import com.korotkov.mainCurrentApp.model.TemplateSendingEmail;
import com.korotkov.mainCurrentApp.model.UserAccount;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TemplateSendingEmailDao {
    void create(TemplateSendingEmail templateSendingEmail);
    void update(TemplateSendingEmail templateSendingEmail);
    void delete(TemplateSendingEmail templateSendingEmail);
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
    Long getIdLastCreatedTemplateSendingEmail(LocalDateTime createdAt, UserAccount createdBy, String title,
                                              String subjectEmail);
    Long getCountSendingEmailsForTemplate(TemplateSendingEmail templateSendingEmail);
}
