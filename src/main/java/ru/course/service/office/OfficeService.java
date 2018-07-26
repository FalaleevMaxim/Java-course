package ru.course.service.office;

import ru.course.dto.office.OfficeCreateDto;
import ru.course.dto.office.OfficeDto;
import ru.course.dto.office.OfficeFilterDto;
import ru.course.dto.office.OfficeListItemDto;

import java.util.List;

public interface OfficeService {
    List<OfficeListItemDto> filter(OfficeFilterDto filter);
    OfficeDto get(int id);
    void save(OfficeCreateDto dto);
    void update(OfficeDto dto);
}
