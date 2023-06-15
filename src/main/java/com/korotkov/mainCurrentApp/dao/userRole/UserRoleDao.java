package com.korotkov.mainCurrentApp.dao.userRole;

import com.korotkov.mainCurrentApp.model.UserRole;

import java.util.List;

public interface UserRoleDao {
    void create(UserRole userRole);
    void update(UserRole userRole);
    UserRole getById(Long id);
    UserRole findByUserRoleName(String userRoleName);
    List<UserRole> getAllRolesWithoutCurrent(Long userRoleId);
    List<UserRole> getAllRoles();
}
