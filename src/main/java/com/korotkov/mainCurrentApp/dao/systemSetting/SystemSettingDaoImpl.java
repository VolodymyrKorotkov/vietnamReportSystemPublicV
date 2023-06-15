package com.korotkov.mainCurrentApp.dao.systemSetting;

import com.korotkov.mainCurrentApp.model.SystemSetting;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SystemSettingDaoImpl implements SystemSettingDao {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(SystemSetting systemSetting) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(systemSetting);
    }

    @Override
    public void update(SystemSetting systemSetting) {
        Session session = sessionFactory.getCurrentSession();
        session.update(systemSetting);
    }

    @Override
    public SystemSetting getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(SystemSetting.class, id);
    }

    @Override
    public Long getCountAllSystemSettingList() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(ss.id) from SystemSetting ss")
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SystemSetting> getAllSystemSettingList(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ss from SystemSetting ss order by ss.id asc")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    public String getSystemSettingValueByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createQuery("select ss.value from SystemSetting ss where ss.title = :title")
                .setParameter("title", title)
                .uniqueResult();
    }
}
