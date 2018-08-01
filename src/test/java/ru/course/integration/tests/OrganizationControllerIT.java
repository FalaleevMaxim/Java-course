package ru.course.integration.tests;

import org.springframework.http.*;
import org.testng.annotations.Test;
import ru.course.integration.dto.ResponseDto;
import ru.course.integration.dto.SuccessDto;
import ru.course.integration.dto.organization.OrganizationCreateDto;
import ru.course.integration.dto.organization.OrganizationDto;
import ru.course.integration.dto.organization.OrganizationFilterDto;
import ru.course.integration.dto.organization.OrganizationListItemDto;

import java.util.List;

import static org.testng.Assert.*;


public class OrganizationControllerIT extends AbstractIT {

    @Test
    public void testGet() {
        ResponseEntity<String> response = get("/api/organization/1");
        ResponseDto<OrganizationDto> dto = reader.read(response.getBody(), OrganizationDto.class);
        assertOk(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        OrganizationDto org = dto.data;
        assertEquals(org.id, Integer.valueOf(1), "Wrong organization id");
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity<String> response = get("/api/organization/200");
        ResponseDto<OrganizationDto> dto = reader.read(response.getBody(), OrganizationDto.class);
        assertError(dto);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND, "Wrong code. Error: " + dto.error + ". ");
    }

    @Test
    public void testNameFilterOk() {
        OrganizationFilterDto filter = new OrganizationFilterDto();
        filter.name = "Filter";
        testFilterOk(filter, 3);
    }

    @Test
    public void testInnFilterOk() {
        OrganizationFilterDto filter = new OrganizationFilterDto();
        filter.name = "Filter";
        filter.inn = "1234567890";
        testFilterOk(filter, 1);
    }

    private void testFilterOk(OrganizationFilterDto filter, int resultSize) {
        ResponseEntity<String> response = post("/api/organization/list", filter);
        ResponseDto<List<OrganizationListItemDto>> dto = reader.readCollection(response.getBody(), OrganizationListItemDto.class);
        assertOk(dto);
        assertEquals(dto.data.size(), resultSize, "Wrong number of list elements");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testSaveOk() {
        OrganizationCreateDto org = new OrganizationCreateDto();
        org.name = "OrgSave";
        org.fullName = "OrganizationSave";
        org.inn = "1527384950";
        org.kpp = "987654321";
        org.phone = "12-34-56";
        org.address = "Address";
        ResponseEntity<String> response = post("/api/organization/save", org);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertSuccess(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testSaveNoName() {
        OrganizationCreateDto org = new OrganizationCreateDto();
        org.fullName = "OrganizationSave";
        org.inn = "1527384950";
        org.kpp = "987654321";
        org.phone = "12-34-56";
        org.address = "Address";
        ResponseEntity<String> response = post("/api/organization/save", org);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertError(dto);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testSaveGet() {
        ResponseEntity<String> response = get("/api/organization/save");
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertError(dto);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateOk() {
        OrganizationDto org = new OrganizationDto();
        org.id = 2;
        org.name = "OrgToUpdate_1";
        org.fullName = "Organization updated";
        org.inn = "1527384951";
        org.kpp = "987654320";
        org.phone = "65-43-21";
        org.address = "New address";
        ResponseEntity<String> response = post("/api/organization/update", org);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertSuccess(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUpdateNoId() {
        OrganizationCreateDto org = new OrganizationCreateDto();
        org.name = "OrgToUpdate_1";
        org.fullName = "Organization updated";
        org.inn = "1527384951";
        org.kpp = "987654320";
        org.phone = "65-43-21";
        org.address = "New address";
        ResponseEntity<String> response = post("/api/organization/update", org);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertError(dto);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY, "Wrong code. Error: " + dto.error + ". ");
    }
}
