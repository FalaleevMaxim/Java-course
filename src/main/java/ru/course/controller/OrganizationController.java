package ru.course.controller;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.course.dto.ResponseDto;
import ru.course.dto.SuccessResponseDto;
import ru.course.dto.organization.OrganizationCreateDto;
import ru.course.dto.organization.OrganizationDto;
import ru.course.dto.organization.OrganizationFilterDto;
import ru.course.dto.organization.OrganizationListItemDto;
import ru.course.model.organization.Organization;
import ru.course.storage.organizations.OrganizationsStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.course.util.ResponseUtil.dataResponse;
import static ru.course.util.ResponseUtil.errorResponse;

@RestController
@RequestMapping(value = "/api/organization", produces = APPLICATION_JSON_VALUE)
public class OrganizationController {
    private final OrganizationsStorage storage;
    private final MapperFactory mapperFactory;

    @Autowired
    public OrganizationController(OrganizationsStorage storage, MapperFactory mapperFactory) {
        this.storage = storage;
        this.mapperFactory = mapperFactory;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<List<OrganizationListItemDto>>> list(@RequestBody @Valid OrganizationFilterDto filter) {
        return dataResponse(storage.filter(filter.name, filter.inn, filter.isActive).stream()
                .map(mapperFactory.getMapperFacade(Organization.class, OrganizationListItemDto.class)::map)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto<OrganizationDto>> get(@PathVariable int id) {
        Organization org = storage.get(id);
        if (org == null) return errorResponse("Organization not found", HttpStatus.NOT_FOUND);
        OrganizationDto dto = mapperFactory.getMapperFacade(Organization.class, OrganizationDto.class).map(org);
        return dataResponse(dto);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponseDto save(@RequestBody @Valid OrganizationCreateDto dto) {
        Organization org = mapperFactory.getMapperFacade(OrganizationCreateDto.class, Organization.class).map(dto);
        storage.save(org);
        return new SuccessResponseDto();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponseDto update(@RequestBody @Valid OrganizationDto dto) {
        Organization org = mapperFactory.getMapperFacade(OrganizationDto.class, Organization.class).map(dto);
        storage.update(org);
        return new SuccessResponseDto();
    }
}