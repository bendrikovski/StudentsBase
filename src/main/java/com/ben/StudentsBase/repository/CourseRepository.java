package com.ben.StudentsBase.repository;

import com.ben.StudentsBase.model.Course;
import com.ben.StudentsBase.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {


    List<Course> findByDiscipline(String discipline);
}
