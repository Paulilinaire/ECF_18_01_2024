package model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 3)
    private String lastName;

    @Column(nullable = false, length = 3)
    private String firstName;

    private LocalDate birthDate;

    private String email;

    @ManyToOne
    @JoinColumn(name = "id_class")
    private Class studentClass;

    @OneToMany (mappedBy = "student")
    private List<Grade> grades;

    public Student() {
    }
}
