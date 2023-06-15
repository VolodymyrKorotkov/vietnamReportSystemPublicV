package com.korotkov.mainCurrentApp.config;

import java.time.LocalDateTime;

public interface ConfigConstants {
    public static final String EMAIL_FROM = "**";
    public static final String EMAIL_FROM_V_2 = "***";

    public static final String TIME_ZONE = "Asia/Ho_Chi_Minh";
    public static final int COUNT_MONTH_FOR_EXPIRED_PASSWORD = 6;
    public static final String MAIN_DOMAIN_URL = "***";
    public static final String VERIFICATION_EMAIL_URL = "/security/verification/email";
    public static final String VERIFICATION_PASSWORD_CHANGE_URL = "/security/verification/change-password";
    public static final String LOGIN_PAGE_URL = "/security/login";
    public static final String LOGOUT_URL = "/security/logout";
    public static final int MAX_COUNT_DAYS_FOR_BONUS_EXPORT = 31;


    int HOUR_CHECKING_DAY_START = 9; //09:00
    int HOUR_CHECKING_DAY_END = 19; //19:59
    int HOUR_CHECKING_EVENING_START = 20; //20:00
    int HOUR_CHECKING_EVENING_END = 23; //23:59
    int HOUR_CHECKING_NIGHT_START = 0; //00:00
    int HOUR_CHECKING_NIGHT_END = 8; //08:59

    String[] EMAIL_MANAGERS_FOR_WARNINGS = new String[] {"***"};
}
