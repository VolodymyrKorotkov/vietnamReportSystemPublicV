package com.korotkov.mainCurrentApp.dao.modelPlan;

import com.korotkov.mainCurrentApp.model.ModelPlan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ModelPlanDaoImpl implements ModelPlanDao {
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("sessionFactoryMain")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(ModelPlan modelPlan) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(modelPlan);
    }

    @Override
    public void update(ModelPlan modelPlan) {
        Session session = sessionFactory.getCurrentSession();
        session.update(modelPlan);
    }

    @Override
    public ModelPlan getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(ModelPlan.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ModelPlan> getAllModelPlans(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select mp from ModelPlan mp order by mp.dateMonth desc")
                .setFirstResult(10 * (page - 1))
                .setMaxResults(10)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ModelPlan> getModelPlansWithDates(LocalDate dateFrom, LocalDate dateTo) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select mp from ModelPlan mp where mp.dateMonth >= :dateMonthFrom and mp.dateMonth <= :dateMonthTo order by mp.dateMonth asc")
                .setParameter("dateMonthFrom",
                        dateFrom.withDayOfMonth(dateFrom.getMonth().length(dateFrom.isLeapYear())))
                .setParameter("dateMonthTo",
                        dateTo.withDayOfMonth(dateTo.getMonth().length(dateTo.isLeapYear())))
                .list();
    }

    @Override
    public Long getCountAllModelPlans() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery("select count(mp.id) from ModelPlan mp")
                .uniqueResult();
    }

    @Override
    public ModelPlan getByDateMonth(LocalDate date) {
        Session session = sessionFactory.getCurrentSession();
        return (ModelPlan) session.createQuery("select mp from ModelPlan mp where mp.dateMonth = :date")
                .setParameter("date", date)
                .uniqueResult();
    }
}
