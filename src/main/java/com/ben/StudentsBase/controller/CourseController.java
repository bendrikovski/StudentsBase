package com.ben.StudentsBase.controller;

import com.ben.StudentsBase.model.Course;
import com.ben.StudentsBase.service.CourseService;
import com.ben.StudentsBase.vo.CourseView;
import com.ben.StudentsBase.vo.StudentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
public class CourseController {

    final private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //READ
    @GetMapping("/courses")
    List<CourseView> getAllCourses() {
        return courseService.findAllViews();
    }

    //READ
    @GetMapping("/courses/{courseId}")
    CourseView getCourseById(@PathVariable Long courseId) {
        return CourseView.fromModel(courseService.findById(courseId));
    }

    //READ
    @GetMapping("/courses/subscribers/{courseId}")
    List<StudentView> getCourseSubscribersById(@PathVariable Long courseId) {
        return courseService.getCourseSubscribersById(courseId);
    }

    //READ
    @GetMapping("/courses/discipline")
    List<CourseView> getCoursesByDisciplineName(@RequestParam(value = "discipline") String discipline) {
        return courseService.findByDiscipline(discipline);
    }

    //CREATE
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/courses")
    Course createCourse(@Valid @RequestBody CourseView courseView) {
        return courseService.saveView(courseView);
    }

    //UPDATE
    @PutMapping("/courses/{courseId}")
    Course updateCourse(@PathVariable Long courseId, @Valid @RequestBody CourseView courseView) {
        return courseService.updateView(courseId, courseView);
    }

    //DELETE
    @DeleteMapping("/courses/{courseId}")
    void deleteCourse(@PathVariable Long courseId) {
        courseService.deleteById(courseId);
    }
}
