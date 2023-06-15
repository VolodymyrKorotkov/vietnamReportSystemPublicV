package com.korotkov.mainCurrentApp.dao.userRole;

import com.korotkov.mainCurrentApp.dao.userRole.UserRoleDao;
import com.korotkov.mainCurrentApp.model.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleDaoImpl implements UserRoleDao {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(UserRole userRole) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(userRole);
    }

    @Override
    public void update(UserRole userRole) {
        Session session = sessionFactory.getCurrentSession();
        session.update(userRole);
    }

    @Override
    public UserRole getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(UserRole.class, id);
    }

    @Override
    public UserRole findByUserRoleName(String userRoleName) {
        Session session = sessionFactory.getCurrentSession();
        return (UserRole) session.createQuery("select ur from UserRole ur where ur.name = :userRoleName")
                .setParameter("userRoleName", userRoleName)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserRole> getAllRolesWithoutCurrent(Long userRoleId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ur from UserRole ur where ur.id <> :userRoleId")
                .setParameter("userRoleId", userRoleId)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserRole> getAllRoles() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ur from UserRole ur")
                .list();
    }

}
