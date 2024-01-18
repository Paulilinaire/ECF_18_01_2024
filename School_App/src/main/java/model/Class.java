package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "class")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String level;

    @OneToMany(mappedBy = "class")
    private List<Student> studentList = new ArrayList<>();

    @ManyToMany(mappedBy = "classes")
    private List<Teacher> teachers = new ArrayList<>();

    public Class() {
    }
}
