package ru.course.dto.user;

import javax.validation.constraints.NotNull;

public class UserUpdateDto extends UserCreateDto {
    @NotNull
    public Integer id;
}
