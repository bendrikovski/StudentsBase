package com.ben.StudentsBase.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"students"})
@RequiredArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String discipline;
    private String professorName;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();


    public Course(String discipline, String professorName) {
        this.discipline = discipline;
        this.professorName = professorName;
    }
}
