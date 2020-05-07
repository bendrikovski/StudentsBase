package com.ben.StudentsBase.controller;

import com.ben.StudentsBase.StudentsBaseApplication;
import com.ben.StudentsBase.model.Hostel;
import com.ben.StudentsBase.model.Student;
import com.ben.StudentsBase.service.HostelService;
import com.ben.StudentsBase.service.StudentService;
import com.ben.StudentsBase.vo.StudentView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentsBaseApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private HostelService hostelService;
    @Autowired
    private StudentService studentService;
    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    private String getRootUrl() {
        return "http://localhost:" + port + "/students/";
    }

    @Test
    void getAllStudents() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("admin", "password")
                .exchange(getRootUrl(),
                        HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
        List<Student> students = Arrays.asList(objectMapper.readValue(response.getBody(), Student[].class));
        assertNotNull(students);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getStudentById() throws JsonProcessingException {
        int id = 1;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseUnauthorized = restTemplate
                .exchange(getRootUrl() + id,
                        HttpMethod.GET, entity, String.class);

        assertEquals(responseUnauthorized.getStatusCode(), HttpStatus.UNAUTHORIZED);

        ResponseEntity<String> responseUser = restTemplate
                .withBasicAuth("admin", "password")
                .exchange(getRootUrl() + id,
                        HttpMethod.GET, entity, String.class);

        assertEquals(responseUser.getStatusCode(), HttpStatus.OK);

        Student student = objectMapper.readValue(responseUser.getBody(), Student.class);
        assertNotNull(student);
    }

    @Test
    void createStudent() {
        Hostel hostel = hostelService.findById(1L);
        Student student = new Student("Ben", "Fisher", 15, hostel, 4);
        StudentView studentView = StudentView.fromModel(student);
        ResponseEntity<Student> responseUser = restTemplate
                .withBasicAuth("user", "password")
                .postForEntity(getRootUrl(), studentView, Student.class);

        assertEquals(responseUser.getStatusCode(), HttpStatus.FORBIDDEN);

        ResponseEntity<Student> postResponse = restTemplate
                .withBasicAuth("admin", "password")
                .postForEntity(getRootUrl(), studentView, Student.class);

        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void updateStudent() {
        int id = 1;
        final String newName = "Ilya";

        TestRestTemplate testRestTemplate = restTemplate
                .withBasicAuth("admin", "password");

        Student student = testRestTemplate
                .getForObject(getRootUrl() + id, Student.class);

        student.setName(newName);

        testRestTemplate.put(getRootUrl() + id, StudentView.fromModel(student));

        Student updatedStudent = testRestTemplate.getForObject(getRootUrl() + id, Student.class);

        assertNotNull(updatedStudent);
        assertEquals(newName, updatedStudent.getName());
    }


    @Test
    void deleteStudent() {
        Long id = 100500L;
        studentService.save(new Student(id, "", "", 5, hostelService.findById(1L), 4, new HashSet<>()));

        TestRestTemplate testRestTemplate = restTemplate
                .withBasicAuth("admin", "password");

        Student student = testRestTemplate
                .getForObject(getRootUrl() + id, Student.class);

        assertNotNull(student);
        testRestTemplate.delete(getRootUrl() + id);
        try {
            testRestTemplate.getForObject(getRootUrl() + id, Student.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}