package ru.course.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

public class UserDto {
    @JsonProperty(required = true)
    public int id;
    @NotBlank
    @JsonProperty(required = true)
    public String firstName;
    public String secondName;
    public String middleName;
    @NotBlank
    @JsonProperty(required = true)
    public String position;
    public String phone;
    public Integer docCode;
    public String docName;
    public String docNumber;
    public String docDate;
    public Integer citizenshipCode;
    public Boolean isIdentified;
    public Integer officeId;
}
