package ru.course.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.course.Application;
import ru.course.dto.ResponseDto;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class)
public class OrganizationControllerTest {
    @LocalServerPort
    private String port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testGet(){
        ResponseEntity<ResponseDto> response = restTemplate.getForEntity("http://localhost:"+port+"/api/organization/2", ResponseDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
