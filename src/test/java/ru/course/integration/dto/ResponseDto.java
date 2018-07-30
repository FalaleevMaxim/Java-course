package ru.course.integration.dto;

public class ResponseDto<T> {
    public T data;
    public String error;

    public ResponseDto(T data) {
        this.data = data;
    }

    public ResponseDto(String error) {
        this.error = error;
    }
}