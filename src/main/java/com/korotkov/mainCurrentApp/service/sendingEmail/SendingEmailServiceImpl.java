package com.korotkov.mainCurrentApp.service.sendingEmail;

import com.korotkov.mainCurrentApp.dao.sendingEmail.SendingEmailDao;
import com.korotkov.mainCurrentApp.enums.EmailSendingStatusEnum;
import com.korotkov.mainCurrentApp.model.SendingEmail;
import com.korotkov.mainCurrentApp.model.TemplateSendingEmail;
import com.korotkov.mainCurrentApp.model.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class SendingEmailServiceImpl implements SendingEmailService {
    private static final Logger logger = LoggerFactory.getLogger(SendingEmailServiceImpl.class);
    private SendingEmailDao sendingEmailDao;

    @Autowired
    public void setSendingEmailDao(SendingEmailDao sendingEmailDao) {
        this.sendingEmailDao = sendingEmailDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long createAutoAsStartedAndGetId(String title, Integer countEmailsTotal) {
        SendingEmail sendingEmail = new SendingEmail();
        LocalDateTime startedAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        sendingEmail.setCreatedAt(startedAt);
        sendingEmail.setStartedAt(startedAt);
        sendingEmail.setTitle(title);
        sendingEmail.setStatus(String.valueOf(EmailSendingStatusEnum.STARTED));
        sendingEmail.setAuto(true);
        sendingEmail.setCountEmailsTotal(countEmailsTotal);
        sendingEmailDao.create(sendingEmail);
        return sendingEmailDao.getIdLastCreatedSendingEmail(startedAt, title, true);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long createManualAsPlannedAndGetId(TemplateSendingEmail templateSendingEmail,
                                              SendingEmail sendingEmail,
                                              UserAccount userAccount) {
        SendingEmail sendingEmailNew = new SendingEmail();
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        sendingEmailNew.setCreatedAt(dateTime);
        sendingEmailNew.setPlannedAt(sendingEmail.getPlannedAt());
        sendingEmailNew.setTitle(sendingEmail.getTitle());
        sendingEmailNew.setStatus(String.valueOf(EmailSendingStatusEnum.PLANNED));
        sendingEmailNew.setAuto(false);
        sendingEmailNew.setTemplateSendingEmail(templateSendingEmail);
        sendingEmailNew.setAllActiveClientsWithoutExpiredLoans(sendingEmail.isAllActiveClientsWithoutExpiredLoans());
        sendingEmailNew.setRepeatPassiveClients(sendingEmail.isRepeatPassiveClients());
        sendingEmailNew.setRepeatPassiveClientsDaysFrom(sendingEmail.getRepeatPassiveClientsDaysFrom());
        sendingEmailNew.setRepeatPassiveClientsDaysTo(sendingEmail.getRepeatPassiveClientsDaysTo());
        sendingEmailNew.setClientsWithExpiredLoan(sendingEmail.isClientsWithExpiredLoan());
        sendingEmailNew.setClientsWithExpiredLoanDaysFrom(sendingEmail.getClientsWithExpiredLoanDaysFrom());
        sendingEmailNew.setClientsWithExpiredLoanDaysTo(sendingEmail.getClientsWithExpiredLoanDaysTo());
        sendingEmailNew.setRegisteredClientsWithoutApplications(sendingEmail.isRegisteredClientsWithoutApplications());
        sendingEmailNew.setRegisteredClientsWithoutApplicationsDaysFrom(sendingEmail.getRegisteredClientsWithoutApplicationsDaysFrom());
        sendingEmailNew.setRegisteredClientsWithoutApplicationsDaysTo(sendingEmail.getRegisteredClientsWithoutApplicationsDaysTo());
        sendingEmailNew.setCreatedBy(userAccount);
        sendingEmailDao.create(sendingEmailNew);
        return sendingEmailDao.getIdLastCreatedSendingEmail(dateTime, sendingEmailNew.getTitle(),
                false);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void updateAsStarted(SendingEmail sendingEmail, int countEmailsTotal) {
        try {
            sendingEmail.setCountEmailsTotal(countEmailsTotal);
            sendingEmail.setStartedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            sendingEmail.setStatus(String.valueOf(EmailSendingStatusEnum.STARTED));
            sendingEmailDao.update(sendingEmail);
        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + ": " + e.getMessage());
        }
    }

    @Override
    @Transactional("transactionManagerMain")
    public void updateAsCompleted(Long sendingEmailId, int countEmailsSent) {
        try {
            SendingEmail sendingEmail = sendingEmailDao.getById(sendingEmailId);
            sendingEmail.setCompletedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            sendingEmail.setStatus(String.valueOf(EmailSendingStatusEnum.COMPLETED));
            sendingEmail.setCountEmailsSent(countEmailsSent);
            sendingEmail.setCountEmailsNotSent(sendingEmail.getCountEmailsTotal() - countEmailsSent);
        } catch (Exception e) {
            logger.error(LocalDateTime.now(ZoneId.of(TIME_ZONE)) + ": " + e.getMessage());
        }
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAllSendingEmails() {
        return sendingEmailDao.getCountAllSendingEmails();
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<SendingEmail> getAllSendingEmails(int page) {
        return sendingEmailDao.getAllSendingEmails(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<SendingEmail> getSendingEmailsForDates(LocalDate dateFrom, LocalDate dateTo, int page) {
        return sendingEmailDao.getSendingEmailsForDates(dateFrom, dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountSendingEmailsForDates(LocalDate dateFrom, LocalDate dateTo) {
        return sendingEmailDao.getCountSendingEmailsForDates(dateFrom, dateTo);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<SendingEmail> getSendingEmailsForDateFrom(LocalDate dateFrom, int page) {
        return sendingEmailDao.getSendingEmailsForDateFrom(dateFrom, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountSendingEmailsForDateFrom(LocalDate dateFrom) {
        return sendingEmailDao.getCountSendingEmailsForDateFrom(dateFrom);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<SendingEmail> getSendingEmailsForDateTo(LocalDate dateTo, int page) {
        return sendingEmailDao.getSendingEmailsForDateTo(dateTo, page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountSendingEmailsForDateTo(LocalDate dateTo) {
        return sendingEmailDao.getCountSendingEmailsForDateTo(dateTo);
    }

    @Override
    @Transactional("transactionManagerMain")
    public SendingEmail getById(Long id) {
        return sendingEmailDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public SendingEmail getOnePlannedSendingEmailForStart() {
        return sendingEmailDao.getOnePlannedSendingEmailForStart();
    }

    @Override
    @Transactional("transactionManagerMain")
    public void updateStatusAsCanceledForNotCompletedSendingEmail(LocalDate dateBefore) {
        sendingEmailDao.updateStatusAsCanceledForNotCompletedSendingEmail(dateBefore);
    }

}
