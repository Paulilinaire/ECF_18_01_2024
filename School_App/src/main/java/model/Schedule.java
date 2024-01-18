package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sch")
    private int id;

    private LocalDate date;

    private LocalTime hour;

    @ManyToMany
    @JoinTable(name = "subject_schedule",
            joinColumns = @JoinColumn(name = "id_sch"),
            inverseJoinColumns = @JoinColumn(name = "id_sub"))
    private List<Subject> subjects = new ArrayList<>();

    public Schedule() {
    }

    public Schedule(LocalDate date, LocalTime hour, List<Subject> subjects) {
        this.date = date;
        this.hour = hour;
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", date=" + date +
                ", hour=" + hour +
                ", subjects=" + subjects +
                '}';
    }
}
