package com.ben.StudentsBase.controller;

import com.ben.StudentsBase.StudentsBaseApplication;
import com.ben.StudentsBase.model.Course;
import com.ben.StudentsBase.service.CourseService;
import com.ben.StudentsBase.vo.CourseView;
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
class CourseControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CourseService courseService;

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    private String getRootUrl() {
        return "http://localhost:" + port + "/courses/";
    }

    @Test
    void getAllCourses() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "password")
                .exchange(getRootUrl(),
                        HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
        List<Course> courses = Arrays.asList(objectMapper.readValue(response.getBody(), Course[].class));
        assertNotNull(courses);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getCourseById() throws JsonProcessingException {
        int id = 1;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseUnauthorized = restTemplate
                .exchange(getRootUrl() + 1,
                        HttpMethod.GET, entity, String.class);

        assertEquals(responseUnauthorized.getStatusCode(), HttpStatus.UNAUTHORIZED);

        ResponseEntity<String> responseUser = restTemplate
                .withBasicAuth("user", "password")
                .exchange(getRootUrl() + 1,
                        HttpMethod.GET, entity, String.class);

        assertEquals(responseUser.getStatusCode(), HttpStatus.OK);

        Course course = objectMapper.readValue(responseUser.getBody(), Course.class);
        assertNotNull(course);
    }

    @Test
    void getCourseSubscribersById() throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String url = getRootUrl() + "subscribers/1";
        ResponseEntity<String> responseUnauthorized = restTemplate
                .exchange(url, HttpMethod.GET, entity, String.class);
        assertEquals(responseUnauthorized.getStatusCode(), HttpStatus.UNAUTHORIZED);

        ResponseEntity<String> responseUser = restTemplate
                .withBasicAuth("user", "password")
                .exchange(url, HttpMethod.GET, entity, String.class);
        assertEquals(responseUser.getStatusCode(), HttpStatus.FORBIDDEN);

        ResponseEntity<String> responseAdmin = restTemplate
                .withBasicAuth("admin", "password")
                .exchange(url, HttpMethod.GET, entity, String.class);

        assertNotNull(responseAdmin.getBody());
        List<StudentView> courses = Arrays.asList(objectMapper.readValue(responseAdmin.getBody(), StudentView[].class));
        assertNotNull(courses);
        assertEquals(responseAdmin.getStatusCode(), HttpStatus.OK);


    }

    @Test
    void getCoursesByDisciplineName() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        String url = getRootUrl() + "discipline?discipline=Web Basics";

        ResponseEntity<String> responseUnauthorized = restTemplate
                .exchange(url, HttpMethod.GET, entity, String.class);

        assertEquals(responseUnauthorized.getStatusCode(), HttpStatus.UNAUTHORIZED);

        ResponseEntity<String> responseUser = restTemplate
                .withBasicAuth("user", "password")
                .exchange(url, HttpMethod.GET, entity, String.class);

        List<CourseView> courses = Arrays.asList(objectMapper.readValue(responseUser.getBody(), CourseView[].class));
        assertNotNull(courses);
        assertEquals(responseUser.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void createCourse() {
        Course course = new Course("Statistics", "Kulik Ilya");
        CourseView courseView = CourseView.fromModel(course);
        ResponseEntity<Course> responseUser = restTemplate
                .withBasicAuth("user", "password")
                .postForEntity(getRootUrl(), courseView, Course.class);

        assertEquals(responseUser.getStatusCode(), HttpStatus.FORBIDDEN);

        ResponseEntity<Course> postResponse = restTemplate
                .withBasicAuth("admin", "password")
                .postForEntity(getRootUrl(), courseView, Course.class);

        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void updateCourse() {
        int id = 1;
        final String newName = "Kulik Ilya";

        TestRestTemplate testRestTemplate = restTemplate
                .withBasicAuth("admin", "password");

        Course course = testRestTemplate
                .getForObject(getRootUrl() + id, Course.class);

        course.setProfessorName(newName);

        testRestTemplate.put(getRootUrl() + id, course);

        Course updatedCourse = testRestTemplate.getForObject(getRootUrl() + id, Course.class);

        assertNotNull(updatedCourse);
        assertEquals(newName, updatedCourse.getProfessorName());
    }

    @Test
    void deleteCourse() {
        Long id = 100500L;
        courseService.save(new Course(id, "", "", new HashSet<>()));

        TestRestTemplate testRestTemplate = restTemplate
                .withBasicAuth("admin", "password");

        Course course = testRestTemplate
                .getForObject(getRootUrl() + id, Course.class);

        assertNotNull(course);
        testRestTemplate.delete(getRootUrl() + id);
        try {
            testRestTemplate.getForObject(getRootUrl() + id, Course.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}