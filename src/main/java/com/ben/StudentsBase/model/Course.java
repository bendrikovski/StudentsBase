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
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "discipline", unique = true)
    private String discipline;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    public Course(String discipline) {
        this.discipline = discipline;
    }

}
