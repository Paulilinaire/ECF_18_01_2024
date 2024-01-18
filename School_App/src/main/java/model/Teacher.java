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
    private Long id;

    @Column(nullable = false, length = 3)
    private String lastName;

    @Column(nullable = false, length = 3)
    private String firstName;

    private int age;

    private boolean isFormTeacher;

    private boolean isHeadDepartment;

    @ManyToOne
    @JoinColumn(name = "id_dep")
    private Department department;

    @ManyToMany
    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "id_sub"),
            inverseJoinColumns = @JoinColumn(name = "id_t"))
    private List<Subject> subjects =new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "teacher_class",
            joinColumns = @JoinColumn(name = "id_class"),
            inverseJoinColumns = @JoinColumn(name = "id_t"))
    private List<Class> classes =new ArrayList<>();


    public Teacher() {
    }
}
