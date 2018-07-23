package ru.course.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("docName")
public class UserUpdateDto extends UserDto {
}
