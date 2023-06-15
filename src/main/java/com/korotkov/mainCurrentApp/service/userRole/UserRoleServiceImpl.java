package com.korotkov.mainCurrentApp.service.userRole;

import com.korotkov.mainCurrentApp.dao.userRole.UserRoleDao;
import com.korotkov.mainCurrentApp.model.UserAccount;
import com.korotkov.mainCurrentApp.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService{
    private UserRoleDao userRoleDao;

    @Autowired
    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    @Transactional("transactionManagerMain")
    public void create(UserRole userRole) {
        userRoleDao.create(userRole);
    }

    @Override
    @Transactional("transactionManagerMain")
    public void update(UserRole userRole) {
        userRoleDao.update(userRole);
    }

    @Override
    @Transactional("transactionManagerMain")
    public UserRole getById(Long id) {
        return userRoleDao.getById(id);
    }

    @Override
    @Transactional("transactionManagerMain")
    public UserRole findByUserRoleName(String userRoleName) {
        return userRoleDao.findByUserRoleName(userRoleName);
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UserRole> getAllRolesWithoutCurrent(UserAccount userAccount) {
        return userRoleDao.getAllRolesWithoutCurrent(userAccount.getUserRole().getId());
    }

    @Override
    @Transactional("transactionManagerMain")
    public List<UserRole> getAllRoles() {
        return userRoleDao.getAllRoles();
    }
}
