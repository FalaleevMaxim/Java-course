package ru.course.integration.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.course.dto.organization.OrganizationDto;
import ru.course.integration.dto.DataDto;
import ru.course.integration.dto.ErrorDto;
import ru.course.integration.dto.ResponseDto;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Читает объект из строкового тела ответа
 */
public class ResponseBodyReader {
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Читает объект, содержащий в data объект указанного типа
     * @param body тело ответа
     * @param tClass класс поля data
     * @param <T> класс поля data
     * @return ResponseDto, содержащий объект в data, или ошибку в error. null, если тело имеет неправильный формат.
     */
    public <T> ResponseDto<T> read(String body, Class<T> tClass){
        try {
            DataDto<T> data = mapper.readValue(body,
                    mapper.getTypeFactory().constructParametricType(DataDto.class, tClass));
            return new ResponseDto<>(data.data);
        } catch (IOException e) {
            return readError(body);
        }
    }

    /**
     * Читает объект, содержащий в data коллекцию объектов указанного типа
     * @param body тело ответа
     * @param tClass класс элемента коллекции в поле data
     * @param <T> класс элемента коллекции в поле data
     * @return ResponseDto, содержащий список объектов в data, или ошибку в error. null, если тело имеет неправильный формат.
     */
    public <T> ResponseDto<List<T>> readCollection(String body, Class<T> tClass){
        try {
            DataDto<List<T>> data = mapper.readValue(body,
                    mapper.getTypeFactory().constructParametricType(DataDto.class,
                            mapper.getTypeFactory().constructCollectionType(List.class,
                                    tClass)));
            return new ResponseDto<>(data.data);
        } catch (IOException e) {
            return readError(body);
        }
    }

    /**
     * Используется внутри {@link #read(String, Class)} и {@link #readCollection(String, Class)} для чтения объекта с полем error.
     * @param body тело ответа
     * @param <T> класс поля data
     * @return ResponseDto, содержащий ошибку в error. null, если тело имеет неправильный формат.
     */
    private <T> ResponseDto<T> readError(String body){
        try {
            ErrorDto dto = mapper.readValue(body, ErrorDto.class);
            return new ResponseDto<>(dto.error);
        } catch (IOException e) {
            return null;
        }
    }
}
