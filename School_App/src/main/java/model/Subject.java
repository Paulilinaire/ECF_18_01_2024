package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int duration;

    private int coefficient;

    private String description;

    @ManyToMany(mappedBy = "subjects")
    private List<Schedule> schedules = new ArrayList<>();

    @ManyToMany(mappedBy = "subjects")
    private List<Teacher> teachers = new ArrayList<>();

    @OneToMany (mappedBy = "subject")
    private List<Grade> grades;

    public Subject() {
    }

    public Subject(String title, int duration, int coefficient, String description) {
        this.title = title;
        this.duration = duration;
        this.coefficient = coefficient;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", coefficient=" + coefficient +
                ", description='" + description + '\'' +
                '}';
    }
}
