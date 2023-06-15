package com.korotkov.mainCurrentApp.service.email;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class EmailConfig {
    /*Email From*/
    public static final String FROM = "from";

    /*Email To*/
    public static final String TO = "to";

    /*Email Subject*/
    public static final String SUBJECT = "subject";

    /*Email BCC*/
    public static final String BCC_LIST = "bccList";

    /*Email CCC*/
    public static final String CCC_LIST = "ccList";

    /*Email Attachment*/
    public static final String ATTACHMENTS = "attachments";



    public static final int COUNT_EMAILS_FOR_EACH_EMAIL_ACCOUNT = 20;
    public static final int COUNT_MINUTES_FOR_ONE_STEP_FOR_USERNAME = 1;

    public static final String EMAIL_USERNAME = "no.reply@tienoi.com.vn";
    public static final String EMAIL_USERNAME_01 = "no.reply-01@tienoi.com.vn";
    public static final String EMAIL_USERNAME_02 = "no.reply-02@tienoi.com.vn";
    public static final String EMAIL_USERNAME_03 = "no.reply-03@tienoi.com.vn";
    public static final String EMAIL_USERNAME_04 = "no.reply-04@tienoi.com.vn";
    public static final String EMAIL_USERNAME_05 = "no.reply-05@tienoi.com.vn";
    public static final String EMAIL_USERNAME_06 = "no.reply-06@tienoi.com.vn";
    public static final String EMAIL_USERNAME_07 = "no.reply-07@tienoi.com.vn";
    public static final String EMAIL_USERNAME_08 = "no.reply-08@tienoi.com.vn";
    public static final String EMAIL_USERNAME_09 = "no.reply-09@tienoi.com.vn";
    public static final String EMAIL_USERNAME_10 = "no.reply-10@tienoi.com.vn";
    public static final String EMAIL_USERNAME_11 = "no.reply-11@tienoi.com.vn";
    public static final String EMAIL_USERNAME_12 = "no.reply-12@tienoi.com.vn";
    public static final String EMAIL_USERNAME_13 = "no.reply-13@tienoi.com.vn";
    public static final String EMAIL_USERNAME_14 = "no.reply-14@tienoi.com.vn";
    public static final String EMAIL_USERNAME_15 = "no.reply-15@tienoi.com.vn";
    public static final String EMAIL_USERNAME_16 = "no.reply-16@tienoi.com.vn";
    public static final String EMAIL_USERNAME_17 = "no.reply-17@tienoi.com.vn";
    public static final String EMAIL_USERNAME_18 = "no.reply-18@tienoi.com.vn";
    public static final String EMAIL_USERNAME_19 = "no.reply-19@tienoi.com.vn";
    public static final String EMAIL_PASSWORD = "***";

    public static Map<String, Integer> MAP_SENDER_AND_COUNT = new HashMap<>();
    public static Map<String, LocalDateTime> MAP_SENDER_AND_LAST_SENT_TIME = new HashMap<>();

}
