package ru.course.dto.user;

import javax.validation.constraints.NotBlank;

public class UserCreateDto {
    @NotBlank
    public String firstName;
    public String secondName;
    public String middleName;
    @NotBlank
    public String position;
    public String phone;
    public Integer docCode;
    public String docNumber;
    public String docDate;
    public Integer citizenshipCode;
    public Boolean isIdentified;
    public Integer officeId;
}
