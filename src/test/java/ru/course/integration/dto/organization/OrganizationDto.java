package ru.course.integration.dto.organization;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationDto extends OrganizationCreateDto{
    public Integer id;
}