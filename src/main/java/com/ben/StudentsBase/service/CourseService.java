package com.ben.StudentsBase.service;

import com.ben.StudentsBase.exception.RecordNotFoundException;
import com.ben.StudentsBase.model.Course;
import com.ben.StudentsBase.repository.CourseRepository;
import com.ben.StudentsBase.vo.CourseView;
import com.ben.StudentsBase.vo.StudentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service("courseService")
public class CourseService extends AbstractService<CourseRepository, Course, CourseView> {

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        super(courseRepository, CourseView::fromModel, CourseView::toModel);
    }

    public List<CourseView> findByDiscipline(String discipline) {
        List<Course> byDiscipline = getRepository()
                .findByDiscipline(discipline);
        if (byDiscipline == null || byDiscipline.isEmpty()) {
            throw new RecordNotFoundException("Discipline not found");
        }
        return byDiscipline.stream().map(CourseView::fromModel).collect(Collectors.toList());
    }

    public List<StudentView> getCourseSubscribersById(Long disciplineId) {
        return getRepository()
                .findById(disciplineId)
                .orElseThrow(() -> new RecordNotFoundException("Course not found"))
                .getStudents().stream().map(StudentView::fromModel).collect(Collectors.toList());
    }


    public Course updateView(Long id, CourseView view) {

        Course course = getRepository().findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        course.setProfessorName(view.getProfessorName());
        course.setDiscipline(view.getDiscipline());
        return getRepository().save(course);
    }
}
