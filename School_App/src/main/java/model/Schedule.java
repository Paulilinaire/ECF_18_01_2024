package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalDate hour;

    @ManyToMany
    @JoinTable(name = "subject_schedule",
            joinColumns = @JoinColumn(name = "id_sub"),
            inverseJoinColumns = @JoinColumn(name = "id_sch"))
    private List<Subject> subjects =new ArrayList<>();

    public Schedule() {
    }
}
