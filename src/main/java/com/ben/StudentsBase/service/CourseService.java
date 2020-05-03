package com.ben.StudentsBase.service;

import com.ben.StudentsBase.model.Course;
import com.ben.StudentsBase.repository.CourseRepository;
import com.ben.StudentsBase.vo.CourseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service("courseService")
public class CourseService extends AbstractService<CourseRepository, Course, CourseView> {
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        super(courseRepository, CourseView::fromModel, CourseView::toModel);
    }

    public Course findByDiscipline(String discipline) {
        List<Course> byDiscipline = getRepository().findByDiscipline(discipline);
        if (byDiscipline == null || byDiscipline.size() > 1){
            throw new RuntimeException();
        }
        return byDiscipline.get(0);
    }
}
