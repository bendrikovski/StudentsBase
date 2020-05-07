package com.ben.StudentsBase.controller;

import com.ben.StudentsBase.model.Student;
import com.ben.StudentsBase.service.StudentService;
import com.ben.StudentsBase.vo.StudentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
public class StudentController {

    final private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //READ
    @GetMapping("/students")
    List<StudentView> getAllStudents() {
        return studentService.findAllViews();
    }

    //READ
    @GetMapping("/students/{studentId}")
    StudentView getStudentById(@PathVariable Long studentId) {
        return studentService.findViewById(studentId);
    }

    //CREATE
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/students")
    Student createStudent(@Valid @RequestBody StudentView studentView) {
        return studentService.saveView(studentView);
    }

    //UPDATE
    @PutMapping("/students/{studentId}")
    Student updateStudent(@PathVariable Long studentId, @RequestBody StudentView studentView) {
        return studentService.updateStudent(studentId, studentView);
    }

    //DELETE
    @DeleteMapping("/students/{studentId}")
    void deleteCourse(@PathVariable Long studentId) {
        studentService.deleteById(studentId);
    }
}