package com.ben.StudentsBase.controller;

import com.ben.StudentsBase.StudentsBaseApplication;
import com.ben.StudentsBase.model.Hostel;
import com.ben.StudentsBase.service.HostelService;
import com.ben.StudentsBase.vo.HostelView;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentsBaseApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HostelControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private HostelService hostelService;

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    private String getRootUrl() {
        return "http://localhost:" + port + "/hostels/";
    }

    @Test
    void getAllHostels() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "password")
                .exchange(getRootUrl(),
                        HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
        List<Hostel> hostels = Arrays.asList(objectMapper.readValue(response.getBody(), Hostel[].class));
        assertNotNull(hostels);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getHostelById() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "password")
                .exchange(getRootUrl(),
                        HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
        List<Hostel> hostels = Arrays.asList(objectMapper.readValue(response.getBody(), Hostel[].class));

        System.out.println("getHostles");
        System.out.println(hostels);
        int id = 1;


        ResponseEntity<String> responseUnauthorized = restTemplate
                .exchange(getRootUrl() + id,
                        HttpMethod.GET, entity, String.class);

        assertEquals(responseUnauthorized.getStatusCode(), HttpStatus.UNAUTHORIZED);

        ResponseEntity<String> responseUser = restTemplate
                .withBasicAuth("user", "password")
                .exchange(getRootUrl() + id,
                        HttpMethod.GET, entity, String.class);

        assertEquals(responseUser.getStatusCode(), HttpStatus.OK);

        Hostel hostel = objectMapper.readValue(responseUser.getBody(), Hostel.class);
        assertNotNull(hostel);
    }

    @Test
    void createHostel() {
        Hostel hostel = new Hostel("Koralestroiteley", "20/1", "VUNK");
        HostelView hostelView = HostelView.fromModel(hostel);
        ResponseEntity<Hostel> responseUser = restTemplate
                .withBasicAuth("user", "password")
                .postForEntity(getRootUrl(), hostelView, Hostel.class);

        assertEquals(responseUser.getStatusCode(), HttpStatus.FORBIDDEN);

        ResponseEntity<Hostel> postResponse = restTemplate
                .withBasicAuth("admin", "password")
                .postForEntity(getRootUrl(), hostelView, Hostel.class);

        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void updateHostel() {
        int id = 1;
        final String newStreet = "Pushkina";

        TestRestTemplate testRestTemplate = restTemplate
                .withBasicAuth("admin", "password");

        Hostel hostel = testRestTemplate
                .getForObject(getRootUrl() + id, Hostel.class);

        hostel.setStreet(newStreet);

        testRestTemplate.put(getRootUrl() + id, HostelView.fromModel(hostel));

        Hostel updatedHostel = testRestTemplate.getForObject(getRootUrl() + id, Hostel.class);

        assertNotNull(updatedHostel);
        assertEquals(newStreet, updatedHostel.getStreet());
    }

    @Test
    void deleteHostel() {
        Long id = 100500L;
        hostelService.save(new Hostel(id, "", "", "", null));

        TestRestTemplate testRestTemplate = restTemplate
                .withBasicAuth("admin", "password");

        Hostel hostel = testRestTemplate
                .getForObject(getRootUrl() + id, Hostel.class);

        assertNotNull(hostel);
        testRestTemplate.delete(getRootUrl() + id);
        try {
            testRestTemplate.getForObject(getRootUrl() + id, Hostel.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}