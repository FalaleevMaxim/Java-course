package ru.course.integration.tests;

import org.springframework.boot.test.web.client.TestRestTemplate;
import ru.course.integration.util.ResponseBodyReader;

public class AbstractIT {
    protected String port = "8080";
    protected TestRestTemplate restTemplate = new TestRestTemplate();
    protected ResponseBodyReader reader = new ResponseBodyReader();
}
