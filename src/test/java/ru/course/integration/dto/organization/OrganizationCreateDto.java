package ru.course.integration.dto.organization;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("id")
public class OrganizationCreateDto extends OrganizationDto {
}
