package ru.course.dto.office;

import javax.validation.constraints.NotNull;

public class OfficeFilterDto {
    @NotNull
    public Integer orgId;
    public String name;
    public String phone;
    public Boolean isActive;
}
