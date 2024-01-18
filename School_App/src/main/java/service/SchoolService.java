package service;

import dao.IBaseDAO;
import impl.*;
import model.*;

import java.util.List;

public class SchoolService {
        private ClassroomDAOImpl classroomDao;
        private DepartmentDAOImpl departmentDAO;
        private GradeDAOimpl gradeDAO;
        private ScheduleDAOImpl scheduleDAO;
        private StudentDAOImpl studentDAO;
        private SubjectDAOImpl subjectDAO;
        private TeacherDAOImpl teacherDAO;


    public SchoolService(ClassroomDAOImpl classroomDao, DepartmentDAOImpl departmentDAO, GradeDAOimpl gradeDAO, ScheduleDAOImpl scheduleDAO, StudentDAOImpl studentDAO, SubjectDAOImpl subjectDAO, TeacherDAOImpl teacherDAO) {
        this.classroomDao = classroomDao;
        this.departmentDAO = departmentDAO;
        this.gradeDAO = gradeDAO;
        this.scheduleDAO = scheduleDAO;
        this.studentDAO = studentDAO;
        this.subjectDAO = subjectDAO;
        this.teacherDAO = teacherDAO;
    }

    public void addClassroom(Classroom classroom) {
        classroomDao.add(classroom);
    }

    public void deleteClassroom(long id) {
        classroomDao.delete(id);
    }

    public Classroom getClassroomById(Long id) {
        return classroomDao.getById(id);
    }


    public List<Classroom> getAllClassrooms() {
        return classroomDao.getAll();
    }





    public void addDepartment(Department department) {
        departmentDAO.add(department);
    }

    public void deleteDepartment(long id) {
        departmentDAO.delete(id);
    }

    public Department getDepartmentById(Long id) {
        return departmentDAO.getById(id);
    }


    public List<Department> getAllDepartments() {
        return departmentDAO.getAll();
    }







    public void addStudent(Student student) {
        studentDAO.add(student);
    }

    public void deleteStudent(long id) {
        studentDAO.delete(id);
    }

    public Student getStudentById(Long id) {
        return studentDAO.getById(id);
    }


    public List<Student> getAllStudents() {
        return studentDAO.getAll();
    }








    public void addGrade(Grade grade) {
        gradeDAO.add(grade);
    }


    public Grade getGradeById(Long id) {
        return gradeDAO.getById(id);
    }

    public List<Grade> getAllGrades() {
        return gradeDAO.getAll();
    }






    public void addSubject(Subject subject) {
        subjectDAO.add(subject);
    }


    public Subject getSubjectById(Long id) {
        return subjectDAO.getById(id);
    }

    public List<Subject> getAllSubjects() {
        return subjectDAO.getAll();
    }





    public void addSchedule(Schedule schedule) {
        scheduleDAO.add(schedule);
    }


    public Schedule getScheduleById(Long id) {
        return scheduleDAO.getById(id);
    }


    public List<Schedule> getAllSchedules() {
        return scheduleDAO.getAll();
    }







    public void addTeacher(Teacher teacher) {
        teacherDAO.add(teacher);
    }


    public Teacher getTeacherById(Long id) {
        return teacherDAO.getById(id);
    }


    public List<Teacher> getAllTeachers() {
        return teacherDAO.getAll();
    }





    public void close() {
        studentDAO.close();
        scheduleDAO.close();
        classroomDao.close();
        departmentDAO.close();
        gradeDAO.close();
        teacherDAO.close();
        subjectDAO.close();
    }




}

