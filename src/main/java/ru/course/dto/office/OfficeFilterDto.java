package ru.course.dto.office;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OfficeFilterDto {
    @JsonProperty(required = true)
    public int orgId;
    public String name;
    public String phone;
    public Boolean isActive;
}
