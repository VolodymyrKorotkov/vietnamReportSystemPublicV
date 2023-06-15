package com.korotkov.mainCurrentApp.dao.systemSetting;

import com.korotkov.mainCurrentApp.model.SystemSetting;

import java.util.List;

public interface SystemSettingDao {
    void create(SystemSetting systemSetting);
    void update(SystemSetting systemSetting);
    SystemSetting getById(Long id);
    Long getCountAllSystemSettingList();
    List<SystemSetting> getAllSystemSettingList(int page);
    String getSystemSettingValueByTitle(String title);
}
