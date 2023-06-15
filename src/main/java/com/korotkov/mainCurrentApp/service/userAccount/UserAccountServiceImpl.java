package com.korotkov.mainCurrentApp.service.userAccount;

import com.korotkov.mainCurrentApp.config.ConfigConstants;
import com.korotkov.mainCurrentApp.dao.userAccount.UserAccountDao;
import com.korotkov.mainCurrentApp.dao.userRole.UserRoleDao;
import com.korotkov.mainCurrentApp.enums.UserStatusEnum;
import com.korotkov.mainCurrentApp.model.UserAccount;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService, ConfigConstants {
    BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserAccountDao userAccountDao;
    private UserRoleDao userRoleDao;

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Autowired
    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public void create(UserAccount userAccount) {
        userAccountDao.create(userAccount);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void update(UserAccount userAccount) {
        userAccountDao.update(userAccount);
    }

    @Override
    @Transactional("transactionManagerMain")
    public UserAccount getById(Long id) {
        UserAccount userAccountFromDB = userAccountDao.getById(id);
        userAccountFromDB.setUserRoleId(userAccountFromDB.getUserRole().getId());
        return userAccountFromDB;
    }

    @Override
    @Transactional("transactionManagerMain")
    public String getPasswordByUsername(String username) {
        return userAccountDao.getPasswordByUsername(username);
    }

    @Override
    @Transactional("transactionManagerMain")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountDao.findByUsername(username);
        if (userAccount == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return userAccount;
    }

    @Override
    @Transactional("transactionManagerMain")
    public UserAccount findByUsername(String username) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(username);
        userAccountFromDB.setUserRoleId(userAccountFromDB.getUserRole().getId());
        return userAccountFromDB;
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean saveUser(UserAccount userAccount, Long roleId, UserAccount saveBy) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        if(userAccountFromDB != null) {
            if (userAccountFromDB.isEmailConfirmed()) {
                return false;
            }
            userAccountFromDB.setPassword(bCryptPasswordEncoder.encode(userAccount.getPassword()));
            userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            userAccountFromDB.setLastModifiedBy(saveBy);
            userAccountFromDB.setEmail(userAccount.getUsername());
            userAccountFromDB.setPasswordChangeRequired(true);
            userAccountFromDB.setPasswordExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusMonths(COUNT_MONTH_FOR_EXPIRED_PASSWORD));
            userAccountFromDB.setStatus(String.valueOf(UserStatusEnum.INACTIVE));
            userAccountFromDB.setFirstName(userAccount.getFirstName());
            userAccountFromDB.setLastName(userAccount.getLastName());
            userAccountFromDB.setGender(userAccount.getGender());
            userAccountFromDB.setUserRole(userRoleDao.getById(roleId));
            userAccountDao.update(userAccountFromDB);
        } else {
            UserAccount userAccountForSave = new UserAccount();
            userAccountForSave.setCreatedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            userAccountForSave.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
            userAccountForSave.setUsername(userAccount.getUsername());
            userAccountForSave.setEmail(userAccount.getUsername());
            userAccountForSave.setPassword(bCryptPasswordEncoder.encode(userAccount.getPassword()));
            userAccountForSave.setPasswordChangeRequired(true);
            userAccountForSave.setEmailConfirmed(false);
            userAccountForSave.setPasswordExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusMonths(COUNT_MONTH_FOR_EXPIRED_PASSWORD));
            userAccountForSave.setStatus(String.valueOf(UserStatusEnum.INACTIVE));
            userAccountForSave.setFirstName(userAccount.getFirstName());
            userAccountForSave.setLastName(userAccount.getLastName());
            userAccountForSave.setGender(userAccount.getGender());
            userAccountForSave.setUserRole(userRoleDao.getById(roleId));
            userAccountForSave.setCreatedBy(saveBy);
            userAccountForSave.setLastModifiedBy(saveBy);
            userAccountDao.create(userAccountForSave);
        }
        return true;
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean updateEmailConfirmedToTrue(Long idUser, String verificationCode) {
        UserAccount userAccountFromDB = userAccountDao.getById(idUser);
        if (userAccountFromDB == null) {
            return false;
        }
        if (userAccountFromDB.isEmailConfirmed()) {
            return true;
        }
        if (!verificationCode.equals(userAccountFromDB.getDynamicCode())) {
            return false;
        }
        userAccountFromDB.setEmailConfirmed(true);
        userAccountFromDB.setDynamicCode(null);
        if (userAccountFromDB.getStatus().equals(String.valueOf(UserStatusEnum.INACTIVE))) {
            userAccountFromDB.setStatus(String.valueOf(UserStatusEnum.ACTIVE));
        }
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountFromDB.setLastModifiedBy(userAccountFromDB);
        userAccountDao.update(userAccountFromDB);
        return true;
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean checkUserPassword(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        if (userAccountFromDB == null) {
            return false;
        }
        if (bCryptPasswordEncoder.matches(userAccount.getPassword(), userAccountFromDB.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional("transactionManagerMain")
    public UserAccount saveNewPasswordWithoutConfirm(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        userAccountFromDB.setLastModifiedBy(userAccountFromDB);
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountFromDB.setPasswordExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusMonths(COUNT_MONTH_FOR_EXPIRED_PASSWORD));
        userAccountFromDB.setPassword(bCryptPasswordEncoder.encode(userAccount.getNewPassword()));
        userAccountFromDB.setPasswordChangeRequired(false);
        userAccountFromDB.setTempPasswordDuringChange(null);
        userAccountDao.update(userAccountFromDB);
        return userAccountFromDB;
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean saveNewTemporaryPassword(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        if (userAccountFromDB == null) {
            return false;
        }
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountFromDB.setLastModifiedBy(userAccountFromDB);
        userAccountFromDB.setTempPasswordDuringChange(bCryptPasswordEncoder.encode(userAccount.getNewPassword()));
        userAccountDao.update(userAccountFromDB);
        return true;
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean saveNewPasswordFinal(Long idUser, String verificationCode) {
        UserAccount userAccountFromDB = userAccountDao.getById(idUser);
        if (userAccountFromDB == null) {
            return false;
        }
        if (userAccountFromDB.getTempPasswordDuringChange() == null) {
            return false;
        }
        if (!verificationCode.equals(userAccountFromDB.getDynamicCode())) {
            return false;
        }
        userAccountFromDB.setLastModifiedBy(userAccountFromDB);
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountFromDB.setPassword(userAccountFromDB.getTempPasswordDuringChange());
        userAccountFromDB.setPasswordExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusMonths(COUNT_MONTH_FOR_EXPIRED_PASSWORD));
        userAccountFromDB.setPasswordChangeRequired(false);
        userAccountFromDB.setTempPasswordDuringChange(null);
        userAccountFromDB.setDynamicCode(null);
        userAccountDao.update(userAccountFromDB);
        return true;
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean generateNewVerificationCode(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        if (userAccountFromDB == null) {
            return false;
        }
        userAccountFromDB.setDynamicCode(RandomStringUtils.randomAlphanumeric(50, 100));
        userAccountFromDB.setLastModifiedBy(userAccountFromDB);
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountDao.update(userAccountFromDB);
        return true;
    }

    @Override
    @Transactional("transactionManagerMain")
    public String generateNewLinkConfirmEmail(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        return MAIN_DOMAIN_URL + VERIFICATION_EMAIL_URL + "/" + userAccountFromDB.getId() + "/" +
                userAccountFromDB.getDynamicCode();
    }

    @Override
    @Transactional("transactionManagerMain")
    public String generateNewLinkConfirmPassword(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        return MAIN_DOMAIN_URL + VERIFICATION_PASSWORD_CHANGE_URL + "/" + userAccountFromDB.getId() +
                "/" + userAccountFromDB.getDynamicCode();
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean checkIsUserByUsername(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        if (userAccountFromDB == null) {
            return false;
        }
        if (userAccountFromDB.getStatus().equals(String.valueOf(UserStatusEnum.INACTIVE))) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional("transactionManagerMain")
    public String resetPassword(UserAccount userAccount) {
        UserAccount userAccountFromDB = userAccountDao.findByUsername(userAccount.getUsername());
        String tempPassword = RandomStringUtils.randomAlphanumeric(12,15) + "!";
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountFromDB.setLastModifiedBy(userAccountFromDB);
        userAccountFromDB.setPasswordExpiredAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)).plusMonths(COUNT_MONTH_FOR_EXPIRED_PASSWORD));
        userAccountFromDB.setPasswordChangeRequired(true);
        userAccountFromDB.setPassword(bCryptPasswordEncoder.encode(tempPassword));
        userAccountDao.update(userAccountFromDB);
        return tempPassword;
    }

    @Override
    @Transactional("transactionManagerMain")
    public UserAccount changeUserAccountData(Long userId, UserAccount userAccount, UserAccount changeBy, Long roleId) {
        UserAccount userAccountFromDB = userAccountDao.getById(userId);
        userAccountFromDB.setFirstName(userAccount.getFirstName());
        userAccountFromDB.setLastName(userAccount.getLastName());
        userAccountFromDB.setGender(userAccount.getGender());
        userAccountFromDB.setUserRole(userRoleDao.getById(roleId));
        userAccountFromDB.setLastModifiedAt(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        userAccountFromDB.setLastModifiedBy(changeBy);
        userAccountDao.update(userAccountFromDB);
        return userAccountFromDB;
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UserAccount> getUserListWithFilterUsername(int page, String username) {
        return userAccountDao.getUserListWithFilterUsername(page, username);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UserAccount> getUserListWithFilterStatus(int page, String status) {
        return userAccountDao.getUserListWithFilterStatus(page, status);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UserAccount> getUserListWithFilterUsernameAndStatus(int page, String username, String status) {
        return userAccountDao.getUserListWithFilterUsernameAndStatus(page, username, status);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UserAccount> getAllUsers(int page) {
        return userAccountDao.getAllUsers(page);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountUserListWithFilterUsername(String username) {
        return userAccountDao.getCountUserListWithFilterUsername(username);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountUserListWithFilterStatus(String status) {
        return userAccountDao.getCountUserListWithFilterStatus(status);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountUserListWithFilterUsernameAndStatus(String username, String status) {
        return userAccountDao.getCountUserListWithFilterUsernameAndStatus(username, status);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountAllUsers() {
        return userAccountDao.getCountAllUsers();
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UserAccount> getUserListWithFilterUsernameAndStatusAndRole(int page, String username, String status, String roleName) {
        return userAccountDao.getUserListWithFilterUsernameAndStatusAndRole(page, username, status, roleName);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountUserListWithFilterUsernameAndStatusAndRole(String username, String status, String roleName) {
        return userAccountDao.getCountUserListWithFilterUsernameAndStatusAndRole(username, status, roleName);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UserAccount> getUserListWithFilterUsernameAndRole(int page, String username, String roleName) {
        return userAccountDao.getUserListWithFilterUsernameAndRole(page, username, roleName);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountUserListWithFilterUsernameAndRole(String username, String roleName) {
        return userAccountDao.getCountUserListWithFilterUsernameAndRole(username, roleName);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UserAccount> getUserListWithFilterStatusAndRole(int page, String status, String roleName) {
        return userAccountDao.getUserListWithFilterStatusAndRole(page, status, roleName);
    }

    @Override
    @Transactional("transactionManagerMain")
    public Long getCountUserListWithFilterStatusAndRole(String status, String roleName) {
        return userAccountDao.getCountUserListWithFilterStatusAndRole(status, roleName);
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean activateUser(Long id, UserAccount changedBy) {
        UserAccount userAccountFromDB = userAccountDao.getById(id);
        if (userAccountFromDB == null) {
            return false;
        }
        if (!userAccountFromDB.getStatus().equals(String.valueOf(UserStatusEnum.LOCKED))) {
            return false;
        }
        userAccountFromDB.setStatus(String.valueOf(UserStatusEnum.ACTIVE));
        userAccountDao.update(userAccountFromDB);
        return true;
    }

    @Override
    @Transactional("transactionManagerMain")
    public boolean lockUser(Long id, UserAccount changedBy) {
        UserAccount userAccountFromDB = userAccountDao.getById(id);
        if (userAccountFromDB == null) {
            return false;
        }
        if (!userAccountFromDB.getStatus().equals(String.valueOf(UserStatusEnum.ACTIVE))) {
            return false;
        }
        userAccountFromDB.setStatus(String.valueOf(UserStatusEnum.LOCKED));
        userAccountDao.update(userAccountFromDB);
        return true;
    }
}
