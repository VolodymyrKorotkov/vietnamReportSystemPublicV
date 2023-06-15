package com.korotkov.creditCRM.service.userAccount;

import com.korotkov.creditCRM.dao.userAccountShortly.UserAccountShortlyDao;
import com.korotkov.creditCRM.model.BackUserAccountShortly;
import com.korotkov.creditCRM.service.userAccount.UserAccountShortlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountShortlyServiceImpl implements UserAccountShortlyService {
    UserAccountShortlyDao userAccountShortlyDao;

    @Autowired
    public void setUserAccountShortlyDao(UserAccountShortlyDao userAccountShortlyDao) {
        this.userAccountShortlyDao = userAccountShortlyDao;
    }

    @Override
    @Transactional("transactionManagerCRM")
    public BackUserAccountShortly getBackUserAccountShortlyByEmail(String email) {
        return userAccountShortlyDao.getBackUserAccountShortlyByEmail(email);
    }
}
