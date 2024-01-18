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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int value;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "id_student")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "id_subject")
    private Subject subject;

    public Grade() {
    }

    public Grade(int value, String comment, Student student, Subject subject) {
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
