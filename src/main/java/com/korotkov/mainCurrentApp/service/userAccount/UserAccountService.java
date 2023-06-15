package com.korotkov.mainCurrentApp.service.userAccount;

import com.korotkov.mainCurrentApp.model.UserAccount;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserAccountService extends UserDetailsService {
    void create(UserAccount userAccount);
    void update(UserAccount userAccount);
    UserAccount getById(Long id);
    String getPasswordByUsername(String username);
    UserDetails loadUserByUsername(String s);
    boolean saveUser(UserAccount userAccount, Long roleId, UserAccount saveBy);
    boolean updateEmailConfirmedToTrue(Long idUser, String verificationCode);
    boolean checkUserPassword(UserAccount userAccount);
    boolean saveNewTemporaryPassword(UserAccount userAccount);
    boolean saveNewPasswordFinal(Long idUser, String verificationCode);
    boolean generateNewVerificationCode(UserAccount userAccount);
    String generateNewLinkConfirmEmail(UserAccount userAccount);
    String generateNewLinkConfirmPassword(UserAccount userAccount);
    boolean checkIsUserByUsername(UserAccount userAccount);
    String resetPassword(UserAccount userAccount);
    UserAccount findByUsername(String username);
    UserAccount changeUserAccountData(Long userId, UserAccount userAccount, UserAccount changeBy, Long roleId);
    UserAccount saveNewPasswordWithoutConfirm(UserAccount userAccount);
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
    boolean activateUser(Long id, UserAccount changedBy);
    boolean lockUser(Long id, UserAccount changedBy);
}
