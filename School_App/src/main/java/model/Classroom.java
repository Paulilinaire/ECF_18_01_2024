package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "classroom")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_class", nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String level;

    @ManyToOne
    @JoinColumn(name = "id_dep", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "teacher_classroom",
            joinColumns = @JoinColumn(name = "id_class"),
            inverseJoinColumns = @JoinColumn(name = "id_t")
    )
    private List<Teacher> teachers = new ArrayList<>();


    public Classroom() {
    }

    public Classroom(String name, String level, Department department, List<Student> students, List<Teacher> teachers) {
        this.name = name;
        this.level = level;
        this.department = department;
        this.students = students;
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", department=" + department +
                '}';
    }
}
