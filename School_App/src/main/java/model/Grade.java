package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grade")
    private int id;

    private BigDecimal value;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "id_s")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "id_sub")
    private Subject subject;

    public Grade() {
    }

    public Grade(BigDecimal value, String comment, Student student, Subject subject) {
        this.value = value;
        this.comment = comment;
        this.student = student;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", value=" + value +
                ", comment='" + comment + '\'' +
                ", student=" + student +
                '}';
    }
}
