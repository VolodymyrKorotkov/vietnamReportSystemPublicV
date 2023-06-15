package com.korotkov.mainCurrentApp.service.systemSetting;

import com.korotkov.mainCurrentApp.dao.systemSetting.SystemSettingDao;
import com.korotkov.mainCurrentApp.model.SystemSetting;
import com.korotkov.mainCurrentApp.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.korotkov.mainCurrentApp.config.ConfigConstants.TIME_ZONE;

@Service
public class SystemSettingServiceImpl implements SystemSettingService {
    private SystemSettingDao systemSettingDao;

    @Autowired
    public void setSystemSettingDao(SystemSettingDao systemSettingDao) {
        this.systemSettingDao = systemSettingDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean update(SystemSetting systemSetting, UserAccount userAccount) {
        SystemSetting systemSettingFromDB = systemSettingDao.getById(systemSetting.getId());
        if (systemSettingFromDB == null) {
            return false;
        }
        systemSettingFromDB.setValue(systemSetting.getValue());
        systemSettingFromDB.setModifiedBy(userAccount);
        systemSettingFromDB.setModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        systemSettingDao.update(systemSettingFromDB);
        return true;
    }

    @Override
    @Transactional("transactionManagerMain")
    public SystemSetting getById(Long id) {
        return systemSettingDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAllSystemSettingList() {
        return systemSettingDao.getCountAllSystemSettingList();
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<SystemSetting> getAllSystemSettingList(int page) {
        return systemSettingDao.getAllSystemSettingList(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public String getSystemSettingValueByTitle(String title) {
        return systemSettingDao.getSystemSettingValueByTitle(title);
    }
}
