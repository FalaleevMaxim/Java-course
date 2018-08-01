package ru.course.integration.tests;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.course.integration.dto.ResponseDto;
import ru.course.integration.dto.SuccessDto;
import ru.course.integration.util.ResponseBodyReader;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class AbstractIT {
    protected String port = "8080";
    protected TestRestTemplate restTemplate = new TestRestTemplate();
    protected ResponseBodyReader reader = new ResponseBodyReader();
    protected HttpHeaders postHeaders;
    {
        postHeaders = new HttpHeaders();
        postHeaders.add("Content-Type", "application/json");
    }

    protected <T> ResponseEntity<String> post(String uri, T dto){
        return restTemplate.exchange(
                "http://localhost:"+port+uri,
                HttpMethod.POST,
                new HttpEntity<>(dto, postHeaders),
                String.class);
    }

    protected ResponseEntity<String> get(String uri){
        return restTemplate.exchange(
                "http://localhost:"+port+uri,
                HttpMethod.GET,
                new HttpEntity<>(null, postHeaders),
                String.class);
    }

    protected void assertOk(ResponseDto<?> dto){
        assertNotNull(dto, "Wrong format of response body");
        assertNull(dto.error, "Error dto:"+dto.error);
        assertNotNull(dto.data, "Data is null");
    }

    protected void assertSuccess(ResponseDto<SuccessDto> dto){
        assertOk(dto);
        SuccessDto success = dto.data;
        assertEquals(success.result, "success");
    }

    protected void assertError(ResponseDto<?> dto){
        assertNotNull(dto, "Wrong format of response body");
        assertNull(dto.data, "Data must be null when error");
        assertNotNull(dto.error, "Error message is null");
    }
}
