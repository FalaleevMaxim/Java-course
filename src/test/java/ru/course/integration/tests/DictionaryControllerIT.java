package ru.course.integration.tests;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;
import ru.course.integration.dto.ResponseDto;
import ru.course.integration.dto.dictionaty.CountryDto;
import ru.course.integration.dto.dictionaty.DocumentDto;

import java.util.List;

import static org.testng.Assert.*;

public class DictionaryControllerIT extends AbstractIT{
    private final int DOCS_COUNT = 2;
    private final int COUNTRIES_COUNT = 3;

    @Test
    public void testGetDocs(){
        ResponseEntity<String> response = get("/api/docs");
        ResponseDto<List<DocumentDto>> dto = reader.readCollection(response.getBody(), DocumentDto.class);
        assertOk(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(dto.data.size(), DOCS_COUNT, "Wrong document list size. ");
        for (DocumentDto doc : dto.data) {
            assertNotNull(doc.code, "Document code is null. ");
            assertNotNull(doc.name, "Document name is null. ");
        }
    }

    @Test
    public void testGetCountries(){
        ResponseEntity<String> response = get("/api/countries");
        ResponseDto<List<CountryDto>> dto = reader.readCollection(response.getBody(), CountryDto.class);
        assertOk(dto);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(dto.data.size(), COUNTRIES_COUNT, "Wrong country list size. ");
        for (CountryDto country : dto.data) {
            assertNotNull(country.code, "Document code is null. ");
            assertNotNull(country.name, "Document name is null. ");
        }
    }
}
