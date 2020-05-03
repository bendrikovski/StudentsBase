package com.ben.StudentsBase.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hostels")
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"students"})
@NoArgsConstructor
@AllArgsConstructor
public class Hostel {
    @Id
    @GeneratedValue
    Long id;

    String street;
    String building;
    String campus;

    @OneToMany(mappedBy = "hostel", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    Set<Student> students = new HashSet<>();

    public Hostel(String street, String building, String campus) {
        this.street = street;
        this.building = building;
        this.campus = campus;
    }
}
