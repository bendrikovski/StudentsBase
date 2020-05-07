package com.ben.StudentsBase.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"courses", "hostel"})
@NoArgsConstructor
@AllArgsConstructor

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int roomNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hostel_id", nullable = false)
    private Hostel hostel;

    private int yearOfLearning;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "students_courses",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id",
                    nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id",
                    nullable = false, updatable = false))
    private Set<Course> courses = new HashSet<>();

    public Student(String name, String surname, int roomNumber, Hostel hostel, int yearOfLearning) {
        this.name = name;
        this.surname = surname;
        this.roomNumber = roomNumber;
        this.hostel = hostel;
        this.yearOfLearning = yearOfLearning;
    }

}
