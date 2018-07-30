package ru.course.integration.tests;

import org.springframework.http.*;
import org.testng.annotations.Test;
import ru.course.integration.dto.organization.OrganizationDto;
import ru.course.integration.dto.ResponseDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class OrganizationControllerIT extends AbstractIT {

    @Test
    public void testGet(){
        ResponseEntity<String> response =
                restTemplate.exchange(
                        "http://localhost:"+port+"/api/organization/1",
                        HttpMethod.GET,
                        new HttpEntity<String>(null, new HttpHeaders()),
                        String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponseDto<OrganizationDto> dto = reader.read(response.getBody(), OrganizationDto.class);
        assertNotNull("Error reading response body", dto);
        assertNull("Error dto:"+dto.error, dto.error);
        OrganizationDto org = dto.data;
        assertEquals("Wrong organization id", 1, org.id);
    }

    @Test
    public void testGetNotFound(){
        ResponseEntity<String> response =
                restTemplate.exchange(
                        "http://localhost:"+port+"/api/organization/2",
                        HttpMethod.GET,
                        new HttpEntity<String>(null, new HttpHeaders()),
                        String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
