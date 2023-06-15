package com.korotkov.creditCRM.dao.userAccountShortly;

import com.korotkov.creditCRM.model.BackUserAccountShortly;

public interface UserAccountShortlyDao {
    BackUserAccountShortly getBackUserAccountShortlyByEmail(String email);
}
