package ru.course.service.office;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.course.controller.advice.NotFoundException;
import ru.course.dto.office.OfficeCreateDto;
import ru.course.dto.office.OfficeDto;
import ru.course.dto.office.OfficeFilterDto;
import ru.course.dto.office.OfficeListItemDto;
import ru.course.model.office.Office;
import ru.course.storage.office.OfficeStorage;
import ru.course.storage.organizations.OrganizationsStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficeServiceImpl implements OfficeService {
    private final OfficeStorage officeStorage;
    private final OrganizationsStorage organizationsStorage;
    private final MapperFactory mapperFactory;

    @Autowired
    public OfficeServiceImpl(OfficeStorage officeStorage, OrganizationsStorage organizationsStorage, MapperFactory mapperFactory) {
        this.officeStorage = officeStorage;
        this.organizationsStorage = organizationsStorage;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public List<OfficeListItemDto> filter(OfficeFilterDto filter) {
        if (!organizationsStorage.contains(filter.orgId)) {
            throw new NotFoundException("Organization by id " + filter.orgId + " not found");
        }
        return officeStorage.filter(filter.orgId, filter.name, filter.phone, filter.isActive).stream()
                .map(mapperFactory.getMapperFacade(Office.class, OfficeListItemDto.class)::map)
                .collect(Collectors.toList());
    }

    @Override
    public OfficeDto get(int id) {
        Office office = officeStorage.get(id);
        if (office == null) return null;
        return mapperFactory.getMapperFacade(Office.class, OfficeDto.class).map(office);
    }

    @Override
    public void save(OfficeCreateDto dto) {
        checkOrgId(dto.getOrgId());
        Office office = mapperFactory.getMapperFacade(OfficeCreateDto.class, Office.class).map(dto);
        officeStorage.save(office);
    }

    @Override
    public void update(OfficeDto dto) {
        checkOrgId(dto.getOrgId());
        Office office = mapperFactory.getMapperFacade(OfficeDto.class, Office.class).map(dto);
        officeStorage.update(office);
    }

    private void checkOrgId(Integer orgId) {
        if (orgId != null && !organizationsStorage.contains(orgId)) {
            throw new NotFoundException("Organization by id " + orgId + " not found");
        }
    }
}
