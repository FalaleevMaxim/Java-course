package ru.course.service.organization;

import ru.course.dto.organization.OrganizationCreateDto;
import ru.course.dto.organization.OrganizationDto;
import ru.course.dto.organization.OrganizationFilterDto;
import ru.course.dto.organization.OrganizationListItemDto;

import java.util.List;

public interface OrganizationService {
    List<OrganizationListItemDto> filter(OrganizationFilterDto filter);
    OrganizationDto get(int id);
    void save(OrganizationCreateDto dto);
    void update(OrganizationDto dto);
}
