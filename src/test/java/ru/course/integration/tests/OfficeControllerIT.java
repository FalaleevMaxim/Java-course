package ru.course.integration.tests;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;
import ru.course.integration.dto.ResponseDto;
import ru.course.integration.dto.SuccessDto;
import ru.course.integration.dto.office.OfficeCreateDto;
import ru.course.integration.dto.office.OfficeDto;
import ru.course.integration.dto.office.OfficeFilterDto;
import ru.course.integration.dto.office.OfficeListItemDto;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class OfficeControllerIT extends AbstractIT {
    @Test
    public void testGet() {
        ResponseEntity<String> response = get("/api/office/1");
        ResponseDto<OfficeDto> dto = reader.read(response.getBody(), OfficeDto.class);
        assertOk(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        OfficeDto office = dto.data;
        assertEquals(office.id, Integer.valueOf(1), "Wrong office id");
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity<String> response = get("/api/office/200");
        ResponseDto<OfficeDto> dto = reader.read(response.getBody(), OfficeDto.class);
        assertError(dto);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND, "Wrong code. Error: " + dto.error + ". ");
    }

    @Test
    public void testOrgIdFilterOk() {
        OfficeFilterDto filter = new OfficeFilterDto();
        filter.orgId = 3;
        testFilterOk(filter, 3);
    }

    @Test
    public void testNameOrgIdFilterOk() {
        OfficeFilterDto filter = new OfficeFilterDto();
        filter.orgId = 3;
        filter.name = "Filter3";
        testFilterOk(filter, 1);
    }

    private void testFilterOk(OfficeFilterDto filter, int resultSize) {
        ResponseEntity<String> response = post("/api/office/list", filter);
        ResponseDto<List<OfficeListItemDto>> dto = reader.readCollection(response.getBody(), OfficeListItemDto.class);
        assertOk(dto);
        assertEquals(dto.data.size(), resultSize, "Wrong number of list elements");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testSaveOk() {
        OfficeCreateDto office = new OfficeCreateDto();
        office.name = "OficeSave";
        office.orgId = 4;
        office.phone = "12-34-56";
        office.address = "Address";
        ResponseEntity<String> response = post("/api/office/save", office);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertSuccess(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testSaveNoName() {
        OfficeCreateDto office = new OfficeCreateDto();
        office.orgId = 4;
        office.phone = "12-34-56";
        office.address = "Address";
        ResponseEntity<String> response = post("/api/office/save", office);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertError(dto);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testSaveGet() {
        ResponseEntity<String> response = get("/api/office/save");
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertError(dto);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateOk() {
        OfficeDto office = new OfficeDto();
        office.id = 2;
        office.name = "UpdatedOffice";
        office.orgId = 5;
        office.phone = "65-43-25";
        office.address = "New address";
        ResponseEntity<String> response = post("/api/office/update", office);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertSuccess(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUpdateNoId() {
        OfficeCreateDto office = new OfficeCreateDto();
        office.name = "UpdatedOffice";
        office.orgId = 3;
        office.phone = "65-43-25";
        office.address = "New address";
        ResponseEntity<String> response = post("/api/office/update", office);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertError(dto);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY, "Wrong code. Error: " + dto.error + ". ");
    }
}
