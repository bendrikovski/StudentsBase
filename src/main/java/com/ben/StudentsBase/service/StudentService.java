package com.ben.StudentsBase.service;

import com.ben.StudentsBase.exception.RecordNotFoundException;
import com.ben.StudentsBase.model.Course;
import com.ben.StudentsBase.model.Hostel;
import com.ben.StudentsBase.model.Student;
import com.ben.StudentsBase.repository.StudentRepository;
import com.ben.StudentsBase.vo.StudentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service("studentService")
public class StudentService extends AbstractService<StudentRepository, Student, StudentView> {

    private final CourseService courseService;
    private final HostelService hostelService;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseService courseService, HostelService hostelService) {
        super(studentRepository, StudentView::fromModel, StudentView::toModel);
        this.courseService = courseService;
        this.hostelService = hostelService;
    }

    @Override
    public Student saveView(StudentView view) {
        Set<Course> collect = view.getCourses().stream()
                .map(courseView -> courseService.findById(courseView.getId()))
                .collect(Collectors.toSet());
        Student student = StudentView.toModel(view);
        student.setCourses(collect);
        return super.save(student);
    }

    public Student updateStudent(Long studentId, StudentView studentView) {

        Student student = getRepository().findById(studentId)
                .orElseThrow(() -> new RecordNotFoundException(studentId));

        Hostel hostel = hostelService.getRepository().findById(studentView.getHostel().getId())
                .orElseThrow(() -> new RecordNotFoundException(studentView.getHostel().getId()));

        student.setName(studentView.getName());
        student.setSurname(studentView.getSurname());
        student.setHostel(hostel);
        student.setRoomNumber(studentView.getRoomNumber());
        student.setYearOfLearning(studentView.getYearOfLearning());
        return getRepository().save(student);
    }
}
