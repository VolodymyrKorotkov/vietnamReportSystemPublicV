package com.korotkov.creditCRM.service.userAccount;

import com.korotkov.creditCRM.model.BackUserAccountShortly;

public interface UserAccountShortlyService {
    BackUserAccountShortly getBackUserAccountShortlyByEmail(String email);
}
