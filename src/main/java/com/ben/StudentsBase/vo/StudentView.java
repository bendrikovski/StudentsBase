package com.ben.StudentsBase.vo;

import com.ben.StudentsBase.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentView {
    private Long id;

    @NotBlank(message = "Please provide a name")
    private String name;

    @NotBlank(message = "Please provide a surname")
    private String surname;

    private int roomNumber;
    private HostelView hostel;
    private int yearOfLearning;
    private Set<CourseView> courses;


    public static StudentView fromModel(Student student) {
        return new StudentView(
                student.getId(),
                student.getName(),
                student.getSurname(),
                student.getRoomNumber(),
                HostelView.fromModel(student.getHostel()),
                student.getYearOfLearning(),
                student.getCourses().stream().map(CourseView::fromModel).collect(Collectors.toSet()));
    }

    public static Student toModel(StudentView studentView) {
        return new Student(
                studentView.getId(),
                studentView.getName(),
                studentView.getSurname(),
                studentView.getRoomNumber(),
                HostelView.toModel(studentView.getHostel()),
                studentView.getYearOfLearning(),
                studentView.getCourses().stream().map(CourseView::toModel).collect(Collectors.toSet())
        );
    }
}
