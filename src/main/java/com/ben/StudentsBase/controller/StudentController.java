package com.ben.StudentsBase.controller;

import com.ben.StudentsBase.model.Student;
import com.ben.StudentsBase.service.StudentService;
import com.ben.StudentsBase.vo.CourseView;
import com.ben.StudentsBase.vo.StudentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    List<StudentView> getAllStudents() {
        return studentService.findAllViews();
    }

    @GetMapping("/students/{studentId}")
    StudentView getStudentById(@PathVariable Long studentId) {
        return studentService.findViewById(studentId).get();
    }

    @PostMapping("/students")
    void addStudent(@RequestBody StudentView studentView) {
        studentService.saveView(studentView);
    }
}