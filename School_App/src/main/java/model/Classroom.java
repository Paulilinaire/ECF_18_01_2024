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
    @Column(name = "id_class")
    private int id;

    private String name;

    private String level;

    @ManyToOne
    @JoinColumn(name = "id_dep")
    private Department department;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
    private List<Student> studentList = new ArrayList<>();

    @ManyToMany(mappedBy = "classrooms")
    private List<Teacher> teachers = new ArrayList<>();


    public Classroom() {
    }

    public Classroom(String name, String level, Department department) {
        this.name = name;
        this.level = level;
        this.department = department;
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
