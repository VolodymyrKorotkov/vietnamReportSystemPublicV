package com.korotkov.mainCurrentApp.service.email;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;
import static com.korotkov.mainCurrentApp.service.email.EmailConfig.*;

@Service
public class EmailCommonServiceImpl implements EmailCommonService {

    private static final Logger logger = LoggerFactory.getLogger(EmailCommonServiceImpl.class);

    private JavaMailSender mailSender;
    private JavaMailSender mailSender01;
    private JavaMailSender mailSender02;
    private JavaMailSender mailSender03;
    private JavaMailSender mailSender04;
    private JavaMailSender mailSender05;
    private JavaMailSender mailSender06;
    private JavaMailSender mailSender07;
    private JavaMailSender mailSender08;
    private JavaMailSender mailSender09;
    private JavaMailSender mailSender10;
    private JavaMailSender mailSender11;
    private JavaMailSender mailSender12;
    private JavaMailSender mailSender13;
    private JavaMailSender mailSender14;
    private JavaMailSender mailSender15;
    private JavaMailSender mailSender16;
    private JavaMailSender mailSender17;
    private JavaMailSender mailSender18;
    private JavaMailSender mailSender19;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    @Qualifier("mailSender")
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    @Qualifier("mailSender01")
    public void setMailSender01(JavaMailSender mailSender01) {
        this.mailSender01 = mailSender01;
    }

    @Autowired
    @Qualifier("mailSender02")
    public void setMailSender02(JavaMailSender mailSender02) {
        this.mailSender02 = mailSender02;
    }

    @Autowired
    @Qualifier("mailSender03")
    public void setMailSender03(JavaMailSender mailSender03) {
        this.mailSender03 = mailSender03;
    }

    @Autowired
    @Qualifier("mailSender04")
    public void setMailSender04(JavaMailSender mailSender04) {
        this.mailSender04 = mailSender04;
    }

    @Autowired
    @Qualifier("mailSender05")
    public void setMailSender05(JavaMailSender mailSender05) {
        this.mailSender05 = mailSender05;
    }

    @Autowired
    @Qualifier("mailSender06")
    public void setMailSender06(JavaMailSender mailSender06) {
        this.mailSender06 = mailSender06;
    }

    @Autowired
    @Qualifier("mailSender07")
    public void setMailSender07(JavaMailSender mailSender07) {
        this.mailSender07 = mailSender07;
    }

    @Autowired
    @Qualifier("mailSender08")
    public void setMailSender08(JavaMailSender mailSender08) {
        this.mailSender08 = mailSender08;
    }

    @Autowired
    @Qualifier("mailSender09")
    public void setMailSender09(JavaMailSender mailSender09) {
        this.mailSender09 = mailSender09;
    }

    @Autowired
    @Qualifier("mailSender10")
    public void setMailSender10(JavaMailSender mailSender10) {
        this.mailSender10 = mailSender10;
    }

    @Autowired
    @Qualifier("mailSender11")
    public void setMailSender11(JavaMailSender mailSender11) {
        this.mailSender11 = mailSender11;
    }

    @Autowired
    @Qualifier("mailSender12")
    public void setMailSender12(JavaMailSender mailSender12) {
        this.mailSender12 = mailSender12;
    }

    @Autowired
    @Qualifier("mailSender13")
    public void setMailSender13(JavaMailSender mailSender13) {
        this.mailSender13 = mailSender13;
    }

    @Autowired
    @Qualifier("mailSender14")
    public void setMailSender14(JavaMailSender mailSender14) {
        this.mailSender14 = mailSender14;
    }

    @Autowired
    @Qualifier("mailSender15")
    public void setMailSender15(JavaMailSender mailSender15) {
        this.mailSender15 = mailSender15;
    }

    @Autowired
    @Qualifier("mailSender16")
    public void setMailSender16(JavaMailSender mailSender16) {
        this.mailSender16 = mailSender16;
    }

    @Autowired
    @Qualifier("mailSender17")
    public void setMailSender17(JavaMailSender mailSender17) {
        this.mailSender17 = mailSender17;
    }

    @Autowired
    @Qualifier("mailSender18")
    public void setMailSender18(JavaMailSender mailSender18) {
        this.mailSender18 = mailSender18;
    }

    @Autowired
    @Qualifier("mailSender19")
    public void setMailSender19(JavaMailSender mailSender19) {
        this.mailSender19 = mailSender19;
    }


    private boolean tryToSendEmailWithUsername(String emailUsername, MimeMessagePreparator preparator) {
        boolean result = false;
        try {

            switch (emailUsername) {
                case EMAIL_USERNAME -> {
                    mailSender.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_01 -> {
                    mailSender01.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_02 -> {
                    mailSender02.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_03 -> {
                    mailSender03.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_04 -> {
                    mailSender04.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_05 -> {
                    mailSender05.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_06 -> {
                    mailSender06.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_07 -> {
                    mailSender07.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_08 -> {
                    mailSender08.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_09 -> {
                    mailSender09.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_10 -> {
                    mailSender10.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_11 -> {
                    mailSender11.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_12 -> {
                    mailSender12.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_13 -> {
                    mailSender13.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_14 -> {
                    mailSender14.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_15 -> {
                    mailSender15.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_16 -> {
                    mailSender16.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_17 -> {
                    mailSender17.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_18 -> {
                    mailSender18.send(preparator);
                    result = true;
                }
                case EMAIL_USERNAME_19 -> {
                    mailSender19.send(preparator);
                    result = true;
                }

            }
        } catch (Exception e) {
//            logger.error(e.getMessage());
        }
        return result;
    }


    @Override
    public boolean sendEmail (final String templateName, final Map<String,Object> model) {
        boolean result = false;
        int countAttempts = 0;

        try {

            while (!result) {

                if (countAttempts > 5) {
                    break;
                }

                if (canSendEmailByUsernameCheckCount(EMAIL_USERNAME_01)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_01)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_01);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_01, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_01, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_01);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_02)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_02)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_02);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_02, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_02, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_02);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_03)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_03)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_03);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_03, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_03, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_03);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_04)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_04)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_04);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_04, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_04, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_04);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_05)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_05)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_05);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_05, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_05, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_05);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_06)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_06)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_06);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_06, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_06, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_06);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_07)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_07)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_07);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_07, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_07, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_07);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_08)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_08)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_08);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_08, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_08, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_08);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_09)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_09)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_09);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_09, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_09, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_09);
                    }
                }

                //add new email accounts

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_10)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_10)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_10);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_10, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_10, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_10);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_11)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_11)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_11);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_11, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_11, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_11);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_12)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_12)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_12);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_12, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_12, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_12);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_13)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_13)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_13);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_13, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_13, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_13);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_14)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_14)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_14);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_14, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_14, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_14);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_15)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_15)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_15);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_15, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_15, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_15);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_16)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_16)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_16);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_16, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_16, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_16);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_17)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_17)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_17);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_17, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_17, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_17);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_18)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_18)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_18);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_18, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_18, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_18);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME_19)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME_19)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME_19);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME_19, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME_19, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME_19);
                    }
                }

                if (!result && canSendEmailByUsernameCheckCount(EMAIL_USERNAME)) {
                    if (!checkTimeCanSendNewEmail(EMAIL_USERNAME)) {
                        sleepCurrentThreadWithTimeLimit();
                    }
                    model.put(FROM, EMAIL_USERNAME);
                    MimeMessagePreparator preparator = getMimeMessagePreparator(templateName, model);
                    result = tryToSendEmailWithUsername(EMAIL_USERNAME, preparator);
                    if (result) {
                        addCountSentEmailToMap(EMAIL_USERNAME, 1);
                        checkAndAddSentEmailCurrentTimeToMap(EMAIL_USERNAME);
                    }
                }

                if (!canSendEmailByUsernameCheckCount(EMAIL_USERNAME)) {
                    deleteAllValuesFromMapCountAndEmailUsername();
                }

                countAttempts++;
            }
        } catch (Exception e) {
//            logger.error(e.getMessage());
        }

        return result;
    }

    private void checkAndAddSentEmailCurrentTimeToMap(String emailUsername) {
        if (!canSendEmailByUsernameCheckCount(emailUsername)) {
            MAP_SENDER_AND_LAST_SENT_TIME.put(emailUsername, LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        }
    }

    private void addCountSentEmailToMap(String emailUsername, int count) {
        if (!MAP_SENDER_AND_COUNT.containsKey(emailUsername)) {
            MAP_SENDER_AND_COUNT.put(emailUsername, count);
        } else {
            MAP_SENDER_AND_COUNT.put(emailUsername, MAP_SENDER_AND_COUNT.get(emailUsername) + count);
        }
    }


    private void sleepCurrentThreadWithTimeLimit() {
        try {
            Thread.sleep(COUNT_MINUTES_FOR_ONE_STEP_FOR_USERNAME * 60 * 1000);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private MimeMessagePreparator getMimeMessagePreparator(final String templateName,
                                                           final Map<String,Object> model) {
        return new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                String from = (String) model.get(FROM);
                String to = (String) model.get(TO);
                String subject = (String) model.get(SUBJECT);

                List<String> bccList = (List<String>) model.get(BCC_LIST);

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");
                message.setFrom(from);
                message.setTo(to);
                message.setSubject(subject);
                message.setSentDate(new Date());
                if(bccList != null){
                    for(String bcc: bccList){
                        message.addBcc(bcc);
                    }
                }

                List<FileSystemResource> fileList = (List<FileSystemResource>) model.get(ATTACHMENTS);

                if (fileList != null) {
                    for (FileSystemResource file:fileList) {
                        message.addAttachment(file.getFilename(), file);
                    }
                }

                model.put("noArgs", new Object());
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        templateName,"UTF-8",model);

                message.setText(text,true);
            }
        };
    }

    private void deleteAllValuesFromMapCountAndEmailUsername() {
        MAP_SENDER_AND_COUNT.clear();
    }

    private boolean canSendEmailByUsernameCheckCount(String emailUsername) {
        boolean result = false;
        if (MAP_SENDER_AND_COUNT.containsKey(emailUsername)) {
            if (MAP_SENDER_AND_COUNT.get(emailUsername) < COUNT_EMAILS_FOR_EACH_EMAIL_ACCOUNT) {
                result = true;
            }
        } else {
            result = true;
        }
        return result;
    }

    private boolean checkTimeCanSendNewEmail(String emailUsername) {
        boolean result = true;
        if (MAP_SENDER_AND_LAST_SENT_TIME.containsKey(emailUsername)) {
            if (MAP_SENDER_AND_LAST_SENT_TIME.get(emailUsername).isAfter(LocalDateTime.now(ZoneId.of(TIME_ZONE))
                    .minusMinutes(COUNT_MINUTES_FOR_ONE_STEP_FOR_USERNAME))) {
                result = false;
            }
        }
        return result;
    }

}
