package ru.course.dto.organization;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Ignores id for update.
 */
@JsonIgnoreProperties("id")
public class OrganizationCreateDto extends OrganizationDto {
}
