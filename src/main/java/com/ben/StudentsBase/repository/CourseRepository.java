package com.ben.StudentsBase.repository;

import com.ben.StudentsBase.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("courseRepository")
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByDiscipline(String discipline);
}
