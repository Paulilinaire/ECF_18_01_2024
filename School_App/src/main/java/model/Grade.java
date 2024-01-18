package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "grade")
public class Grade {
    @Id
    private Long id;

    private int value;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "id_grade")
    private Student student;

    public Grade() {
    }
}
