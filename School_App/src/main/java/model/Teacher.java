package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_t", nullable = false)
    private int id;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    private int age;

    private String level;

    private boolean isFormTeacher;

    private boolean isHeadDepartment;

    @ManyToOne
    @JoinColumn(name = "id_dep")
    private Department department;

    @ManyToMany
    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "id_t"),
            inverseJoinColumns = @JoinColumn(name = "id_sub"))
    private List<Subject> subjects =new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "teacher_classroom",
            joinColumns = @JoinColumn(name = "id_t"),
            inverseJoinColumns = @JoinColumn(name = "id_class"))
    private List<Classroom> classrooms = new ArrayList<>();


    public Teacher() {
    }

    public Teacher(String lastName, String firstName, int age, String level, boolean isFormTeacher, boolean isHeadDepartment, Department department) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.level = level;
        this.isFormTeacher = isFormTeacher;
        this.isHeadDepartment = isHeadDepartment;
        this.department = department;
        this.classrooms = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }

    public void addClassroom(Classroom classroom) {
        if (classrooms == null) {
            classrooms = new ArrayList<>();
        }
        classrooms.add(classroom);
    }

    public void addSubject(Subject subject) {
        if (subjects == null) {
            subjects = new ArrayList<>();
        }
        subjects.add(subject);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", isFormTeacher=" + isFormTeacher +
                ", isHeadDepartment=" + isHeadDepartment +
                ", department=" + department +
                '}';
    }
}
