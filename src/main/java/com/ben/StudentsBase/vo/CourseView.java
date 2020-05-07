package com.ben.StudentsBase.vo;

import com.ben.StudentsBase.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseView {
    private Long id;
    @NotBlank(message = "Please provide the discipline name")
    private String discipline;
    @NotBlank(message = "Please provide the professor name")
    private String professorName;

    public static CourseView fromModel(Course course) {
        return new CourseView(
                course.getId(),
                course.getDiscipline(),
                course.getProfessorName()
        );
    }

    public static Course toModel(CourseView courseView) {
        return new Course(
                courseView.getDiscipline(),
                courseView.getProfessorName()
        );
    }
}
