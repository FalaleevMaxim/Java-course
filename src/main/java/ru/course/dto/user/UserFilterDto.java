package ru.course.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserFilterDto {
    @JsonProperty(required = true)
    public int officeId;
    public String firstName;
    public String lastName;
    public String middleName;
    public String position;
    public String phone;
    public Integer docCode;
    public Integer citizenshipCode;
}
