package impl;

import dao.IBaseDAO;
import model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements IBaseDAO<Student> {

    private final SessionFactory sessionFactory;

    public StudentDAOImpl() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Override
    public void add(Student student) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(student);
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
            Student studentToDelete = session.get(Student.class, id);
            session.delete(studentToDelete);
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
    public Student getById(int id) {
        Student student = null;
        try (Session session = sessionFactory.openSession()) {
            student = session.get(Student.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public List<Student> getAll() {
        List<Student> studentList = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Student> studentQuery = session.createQuery("from Student", Student.class);
            studentList = studentQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public boolean update(Student student) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();

        }
        return false;
    }

    public List<Student> getDepStudents(String departmentName) {
        Session session = null;
        List<Student> studentList = new ArrayList<>();

        try {
            session = sessionFactory.openSession();

            // Assuming department is a property in the Classroom entity
            Query<Student> departmentStudentsQuery = session.createQuery(
                    "select distinct student " +
                            "from Classroom classroom " +
                            "join classroom.students student " +
                            "where classroom.department.name = :departmentName", Student.class
            );
            departmentStudentsQuery.setParameter("departmentName", departmentName);

            studentList = departmentStudentsQuery.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return studentList;
    }




    @Override
    public void close() {
        sessionFactory.close();
    }
}



