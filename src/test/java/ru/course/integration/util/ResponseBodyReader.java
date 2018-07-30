package ru.course.integration.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.course.dto.organization.OrganizationDto;
import ru.course.integration.dto.DataDto;
import ru.course.integration.dto.ErrorDto;
import ru.course.integration.dto.ResponseDto;

import java.io.IOException;

import static org.junit.Assert.fail;

public class ResponseBodyReader {
    private ObjectMapper mapper = new ObjectMapper();

    public <T> ResponseDto<T> read(String body, Class<T> tClass){
        try {
            DataDto<T> data = mapper.readValue(body,
                    mapper.getTypeFactory().constructParametricType(DataDto.class, tClass));
            return new ResponseDto<>(data.data);
        } catch (IOException e) {
            return readError(body);
        }
    }

    private <T> ResponseDto<T> readError(String body){
        try {
            ErrorDto dto = mapper.readValue(body, ErrorDto.class);
            return new ResponseDto<>(dto.error);
        } catch (IOException e) {
            return null;
        }
    }
}
