package com.korotkov.mainCurrentApp.dao.userAccount;

import com.korotkov.mainCurrentApp.dao.userAccount.UserAccountDao;
import com.korotkov.mainCurrentApp.model.UserAccount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAccountDaoImpl implements UserAccountDao {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(UserAccount userAccount) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(userAccount);
    }

    @Override
    public void update(UserAccount userAccount) {
        Session session = sessionFactory.getCurrentSession();
        session.update(userAccount);
    }

    @Override
    public UserAccount getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(UserAccount.class, id);
    }

    @Override
    public String getPasswordByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createQuery("select ua.password from UserAccount ua where ua.username = :username")
                .setParameter("username", username)
                .uniqueResult();
    }

    @Override
    public UserAccount findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        return (UserAccount) session.createQuery("select ua from UserAccount ua where ua.username = :username")
                .setParameter("username", username)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAccount> getUserListWithFilterUsername(int page, String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ua from UserAccount ua where ua.username like :username order by ua.id asc")
                .setParameter("username", "%" + username + "%")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAccount> getUserListWithFilterStatus(int page, String status) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ua from UserAccount ua where ua.status = :status order by ua.id asc")
                .setParameter("status", status)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAccount> getUserListWithFilterUsernameAndStatus(int page, String username, String status) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ua from UserAccount ua where ua.username like :username and ua.status = :status order by ua.id asc")
                .setParameter("username", "%" + username + "%")
                .setParameter("status", status)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAccount> getUserListWithFilterUsernameAndStatusAndRole(int page, String username, String status, String roleName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ua from UserAccount ua where ua.username like :username and ua.status = :status and ua.userRole.name = :roleName order by ua.id asc")
                .setParameter("username", "%" + username + "%")
                .setParameter("status", status)
                .setParameter("roleName", roleName)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountUserListWithFilterUsernameAndStatusAndRole(String username, String status, String roleName) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(ua.id) from UserAccount ua where ua.username like :username and ua.status = :status and ua.userRole.name = :roleName")
                .setParameter("username", "%" + username + "%")
                .setParameter("status", status)
                .setParameter("roleName", roleName)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAccount> getAllUsers(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ua from UserAccount ua order by ua.id asc")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountUserListWithFilterUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(ua.id) from UserAccount ua where ua.username like :username")
                .setParameter("username", "%" + username + "%")
                .uniqueResult();

    }

    @Override
    public Long getCountUserListWithFilterStatus(String status) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(ua.id) from UserAccount ua where ua.status = :status")
                .setParameter("status", status)
                .uniqueResult();
    }

    @Override
    public Long getCountUserListWithFilterUsernameAndStatus(String username, String status) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(ua.id) from UserAccount ua where ua.username like :username and ua.status = :status")
                .setParameter("username", "%" + username + "%")
                .setParameter("status", status)
                .uniqueResult();
    }

    @Override
    public Long getCountAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(ua.id) from UserAccount ua")
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAccount> getUserListWithFilterUsernameAndRole(int page, String username, String roleName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ua from UserAccount ua where ua.username like :username and ua.userRole.name = :roleName order by ua.id asc")
                .setParameter("username", "%" + username + "%")
                .setParameter("roleName", roleName)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountUserListWithFilterUsernameAndRole(String username, String roleName) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(ua.id) from UserAccount ua where ua.username like :username and ua.userRole.name = :roleName")
                .setParameter("username", "%" + username + "%")
                .setParameter("roleName", roleName)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAccount> getUserListWithFilterStatusAndRole(int page, String status, String roleName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ua from UserAccount ua where ua.status = :status and ua.userRole.name = :roleName order by ua.id asc")
                .setParameter("status", status)
                .setParameter("roleName", roleName)
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public Long getCountUserListWithFilterStatusAndRole(String status, String roleName) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(ua.id) from UserAccount ua where ua.status = :status and ua.userRole.name = :roleName")
                .setParameter("status", status)
                .setParameter("roleName", roleName)
                .uniqueResult();
    }
}
