package ru.course.dto.office;

import javax.validation.constraints.NotNull;

public class OfficeDto extends OfficeCreateDto {
    @NotNull
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
