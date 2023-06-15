package com.korotkov.mainCurrentApp.dao.userAccount;

import com.korotkov.mainCurrentApp.model.UserAccount;

import java.util.List;

public interface UserAccountDao {
    void create(UserAccount userAccount);
    void update(UserAccount userAccount);
    UserAccount getById(Long id);
    String getPasswordByUsername(String username);
    UserAccount findByUsername(String username);
    List<UserAccount> getUserListWithFilterUsername(int page, String username);
    List<UserAccount> getUserListWithFilterStatus(int page, String status);
    List<UserAccount> getUserListWithFilterUsernameAndStatus(int page, String username, String status);
    List<UserAccount> getAllUsers(int page);
    Long getCountUserListWithFilterUsername(String username);
    Long getCountUserListWithFilterStatus(String status);
    Long getCountUserListWithFilterUsernameAndStatus(String username, String status);
    Long getCountAllUsers();
    List<UserAccount> getUserListWithFilterUsernameAndStatusAndRole(int page, String username, String status, String roleName);
    Long getCountUserListWithFilterUsernameAndStatusAndRole(String username, String status, String roleName);
    List<UserAccount> getUserListWithFilterUsernameAndRole(int page, String username, String roleName);
    Long getCountUserListWithFilterUsernameAndRole(String username, String roleName);
    List<UserAccount> getUserListWithFilterStatusAndRole(int page, String status, String roleName);
    Long getCountUserListWithFilterStatusAndRole(String status, String roleName);
}
