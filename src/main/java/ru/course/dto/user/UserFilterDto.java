package ru.course.dto.user;

import javax.validation.constraints.NotNull;

public class UserFilterDto {
    @NotNull
    public Integer officeId;
    public String firstName;
    public String lastName;
    public String middleName;
    public String position;
    public String phone;
    public Integer citizenshipCode;
}
