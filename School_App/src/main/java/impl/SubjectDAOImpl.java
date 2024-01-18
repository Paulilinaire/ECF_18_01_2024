package impl;

import dao.IBaseDAO;
import model.Subject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class SubjectDAOImpl implements IBaseDAO<Subject> {

    private final SessionFactory sessionFactory;

    public SubjectDAOImpl() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Override
    public void add(Subject subject) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(subject);
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
    public Subject getById(int id) {
        Subject subject = null;
        try (Session session = sessionFactory.openSession()) {
            subject = session.get(Subject.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subject;
    }

    @Override
    public List<Subject> getAll() {
        List<Subject> subjectList = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Subject> subjectQuery = session.createQuery("from Subject", Subject.class);
            subjectList = subjectQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjectList;
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
