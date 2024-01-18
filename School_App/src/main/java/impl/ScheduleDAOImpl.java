package impl;

import dao.IBaseDAO;
import model.Schedule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class ScheduleDAOImpl implements IBaseDAO<Schedule> {

    private final SessionFactory sessionFactory;

    public ScheduleDAOImpl() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Override
    public void add(Schedule schedule) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(schedule);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public Schedule getById(long id) {
        Schedule schedule = null;
        try (Session session = sessionFactory.openSession()) {
            schedule = session.get(Schedule.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedule;
    }

    @Override
    public List<Schedule> getAll() {
        List<Schedule> scheduleList = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Schedule> scheduleQuery = session.createQuery("from Schedule", Schedule.class);
            scheduleList = scheduleQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduleList;
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
