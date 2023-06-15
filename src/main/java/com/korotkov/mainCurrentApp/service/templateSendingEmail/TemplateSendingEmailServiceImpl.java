package com.korotkov.mainCurrentApp.service.templateSendingEmail;

import com.korotkov.mainCurrentApp.dao.templateSendingEmail.TemplateSendingEmailDao;
import com.korotkov.mainCurrentApp.model.TemplateSendingEmail;
import com.korotkov.mainCurrentApp.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class TemplateSendingEmailServiceImpl implements TemplateSendingEmailService {
    private TemplateSendingEmailDao templateSendingEmailDao;

    @Autowired
    public void setTemplateSendingEmailDao(TemplateSendingEmailDao templateSendingEmailDao) {
        this.templateSendingEmailDao = templateSendingEmailDao;
    }


    @Override
    @Transactional("transactionManagerMain")
    public Long createAndGetId(TemplateSendingEmail templateSendingEmail, UserAccount userAccount) {
        TemplateSendingEmail templateSendingEmailNew = new TemplateSendingEmail();
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        templateSendingEmailNew.setCreatedAt(dateTime);
        templateSendingEmailNew.setLastModifiedAt(dateTime);
        templateSendingEmailNew.setCreatedBy(userAccount);
        templateSendingEmailNew.setLastModifiedBy(userAccount);
        templateSendingEmailNew.setTitle(templateSendingEmail.getTitle());
        templateSendingEmailNew.setDescription(templateSendingEmail.getDescription());
        templateSendingEmailNew.setSubjectEmail(templateSendingEmail.getSubjectEmail());
        templateSendingEmailNew.setBodyEmailText(templateSendingEmail.getBodyEmailText());
        templateSendingEmailNew.setBodyEmailTitle(templateSendingEmail.getBodyEmailTitle());
        templateSendingEmailNew.setButtonEmailText(templateSendingEmail.getButtonEmailText());
        templateSendingEmailNew.setLinkForClick(templateSendingEmail.getLinkForClick());
        templateSendingEmailNew.setBannerLink(templateSendingEmail.getBannerLink());
        templateSendingEmailDao.create(templateSendingEmailNew);
        return templateSendingEmailDao.getIdLastCreatedTemplateSendingEmail(dateTime, userAccount,
                templateSendingEmailNew.getTitle(), templateSendingEmailNew.getSubjectEmail());
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean update(TemplateSendingEmail templateSendingEmail, UserAccount userAccount) {
        TemplateSendingEmail templateSendingEmailFromDB = templateSendingEmailDao.getById(templateSendingEmail.getId());
        if (templateSendingEmailFromDB == null) {
            return false;
        }
        templateSendingEmailFromDB.setTitle(templateSendingEmail.getTitle());
        templateSendingEmailFromDB.setDescription(templateSendingEmail.getDescription());
        templateSendingEmailFromDB.setSubjectEmail(templateSendingEmail.getSubjectEmail());
        templateSendingEmailFromDB.setBodyEmailTitle(templateSendingEmail.getBodyEmailTitle());
        templateSendingEmailFromDB.setBodyEmailText(templateSendingEmail.getBodyEmailText());
        templateSendingEmailFromDB.setButtonEmailText(templateSendingEmailFromDB.getButtonEmailText());
        templateSendingEmailFromDB.setLinkForClick(templateSendingEmail.getLinkForClick());
        templateSendingEmailFromDB.setBannerLink(templateSendingEmail.getBannerLink());
        templateSendingEmailFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        templateSendingEmailFromDB.setLastModifiedBy(userAccount);
        templateSendingEmailDao.update(templateSendingEmailFromDB);
        return true;
    }

    @Override
    @Transactional("transactionManagerMain")
    public TemplateSendingEmail getById(Long id) {
        return templateSendingEmailDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAllTemplatesSendingEmails() {
        return templateSendingEmailDao.getCountAllTemplatesSendingEmails();
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<TemplateSendingEmail> getAllTemplatesSendingEmails(int page) {
        return templateSendingEmailDao.getAllTemplatesSendingEmails(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountTemplatesSendingEmailsForDates(LocalDate dateFrom, LocalDate dateTo) {
        return templateSendingEmailDao.getCountTemplatesSendingEmailsForDates(dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<TemplateSendingEmail> getTemplatesSendingEmailsForDates(LocalDate dateFrom,
                                                                        LocalDate dateTo,
                                                                        int page) {
        return templateSendingEmailDao.getTemplatesSendingEmailsForDates(dateFrom, dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountTemplatesSendingEmailsForDateFrom(LocalDate dateFrom) {
        return templateSendingEmailDao.getCountTemplatesSendingEmailsForDateFrom(dateFrom);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<TemplateSendingEmail> getTemplatesSendingEmailsForDateFrom(LocalDate dateFrom,
                                                                           int page) {
        return templateSendingEmailDao.getTemplatesSendingEmailsForDateFrom(dateFrom, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountTemplatesSendingEmailsForDateTo(LocalDate dateTo) {
        return templateSendingEmailDao.getCountTemplatesSendingEmailsForDateTo(dateTo);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<TemplateSendingEmail> getTemplatesSendingEmailsForDateTo(LocalDate dateTo, int page) {
        return templateSendingEmailDao.getTemplatesSendingEmailsForDateTo(dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean delete(Long id) {
        TemplateSendingEmail templateSendingEmail = templateSendingEmailDao.getById(id);
        if (templateSendingEmail == null) {
            return false;
        }
        if (templateSendingEmailDao.getCountSendingEmailsForTemplate(templateSendingEmail) > 0) {
            return false;
        }
        templateSendingEmailDao.delete(templateSendingEmail);
        return true;
    }

}
