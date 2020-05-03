package com.ben.StudentsBase.vo;

import com.ben.StudentsBase.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseView {
    private String discipline;

    public static CourseView fromModel(Course course) {
        return new CourseView(
                course.getDiscipline());
    }

    public static Course toModel(CourseView courseView) {
        return new Course(
                courseView.getDiscipline());
    }
}
