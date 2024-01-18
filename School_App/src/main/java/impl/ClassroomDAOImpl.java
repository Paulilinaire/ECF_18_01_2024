package impl;

import dao.IBaseDAO;
import model.Classroom;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class ClassroomDAOImpl implements IBaseDAO<Classroom> {

    private final SessionFactory sessionFactory;

    public ClassroomDAOImpl() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Override
    public void add(Classroom classroom) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(classroom);
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
    public void delete(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Classroom classroomToDelete = session.get(Classroom.class, id);
            session.delete(classroomToDelete);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Classroom getById(int id) {
        Classroom classroom = null;
        try (Session session = sessionFactory.openSession()) {
            classroom = session.get(Classroom.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classroom;
    }

    @Override
    public List<Classroom> getAll() {
        List<Classroom> classroomList = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Classroom> classroomQuery = session.createQuery("from Classroom", Classroom.class);
            classroomList = classroomQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classroomList;
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}



