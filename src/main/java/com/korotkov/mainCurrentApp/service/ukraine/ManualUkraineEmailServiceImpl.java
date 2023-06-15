package com.korotkov.mainCurrentApp.service.ukraine;

import com.korotkov.mainCurrentApp.model.ManualUkraineEmails;
import com.korotkov.mainCurrentApp.service.email.EmailCommonService;
import com.korotkov.mainCurrentApp.service.manualUkraineEmails.ManualUkraineEmailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;
import static com.korotkov.mainCurrentApp.service.email.EmailConfig.SUBJECT;
import static com.korotkov.mainCurrentApp.service.email.EmailConfig.TO;

@Service
public class ManualUkraineEmailServiceImpl implements ManualUkraineEmailService {
    EmailCommonService emailService;
    ManualUkraineEmailsService manualUkraineEmailsService;

    @Autowired
    public void setEmailService(EmailCommonService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setManualUkraineEmailsService(ManualUkraineEmailsService manualUkraineEmailsService) {
        this.manualUkraineEmailsService = manualUkraineEmailsService;
    }


    @Async("emailScheduler")
    public void sendUkraineManualEmails(List<String> emailList) {
        LocalDateTime startedAt = LocalDateTime.now(ZoneId.of(TIME_ZONE));

        int countSuccessSentEmails = 0;
        Map<String, Object> emailModel = new HashMap<>();
        emailModel.put("mainUrl", "https://scoring-machine.com/kredyty-online");
        emailModel.put(SUBJECT, "1 клік для отримання кредиту");

        for (String email : emailList) {
            emailModel.put(TO, email);
            boolean result = emailService.sendEmail("ukraineManualEmail.vm", emailModel);

            if (result) {
                countSuccessSentEmails++;
            }
        }

        ManualUkraineEmails manualUkraineEmails = new ManualUkraineEmails();
        manualUkraineEmails.setStartedAt(startedAt);
        manualUkraineEmails.setFinishedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        manualUkraineEmails.setCount(countSuccessSentEmails);
        manualUkraineEmailsService.create(manualUkraineEmails);
    }
}
