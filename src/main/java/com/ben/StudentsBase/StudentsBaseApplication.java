package com.ben.StudentsBase;

import com.ben.StudentsBase.model.Course;
import com.ben.StudentsBase.model.Hostel;
import com.ben.StudentsBase.model.Student;
import com.ben.StudentsBase.repository.CourseRepository;
import com.ben.StudentsBase.repository.HostelRepository;
import com.ben.StudentsBase.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class StudentsBaseApplication implements CommandLineRunner {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    HostelRepository hostelRepository;

    public static void main(String[] args) {
        SpringApplication.run(StudentsBaseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Hostel hostel = new Hostel("Botanicheskaya", "12/2", "PUNK");
        hostelRepository.save(hostel);

        Student student = new Student("John", "Doe", 15, hostel, 4);
        studentRepository.save(student);

        // create three courses
        Course course1 = new Course("Machine Learning");
        Course course2 = new Course("Database Systems");
        Course course3 = new Course("Web Basics");

        // save courses
        courseRepository.saveAll(Arrays.asList(course1, course2, course3));

        // add courses to the student
        student.getCourses().addAll(Arrays.asList(course1, course2, course3));

        // update the student
        studentRepository.save(student);
    }

}
