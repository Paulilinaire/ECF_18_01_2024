package impl;

import dao.IBaseDAO;
import model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class TeacherDAOImpl implements IBaseDAO<Teacher> {

    private final SessionFactory sessionFactory;

    public TeacherDAOImpl() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Override
    public void add(Teacher teacher) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(teacher);
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
    }

    @Override
    public Teacher getById(int id) {
        Teacher teacher = null;
        try (Session session = sessionFactory.openSession()) {
            teacher = session.get(Teacher.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacher;
    }

    @Override
    public List<Teacher> getAll() {
        List<Teacher> teacherList = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Teacher> teacherQuery = session.createQuery("from Teacher", Teacher.class);
            teacherList = teacherQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacherList;
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
