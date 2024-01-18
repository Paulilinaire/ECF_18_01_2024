package impl;

import dao.IBaseDAO;
import model.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class DepartmentDAOImpl implements IBaseDAO<Department> {

    private final SessionFactory sessionFactory;

    public DepartmentDAOImpl() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Override
    public void add(Department department) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(department);
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
            Department departmentToDelete = session.get(Department.class, id);
            session.delete(departmentToDelete);
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
    public Department getById(int id) {
        Department department = null;
        try (Session session = sessionFactory.openSession()) {
            department = session.get(Department.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public List<Department> getAll() {
        List<Department> departmentList = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Department> departmentQuery = session.createQuery("from Department", Department.class);
            departmentList = departmentQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departmentList;
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}



