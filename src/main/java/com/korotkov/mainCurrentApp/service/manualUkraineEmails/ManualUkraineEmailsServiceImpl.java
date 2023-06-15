package com.korotkov.mainCurrentApp.service.manualUkraineEmails;

import com.korotkov.mainCurrentApp.dao.manualUkraineEmails.ManualUkraineEmailsDao;
import com.korotkov.mainCurrentApp.model.ManualUkraineEmails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManualUkraineEmailsServiceImpl implements ManualUkraineEmailsService {

    private ManualUkraineEmailsDao manualUkraineEmailsDao;

    @Autowired
    public void setManualUkraineEmailsDao(ManualUkraineEmailsDao manualUkraineEmailsDao) {
        this.manualUkraineEmailsDao = manualUkraineEmailsDao;
    }


    @Override
    @Transactional("transactionManagerMain")
    public void create(ManualUkraineEmails manualUkraineEmails) {
        manualUkraineEmailsDao.create(manualUkraineEmails);
    }
}
