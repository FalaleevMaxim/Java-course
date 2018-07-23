package ru.course.dto.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class OrganizationFilterDto {
    @NotBlank
    @JsonProperty(required = true)
    public String name;
    @Pattern(regexp = "[0-9]{10}", message = "Inn must be 10 digits")
    public String inn;
    public Boolean isActive;
}