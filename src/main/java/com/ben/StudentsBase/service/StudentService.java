package com.ben.StudentsBase.service;

import com.ben.StudentsBase.model.Course;
import com.ben.StudentsBase.model.Student;
import com.ben.StudentsBase.repository.StudentRepository;
import com.ben.StudentsBase.vo.StudentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("studentService")
public class StudentService extends AbstractService<StudentRepository, Student, StudentView> {
    @Autowired
    CourseService courseService;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        super(studentRepository, StudentView::fromModel, StudentView::toModel);
    }

    @Override
    public void saveView(StudentView view) {
        Set<Course> collect = view.getCourses().stream()
                .map(courseView -> courseService.findByDiscipline(courseView.getDiscipline()))
                .collect(Collectors.toSet());
        Student student = StudentView.toModel(view);
        student.setCourses(collect);
        super.save(student);
    }
}
