package com.ben.StudentsBase.controller;

import com.ben.StudentsBase.model.Course;
import com.ben.StudentsBase.model.Student;
import com.ben.StudentsBase.service.CourseService;
import com.ben.StudentsBase.vo.CourseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    List<CourseView> getAllStudents() {
        return courseService.findAllViews();
    }

    @GetMapping("/courses/{courseId}")
    CourseView getStudentById(@PathVariable Long studentId) {
        return courseService.findViewById(studentId).get();
    }

    @PostMapping("/courses")
    void addCourse(@RequestBody CourseView courseView) {
        courseService.saveView(courseView);
    }
}
