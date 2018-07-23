package ru.course.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.course.dto.ResponseDto;

import static ru.course.util.ResponseUtil.errorResponse;

@RestControllerAdvice
public class ControllersAdvice {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseDto<Object>> handleNotFound(NotFoundException e) {
        return errorResponse( e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleException(Exception e) {
        return errorResponse( e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
