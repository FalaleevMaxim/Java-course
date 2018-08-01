package ru.course.integration.tests;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;
import ru.course.integration.dto.ResponseDto;
import ru.course.integration.dto.SuccessDto;
import ru.course.integration.dto.user.*;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class UserControllerIT extends AbstractIT {
    @Test
    public void testGetNoDoc() {
        ResponseEntity<String> response = get("/api/user/1");
        ResponseDto<UserDto> dto = reader.read(response.getBody(), UserDto.class);
        assertOk(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        UserDto user = dto.data;
        assertEquals(user.id, Integer.valueOf(1), "Wrong user id");
        assertNull(user.docCode, "Document code is not null");
        assertNull(user.docName, "Document name is not null");
    }

    @Test(dependsOnMethods = "testGetNoDoc")
    public void testGetWithDoc() {
        ResponseEntity<String> response = get("/api/user/2");
        ResponseDto<UserDto> dto = reader.read(response.getBody(), UserDto.class);
        assertOk(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        UserDto user = dto.data;
        assertEquals(user.id, Integer.valueOf(1), "Wrong user id");
        assertNotNull(user.docCode, "Document code is null");
        assertNotNull(user.docName, "Document name is null");
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity<String> response = get("/api/user/200");
        ResponseDto<UserDto> dto = reader.read(response.getBody(), UserDto.class);
        assertError(dto);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND, "Wrong code. Error: " + dto.error + ". ");
    }

    @Test
    public void testOfficeIdFilterOk() {
        UserFilterDto filter = new UserFilterDto();
        filter.officeId = 4;
        testFilterOk(filter, 3);
    }

    @Test
    public void testFirstNameFilterOk() {
        UserFilterDto filter = new UserFilterDto();
        filter.officeId = 4;
        filter.firstName = "User5";
        testFilterOk(filter, 2);
    }

    @Test
    public void testFirstNameAndPositionFilterOk() {
        UserFilterDto filter = new UserFilterDto();
        filter.officeId = 4;
        filter.firstName = "User5";
        filter.position = "ForFilterTest1";
        testFilterOk(filter, 1);
    }

    private void testFilterOk(UserFilterDto filter, int resultSize) {
        ResponseEntity<String> response = post("/api/user/list", filter);
        ResponseDto<List<UserListItemDto>> dto = reader.readCollection(response.getBody(), UserListItemDto.class);
        assertOk(dto);
        assertEquals(dto.data.size(), resultSize, "Wrong number of list elements");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testSaveOk() {
        UserCreateDto user = new UserCreateDto();
        user.officeId = 3;
        user.firstName = "SaveFirstName";
        user.middleName = "SaveMiddleName";
        user.secondName = "SaveSecondName";
        user.phone = "12-34-56";
        user.citizenshipCode = 643;
        user.position = "SaveUser";
        ResponseEntity<String> response = post("/api/user/save", user);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertSuccess(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testSaveNoName() {
        UserCreateDto user = new UserCreateDto();
        user.officeId = 3;
        user.middleName = "SaveMiddleName";
        user.secondName = "SaveSecondName";
        user.phone = "12-34-56";
        user.citizenshipCode = 643;
        user.position = "SaveUser";
        ResponseEntity<String> response = post("/api/user/save", user);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertError(dto);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY, "Wrong code. Error: " + dto.error + ". ");
    }

    @Test
    public void testSaveGet() {
        ResponseEntity<String> response = get("/api/user/save");
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertError(dto);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateOk() {
        UserUpdateDto user = new UserUpdateDto();
        user.id = 3;
        user.officeId = 3;
        user.firstName = "UpdateFirstName";
        user.middleName = "UpdateMiddleName";
        user.secondName = "UpdateSecondName";
        user.phone = "12-34-56";
        user.citizenshipCode = 643;
        user.position = "UpdateUser";
        ResponseEntity<String> response = post("/api/user/update", user);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertSuccess(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUpdateDocument() {
        UserUpdateDto user = new UserUpdateDto();
        user.id = 3;
        user.officeId = 3;
        user.firstName = "UpdateFirstName";
        user.middleName = "UpdateMiddleName";
        user.secondName = "UpdateSecondName";
        user.phone = "12-34-56";
        user.citizenshipCode = 643;
        user.position = "UpdateUser";
        user.docCode = 10;
        user.docNumber = "12345678";
        user.docDate = "01/08/2018";
        ResponseEntity<String> response = post("/api/user/update", user);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertSuccess(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUpdateNoId() {
        UserCreateDto user = new UserCreateDto();
        user.officeId = 3;
        user.firstName = "UpdateFirstName";
        user.middleName = "UpdateMiddleName";
        user.secondName = "UpdateSecondName";
        user.phone = "12-34-56";
        user.citizenshipCode = 643;
        user.position = "UpdateUser";
        ResponseEntity<String> response = post("/api/user/update", user);
        ResponseDto<SuccessDto> dto = reader.read(response.getBody(), SuccessDto.class);
        assertError(dto);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY, "Wrong code. Error: " + dto.error + ". ");
    }
}
