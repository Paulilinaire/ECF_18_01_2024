package impl;

import dao.IBaseDAO;
import model.Grade;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class GradeDAOImpl implements IBaseDAO<Grade> {

    private final SessionFactory sessionFactory;

    public GradeDAOImpl() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Override
    public void add(Grade grade) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(grade);
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
    public Grade getById(int id) {
        Grade grade = null;
        try (Session session = sessionFactory.openSession()) {
            grade = session.get(Grade.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grade;
    }

    @Override
    public List<Grade> getAll() {
        List<Grade> gradeList = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Grade> gradeQuery = session.createQuery("from Grade", Grade.class);
            gradeList = gradeQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gradeList;
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
