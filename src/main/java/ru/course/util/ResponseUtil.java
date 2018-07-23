package ru.course.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.course.dto.ResponseDto;

public class ResponseUtil {
    public static <T> ResponseEntity<ResponseDto<T>> errorResponse(String msg, HttpStatus code) {
        return new ResponseEntity<>(new ResponseDto<>(msg), code);
    }

    public static <T> ResponseEntity<ResponseDto<T>> dataResponse(T data) {
        return new ResponseEntity<>(new ResponseDto<>(data), HttpStatus.OK);
    }
}
