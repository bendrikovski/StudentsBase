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
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class StudentsBaseApplication {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    HostelRepository hostelRepository;

    public static void main(String[] args) {
        SpringApplication.run(StudentsBaseApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(CourseRepository courseRepository, HostelRepository hostelRepository, StudentRepository studentRepository) {
        return args -> {
            Hostel hostel = new Hostel("Botanicheskaya", "12/2", "PUNK");
            hostelRepository.save(hostel);

            Student student1 = new Student("Ben", "Fisher", 15, hostel, 4);
            Student student2 = new Student("Jack", "Walker", 15, hostel, 4);
            studentRepository.save(student1);
            studentRepository.save(student2);

            // create 4 courses
            Course course1 = new Course("Machine Learning", "Vlasov Daniel");
            Course course2 = new Course("Database Systems", "Kremlev Michael");
            Course course3 = new Course("Web Basics", "Pushkin Alex");
            Course course4 = new Course("Web Basics", "Kovalchuk Ilya");

            // save courses
            courseRepository.saveAll(Arrays.asList(course1, course2, course3, course4));

            // add courses to the student
            student1.getCourses().addAll(Arrays.asList(course1, course2, course3, course4));
            student2.getCourses().add(course3);


            // update the student
            studentRepository.save(student1);
            studentRepository.save(student2);
        };

    }
}
