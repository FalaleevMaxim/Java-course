package ru.course.dto.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class OrganizationDto extends OrganizationCreateDto {
    @NotNull
    @JsonProperty(required = true)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
