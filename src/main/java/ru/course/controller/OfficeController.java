package ru.course.controller;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.course.controller.advice.NotFoundException;
import ru.course.dto.ResponseDto;
import ru.course.dto.SuccessResponseDto;
import ru.course.dto.office.OfficeCreateDto;
import ru.course.dto.office.OfficeDto;
import ru.course.dto.office.OfficeFilterDto;
import ru.course.dto.office.OfficeListItemDto;
import ru.course.model.office.Office;
import ru.course.storage.office.OfficeStorage;
import ru.course.storage.organizations.OrganizationsStorage;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.course.util.ResponseUtil.dataResponse;
import static ru.course.util.ResponseUtil.errorResponse;

@RestController
@RequestMapping(value = "/api/office", produces = APPLICATION_JSON_VALUE)
public class OfficeController {
    private final OfficeStorage officeStorage;
    private final OrganizationsStorage organizationsStorage;
    private final MapperFactory mapperFactory;

    @Autowired
    public OfficeController(OfficeStorage officeStorage, OrganizationsStorage organizationsStorage, MapperFactory mapperFactory) {
        this.officeStorage = officeStorage;
        this.organizationsStorage = organizationsStorage;
        this.mapperFactory = mapperFactory;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<List<OfficeListItemDto>>> list(@RequestBody @Valid OfficeFilterDto filter) {
        return dataResponse(officeStorage.filter(filter.orgId, filter.name, filter.phone, filter.isActive).stream()
                .map(mapperFactory.getMapperFacade(Office.class, OfficeListItemDto.class)::map)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto<OfficeDto>> get(@PathVariable int id) {
        Office office = officeStorage.get(id);
        if (office == null) return errorResponse("Office not found", HttpStatus.NOT_FOUND);
        OfficeDto dto = mapperFactory.getMapperFacade(Office.class, OfficeDto.class).map(office);
        return dataResponse(dto);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponseDto save(@RequestBody @Valid OfficeCreateDto dto) {
        if (dto.getOrgId() != null && !organizationsStorage.contains(dto.getOrgId())) {
            throw new NotFoundException("Organization by id " + dto.getOrgId() + " not found");
        }
        Office office = mapperFactory.getMapperFacade(OfficeDto.class, Office.class).map(dto);
        officeStorage.save(office);
        return new SuccessResponseDto();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponseDto update(@RequestBody @Valid OfficeDto dto) {
        Office office = mapperFactory.getMapperFacade(OfficeDto.class, Office.class).map(dto);
        officeStorage.update(office);
        return new SuccessResponseDto();
    }
}