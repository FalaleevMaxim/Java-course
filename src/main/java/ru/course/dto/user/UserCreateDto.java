package ru.course.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"id", "docName"})
public class UserCreateDto extends UserDto {
}
