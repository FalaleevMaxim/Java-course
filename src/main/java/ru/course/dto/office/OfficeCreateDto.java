package ru.course.dto.office;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("id")
public class OfficeCreateDto extends OfficeDto {
}
