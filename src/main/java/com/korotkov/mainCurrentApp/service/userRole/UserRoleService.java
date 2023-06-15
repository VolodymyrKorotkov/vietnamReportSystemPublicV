package com.korotkov.mainCurrentApp.service.userRole;

import com.korotkov.mainCurrentApp.model.UserAccount;
import com.korotkov.mainCurrentApp.model.UserRole;

import java.util.List;

public interface UserRoleService {
    void create(UserRole userRole);
    void update(UserRole userRole);
    UserRole getById(Long id);
    UserRole findByUserRoleName(String userRoleName);
    List<UserRole> getAllRolesWithoutCurrent(UserAccount userAccount);
    List<UserRole> getAllRoles();
}
