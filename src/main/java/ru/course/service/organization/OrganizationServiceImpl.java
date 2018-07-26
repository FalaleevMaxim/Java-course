package ru.course.service.organization;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.course.dto.organization.OrganizationCreateDto;
import ru.course.dto.organization.OrganizationDto;
import ru.course.dto.organization.OrganizationFilterDto;
import ru.course.dto.organization.OrganizationListItemDto;
import ru.course.model.organization.Organization;
import ru.course.storage.organizations.OrganizationsStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationsStorage storage;
    private final MapperFactory mapperFactory;

    @Autowired
    public OrganizationServiceImpl(OrganizationsStorage storage, MapperFactory mapperFactory) {
        this.storage = storage;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public List<OrganizationListItemDto> filter(OrganizationFilterDto filter) {
        return storage.filter(filter.name, filter.inn, filter.isActive).stream()
                .map(mapperFactory.getMapperFacade(Organization.class, OrganizationListItemDto.class)::map)
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationDto get(int id) {
        Organization org = storage.get(id);
        if (org == null) return null;
        return mapperFactory.getMapperFacade(Organization.class, OrganizationDto.class).map(org);
    }

    @Override
    public void save(OrganizationCreateDto dto) {
        Organization org = mapperFactory.getMapperFacade(OrganizationCreateDto.class, Organization.class).map(dto);
        storage.save(org);
    }

    @Override
    public void update(OrganizationDto dto) {
        Organization org = mapperFactory.getMapperFacade(OrganizationDto.class, Organization.class).map(dto);
        storage.update(org);
    }
}
