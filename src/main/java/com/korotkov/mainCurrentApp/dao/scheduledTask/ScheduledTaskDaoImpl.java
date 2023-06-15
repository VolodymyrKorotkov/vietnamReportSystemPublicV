package com.korotkov.mainCurrentApp.dao.scheduledTask;

import com.korotkov.mainCurrentApp.model.ScheduledTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduledTaskDaoImpl implements ScheduledTaskDao{
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(ScheduledTask scheduledTask) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(scheduledTask);
    }

    @Override
    public void update(ScheduledTask scheduledTask) {
        Session session = sessionFactory.getCurrentSession();
        session.update(scheduledTask);
    }

    @Override
    public ScheduledTask getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(ScheduledTask.class, id);
    }

    @Override
    public Long getCountAllScheduledTasks() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(st.id) from ScheduledTask st")
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScheduledTask> getAllScheduledTasks(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select st from ScheduledTask st order by st.id desc")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }
}
