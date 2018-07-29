package ru.course.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

public class OrganizationControllerIT {
    private String port = "8080";

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGet(){
        ResponseEntity<String> response =
                restTemplate.exchange(
                        "http://localhost:"+port+"/api/organization/2",
                        HttpMethod.GET,
                        new HttpEntity<String>(null, new HttpHeaders()),
                        String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
