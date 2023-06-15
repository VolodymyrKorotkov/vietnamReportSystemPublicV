package com.korotkov.mainCurrentApp.service.systemSetting;

import com.korotkov.mainCurrentApp.model.SystemSetting;
import com.korotkov.mainCurrentApp.model.UserAccount;

import java.util.List;

public interface SystemSettingService {
    boolean update(SystemSetting systemSetting, UserAccount userAccount);
    SystemSetting getById(Long id);
    Long getCountAllSystemSettingList();
    List<SystemSetting> getAllSystemSettingList(int page);
    String getSystemSettingValueByTitle(String title);
}
